package me.masterejay.pacman;

import me.masterejay.pacman.teams.TeamHandler;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

/**
 * @author MasterEjay
 */
public class Values {

    private static Location observerSpawn = null;
    private static Location pacmanSpawn = null;
    private static Location ghostSpawn = null;
    private static Material originBlock = null;
    private static Material newBlock = null;
    private static int points = 0;
    private static int pointsToWin = 0;
    private static int livesLeft = 5;
    private static boolean isGhostMode = false;
    private static Material ghostBlock = null;

    public static int getPoints(){
        return points;
    }

    public static void addPoints(int point){
        points = points + point;
    }

    public static Location getObserverSpawn() {
        return observerSpawn;
    }

    public static Location getPacmanSpawn() {
        return pacmanSpawn;
    }

    public static Location getGhostSpawn() {
        return ghostSpawn;
    }

    public static Material getOriginBlock(){
        return originBlock;
    }

    public static Material getNewBlock(){
        return newBlock;
    }

    public static void setNewBlock(Material material){
        newBlock = material;
    }

    public static void setOriginBlock(Material material){
        originBlock = material;
    }

    public static void setObserverSpawn(Location location){
       observerSpawn = location;
    }

    public static void setPacmanSpawn(Location location){
        pacmanSpawn = location;
    }

    public static void setGhostSpawn(Location location){
        ghostSpawn = location;
    }

    public static int getPointsToWin() {
        return pointsToWin;
    }

    public static void setPointsToWin(int pointsToWin) {
        Values.pointsToWin = pointsToWin;
    }

    public static int getLivesLeft() {
        return livesLeft;
    }

    public static void setLivesLeft(int livesLeft) {
        Values.livesLeft = livesLeft;
        TeamHandler.getPacmanPlayer().getPacman().sendMessage(ChatColor.RED + "You have lost a life! Be careful, you only have " +
                ChatColor.DARK_RED + Values.getLivesLeft() + " lives left");
    }

    public static boolean isGhostMode() {
        return isGhostMode;
    }

    public static void setGhostMode(boolean ghostMode) {
        isGhostMode = ghostMode;
    }

    public static Material getGhostBlock() {
        return ghostBlock;
    }

    public static void setGhostBlock(Material ghostBlock) {
        Values.ghostBlock = ghostBlock;
    }
}
