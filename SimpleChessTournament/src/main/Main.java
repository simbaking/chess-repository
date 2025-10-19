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

	public static void main(String[] args) {
		
		
		Player[] players = new Player[8];
		ArrayList<Player> playersInGame = new ArrayList<>();
		int[] playerIds = new int[8];
		int userId;
		ArrayList<ChessGame> games = new ArrayList<>();
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
			        		
			        		ChessGame game = new ChessGame(games, playersTemp, playersInGame, gameOffers, userId, playersTemp[buttonIndex].id);
			        		games.add(game);
			        		
			        	}
			        	
			        	else {
			        		
			        		ChessGame game = new ChessGame(games, playersTemp, playersInGame, gameOffers, playersTemp[buttonIndex].id, userId);
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
			System.out.println(players[0].playerName);
			
			for(int i = 0; i < 8; i++) {
				
				playerCards[i].setText("<html>Pos " + (i + 1) + " : " + players[i].playerName
						+ "<br> score : " + players[i].score + "</html");
				playerCards[i].setFont(new Font("Arial", Font.PLAIN, 15));
				
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
			
			ArrayList<ChessGame> gamesTemp = games;
			
			for(ChessGame game : gamesTemp) {
				
				if(game.getIsChessGameOver()) {
					
					if(!game.getScoreUpdated()) {

						for(Player player : players) {

							if(player.id == game.getWhiteId()) {
								
								player.score += game.getWhiteScore();
								
								playersInGame.remove(player);
								
							}

							if(player.id == game.getBlackId()) {
								
								player.score += game.getBlackScore();
								
								playersInGame.remove(player);
								
							}
							
						}
						
						game.scoreUpdated();
						
					}
					
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

				if(rankingPlayer.score > rankedPlayers[i].score) {
					
					rankingPlayers[i] = rankingPlayer;
					
					for(int j = 1; (i + j) < rearrangePlayers.length; j++) {
						rankingPlayers[i + j] = rankedPlayers[i + (j - 1)];
					}
					
					rankedPlayers = rankingPlayers;
					System.out.println(rankedPlayers[0].playerName + " r");
					break;
				}
				
			}
			
		}
		
		ogPlayers = rankedPlayers;
		
		System.out.println(ogPlayers[0].playerName);
		
		for(int i = 0; i < playerIds.length; i++) {
			rankedIds[i] = rankedPlayers[i].id;
		}
		
		playerIds = rankedIds;
		
		return ogPlayers;
		
	}

}
