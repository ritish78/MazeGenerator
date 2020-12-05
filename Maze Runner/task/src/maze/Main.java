package maze;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter size of matrix (separated by space): ");
        String[] size = scanner.nextLine().split(" ");
        int width = Integer.valueOf(size[0]);
        int height = Integer.valueOf(size[1]);

        Maze maze = new Maze(width, height);
        maze.generateMaze();
        System.out.println(maze);
    }
}


