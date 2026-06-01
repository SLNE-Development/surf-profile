package dev.slne.surf.profile.paper.menu

import com.github.shynixn.mccoroutine.folia.launch
import dev.slne.surf.api.core.font.toSmallCaps
import dev.slne.surf.api.core.luckperms.LuckPermsAccess
import dev.slne.surf.api.core.luckperms.getMeta
import dev.slne.surf.api.core.luckperms.prefix
import dev.slne.surf.api.core.messages.adventure.key
import dev.slne.surf.api.core.messages.adventure.sendText
import dev.slne.surf.api.core.minimessage.miniMessage
import dev.slne.surf.api.paper.builder.buildItem
import dev.slne.surf.api.paper.builder.buildLore
import dev.slne.surf.api.paper.builder.displayName
import dev.slne.surf.core.api.common.player.SurfPlayer
import dev.slne.surf.profile.paper.hook.ClanHook
import dev.slne.surf.profile.paper.hook.FriendsHook
import dev.slne.surf.profile.paper.hook.SocialsHook
import dev.slne.surf.profile.paper.hook.TrophyHook
import dev.slne.surf.profile.paper.integration.SettingsIntegration
import dev.slne.surf.profile.paper.mapped.MappedClan
import dev.slne.surf.profile.paper.mapped.MappedFriends
import dev.slne.surf.profile.paper.mapped.MappedTrophy
import dev.slne.surf.profile.paper.mapped.MappedVerification
import dev.slne.surf.profile.paper.plugin
import io.papermc.paper.datacomponent.DataComponentTypes
import io.papermc.paper.datacomponent.item.ResolvableProfile
import io.papermc.paper.datacomponent.item.TooltipDisplay
import me.devnatan.inventoryframework.View
import me.devnatan.inventoryframework.ViewConfigBuilder
import me.devnatan.inventoryframework.context.RenderContext
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material
import org.bukkit.inventory.ItemFlag

@Suppress("UnstableApiUsage")
object ProfileView : View() {
    private val targetHolder = initialState<SurfPlayer>("target")

    private val twitchNameHolder = mutableState("Lädt...")
    private val discordNameHolder = mutableState("Lädt...")
    private val verificationHolder = mutableState(MappedVerification.loading())

    private val trophiesHolder = mutableState(listOf(MappedTrophy.loading()))
    private val friendsHolder = mutableState(MappedFriends.loading())
    private val clansHolder = mutableState(MappedClan.loading())

    override fun onInit(config: ViewConfigBuilder) {
        config
            .title("<shift:-8><glyph:profile-gui>")
            .size(3)
            .layout(
                " PH      ",
                " HV YFC  ",
                " TD    S "
            )
            .cancelInteractions()
    }

