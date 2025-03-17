package chess;

public class Vector2D {
    public int x;
    public int y;

    public Vector2D(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Vector2D add(Vector2D other){
        return new Vector2D(x + other.x, y + other.y);
    }

    public Vector2D add(int x, int y){
        return new Vector2D(this.x + x, this.y + y);
    }

    public Vector2D sub(Vector2D other){
        return new Vector2D(x - other.x, y - other.y);
    }

    public Vector2D sub(int x, int y){
        return new Vector2D(this.x - x, this.y - y);
    }

    public Vector2D mul(int scalar){
        return new Vector2D(x * scalar, y * scalar);
    }

    public Vector2D div(int scalar){
        return new Vector2D(x / scalar, y / scalar);
    }

    @Override
    public boolean equals(Object obj){
        if (!(obj instanceof Vector2D other)){return false;}
        return x == other.x && y == other.y;
    }
    @Override
    public int hashCode() {
        return y*8 + x;
    }

    public String toString(){
        return "(" + x + ", " + y + ")";
    }
    public boolean inBounds(){
        return x >= 0 && x <= 7 && y >= 0 && y <= 7;
    }
}
