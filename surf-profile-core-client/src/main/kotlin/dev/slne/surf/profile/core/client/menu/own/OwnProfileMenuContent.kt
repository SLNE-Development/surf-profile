package dev.slne.surf.profile.core.client.menu.own

import dev.slne.surf.api.core.font.toSmallCaps
import dev.slne.surf.api.core.messages.adventure.buildText
import dev.slne.surf.api.core.messages.builder.SurfComponentBuilder
import dev.slne.surf.api.core.util.dateTimeFormatter
import dev.slne.surf.core.api.common.player.SurfPlayer
import dev.slne.surf.playtime.api.common.surfPlaytimeApi
import dev.slne.surf.profile.core.client.integration.LuckPermsIntegration
import dev.slne.surf.profile.core.client.util.formatSeconds
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.Component.empty
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration

/**
 * Applies the profile menu's accent color to [text].
 */
fun SurfComponentBuilder.localColored(text: Any, vararg decoration: TextDecoration) =
    text(text.toString(), TextColor.color(0x42f590), *decoration)

/**
 * The platform-independent texts of the own-profile menu.
 *
 * Platform modules supply the inventory items and the click handling and take their display
 * names and lore from here, so both platforms render the same menu.
 */
object OwnProfileMenuContent {

    const val TITLE = "Dein Profil"

    /**
     * The display name of the head item belonging to the player named [playerName].
     */
    fun headDisplayName(playerName: String): Component = buildText {
        localColored(playerName.toSmallCaps(), TextDecoration.BOLD)
    }

    private val rangLabel = buildText { variableValue("Rang: ".toSmallCaps()) }
    private val sessionLabel = buildText { variableValue("Aktuelle Session: ".toSmallCaps()) }
    private val serverLabel = buildText { variableValue("Server: ".toSmallCaps()) }
    private val firstLoginLabel = buildText { variableValue("Erster Login: ".toSmallCaps()) }

    /**
     * The lore of the head item, describing [surfPlayer]'s rank, session, server and first login.
     */
    fun headLore(surfPlayer: SurfPlayer): Array<TextComponent> = arrayOf(
        empty(),
        rangLabel,
        buildText { append(LuckPermsIntegration.getRang(surfPlayer.uuid)) },

        empty(),
        sessionLabel,
        buildText {
            note(
                surfPlaytimeApi.getCurrentPlaytimeSession(surfPlayer.uuid)
                    ?.durationSeconds
                    ?.formatSeconds() ?: "0s"
            )
        },

        empty(),
        serverLabel,
        buildText { note(surfPlayer.currentServer?.name ?: "Unbekannt") },

        empty(),
        firstLoginLabel,
        buildText { note(surfPlayer.firstSeen?.format(dateTimeFormatter) ?: "Unbekannt") },
    )

    val friendsDisplayName: Component = buildText {
        localColored("Freunde".toSmallCaps(), TextDecoration.BOLD)
    }

    val friendsLore: Array<TextComponent> = arrayOf(
        empty(),
        buildText { variableValue("Beschreibung:".toSmallCaps()) },
        buildText { note("Das Freundesystem ist derzeit nur über Befehle verfügbar.") }
    )

    val settingsDisplayName: Component = buildText {
        localColored("Einstellungen".toSmallCaps(), TextDecoration.BOLD)
    }

    val settingsLore: Array<Component> = arrayOf(
        empty(),
        buildText { variableValue("Beschreibung:".toSmallCaps()) },
        settingsDescriptionLine("Chateinstellungen ändern"),
        settingsDescriptionLine("Claneinstellungen anpassen"),
        settingsDescriptionLine("Freundeseinstellungen verwalten"),
        settingsDescriptionLine("Lobbyeinstellungen festlegen"),

        empty(),
        buildText { spacer("Klicke, um das Einstellungsmenü zu öffnen.") },
    )

    private fun settingsDescriptionLine(description: String): Component = buildText {
        spacer("-")
        appendSpace()
        note(description)
    }
}
