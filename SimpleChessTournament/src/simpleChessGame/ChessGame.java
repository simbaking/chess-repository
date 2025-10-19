package simpleChessGame;

import java.util.ArrayList;

import javax.swing.*;

import simpleChessGame.*;
import simpleChessGame.chessGame.*;
import simpleChessGame.piece.*;
import main.*;

public class ChessGame extends Thread{
	
	
	GamePanel gp = new GamePanel();
	int gameId;
	int whiteId;
	int blackId;

	public ChessGame(ArrayList<ChessGame> games) {
		
		
		if(!games.contains(this)) {

			JFrame window = new JFrame("chess game");
			//window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			window.setResizable(false);// prefer this window.setResizable(true); if can work
			
			window.add(gp);
			window.pack();
			
			window.setLocationRelativeTo(null);
			window.setVisible(true);
			
			gp.launchGame();
			games.add(this);
			
		}
		
		
	}
	
	public ChessGame(ArrayList<ChessGame> games, Player[] players, ArrayList<Player> playersInGame, JButton[] gameOffers,
			int whitePlayerId, int blackPlayerId) {
		
		
		whiteId = whitePlayerId;
		gp.whiteId = whiteId;
		blackId = blackPlayerId;
		gp.blackId = blackId;

		if(!games.contains(this)) {

			JFrame window = new JFrame("chess game");
			//window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			window.setResizable(false);// prefer this window.setResizable(true); if can work
			
			window.add(gp);
			window.pack();
			
			window.setLocationRelativeTo(null);
			window.setVisible(true);
			
			gp.launchGame();
			games.add(this);
			
		}
		
		
	}

	public int[] getChessGameScore() {
		return gp.getScore();
	}

	public boolean getIsChessGameOver() {
		return gp.getIsGameOver();
	}

	public int getWhiteScore() {
		return gp.getWhiteScore();
	}

	public int getBlackScore() {
		return gp.getBlackScore();
	}

	public int getWhiteId() {
		return gp.whiteId;
	}

	public int getBlackId() {
		return gp.blackId;
	}
	
	public boolean getScoreUpdated() {
		return gp.scoreupdated;
	}
	
	public void scoreUpdated() {
		gp.scoreupdated = true;
	}
	
	public int getGameTime() {
		return (gp.gameMinutes * 60) + gp.gameSeconds;
	}
	
	
	
}
