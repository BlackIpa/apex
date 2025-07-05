package com.football.service;

import com.football.dto.TeamDto;
import com.football.repository.TeamRepository;
import com.football.service.api.TeamService;
import com.football.service.mappers.TeamMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TeamServiceImpl implements TeamService {

	private final TeamMapper teamMapper;

	private final TeamRepository teamRepository;

	public TeamServiceImpl(TeamMapper teamMapper, TeamRepository teamRepository) {
		this.teamMapper = teamMapper;
		this.teamRepository = teamRepository;
	}

	@Override
	public TeamDto read(Long id) {
		return teamRepository.findById(id)
				.map(teamMapper::toDto)
				.orElseThrow(() -> new IllegalArgumentException("Team not found with id: " + id));
	}

	@Override
	public Page<TeamDto> readAll(Pageable pageable) {
		return teamRepository.findAll(pageable)
				.map(teamMapper::toDto);
	}

}
