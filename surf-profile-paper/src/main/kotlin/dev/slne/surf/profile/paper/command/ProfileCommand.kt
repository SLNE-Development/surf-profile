package dev.slne.surf.profile.paper.command

import dev.jorel.commandapi.kotlindsl.commandTree
import dev.jorel.commandapi.kotlindsl.literalArgument
import dev.slne.surf.api.core.command.args.awaiting
import dev.slne.surf.api.core.messages.adventure.sendText
import dev.slne.surf.api.paper.command.executors.playerExecutorSuspend
import dev.slne.surf.api.paper.inventory.framework.viewFrame
import dev.slne.surf.core.api.common.player.SurfPlayer
import dev.slne.surf.core.api.paper.command.argument.surfOfflinePlayerArgument
import dev.slne.surf.core.api.paper.util.surfPlayer
import dev.slne.surf.profile.paper.integration.SettingsIntegration
import dev.slne.surf.profile.paper.menu.ProfileView

fun profileCommand() = commandTree("profile") {
    withPermission("surf.profile.command.profile")
    playerExecutorSuspend { player, _ ->
        viewFrame.open(ProfileView::class.java, player, mapOf("target" to player.surfPlayer))
    }

    surfOfflinePlayerArgument("target") {
        withPermission("surf.profile.command.profile.other")
        playerExecutorSuspend { player, arguments ->
            val target = arguments.awaiting<SurfPlayer>("target")

            viewFrame.open(ProfileView::class.java, player, mapOf("target" to target))
        }
    }

    literalArgument("connections") {
        literalArgument("toggleDiscord") {
            playerExecutorSuspend { player, _ ->
                val currentValue = SettingsIntegration.hasDiscordEnabled(player.uniqueId)
                SettingsIntegration.setDiscordEnabled(player.uniqueId, !currentValue)

                player.sendText {
                    appendSuccessPrefix()
                    info("Deine Discord Verbindung ist nun ")
                    if (!currentValue) {
                        success("öffentlich")
                    } else {
                        error("privat")
                    }

                    info(".")
                }
            }
        }

        literalArgument("toggleTwitch") {
            playerExecutorSuspend { player, _ ->
                val currentValue = SettingsIntegration.hasTwitchEnabled(player.uniqueId)
                SettingsIntegration.setTwitchEnabled(player.uniqueId, !currentValue)

                player.sendText {
                    appendSuccessPrefix()
                    info("Deine Twitch Verbindung ist nun ")
                    if (!currentValue) {
                        success("öffentlich")
                    } else {
                        error("privat")
                    }

                    info(".")
                }
            }
        }
    }
}