package LogicLayer.Enemies;


import LogicLayer.Actions.*;
import LogicLayer.Tiles.*;

public class Trap extends Enemy {
    protected int visibilityT;
    protected int invisibilityT;
    protected int ticksCount;
    protected boolean visible;

    public Trap(char tile, String name, int healthCapacity, int attack, int defense, int visibilityT, int invisibilityT, int xp) {
        super(tile, name, healthCapacity, attack, defense, xp);
        this.invisibilityT = invisibilityT;
        this.ticksCount = 0;
        this.visibilityT = visibilityT;
        this.visible = true;
    }

    @Override
    public Action EnemyTurn(Player player) {
        this.visible = this.ticksCount < this.visibilityT;
        if (this.ticksCount == (this.visibilityT + this.invisibilityT))
            this.ticksCount = 0;
        else
            this.ticksCount++;
        if (RangeFrom(player) < 2)
            battle(player);
        return new Nothing();
    }

    @Override
    public String describe() {
        return super.describe();
    }

    @Override
    public String toString(){
        if(!visible)
            return ".";
        else
            return ""+this.tile;};
}


