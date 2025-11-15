package main;

import util.log.*;
import java.util.*;
import java.util.logging.*;
import java.util.ArrayList.*;
import chessGame.*;

public class chessTournament extends Log{
	
	
	public static Player[] tournamentPlayers = new Player[8];
	public static int[] tournamentPlayerIds = new int[8];
	public static ArrayList<ChessGame> games = new ArrayList<>();
	
	public static Log l = new Log();

	public static void main(String[] args) {
		
		
		int userId = 1;
		
		l.init();
		
		tournamentPlayerIds[0] = userId;
		l.logger.log(Level.INFO, "user id initailized" +
				"\n ..................................................");
		
		for(int i = 0; i < (tournamentPlayers.length - 1); i++) {
			tournamentPlayers[i] = new Player( "CPU " + (i + 1), true, tournamentPlayerIds);
		}
		l.logger.log(Level.INFO, "tournamentPlayers array initialized except last Player who will be the user" + 
				"\n ..................................................");
		
		
	}

	
}
