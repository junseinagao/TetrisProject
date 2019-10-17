import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public abstract class AbstractPiece implements Piece {
	protected boolean ableToMove; // can this piece move

	protected Square[] square; // the squares that make up this piece

	// Made up of PIECE_COUNT squares
	protected Grid grid; // the board this piece is on

	// number of squares in one Tetris game piece
	protected static final int PIECE_COUNT = 4;
	
	public AbstractPiece(int r, int c, Grid g) {
		grid = g;
		square = new Square[PIECE_COUNT];
		ableToMove = true;
	}
	
	/**
	 * Draws the piece on the given Graphics context
	 */
	public void draw(Graphics g) {
		for (int i = 0; i < PIECE_COUNT; i++) {
			square[i].draw(g);
		}
	}
	
	/**
	 * Returns if this piece can move in the given direction
	 * 
	 */
	public boolean canMove(Direction direction) {
		if (!ableToMove)
			return false;

		// Each square must be able to move in that direction
		boolean answer = true;
		for (int i = 0; i < PIECE_COUNT; i++) {
			answer = answer && square[i].canMove(direction);
		}

		return answer;
	}
	
	public boolean canRotate(Direction direction) {
		switch (direction) {
		case LEFT:
			for (int i = 1; i < square.length; i++) {
				int posR = square[0].getRow()-square[i].getRow();
				int posC = square[0].getCol()-square[i].getCol();
				
				if ((square[0].getRow()+posC) < 0 || (square[0].getRow()+posC) > Grid.HEIGHT -1) {
					return false;
				}
				if ((square[0].getCol()-posR) < 0 || (square[0].getCol()-posR) > Grid.WIDTH -1) {
					return false;
				}
				if (grid.isSet(square[0].getRow()+posC, square[0].getCol()-posR)) {
					return false;
				}
								
			}
			return true;
			
		case RIGHT:
			for (int i = 1; i < square.length; i++) {
				int posR = square[0].getRow()-square[i].getRow();
				int posC = square[0].getCol()-square[i].getCol();
				
				if (square[0].getRow()-posC < 0 || square[0].getRow()-posC > Grid.HEIGHT -1) {
					return false;
				}
				if (square[0].getCol()+posR < 0 || square[0].getCol()+posR > Grid.WIDTH -1) {
					return false;
				}
				if (grid.isSet(square[0].getRow()-posC, square[0].getCol()+posR)) {
					return false;
				}
								
			}
			return true;
			
		default:
			return false;
			
		}
			
	}
	
	public void instantDrop() {
		while (canMove(Direction.DOWN)) {
			for (int i = 0; i < PIECE_COUNT; i++)
				square[i].move(Direction.DOWN);
			
		}
		ableToMove = false;
	}
	
	public void move(Direction direction) {
		if (canMove(direction)) {
			for (int i = 0; i < PIECE_COUNT; i++)
				square[i].move(direction);
		}
		// if we couldn't move, see if because we're at the bottom
		else if (direction == Direction.DOWN) {
			ableToMove = false;
		}
	}
	
	public void rotate(Direction dir) {
		
		if (canRotate(dir)) {
			if (dir == Direction.RIGHT) {
				rotateRight();
			}
			
			if (dir == Direction.LEFT) {
				rotateLeft();
			}
		}
		
			
	}
	
	private void rotateLeft() {
		Square[] temp = new Square[4];
		temp[0] = square[0];
		
		for (int i = 1; i < PIECE_COUNT; i++) {
		
		int posR = square[0].getRow() - square[i].getRow();
		int posC = square[0].getCol() - square[i].getCol();
		
		temp[i] = square[i];
		temp[i].setRow(square[0].getRow()+posC);
		temp[i].setCol(square[0].getCol()-posR);			
			
		}
		
		square = temp;
	
	}
	
	private void rotateRight() {
		Square[] temp = new Square[4];
		temp[0] = square[0];
		
		for (int i = 1; i < PIECE_COUNT; i++) {
		
		int posR = square[0].getRow() - square[i].getRow();
		int posC = square[0].getCol() - square[i].getCol();
		
		temp[i] = square[i];
		temp[i].setRow(square[0].getRow()-posC);
		temp[i].setCol(square[0].getCol()+posR);			
			
		}
		
		square = temp;
	}
	
	/**
	 * Returns the (row,col) grid coordinates occupied by this Piece
	 * 
	 * @return an Array of (row,col) Points
	 */
	public Point[] getLocations() {
		Point[] points = new Point[PIECE_COUNT];
		for (int i = 0; i < PIECE_COUNT; i++) {
			points[i] = new Point(square[i].getRow(), square[i].getCol());
		}
		return points;
	}

	/**
	 * Return the color of this piece
	 */
	public Color getColor() {
		// all squares of this piece have the same color
		return square[0].getColor();
	}
	
	
}
