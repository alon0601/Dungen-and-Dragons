package LogicLayer.Tiles;

import LogicLayer.*;

public abstract class Tile implements Comparable<Tile> {
    protected char tile;
    protected Position position;
    protected double RangeFrom(Tile tile) {
        return this.position.range(tile.position);
    }
    protected Tile(char tile){
        this.tile = tile;
    }

    public void initialize(Position position){
        this.position = position;
    }

    public char getTile() {
        return tile;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public abstract void accept(Unit unit);

    @Override
    public int compareTo(Tile tile) {
        return tile.getPosition().compareTo(getPosition());
    }

    @Override
    public String toString() {
        return String.valueOf(tile);
    }
}