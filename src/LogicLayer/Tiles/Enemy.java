package LogicLayer.Tiles;

import LogicLayer.*;
import LogicLayer.Actions.Action;

public abstract class Enemy extends Unit {
    protected int xp;
    protected EnemyDeathCallback edc;
    protected Enemy(char tile, String name, int healthCapacity, int attack, int defense, int xp) {
        super(tile, name, healthCapacity, attack, defense);
        this.xp = xp;
    }

    public String describe() {
        return String.format("%s\t\tExperience: %d", super.describe(), this.xp);
    }
    @Override
    public void accept(Unit u){
        u.visit(this);
    }
    @Override
    public void processStep() {

    }

    @Override
    public void onDeath() {
        messageCallback.send(String.format("you killed %s good jod.",getName()));
        edc.onDeath();
    }

    @Override
    public void visit(Player p) {
        super.battle(p);
        if(!p.alive()) {
            p.onDeath();
        }
    }

    @Override
    public void visit(Enemy e) {
    }

    @Override
    public abstract String toString();


    public abstract Action EnemyTurn(Player player);

    public void setDeathCallback(EnemyDeathCallback edcb) {
        this.edc = edcb;
    }

    public void setMessageCallback(MessageCallback messageCallback) {
        this.messageCallback = messageCallback;
    }
}
