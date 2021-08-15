package GUI;

import java.util.List;

import LogicLayer.*;
import LogicLayer.Tiles.*;

public class GameController {

    private final int FIRST_LEVEL = 0;
    private List<GameBoard> gameBoards;
    private boolean win = false;
    private List<Position> startPositions;
    private boolean isPlayerAlive = true;
    private boolean isNextLevel = false;
    private int currentLevel = FIRST_LEVEL;
    public GameController(List<GameBoard> gameBoards,List<Position> startPositions){
        this.startPositions = startPositions;
        this.gameBoards = gameBoards;
    }

    private void removeEnemy(Enemy e){
        isNextLevel = gameBoards.get(currentLevel).RemoveEnemy(e); // returns true if level is over (all enemies are dead)

    }
    private void onPlayerDeaths(Player p) {
        p.setPlayerTile('X');
        isPlayerAlive = false;
    }
    private Level LevelSetup(int thisLevel){
        if (thisLevel < gameBoards.size()){ // there is such level
            GameBoard gameBoard = gameBoards.get(thisLevel);
            Player player = gameBoard.getPlayer();
            player.initialize(startPositions.get(thisLevel));
            player.setDeathCallback(()->onPlayerDeaths(player)); // set the player death call back
            player.setMessageCallback(System.out::println); // set the player message call back
            for (Enemy enemy : gameBoard.getEnemies()){ // set for each enemy the message call back and death call back
                enemy.setDeathCallback(()->removeEnemy(enemy));
                enemy.setMessageCallback(System.out::println);
            }
            Level currentLevel = new Level(gameBoard.getTiles(),player,gameBoard.getEnemies());
            return currentLevel;
        }
        // else - no more levels
        win = true;
        return null;
    }

    void Play(int thisLevel) {
        Level playedLevel = LevelSetup(thisLevel);
        GameBoard currentLevel = gameBoards.get(this.currentLevel);
        System.out.println("Level " + (thisLevel+1) + " start");
        System.out.println(currentLevel);
        System.out.println(currentLevel.getPlayer().describe());
        while (!win && playedLevel != null && isPlayerAlive){
            playedLevel.onPlayerTurn();
            playedLevel.onEnemyTurn();
            if (this.isNextLevel){
                this.currentLevel++;
                this.isNextLevel = false;
                Play(this.currentLevel);
                currentLevel = gameBoards.get(this.currentLevel);
            }
            System.out.println(currentLevel);
            System.out.println(currentLevel.getPlayer().describe());
        }
        if (!isPlayerAlive) {
            System.out.println("You lost game over.");
        }
        else {
            System.out.println("You won");
        }
        System.exit(0);
    }
}


