/**
 * Create and control the game Tetris.
 * 
 * @CristianHabib CSC 143
 * @JunseiNagao CSC 143
 * @CarlosAlonsoGamboa CSC 143
 * 
 * We are in full compliance with the requirements of this assignment.
 *
 */
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.*;

public class Tetris extends JPanel {

	private Game game;
	private static Clip thisClip;
	private static AudioInputStream audioInputStream;
	private static String filePath;

	/**
	 * Sets up the parts for the Tetris game, display and user control
	 * @throws LineUnavailableException 
	 * @throws IOException 
	 * @throws UnsupportedAudioFileException 
	 */
	public Tetris() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		game = new Game(this);
		JFrame f = new JFrame("The Tetris Game");
		f.add(this);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(400, 550);
		f.setVisible(true);
		EventController ec = new EventController(game);
		f.addKeyListener(ec);
		setBackground(Color.YELLOW);
		
		filePath = "Tetris Theme on KazooRecorder.wav";
		setTunes();
	}

	/**
	 * Updates the display
	 */
	public void update() {
		repaint();
	}

	/**
	 * Paint the current state of the game
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		game.draw(g);
		if (game.isGameOver()) {
			g.setFont(new Font("Palatino", Font.BOLD, 40));
			g.setColor(Color.BLACK);
			g.drawString("GAME OVER", 80, 300);
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					new Tetris();
				} catch (UnsupportedAudioFileException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (LineUnavailableException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static void setTunes() throws UnsupportedAudioFileException,IOException,LineUnavailableException {
		audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
		thisClip = AudioSystem.getClip();
		thisClip.open(audioInputStream);
		FloatControl gainControl = (FloatControl) thisClip.getControl(FloatControl.Type.MASTER_GAIN);
		gainControl.setValue(-30.0f);
		thisClip.loop(Clip.LOOP_CONTINUOUSLY);
	}

}
