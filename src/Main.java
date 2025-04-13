// Main.java
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Maze Generator and Solver");
        System.out.print("Enter maze width (default 21): ");
        int width = readIntWithDefault(scanner, 21);

        System.out.print("Enter maze height (default 21): ");
        int height = readIntWithDefault(scanner, 21);

        // Generate maze
        MazeGenerator generator = new MazeGenerator(width, height);
        generator.generateMaze();

        System.out.println("\nGenerated Maze:");
        generator.printMaze();

        // Solve maze
        MazeSolver solver = new MazeSolver(generator.getMaze());
        boolean solutionFound = solver.solveMaze();

        System.out.println("\nSolved Maze:");
        if (solutionFound) {
            System.out.println("Path found (marked with '" + MazeGenerator.VISITED + "'):");
        } else {
            System.out.println("No path found!");
        }

        // Print solved maze
        char[][] solvedMaze = solver.getSolvedMaze();
        for (int y = 0; y < solvedMaze.length; y++) {
            for (int x = 0; x < solvedMaze[0].length; x++) {
                System.out.print(solvedMaze[y][x]);
            }
            System.out.println();
        }
    }

    private static int readIntWithDefault(Scanner scanner, int defaultValue) {
        try {
            String input = scanner.nextLine().trim();
            return input.isEmpty() ? defaultValue : Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}