package LogicLayer.Players;


import LogicLayer.Enemies.*;
import LogicLayer.Tiles.*;
import LogicLayer.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Mage extends Player {
    protected int spellPower;
    protected int range;
    protected int manaPool;
    protected int manaCost;
    protected int currentMana;
    protected int countHits;
    public Mage(String name, int healthCapacity, int attack, int defense,int spellPower,int range,int manaPool,int manaCost,int countHits){
        super(name,healthCapacity,attack,defense);
        this.spellPower = spellPower;
        this.range = range;
        this.manaCost = manaCost;
        this.manaPool = manaPool;
        this.currentMana = this.manaPool/4;
        this.countHits = countHits;
    }

    @Override
    protected void levelUp() {
        this.manaPool = this.manaPool + 25*this.level;
        this.currentMana = Math.min(this.currentMana+this.manaPool/4,this.manaPool);
        this.spellPower = this.spellPower + 10*this.level;
        genericLevelUp();
    }

    @Override
    public void castAbility(List<Enemy> enemies) {
        if(this.currentMana < this.manaCost)
            messageCallback.send(getName() + "tried to cast ability but dont have enough mana");
        else {
            messageCallback.send(getName() + "used his special ability");
            this.currentMana = this.currentMana - this.manaCost;
            int hits = 0;
            Enemy e =GetRandomEnemy(GetInRangeEnemies(enemies,this.range));
            while (e != null && hits < this.countHits && this.position.range(e.getPosition()) < this.range)  {
                SpecialAbility(e);
                hits++;
                e =GetRandomEnemy(GetInRangeEnemies(enemies,range));
            }
        }
    }

    protected void SpecialAbility(Enemy enemy) {
        messageCallback.send(String.format("%s engaged in combat with %s. \n%s\n%s",getName(),enemy.getName(),defense,enemy.describe()));
        int defence = enemy.defend();
        int damage = this.spellPower - defence;
        enemy.getHealth().reduceHealth(damage);
        messageCallback.send(String.format("%s dealt %d damage to %s.",getName(),damage,enemy.getName()));
        if (!enemy.alive()) {
            this.onKill(enemy);
        }

    }

    @Override
    public void onGameTick() {
        this.currentMana = Math.min(manaPool, currentMana + getLevel());
    }
    public String  describe() {
        //  Mana: 48/150            Spell Power: 20
        return super.describe() + " Mana: "+currentMana+"/"+manaPool + " Spell Power: " + spellPower;
    }

    @Override
    public void processStep() {
        this.currentMana = Math.min(this.manaPool,this.currentMana+this.level);
    }
}
