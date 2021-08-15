package LogicLayer;

public class Health {
    protected int healthPool;
    protected int healthAmount;
    public Health(int healthPool,int healthAmount){
        this.healthAmount= healthAmount;
        this.healthPool = healthPool;
    }

    public int getHealthAmount() {
        return healthAmount;
    }
    public int getHealthPool(){
        return this.healthPool;
    }
    public void setHealthAmount(int healthAmount){
        this.healthAmount = healthAmount;
    }
    public String toString(){
        return String.format("health pool:%d     health amount:%d",this.healthPool,this.healthAmount);
    }

    public void reduceHealth(int healthToReduce){
        this.healthAmount = this.healthAmount - healthToReduce;
    }
    public void addCapacity(int healthPool){
        this.healthPool = this.healthPool + healthPool;
    }
    public void addCurrentHealth(int healthAmount){
        this.healthAmount = this.healthAmount+healthAmount;
    }

    public void restore() {
        this.healthAmount = healthPool;
    }
}
