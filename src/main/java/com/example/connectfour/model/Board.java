package com.example.connectfour.model;

public class Board {
    private static final int ROWS = 6;
    private static final int COLS = 7;
    private int[][] cells;
    
    public Board() {
        this.cells = new int[ROWS][COLS];
    }
    
    public int[][] getCells() { return cells; }
    
    public void setCells(int[][] cells) { this.cells = cells; }
    
    public void setCell(int row, int col, int value) {
        cells[row][col] = value;
    }
    
    public int getCell(int row, int col) {
        return cells[row][col];
    }
    
    public boolean isFull() {
        for (int col = 0; col < COLS; col++) {
            if (cells[0][col] == 0) {
                return false;
            }
        }
        return true;
    }
    
    public int getRows() { return ROWS; }
    public int getCols() { return COLS; }
}
