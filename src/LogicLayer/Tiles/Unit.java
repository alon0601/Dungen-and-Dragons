package LogicLayer.Tiles;


import LogicLayer.*;

import java.util.Random;

public abstract class Unit extends Tile {
    // A singleton object for generating numbers - NumericGenerator is an interface, implemented by a RandomGenerator and a DeterministicGenerator
    protected static final Random r = new Random();
    protected MessageCallback messageCallback;
    protected Random random = new Random();
    protected String name;
    protected Health health;
    protected int attack;
    protected int defense;

    public String getName() {
        return name;
    }

    public Health getHealth() {
        return health;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    protected Unit(char tile, String name, int healthCapacity, int attack, int defense) {
        super(tile);
        this.name = name;
        this.health = new Health(healthCapacity, healthCapacity);
        this.attack = attack;
        this.defense = defense;
    }

    protected void initialize(Position position, MessageCallback messageCallback){
        super.initialize(position);
        this.messageCallback = messageCallback;
    }

    protected int attack(){
		int result = (int)(Math.random()*this.attack);
		messageCallback.send(String.format("%s rolled %d attack points",getName(),result));
		return  result;
    }

    public int defend(){
        int result = (int)(Math.random()*this.defense);
        messageCallback.send(String.format("%s rolled %d defence points",getName(),result));
        return  result;
    }

    // Should be automatically called once the unit finishes its turn
    public abstract void processStep();

    // What happens when the unit dies
    public abstract void onDeath();

    public void interact(Tile tile){
		tile.accept(this);
    }
    protected void swapPosition(Tile t){
        this.position.swapPosition(t.position);
    }
    public void visit(Wall w){
    }
    public void visit(Empty e){
        swapPosition(e);
    }
    public abstract void visit(Player p);
    public abstract void visit(Enemy e);

    protected void battle(Unit u){
        messageCallback.send(String.format("%s engaged in combat with %s. \n%s\n%s",getName(),u.getName(),defense,u.describe()));
        int damageDone = Math.max(attack() - u.defend(),0);
        u.health.reduceHealth(damageDone);
        messageCallback.send(String.format("%s dealt %d damage to %s.",getName(),damageDone,u.getName()));
    }
    public boolean alive(){
        return getHealth().getHealthAmount() > 0;
    }
    public String describe() {
        return String.format("%s\t\tHealth: %s\t\tAttack: %d\t\tDefense: %d", getName(), getHealth(), getAttack(), getDefense());
    }
}

