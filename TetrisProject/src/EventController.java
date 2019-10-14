/**
 * Handles events for the Tetris Game.  User events (key strokes) as well as periodic timer
 * events.
 * 
 * @author CSC 143
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Color;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.Timer;

public class EventController extends KeyAdapter implements ActionListener {

	private Game game; // current game: grid and current piece
	private Timer timer;

	private static final double PIECE_MOVE_TIME = 0.2; // wait 0.2 s every time
														// the piece moves down
														// increase to slow it
														// down

	private boolean gameOver;

	/**
	 * Creates an EventController to handle key and timer events.
	 * 
	 * @param game
	 *            the game this is controlling
	 */
	public EventController(Game game) {
		this.game = game;
		gameOver = false;
		double delay = 1000 * PIECE_MOVE_TIME; // in milliseconds
		timer = new Timer((int) delay, this);
		timer.setCoalesce(true); // if multiple events pending, bunch them to
		// 1 event
		timer.start();
	}

	/**
	 * Responds to special keys being pressed.
	 * 
	 * Currently just responds to the space key and the q(uit) key
	 */
	public void keyPressed(KeyEvent e) {
		// if 'Q', quit the game
		if (e.getKeyCode() == KeyEvent.VK_Q) {
			timer.stop();
			((JFrame) e.getSource()).dispose();
		}
		if (!gameOver) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_DOWN:
				handleMove(Direction.DOWN);
				break;
			case KeyEvent.VK_LEFT:	//Added left and right cases
				handleMove(Direction.LEFT);
				break;
			case KeyEvent.VK_RIGHT:
				handleMove(Direction.RIGHT);
				break;
			case KeyEvent.VK_UP: // Instant drop uppon Up key press
				if (game.getPiece() != null) {
				game.getPiece().instantDrop(); // Uses Game getter method to access the active piece's instantDrop method
				} 
				break;

			}
		}
	}

	/** Updates the game periodically based on a timer event */
	public void actionPerformed(ActionEvent e) {
		
		/**
		 * Transfered the pieceUpdate method from the Game class to use the timer from EventController.
		 * 
		 * Allows for extra adjustment of position when the piece reached the bottom.
		 */
		
		if(game.getPiece() == null) { 
			game.setPiece(new LShape(1, Grid.WIDTH / 2 - 1, game.getGrid())); // Makes a new piece 
		}
		else if (!game.getPiece().canMove(Direction.DOWN)) {
			Point[] p = game.getPiece().getLocations();
			Color c = game.getPiece().getColor();
			for (int i = 0; i < p.length; i++) {
				game.getGrid().set((int) p[i].getX(), (int) p[i].getY(), c);
			}
			game.setPiece(null);
		}
		
		// Periodic downward movement
		handleMove(Direction.DOWN);
	}

	/**
	 * Update the game by moving in the given direction
	 */
	private void handleMove(Direction direction) {
		game.movePiece(direction);
		gameOver = game.isGameOver();
		if (gameOver)
			timer.stop();
	}
}
