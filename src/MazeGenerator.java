// MazeGenerator.java
import java.util.Random;
import java.util.Stack;

public class MazeGenerator {
    private int width;
    private int height;
    private char[][] maze;
    private Random random = new Random();

    // Constants for cell types
    public static final char WALL = '#';
    public static final char PATH = ' ';
    public static final char START = 'S';
    public static final char EXIT = 'E';
    public static final char VISITED = '.';

    public MazeGenerator(int width, int height) {
        // Ensure odd dimensions for proper maze generation
        this.width = (width % 2 == 0) ? width + 1 : width;
        this.height = (height % 2 == 0) ? height + 1 : height;
        this.maze = new char[this.height][this.width];
        initializeMaze();
    }

    private void initializeMaze() {
        // Fill the entire maze with walls
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                maze[y][x] = WALL;
            }
        }
    }

    public void generateMaze() {
        // Start from (1,1) - must be odd coordinates
        int startX = 1;
        int startY = 1;

        // Create stack for backtracking
        Stack<int[]> stack = new Stack<>();
        stack.push(new int[]{startX, startY});
        maze[startY][startX] = PATH;

        // Directions: up, right, down, left
        int[][] directions = {{0, -2}, {2, 0}, {0, 2}, {-2, 0}};

        while (!stack.isEmpty()) {
            int[] current = stack.peek();
            int x = current[0];
            int y = current[1];

            // Check for unvisited neighbors
            boolean[] visited = new boolean[4];
            int unvisitedCount = 0;

            for (int i = 0; i < 4; i++) {
                int nx = x + directions[i][0];
                int ny = y + directions[i][1];

                if (nx > 0 && nx < width - 1 && ny > 0 && ny < height - 1 && maze[ny][nx] == WALL) {
                    unvisitedCount++;
                    visited[i] = false;
                } else {
                    visited[i] = true;
                }
            }

            if (unvisitedCount > 0) {
                // Choose random unvisited direction
                int nextDir;
                do {
                    nextDir = random.nextInt(4);
                } while (visited[nextDir]);

                // Carve path
                int nx = x + directions[nextDir][0];
                int ny = y + directions[nextDir][1];
                maze[ny][nx] = PATH;

                // Remove wall between current and next cell
                int wallX = x + directions[nextDir][0]/2;
                int wallY = y + directions[nextDir][1]/2;
                maze[wallY][wallX] = PATH;

                stack.push(new int[]{nx, ny});
            } else {
                // Backtrack
                stack.pop();
            }
        }

        // Set start and exit points
        maze[1][0] = START;  // Entrance on left wall
        maze[height-2][width-1] = EXIT;  // Exit on right wall
    }

    public char[][] getMaze() {
        return maze;
    }

    public void printMaze() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                System.out.print(maze[y][x]);
            }
            System.out.println();
        }
    }
}