    override fun onFirstRender(render: RenderContext) {
        val target = targetHolder.get(render)

        plugin.launch {
            if (plugin.hasSocialsHook()) {
                twitchNameHolder.set(SocialsHook.getTwitchName(target.uuid) ?: "Unbekannt", render)
                discordNameHolder.set(
                    SocialsHook.getDiscordName(target.uuid) ?: "Unbekannt",
                    render
                )
            }

            if (plugin.hasClanHook()) {
                clansHolder.set(ClanHook.getMappedClan(target.uuid), render)
            }

            if (plugin.hasTrophiesHook()) {
                trophiesHolder.set(TrophyHook.getTrophies(target.uuid), render)
            }

            if (plugin.hasFriendsHook()) {
                friendsHolder.set(FriendsHook.getMappedFriends(target.uuid), render)
            }

            verificationHolder.set(loadVerification(target), render)
        }

        render.layoutSlot('P', create2DHead(target))
        render.layoutSlot('H', createEmptyHeadItem(target))
        render.layoutSlot('V').renderWith {
            createVerifiedIcon(target, verificationHolder.get(render))
        }.updateOnStateChange(verificationHolder)

        render.layoutSlot('T').renderWith {
            buildTwitchIcon(twitchNameHolder.get(render))
        }.updateOnStateChange(twitchNameHolder)

        render.layoutSlot('D').renderWith {
            buildDiscordIcon(discordNameHolder.get(render))
        }.updateOnStateChange(discordNameHolder)

        render.layoutSlot('Y').renderWith {
            createTrophiesItem(trophiesHolder.get(render))
        }.updateOnStateChange(trophiesHolder).onClick { click ->
            if (plugin.hasTrophiesHook()) {
                TrophyHook.openTrophyMenu(target.uuid, click.player.uniqueId)
            } else {
                click.player.sendText {
                    appendErrorPrefix()
                    error("Die Trophäen sind hier nicht verfügbar.")
                }
            }
        }

        render.layoutSlot('F').renderWith {
            createFriendsItem(friendsHolder.get(render))
        }.updateOnStateChange(friendsHolder)

        render.layoutSlot('C').renderWith {
            createClansItem(clansHolder.get(render))
        }.updateOnStateChange(clansHolder)

        render.layoutSlot('S', buildItem(Material.REPEATER) {
            displayName {
                red("Einstellungen".toSmallCaps())
            }

            buildLore {
                emptyLine()
                line {
                    variableValue("Beschreibung:".toSmallCaps())
                }
                line {
                    spacer("-")
                    appendSpace()
                    note("Chateinstellungen ändern")
                }
                line {
                    spacer("-")
                    appendSpace()
                    note("Claneinstellungen anpassen")
                }
                line {
                    spacer("-")
                    appendSpace()
                    note("Freundeseinstellungen verwalten")
                }
                line {
                    spacer("-")
                    appendSpace()
                    note("Lobbyeinstellungen festlegen")
                }

                emptyLine()
                line {
                    spacer("Klicke, um das Einstellungsmenü zu öffnen.")
                }
            }
        }).displayIf { context ->
            context.player.uniqueId == target.uuid
        }.onClick { click ->
            if (plugin.hasSettingsHook()) {
                SettingsIntegration.openMenu(click.player)
            } else {
                click.player.sendText {
                    appendErrorPrefix()
                    error("Die Einstellungen sind hier nicht verfügbar.")
                }
            }
        }
    }

    private fun create2DHead(surfPlayer: SurfPlayer) = buildItem(Material.PLAYER_HEAD) {
        displayName {
            variableValue(surfPlayer.username.toSmallCaps())
        }

        setData(DataComponentTypes.ITEM_MODEL, key("nexo", "2d_player_head_2x"))
        setData(
            DataComponentTypes.PROFILE, ResolvableProfile
                .resolvableProfile()
                .uuid(surfPlayer.uuid)
                .build()
        )

        setData(
            DataComponentTypes.TOOLTIP_DISPLAY, TooltipDisplay
                .tooltipDisplay()
                .hiddenComponents(setOf(DataComponentTypes.PROFILE))
                .build()
        )

        addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
    }

    private fun createEmptyHeadItem(surfPlayer: SurfPlayer) = buildItem(Material.PAPER) {
        displayName {
            variableValue(surfPlayer.username.toSmallCaps())
        }

        setData(
            DataComponentTypes.ITEM_MODEL,
            key("nexo", "empty")
        )
    }

    private fun createVerifiedIcon(surfPlayer: SurfPlayer, verification: MappedVerification) =
        buildItem(Material.PAPER) {
            if (!verification.verified || verification.loading) {
                displayName {
                    variableValue(surfPlayer.username.toSmallCaps())
                }

                setData(
                    DataComponentTypes.ITEM_MODEL,
                    key("nexo", "empty")
                )
                return@buildItem
            }

            displayName {
                success("✔ Verifiziert")
            }

            setData(DataComponentTypes.ITEM_MODEL, key("nexo", "verified"))

            buildLore {
                emptyLine()
                line {
                    append(verification.rank)
                }

                line {
                    variableValue(verification.verificationText.toSmallCaps())
                }
            }
        }

