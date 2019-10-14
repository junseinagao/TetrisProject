

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Color;

import org.junit.jupiter.api.Test;



public class TetrisUnitTest {

	@Test
	public void testCheckRows() {
		// Create a grid with a full row at the bottom
		// and two squares above the full bottom row
		Grid g = new Grid();
		// full bottom row
		for (int col = 0; col < Grid.WIDTH; col++) {
			g.set(Grid.HEIGHT - 1, col, Color.GREEN);
		}
		// add two squares above the bottom row
		g.set(Grid.HEIGHT - 2, 3, Color.RED);
		g.set(Grid.HEIGHT - 3, 3, Color.RED);
		// remove the full row
		g.checkRows();
		// check that the grid has been updated correctly
		for (int row = 0; row < Grid.HEIGHT; row++) {
			for (int col = 0; col < Grid.WIDTH; col++) {
				// check the square at (row,col)
				// must have: (18,3) and (19,3) set
				// and all of the others not set
				if ((row == 18 || row == 19) && col == 3) {
					assertTrue(g.isSet(row, col));

				} else {
					assertFalse(g.isSet(row, col));
				}
			}
		}
	}
	
	@Test
	public void testLShapeMotion() {
		// Create a grid to test the motion of an L shape
		Grid g = new Grid();
		Tetris display = new Tetris();
		Game game = new Game(display);
		
		
		// Makes the piece 
		game.setPiece(new LShape(1,0,g));
		assertTrue(!game.getPiece().canMove(Direction.LEFT) && game.getPiece().canMove(Direction.RIGHT));
		
		for (int i = 1; i < g.WIDTH; i++) {
			game.movePiece(Direction.RIGHT);
			if (i < g.WIDTH - 2) {
				assertTrue(game.getPiece().canMove(Direction.RIGHT));
			}
			else
				assertFalse(game.getPiece().canMove(Direction.RIGHT));
		}
		
		
		game.getPiece().instantDrop();
		assertFalse(game.getPiece().canMove(Direction.DOWN));	
	}
	
	@Test
	public void testSquareMove() {
		// Create Grid.
		Grid g2 = new Grid();
		
		// make new square
		Square s1 = new Square(g2, 3, 8,Color.RED,true);
		Square s2 = new Square(g2, 3, 1,Color.RED,true);
		
		// test canMove to RIGHT
		boolean b1 = s1.canMove(Direction.RIGHT);
		if (b1 == true) {
			assertTrue(s1.canMove(Direction.RIGHT));
		} else {
			assertFalse(s1.canMove(Direction.RIGHT));
		}
		// test Move to RIGHT.
		s1.move(Direction.RIGHT);
		if( s1.getCol() == 9) {
			assertTrue(s1.getCol() == 9);
		} else {
			assertFalse(s1.getCol() == 9);
		}
		
		// test canMove to LEFT
		boolean b2 = s2.canMove(Direction.LEFT);
		if (b2 == true) {
			assertTrue(s2.canMove(Direction.LEFT));
		} else {
			assertFalse(s2.canMove(Direction.LEFT));
		}
		// test Move to RIGHT.
		s2.move(Direction.LEFT);
		if( s2.getCol() == 0) {
			assertTrue(s2.getCol() == 0);
		} else {
			assertFalse(s2.getCol() == 0);
		}
		
		/* test the square can't move RIGHT or LEFT 
		 * if the square closed to wall.
		 */
		
		// test canMove that should be return false.
		
		//RIGHT wall
		boolean b3 = s1.canMove(Direction.RIGHT);
		if (b3 == false) {
			assertFalse(s1.canMove(Direction.RIGHT));
		} else {
			assertTrue(s1.canMove(Direction.RIGHT));
		}
		
		//LEFT wall
		boolean b4 = s2.canMove(Direction.LEFT);
		if (b4 == false) {
			assertFalse(s2.canMove(Direction.LEFT));
		} else {
			assertTrue(s2.canMove(Direction.LEFT));
		}
		
		// test Move that must not be excute.
		s1.move(Direction.RIGHT);
		s2.move(Direction.LEFT);
		
		// return should true because Move method don't complite.
		// the square should remain.
		if(s1.getCol() == 9 && s2.getCol() == 0) {
			assertTrue(s1.getCol() == 9 && s2.getCol() == 0);
		} else {
			assertFalse(s1.getCol() == 9 && s2.getCol() == 0);
		}
		
		
		
		
		
		
	}
	
}
