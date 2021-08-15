package LogicLayer.Players;


import LogicLayer.Tiles.Enemy;
import LogicLayer.Tiles.Player;

import java.util.List;

public class Rogue extends Player {
    private final int DEFAULT_ENERGY = 100;
    protected int cost;
    protected int currentEnergy;
    public Rogue(String name, int healthCapacity, int attack, int defense, int cost) {
        super(name, healthCapacity, attack, defense);
        this.cost = cost;
        this.currentEnergy = DEFAULT_ENERGY;
    }

    @Override
    protected void levelUp() {
        genericLevelUp();
        this.currentEnergy = DEFAULT_ENERGY;
        this.attack = this.attack + 3*this.level;
    }

    @Override
    public void onGameTick() {
        this.currentEnergy = Math.min(this.currentEnergy + 10 , DEFAULT_ENERGY);
    }

    @Override
    public void castAbility(List<Enemy> enemies) {
        if (this.currentEnergy < cost) {
            // Arya Stark tried to cast Fan of Knives, but there was not enough energy: 10/20.
            messageCallback.send(String.format("%s dont have enough energy, current energy/needed %d/%d",getName(),this.currentEnergy,cost));
        }
        else {
            this.currentEnergy = this.currentEnergy - cost;
            messageCallback.send(String.format("%s activated special ability",getName()));

            List<Enemy> inRange = GetInRangeEnemies(enemies, 2); // get all enemies within range of 2
            for (Enemy enemy : inRange) {
                SpecialAbility(enemy);
            }
        }
    }

    protected void SpecialAbility(Enemy enemy) {
        messageCallback.send(String.format("%s engaged in combat with %s. \n%s\n%s",getName(),enemy.getName(),defense,enemy.describe()));
        int defence = enemy.defend();
        int damage = this.attack - defence;
        enemy.getHealth().reduceHealth(damage);
        messageCallback.send(String.format("%s dealt %d damage to %s.",getName(),damage,enemy.getName()));
        if(!enemy.alive()){
            this.onKill(enemy);
        }
    }

    @Override
    public void processStep() {
        this.currentEnergy = Math.min(this.currentEnergy + 10,100);
    }
}
