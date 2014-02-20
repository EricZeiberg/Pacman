package me.masterejay.pacman.teams;

/**
 * @author MasterEjay
 */
public class TeamHandler {

    private static Observers observers = new Observers();
    private static PacmanPlayer pacman = new PacmanPlayer();
    private static Ghosts ghosts = new Ghosts();

    public static Observers getObservers(){
        return observers;
    }

    public static PacmanPlayer getPacmanPlayer(){
        return pacman;
    }

    public static Ghosts getGhosts() {
        return ghosts;
    }
}
