import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public interface Piece {

	public abstract void draw(Graphics g);
	
	public abstract boolean canMove(Direction direction);
	
	public abstract void instantDrop();
	
	public abstract void move(Direction direction);
	
	public abstract void rotate(Direction direction);
	
	public abstract Point[] getLocations();
	
	public abstract Color getColor();
}
