package LogicLayer.Actions;

import LogicLayer.Enemies.Monster;
import LogicLayer.Enemies.Trap;
import LogicLayer.EnemyDeathCallback;
import LogicLayer.Level;
import LogicLayer.PlayerDeathCallback;
import LogicLayer.Players.Mage;
import LogicLayer.Tiles.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.Before;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MoveLeftTest {
    private static TilesPositionComperator comperator;
    private static Player u1;
    private static Enemy u2;
    private static Enemy u3;
    private static Tile u4;
    private static Tile u5;
    private static Level level;
    private static Action act;
    private static Player p2;
    private static Player p3;
    private static Position position1;
    private static Position position2;
    private static Position position3;
    private static Position position4;
    private static Position position5;
    private static EnemyDeathCallback edc;
    private static PlayerDeathCallback pdc;

    @BeforeAll
    static void setUp() {
        comperator = new TilesPositionComperator();
        position1 = new Position(1, 2);
        position2 = new Position(1, 1);
        position3 = new Position(2, 9);
        position4 = new Position(2, 6);
        position5 = new Position(3,4);
        edc = new EnemyDeathCallback() {
            @Override
            public void onDeath() {

            }
        };
        pdc = new PlayerDeathCallback() {
            @Override
            public void onDeath() {

            }
        };
        u1 = new Mage("d", 3, 3, 2, 3, 4, 5, 6, 4);
        u1.initialize(position1);
        u1.setMessageCallback(System.out::println);
        u2 = new Trap('s',"s", 0, 3, 2, 3, 4, 5);
        u2.initialize(position2);
        u2.setMessageCallback(System.out::println);
        u2.setDeathCallback(edc);
        u3 = new Monster('s', "s", 0, 3, 2, 3, 4);
        u3.initialize(position3);u3.setDeathCallback(edc);
        u3.setMessageCallback(System.out::println);
        u4 = new Empty(position4);
        u5 = new Wall(position5);
        p2 = new Mage("d", 3, 3, 2, 3, 4, 5, 6, 4);
        p2.initialize(new Position(3,5));
        p2.setMessageCallback(System.out::println);
        p3 = new Mage("d", 3, 3, 2, 3, 4, 5, 6, 4);
        p3.initialize(new Position(2,7));
        p2.setMessageCallback(System.out::println);
        List<Tile> tiles = new LinkedList<Tile>(); tiles.add(u1); tiles.add(u2); tiles.add(u3); tiles.add(u4); tiles.add(u5);
        List<Enemy> enemies = new ArrayList<>(); enemies.add(u2); enemies.add(u3);
        level = new Level(tiles,u1,enemies);
    }
    @Test
    void PlayerOnEnemy() {
        this.act = new MoveLeft(u1);
        this.act.act(level);

        Assertions.assertEquals(new Position(1,1).toString(),u1.getPosition().toString());
        Assertions.assertEquals(new Position(1,2).toString(),u2.getPosition().toString());

    }
    @Test
    void PlayerOnWall() {
        this.act = new MoveLeft(p2);
        this.act.act(level);

        Assertions.assertEquals(new Position( 3,4).toString(),u5.getPosition().toString());
        Assertions.assertEquals(new Position(3,5).toString(),p2.getPosition().toString());
    }
    @Test
    void PlayerOnEmpty() {
        this.act = new MoveLeft(p3);
        this.act.act(level);

        Assertions.assertEquals(new Position( 2,7).toString(),u4.getPosition().toString());
        Assertions.assertEquals(new Position(2,6).toString(),p3.getPosition().toString());
    }
}