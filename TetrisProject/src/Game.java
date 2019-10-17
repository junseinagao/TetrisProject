import java.awt.Graphics;
import java.awt.Point;
import java.util.Random;


/**
 * Manages the game Tetris. Keeps track of the current piece and the grid.
 * Updates the display whenever the state of the game has changed.
 * 
 * @author CSC 143
 */
public class Game {

	private Grid grid; // the grid that makes up the Tetris board

	private Tetris display; // the visual for the Tetris game

	private Piece piece; // the current piece that is dropping
	
	

	private boolean isOver; // has the game finished?
	
	public static Random rand = new Random();

	/**
	 * Creates a Tetris game
	 * 
	 * @param Tetris
	 *            the display
	 */
	public Game(Tetris display) {
		grid = new Grid();
		this.display = display;
		piece = pieceSelection(rand.nextInt(7));
		isOver = false;
	}
	
	public Piece pieceSelection(int rdm) {
		switch (rdm) {
		case 0:
			return new IShape(1, Grid.WIDTH / 2 - 1, grid);
		case 1:
			return new OShape(0, Grid.WIDTH / 2 - 1, grid);
		case 2:
			return new TShape(0, Grid.WIDTH / 2 - 1, grid);
		case 3:
			return new SShape(0, Grid.WIDTH / 2 - 1, grid);
		case 4:
			return new ZShape(0, Grid.WIDTH / 2 - 1, grid);
		case 5:
			return new LShape(1, Grid.WIDTH / 2 - 1, grid);
		case 6:
			return new JShape(1, Grid.WIDTH / 2 - 1, grid);
		default:
			return null;
		}
		
	}

	/**
	 * Draws the current state of the game
	 * 
	 * @param g
	 *            the Graphics context on which to draw
	 */
	public void draw(Graphics g) {
		grid.draw(g);
		if (piece != null) {
			piece.draw(g);
		}
	}

	/**
	 * Moves the piece in the given direction
	 * 
	 * @param the
	 *            direction to move
	 */
	public void movePiece(Direction direction) {
		if (piece != null) {
			piece.move(direction);
		}
		//updatePiece();
		display.update();
		grid.checkRows();
	}

	/**
	 * Returns true if the game is over
	 */
	public boolean isGameOver() {
		// game is over if the piece occupies the same space as some non-empty
		// part of the grid. Usually happens when a new piece is made
		if (piece == null) {
			return false;
		}

		// check if game is already over
		if (isOver) {
			return true;
		}

		// check every part of the piece
		Point[] p = piece.getLocations();
		for (int i = 0; i < p.length; i++) {
			if (grid.isSet((int) p[i].getX(), (int) p[i].getY())) {
				isOver = true;
				return true;
			}
		}
		return false;
	}

	
	
	/**
	 * Setter and getter methods to transfer the updatePiece method to Event Controller
	 */
	public Piece getPiece() {
		return piece;
	}
	
	public void setPiece(Piece p) {
		this.piece = p;
	}
	
	//public Piece[] getPieces() {
		//return pieces;
	//}
	
	public Grid getGrid() {
		return grid;
	}

}
