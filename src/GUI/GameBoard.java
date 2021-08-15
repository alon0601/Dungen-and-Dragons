package GUI;

import java.util.*;

import LogicLayer.Tiles.*;

public class GameBoard {
    private List<Tile> tiles;
    private List<Enemy> enemies;
    private int numColumn;
    private int numRows;
    private final Comparator<Tile> cp = new TilesPositionComperator();
    protected Player p;
    private Comparator<Tile> comparator;
    public GameBoard(int numColumn,int numRows,Player ChosenPlayer,List<Enemy> enemies){
        this.tiles = new ArrayList<Tile>();
        this.enemies = enemies;
        this.numColumn = numColumn;
        this.numRows = numRows;
        this.p = ChosenPlayer;
        comparator = new TilesPositionComperator();
    }

    public Player getPlayer() {
        return this.p;
    }

    public String toString(){
        StringBuilder boardString = new StringBuilder();
        tiles.sort(this.cp);
        int i = 0;
        int j = 0;
        while (i<tiles.size()) {
            boardString.append(tiles.get(i));
            if(j == numColumn-1) {
                j = 0;
                boardString.append("\n");
            }
            else
                j++;
            i++;
        }
        return boardString.toString();
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public void add(Tile tile){
        this.tiles.add(tile);
    }

    public List<Enemy> getEnemies() {
        return this.enemies;
    }
    public void addEnemy(Enemy e){
        enemies.add(e);
    }

    public boolean RemoveEnemy(Enemy e) {
        int x = e.getPosition().x;
        int y = e.getPosition().y;
        tiles.remove(e);
        enemies.remove(e);
        tiles.add(new Empty(new Position(x,y)));
        return enemies.isEmpty();// return true if all enemies died
    }
}
