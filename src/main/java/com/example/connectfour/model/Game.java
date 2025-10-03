package com.example.connectfour.model;

public class Game {
    public enum Status {
        WAITING,
        IN_PROGRESS,
        FINISHED,
        ABANDONED
    }
    
    public enum Player {
        PLAYER_1(1),
        PLAYER_2(2);
        
        private final int number;
        
        Player(int number) {
            this.number = number;
        }
        
        public int getNumber() {
            return number;
        }
        
        public Player getOpponent() {
            return this == PLAYER_1 ? PLAYER_2 : PLAYER_1;
        }
    }
    
    private String id;
    private Board board;
    private Player currentPlayer;
    private Status status;
    private Player winner;
    private GamePlayer player1;
    private GamePlayer player2;
    
    public Game(String id) {
        this.id = id;
        this.board = new Board();
        this.currentPlayer = Player.PLAYER_1;
        this.status = Status.WAITING;
    }
    
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public Board getBoard() { return board; }
    public void setBoard(Board board) { this.board = board; }
    
    public Player getCurrentPlayer() { return currentPlayer; }
    public void setCurrentPlayer(Player player) { this.currentPlayer = player; }
    
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    
    public Player getWinner() { return winner; }
    public void setWinner(Player winner) { this.winner = winner; }
    
    public GamePlayer getPlayer1() { return player1; }
    public void setPlayer1(GamePlayer player) { this.player1 = player; }
    
    public GamePlayer getPlayer2() { return player2; }
    public void setPlayer2(GamePlayer player) { this.player2 = player; }
    
    //Might need later
    public GamePlayer getPlayerByNumber(Player playerNumber) {
        return playerNumber == Player.PLAYER_1 ? player1 : player2;
    }
}