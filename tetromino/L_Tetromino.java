package swen221.tetris.tetromino;

import swen221.tetris.logic.Rectangle;

/**
 * The "L" tetromino.
 *
 * @author David J. Pearce
 * @author Marco Servetto
 *
 */
public class L_Tetromino extends AbstractTetromino {

	public L_Tetromino(Orientation orientation, Color color) {
		super(orientation, color);
	}

	@Override
	public boolean isWithin(int x, int y) {
		return (x >= -1 && x <= 1 && y == 0) || (x == 1 && y == 1);
	}

	@Override
	public Rectangle getBoundingBox() {
		
	     // Returns a new Rectangle bound according to the orientation the tetromino is facing
		switch(orientation) {
		case NORTH:
			return new Rectangle(-1, 1, 1, 0);
		case SOUTH:
			return new Rectangle(-1,0,1,-1);
		case EAST:
			return new Rectangle(0,1,1,-1);
		case WEST:
			return new Rectangle(-1, 1, 0 , -1 );
		}
		return null;
		
	}

	@Override
	public Tetromino rotate(int steps) {
		return new L_Tetromino(orientation.rotate(steps), color);
	}
}
