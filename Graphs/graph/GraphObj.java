package graph;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;

/* See restrictions in Graph.java. */

/** A partial implementation of Graph containing elements common to
 *  directed and undirected graphs.
 *
 *  @author Michael Ferrin
 */
abstract class GraphObj extends Graph {

    /** A new, empty Graph. */
    GraphObj() {
        _myGraph = new HashMap<Integer, ArrayList<Integer>>();
    }

    @Override
    public int vertexSize() {
        return _myGraph.size();
    }

    @Override
    public int maxVertex() {
        if (_myGraph.size() == 0) {
            return 0;
        }
        int currentMax = 0;
        for (Integer key : _myGraph.keySet()) {
            if (key > currentMax) {
                currentMax = key;
            }
        }
        return currentMax;
    }

    @Override
    public int edgeSize() {
        int counter = 0;
        for (Integer key : _myGraph.keySet()) {
            counter = counter + _myGraph.get(key).size();
        }
        if (isDirected()) {
            return counter;
        } else {
            return counter / 2;
        }
    }

    @Override
    public abstract boolean isDirected();

    @Override
    public int outDegree(int v) {
        for (Integer key : _myGraph.keySet()) {
            if (key == v) {
                return _myGraph.get(v).size();
            }
        }
        return 0;
    }

    @Override
    public abstract int inDegree(int v);

    @Override
    public boolean contains(int u) {
        for (Integer key : _myGraph.keySet()) {
            if (key == u) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean contains(int u, int v) {
        if (contains(u) && contains(v)) {
            if (_myGraph.get(u).contains(v)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int add() {
        ArrayList<Integer> list = new ArrayList<Integer>();
        ArrayList<Integer> pList = new ArrayList<Integer>();
        int temp = this.maxVertex();
        for (int i = 1; i < temp; i++) {
            if (!(_myGraph.containsKey(i))) {
                _myGraph.put(i, list);
                _predecess.put(i, pList);
                return i;
            }
        }
        _myGraph.put(temp + 1, list);
        _predecess.put(temp + 1, pList);
        return temp + 1;
    }

    @Override
    public int add(int u, int v) {
        if (_myGraph.get(u).contains(v)) {
            return u;
        }
        if (isDirected()) {
            _myGraph.get(u).add(v);
            _predecess.get(v).add(u);
            return u;
        } else {
            _myGraph.get(u).add(v);
            _myGraph.get(v).add(u);
            _predecess.get(v).add(u);
            _predecess.get(u).add(v);
            return u;
        }
    }

    @Override
    public void remove(int v) {
        if (contains(v)) {
            for (Integer key : _myGraph.keySet()) {
                remove(key, v);
            }
            _myGraph.remove((Integer) v);
            _predecess.remove((Integer) v);
        }
    }

    @Override
    public void remove(int u, int v) {
        if (this.isDirected()) {
            _myGraph.get(u).remove((Integer) v);
            _predecess.get(v).remove((Integer) u);
        } else {
            _myGraph.get(u).remove((Integer) v);
            _myGraph.get(v).remove((Integer) u);
            _predecess.get(v).remove((Integer) u);
            _predecess.get(u).remove((Integer) v);
        }
    }

    @Override
    public Iteration<Integer> vertices() {
        Iterator<Integer> listiter = _myGraph.keySet().iterator();
        Iteration<Integer> result = Iteration.iteration(listiter);
        return result;
    }

    @Override
    public int successor(int v, int k) {
        if (this.contains(v)) {
            if (outDegree(v) == 0) {
                return 0;
            } else if (_myGraph.get(v).size() >= k + 1) {
                return _myGraph.get(v).get(k);
            }
        }
        return 0;
    }

    @Override
    public abstract int predecessor(int v, int k);

    @Override
    public Iteration<Integer> successors(int v) {
        Set<Integer> listsucc = new HashSet<Integer>();
        if (mine(v)) {
            for (int i = 0; i < _myGraph.size(); i++) {
                if (successor(v, i) != 0) {
                    listsucc.add(successor(v, i));
                }
            }
            Iterator<Integer> listiter = listsucc.iterator();
            Iteration<Integer> answer = Iteration.iteration(listiter);
            return answer;
        }
        Iterator<Integer> emptyiter = listsucc.iterator();
        Iteration<Integer> emptyanswer = Iteration.iteration(emptyiter);
        return emptyanswer;
    }

    @Override
    public abstract Iteration<Integer> predecessors(int v);

    @Override
    public Iteration<int[]> edges() {
        ArrayList<int[]> correct = new ArrayList<int[]>();
        HashSet<HashSet<Integer>> checker = new HashSet<HashSet<Integer>>();
        if (!(isDirected())) {
            for (Integer key : _myGraph.keySet()) {
                for (int y = 0; y < _myGraph.get(key).size(); y++) {
                    int[] pair = new int[]{key, _myGraph.get(key).get(y)};
                    HashSet<Integer> hs = new HashSet<Integer>();
                    hs.add(_myGraph.get(key).get(y));
                    hs.add(key);
                    if (!(checker.contains(hs))) {
                        correct.add(pair);
                        checker.add(hs);
                    }
                }
            }
        } else {
            for (Integer key : _myGraph.keySet()) {
                for (int y = 0; y < _myGraph.get(key).size(); y++) {
                    int[] pair = new int[]{key, _myGraph.get(key).get(y)};
                    correct.add(pair);
                }
            }
        }
        Iterator<int[]> resultinglist = correct.iterator();
        Iteration<int[]> resultingiteration =
            Iteration.iteration(resultinglist);
        return resultingiteration;
    }

    @Override
    protected boolean mine(int v) {
        for (Integer key : _myGraph.keySet()) {
            if (key == v) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void checkMyVertex(int v) {
        if (!mine(v)) {
            throw new IllegalArgumentException("Vertex does not exist");
        }
    }

    @Override
    protected int edgeId(int u, int v) {
        if (contains(u, v)) {
            if (isDirected()) {
                int unique = ((u + v) * (u + v + 1) + v);
                return unique;
            } else if (!isDirected()) {
                if (u > v) {
                    int temp = v;
                    v = u;
                    u = temp;
                }
                int unique = ((u + v) * (u + v + 1) + v);
                return unique;
            }
        }
        return 0;
    }

    /** My implementaton of a graph storing vertices and edges. */
    protected HashMap<Integer, ArrayList<Integer>> _myGraph;

    /** A helper map used to store predecessors. */
    protected HashMap<Integer, ArrayList<Integer>> _predecess
        = new HashMap<Integer, ArrayList<Integer>>();
}
