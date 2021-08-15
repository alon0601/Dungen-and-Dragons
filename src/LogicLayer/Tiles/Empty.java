package LogicLayer.Tiles;

import LogicLayer.Tiles.*;
public class Empty extends Tile {
    public Empty(Position position){
        super('.');
        this.initialize(position);
    }

    @Override
    public void accept(Unit unit) {
        this.position.swapPosition(unit.getPosition());
    }

}
