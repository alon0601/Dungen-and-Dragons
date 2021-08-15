package LogicLayer.Tiles;

import LogicLayer.Enemies.Monster;
import LogicLayer.Players.Mage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TilesPositionComperatorTest {

    private TilesPositionComperator comperator;
    private Unit u1;
    private Unit u2;
    private Unit u3;
    private Unit u4;
    private Unit u5;
    private Position position1;
    private Position position2;
    private Position position3;
    private Position position4;
    private Position position5;

    @BeforeEach
    void setUp() {
        this.comperator = new TilesPositionComperator();
        this.position1 = new Position(1, 2);
        this.position2 = new Position(3, 2);
        this.position3 = new Position(2, 7);
        this.position4 = new Position(5, 6);
        this.position5 = new Position(1, 5);
        this.u1 = new Mage("d", 3, 3, 2, 3, 4, 5, 6, 4);
        u1.initialize(position1);
        this.u2 = new Mage("s", 3, 3, 2, 3, 4, 5, 6, 5);
        u2.initialize(position2);
        this.u3 = new Monster('s', "s", 3, 3, 2, 3, 4);
        u3.initialize(position3);
        this.u4 = new Mage("s", 3, 3, 2, 3, 4, 5, 6, 5);
        u4.initialize(position4);
        this.u5 = new Mage("s", 3, 3, 2, 3, 4, 5, 6, 5);
        u5.initialize(position5);
    }

    @org.junit.jupiter.api.Test
    void compare() {
        int[] expectedValues = {-1, 0, 1, -1,1, 1, -1};
        int[] result = {this.comperator.compare(u1, u2), this.comperator.compare(u2, u2), this.comperator.compare(u2, u1)
                , this.comperator.compare(u3, u4), this.comperator.compare(u3, u5), this.comperator.compare(u4, u5),
                this.comperator.compare(u5, u4)};
        Assertions.assertArrayEquals(expectedValues, result);
    }
}


