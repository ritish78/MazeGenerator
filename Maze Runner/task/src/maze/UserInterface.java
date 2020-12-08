package maze;

import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class UserInterface {
    public void start(){
        Scanner scanner = new Scanner(System.in);
        String maze = "";

            //boolean fromGenerate = false;

            System.out.println("=== Menu ===");
            System.out.println("1. Generate a new maze");
            System.out.println("2. Load a maze");
            System.out.println("0. Exit");

            int command = scanner.nextInt();

            if (command == 1) {
                maze = generateAMaze();
                System.out.println(maze);
                loadMazeMenu(maze);

            } else if (command == 2) {
                loadMaze();
            } else if (command == 0) {
                return;
            } else {
                System.out.println("Incorrect option. Please try again.");
            }


    }

    public String generateAMaze(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please, enter the size of a maze");
//        String[] size = scanner.nextLine().split(" ");
//        int width = Integer.valueOf(size[0]);
        //int height = Integer.valueOf(size[1]);

        int width = scanner.nextInt();

        Maze maze = new Maze(width, width);
        maze.generateMaze();
        return maze.toString();

    }

    public void loadMazeMenu(String maze){

        Scanner scanner = new Scanner(System.in);

            System.out.println("=== Menu ===");
            System.out.println("1. Generate a new maze");
            System.out.println("2. Load a maze");
            System.out.println("3. Save the maze");
            System.out.println("4. Display the maze");
            System.out.println("0. Exit");

            int loadOptions = scanner.nextInt();

            if (loadOptions == 1) {
                generateAMaze();
            } else if (loadOptions == 2) {
                maze = loadMaze();
            } else if (loadOptions == 3) {
                if (!maze.equals(" ")){
                    saveMaze(maze);
                }
            } else if (loadOptions == 4) {
                displayMaze(maze);
            } else if (loadOptions == 0) {
                //break;
                return;
            }else{
                System.out.println("Incorrect option. Please try again.");
            }

    }

    public String loadMaze(){
        System.out.println("Specify the file location: ");
        Scanner scanner = new Scanner(System.in);
        String filePath = scanner.nextLine().trim();

        StringBuilder sb = new StringBuilder();
        ArrayList<String> mazeLines = new ArrayList<>();
        String currentLine = "";
        try (Scanner fileScanner = new Scanner(Paths.get(filePath))){
            while (fileScanner.hasNextLine()){
                currentLine = fileScanner.nextLine();
                sb.append(currentLine);
                sb.append(",");
                mazeLines.add(currentLine);
                //System.out.println(mazeLines.toString());
            }
        }catch (Exception e){
            return "The file does not exist";
        }
        if (checkMazeValidity(mazeLines)){
            String pieces[] = sb.toString().split(",");
            for (int i = 0; i < pieces.length; i++){
                System.out.println(pieces[i]);
            }
            return sb.toString();
        }else{
            return "Cannot load the maze. It has an invalid format";
        }
    }

    private Boolean checkMazeValidity(ArrayList<String> mazeLines) {
        for (String line : mazeLines){
            if (line.length() % 2 == 0){
                continue;
            }else{
                return false;
            }
        }
        return true;
    }


    public void displayMaze(String maze){
        String[] pieces = maze.split(",");
        for (int i = 0; i < pieces.length; i++){
            System.out.println(pieces[i]);
        }
    }

    public void saveMaze(String maze){
        Scanner scanner = new Scanner(System.in);
        String savePath = scanner.nextLine();

        try (PrintWriter printWriter = new PrintWriter(savePath)){
            String[] pieces  = maze.split(",");

            for (int i = 0; i < pieces.length; i++) {
                printWriter.append(pieces[i] + "\n");
            }

        }catch (Exception e){
            System.out.println("Path does not exists!");
        }

    }

}
