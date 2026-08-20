package dev.slne.surf.profile.paper.menu.own

import dev.slne.surf.api.paper.builder.displayName
import dev.slne.surf.api.paper.builder.lore
import dev.slne.surf.api.paper.inventory.framework.dsl.slot
import dev.slne.surf.api.paper.inventory.framework.dsl.withItem
import dev.slne.surf.api.paper.inventory.framework.view.onFirstRender
import dev.slne.surf.api.paper.inventory.framework.view.settings
import dev.slne.surf.api.paper.inventory.framework.view.surfView
import dev.slne.surf.core.api.paper.util.toSurfPlayer
import dev.slne.surf.profile.core.client.integration.SettingsIntegration
import dev.slne.surf.profile.core.client.menu.own.OwnProfileMenuContent
import dev.slne.surf.profile.paper.menu.playClickSound
import io.papermc.paper.datacomponent.DataComponentTypes
import io.papermc.paper.datacomponent.item.ResolvableProfile
import org.bukkit.inventory.ItemType

@Suppress("UnstableApiUsage")
val ownProfileMenu = surfView(OwnProfileMenuContent.TITLE) {
    settings {
        rows(3)
        cancelAllInteractions()
    }

    onFirstRender {
        val player = this.player
        val surfPlayer = player.toSurfPlayer()

        slot(2, 5) {
            withItem(ItemType.PLAYER_HEAD) {
                displayName(OwnProfileMenuContent.headDisplayName(player.name))

                setData(
                    DataComponentTypes.PROFILE,
                    ResolvableProfile.resolvableProfile(player.playerProfile)
                )

                lore(*OwnProfileMenuContent.headLore(surfPlayer))
            }
        }

        slot(2, 3) {
            withItem(ItemType.POPPY) {
                displayName(OwnProfileMenuContent.friendsDisplayName)
                lore(*OwnProfileMenuContent.friendsLore)
            }
        }

        slot(2, 7) {
            withItem(ItemType.REPEATER) {
                displayName(OwnProfileMenuContent.settingsDisplayName)
                lore(*OwnProfileMenuContent.settingsLore)
            }

            onClick { click ->
                click.playClickSound()
                SettingsIntegration.openMenu(click.player.uniqueId)
            }
        }
    }
}
