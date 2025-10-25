package main;

import java.util.*;
import java.awt.Color;


import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import simpleChessGame.*;
import simpleChessGame.chessGame.*;
import simpleChessGame.piece.*;
public class Main {

	
	public static ArrayList<ChessGame> games = new ArrayList<>();
	public static ArrayList<Player> playersInGame = new ArrayList<>();
	public static Player[] players = new Player[8];
	public static int[] gameIds = new int[4];
	
	
	public static void main(String[] args) {
		
		
		int[] playerIds = new int[8];
		int userId;
		JLabel[] playerCards = new JLabel[8];
		JButton[] gameOffers = new JButton[8];
		
		Random random = new Random();
		
		for(int i = 0; i < 7; i++) {
			players[i] = new Player("cpu" + (i + 1), true, playerIds, i);
		}
		
		players[7] = new Player("user", false, playerIds, 7);
		userId = players[7].id;
		
		JFrame window = new JFrame("chess tournament");
		JPanel tp = new JPanel();
		
		tp.setLayout(null);
		
		tp.setPreferredSize(new Dimension(200,800));
		tp.setBackground(new Color(128, 64, 0));
		
		for(int i = 0; i < 8; i++) {
			
			playerCards[i] = new JLabel("Pos " + (i + 1) + " : " + players[i].playerName);
			playerCards[i].setBounds(30, (i * 100), 120, 50);
			tp.add(playerCards[i]);
			
		}
		

		for(int i = 0; i < 8; i++) {

			gameOffers[i] = new JButton(10 + " min time controls");
			// add ability for different time controls
			
			final int buttonIndex = i;
			final Player[] playersTemp = players;
			
		    gameOffers[i].addActionListener(new ActionListener() {
		        @Override
		        public void actionPerformed(ActionEvent e) {
		        	
		        	if(!playersInGame.contains(playersTemp[buttonIndex])) {

			        	playersInGame.add(playersTemp[buttonIndex]);
			        	gameOffers[buttonIndex].setText(playersTemp[buttonIndex].playerName + " is in " + 10 + " min game");

			        	for(Player user : playersTemp) {
			        		
			        		if(user.id == userId) {
			        			
			        			for(int i = 0; i < playersTemp.length; i++) {
			        				
			        				if(user == playersTemp[i]) {

					        			playersInGame.add(playersTemp[i]);
					        			gameOffers[i].setText(playersTemp[i].playerName + " is in " + 10 + " min game");
					        			
			        				}
			        				
			        			}
			        			
			        		}
			        		
			        	}
			        	
			        	boolean userColor = random.nextBoolean();
			        	
			        	if(userColor) {
			        		
			        		ChessGame game = new ChessGame(games, gameIds, playersTemp, playersInGame, gameOffers,
			        				userId, playersTemp[buttonIndex].id);
			        		games.add(game);
			        		
			        	}
			        	
			        	else {
			        		
			        		ChessGame game = new ChessGame(games, gameIds, playersTemp, playersInGame, gameOffers,
			        				playersTemp[buttonIndex].id, userId);
			        		games.add(game);
			        		
			        	}
			        	
		        	}
		        	
		        }
		        
		    });
		    
		    gameOffers[i].setBounds(25, (i * 100) + 50, 150, 50);
		    gameOffers[i].setFont(new Font("Arial", Font.PLAIN, 12));
		    tp.add(gameOffers[i]);

		}
		
		
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);// prefer this window.setResizable(true); if can work
		
		window.add(tp);
		window.pack();
		
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		

