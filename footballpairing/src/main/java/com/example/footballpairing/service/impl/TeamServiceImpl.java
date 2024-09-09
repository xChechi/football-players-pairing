package com.example.footballpairing.service.impl;

import com.example.footballpairing.converter.TeamConverter;
import com.example.footballpairing.dto.team.TeamRequest;
import com.example.footballpairing.dto.team.TeamResponse;
import com.example.footballpairing.dto.team.TeamUpdateRequest;
import com.example.footballpairing.entity.Team;
import com.example.footballpairing.exception.DuplicateTeamException;
import com.example.footballpairing.exception.TeamNotFoundException;
import com.example.footballpairing.repository.TeamRepository;
import com.example.footballpairing.service.TeamService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final TeamConverter teamConverter;

    @Override
    public List<TeamResponse> getAllTeams() {
        List<Team> teams = teamRepository.findAll();
        List<TeamResponse> responses = new ArrayList<>();

        for (Team t : teams) {
            TeamResponse response = teamConverter.toResponse(t);
            responses.add(response);
        }

        return responses;
    }

    @Override
    public TeamResponse getTeamById(Integer id) {
        Team team = teamRepository.findById(id).orElseThrow(() -> new TeamNotFoundException("Team not exist in database"));
        return teamConverter.toResponse(team);
    }

    @Override
    public void deleteTeamById(Integer id) {
        teamRepository.deleteById(id);
    }

    @Override
    public TeamResponse createTeam(TeamRequest request) {
        if (existByName(request.getName())) {
            throw new DuplicateTeamException("Team already exist");
        }
        Team team = teamConverter.create(request);
        Team savedTeam = teamRepository.save(team);
        return teamConverter.toResponse(savedTeam);
    }

    @Override
    public TeamResponse updateTeam(Integer id, TeamUpdateRequest request) {
        Team team = teamRepository.findById(id).orElseThrow(() -> new TeamNotFoundException("Team not exist in database"));

        team.setName(request.getName());
        team.setManager(request.getManager());
        team.setGroup(request.getGroup());

        Team savedTeam = teamRepository.save(team);
        return teamConverter.toResponse(savedTeam);
    }

    @Override
    public boolean existByName(String name) {
        return teamRepository.findByName(name).isPresent();
    }
}
