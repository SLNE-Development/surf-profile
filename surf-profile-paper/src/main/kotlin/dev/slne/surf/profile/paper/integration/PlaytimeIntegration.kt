package dev.slne.surf.profile.paper.integration

import dev.slne.surf.playtime.api.session.PlaytimeSession
import dev.slne.surf.playtime.api.surfPlaytimeApi
import dev.slne.surf.profile.paper.util.formatSeconds
import dev.slne.surf.surfapi.bukkit.api.builder.buildItem
import dev.slne.surf.surfapi.bukkit.api.builder.buildLore
import dev.slne.surf.surfapi.bukkit.api.builder.displayName
import dev.slne.surf.surfapi.core.api.font.toSmallCaps
import dev.slne.surf.surfapi.core.api.util.mutableObjectSetOf
import dev.slne.surf.surfapi.core.api.util.toObjectSet
import it.unimi.dsi.fastutil.objects.ObjectSet
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import java.util.*

object PlaytimeIntegration {
    private val enabled get() = Bukkit.getPluginManager().isPluginEnabled("surf-playtime-paper")

    suspend fun getPlaytimeSessionItems(playerUuid: UUID): ObjectSet<ItemStack> {
        if (!enabled) {
            return mutableObjectSetOf()
        }

        val playtime = surfPlaytimeApi.getAllPlaytimeSessions(playerUuid).groupBy { it.server }
        val mappedTimes = playtime.mapValues { it.value.sum() }

        return mappedTimes.map {
            buildItem(Material.CANDLE) {
                displayName {
                    variableValue(it.key.toSmallCaps())
                }

                buildLore {
                    emptyLine()
                    line {
                        spacer("-")
                        appendSpace()
                        note("Spielzeit: ")
                        variableValue(it.value.durationSeconds.formatSeconds())
                    }

                    emptyLine()
                    line {
                        spacer("-")
                        appendSpace()
                        note("Kategorie: ")
                        variableValue(it.value.category)
                    }

                    emptyLine()
                    line {
                        spacer("-")
                        appendSpace()
                        note("Einzelne Sessions: ")
                        variableValue(playtime.values.size)
                    }
                }
            }
        }.toObjectSet()
    }

    private fun List<PlaytimeSession>.sum(): PlaytimeSession {
        var totalDuration = 0L
        val base = this.first()

        forEach {
            totalDuration += it.durationSeconds
        }

        return PlaytimeSession(
            playerUuid = base.playerUuid,
            sessionId = UUID.randomUUID(),
            server = base.server,
            category = base.category,
            durationSeconds = totalDuration
        )
    }
}