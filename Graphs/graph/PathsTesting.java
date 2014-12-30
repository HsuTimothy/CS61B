package graph;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

/** Unit tests for the Graph class.
 *  @author Michael Ferrin
 */

public class PathsTesting {
    class VideoGraphPaths extends SimpleShortestPaths {

        /** A shortest path in G from SOURCE to DEST. */
        public VideoGraphPaths(Graph G, int source, int dest) {
            super(G, source, dest);
            _G = G;
        }

        /** Returns an estimated heuristic weight of the shortest path
            from vertex V to the destination vertex (if any).  This is
            assumed to be less than the actual weight, and is 0 by default. */
        @Override
        protected double estimatedDistance(int v) {
            if (_storeED.containsKey(v)) {
                return _storeED.get(v);
            } else {
                return 0.0;
            }
        }

        /** Returns the current weight of edge (U, V) in the graph.
            If (U, V) is not in the graph, returns positive infinity. */
        @Override
        public double getWeight(int u, int v) {
            if (_G.contains(u, v)) {
                int weightKey = _G.edgeId(u, v);
                return _storeWeights.get(weightKey);
            } else {
                double inf = Double.POSITIVE_INFINITY;
                return inf;
            }
        }

        protected void setGetWeight(int u, int v, double w) {
            int weightKey = _G.edgeId(u, v);
            _storeWeights.put(weightKey, w);
        }

        protected void setEstimatedDistance(int v, double d) {
            _storeED.put(v, d);
        }

        protected HashMap<Integer, Double> _storeWeights =
            new HashMap<Integer, Double>();
        protected HashMap<Integer, Double> _storeED =
            new HashMap<Integer, Double>();
        private final Graph _G;
    }

    @Test
    public void testWeights() {
        Graph videoGraph = new DirectedGraph();
        videoGraph.add();
        videoGraph.add();
        videoGraph.add();
        videoGraph.add();
        videoGraph.add();
        videoGraph.add();
        videoGraph.add();
        videoGraph.add();
        videoGraph.add(2, 3);
        videoGraph.add(4, 2);
        videoGraph.add(4, 3);
        videoGraph.add(4, 5);
        videoGraph.add(5, 3);
        videoGraph.add(5, 6);
        videoGraph.add(6, 7);
        videoGraph.add(4, 1);
        videoGraph.add(1, 8);
        VideoGraphPaths vgp = new VideoGraphPaths(videoGraph, 4, 3);
        vgp.setGetWeight(2, 3, 6.5);
        vgp.setGetWeight(4, 2, 12.2);
        vgp.setGetWeight(4, 3, 102.0);
        vgp.setGetWeight(4, 5, 11.2);
        vgp.setGetWeight(5, 3, 9.1);
        vgp.setGetWeight(5, 6, 30.0);
        vgp.setGetWeight(6, 7, 94238.1);
        vgp.setGetWeight(4, 1, 2.1);
        vgp.setGetWeight(1, 8, 1.9);
        vgp.setEstimatedDistance(4, 102.0);
        vgp.setEstimatedDistance(2, 4.0);
        vgp.setEstimatedDistance(5, 5.1);
        vgp.setEstimatedDistance(6, 40.0);
        vgp.setEstimatedDistance(3, 0.0);
        vgp.setEstimatedDistance(1, 123456.7);
        vgp.setEstimatedDistance(7, 234235.8);
        vgp.setEstimatedDistance(8, 98989898.5);
        vgp.setEstimatedDistance(7, Double.POSITIVE_INFINITY);
        vgp.setEstimatedDistance(6, Double.POSITIVE_INFINITY);
        vgp.setEstimatedDistance(6, Double.POSITIVE_INFINITY);
        vgp.setPaths();
        assertEquals(4, vgp.getSource());
        assertEquals(3, vgp.getDest());
        List<Integer> outputPath = new ArrayList<Integer>();
        outputPath.add(4);
        outputPath.add(2);
        outputPath.add(3);
        assertTrue(vgp.pathTo().equals(outputPath));
        assertEquals(0, vgp.getPredecessor(8));
        assertEquals(0, vgp.getPredecessor(7));
        assertEquals(102.0, vgp.estimatedDistance(4), 0.0);
        assertEquals(5.1, vgp.estimatedDistance(5), 0.0);
    }

}
