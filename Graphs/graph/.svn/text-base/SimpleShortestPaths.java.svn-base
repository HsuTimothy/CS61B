package graph;

import java.util.HashMap;

/* See restrictions in Graph.java. */

/** A partial implementation of ShortestPaths that contains the weights of
 *  the vertices and the predecessor edges.   The client needs to
 *  supply only the two-argument getWeight method.
 *  @author Michael Ferrin
 */
public abstract class SimpleShortestPaths extends ShortestPaths {

    /** The shortest paths in G from SOURCE. */
    public SimpleShortestPaths(Graph G, int source) {
        this(G, source, 0);
    }

    /** A shortest path in G from SOURCE to DEST. */
    public SimpleShortestPaths(Graph G, int source, int dest) {
        super(G, source, dest);
    }

    @Override
    public double getWeight(int v) {
        if (_G.contains(v)) {
            if (_storeWeight.containsKey(v)) {
                return _storeWeight.get(v);
            }
        }
        return Double.POSITIVE_INFINITY;
    }

    @Override
    protected void setWeight(int v, double w) {
        _storeWeight.put(v, w);
    }

    @Override
    public int getPredecessor(int v) {
        if (_storePredecessor.containsKey(v)) {
            return _storePredecessor.get(v);
        } else {
            return 0;
        }
    }

    @Override
    protected void setPredecessor(int v, int u) {
        _storePredecessor.put(v, u);
    }

    /** Stores vertex predecessors. */
    private HashMap<Integer, Integer> _storePredecessor =
        new HashMap<Integer, Integer>();
    /** Stores edge weights. */
    private HashMap<Integer, Double> _storeWeight =
        new HashMap<Integer, Double>();

}
