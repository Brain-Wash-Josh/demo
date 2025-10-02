package com.example.connectfour.service;

import com.example.connectfour.model.Game;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

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
        if (game == null) return null;

        if (game.getPlayer1Id() == null) {
            game.setPlayer1Id(playerId);
        } else if (game.getPlayer2Id() == null) {
            game.setPlayer2Id(playerId);
            game.setStatus("playing");
        }
        return game;
    }

    public Game makeMove(String gameId, int column, String playerId) {
        Game game = games.get(gameId);
        if (game == null || !game.getStatus().equals("playing")) {
            return null;
        }

        // Verify it's the player's turn
        if ((game.getCurrentPlayer() == 1 && !playerId.equals(game.getPlayer1Id())) ||
            (game.getCurrentPlayer() == 2 && !playerId.equals(game.getPlayer2Id()))) {
            return null;
        }

        // Find the lowest empty row in the column
        int row = -1;
        for (int r = 5; r >= 0; r--) {
            if (game.getBoard()[r][column] == 0) {
                row = r;
                break;
            }
        }

        if (row == -1) return null; // Column is full

        // Place the piece
        game.getBoard()[row][column] = game.getCurrentPlayer();

        // Check for winner
        if (checkWinner(game.getBoard(), row, column, game.getCurrentPlayer())) {
            game.setWinner(game.getCurrentPlayer());
            game.setStatus("finished");
        } else if (isBoardFull(game.getBoard())) {
            game.setWinner(0); // Draw
            game.setStatus("finished");
        } else {
            // Switch player
            game.setCurrentPlayer(game.getCurrentPlayer() == 1 ? 2 : 1);
        }

        return game;
    }

    private boolean checkWinner(int[][] board, int row, int col, int player) {
        // Check horizontal
        int count = 0;
        for (int c = 0; c < 7; c++) {
            count = (board[row][c] == player) ? count + 1 : 0;
            if (count >= 4) return true;
        }

        // Check vertical
        count = 0;
        for (int r = 0; r < 6; r++) {
            count = (board[r][col] == player) ? count + 1 : 0;
            if (count >= 4) return true;
        }

        // Check diagonal (top-left to bottom-right)
        count = 0;
        int startRow = row - Math.min(row, col);
        int startCol = col - Math.min(row, col);
        while (startRow < 6 && startCol < 7) {
            count = (board[startRow][startCol] == player) ? count + 1 : 0;
            if (count >= 4) return true;
            startRow++;
            startCol++;
        }

        // Check diagonal (bottom-left to top-right)
        count = 0;
        startRow = row + Math.min(5 - row, col);
        startCol = col - Math.min(5 - row, col);
        while (startRow >= 0 && startCol < 7) {
            count = (board[startRow][startCol] == player) ? count + 1 : 0;
            if (count >= 4) return true;
            startRow--;
            startCol++;
        }

        return false;
    }

    private boolean isBoardFull(int[][] board) {
        for (int c = 0; c < 7; c++) {
            if (board[0][c] == 0) return false;
        }
        return true;
    }

    public List<Game> getAllGames() {
        return new ArrayList<>(games.values());
    }
}
