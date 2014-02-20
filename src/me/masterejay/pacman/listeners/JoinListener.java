package me.masterejay.pacman.listeners;

import me.masterejay.pacman.Values;
import me.masterejay.pacman.match.MatchHandler;
import me.masterejay.pacman.teams.TeamHandler;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * @author MasterEjay
 */
public class JoinListener implements Listener{



    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        TeamHandler.getObservers().addObserver(e.getPlayer());
        e.getPlayer().teleport(Values.getObserverSpawn());
    }
}
