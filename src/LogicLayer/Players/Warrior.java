package LogicLayer.Players;

import LogicLayer.Tiles.Enemy;
import LogicLayer.Tiles.Player;
import java.util.List;

public class Warrior extends Player {
    protected int coolDown;
    protected int remainingCD;
    public Warrior(String name, int healthCapacity, int attack, int defense, int coolDown) {
        super(name, healthCapacity, attack, defense);
        this.coolDown = coolDown;
        this.remainingCD = coolDown;
    }
    public void levelUp(){
        genericLevelUp();
        this.remainingCD = 0;
        this.health.addCapacity(5*this.level);
        this.attack = this.attack + 2*this.level;
        this.defense = this.defense + this.level;
    }

    @Override
    public void onGameTick() {
        if(this.remainingCD > 0)
            this.remainingCD--;
    }

    @Override
    public void castAbility(List<Enemy> enemies) {
        if (this.remainingCD > 0){
            // Jon Snow tried to cast Avenger's Shield, but there is a cooldown: 2.
            messageCallback.send(String.format("%s you steal have coolDown for that ability, current cool down %d",getName(),this.remainingCD));
        }
        else {
            this.remainingCD = this.coolDown+1;
            //int beforeHealing = health_amount;
            this.health.setHealthAmount(Math.min(health.getHealthPool(),health.getHealthAmount()+(10*defense)));
            messageCallback.send(String.format("%s activated special ability",getName()));
            List<Enemy> inRange = GetInRangeEnemies(enemies,3); // get enemies within range of 3
            Enemy enemy = GetRandomEnemy(inRange);
            if (enemy != null) { // there's random enemy in valid range
                SpecialAbility(enemy);
            }
        }
    }

    protected void SpecialAbility(Enemy e) {
        messageCallback.send(String.format("%s engaged in combat with %s. \n%s\n%s",getName(),e.getName(),defense,e.describe()));
        e.getHealth().reduceHealth((int)0.1*this.health.getHealthPool());
        messageCallback.send(String.format("%s dealt %d damage to %s.",getName(),(int)0.1*this.health.getHealthPool(),e.getName()));
        if(!e.alive())
            this.onKill(e);
    }


    @Override
    public void processStep() {
        this.remainingCD = this.remainingCD - 1;
    }
}
