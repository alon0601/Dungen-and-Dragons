package LogicLayer.Actions;

import LogicLayer.Level;
import LogicLayer.Tiles.Position;
import LogicLayer.Tiles.Tile;
import LogicLayer.Tiles.Unit;

public class MoveDown implements Action {
    private Unit actedUnit;

    public MoveDown(Unit actedUnit) {
        this.actedUnit = actedUnit;
    }

    public void act(Level board) {
        Position destination = new Position(actedUnit.getPosition().x+1, actedUnit.getPosition().y );
        Tile interactedTile = board.getTile(destination);
        actedUnit.interact(interactedTile);
    }
}
