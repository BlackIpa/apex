package com.apex.service;

import com.apex.dto.TeamDto;
import com.apex.repository.TeamRepository;
import com.apex.service.api.TeamService;
import com.apex.service.mappers.TeamMapper;
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
