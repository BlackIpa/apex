package com.apex.rest;

import com.apex.dto.TeamDto;
import com.apex.service.api.TeamService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teams")
public class TeamsController {

	private final TeamService teamService;

	public TeamsController(TeamService teamService) {
		this.teamService = teamService;
	}

	@GetMapping("/{id}")
	public ResponseEntity<TeamDto> getTeamById(@PathVariable Long id) {
		return ResponseEntity.ok(teamService.read(id));
	}

	@GetMapping
	public Page<TeamDto> getAllTeams(Pageable pageable) {
		return teamService.readAll(pageable);
	}

}
