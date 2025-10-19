package main;

public class Player {
	
	public String playerName;
	
	public int id;
	public int score;
	
	boolean isCpu;

	public Player(int[] idList, int posToAdd) {
		
		for(int i = 0; i < idList.length; i++) {
			
			boolean isFree = true;
			
			for(int oldId : idList) {
				
				if(i == oldId) {
					isFree = false;
				}
				
			}
			
			if(isFree) {
				
				id = i;
				idList[posToAdd] = i;
				break;
				
			}
			
		}
		
	}

	public Player(boolean cpu, int[] idList, int posToAdd) {

		for(int i = 0; i < idList.length; i++) {
			
			boolean isFree = true;
			
			for(int oldId : idList) {
				
				if(i == oldId) {
					isFree = false;
				}
				
			}
			
			if(isFree) {
				
				id = i;
				idList[posToAdd] = i;
				break;
				
			}
			
		}
		
		isCpu = cpu;
		
	}

	public Player(String name, int[] idList, int posToAdd) {
		
		for(int i = 0; i < idList.length; i++) {
			
			boolean isFree = true;
			
			for(int oldId : idList) {
				
				if(i == oldId) {
					isFree = false;
				}
				
			}
			
			if(isFree) {
				
				id = i;
				idList[posToAdd] = i;
				break;
				
			}
			
		}
		
		playerName = name;
		
	}

	public Player(String name, boolean cpu, int[] idList, int posToAdd) {

		for(int i = 0; i < idList.length; i++) {
			
			boolean isFree = true;
			
			for(int oldId : idList) {
				
				if(i == oldId) {
					isFree = false;
				}
				
			}
			
			if(isFree) {
				
				id = i;
				idList[posToAdd] = i;
				break;
				
			}
			
		}
		
		isCpu = cpu;
		playerName = name;
		
	}
	
}
