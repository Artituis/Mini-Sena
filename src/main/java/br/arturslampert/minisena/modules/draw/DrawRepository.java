package br.arturslampert.minisena.modules.draw;

import br.arturslampert.minisena.modules.draw.entities.DrawEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DrawRepository extends JpaRepository<DrawEntity, Integer> {
    @Query(value = "SELECT * FROM draw ORDER BY updated_at DESC LIMIT 1", nativeQuery = true)
    Optional<DrawEntity> findLastDraw();
}
