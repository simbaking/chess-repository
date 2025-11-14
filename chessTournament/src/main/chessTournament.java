package main;

import util.log.*;
import java.util.logging.*;

public class chessTournament extends Log{
	
	
	public static Player[] tournamentPlayers = new Player[8];
	public static int[] tournamentPlayerIds = new int[8];
	
	public Log l = new Log();

	public static void main(String[] args) {
		
		
		for(int i = 0; i < (tournamentPlayers.length - 1); i++) {
			tournamentPlayers[i] = new Player( "CPU " + (i + 1), true, tournamentPlayerIds);
		}
		logger.log(Level.INFO, "tournamentPlayers array initialized except last Player who will be the user" + 
				"\n ..................................................");
		
		
	}

	
}
