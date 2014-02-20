package me.masterejay.pacman.match;

import me.masterejay.pacman.Pacman;
import me.masterejay.pacman.Values;
import me.masterejay.pacman.countdowns.CountdownMethods;
import me.masterejay.pacman.countdowns.GhostCountdown;
import me.masterejay.pacman.countdowns.RollbackCountdown;
import me.masterejay.pacman.teams.TeamHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * @author MasterEjay
 */
public class MatchHandler {

    public static void start(){
      for (Player p : TeamHandler.getGhosts().getPlayers()){
          p.teleport(Values.getGhostSpawn());
          p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100000*60, 1));
          p.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
          p.setGameMode(GameMode.SURVIVAL);
          for (Player p1 : TeamHandler.getObservers().getObservers()){
              p.hidePlayer(p1);
          }
      }
        TeamHandler.getPacmanPlayer().getPacman().teleport(Values.getPacmanSpawn());
        for (Player p1 : TeamHandler.getObservers().getObservers()){
            TeamHandler.getPacmanPlayer().getPacman().hidePlayer(p1);
        }
        TeamHandler.getPacmanPlayer().getPacman().setGameMode(GameMode.SURVIVAL);
        Pacman.setState(MatchState.PLAYING);
    }


    public static void finish(boolean isPacman){
        for (Player p : Pacman.get().getServer().getOnlinePlayers()){
            for(PotionEffect effect : p.getActivePotionEffects())
            {
                p.removePotionEffect(effect.getType());
            }
            p.getInventory().clear();
            p.setGameMode(GameMode.CREATIVE);
        }
        Pacman.setState(MatchState.FINISHED);
        if (isPacman){
            Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "############");
            Bukkit.broadcastMessage(ChatColor.GREEN + "The match has ended!");
            Bukkit.broadcastMessage(ChatColor.GREEN + "The pacman has won! He collected " + Values.getPointsToWin() + " points!");
            Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "############");
        }
        else {
            Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "############");
            Bukkit.broadcastMessage(ChatColor.GREEN + "The match has ended!");
            Bukkit.broadcastMessage(ChatColor.GREEN + "The ghosts have won!");
            Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "############");
        }
        CountdownMethods.start(new RollbackCountdown(), 20);
    }

    public static void onPointGane(){
        TeamHandler.getPacmanPlayer().getPacman().playSound(TeamHandler.getPacmanPlayer().getPacman().getLocation(),
                Sound.LEVEL_UP, 5, 1);
       Values.addPoints(1);
        TeamHandler.getPacmanPlayer().getPacman().sendMessage(ChatColor.BOLD + "" + ChatColor.GREEN + "+1 point" );
        if (Values.getPoints() == Values.getPointsToWin()){
            finish(true);
        }
    }

    public static void onGhostMode(){
        TeamHandler.getPacmanPlayer().getPacman().playSound(TeamHandler.getPacmanPlayer().getPacman().getLocation(),
                Sound.ENDERDRAGON_GROWL, 3, 1);
        CountdownMethods.cancelAll();
        Bukkit.broadcastMessage(ChatColor.GOLD + "Ghost mode has been activated! The pacman can now send ghosts back to spawn");
        Values.setGhostMode(true);
        CountdownMethods.start(new GhostCountdown(), 30);
    }

    public static void unloadMap(String mapname){
        if(Bukkit.getServer().unloadWorld(Bukkit.getServer().getWorld(mapname), false)){
            Pacman.get().getLogger().info("Successfully unloaded " + mapname);
        }else{
            Pacman.get().getLogger().severe("COULD NOT UNLOAD " + mapname);
        }
    }
    //Loading maps (MUST BE CALLED AFTER UNLOAD MAPS TO FINISH THE ROLLBACK PROCESS)
    public static void loadMap(String mapname){
        Bukkit.getServer().createWorld(new WorldCreator(mapname));
    }

    //Maprollback method, because were too lazy to type 2 lines
    public static void rollback(String mapname){
        unloadMap(mapname);
        loadMap(mapname);
    }

}
