package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

import java.util.Comparator;

public class Solver {
    private MinPQ<SearchNode> fringe;
    private SearchNode startNode;
    private int moves;
    private Stack<WorldState> solution;
    private int totalEnqueued;


    private class SearchNode {
        WorldState ws;
        int movesSoFar;
        SearchNode previous;
        int distanceToGoal;

        private SearchNode(WorldState ws, int movesSoFar, SearchNode previous) {
            this.ws = ws;
            this.movesSoFar = movesSoFar;
            this.previous = previous;
            distanceToGoal = ws.estimatedDistanceToGoal();
        }
    }

    private class SearchNodeComparator implements Comparator<SearchNode> {
        @Override
        public int compare(SearchNode o1, SearchNode o2) {
            return o1.movesSoFar + o1.distanceToGoal - o2.movesSoFar - o2.distanceToGoal;
        }
    }

    public Solver(WorldState initial) {
        fringe = new MinPQ<>(new SearchNodeComparator());
        startNode = new SearchNode(initial, 0, null);
        fringe.insert(startNode);
        totalEnqueued = 1;
        solution = new Stack<>();
        SearchNode end = null;

        while (!fringe.isEmpty()) {
            SearchNode current = fringe.delMin();
            if (current.ws.isGoal()) {
                moves = current.movesSoFar;
                end = current;
                break;
            }
            for (WorldState w : current.ws.neighbors()) {
                if (current.previous == null || !w.equals(current.previous.ws)) {
                    fringe.insert(new SearchNode(w, current.movesSoFar + 1, current));
                    totalEnqueued += 1;
                }
            }
        }
        for (SearchNode temp = end; temp != null; temp = temp.previous) {
            solution.push(temp.ws);
        }
    }

    public int moves() {
        return moves;
    }

    public Iterable<WorldState> solution() {
        return solution;
    }

    private int totalEnqueued() {
        return totalEnqueued;
    }
}
