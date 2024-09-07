package com.example.footballpairing.dto.player;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlayerResponse {

    private int id;
    private String teamNumber;
    private String position;
    private String fullName;
    private String teamName;
}
