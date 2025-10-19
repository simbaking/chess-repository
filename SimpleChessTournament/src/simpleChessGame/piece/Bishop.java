package simpleChessGame.piece;


import simpleChessGame.*;
import simpleChessGame.chessGame.*;
import simpleChessGame.chessGame.Type;
import simpleChessGame.piece.*;


public class Bishop extends Piece{

	public Bishop(int color, int col, int row) {
		
		super(color, col, row);
		
		type = Type.BISHOP;
		
		if(color == GamePanel.WHITE) {
			image = getImage("/piece/white-bishop");
		}
		else {
			image = getImage("/piece/black-bishop");
		}
		
		
	}
	
	public boolean canMove(int targetCol, int targetRow) {
		
		if(isWithinBoard(targetCol, targetRow) && isSameSquare(targetCol, targetRow) == false) {
			
			if(Math.abs(targetCol - preCol) == Math.abs(targetRow - preRow)) {
				
				if(isValidSquare(targetCol, targetRow) && pieceIsOnDiagonalLine(targetCol, targetRow) == false) {
					
					return true;
					
				}
				
			}
			
		}
		
		return false;
		
	}

}