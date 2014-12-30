package graph;

import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;

/* See restrictions in Graph.java. */

/** Represents an undirected graph.  Out edges and in edges are not
 *  distinguished.  Likewise for successors and predecessors.
 *
 *  @author Michael Ferrin
 */
public class UndirectedGraph extends GraphObj {

    @Override
    public boolean isDirected() {
        return false;
    }

    @Override
    public int inDegree(int v) {
        if (_predecess.containsKey(v)) {
            return _predecess.get(v).size();
        }
        return 0;
    }

    @Override
    public int predecessor(int v, int k) {
        if (_predecess.containsKey(v) && _predecess.get(v).size() >= k) {
            return _predecess.get(v).get(k);
        }
        return 0;
    }

    @Override
    public Iteration<Integer> predecessors(int v) {
        Set<Integer> listpred = new HashSet<Integer>();
        if (mine(v)) {
            for (Integer key : _myGraph.keySet()) {
                for (int y = 0; y < _myGraph.get(key).size(); y++) {
                    if (_myGraph.get(key).contains(v)) {
                        listpred.add(key);
                    }
                }
            }
        }
        Iterator<Integer> finaliter = listpred.iterator();
        Iteration<Integer> finalanswer = Iteration.iteration(finaliter);
        return finalanswer;
    }

}