		while(true) {
			
			
			players = orderPlayersByScore(players, playerIds);
			
			for(int i = 0; i < 8; i++) {
				
				if(players[i] != null && playerCards[i] != null) {

					playerCards[i].setText("<html>Pos " + (i + 1) + " : " + players[i].playerName
							+ "<br> score : " + players[i].score + "</html");
					playerCards[i].setFont(new Font("Arial", Font.PLAIN, 15));
					
				}
				
				if(playersInGame.contains(players[i])) {
					
					gameOffers[i].setText(players[i].playerName + " is in " + 10 + " min game");
					
				}
				
				else {
					gameOffers[i].setText(10 + " min time controls");
					}
				
				boolean playing = false;
				
				if(players != null && playersInGame != null) {
					
					ArrayList<Player> playersInGameTemp = playersInGame;
					
					for(Player inGame : playersInGameTemp) {
						
						if(inGame == players[i]) {
							playing = true;
						}
						
					}
					
				}
				
				if(!playing) {
					gameOffers[i].setText(10 + " min time controls");
				}
				
			}
			

			for(int i = 0; i < 8; i++) {
				
				if(!playersInGame.contains(players[i])) {
					

					gameOffers[i].setText(10 + " min time controls");
					// add ability for different time controls
					
					final int buttonIndex = i;
					final Player[] playersTemp = players;
					
					gameOffers[i].removeActionListener(gameOffers[i].getActionListeners()[0]);
				    gameOffers[i].addActionListener(new ActionListener() {
				        @Override
				        public void actionPerformed(ActionEvent e) {
				        	
				        	if(!playersInGame.contains(playersTemp[buttonIndex])) {

					        	playersInGame.add(playersTemp[buttonIndex]);
					        	gameOffers[buttonIndex].setText(playersTemp[buttonIndex].playerName + " is in " + 10 + " min game");
					        	
					        	for(Player user : playersTemp) {
					        		
					        		if(user != null) {

						        		if(user.id == userId) {
						        			
						        			for(int i = 0; i < playersTemp.length; i++) {
						        				
						        				if(user == playersTemp[i]) {

								        			playersInGame.add(playersTemp[i]);
								        			gameOffers[i].setText(playersTemp[i].playerName + " is in " + 10 + " min game");
								        			
						        				}
						        				
						        			}
						        			
						        		}
						        		
					        		}
					        		
					        	}
					        	
					        	boolean userColor = random.nextBoolean();
					        	
					        	if(userColor) {
					        		
					        		ChessGame game = new ChessGame(games, gameIds, playersTemp, playersInGame, gameOffers,
					        				userId, playersTemp[buttonIndex].id);
					        		
					        		games.add(game);
					        		
					        	}
					        	
					        	else {
					        		
					        		ChessGame game = new ChessGame(games, gameIds, playersTemp, playersInGame, gameOffers,
					        				playersTemp[buttonIndex].id, userId);
					        		
					        		games.add(game);
					        		
					        	}
					        	
				        	}
				        	
				        }
				        
				    });
				    
				    gameOffers[i].setBounds(25, (i * 100) + 50, 150, 50);
				    gameOffers[i].setFont(new Font("Arial", Font.PLAIN, 12));
				    tp.add(gameOffers[i]);
				    
					
				}
				
			}
			
		}
		
		
	}
	
	public static Player[] orderPlayersByScore(Player[] ogPlayers, int[] playerIds){

		Player[] rearrangePlayers = ogPlayers;
		Player[] rankingPlayers = new Player[8];
		Player[] rankedPlayers = ogPlayers;

		int[] rankedIds = new int[8];
		
		for(Player rankingPlayer : rearrangePlayers) {
			
			for(int i = 0; i < rearrangePlayers.length; i++) {
				
				if(rankedPlayers[i] != null) {

					if(rankingPlayer.score > rankedPlayers[i].score) {
						
						rankingPlayers[i] = rankingPlayer;
						
						int extraShift = 0; // the amount the new list shifts the old to make space for the added player at top by default is 1
						
						for(int j = 1; (i + j) < rearrangePlayers.length; j++) {
							
							
							if(rankingPlayers[i + j] != rankingPlayer) {
								rankingPlayers[i + j] = rankedPlayers[i + (j - 1) + extraShift];
							}
							
							else {
								
								rankingPlayers[i + j] = rankedPlayers[i + (j - 1) + extraShift];
								extraShift = 1;
								
							}
							
						}
						
						rankedPlayers = rankingPlayers;
						break;
					}
					
					else if(rankingPlayer.id == rankedPlayers[i].id) {

						rankingPlayers[i] = rankingPlayer;
						
						int extraShift = 0; // the amount the new list shifts the old to make space for the added player at top by default is 1
						
						for(int j = 1; (i + j) < rearrangePlayers.length; j++) {
							
							
							if(rankingPlayers[i + j] != rankingPlayer) {
								rankingPlayers[i + j] = rankedPlayers[i + (j - 1) + extraShift];
							}
							
							else {
								
								rankingPlayers[i + j] = rankedPlayers[i + (j - 1) + extraShift];
								extraShift = 1;
								
							}
							
						}
						
						rankedPlayers = rankingPlayers;
						break;
					}
					
				}
				
			}
			
		}
		
		ogPlayers = rankedPlayers;
		
		for(int i = 0; i < playerIds.length; i++) {
			if(rankedPlayers[i] != null) {
				rankedIds[i] = rankedPlayers[i].id;
			}
		}
		
		playerIds = rankedIds;
		
		return ogPlayers;
		
	}
	
}
