package simpleChessGame.chessGame;

import javax.swing.*;


import javax.swing.Timer;

import main.Player;

import java.awt.*;
import java.util.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import simpleChessGame.*;
import simpleChessGame.chessGame.*;
import simpleChessGame.chessGame.Type;
import simpleChessGame.piece.*;

public class GamePanel extends JPanel implements Runnable{
	
	
	public static final int WIDTH = 1100;
	public static final int HEIGHT = 800;
	final int FPS = 60;
	Thread gameThread;
	Timer whiteTimer;
	Timer blackTimer;
	Timer totalTimer;
	JLabel whiteCountdownLabel;
	JLabel blackCountdownLabel;
	JLabel totalCountdownLabel;
	Board board = new Board();
	Mouse mouse = new Mouse();
	
	public Player[] tournamentPlayers;
	public ArrayList<Player> tournamentPlayersInGame;
	public JButton[] tournamentGameOffers;
	public static ArrayList<Piece> pieces = new ArrayList<>();
	public static ArrayList<Piece> simPieces = new ArrayList<>();
	ArrayList<Piece> promoPieces = new ArrayList<>();
	Piece activeP, checkingP;
	public static Piece castlingP;
	
	public static final int WHITEWON = 0;
	public static final int BLACKWON = 1;
	public static final int TIE = 3;
	int endCondition;
	public static final int WHITE = 0;
	public static final int BLACK = 1;
	int currentColor = WHITE;
	int resignColor;
	public int gameMinutes;
	public int gameSeconds;
	int whiteScore;
	int blackScore;
	public int whiteId;
	public int blackId;

	
	boolean canMove;
	boolean validSquare;
	boolean promotion;
	boolean resign;
	boolean gameover;
	boolean stalemate;
	boolean whiteOfferedTie;
	boolean blackOfferedTie;
	boolean scoreSent = false;
	public boolean scoreupdated = false;
	
