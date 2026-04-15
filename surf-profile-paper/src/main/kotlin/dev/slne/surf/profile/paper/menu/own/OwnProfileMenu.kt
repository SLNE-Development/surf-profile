package dev.slne.surf.profile.paper.menu.own

import dev.slne.surf.api.core.font.toSmallCaps
import dev.slne.surf.api.core.messages.builder.SurfComponentBuilder
import dev.slne.surf.api.core.util.dateTimeFormatter
import dev.slne.surf.api.paper.builder.buildItem
import dev.slne.surf.api.paper.builder.buildLore
import dev.slne.surf.api.paper.builder.displayName
import dev.slne.surf.api.paper.inventory.framework.dsl.slot
import dev.slne.surf.api.paper.inventory.framework.view.AbstractSurfView
import dev.slne.surf.api.paper.inventory.framework.view.onFirstRender
import dev.slne.surf.api.paper.inventory.framework.view.settings
import dev.slne.surf.api.paper.inventory.framework.view.surfView
import dev.slne.surf.core.api.paper.util.toSurfPlayer
import dev.slne.surf.playtime.api.common.surfPlaytimeApi
import dev.slne.surf.profile.paper.integration.LuckPermsIntegration
import dev.slne.surf.profile.paper.integration.SettingsIntegration
import dev.slne.surf.profile.paper.menu.playClickSound
import dev.slne.surf.profile.paper.util.formatSeconds
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material
import org.bukkit.inventory.meta.SkullMeta

fun ownProfileMenu(): AbstractSurfView = surfView("Dein Profil") {
    settings {
        rows(3)
        cancelAllInteractions()
    }

    onFirstRender {
        val player = this.player
        val surfPlayer = player.toSurfPlayer()

        slot(2, 5) {
            withItem(buildItem(Material.PLAYER_HEAD) {
                displayName {
                    localColored(player.name.toSmallCaps(), TextDecoration.BOLD)
                }

                editMeta(SkullMeta::class.java) {
                    it.owningPlayer = player
                }

                buildLore {
                    emptyLine()
                    line {
                        variableValue("Rang: ".toSmallCaps())
                    }
                    line {
                        append(LuckPermsIntegration.getRang(player.uniqueId))
                    }

                    emptyLine()
                    line {
                        variableValue("Aktuelle Session: ".toSmallCaps())
                    }
                    line {
                        note(
                            surfPlaytimeApi.getCurrentPlaytimeSession(player.uniqueId)
                                ?.durationSeconds
                                ?.formatSeconds() ?: "0s"
                        )
                    }

                    emptyLine()
                    line {
                        variableValue("Server: ".toSmallCaps())
                    }
                    line {
                        note(surfPlayer.currentServer?.name ?: "Unbekannt")
                    }

                    emptyLine()
                    line {
                        variableValue("Erster Login: ".toSmallCaps())
                    }
                    line {
                        note(
                            surfPlayer.firstSeen?.format(dateTimeFormatter) ?: "Unbekannt"
                        )
                    }
                }
            })
        }

        slot(2, 3) {
            withItem(buildItem(Material.POPPY) {
                displayName {
                    localColored("Freunde".toSmallCaps(), TextDecoration.BOLD)
                }

                buildLore {
                    emptyLine()
                    line {
                        variableValue("Beschreibung:".toSmallCaps())
                    }
                    line {
                        note("Das Freundesystem ist derzeit nur über Commands verfügbar.")
                    }
                }
            })
        }

        slot(2, 7) {
            withItem(buildItem(Material.REPEATER) {
                displayName {
                    localColored("Einstellungen".toSmallCaps(), TextDecoration.BOLD)
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
            })

            onClick { click ->
                click.playClickSound()
                SettingsIntegration.openMenu(click.player)
            }
        }
    }
}

private fun SurfComponentBuilder.localColored(text: Any, vararg decoration: TextDecoration) =
    text(text.toString(), TextColor.fromHexString("#42f590"), *decoration)