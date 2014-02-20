package me.masterejay.pacman.teams;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MasterEjay
 */
public class PacmanPlayer {

  private static Player pacman = null;


    public void setPacman(Player player){
        pacman = player;
        player.sendMessage(ChatColor.GRAY + "You have become " + ChatColor.GREEN + "Pacman!");
        player.setDisplayName(ChatColor.GREEN + player.getName());
    }

    public void clearPacman(){
        pacman = null;
    }

    public boolean isPlayerPacman(Player player){
        if (player == pacman){
            return true;
        }
        else  {
            return false;
        }
    }

    public Player getPacman() {
        return pacman;
    }
}
