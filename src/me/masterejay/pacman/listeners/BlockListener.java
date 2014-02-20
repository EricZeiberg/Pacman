package me.masterejay.pacman.listeners;

import me.masterejay.pacman.Pacman;
import me.masterejay.pacman.Values;
import me.masterejay.pacman.match.MatchHandler;
import me.masterejay.pacman.match.MatchState;
import me.masterejay.pacman.teams.TeamHandler;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * @author MasterEjay
 */
public class BlockListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){
        e.setCancelled(true);
    }

    @EventHandler
    public void onBlockStep(PlayerMoveEvent e){
        Location loc = e.getTo();
        if (e.getTo().getBlockY() <= 0){
            e.getPlayer().teleport(Values.getObserverSpawn());
        }
        if (TeamHandler.getPacmanPlayer().isPlayerPacman(e.getPlayer()) && loc.subtract(0, 1, 0).getBlock().getType() ==
                Values.getOriginBlock() && Pacman.getState() == MatchState.PLAYING){
            MatchHandler.onPointGane();
            loc.getBlock().setType(Values.getNewBlock());

        }
        else if (TeamHandler.getPacmanPlayer().isPlayerPacman(e.getPlayer()) && loc.subtract(0, 1, 0).getBlock().getType() ==
                Values.getGhostBlock() && Pacman.getState() == MatchState.PLAYING){
            MatchHandler.onGhostMode();
            loc.getBlock().setType(Values.getNewBlock());

        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        e.setCancelled(true);
    }
}
