package LogicLayer;

import LogicLayer.Actions.*;
import LogicLayer.*;
import LogicLayer.Tiles.Enemy;
import LogicLayer.Tiles.Tile;
import LogicLayer.Tiles.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Level {
    private List<Tile> tiles;
    private Player player;
    private List<Enemy> enemies;


    private Map<Character,Action> PlayerActionsMap = new HashMap<>();
    private void InitActionsMap() {
        PlayerActionsMap.put('w',new MoveUp(player));
        PlayerActionsMap.put('s',new MoveDown(player));
        PlayerActionsMap.put('d',new MoveRight(player));
        PlayerActionsMap.put('a',new MoveLeft(player));
        PlayerActionsMap.put('e',new SpecialAbility(player));
    }

    public Level(List<Tile> tiles, Player player, List<Enemy> enemies){
        this.tiles = tiles;
        this.player = player;
        this.enemies = enemies;
        InitActionsMap();
    }


    public List<Enemy> getEnemies(){return enemies;}

    private void EnemiesTurn()  {
        for (Enemy enemy : enemies){
            Action action = enemy.EnemyTurn(player);
            action.act(this);
        }
    }
    public void onEnemyTurn(){
        EnemiesTurn();
    }
    public void onPlayerTurn(){
        GetPlayerAction().act(this);
        player.onGameTick();
    }

    private Action GetPlayerAction() {
        Scanner scanner = new Scanner(System.in);
        char input = 'a';
        boolean notValidChar = true;
        while (notValidChar) {
            try {
                input = scanner.next().charAt(0);
                if (this.PlayerActionsMap.containsKey(input))
                    notValidChar = false;
                else {
                    System.out.println("this char isn't in the action map");
                    System.out.println("please enter a-left, s-down, w-up,d-right,e-special ability");
                }
            }
            catch (Exception e){
                System.out.println("the string is to long please enter a char in the action map");
                System.out.println("please enter a-left, s-down, w-up,d-right,e-special ability");
            }
        }
        return this.PlayerActionsMap.get(input);
    }

    public Tile getTile(Position destination) {
        for(Tile tile : tiles) {
            if (tile.getPosition().compareTo(destination) == 0)
                return tile;
        }
        return null;
    }
}

