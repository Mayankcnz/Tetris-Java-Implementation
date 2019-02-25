package swen221.tetris.moves;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

import javax.swing.plaf.synth.SynthSpinnerUI;

import swen221.tetris.logic.Board;
import swen221.tetris.logic.Rectangle;
import swen221.tetris.tetromino.ActiveTetromino;
import swen221.tetris.tetromino.Tetromino;

/**
 * Implements a "landing" move. That is, when the tetromino is placed on the
 * board properly.
 *
 * @author David J. Pearce
 * @author Marco Servetto
 *
 */
public class LandingMove implements Move {
	private int lines;
	

	public LandingMove() {
		this.lines = -1;
	}

	public LandingMove(int lines) {
		this.lines = lines;

	}

	@Override
	/**
	 * This Method returns true if the number of full rows is equal to the number of lines to be removed
	 * 
	 */
	public boolean isValid(Board board) {
		
		if(this.lines == -1)return true;
		
		int counter = 0;
		for(int row = 0; row < board.getHeight(); row++) {
			boolean flag = true;
			for(int col = 0; col < board.getWidth(); col++) {
				if(board.getTetrominoAt(col, row) == null) {
					flag = false; // if this row contains a placed tetromino then disregard this row
					break;
				}
			}
			if(flag) {
				counter++;
			}
		}
		
		
		if(counter != this.lines)return false;  // if the number of rows to be removed does not equals the lines been passed
		
		
		
	 return true;
	}

	@Override
	public Board apply(Board board) {
		// Create copy of the board to prevent modifying its previous state.
		board = new Board(board);
		// Place tetromino on board
		
		pushDownTetromino(board);
		if(board.getTetromino() != null)
		board.put(board.getTetromino());
	
		// Reset active tetromino
		board.updateTetromino(null);
	
		// Remove any full rows
		removeFullRows(board);
		// DOne!
		return board;
	}

	/**
	 * Remove any rows on the board which are now full.
	 * The Number of the rows removed is equal to the number of lines
	 * @param board
	 * @return
	 */
	private void removeFullRows(Board b) {
 
		int width = b.getWidth(), height = b.getHeight();
		
		for(int i = height-1; i >= 0; i--) { // start from the top because its easier to delete
			boolean rowIsFull = true;
			for(int j = 0; j < width; j++) {
				if(b.getTetrominoAt(j, i) == null) { // there is no tetromino, this means this row is not full
					rowIsFull = false;
					break;
				}
			}
			
			if(rowIsFull) { 
				for(int k = i; k < height -1 ; k++) {
					for(int j = 0; j < width; j++) {
						if(b.getTetrominoAt(j, k + 1) != null) { // if there is a tetrimino one step above, copy it over to the current location
							b.setPlacedTetromino(j,k, b.getTetrominoAt(j, k+1));
						}else {
							b.setPlacedTetromino(j, k, null); // there was no tetromino one step above, so set the current one to null
						}
					}
				}
			}
		}
	
	}
	
	/**
	 * This Method pushes down the current ActiveTetromino as far as possible i.e:
	 * 1: The tetromino should be touching either the base
	 * 2: or another tetromino
	 * 3: It cannot land in the mid air
	 * @param b
	 */
	private void pushDownTetromino(Board b) {
		
	if(b.getTetromino() == null)return;
	
	boolean isValid = true;
		while(isValid) { // if there remains some more space to drop down
			ActiveTetromino t = b.getTetromino();
			Rectangle rect = t.getBoundingBox();
			
			for(int i = rect.getMinX(); i <= rect.getMaxX(); i++) {
				for(int j = rect.getMinY(); j <= rect.getMaxY(); j++) {

				if(!t.isWithin(i, j)) continue;
				
				if((j-1) < 0 || b.getPlacedTetrominoAt(i, j-1) != null ) { // if we didnt reached the base or we not overlapping a tetromino
					isValid = false;
					break;
				}
			  }
			}
			if(isValid) { // if it is valid to drop down the next row
			ActiveTetromino tetromino = t.translate(0, -1); // calls the trasnlate method in the activetetromino class
			b.updateTetromino(tetromino); // update the board
			}
		}
	}


	@Override
	public String toString() {
		return "landing " + lines;
	}
}
