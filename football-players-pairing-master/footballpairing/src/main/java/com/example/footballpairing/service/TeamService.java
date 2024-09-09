package com.example.footballpairing.service;

import com.example.footballpairing.dto.team.TeamRequest;
import com.example.footballpairing.dto.team.TeamResponse;
import com.example.footballpairing.dto.team.TeamUpdateRequest;

import java.util.List;

public interface TeamService {

    List<TeamResponse> getAllTeams ();
    TeamResponse getTeamById (Integer id);
    void deleteTeamById (Integer id);
    TeamResponse createTeam (TeamRequest request);
    TeamResponse updateTeam (Integer id, TeamUpdateRequest request);

    boolean existByName (String name);

}
