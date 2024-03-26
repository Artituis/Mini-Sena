package br.arturslampert.minisena.modules.bet;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BetRepository extends JpaRepository<BetEntity, Integer> {
    List<BetEntity> findByBettorId(int bettorId);
}
