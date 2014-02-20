package me.masterejay.pacman.commands;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandException;
import com.sk89q.minecraft.util.commands.CommandPermissionsException;
import me.masterejay.pacman.Pacman;
import me.masterejay.pacman.match.MatchState;
import me.masterejay.pacman.teams.TeamHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

/**
 * @author MasterEjay
 */
public class JoinCommand {

    @Command(aliases = { "join", "j", "p" }, desc = "Joins the game", usage = "", min = 0, max = 1)
    public static void join(final CommandContext args, CommandSender sender) throws CommandException {
        if (sender instanceof ConsoleCommandSender) {
            throw new CommandException("Consoles can't use this command!");
        }
        else if (Pacman.getState() == MatchState.PLAYING ) {
            throw new CommandException("You can't join at this time!");
        }
       if (args.argsLength() == 1){
           if (sender.hasPermission("admin.pickteam")){
             if (args.getString(0).equalsIgnoreCase("g")){
               Player player = (Player) sender;
                 TeamHandler.getObservers().removeObserver(player);
                 TeamHandler.getGhosts().addPlaying(player);
             }
             else if (args.getString(0).equalsIgnoreCase("p")){
                 Player player = (Player) sender;
                 TeamHandler.getPacmanPlayer().clearPacman();
                 TeamHandler.getObservers().removeObserver(player);
                 TeamHandler.getPacmanPlayer().setPacman(player);
                 Bukkit.broadcastMessage(ChatColor.GOLD + ((Player) sender).getDisplayName() + " has overridden the existing pacman!");
             }
             else {
                 throw new CommandException("Thats not a team!");
             }
           }
           else {
               throw new CommandPermissionsException();
           }
       }
    }
}
