package main;

public class Player {
	
	
	public String playerName;
	
	public int playerId;
	public int score;
	
	public boolean inGame;
	public boolean playerIsCpu;
	
	public Player(String name, boolean isCpu, int[] tournamentPlayerIds){
		
		playerName = name;
		
		playerIsCpu = isCpu;
		
		inGame = false;
		
		for(int i = 0; i < tournamentPlayerIds.length; i++) {
			
			if(tournamentPlayerIds[i] == 0) {
				
				tournamentPlayerIds[i] = i+1;
				
				break;
			}
			
		}
		
	}
	
	

}
