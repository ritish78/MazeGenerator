package maze;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Maze {
    int width;
    int height;
    int temp1;
    int temp2;
    private Cell[][] maze;

    public Maze(int width, int height){
        this.width = width;
        this.height = height;
        this.maze = new Cell[width][height];
    }

    public void generateMaze(){
        Random random = new Random();
        ArrayList<Cell> nodes = new ArrayList<>();
        ArrayList<Edge> edges = new ArrayList<>();
        ArrayList<Edge> tree = new ArrayList<>();

        for (int i = 0; i < width; i++){
            for (int j = 0; j < height; j++){
                maze[i][j] = new Cell(i,j);
            }
        }

        //Then creating an entry and exit point in the maze
        while (true){
            temp1 = random.nextInt(this.width - 2) + 1;

            if (temp1 % 2 != 0){
                break;
            }
        }
        while (true){
            temp2 = random.nextInt(this.width - 2) + 1;

            if (temp2 % 2 != 0){
                break;
            }
        }

        nodes.add(maze[temp1][0]);
        nodes.add(maze[temp2][height - 1]);
        edges.add(new Edge(maze[temp1][0], maze[temp1][1], 1));
        edges.add(new Edge(maze[temp2][height - 1], maze[temp2][height - 2], 1));

        //Then creating the rest of the nodes.
        for (int i = 0; i < this.width; i++){
            for (int j = 0; j < this.height; j++){
                if (i != 0 && j != 0 && i != (width - 1) && j != (height - 1)){
                    if ( (i * 2 - 1) < (width - 1) && (j * 2 - 1) < (height - 1)) {
                        nodes.add(maze[i * 2 - 1][j * 2 - 1]);
                    }
                }
            }
        }

        for (int i = 0; i < nodes.size(); i++){
            nodes.get(i).setId(i);
        }

        for (Cell cell : nodes){
            int xPosition = cell.getX();
            int yPosition = cell.getY();

            for (Cell anotherCell : nodes){
                if (xPosition == anotherCell.getX() && yPosition == anotherCell.getY() + 2 ||
                                xPosition == anotherCell.getX() && yPosition == anotherCell.getY() - 2 ||
                                xPosition == anotherCell.getX() + 2 && yPosition == anotherCell.getY() ||
                                xPosition == anotherCell.getX() - 2 && yPosition == anotherCell.getY()){
                    //Duplicate might also be added
                    edges.add(new Edge(cell,anotherCell));
                }
            }
        }

        //Now, sorting the edges by the weight (value)
        Collections.sort(edges);

        UnionFind unionFind = new UnionFind(nodes.size());

        for (Edge edge : edges){
            if (unionFind.isConnected(edge.getStart().getId(), edge.getEnd().getId())){
                continue;
            }

            unionFind.unify(edge.getStart().getId(), edge.getEnd().getId());
            tree.add(edge);
        }

        for (Cell cell : nodes){
            cell.createPath();
        }

        for (Edge edge : tree){
            int row = (edge.getStart().getX() + edge.getEnd().getX())/2;
            int column = (edge.getStart().getY() + edge.getEnd().getY())/2;

            for (Cell[] cells : maze){
                for (Cell cell : cells){
                    if (cell.getX() == row & cell.getY() == column){
                        cell.createPath();
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < width; i++){
            for (int j = 0; j < height; j++){
                sb.append(maze[i][j]);
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
