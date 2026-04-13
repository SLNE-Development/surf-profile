package dev.slne.surf.profile.paper.menu

import dev.slne.surf.api.core.messages.adventure.playSound
import dev.slne.surf.api.paper.util.BukkitSound
import me.devnatan.inventoryframework.context.SlotClickContext

fun SlotClickContext.playClickSound() {
    this.player.playSound(true) {
        type(BukkitSound.UI_BUTTON_CLICK)
    }
}

