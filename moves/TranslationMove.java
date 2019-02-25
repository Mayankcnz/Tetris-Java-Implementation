package swen221.tetris.moves;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import swen221.tetris.logic.Board;
import swen221.tetris.logic.Rectangle;
import swen221.tetris.tetromino.ActiveTetromino;
import swen221.tetris.tetromino.O_Tetromino;
import swen221.tetris.tetromino.Tetromino;

/**
 * Implements a translation move.
 *
 * @author David J. Pearce
 * @author Marco Servetto
 *
 */
public class TranslationMove implements Move {
	/**
	 * Amount to translate x-coordinate.
	 */
	private int dx;
	/**
	 * Amount to translate y-coordinate.
	 */
	private int dy;

	/**
	 * Construct new TranslationMove for a given amount of horizontal and vertical
	 * translation.
	 *
	 * @param dx
	 *            Amount to translate in horizontal direction.
	 * @param dy
	 *            Amount to translate in vertical direction.
	 */
	public TranslationMove(int dx, int dy) {
		this.dx = dx;
		this.dy = dy;

	}
		
	@Override
	public boolean isValid(Board board) {
		
		if(board.getTetromino() == null) {
			return false; // if no active tetromino
		}
		
		ActiveTetromino tet = board.getTetromino();
		Rectangle rect = tet.getBoundingBox();
		
		int getMinX = rect.getMinX(), getMaxX = rect.getMaxX();
		int getMaxY = rect.getMaxY(), getMinY = rect.getMinY();

		if(this.dx < 0 && this.dy == 0) { // if the turn is left
					
			int dx = (getMinX - Math.abs(this.dx));
			if(dx < 0 || !check(board, tet, dx, "left"))return false;
			
		}else if(this.dy < 0 ) { // if the turn is drop
					
			int dy1 = (getMinY  - Math.abs(this.dy));
			if(dy1 < 0 || !check(board, tet, dy1, "down"))return false;
		
		}else if(this.dx > 0) {// if the turn is to right
			
			int dx2 = (getMaxX + this.dx);
			if(dx2 >= board.getWidth() || !check(board, tet, dx2, "right"))return false;
		}

		return true;
	}
	
	
	/**
	 * This Method checks whether the new position after shifting the tetromino will overall other tetromino in the board
	 * 
	 * @param board  The current board
	 * @param tet Currently Active Tetromino
	 * @param shift The number of blocks the tetromino would be shifted to either of the directions by
	 * @param direction The direction to be shifted
	 * @return
	 */
	private boolean check(Board board, ActiveTetromino tet, int shift, String direction) {
		
		Rectangle rect = tet.getBoundingBox();
		
		int minX = rect.getMinX(), maxX = rect.getMaxX();
		int maxY = rect.getMaxY(), minY = rect.getMinY();
		
		Tetromino t = null;
		
		for(int i = minX; i <= maxX; i++) {
			for(int j = minY; j <= maxY; j++) {
				if(tet.isWithin(i, j)) {
					if(direction.equals("left")) { 
						if(board.getPlacedTetrominoAt(i-Math.abs(this.dx), j) != null)return false; // check if the board contains some other tetromino towards the left side
					}else if(direction.equals("down")) {
						if(board.getPlacedTetrominoAt(i, j-Math.abs(this.dy)) != null)return false; // check if the board contains some other tetromino towards the botton side
					}else if(direction.equals("right")) {
						if(board.getPlacedTetrominoAt(i+this.dx, j) != null)return false; // check if the board contains some other tetromino towards the right side
					}
				}
			}
		}

		return true;
		
	}
	


	@Override
	public Board apply(Board board) {
		// Create copy of the board to prevent modifying its previous state.
		board = new Board(board);
		// Apply translation for this move
		
		

		ActiveTetromino tetromino = board.getTetromino().translate(dx, dy);
		
		Rectangle rect = tetromino.getBoundingBox();
		
		// Apply the move to the new board.
		board.updateTetromino(tetromino);

		// Return updated version of board
		return board;
	}
	

	@Override
	public String toString() {
		if (dx > 0) {
			return "right " + dx;
		} else if (dx < 0) {
			return "left " + dx;
		} else {
			return "drop " + dy;
		}
	}
}
