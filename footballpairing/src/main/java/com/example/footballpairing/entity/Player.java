package com.example.footballpairing.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "players")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    private String teamNumber;

    @NotBlank
    private String position;

    @NotBlank
    private String fullName;

    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;
}
