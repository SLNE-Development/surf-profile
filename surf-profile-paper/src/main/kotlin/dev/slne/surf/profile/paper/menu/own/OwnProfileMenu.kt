package dev.slne.surf.profile.paper.menu.own

import com.github.stefvanschie.inventoryframework.gui.GuiItem
import com.github.stefvanschie.inventoryframework.pane.StaticPane
import dev.slne.surf.core.api.paper.util.toSurfPlayer
import dev.slne.surf.playtime.api.surfPlaytimeApi
import dev.slne.surf.profile.paper.integration.LuckPermsIntegration
import dev.slne.surf.profile.paper.integration.SettingsIntegration
import dev.slne.surf.profile.paper.util.formatSeconds
import dev.slne.surf.surfapi.bukkit.api.builder.buildItem
import dev.slne.surf.surfapi.bukkit.api.builder.buildLore
import dev.slne.surf.surfapi.bukkit.api.builder.displayName
import dev.slne.surf.surfapi.bukkit.api.event.cancel
import dev.slne.surf.surfapi.bukkit.api.inventory.dsl.menu
import dev.slne.surf.surfapi.core.api.font.toSmallCaps
import dev.slne.surf.surfapi.core.api.messages.adventure.buildText
import dev.slne.surf.surfapi.core.api.messages.builder.SurfComponentBuilder
import dev.slne.surf.surfapi.core.api.util.dateTimeFormatter
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.meta.SkullMeta

private const val width = 9
private const val height = 5

private val borderItem = GuiItem(buildItem(Material.GRAY_STAINED_GLASS_PANE) {
    displayName {
        text(" ")
    }
})


fun ownProfileMenu(player: Player) = menu(buildText {
    spacer("Dein Profil".toSmallCaps())
}, height) {
    val surfPlayer = player.toSurfPlayer()
    val outlinePane = StaticPane(0, 0, width, height).apply {
        for (y in 1 until height - 1) {
            addItem(borderItem, 0, y)
            addItem(borderItem, width - 1, y)
        }

        for (x in 0 until width) {
            addItem(borderItem, x, 0)
            addItem(borderItem, x, height - 1)
        }
    }

    val contentPane = StaticPane(1, 2, 7, 1).apply {
        addItem(
            GuiItem(
                buildItem(Material.PLAYER_HEAD) {
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
                                surfPlaytimeApi.getCurrentPlaytimeSession(player.uniqueId)?.durationSeconds?.formatSeconds()
                                    ?: "0s"
                            )
                        }
                        emptyLine()
                        line {
                            variableValue("Server: ".toSmallCaps())
                        }
                        line {
                            note(surfPlayer.currentServer ?: "Unbekannt")
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
                }
            ), 3, 0)

        addItem(GuiItem(buildItem(Material.POPPY) {
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
        }), 1, 0)

        addItem(GuiItem(buildItem(Material.REPEATER) {
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
        }) {
            SettingsIntegration.openMenu(it.whoClicked)
        }, 5, 0)
    }

    addPane(outlinePane)
    addPane(contentPane)

    setOnGlobalDrag { it.cancel() }
    setOnGlobalClick { it.cancel() }

    show(player)
}

private fun SurfComponentBuilder.localColored(text: Any, vararg decoration: TextDecoration) =
    text(text.toString(), TextColor.fromHexString("#42f590"), *decoration)

