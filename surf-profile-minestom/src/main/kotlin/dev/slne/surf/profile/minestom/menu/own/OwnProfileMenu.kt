package dev.slne.surf.profile.minestom.menu.own

import dev.slne.surf.api.minestom.builder.ItemDsl
import dev.slne.surf.api.minestom.inventory.framework.dsl.slot
import dev.slne.surf.api.minestom.inventory.framework.dsl.withItem
import dev.slne.surf.api.minestom.inventory.framework.view.onFirstRender
import dev.slne.surf.api.minestom.inventory.framework.view.settings
import dev.slne.surf.api.minestom.inventory.framework.view.surfView
import dev.slne.surf.core.api.minestom.util.toSurfPlayer
import dev.slne.surf.profile.core.client.integration.SettingsIntegration
import dev.slne.surf.profile.core.client.menu.own.OwnProfileMenuContent
import dev.slne.surf.profile.minestom.menu.playClickSound
import net.minestom.server.component.DataComponents
import net.minestom.server.entity.Player
import net.minestom.server.item.Material
import net.minestom.server.item.component.TooltipDisplay
import net.minestom.server.network.player.ResolvableProfile

val ownProfileMenu = surfView(OwnProfileMenuContent.TITLE) {
    settings {
        rows(3)
        cancelAllInteractions()
    }

    onFirstRender {
        val player = this.player
        val surfPlayer = player.toSurfPlayer()

        slot(2, 5) {
            withItem(Material.PLAYER_HEAD) {
                displayName(OwnProfileMenuContent.headDisplayName(player.username))
                headOf(player)
                lore(*OwnProfileMenuContent.headLore(surfPlayer))
            }
        }

        slot(2, 3) {
            withItem(Material.POPPY) {
                displayName(OwnProfileMenuContent.friendsDisplayName)
                lore(*OwnProfileMenuContent.friendsLore)
            }
        }

        slot(2, 7) {
            withItem(Material.REPEATER) {
                displayName(OwnProfileMenuContent.settingsDisplayName)
                lore(*OwnProfileMenuContent.settingsLore)
            }

            onClick { click ->
                click.playClickSound()
                SettingsIntegration.openMenu(click.player.uuid)
            }
        }
    }
}

private fun ItemDsl.headOf(player: Player) {
    val skin = player.skin ?: return

    builder.set(DataComponents.PROFILE, ResolvableProfile(skin))
    builder.set(
        DataComponents.TOOLTIP_DISPLAY,
        TooltipDisplay(false, setOf(DataComponents.PROFILE))
    )
}
