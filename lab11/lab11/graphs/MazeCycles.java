package lab11.graphs;

import edu.princeton.cs.algs4.Stack;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s;
    private int[] parent;
    private boolean detected;

    public MazeCycles(Maze m) {
        super(m);
        s = maze.xyTo1D(1,1);
        parent = new int[maze.N() * maze.N()];
        parent[s] = 0;
        detected = false;
    }

    @Override
    public void solve() {
        detectCycle(s);
    }

    // Helper methods go here
    private void detectCycle(int s) {
        marked[s] = true;
        if (detected) {
            return;
        }

        for (int i : maze.adj(s)) {
            if (!marked[i]) {
                parent[i] = s;
                detectCycle(i);
            } else if (parent[s] != i) {
                marked[i] = true;
                detected = true;
                parent[i] = s;
                int ptr = s;
                while (i != ptr) {
                    edgeTo[ptr] = parent[ptr];
                    ptr = parent[ptr];
                }
                edgeTo[ptr] = s;
                announce();
                return;
            }
        }
    }
}

