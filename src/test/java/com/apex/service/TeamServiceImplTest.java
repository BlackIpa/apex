package com.apex.service;

import com.apex.dto.TeamDto;
import com.apex.enums.Region;
import com.apex.repository.TeamRepository;
import com.apex.repository.dao.Team;
import com.apex.service.mappers.TeamMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TeamServiceImplTest {

	private static final Long TEAM_ID = 1L;

	private static final String TEAM_NAME = "SJP2";

	@Mock
	private TeamMapper teamMapper;

	@Mock
	private TeamRepository teamRepository;

	@InjectMocks
	private TeamServiceImpl teamService;

	@Test
	void shouldReturnTeamDto_whenTeamExists() {
		// given
		Team testTeam = new Team(TEAM_ID, TEAM_NAME, Region.EMEA);
		TeamDto dto = new TeamDto(TEAM_NAME, Region.EMEA);
		when(teamRepository.findById(TEAM_ID)).thenReturn(Optional.of(testTeam));
		when(teamMapper.toDto(testTeam)).thenReturn(dto);

		// when
		TeamDto result = teamService.read(TEAM_ID);

		// then
		assertNotNull(result);
		assertEquals(dto, result);
		verify(teamRepository).findById(TEAM_ID);
		verify(teamMapper).toDto(testTeam);
		verifyNoMoreInteractions(teamMapper, teamRepository);
	}

	@Test
	void shouldThrowException_whenTeamDoesNotExist() {
		// given
		when(teamRepository.findById(TEAM_ID)).thenReturn(Optional.empty());

		// when
		assertThrows(IllegalArgumentException.class, () -> teamService.read(TEAM_ID));

		// then
		verify(teamRepository).findById(anyLong());
		verifyNoMoreInteractions(teamRepository);
		verifyNoInteractions(teamMapper);
	}

	@Test
	void shouldReturnPagedTeamDtos_whenTeamsExist() {
		// given
		Pageable pageable = PageRequest.of(0, 1);
		Team testTeam = new Team(TEAM_ID, TEAM_NAME, Region.EMEA);
		TeamDto expectedTeamDto = new TeamDto(TEAM_NAME, Region.EMEA);
		List<Team> teamList = List.of(testTeam);
		Page<Team> teamPage = new PageImpl<>(teamList, pageable, teamList.size());
		when(teamRepository.findAll(pageable)).thenReturn(teamPage);
		when(teamMapper.toDto(testTeam)).thenReturn(expectedTeamDto);

		// when
		Page<TeamDto> result = teamService.readAll(pageable);

		// then
		assertNotNull(result);
		assertEquals(1, result.getTotalElements());
		assertTrue(result.getContent().contains(expectedTeamDto));
		verify(teamRepository).findAll(pageable);
		verify(teamMapper).toDto(testTeam);
		verifyNoMoreInteractions(teamMapper, teamRepository);
	}

}