	public GamePanel() {
		
		setPreferredSize(new Dimension(WIDTH,HEIGHT));
		setBackground(new Color(128, 64, 0));
		JButton whiteResign = new JButton("Resign (white)");
		JButton blackResign = new JButton("Resign (black)");

	    whiteResign.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	whiteResign();
	        }
	        
	    });

	    blackResign.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	blackResign();
	        }
	        
	    });
	    
	    JButton whiteOfferTie = new JButton("Offer Tie (white)");
		JButton blackOfferTie = new JButton("Offer Tie (black)");

	    whiteOfferTie.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	
	        	whiteOfferedTie = true;
	        	whiteOfferTie.setOpaque(true);
	        	whiteOfferTie.setText("Offered Tie (white)");
	        	
	        }
	        
	    });

	    blackOfferTie.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	
	        	blackOfferedTie = true;
	        	blackOfferTie.setOpaque(true);
	        	blackOfferTie.setText("Offered Tie (white)");
	        	
	        }
	        
	    });
	    
	    int whiteDelay = 1000;
	    whiteCountdownLabel = new JLabel("White Time : ");
	    whiteCountdownLabel.setFont(new Font("Arial", Font.PLAIN, 17));
	    whiteCountdownLabel.setForeground(new Color(255, 224, 128));
	    
	    whiteTimer = new Timer(whiteDelay, new ActionListener() {
	    	
	    	int minutesLeft = 10;
	        int secondsLeft = 0;

	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	
	        	if (minutesLeft >= 0) {

		            if (secondsLeft > 0) {
		            	
		                secondsLeft--;
		                whiteCountdownLabel.setText("White Time : " + minutesLeft + ":" + secondsLeft);
		                
		            } 
		            
		            else if(minutesLeft > 0) {
		            		
		            	minutesLeft--;
			            secondsLeft+=60;
			                
		            }

		            else {
		            	
		                ((Timer) e.getSource()).stop();
		                whiteCountdownLabel.setText("Time's Up!");
		                whiteResign();
		                
		            }	
		            
	            }
	        	
	        }
	    });

	    int blackDelay = 1000;
	    blackCountdownLabel = new JLabel("Black Time : ");
	    blackCountdownLabel.setFont(new Font("Arial", Font.PLAIN, 17));
	    blackCountdownLabel.setForeground(new Color(255, 224, 128));
	    
	    blackTimer = new Timer(blackDelay, new ActionListener() {
	    	
	    	int minutesLeft = 10;
	        int secondsLeft = 0;

	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	
	        	if (minutesLeft >= 0) {

		            if (secondsLeft > 0) {
		            	
		                secondsLeft--;
		                blackCountdownLabel.setText("Black Time : " + minutesLeft + ":" + secondsLeft);
		                
		            } 
		            
		            else if(minutesLeft > 0) {
		            		
		            	minutesLeft--;
			            secondsLeft+=60;
			                
		            }

		            else {
		            	
		                ((Timer) e.getSource()).stop();
		                blackCountdownLabel.setText("Time's Up!");
		                blackResign();
		                
		            }	
		            
	            }
	        	
	        }
	    });

	    int totalDelay = 1000;
	    totalCountdownLabel = new JLabel("Total Time : ");
	    totalCountdownLabel.setFont(new Font("Arial", Font.PLAIN, 17));
	    totalCountdownLabel.setForeground(new Color(255, 224, 128));
	    
	    totalTimer = new Timer(totalDelay, new ActionListener() {
	    	
	    	int minutes = 0;
	        int seconds = 0;

	        @Override
	        public void actionPerformed(ActionEvent e) {

	            if (seconds < 60) {
	            	
	                seconds++;
	                totalCountdownLabel.setText("Total Time : " + minutes + ":" + seconds);
	                
	            } 
	            
	            else {
	            		
	            	minutes++;
		            seconds-=60;
		                
	            }

	            if(gameover || stalemate){
	            	
	                ((Timer) e.getSource()).stop();
	                totalCountdownLabel.setText("Final time : " + minutes + ":" + seconds);
	                
	            }	
	            
	            gameMinutes = minutes;
                gameSeconds = seconds;
	            
	        }
	    });
	    
	    whiteTimer.start();
	    totalTimer.start();
	    
	    
	    this.setLayout(null);
	    
	    whiteResign.setBounds(800, 725, 150, 50);
	    blackResign.setBounds(800, 25, 150, 50);
	    whiteOfferTie.setBounds(950, 725, 150, 50);
	    whiteOfferTie.setBackground(new Color(255, 96, 0));
	    blackOfferTie.setBounds(950, 25, 150, 50);
	    blackOfferTie.setBackground(new Color(255, 96, 0));
	    whiteCountdownLabel.setBounds(875, 425, 150, 50);
	    blackCountdownLabel.setBounds(875, 325, 150, 50);
	    totalCountdownLabel.setBounds(875, 375, 150, 50);
		
	    this.add(whiteResign);
	    this.add(blackResign);
	    this.add(whiteOfferTie);
	    this.add(blackOfferTie);
	    this.add(whiteCountdownLabel);
	    this.add(blackCountdownLabel);
	    this.add(totalCountdownLabel);
		addMouseMotionListener(mouse);
		addMouseListener(mouse);
		
		setPieces();
		copyPieces(pieces, simPieces);
		
	}
	
	public void launchGame() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	public void launchGame(Player[] players, ArrayList<Player> playersInGame, JButton[] gameOffers, int whitePlayerId, int blackPlayerId) {
		
		
		gameThread = new Thread(this);
		gameThread.start();
		
		whiteId = whitePlayerId;
		blackId = blackPlayerId;
		tournamentPlayers = players;
		tournamentPlayersInGame = playersInGame;
		tournamentGameOffers = gameOffers;
		
		
	}
	
	public void setPieces() {
		
		pieces.add(new Pawn(WHITE,0,6));
		pieces.add(new Pawn(WHITE,1,6));
		pieces.add(new Pawn(WHITE,2,6));
		pieces.add(new Pawn(WHITE,3,6));
		pieces.add(new Pawn(WHITE,4,6));
		pieces.add(new Pawn(WHITE,5,6));
		pieces.add(new Pawn(WHITE,6,6));
		pieces.add(new Pawn(WHITE,7,6));
		pieces.add(new Rook(WHITE,0,7));
		pieces.add(new Rook(WHITE,7,7));
		pieces.add(new Knight(WHITE,1,7));
		pieces.add(new Knight(WHITE,6,7));
		pieces.add(new Bishop(WHITE,2,7));
		pieces.add(new Bishop(WHITE,5,7));
		pieces.add(new Queen(WHITE,3,7));
		pieces.add(new King(WHITE,4,7));
		
		pieces.add(new Pawn(BLACK,0,1));
		pieces.add(new Pawn(BLACK,1,1));
		pieces.add(new Pawn(BLACK,2,1));
		pieces.add(new Pawn(BLACK,3,1));
		pieces.add(new Pawn(BLACK,4,1));
		pieces.add(new Pawn(BLACK,5,1));
		pieces.add(new Pawn(BLACK,6,1));
		pieces.add(new Pawn(BLACK,7,1));
		pieces.add(new Rook(BLACK,0,0));
		pieces.add(new Rook(BLACK,7,0));
		pieces.add(new Knight(BLACK,1,0));
		pieces.add(new Knight(BLACK,6,0));
		pieces.add(new Bishop(BLACK,2,0));
		pieces.add(new Bishop(BLACK,5,0));
		pieces.add(new Queen(BLACK,3,0));
		pieces.add(new King(BLACK,4,0));
		
	}
	
	private void copyPieces(ArrayList<Piece> source, ArrayList<Piece> target) {
		
		target.clear();
		for(int i = 0; i < source.size(); i++) {
			target.add(source.get(i));
		}
		
	}

	@Override
	public void run() {
		
		
		double drawInterval = 1000000000 / FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long curentTime;
		
		while(gameThread != null) {
			
			curentTime = System.nanoTime();
			
			delta += (curentTime - lastTime) / drawInterval;
			lastTime = curentTime;
			
			if(delta >= 1) {
				update();
				repaint();
				delta --;
			}
			

			if(tournamentPlayers != null) {
				
				for(Player player : tournamentPlayers) {
					
					boolean playing = false;
					
					for(Player playerInGame : tournamentPlayersInGame) {
						
						if(player == playerInGame) {
							playing = true;
						}
						
					}
					
					if(!playing) {
						
						for(int i = 0; i < tournamentPlayers.length; i++) {
							
							if(player == tournamentPlayers[i]) {
								tournamentGameOffers[i].setText(10 + " min time controls");
							}
							
						}
						
					}
					
				}
				
			}
			
			
			if(this.getIsGameOver()) {
				
				whiteScore = this.getScore()[0];
				blackScore = this.getScore()[1];
				
				
				
				if(!scoreSent) {
					
					if(tournamentPlayers != null) {

						for(Player player : tournamentPlayers) {

							if(player.id == whiteId) {
								
								player.score += whiteScore;

								tournamentPlayersInGame.remove(player);
								
							}

							if(player.id == blackId) {
								
								player.score += blackScore;
								
								tournamentPlayersInGame.remove(player);
								
							}
							
						}
						
					}
					
					scoreSent = true;
					
				}
				
			}
			

		}
		
		
	}

	private void update() {
		
		if(promotion) {
			promoting();
		}
		
		else if(gameover == false && stalemate == false){
			
			if(mouse.pressed) {
				if(activeP == null) {
					
					for(Piece piece : simPieces) {
						
						if(piece.color == currentColor &&
								piece.col == mouse.x / Board.SQUARE_SIZE &&
								piece.row == mouse.y / Board.SQUARE_SIZE) {
							
							activeP = piece;
							
						}
						
					}
					
				}
				else {
					simulate();
				}
			}
			
			if(mouse.pressed == false) {
				if(activeP != null) {
					
					if(validSquare) {
						
						copyPieces(simPieces, pieces);
						activeP.updatePosition();
						
						if(castlingP != null) {
							castlingP.updatePosition();
						}

						checkingP = null;//reset checkingP before checking if in check or checkmate so it is null when mouse is away from checking square
						
						if(isKingInCheck() && isCheckmate()) {
							
							gameover = true;

						}

						else if(isStalemate() && isKingInCheck() == false) {
							
							stalemate = true;
							endCondition = TIE;
							
						}
						
						else {

							if(canPromote()) {
								promotion = true;
							}
							
							else {
								changePlayer();
							}
							
						}
						
					}
					else {
						
						copyPieces(pieces, simPieces);
						activeP.resetPosition();
						activeP = null;
						
					}
					
				}
				
			}
			
			if(whiteOfferedTie && blackOfferedTie) {
				
				gameover = true;
				endCondition = TIE;
				
			}
			
		}

	}
	
	private void simulate() {
		
		
		canMove = false;
		validSquare = false;
		
		copyPieces(pieces, simPieces);
		
		if(castlingP != null) {
			
			castlingP.col = castlingP.preCol;
			castlingP.x = castlingP.getX(castlingP.col);
			castlingP = null;
			
		}
		
		activeP.x = mouse.x - Board.HALF_SQUARE_SIZE;
		activeP.y = mouse.y - Board.HALF_SQUARE_SIZE;
		activeP.col = activeP.getCol(activeP.x);
		activeP.row = activeP.getRow(activeP.y);
		
		
		if(activeP.canMove(activeP.col, activeP.row)) {

			canMove = true;
			
			if(activeP.hittingP != null) {
				simPieces.remove(activeP.hittingP.getIndex());
			}
			
			checkCastling();
			
			if(isIllegal(activeP) == false && opponentCanCaptureKing() == false) {
				
				validSquare = true;
				
			}
			
		}
		
		
	}
	
	private boolean isIllegal(Piece king) {
		
		if(king.type == Type.KING) {
			
			for(Piece piece : simPieces) {
				
				if(piece != king && piece.color != king.color && piece.canMove(king.col, king.row)) {
					return true;
				}
				
			}
			
		}
		
		return false;
		
	}
	
	private boolean opponentCanCaptureKing() {
		
		Piece king = getKing(false);
		
		for(Piece piece : simPieces) {
			
			if(piece.color != king.color && piece.canMove(king.col, king.row)) {
					return true;
			}
			
		}
		
		return false;
		
	}
	
	private boolean isKingInCheck() {
		
		Piece king = getKing(true);
		
		if(checkingP == null) {

			for(Piece piece : simPieces) {

				if(piece.canMove(king.col, king.row)) {
					
					checkingP = piece;
					return true;
					
				}
				
			}
			
		}
		
		return false;
		
	}
	
	private Piece getKing(boolean opponent) {
		
		Piece king = null;
		
		for(Piece piece : simPieces) {
			
			if(opponent) {
				
				if(piece.type == Type.KING && piece.color != currentColor) {
					king = piece;
				}
				
			}
			
			else{

				if(piece.type == Type.KING && piece.color == currentColor) {
					king = piece;
				}
				
			}
			
		}
		
		return king;
		
	}
	
	private boolean isCheckmate(){
		
		Piece king = getKing(true);
		
		if(kingCanMove(king)) {
			return false;
		}
		
		else if(checkingP != null){

			int colDiff = Math.abs(checkingP.col - king.col);
			int rowDiff = Math.abs(checkingP.row - king.row);
			
			if(colDiff == 0) {

				if(checkingP.row < king.row) {
					
					for(int row = checkingP.row; row < king.row; row++) {
						
						for(Piece piece : simPieces) {
							
							if(piece != king && piece.color != currentColor && piece.canMove(checkingP.col, row)) {
								return false;
							}
							
						}
						
					}
					
				}

				if(checkingP.row > king.row) {

					for(int row = checkingP.row; row > king.row; row--) {
						
						for(Piece piece : simPieces) {
							
							if(piece != king && piece.color != currentColor && piece.canMove(checkingP.col, row)) {
								return false;
							}
							
						}
						
					}
					
				}
				
			}
			
			else if(rowDiff == 0) {

				if(checkingP.col < king.col) {
					
					for(int col = checkingP.col; col < king.col; col++) {
						
						for(Piece piece : simPieces) {
							
							if(piece != king && piece.color != currentColor && piece.canMove(col, checkingP.row)) {
								return false;
							}
							
						}
						
					}
					
				}

				if(checkingP.col > king.col) {

					for(int col = checkingP.col; col > king.col; col--) {
						
						for(Piece piece : simPieces) {
							
							if(piece != king && piece.color != currentColor && piece.canMove(col, checkingP.row)) {
								return false;
							}
							
						}
						
					}
					
				}
				
			}
			
			else if(colDiff == rowDiff) {
				
				if(checkingP.row < king.row) {

					if(checkingP.col < king.col) {
						
						for(int col = checkingP.col, row = checkingP.row; col < king.col; col++, row++) {

							for(Piece piece : simPieces) {
								
								if(piece != king && piece.color != currentColor && piece.canMove(col, row)) {
									return false;
								}
								
							}
							
						}
						
					}
						
					if(checkingP.col > king.col) {

						for(int col = checkingP.col, row = checkingP.row; col > king.col; col--, row++) {

							for(Piece piece : simPieces) {
								
								if(piece != king && piece.color != currentColor && piece.canMove(col, row)) {
									return false;
								}
								
							}
							
						}
						
					}
					
				}
					
				if(checkingP.row > king.row) {

					if(checkingP.col < king.col) {

						for(int col = checkingP.col, row = checkingP.row; col < king.col; col++, row--) {

							for(Piece piece : simPieces) {
								
								if(piece != king && piece.color != currentColor && piece.canMove(col, row)) {
									return false;
								}
								
							}
							
						}
						
					}
						
					if(checkingP.col > king.col) {

						for(int col = checkingP.col, row = checkingP.row; col > king.col; col--, row--) {

							for(Piece piece : simPieces) {
								
								if(piece != king && piece.color != currentColor && piece.canMove(col, row)) {
									return false;
								}
								
							}
							
						}
						
					}
					
				}
				
			}
			
			else {
				
			}
			
		}
		
		return true;
		
	}
	
	private boolean kingCanMove(Piece king){

		if(isValidMove(king, -1, -1)) {return true;}
		if(isValidMove(king, 0, -1)) {return true;}
		if(isValidMove(king, 1, -1)) {return true;}
		if(isValidMove(king, -1, 0)) {return true;}
		if(isValidMove(king, 1, 0)) {return true;}
		if(isValidMove(king, -1, 1)) {return true;}
		if(isValidMove(king, 0, 1)) {return true;}
		if(isValidMove(king, 1, 1)) {return true;}
		
		return false;
		
	}
	
	private boolean isValidMove(Piece king, int colPlus, int rowPlus){
		
		boolean isValidMove = false;
		
		king.col += colPlus;
		king.row += rowPlus;
		
		if(king.canMove(king.col, king.row)) {
			
			if(king.hittingP != null) {
				simPieces.remove(king.hittingP.getIndex());
			}
			
			if(isIllegal(king) == false) {
				isValidMove = true;
			}
		}
		
		king.resetPosition();
		copyPieces(pieces, simPieces);
		
		return isValidMove;
		
	}
	
	private boolean isStalemate() {
		
		int count = 0;
		
		for(Piece piece : simPieces) {
			
			if(piece.color != currentColor) {
				count ++;
			}
			
		}
		
		if(count == 1) {
			
			if(kingCanMove(getKing(true)) == false) {
				return true;
			}
			
		}
		
		return false;
		
	}
	
	private void checkCastling() {
		
		if(castlingP != null) {
			
			if(castlingP.col == 0) {
				castlingP.col += 3;
			}
			
			else if(castlingP.col == 7) {
				castlingP.col -= 2;
			}
			
			castlingP.x = castlingP.getX(castlingP.col);
			
		}
		
	}
	
	private void changePlayer() {
		
		if(currentColor == WHITE) {
			
			currentColor = BLACK;
			
			whiteTimer.stop();
			blackTimer.start();
			
			for(Piece piece : pieces) {
				
				if(piece.color == BLACK) {
					piece.twoStepped = false;
				}
				
			}
			
		}
		else {
			
			currentColor = WHITE;
			
			blackTimer.stop();
			whiteTimer.start();
			
			for(Piece piece : pieces) {
				
				if(piece.color == WHITE) {
					piece.twoStepped = false;
				}
				
			}
			
		}
		
		activeP = null;
		
	}
	
	private boolean canPromote() {
		
		if(activeP.type == Type.PAWN) {
			
			if(currentColor == WHITE && activeP.row == 0 || currentColor == BLACK && activeP.row == 7) {
				
				promoPieces.clear();
				promoPieces.add(new Rook(currentColor, 9, 2));
				promoPieces.add(new Knight(currentColor, 9, 3));
				promoPieces.add(new Bishop(currentColor, 9, 4));
				promoPieces.add(new Queen(currentColor, 9, 5));
				
				return true;
				
			}
			
		}
		
		return false;
		
	}
	
	private void promoting(){
		
		if(mouse.pressed) {
			
			for(Piece piece : promoPieces) {
				
				if(piece.col == mouse.x / Board.SQUARE_SIZE && piece.row == mouse.y / Board.SQUARE_SIZE) {
					
					switch(piece.type) {
					
					case ROOK: simPieces.add(new Rook(currentColor, activeP.col, activeP.row)); break;
					case KNIGHT: simPieces.add(new Knight(currentColor, activeP.col, activeP.row)); break;
					case BISHOP: simPieces.add(new Bishop(currentColor, activeP.col, activeP.row)); break;
					case QUEEN: simPieces.add(new Queen(currentColor, activeP.col, activeP.row)); break;
					default: break;
					
					}
					
					simPieces.remove(activeP.getIndex());
					copyPieces(simPieces, pieces);
					activeP = null;
					promotion = false;
					changePlayer();
					
				}
				
			}
			
		}
		
	}
	
	public void whiteResign() {

        resign = true;
        resignColor = WHITE;
        gameover = true;
        endCondition = BLACKWON;
        
	}
	
	public void blackResign() {

        resign = true;
        resignColor = BLACK;
        gameover = true;
        endCondition = WHITEWON;
        
	}
	
	public int[] getScore(){
		
		if(gameover) {

			int gameTime = (gameMinutes * 60) + gameSeconds;
			int[] scores = new int[2];
			
			if(endCondition == WHITEWON) {
				
				scores[0] = gameTime * 3;
				scores[1] = 0;
				
			}
			
			else if(endCondition == BLACKWON) {

				scores[0] = 0;
				scores[1] = gameTime * 3;
				
			}
			
			else if(endCondition == TIE) {

				scores[0] = gameTime;
				scores[1] = gameTime;
				
			}
			
			return scores;
			
		}
		
		return null;
		
	}

	public int getWhiteScore() {

		if(gameover) {

			int gameTime = (gameMinutes * 60) + gameSeconds;
			int score = 0;
			
			if(endCondition == WHITEWON) {
				
				score = gameTime * 3;
				
			}
			
			else if(endCondition == BLACKWON) {

				score = 0;
				
			}
			
			else if(endCondition == TIE) {

				score = gameTime;
				
			}
			
			return score;
			
		}
		
		return 0;
		
	}

	public int getBlackScore() {

		if(gameover) {

			int gameTime = (gameMinutes * 60) + gameSeconds;
			int score = 0;
			
			if(endCondition == WHITEWON) {
				
				score = 0;
				
			}
			
			else if(endCondition == BLACKWON) {

				score = gameTime * 3;
				
			}
			
			else if(endCondition == TIE) {

				score = gameTime;
				
			}
			
			return score;
			
		}
		
		return 0;
		
	}
	
	public boolean getIsGameOver() {
		
		if(gameover || stalemate) {
			return true;
		}
		
		return false;
		
	}
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		
		board.draw(g2);
		
		for(Piece p : simPieces) {
			p.draw(g2);
		}
		
		if(activeP != null) {
			
			if(canMove) {
				
				if(isIllegal(activeP) || opponentCanCaptureKing()) {

					g2.setColor(Color.red);
					g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.9f));
					g2.fillRect(activeP.col * Board.SQUARE_SIZE, activeP.row * Board.SQUARE_SIZE, Board.SQUARE_SIZE, Board.SQUARE_SIZE);
					g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
					
				}
				
				else {

					g2.setColor(Color.white);
					g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
					g2.fillRect(activeP.col * Board.SQUARE_SIZE, activeP.row * Board.SQUARE_SIZE, Board.SQUARE_SIZE, Board.SQUARE_SIZE);
					g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
					
				}
				
			}
			
			activeP.draw(g2);
			
		}
		
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setFont(new Font("Book Antiqua", Font.PLAIN, 40));
		g2.setColor(Color.white);
		
		if(promotion) {
			
			whiteCountdownLabel.setVisible(false);
			blackCountdownLabel.setVisible(false);
			totalCountdownLabel.setVisible(false);
			g2.drawString("Promote to:", 840, 150);
			
			for(Piece piece : promoPieces) {
				g2.drawImage(piece.image, piece.getX(piece.col), piece.getY(piece.row), Board.SQUARE_SIZE, Board.SQUARE_SIZE, null);
			}
			
			
		}
		
		else {
			
			whiteCountdownLabel.setVisible(true);
			blackCountdownLabel.setVisible(true);
			totalCountdownLabel.setVisible(true);
			
			if(currentColor == WHITE) {
				
				g2.drawString("White's turn", 840, 550);
				
				if(checkingP != null && checkingP.color == BLACK) {
					
					g2.setColor(Color.red);
					g2.drawString("The King", 840, 650);
					g2.drawString("is in check!", 840, 700);
					
				}
				
			}
			else {
				
				g2.drawString("Black's turn", 840, 300);

				if(checkingP != null && checkingP.color == WHITE) {
					
					g2.setColor(Color.red);
					g2.drawString("The King", 840, 150);
					g2.drawString("is in check!", 840, 200);
					
				}
				
			}
			
		}
		
		if(gameover) {
			
			String s = "";
			
			if(resign) {

				if(resignColor == BLACK) {
					s = "White Wins";
				}
				
				else if(resignColor == WHITE) {
					s = "Black Wins";
				}
				
			}
			
			else {
				
				if(endCondition == TIE) {
					
					g2.setFont(new Font("Arial", Font.PLAIN, 90));
					g2.setColor(Color.cyan);
					g2.drawString("Tie", 338, 420);
					
				}
				
				else {

					if(currentColor == WHITE) {
						s = "White Wins";
					}
					
					else {
						s = "Black Wins";
					}
					
				}
				
			}
			
			g2.setFont(new Font("Arial", Font.PLAIN, 90));
			g2.setColor(Color.green);
			g2.drawString(s, 200, 420);
			
		}
		
		if(stalemate) {

			g2.setFont(new Font("Arial", Font.PLAIN, 90));
			g2.setColor(Color.cyan);
			g2.drawString("Stalemate", 200, 420);
			
		}
		
	}
	

}
