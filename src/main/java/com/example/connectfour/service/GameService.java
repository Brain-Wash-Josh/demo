package com.example.connectfour.service;

import com.example.connectfour.model.Game;
import com.example.connectfour.model.Game.Player;
import com.example.connectfour.model.Game.Status;
import com.example.connectfour.model.GamePlayer;
import com.example.connectfour.model.Board;


import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

@Service
public class GameService {
    private final Map<String, Game> games = new ConcurrentHashMap<>();

    public Game createGame() {
        String gameId = UUID.randomUUID().toString();
        Game game = new Game(gameId);
        games.put(gameId, game);
        return game;
    }

    public Game getGame(String gameId) {
        return games.get(gameId);
    }

    public Game joinGame(String gameId, String playerId) {
        Game game = games.get(gameId);
        if (game == null)
            return null;

        if (game.getPlayer1() == null) {
            GamePlayer player1 = new GamePlayer(playerId, Game.Player.PLAYER_1);
            game.setPlayer1(player1);
        } else if (game.getPlayer2() == null) {
           GamePlayer player2 = new GamePlayer(playerId, Game.Player.PLAYER_2);
            game.setPlayer2(player2);
            game.setStatus(Game.Status.IN_PROGRESS);
        }
        return game;
    }

    public Game makeMove(String gameId, int column, String playerId) {
        Game game = games.get(gameId);
        if (game == null || game.getStatus() != Status.IN_PROGRESS) {
            return null;
        }

        // Verify it's the player's turn
        if ((game.getCurrentPlayer() == Player.PLAYER_1 && !playerId.equals(game.getPlayer1().getId())) ||
                (game.getCurrentPlayer() == Player.PLAYER_2 && !playerId.equals(game.getPlayer2().getId()))) {
            return null;
        }

        
        // Find the lowest empty row in the column
        Board board = game.getBoard();
        int rows = board.getRows(); 
        int row = IntStream.iterate(rows - 1, i -> i - 1)
                .limit(rows)
                .filter(r -> board.getCells()[r][column] == 0)
                .findFirst()
                .orElse(-1);

        if (row == -1)
            return null; // Column is full

        // Place the piece
        
        board.getCells()[row][column] = game.getCurrentPlayer().getNumber();

        // Check for winner
        if (checkWinner(board.getCells(), row, column, game.getCurrentPlayer().getNumber())) {
            game.setWinner(game.getCurrentPlayer());
            game.setStatus(Status.FINISHED);
        } else if (isBoardFull(board.getCells())) {
            game.setWinner(null); // Draw
            game.setStatus(Status.FINISHED);
        } else {
            // Switch player
            game.setCurrentPlayer(
                    game.getCurrentPlayer() == Game.Player.PLAYER_1
                            ? Game.Player.PLAYER_2
                            : Game.Player.PLAYER_1);
        }

        return game;
    }

    private boolean checkWinner(int[][] board, int row, int col, int player) {
        // Check horizontal
        int count = 0;
        for (int c = 0; c < 7; c++) {
            count = (board[row][c] == player) ? count + 1 : 0;
            if (count >= 4)
                return true;
        }

        // Check vertical
        count = 0;
        for (int r = 0; r < 6; r++) {
            count = (board[r][col] == player) ? count + 1 : 0;
            if (count >= 4)
                return true;
        }

        // Check diagonal (top-left to bottom-right)
        count = 0;
        int startRow = row - Math.min(row, col);
        int startCol = col - Math.min(row, col);
        while (startRow < 6 && startCol < 7) {
            count = (board[startRow][startCol] == player) ? count + 1 : 0;
            if (count >= 4)
                return true;
            startRow++;
            startCol++;
        }

        // Check diagonal (bottom-left to top-right)
        count = 0;
        startRow = row + Math.min(5 - row, col);
        startCol = col - Math.min(5 - row, col);
        while (startRow >= 0 && startCol < 7) {
            count = (board[startRow][startCol] == player) ? count + 1 : 0;
            if (count >= 4)
                return true;
            startRow--;
            startCol++;
        }

        return false;
    }

    private boolean isBoardFull(int[][] board) {
        for (int c = 0; c < 7; c++) {
            if (board[0][c] == 0)
                return false;
        }
        return true;
    }

    public List<Game> getAllGames() {
        return new ArrayList<>(games.values());
    }
}
