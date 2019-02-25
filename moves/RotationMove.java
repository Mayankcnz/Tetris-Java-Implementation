package swen221.tetris.moves;

import swen221.tetris.logic.Board;
import swen221.tetris.logic.Rectangle;
import swen221.tetris.tetromino.ActiveTetromino;
import swen221.tetris.tetromino.Tetromino;

/**
 * Implements a rotation move which is either clockwise or anti-clockwise.
 *
 * @author David J. Pearce
 * @author Marco Servetto
 *
 */
public class RotationMove implements Move {
	/**
	 * Rotate a given number of steps in a clockwise direction (if positive), or an
	 * anti-clockwise direction (if negative).
	 */
	private final int rotation;
	private ActiveTetromino rotatedTetromino;

	public RotationMove(int steps) {
		this.rotation = steps;
	}

	
	@Override
	public boolean isValid(Board board) { // so we cannot rotate if we are touching the surface, implement that functionality first
		
		
		if(board.getTetromino() == null)return false;  // if no active tetromino
		
		ActiveTetromino a = board.getTetromino();
		
		ActiveTetromino tetromino = board.getTetromino().rotate(rotation); // rotating first (temporary)
		this.rotatedTetromino = tetromino;
		Rectangle rect = tetromino.getBoundingBox(); // get the new bounds
	
		int minY = rect.getMinY(), maxY = rect.getMaxY(); // top y
		int minX = rect.getMinX(), maxX = rect.getMaxX(); // left x
		
		if(minY <= 0)return false;  // means that the temporary rotated tetromino is less than the minimum base height
		if(minX < 0)return false;  // goes outside the left walls
		if(maxX >= board.getWidth())return false; // goes outside the right walls
	
		if(!checkBoard(board, rect, tetromino))return false; 
		
		return true;
	}
	
	
	
	
	/**
	 * This Method checks its feasible to rotate the current Tetromino. It takes a Temporary Rotated Tetromino
	 * and checks whether the new location that the teromino is at right now overlaps any other tetromino
	 * 
	 * @param board  The current board 
	 * @param rect  The Rectangle covering the current AcitiveTetromino
	 * @param tetromino ActiveTetromino
	 * @return
	 */
	private boolean checkBoard(Board board, Rectangle rect, Tetromino tetromino) {// so this method checks, if we are not on top of any other tetromino
		
		for(int i = rect.getMinX(); i <= rect.getMaxX(); i++) {
			for(int j = rect.getMinY(); j <= rect.getMaxY() && j < board.getHeight(); j++) {
				if(tetromino.isWithin(i, j)) {
					if(board.getPlacedTetrominoAt(i, j) != null)return false;
				}
			}
		}
		return true;
	}

	/**
	 * Get the number of rotation steps in this move.
	 *
	 * @return
	 */
	public int getRotation() {
		return rotation;
	}

	@Override
	public Board apply(Board board) {
		
		
		// Create copy of the board to prevent modifying its previous state.
		board = new Board(board);
		
		// Create a copy of this board which will be updated.
		//ActiveTetromino tetromino = board.getTetromino().rotate(rotation);

		// Apply the move to the new board, rather than to this board.
		board.updateTetromino(this.rotatedTetromino);
		// Return updated version of this board.
		return board;
	}

	@Override
	public String toString() {
		return "rotate " + rotation;
	}

}
