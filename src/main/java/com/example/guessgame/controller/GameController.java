package com.example.guessgame.controller;
import java.util.List;

import com.example.guessgame.model.Game;
import com.example.guessgame.model.Player;
import com.example.guessgame.service.GameService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/start")
    public String start(@RequestParam String name, HttpSession session, Model model) {
        if (name == null || name.trim().isEmpty()) {
            model.addAttribute("error", "Name cannot be empty");
            return "index"; // stay on homepage and show error
        }
        Player player = gameService.findOrCreatePlayer(name.trim());
        Game game = gameService.startNewGame(player);
        session.setAttribute("currentGameId", game.getId());
        session.setAttribute("playerName", player.getName());
        return "redirect:/play";
    }

    @GetMapping("/play")
    public String play(Model model, HttpSession session) {
        Long gid = (Long) session.getAttribute("currentGameId");
        if (gid == null) {
            model.addAttribute("error", "No active game. Start a new game from the homepage.");
            return "index";
        }
        Game game = gameService.getGame(gid);
        model.addAttribute("game", game);
        model.addAttribute("message", "");
        return "play";
    }

    @PostMapping("/guess")
    public String guess(@RequestParam String guess, Model model, HttpSession session) {
        Long gid = (Long) session.getAttribute("currentGameId");
        if (gid == null) {
            model.addAttribute("error", "No active game. Start a new game.");
            return "index";
        }

        Game game = gameService.getGame(gid);

        int guessNumber;
        try {
            guessNumber = Integer.parseInt(guess);  // Try to parse the guess
        } catch (NumberFormatException e) {
            model.addAttribute("game", game);
            model.addAttribute("message", "Please enter a valid number.");
            return "play";
        }

        if (guessNumber < 1 || guessNumber > 100) {  // Check bounds
            model.addAttribute("game", game);
            model.addAttribute("message", "Guess must be between 1 and 100.");
            return "play";
        }

        String feedback = gameService.makeGuess(game, guessNumber);
        model.addAttribute("game", game);
        model.addAttribute("message", feedback);
        return "play";
    }

    @PostMapping("/new")
    public String newGame(HttpSession session) {
        String name = (String) session.getAttribute("playerName");
        if (name == null) return "redirect:/";
        Player player = gameService.findOrCreatePlayer(name);
        Game game = gameService.startNewGame(player);
        session.setAttribute("currentGameId", game.getId());
        return "redirect:/play";
    }

    @GetMapping("/leaderboard")
    public String leaderboard(Model model) {
        List<Game> topGames = gameService.getTopFinishedGames(10); // Top 10 best games
        model.addAttribute("topGames", topGames);
        return "leaderboard"; // points to leaderboard.html template
    }

    @GetMapping("/history")
    public String history(HttpSession session, Model model) {
        // Get player name from session
        String playerName = (String) session.getAttribute("playerName");

        // If no player is in session, show empty history
        if (playerName == null || playerName.trim().isEmpty()) {
            model.addAttribute("games", List.of());
            return "history";
        }

        // Fetch player from database using GameService
        Player player = gameService.findPlayerByName(playerName);
        if (player == null) {
            model.addAttribute("games", List.of());
            return "history";
        }

        // Fetch all games for this player
        List<Game> games = gameService.getGamesByPlayer(player.getId());
        model.addAttribute("games", games);

        return "history"; // points to history.html template
    }

}
