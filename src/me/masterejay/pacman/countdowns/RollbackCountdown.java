package me.masterejay.pacman.countdowns;

import me.masterejay.pacman.Pacman;
import me.masterejay.pacman.match.MatchHandler;

import me.masterejay.pacman.teams.TeamHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * @author MasterEjay
 */
public class RollbackCountdown extends Countdown{

    @Override
    public void onEnd() {
        for (Player p : Bukkit.getOnlinePlayers()){
            if (TeamHandler.getPacmanPlayer().isPlayerPacman(p)){
                TeamHandler.getPacmanPlayer().clearPacman();
                TeamHandler.getObservers().addObserver(p);
            }
            else if (TeamHandler.getGhosts().getPlayers().contains(p)){
                TeamHandler.getGhosts().removePlaying(p);
                TeamHandler.getObservers().addObserver(p);
            }
            p.kickPlayer(ChatColor.GREEN + "Good game!");
        }
        MatchHandler.rollback("Map");
        Pacman.get().getServer().shutdown();

    }

    @Override
    public void tick(int secs) {
        if (secs % 5 == 0 || secs < 5) {
            Bukkit.broadcastMessage(ChatColor.AQUA + "Server restarting in " + ChatColor.DARK_RED + "" + secs + ChatColor.AQUA + " second" + (secs == 1 ? "" : "s") + "!");
        }

    }
}
