package grid.bubble;

import grid.Coordinate;
import grid.Grid;
import org.junit.jupiter.api.BeforeEach;

public class GridBubbleTest extends BubbleTest {

    @BeforeEach
    void setupTestEnvironment() {
        bubbleFactory = new GridBubbleFactory();
        location = new Coordinate(0,0);
    }
}
