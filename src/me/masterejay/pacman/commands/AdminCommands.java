package me.masterejay.pacman.commands;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandException;
import com.sk89q.minecraft.util.commands.CommandPermissions;
import me.masterejay.pacman.Pacman;
import me.masterejay.pacman.countdowns.CountdownMethods;
import me.masterejay.pacman.countdowns.MatchStartCountdown;
import me.masterejay.pacman.match.MatchHandler;
import me.masterejay.pacman.match.MatchState;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 * @author MasterEjay
 */
public class AdminCommands {

    @Command(aliases = { "start" }, desc = "Starts the game", usage = "", min = 0, max = 1)
    @CommandPermissions("admin.game.start")
    public static void start(final CommandContext args, CommandSender sender) throws CommandException {
        if (args.argsLength() == 0){
            Pacman.setState(MatchState.STARTING);
            CountdownMethods.start(new MatchStartCountdown(), 15);
        }
        else if (args.argsLength() == 1){
            Pacman.setState(MatchState.STARTING);
            CountdownMethods.start(new MatchStartCountdown(), args.getInteger(0));
        }
    }

    @Command(aliases = { "finish" }, desc = "Starts the game", usage = "", min = 1, max = 1)
    @CommandPermissions("admin.game.finish")
    public static void finish(final CommandContext args, CommandSender sender) throws CommandException {
      if (Pacman.getState() == MatchState.PLAYING){
          if (args.getString(0).equalsIgnoreCase("g")){
            MatchHandler.finish(false);
          }
          else if (args.getString(0).equalsIgnoreCase("p")){
              MatchHandler.finish(true);
          }
          else {
              throw new CommandException("Thats not a team!");
          }

      }
        else {
          throw new CommandException("You can't end the match at this time!");
      }
    }

    @Command(aliases = { "cancel", "c"}, desc = "Cancels any countdowns", usage = "")
    @CommandPermissions("admin.game.cancel")
    public static void cancel(final CommandContext args, CommandSender sender) throws CommandException {
       CountdownMethods.cancelAll();
        sender.sendMessage(ChatColor.GOLD + "All countdowns cancelled");
    }
}
