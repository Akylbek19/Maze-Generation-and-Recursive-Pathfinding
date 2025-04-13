// MazeSolver.java
public class MazeSolver {
    private char[][] maze;
    private boolean[][] visited;
    private boolean solved = false;
    private int startX, startY;
    private int exitX, exitY;

    public MazeSolver(char[][] maze) {
        this.maze = maze;
        this.visited = new boolean[maze.length][maze[0].length];

        // Find start and exit positions
        for (int y = 0; y < maze.length; y++) {
            for (int x = 0; x < maze[0].length; x++) {
                if (maze[y][x] == MazeGenerator.START) {
                    startX = x;
                    startY = y;
                } else if (maze[y][x] == MazeGenerator.EXIT) {
                    exitX = x;
                    exitY = y;
                }
            }
        }
    }

    public boolean solveMaze() {
        return solve(startX, startY);
    }

    private boolean solve(int x, int y) {
        // Base cases
        if (x < 0 || x >= maze[0].length || y < 0 || y >= maze.length) {
            return false;  // Out of bounds
        }

        if (maze[y][x] == MazeGenerator.WALL || visited[y][x]) {
            return false;  // Hit wall or already visited
        }

        if (x == exitX && y == exitY) {
            visited[y][x] = true;
            maze[y][x] = MazeGenerator.VISITED;
            solved = true;
            return true;  // Found exit
        }

        // Mark current cell as visited
        visited[y][x] = true;
        if (maze[y][x] != MazeGenerator.START) {
            maze[y][x] = MazeGenerator.VISITED;
        }

        // Recursive cases - explore all directions
        if (!solved) solve(x+1, y);  // Right
        if (!solved) solve(x, y+1);  // Down
        if (!solved) solve(x-1, y);  // Left
        if (!solved) solve(x, y-1);  // Up

        // Backtrack if path doesn't lead to exit
        if (!solved) {
            if (maze[y][x] != MazeGenerator.START) {
                maze[y][x] = MazeGenerator.PATH;
            }
            visited[y][x] = false;
        }

        return solved;
    }

    public char[][] getSolvedMaze() {
        return maze;
    }
}
