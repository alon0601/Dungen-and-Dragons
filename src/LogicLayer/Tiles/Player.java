package LogicLayer.Tiles;

import LogicLayer.MessageCallback;
import LogicLayer.PlayerDeathCallback;
import LogicLayer.Tiles.Enemy;
import LogicLayer.Tiles.Position;
import LogicLayer.Tiles.Unit;

import java.util.ArrayList;
import java.util.List;
public abstract class Player extends Unit {
    public static final char playerTile = '@';
    protected static final int REQ_EXP = 50;
    protected static final int ATTACK_BONUS = 4;
    protected static final int DEFENSE_BONUS = 1;
    protected static final int HEALTH_BONUS = 10;

    protected int level;
    protected int experience;

    protected PlayerDeathCallback deathCallback;

    protected Player(String name, int healthCapacity, int attack, int defense) {
        super(playerTile, name, healthCapacity, attack, defense);
        this.level = 1;
        this.experience = 0;
    }

    public Player initialize(Position position, MessageCallback messageCallback, PlayerDeathCallback deathCallback){
        super.initialize(position, messageCallback);
        this.deathCallback = deathCallback;
        return this;
    }

    public void accept(Unit u){
        u.visit(this);
    }

    public void visit(Enemy e){
        super.battle(e);
        if(!e.alive()) {
            swapPosition(e);
            onKill(e);
        }
    }

    protected void swapPosition(Enemy e){
        position.swapPosition(e.position);
    }


    // Deals damage to the enemy with ability
    protected void abilityDamage(Enemy e, int abilityDamage) {
		int damageDone = Math.max(abilityDamage-e.defense,0);
		e.health.reduceHealth(damageDone);
		messageCallback.send(String.format("%s hits %s for %d ability damage.",getName(),e.getName(),damageDone));
    }

    // When the player kills an enemy
    protected void onKill(Enemy e){
		int exp = e.xp;
		messageCallback.send(String.format("%s died. %s gained %d xp.",e.getName(),getName(),exp));
		addXP(exp);
		e.onDeath();
    }

    protected void addXP(int exp){
        this.experience = this.experience + exp;
        int nextLevelRequirement = levelUpRequirement();
        while(this.experience >= nextLevelRequirement) {
            levelUp();
            this.experience = this.experience - nextLevelRequirement;
            nextLevelRequirement = levelUpRequirement();
        }
    }

    // When the player dies
    @Override
    public void onDeath() {
        messageCallback.send("You lost.");
        // Use deathCallback to alert the level manager
        deathCallback.onDeath();
    }
    protected abstract void levelUp();
    // Player level up
    protected void genericLevelUp(){
        level++;
        int healthGained = gainHealth();
        int attackGained = gainAttack();
        int defenceGained = gainDefense();
        health.addCapacity(healthGained);
        health.restore();
        this.attack += attackGained;
        this.defense += defenceGained;
        messageCallback.send(String.format("%s reached level %d: +%d Health, +%d attack , +%d defence",getName(),getLevel(),healthGained,attackGained,defenceGained));
    }



    // Health / attack / defense gain when the player levels up - should be overriden by player subclasses and call super.gainHealth() for the base amount, then add the extra value
    protected int gainHealth(){
        return level * HEALTH_BONUS;
    }
    protected int gainAttack(){
        return level * ATTACK_BONUS;
    }
    protected int gainDefense(){
        return level * DEFENSE_BONUS;
    }

    private int levelUpRequirement(){
        return REQ_EXP * level;
    }

    public int getLevel() {
        return level;
    }
    public int getExperience() {
        return experience;
    }

    public void visit(Player p){

    }

    public String describe() {
        return String.format("%s\t\tLevel: %d\t\tExperience: %d/%d", super.describe(), getLevel(), getExperience(), levelUpRequirement());
    }
    protected Enemy GetRandomEnemy(List<Enemy> enemies) {
        if (enemies.size() > 0)
            return enemies.get(random.nextInt(enemies.size())); // get enemy at random index
        return null; // given empty enemy list
    }

    protected List<Enemy> GetInRangeEnemies(List<Enemy> enemies, double range){
        List<Enemy> inRange = new ArrayList<>();
        for (Enemy enemy : enemies){
            if (RangeFrom(enemy) < range) // in range
                inRange.add(enemy);
        }
        return inRange;
    }

    protected Enemy ClosestEnemy(List<Enemy> enemies){
        if (enemies.isEmpty())
            return null;
        Enemy closest = enemies.get(0);
        for (Enemy enemy : enemies){
            if (RangeFrom(enemy) < RangeFrom(closest))
                closest = enemy;
        }
        return closest;
    }
    public abstract void onGameTick();

    public void setDeathCallback(PlayerDeathCallback pdcb) {
        this.deathCallback = pdcb;
    }
    public abstract void castAbility(List<Enemy> enemies);
    public void setMessageCallback(MessageCallback messageCallback) {
        this.messageCallback = messageCallback;
    }

    public void setPlayerTile(char x) {
        this.tile = x;
    }
}
