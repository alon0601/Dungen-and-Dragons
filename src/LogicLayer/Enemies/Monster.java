package LogicLayer.Enemies;


import LogicLayer.Actions.*;
import LogicLayer.Tiles.*;


public class Monster extends Enemy {
    protected int visionRange;

    public Monster(char tile, String name, int healthCapacity, int attack, int defense, int visionRange, int xp) {
        super(tile, name, healthCapacity, attack, defense,xp);
        this.visionRange = visionRange;
    }


    Action GetRandomMove(){
        int rand = random.nextInt(5 ); // 5 options, 4 directions and stay foot
        if (rand == 0)
            return new MoveDown(this);
        else if (rand == 1)
            return new MoveUp(this  );
        else if (rand == 2)
            return new MoveLeft(this);
        else if (rand == 3)
            return new MoveRight(this   );
        return new Nothing();
    }
    Action FollowPlayer(Player p) {
        int x = this.position.x - p.getPosition().x;
        int y = this.position.y - p.getPosition().y;
        if (Math.abs(x) > Math.abs(y)){
            if (x > 0)
                return new MoveUp(this);
            else
                return new MoveDown(this);
        }
        else {
            if (y > 0)
                return new MoveLeft(this);
            else
                return new MoveRight(this);
        }
    }
    @Override
    public Action EnemyTurn(Player player) {
        if (RangeFrom(player) < visionRange){
            return FollowPlayer(player);
        }
        else
            return GetRandomMove();
    }
    @Override
    public String describe() {
        return String.format("%s\t\tVision range: %d/", super.describe(), this.visionRange);
    }

    @Override
    public String toString() {
        return ""+this.tile;
    }
}
