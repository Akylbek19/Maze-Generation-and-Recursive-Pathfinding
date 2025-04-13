// MazeSolver.java
public class MazeSolver {
    private char[][] maze;   // Лабиринт для решения
    private boolean[][] visited;  // Массив посещенных клеток
    private boolean solved = false;  // Флаг найденного решения
    private int startX, startY;   // Координаты старта
    private int exitX, exitY;   // Координаты выхода

    public MazeSolver(char[][] maze) {
        this.maze = maze;
        this.visited = new boolean[maze.length][maze[0].length];

        // Находим стартовую и конечную позиции
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

    // Основной метод для запуска решения
    public boolean solveMaze() {
        return solve(startX, startY);
    }

    // Рекурсивный метод поиска пути
    private boolean solve(int x, int y) {
        // Базовые случаи:
        // 1. Выход за границы лабиринта
        if (x < 0 || x >= maze[0].length || y < 0 || y >= maze.length) {
            return false;
        }

        // 2. Наткнулись на стену или уже посещали эту клетку
        if (maze[y][x] == MazeGenerator.WALL || visited[y][x]) {
            return false;  // Hit wall or already visited
        }

        // 3. Нашли выход
        if (x == exitX && y == exitY) {
            visited[y][x] = true;
            maze[y][x] = MazeGenerator.VISITED;
            solved = true;
            return true;  // Found exit
        }

        // Помечаем текущую клетку как посещенную
        visited[y][x] = true;
        if (maze[y][x] != MazeGenerator.START) {
            maze[y][x] = MazeGenerator.VISITED;
        }

        // Рекурсивно исследуем все направления:
        if (!solved) solve(x+1, y);  // Идем вправо
        if (!solved) solve(x, y+1);  // Идем вниз
        if (!solved) solve(x-1, y);  // Идем вниз
        if (!solved) solve(x, y-1);  // Идем вверх

        // Если решение не найдено - откатываем изменения
        if (!solved) {
            if (maze[y][x] != MazeGenerator.START) {
                maze[y][x] = MazeGenerator.PATH;
            }
            visited[y][x] = false;
        }

        return solved;
    }

    // Возвращает лабиринт с отмеченным путем
    public char[][] getSolvedMaze() {
        return maze;
    }
}
