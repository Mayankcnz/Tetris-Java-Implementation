package swen221.tetris.tetromino;

import swen221.tetris.logic.Rectangle;

/**
 * Represents a tetromino which can only perform one rotation operation.
 *
 * @author David J. Pearce
 * @author Marco Servetto
 *
 */
public class StickyTetromino implements Tetromino {
	
	private int count;
	private Tetromino tetromino;  
	private Tetromino lastRotated;
	public StickyTetromino(int count, Tetromino tetromino) {
		this.count = count;
		this.tetromino = tetromino;

	}

	@Override
	public Color getColor() {
		
		return this.tetromino.getColor();
		
	}

	@Override
	public Orientation getOrientation() {
		
		return this.tetromino.getOrientation();
		
	}

	@Override
	public boolean isWithin(int x, int y) {
		
		return this.tetromino.isWithin(x, y);
	}

	@Override
	public Rectangle getBoundingBox() {
		
		return this.tetromino.getBoundingBox();
	}

	@Override
	public Tetromino rotate(int steps) {

		if(steps >= 3 && this.count == 3) { // if steps to be rotated is greater than or equal to 3 and no other rotation has been made
			StickyTetromino s =  new StickyTetromino(0,this.tetromino.rotate(3));
			s.setColor(Color.DARK_GRAY);
			return s;
		}else if(this.count == 0 && this.tetromino.getColor() != Color.DARK_GRAY) { // have made 3 rotations, but the color is yet to be set to dark_gray
			StickyTetromino s =  new StickyTetromino(0,this.tetromino.rotate(0));
			s.setColor(Color.DARK_GRAY);
			return s;	
		}else if(this.count == 0 && this.tetromino.getColor() == Color.DARK_GRAY) { // made 3 rotations and the color is already set to dark_gray
			return new StickyTetromino(0,this.tetromino.rotate(0)); // then simply return the same stickytetromino with zero rotations
		}
		
		this.count = this.count - steps;
		
		if(this.count < 0 && this.tetromino.getColor() != Color.DARK_GRAY) { // handles the case where one rotation is made then reuqest to 3 roations has been made
			setColor(Color.DARK_GRAY);
			return new StickyTetromino(0, this.tetromino.rotate(1));
		}
		
		return new StickyTetromino(this.count,this.tetromino.rotate(steps));  // default: return a new rotated sticky tetromino 
	
	}
		

	@Override
	public void setColor(Color color) {
		this.tetromino.setColor(Color.DARK_GRAY);
	}
}
