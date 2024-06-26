package br.arturslampert.minisena.modules.bet;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity(name = "bet")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BetEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(name = "bettor_id")
    private int bettorId;

    @Column(name = "draw_id")
    private int drawId;

    @Size(min = 5, max = 5)
    private int[] numbers;

    @CreationTimestamp
    private LocalDateTime createdAt;

}
