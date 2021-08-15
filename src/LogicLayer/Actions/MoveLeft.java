package LogicLayer.Actions;

import LogicLayer.Level;
import LogicLayer.Tiles.Position;
import LogicLayer.Tiles.Tile;
import LogicLayer.Tiles.Unit;

public class MoveLeft implements Action {
    private Unit actedUnit;

    public MoveLeft(Unit actedUnit) {
        this.actedUnit = actedUnit;
    }

    public void act(Level board) {
        Position destination = new Position(actedUnit.getPosition().x , actedUnit.getPosition().y-1);
        Tile interactedTile = board.getTile(destination);
        actedUnit.interact(interactedTile);
    }
}
