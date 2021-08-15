package LogicLayer.Tiles;

import java.util.Comparator;

public class TilesPositionComperator implements Comparator<Tile> {
    @Override
    public int compare(Tile o1, Tile o2) {
        if(o1.position.x != o2.position.x)
            return Integer.compare(o1.position.x, o2.position.x);
        return Integer.compare(o1.position.y, o2.position.y);
    }
}
