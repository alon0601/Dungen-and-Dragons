package GUI;
import LogicLayer.Tiles.*;
import LogicLayer.Enemies.Monster;
import LogicLayer.Enemies.Trap;
import LogicLayer.Players.Mage;
import LogicLayer.Players.*;
import LogicLayer.Players.Warrior;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.function.Supplier;

public class LevelParser {
    private Map<Character, Supplier<Enemy>> enemiesMap = new HashMap<>();
    private ArrayList<Player> playersMap = new ArrayList<>();
    private List<Position> startP = new LinkedList<>();
    LevelParser() {
        InitPlayersMap();
        InitEnemiesMap();
    }

    public Player ChoosePlayer() {
        Player player = null;
        System.out.println("Select Player:");
        StringBuilder PlayerMenu = new StringBuilder();
        for (int i = 0; i < playersMap.size(); i++) {
            PlayerMenu.append(i + 1).append(". ").append(playersMap.get(i).describe()).append("\n");
        }
        Scanner scanner = new Scanner(System.in);
        boolean notValidInput = true;
        while (notValidInput) {
            try {
                System.out.print(PlayerMenu);
                int input = Integer.parseInt(scanner.nextLine());
                if (input > 0 & input <= playersMap.size()) {
                    player = playersMap.get(input - 1);
                    System.out.println("You have selected:");
                    System.out.println(player.getName());
                    break;
                } else
                    System.out.print(PlayerMenu);
            } catch (Exception e) {
                System.out.println("Not a number.");
            }
        }
        return player;
    }

    private void InitPlayersMap() {
        //Warriors
        playersMap.add(new Warrior("Jon Snow", 300, 30, 4, 3));
        playersMap.add( new Warrior("The Hound", 400, 20, 6, 5));
        //Mages
        playersMap.add(new Mage("Melisandre", 100, 5, 1, 15, 6, 300, 30, 5));
        playersMap.add(new Mage("Thoros of Myr", 250, 25, 4, 20, 4, 150, 20, 3));
        //Rogues
        playersMap.add(new Rogue("Arya Stark", 150, 40, 2, 20));
        playersMap.add(new Rogue("Bronn", 250, 35, 3, 50));
    }

    private void InitEnemiesMap() {
        // Monsters
        enemiesMap.put('s',()-> new Monster('s', "Lannister Solider", 80, 8, 3, 3, 25));
        enemiesMap.put('k',()-> new Monster('k', "Lannister knight", 200, 14, 8, 4, 50));
        enemiesMap.put('q',()->new Monster('q', "Queen's Guard", 400, 20, 15, 5, 100));
        enemiesMap.put('z',()->new Monster('z', "Wright", 600, 30, 15, 3, 100));
        enemiesMap.put('b',()->new Monster('b', "Bear-Wright", 1000, 75, 30, 4, 250));
        enemiesMap.put('g',()->new Monster('g', "Giant-Wright", 1500, 100, 40, 5, 500));
        enemiesMap.put('w',()->new Monster('w', "White Walker", 2000, 150, 50, 6, 1000));
        enemiesMap.put('M',()->new Monster('M', "The mountain", 1000, 60, 25, 6, 500));
        enemiesMap.put('C',()-> new Monster('C', "Queen Cersei", 100, 10, 10, 1, 1000));
        enemiesMap.put('K', ()->new Monster('K', "Night's King", 5000, 300, 150, 8, 5000));
        // Traps
        enemiesMap.put('B',()->new Trap('B', "Bonus Trap", 1, 1, 1, 1, 5, 250));
        enemiesMap.put('Q',()->new Trap('Q', "Queen's Trap", 250, 50, 10, 3, 7, 100));
        enemiesMap.put('D',()->new Trap('D', "Bonus Trap", 500, 100, 20, 1, 10, 250));
    }

    public char[][] levelBoard(String levelFile) {
        char[][] boardLevel = new char[0][1];
        try {
            BufferedReader br = new BufferedReader(
                    new FileReader(levelFile));
            String line = br.readLine();
            int columns = line.length();
            int row = 0;
            while (line != null) {
                row++;
                line = br.readLine();
            }
            boardLevel = new char[row][columns];
            BufferedReader br2 = new BufferedReader(
                    new FileReader(levelFile));
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < columns; j++) {
                    char c = (char) br2.read();
                    boardLevel[i][j] = c;
                }
                br2.read();
                br2.read();
            }
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return boardLevel;
    }

    public GameBoard initialize(char[][] gameLevel, Player chosenPlayer,int times) {
        List<Enemy> enemies = new ArrayList<>();
        GameBoard gameBoard = new GameBoard(gameLevel[0].length, gameLevel.length, chosenPlayer,enemies);
        for (int i = 0; i < gameLevel.length; i++) {
            for (int j = 0; j < gameLevel[0].length; j++) {
                Position position = new Position(i, j);
                if (gameLevel[i][j] == '#')
                    gameBoard.add(new Wall(position));
                else if (gameLevel[i][j] == '.')
                    gameBoard.add(new Empty(position));
                else if (gameLevel[i][j] == '@') {
                    if(times == 0)
                        chosenPlayer.initialize(position);
                    gameBoard.add(chosenPlayer);
                    startP.add(position);
                }
                else {
                    try {
                        Enemy enemy = this.enemiesMap.get(gameLevel[i][j]).get();
                        enemy.setPosition(position);
                        gameBoard.add(enemy);
                        enemies.add(enemy);
                    } catch (Exception e) {
                        System.out.println("level map has undefined chars" + e.getMessage());
                        System.exit(-1);
                    }
                }
            }
        }
        return gameBoard;
    }

    public List<Position> getStartPositions() {
        return this.startP;
    }
}
