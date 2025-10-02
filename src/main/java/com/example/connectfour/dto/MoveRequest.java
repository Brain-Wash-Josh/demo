package com.example.connectfour.dto;

public class MoveRequest {
    private int column;
    private String playerId;

    public int getColumn() { return column; }
    public void setColumn(int column) { this.column = column; }
    
    public String getPlayerId() { return playerId; }
    public void setPlayerId(String playerId) { this.playerId = playerId; }
}
