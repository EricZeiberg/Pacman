package me.masterejay.pacman.listeners;

import me.masterejay.pacman.Pacman;
import me.masterejay.pacman.Values;
import me.masterejay.pacman.match.MatchHandler;
import me.masterejay.pacman.match.MatchState;
import me.masterejay.pacman.teams.TeamHandler;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * @author MasterEjay
 */
public class DamageListener implements Listener {

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e){
        if (e.getDamager() instanceof Player && e.getEntity() instanceof Player){
            if (TeamHandler.getGhosts().getPlayers().contains(e.getDamager()) &&
                    TeamHandler.getPacmanPlayer().isPlayerPacman((Player) e.getEntity()) && Pacman.getState() == MatchState.PLAYING){
               e.getEntity().teleport(Values.getPacmanSpawn());
                Values.setLivesLeft(Values.getLivesLeft() - 1);
                if (Values.getLivesLeft() == 0){
                    MatchHandler.finish(false);
                }
                else {
                    ((Player) e.getDamager()).sendMessage(ChatColor.GREEN + "You have eaten the pacman! He only has " +
                            ChatColor.DARK_RED + Values.getLivesLeft() + ChatColor.GREEN + " lives left!");
                }
            }
            else if (TeamHandler.getPacmanPlayer().isPlayerPacman((Player) e.getDamager()) &&
                    TeamHandler.getGhosts().getPlayers().contains(e.getEntity()) && Pacman.getState() == MatchState.PLAYING && Values.isGhostMode()) {
              ((Player) e.getEntity()).sendMessage(ChatColor.RED + "The pacman has hit you in ghost mode!");
                e.getEntity().teleport(Values.getGhostSpawn());
            }
            else {
                e.setCancelled(true);
            }
        }
    }
}
