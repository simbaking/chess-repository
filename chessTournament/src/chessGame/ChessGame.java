package chessGame;

import javax.swing.*;
import chessGame.game.*;

public class ChessGame {
	
	public static int whitePlayerId;
	public static int blackPlayerId;

	public ChessGame() {
		
		
		JFrame window = new JFrame("chess game");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);// prefer this window.setResizable(true); if can work
		
		GamePanel gp = new GamePanel();
		window.add(gp);
		window.pack();
		
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		gp.launchGame();
		
		
	}

}
