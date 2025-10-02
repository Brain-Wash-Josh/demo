package com.example.connectfour.model;

public class Game {
    private String id;
    private int[][] board; 
    private int currentPlayer; 
    private String status; 
    private Integer winner; 
    private String player1Id;
    private String player2Id;

    public Game(String id) {
        this.id = id;
        this.board = new int[6][7];
        this.currentPlayer = 1;
        this.status = "waiting";
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public int[][] getBoard() { return board; }
    public void setBoard(int[][] board) { this.board = board; }
    
    public int getCurrentPlayer() { return currentPlayer; }
    public void setCurrentPlayer(int player) { this.currentPlayer = player; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public Integer getWinner() { return winner; }
    public void setWinner(Integer winner) { this.winner = winner; }
    
    public String getPlayer1Id() { return player1Id; }
    public void setPlayer1Id(String id) { this.player1Id = id; }
    
    public String getPlayer2Id() { return player2Id; }
    public void setPlayer2Id(String id) { this.player2Id = id; }
}
