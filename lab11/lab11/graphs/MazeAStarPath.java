package lab11.graphs;
/**
 *  @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;
    private ArrayHeap<Integer> fringe;

    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
        fringe = new ArrayHeap<>();
    }

    /** Estimate of the distance from v to the target. */
    private int h(int v) {
        int vertexX = v % maze.N() + 1;
        int vertexY = v / maze.N() + 1;
        return Math.abs(vertexX - maze.N()) + Math.abs(vertexY - maze.N());
    }

    /** Finds vertex estimated to be closest to target. */
    private int findMinimumUnmarked() {
        return -1;
        /* You do not have to use this method. */
    }

    /** Performs an A star search from vertex s. */
    private void astar(int s) {
        fringe.insert(s, 0);

        while (fringe.size() != 0) {
            int min = fringe.removeMin();
            marked[min] = true;
            announce();
            if (min == t) {
                return;
            }

            for (int i : maze.adj(min)) {
                if (!marked[i]) {
                    if (distTo[i] == Integer.MAX_VALUE) {
                        distTo[i] = distTo[min] + 1;
                        edgeTo[i] = min;
                        fringe.insert(i, distTo[i] + h(i));
                    } else {
                        relax(min, i);
                    }
                }
            }
        }
    }

    private void relax(int start, int end) {
        if (distTo[start] + 1 < distTo[end]) {
            edgeTo[end] = start;
            distTo[end] = distTo[start] + 1;
        }
    }

    @Override
    public void solve() {
        astar(s);
    }

}

