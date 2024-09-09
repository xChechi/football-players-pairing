package com.example.footballpairing.dto.player;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlayerRequest {

    @NotBlank
    private String teamNumber;

    @NotBlank
    private String position;

    @NotBlank
    private String fullName;

    @NotNull
    private int teamId;
}