    private fun buildTwitchIcon(name: String) = buildItem(Material.PAPER) {
        displayName {
            text(
                "Twitch Link".toSmallCaps(),
                TextColor.fromHexString("#8956fb"),
                TextDecoration.BOLD
            )
        }

        setData(DataComponentTypes.ITEM_MODEL, key("nexo", "twitch-logo"))

        buildLore {
            line {
                darkSpacer(">")
                appendSpace()
                spacer("@")
                text(name, TextColor.fromHexString("#8956fb"))
            }
        }
    }

    private fun buildDiscordIcon(name: String = "Lädt...") = buildItem(Material.PAPER) {
        displayName {
            text(
                "Discord Link".toSmallCaps(),
                TextColor.fromHexString("#5865f2"),
                TextDecoration.BOLD
            )
        }

        setData(DataComponentTypes.ITEM_MODEL, key("nexo", "discord-logo"))

        buildLore {
            line {
                darkSpacer(">")
                appendSpace()
                spacer("@")
                text(name, TextColor.fromHexString("#5865f2"))
            }
        }
    }

    private fun createTrophiesItem(trophies: List<MappedTrophy>) = buildItem(Material.GOLD_INGOT) {
        displayName {
            variableValue("Trophäen".toSmallCaps())
        }

        buildLore {
            emptyLine()
            if (trophies.any { it.loading }) {
                line {
                    note("Lädt...")
                }
                return@buildLore
            }

            if (trophies.isEmpty()) {
                line {
                    note("Dieser Spieler hat noch keine Trophäen erhalten.")
                }
            } else {
                line {
                    info("Erhaltene Trophäen: ")
                    variableValue(trophies.size)
                }
                trophies.sortedBy { it.receivedAt }.take(10).forEach {
                    line {
                        darkSpacer(">")
                        appendSpace()
                        variableValue(it.name.toSmallCaps())
                    }
                }
            }
        }
    }

    private fun createFriendsItem(friends: MappedFriends) = buildItem(Material.CANDLE) {
        displayName {
            variableValue("Freunde".toSmallCaps())
        }

        buildLore {
            emptyLine()
            if (friends.loading) {
                line {
                    note("Lädt...")
                }
                return@buildLore
            }

            line {
                info("Anzahl Freunde: ")
                variableValue(friends.friendCount)
            }
        }
    }

    private fun createClansItem(mappedClan: MappedClan) = buildItem(Material.TNT) {
        displayName {
            variableValue("Clan".toSmallCaps())
        }

        buildLore {
            emptyLine()
            if (mappedClan.loading) {
                line {
                    note("Lädt...")
                }
                return@buildLore
            }

            if (mappedClan.empty) {
                line {
                    note("Dieser Spieler ist in keinem Clan.")
                }
            } else {
                line {
                    darkSpacer(">")
                    appendSpace()
                    info("Clanname: ")
                    variableValue(mappedClan.clanName.toSmallCaps())
                }

                line {
                    darkSpacer(">")
                    appendSpace()
                    info("Clan-Tag: ")
                    variableValue(mappedClan.clanTag.toSmallCaps())
                }

                line {
                    darkSpacer(">")
                    appendSpace()
                    info("Rolle: ")
                    variableValue(mappedClan.playersClanRole.toSmallCaps())
                }
            }
        }
    }


    private suspend fun loadVerification(surfPlayer: SurfPlayer): MappedVerification {
        val luckPermsUser =
            LuckPermsAccess.getUser(surfPlayer.uuid) ?: LuckPermsAccess.loadUser(surfPlayer.uuid)

        val verified = luckPermsUser.getMeta<String>("verified")
        val verificationText = luckPermsUser.getMeta<String>("verification_text", "Not verified")
        val rank = "${luckPermsUser.prefix}${surfPlayer.username}"

        return MappedVerification(
            verified = verified?.toBoolean() ?: false,
            verificationText = verificationText,
            rank = miniMessage.deserialize(rank)
        )
    }
}