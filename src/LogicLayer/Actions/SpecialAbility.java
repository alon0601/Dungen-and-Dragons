package LogicLayer.Actions;

import LogicLayer.Level;
import LogicLayer.Tiles.Player;
import LogicLayer.Tiles.Unit;

public class SpecialAbility implements Action{
    private Player acted;
    public SpecialAbility(Player actedUnit){
        this.acted = actedUnit;
    }

    @Override
    public void act(Level level) {
        this.acted.castAbility(level.getEnemies());
    }
}
