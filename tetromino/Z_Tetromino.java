package swen221.tetris.tetromino;

import swen221.tetris.logic.Rectangle;

/**
 * The "Z" tetromino.
 *
 * @author David J. Pearce
 * @author Marco Servetto
 *
 */
public class Z_Tetromino extends AbstractTetromino {
	public Z_Tetromino(Orientation orientation, Color color) {
		super(orientation, color);
	}

	@Override
	public boolean isWithin(int x, int y) {
		return (x == -1 && y == 1) || (x == 0 && y == 1) || (x == 0 && y == 0) || (x == 1 && y == 0);
	}

	@Override
	public Rectangle getBoundingBox() { // have to change the rectangle bounding box on rotation
		
		switch(orientation) {
		
		case NORTH:
			return new Rectangle(-1, 1, 1, 0);
		case SOUTH:
			return new Rectangle(-1, 0, 1, -1);
		case EAST:
			return new Rectangle(0,1,1,-1);
		case WEST:
			return new Rectangle(-1, 1, 0, -1);
		}
		
		return null;
	}

	@Override
	public Tetromino rotate(int steps) {
		return new Z_Tetromino(orientation.rotate(steps), color);
	}
}
