package swen221.tetris.moves;

import swen221.tetris.logic.Board;
import swen221.tetris.logic.Game;
import swen221.tetris.logic.Rectangle;
import swen221.tetris.tetromino.ActiveTetromino;
import swen221.tetris.tetromino.Tetromino;

public class NextMove implements Move {
	private final Tetromino tetromino;

	public NextMove(Tetromino tetromino) {
		this.tetromino = tetromino;
	}

	/**
	 * Get the underlying tetromino on which this is based.
	 *
	 * @return
	 */
	public Tetromino getTetromino() {
		return tetromino;
	}

	@Override
	/**
	 * This Method determines whether it is valid to extract a tetromino
	 * 1: If the tetromino at the spawning area overlaps an existing tetromino, the method should return false
	 */
	public boolean isValid(Board board) {
		
		int cx = board.getWidth() / 2;
		int cy = board.getHeight() - 2;
		
		Board tempBoard = new Board(board); // create a copy of the board
		tempBoard.updateTetromino(new ActiveTetromino(cx, cy, tetromino)); 
		
		ActiveTetromino  tet =  tempBoard.getTetromino(); 
		Rectangle rect = tet.getBoundingBox();
		
		for(int i = rect.getMinX(); i < rect.getMaxX(); i++) {
			for(int j = rect.getMinY(); j < rect.getMaxY(); j++) {
				if(tempBoard.getPlacedTetrominoAt(i, j) != null) { // check if the active tetromino overlaps with an existing tetromino
					return false;
				}
			}
		}
		
		return board.getTetromino() == null;
	}

	@Override
	public Board apply(Board board) {
		board = new Board(board);
		// Determine center for next tetromino
		int cx = board.getWidth() / 2;
		int cy = board.getHeight() - 2;
		
		board.updateTetromino(new ActiveTetromino(cx, cy, tetromino));
		// Done!
		return board;
	}

	@Override
	public String toString() {
		// Determine first character of tetromino name
		char c = tetromino.getClass().getSimpleName().charAt(0);
		// Get color name in lowercase
		String color = tetromino.getColor().toString().toLowerCase();
		// Done
		return c + " " + color;
	}
}
