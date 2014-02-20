package me.masterejay.pacman;

import com.sk89q.bukkit.util.CommandsManagerRegistration;
import com.sk89q.minecraft.util.commands.CommandException;
import com.sk89q.minecraft.util.commands.CommandPermissionsException;
import com.sk89q.minecraft.util.commands.CommandUsageException;
import com.sk89q.minecraft.util.commands.CommandsManager;
import com.sk89q.minecraft.util.commands.MissingNestedCommandException;
import com.sk89q.minecraft.util.commands.WrappedCommandException;
import me.masterejay.pacman.commands.AdminCommands;
import me.masterejay.pacman.commands.JoinCommand;
import me.masterejay.pacman.listeners.BlockListener;
import me.masterejay.pacman.listeners.DamageListener;
import me.masterejay.pacman.listeners.JoinListener;
import me.masterejay.pacman.match.MatchState;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

/**
 * @author MasterEjay
 */
public class Pacman extends JavaPlugin{

    private CommandsManager<CommandSender> commands;
    private static Pacman plugin = null;
    static World map = null;
    private static MatchState state = MatchState.FINISHED;

    @Override
    public void onEnable(){
        getLogger().info("Pacman is starting up!");
        if (!new File(getDataFolder(), "config.yml").exists()) {
            saveDefaultConfig();
        }
        findMap();
        parseConfig();
        setupCommands();
        getServer().getPluginManager().registerEvents(new JoinListener(), this);
        getServer().getPluginManager().registerEvents(new BlockListener(), this);
        getServer().getPluginManager().registerEvents(new DamageListener(), this);
        plugin = this;

    }

    public static Pacman get(){
        return plugin;
    }

    public static MatchState getState(){
        return state;
    }

    public static void setState(MatchState matchState){
        state = matchState;
    }

    private void setupCommands() {
        this.commands = new CommandsManager<CommandSender>() {
            @Override
            public boolean hasPermission(CommandSender sender, String perm) {
                return sender instanceof ConsoleCommandSender || sender.hasPermission(perm);
            }
        };
        CommandsManagerRegistration cmdRegister = new CommandsManagerRegistration(this, this.commands);
        cmdRegister.register(JoinCommand.class);
        cmdRegister.register(AdminCommands.class);
    }

    private void findMap(){
        map = Bukkit.getServer().createWorld(new WorldCreator("Map"));
    }
    public static World getMap(){
        return map;
    }

    private void parseConfig(){
        getLogger().info("PARSING CONFIG!!!!");
        String[] observerSpawn = getConfig().getString("observer").split(",");
        Location loc = new Location(getMap(), Integer.parseInt(observerSpawn[0]), Integer.parseInt(observerSpawn[1]),
                Integer.parseInt(observerSpawn[2]));
        Values.setObserverSpawn(loc);
        String[] pacmanSpawn = getConfig().getString("pacman").split(",");
        Location loc1 = new Location(getMap(), Integer.parseInt(pacmanSpawn[0]), Integer.parseInt(pacmanSpawn[1]),
                Integer.parseInt(pacmanSpawn[2]));
        Values.setPacmanSpawn(loc1);
        String[] ghostSpawn = getConfig().getString("ghost").split(",");
        Location loc2 = new Location(getMap(), Integer.parseInt(ghostSpawn[0]), Integer.parseInt(ghostSpawn[1]),
                Integer.parseInt(ghostSpawn[2]));
        Values.setGhostSpawn(loc2);
        int rawBlock = getConfig().getInt("blockfrom");
       Values.setOriginBlock(Material.getMaterial(rawBlock));
        int rawBlockTo = getConfig().getInt("blockto");
	    Values.setNewBlock(Material.getMaterial(rawBlock));
        int ghostBlock = getConfig().getInt("ghostblock");
	    Values.setGhostBlock(Material.getMaterial(ghostBlock));
        int points = getConfig().getInt("points");
        Values.setPointsToWin(points);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        try {
            this.commands.execute(cmd.getName(), args, sender, sender);
        } catch (CommandPermissionsException e) {
            sender.sendMessage(ChatColor.RED + "You don't have permission.");
        } catch (MissingNestedCommandException e) {
            sender.sendMessage(ChatColor.RED + e.getUsage());
        } catch (CommandUsageException e) {
            sender.sendMessage(ChatColor.RED + e.getMessage());
            sender.sendMessage(ChatColor.RED + e.getUsage());
        } catch (WrappedCommandException e) {
            if (e.getCause() instanceof NumberFormatException) {
                sender.sendMessage(ChatColor.RED + "Number expected, string received instead.");
            } else {
                sender.sendMessage(ChatColor.RED + "An error has occurred. See console.");
                e.printStackTrace();
            }
        } catch (CommandException e) {
            sender.sendMessage(ChatColor.RED + e.getMessage());
        }

        return true;
    }
}
