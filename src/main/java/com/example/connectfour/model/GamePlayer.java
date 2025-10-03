package com.example.connectfour.model;

public class GamePlayer {
    private String id;
    private String name;
    private Game.Player playerNumber;
    
    public GamePlayer(String id, Game.Player playerNumber) {
        this.id = id;
        this.playerNumber = playerNumber;
    }
    
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public Game.Player getPlayerNumber() { return playerNumber; }
    public void setPlayerNumber(Game.Player playerNumber) { this.playerNumber = playerNumber; }
}