package me.masterejay.pacman.countdowns;

import me.masterejay.pacman.Pacman;
import me.masterejay.pacman.Values;
import me.masterejay.pacman.match.MatchHandler;
import me.masterejay.pacman.teams.TeamHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * @author MasterEjay
 */
public class GhostCountdown extends Countdown{

    @Override
    public void onEnd() {
        Values.setGhostMode(false);

    }

    @Override
    public void tick(int secs) {
        if (secs % 5 == 0 || secs < 5) {
            Bukkit.broadcastMessage(ChatColor.AQUA + "Ghostmode ends in " + ChatColor.DARK_RED + "" + secs + ChatColor.AQUA + " second" + (secs == 1 ? "" : "s") + "!");
        }

    }
}
