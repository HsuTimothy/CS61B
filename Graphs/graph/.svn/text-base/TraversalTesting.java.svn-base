package graph;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

/** Unit tests for the Graph class.
 *  @author Michael Ferrin
 */
public class TraversalTesting {

    public class BreadthFirstTraversalExtended extends BreadthFirstTraversal {

        protected BreadthFirstTraversalExtended(Graph G) {
            super(G);
        }

        @Override
        protected boolean visit(int v) {
            _visitedT.add(v);
            return true;
        }

        protected ArrayList<Integer> getList() {
            return _visitedT;
        }

        protected ArrayList<Integer> _visitedT = new ArrayList<Integer>();
    }

    public class DepthFirstTraversalExtended extends DepthFirstTraversal {

        protected DepthFirstTraversalExtended(Graph G) {
            super(G);
            _G = G;
        }

        @Override
        protected boolean visit(int v) {
            _visitedT.add(v);
            return true;
        }

        @Override
        protected boolean postVisit(int v) {
            _postVisitedT.add(v);
            return true;
        }

        @Override
        protected boolean shouldPostVisit(int v) {
            if (_postVisitedT.contains(v)) {
                return false;
            }
            if (_G.outDegree(v) == 0 || marked(_G.successor(v, 0))) {
                return true;
            }
            return true;
        }

        protected ArrayList<Integer> getList() {
            return _visitedT;
        }

        protected ArrayList<Integer> getPostList() {
            return _postVisitedT;
        }

        protected ArrayList<Integer> _visitedT = new ArrayList<Integer>();
        protected ArrayList<Integer> _postVisitedT = new ArrayList<Integer>();
        private final Graph _G;
    }

    @Test
    public void bfTraverse() {
        DirectedGraph g = new DirectedGraph();
        g.add();
        g.add();
        g.add();
        g.add();
        g.add();
        g.add(5, 4);
        g.add(5, 3);
        g.add(4, 1);
        g.add(3, 2);
        g.add(1, 5);
        ArrayList<Integer> option1 = new ArrayList<Integer>();
        option1.add(5);
        option1.add(4);
        option1.add(3);
        option1.add(1);
        option1.add(2);
        ArrayList<Integer> option2 = new ArrayList<Integer>();
        option2.add(5);
        option2.add(3);
        option2.add(4);
        option2.add(1);
        option2.add(2);
        ArrayList<Integer> option3 = new ArrayList<Integer>();
        option3.add(5);
        option3.add(4);
        option3.add(3);
        option3.add(2);
        option3.add(1);
        ArrayList<Integer> option4 = new ArrayList<Integer>();
        option4.add(5);
        option4.add(3);
        option4.add(4);
        option4.add(2);
        option4.add(1);
        BreadthFirstTraversalExtended computer =
            new BreadthFirstTraversalExtended(g);
        computer.traverse(5);
        ArrayList<Integer> traverseList = computer.getList();
        assertTrue((traverseList.equals(option1))
            || (traverseList.equals(option2))
            || (traverseList.equals(option3))
            || (traverseList.equals(option4)));
    }

    @Test
    public void dfTraverse() {
        UndirectedGraph g = new UndirectedGraph();
        g.add();
        g.add();
        g.add();
        g.add();
        g.add();
        g.add(5, 4);
        g.add(5, 3);
        g.add(4, 1);
        g.add(3, 2);
        g.add(1, 5);
        ArrayList<Integer> option1 = new ArrayList<Integer>();
        option1.add(5);
        option1.add(4);
        option1.add(1);
        option1.add(3);
        option1.add(2);
        ArrayList<Integer> option2 = new ArrayList<Integer>();
        option2.add(5);
        option2.add(3);
        option2.add(2);
        option2.add(4);
        option2.add(1);
        ArrayList<Integer> option3 = new ArrayList<Integer>();
        option3.add(1);
        option3.add(4);
        option3.add(2);
        option3.add(3);
        option3.add(5);
        ArrayList<Integer> option4 = new ArrayList<Integer>();
        option4.add(2);
        option4.add(3);
        option4.add(1);
        option4.add(4);
        option4.add(5);
        DepthFirstTraversalExtended computer =
            new DepthFirstTraversalExtended(g);
        computer.traverse(5);
        ArrayList<Integer> traverseList = computer.getList();
        ArrayList<Integer> postTraverseList = computer.getPostList();
        assertTrue((traverseList.equals(option1))
            || (traverseList.equals(option2)));
        assertTrue((postTraverseList.equals(option3))
            || postTraverseList.equals(option4));
    }

}
