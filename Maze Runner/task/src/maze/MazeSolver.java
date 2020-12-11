package maze;

import java.util.*;

public class MazeSolver {
    Maze maze;
    ArrayList<Cell> visited;
    Cell end;
    List<Cell> solvedPath;

    public MazeSolver(Maze maze){
        this.maze = maze;
    }

    public List<Cell> solve(){
        visited = new ArrayList<>();
        solvedPath = new ArrayList<>();

        end = maze.nodes.get(1);
        Cell current = maze.nodes.get(0);

        if (explore(maze, current, solvedPath)){
            ArrayList<Cell> newPath = new ArrayList<>();
            for (int i = 0; i < solvedPath.size() - 1; i++){
                newPath.add(maze.maze[(solvedPath.get(i).getX() + solvedPath.get(i+1).getX())/2][(solvedPath.get(i).getY() + solvedPath.get(i+1).getY())/2]);
            }
            solvedPath.addAll(newPath);
            return solvedPath;
        }else{
            return Collections.emptyList();
        }
    }

    private boolean explore(Maze maze, Cell current, List<Cell> solvedPath) {
        System.out.println(current);

        if (visited.contains(current)){
            return false;
        }

        solvedPath.add(current);
        visited.add(current);

        if (current.equals(end)){
            //Getting the exit
            return true;
        }

        for (Cell cell : maze.adjacencyList.get(current)){
            current = cell;
            if (explore(maze, current, solvedPath)){
                return true;
            }
        }

        solvedPath.remove(solvedPath.size() - 1);
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < maze.maze.length; i++){
            for (int j = 0; j <maze.maze[i].length; j++){
                if (this.solvedPath.contains(maze.maze[i][j])){
                    sb.append("//");
                }else{
                    sb.append(maze.maze[i][j]);
                }
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
