package me.masterejay.pacman.countdowns;

import me.masterejay.pacman.match.MatchHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

/**
 * @author MasterEjay
 */
public class MatchStartCountdown extends me.masterejay.pacman.countdowns.Countdown {
    @Override
    public void onEnd() {
        Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "############");
        Bukkit.broadcastMessage(ChatColor.GREEN + "The match has started!");
        Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "############");
        MatchHandler.start();
    }

    @Override
    public void tick(int secs) {
        if (secs % 5 == 0 || secs < 5) {
            Bukkit.broadcastMessage(ChatColor.GREEN + "Match starting in " + ChatColor.DARK_RED + "" + secs + ChatColor.GREEN + " second" + (secs == 1 ? "" : "s") + "!");
        }

    }
}
