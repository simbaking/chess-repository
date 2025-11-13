package main;

import util.log.*;

public class chessTournament {
	
	
	public static Player[] tournamentPlayers = new Player[8];
	public static int[] tournamentPlayerIds = new int[8];
	
	public Log l = new Log();

	public static void main(String[] args) {
		
		
		for(int i = 0; i < (tournamentPlayers.length - 1); i++) {
			tournamentPlayers[i] = new Player( "CPU " + (i + 1), true, tournamentPlayerIds);
		}
		

	}

	
}
