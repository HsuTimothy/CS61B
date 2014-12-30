package graph;

import org.junit.Test;
import static org.junit.Assert.*;

/** Unit tests for the Graph class.
 *  @author Michael Ferrin
 */
public class GraphTesting {


    @Test
    public void graphObj() {
        DirectedGraph go = new DirectedGraph();
        UndirectedGraph yo = new UndirectedGraph();
    }

    @Test
    public void emptyGraph() {
        DirectedGraph g = new DirectedGraph();
        assertEquals("Initial graph has vertices", 0, g.vertexSize());
        assertEquals("Initial graph has edges", 0, g.edgeSize());
    }

    @Test
    public void vertexSize() {
        DirectedGraph g = new DirectedGraph();
        assertEquals(0, g.vertexSize());
        g.add();
        assertEquals(1, g.vertexSize());
        g.add();
        g.add();
        assertEquals(3, g.vertexSize());
        g.remove(1);
        assertEquals(2, g.vertexSize());
    }

    @Test
    public void maxVertex() {
        DirectedGraph g = new DirectedGraph();
        assertEquals(0, g.maxVertex());
        g.add();
        g.add();
        assertEquals(2, g.maxVertex());
        g.remove(1);
        assertEquals(2, g.maxVertex());
    }

    @Test
    public void edgeSize() {
        DirectedGraph g = new DirectedGraph();
        assertEquals(0, g.edgeSize());
    }

    @Test
    public void remove() {
        DirectedGraph g = new DirectedGraph();
        g.add();
        g.add();
        g.add();
        g.add();
        assertEquals(4, g.vertexSize());
        assertEquals(4, g.maxVertex());
        g.remove(3);
        assertEquals(3, g.vertexSize());
        assertEquals(4, g.maxVertex());
        g.remove(4);
        assertEquals(2, g.vertexSize());
        assertEquals(2, g.maxVertex());
        g.remove(1);
        g.add();
        g.remove(1);
        g.add();
        g.remove(10);
    }

    @Test
    public void contains() {
        DirectedGraph g = new DirectedGraph();
        g.add();
        g.add();
        g.add();
        assertFalse(g.contains(4));
        assertTrue(g.contains(1));
        g.remove(2);
        assertFalse(g.contains(2));
    }

    @Test
    public void add() {
        DirectedGraph g = new DirectedGraph();
        assertEquals(0, g.vertexSize());
        g.add();
        g.add();
        g.add();
        assertEquals(3, g.vertexSize());
        assertEquals(3, g.maxVertex());
        g.remove(1);
        assertFalse(g.contains(1));
        assertEquals(3, g.maxVertex());
        assertEquals(2, g.vertexSize());
        g.add();
        assertEquals(3, g.maxVertex());
        assertTrue(g.contains(1));
    }

    @Test
    public void removeEdge() {

    }

    @Test
    public void predecessor() {
        DirectedGraph g = new DirectedGraph();
        g.add();
        g.add();
        g.add();
        g.add();
        g.add();
        g.add();
        g.add(6, 2);
        g.add(3, 2);
        g.add(1, 5);
        g.add(4, 3);
        g.add(4, 2);
        g.add(2, 5);
        g.add(2, 3);
        assertEquals(6, g.predecessor(2, 0));
        assertEquals(3, g.predecessor(2, 1));
    }

}










