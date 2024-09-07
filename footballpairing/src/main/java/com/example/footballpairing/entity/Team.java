package com.example.footballpairing.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "teams")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    private String name;

    @NotBlank
    private String manager;

    @NotBlank
    @Column(name = "team_group")
    private String group;

    @OneToMany(mappedBy = "team")
    private Set<Player> players;
}
