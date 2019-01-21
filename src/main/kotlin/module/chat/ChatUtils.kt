package io.github.pipespotatos.module.chat

import io.github.pipespotatos.module.roles.RoleManager
import org.spongepowered.api.entity.living.player.Player
import org.spongepowered.api.text.Text
import org.spongepowered.api.text.format.TextColor
import org.spongepowered.api.text.format.TextColors

private fun getPlayerColor(player: Player): TextColor {
    var ret = TextColors.WHITE
    val roles = RoleManager.getPlayerRoles(player.uniqueId)

    roles.forEach {
        ret = TextColors::class.java.getDeclaredField(it.properties.chatColor).get(null) as TextColor
    }

    return ret
}

fun getPlayerName(p: Player): Text {
    val color = getPlayerColor(p)
    val opIndicator = if (p.hasPermission("chatty.fakeperm.opcheck")) {
        Text.of(TextColors.GOLD, "@")
    } else {
        Text.EMPTY
    }

    return Text.of(opIndicator, color, p.name)
}