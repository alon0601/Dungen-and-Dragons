package LogicLayer.Tiles;

public class Position implements Comparable<Position> {
    public int x;
    public int y;
     public Position(int x,int y){
         this.x = x;
         this.y = y;
     }
    public int compareTo(Position position){
         if(x == position.x && y == position.y)
             return 0;
         else if(x == position.x && y > position.y)
             return 1;
         else if(x > position.x)
             return 1;
         else
             return -1;

    }
    public void swapPosition(Position position){
         int x = this.x;
         int y = this.y;
         this.y = position.y;
         this.x = position.x;
         position.x = x;
         position.y = y;
    }
    public String toString(){
         return "x" + this.x + "y" + this.y;
    }

    public double range(Position position) {
         return Math.sqrt(Math.pow((this.x - position.x),2)+Math.pow((this.y- position.y),2));
    }
}
