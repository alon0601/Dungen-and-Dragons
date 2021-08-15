package GUI;

import java.util.ArrayList;
import java.util.List;

import GUI.GameController;
import LogicLayer.*;
import LogicLayer.Tiles.*;

public class GameRunner {
    public static void main(String[] args) {
        String levelsPath = args[0];
        List<char[][]> levelBoard = new ArrayList<>();
        LevelParser levelParser = new LevelParser();
        int i = 0;
        String path = levelsPath + "\\level" + (i+1) + ".txt";
        List<GameBoard> gameBoards = new ArrayList<>();
        Player p = levelParser.ChoosePlayer();
        levelBoard.add(levelParser.levelBoard(path));
        char[][] levelBoard1 = levelBoard.get(i);
        while (levelBoard1 != null) {
            gameBoards.add(levelParser.initialize(levelBoard.get(i),p,i));
            i++;
            path = levelsPath + "\\level" + (i+1) + ".txt";
            levelBoard.add(levelParser.levelBoard(path));
            levelBoard1 = levelBoard.get(i);
        }
        GameController gameManager = new GameController(gameBoards,levelParser.getStartPositions());
        gameManager.Play(0);

    }
}

