package LogicLayer.Tiles;

import LogicLayer.*;

public class Wall extends Tile {
    public static final char wallTile = '#';
    public Wall(Position position){
        super(wallTile);
        this.initialize(position);
    }


    @Override
    public void accept(Unit unit) {

    }

}
