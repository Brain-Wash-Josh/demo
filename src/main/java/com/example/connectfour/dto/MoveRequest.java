package com.example.connectfour.dto;

public class MoveRequest {

    private final int column;
    private final String playerId;
    
    public MoveRequest(int column, String playerId) {
        this.column = column;
        this.playerId = playerId;
    }
    
    public int getColumn() { return column; }
    public String getPlayerId() { return playerId; }

}
