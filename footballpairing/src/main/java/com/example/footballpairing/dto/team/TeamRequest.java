package com.example.footballpairing.dto.team;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TeamRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String manager;

    @NotBlank
    private String group;
}
