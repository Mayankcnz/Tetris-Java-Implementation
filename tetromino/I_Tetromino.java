package swen221.tetris.tetromino;

import swen221.tetris.logic.Rectangle;
import swen221.tetris.tetromino.Tetromino.Orientation;

/**
 * I is the 'bar'.
 *
 * @author David J. Pearce
 * @author Marco Servetto
 */
public class I_Tetromino extends AbstractTetromino {
	public I_Tetromino(Orientation orientation, Color color) {
		super(orientation, color);
	}

	@Override
	public boolean isWithin(int x, int y) {
		return x >= -1 && x <= 2 && y == 0;
	}

	@Override
	public Rectangle getBoundingBox() {
		
switch(orientation) {
		
// Returns a new Rectangle bound according to the orientation the tetromino is facing
		case NORTH:
			return new Rectangle(-1, 0, 2, 0);
		case SOUTH:
			return new Rectangle(-2, 0, 1, 0);
		case EAST:
			return new Rectangle(0,1,0,-2);
		case WEST:
			return new Rectangle(0,2,0,-1);
		}
		
		return null;
	}

	@Override
	public Tetromino rotate(int steps) {
		return new I_Tetromino(orientation.rotate(steps), color);
	}
}
