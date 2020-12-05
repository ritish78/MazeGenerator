package maze;

public class Cell {
    private int x;
    private int y;
    private int id;
    private String wall = "\u2588\u2588";

    public Cell(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWall(){
        return this.wall;
    }

    public void createPath(){
        this.wall = "  ";
    }

    @Override
    public String toString() {
        return this.wall;
    }
}
