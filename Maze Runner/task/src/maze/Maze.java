package maze;

import java.util.*;

public class Maze {
    int x;
    int y;
    Cell[][] maze;
    int temp1;
    int temp2;
    private boolean created;
    ArrayList<Cell> nodes;
    ArrayList<Edge> tree;
    HashMap<Cell, ArrayList<Cell>> adjacencyList;

    public Maze() {
        this.created = false;
    }

    public void generateMaze(int width) {
        this.x = width;
        this.y = width;
        this.maze = new Cell[width][width];
        this.created = true;
        Random random = new Random();
        nodes = new ArrayList<>();
        tree = new ArrayList<>();
        adjacencyList = new HashMap<>();
        ArrayList<Edge> edges = new ArrayList<>();


        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                maze[i][j] = new Cell(i,j);
            }
        }

        //creating entry point
        while (true) {
            temp1 = random.nextInt(this.x -2)+1;

            if (temp1 % 2 != 0) {
                break;
            }
        }

        //creating exit point
        while (true) {
            temp2 = random.nextInt(this.x -2)+1;

            if (temp2 % 2 != 0) {
                break;
            }
        }


        nodes.add(maze[temp1][0]);
        nodes.add(maze[temp2][y-1]);

        edges.add(new Edge(maze[temp1][0], maze[temp1][1], 1));
        edges.add(new Edge(maze[temp2][y-1], maze[temp2][y-2], 1));

        adjacencyList.computeIfAbsent(maze[temp1][0], k -> new ArrayList<>()).add(maze[temp1][1]);
        adjacencyList.computeIfAbsent(maze[temp2][y-1], k -> new ArrayList<>()).add(maze[temp2][y-2]);


        // Then, creating rest of the nodes
        for (int i = 0; i < this.x; i++) {
            for (int j = 0; j < this.y; j++){
                if(i != 0 && j != 0 && i != x -1 && j != y-1) {
                    if(i*2-1< x -1 && j*2-1<y -1) {
                        nodes.add(maze[i * 2 - 1][j * 2 - 1]);
                    }
                }
            }
        }

        for(int i = 0; i < nodes.size(); i++) {
            nodes.get(i).setId(i);
        }


        for(Cell a: nodes) {
            int xPosition = a.getX();
            int yPosition = a.getY();
            for (Cell b: nodes) {
                if (xPosition == b.getX() && yPosition == b.getY() + 2 ||
                        xPosition == b.getX() && yPosition == b.getY() - 2 ||
                        xPosition == b.getX() + 2 && yPosition == b.getY() ||
                        xPosition == b.getX() - 2 && yPosition == b.getY()) {
                    edges.add(new Edge(a, b));

                }
            }
        }

        // sort edges by value (weight of the edges in graph)
        Collections.sort(edges);

        UnionFind unionFind = new UnionFind(nodes.size());
        for (Edge a: edges) {

            if(unionFind.isConnected(a.getStart().getId(), a.getEnd().getId())) {
                continue;
            }

            unionFind.unify(a.getStart().getId(), a.getEnd().getId());
            tree.add(a);


            adjacencyList.computeIfAbsent(a.getStart(), k -> new ArrayList<>()).add(a.getEnd());
            adjacencyList.computeIfAbsent(a.getEnd(), k -> new ArrayList<>()).add(a.getStart());
        }


        for (Edge a: tree) {
            int row = (a.getStart().getX() + a.getEnd().getX())/2;
            int column = (a.getStart().getY() + a.getEnd().getY())/2;

            for (Cell[] c: maze) {
                for (Cell b : c) {

                    if(b.getX() == row & b.getY() == column) {
                        nodes.add(b);
                    }
                }
            }
        }
        for (Cell a: nodes) {
            a.createPath();
        }

    }


    public boolean isCreated() {
        return this.created;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                sb.append(maze[i][j]);

            }
            sb.append("\n");
        }
        return sb.toString();
    }
}