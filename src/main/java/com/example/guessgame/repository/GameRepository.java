package com.example.guessgame.repository;

import com.example.guessgame.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {
    List<Game> findByPlayerIdOrderByCreatedAtDesc(Long playerId);

    // New method with pagination support for leaderboard
    Page<Game> findByFinishedTrueOrderByAttemptsAsc(Pageable pageable);
}
