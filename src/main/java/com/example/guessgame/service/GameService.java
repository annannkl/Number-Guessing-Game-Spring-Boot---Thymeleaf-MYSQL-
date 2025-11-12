package com.example.guessgame.service;

import com.example.guessgame.model.Game;
import com.example.guessgame.model.Player;
import com.example.guessgame.repository.GameRepository;
import com.example.guessgame.repository.PlayerRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import java.util.List;

import java.time.Instant;
import java.util.Random;

@Service
public class GameService {

    private final GameRepository gameRepo;
    private final PlayerRepository playerRepo;
    private final Random random = new Random();

    public GameService(GameRepository gameRepo, PlayerRepository playerRepo) {
        this.gameRepo = gameRepo;
        this.playerRepo = playerRepo;
    }

    public Player findOrCreatePlayer(String name) {
        Player p = playerRepo.findByName(name);
        if (p == null) {
            p = new Player(name);
            p = playerRepo.save(p);
        }
        return p;
    }

    public Game startNewGame(Player player) {
        int secret = random.nextInt(100) + 1; // 1..100
        Game g = new Game(player, secret);
        return gameRepo.save(g);
    }

    public String makeGuess(Game game, int guess) {
        if (game.isFinished()) return "Game already finished.";
        game.setAttempts(game.getAttempts() + 1);
        if (guess == game.getSecretNumber()) {
            game.setFinished(true);
            game.setFinishedAt(Instant.now());
            gameRepo.save(game);
            return "Correct!";
        } else if (guess < game.getSecretNumber()) {
            gameRepo.save(game);
            return "Too Low";
        } else {
            gameRepo.save(game);
            return "Too High";
        }
    }

    public Game getGame(Long id) {
        return gameRepo.findById(id).orElse(null);
    }

    public List<Game> getTopFinishedGames(int limit) {
        return gameRepo
                .findByFinishedTrueOrderByAttemptsAsc(PageRequest.of(0, limit))
                .getContent();
    }

    // Fetch all games for a specific player, newest first
    public List<Game> getGamesByPlayer(Long playerId) {
        return gameRepo.findByPlayerIdOrderByCreatedAtDesc(playerId);
    }

    public Player findPlayerByName(String name) {
        return playerRepo.findByName(name);
    }
}
