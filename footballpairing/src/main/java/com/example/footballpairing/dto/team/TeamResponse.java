package com.example.footballpairing.dto.team;

import com.example.footballpairing.dto.player.PlayerResponse;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeamResponse {

    private int id;
    private String name;
    private String manager;
    private String group;
    private List<PlayerResponse> players;
}
