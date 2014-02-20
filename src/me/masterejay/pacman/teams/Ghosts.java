package me.masterejay.pacman.teams;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MasterEjay
 */
public class Ghosts {

    private static List<Player> ghosts = new ArrayList<Player>();

    public void addPlaying(Player player){
        if (ghosts.contains(player)){
            return;
        }
        ghosts.add(player);
        player.setDisplayName(ChatColor.RED + player.getName());
        player.sendMessage(ChatColor.GRAY + "You have joined the " + ChatColor.RED + "Ghosts");
    }

    public List<Player> getPlayers(){
        return ghosts;
    }

    public void removePlaying(Player player){
        if (ghosts.contains(player)){
            ghosts.remove(player);
        }

    }

}
