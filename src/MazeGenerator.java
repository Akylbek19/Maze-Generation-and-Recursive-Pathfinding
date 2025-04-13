// MazeGenerator.java
import java.util.Random;
import java.util.Stack;

public class MazeGenerator {
    private int width;   // Ширина лабиринта
    private int height;   // Высота лабиринта
    private char[][] maze;  // 2D массив для хранения лабиринта
    private Random random = new Random();  // Генератор случайных чисел

    // Constants for cell types
    public static final char WALL = '#';   // Стена
    public static final char PATH = ' ';   // Проход
    public static final char START = 'S';  // Начальная точка
    public static final char EXIT = 'E';   // Выход
    public static final char VISITED = '.';  // Посещенная клетка при поиске пути

    public MazeGenerator(int width, int height) {
        // Корректируем размеры, чтобы были нечетными (для правильной генерации)
        this.width = (width % 2 == 0) ? width + 1 : width;
        this.height = (height % 2 == 0) ? height + 1 : height;
        this.maze = new char[this.height][this.width];
        initializeMaze();
    }

    // Инициализация лабиринта (все клетки - стены)
    private void initializeMaze() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                maze[y][x] = WALL;
            }
        }
    }

    // Генерация лабиринта методом рекурсивного backtracking
    public void generateMaze() {
        // Начинаем с клетки (1,1) - должна быть нечетная
        int startX = 1;
        int startY = 1;

        // Стек для хранения пути и реализации backtracking
        Stack<int[]> stack = new Stack<>();
        stack.push(new int[]{startX, startY});
        maze[startY][startX] = PATH;

        // Возможные направления движения: вверх, вправо, вниз, влево
        int[][] directions = {{0, -2}, {2, 0}, {0, 2}, {-2, 0}};

        while (!stack.isEmpty()) {
            int[] current = stack.peek();
            int x = current[0];
            int y = current[1];

            // Проверяем непосещенных соседей
            boolean[] visited = new boolean[4];
            int unvisitedCount = 0;

            for (int i = 0; i < 4; i++) {
                int nx = x + directions[i][0];
                int ny = y + directions[i][1];

                // Если сосед в пределах лабиринта и это стена
                if (nx > 0 && nx < width - 1 && ny > 0 && ny < height - 1 && maze[ny][nx] == WALL) {
                    unvisitedCount++;
                    visited[i] = false;
                } else {
                    visited[i] = true;
                }
            }

            if (unvisitedCount > 0) {
                // Выбираем случайное направление из непосещенных
                int nextDir;
                do {
                    nextDir = random.nextInt(4);
                } while (visited[nextDir]);

                // Координаты новой клетки
                int nx = x + directions[nextDir][0];
                int ny = y + directions[nextDir][1];
                maze[ny][nx] = PATH;

                // Убираем стену между текущей и новой клеткой
                int wallX = x + directions[nextDir][0]/2;
                int wallY = y + directions[nextDir][1]/2;
                maze[wallY][wallX] = PATH;

                stack.push(new int[]{nx, ny});
            } else {
                // Если нет непосещенных соседей - возвращаемся назад
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
