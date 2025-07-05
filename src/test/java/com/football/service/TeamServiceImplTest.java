package com.football.service;

import com.football.dto.TeamDto;
import com.football.repository.TeamRepository;
import com.football.repository.dao.Team;
import com.football.service.mappers.TeamMapper;
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
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TeamServiceImplTest {

	@Mock
	private TeamMapper teamMapper;

	@Mock
	private TeamRepository teamRepository;

	@InjectMocks
	private TeamServiceImpl teamService;

	@Test
	void shouldReturnTeamDto_whenTeamExists() {
		// given
		Long id = 1L;
		Team testTeam = new Team(id, "Arsenal", "Emirates Stadium", 60000);
		TeamDto dto = new TeamDto("Arsenal", "Emirates Stadium", 60000);
		when(teamRepository.findById(id)).thenReturn(Optional.of(testTeam));
		when(teamMapper.toDto(testTeam)).thenReturn(dto);

		// when
		TeamDto result = teamService.read(id);

		// then
		assertNotNull(result);
		assertEquals(dto, result);
		verify(teamRepository).findById(id);
		verify(teamMapper).toDto(testTeam);
		verifyNoMoreInteractions(teamMapper, teamRepository);
	}

	@Test
	void shouldThrowException_whenTeamNotFound() {
		// given
		Long id = 99L;
		when(teamRepository.findById(id)).thenReturn(Optional.empty());

		// when
		assertThrows(IllegalArgumentException.class, () -> teamService.read(id));

		// then
		verify(teamRepository).findById(anyLong());
		verifyNoMoreInteractions(teamRepository);
		verifyNoMoreInteractions(teamMapper);
	}

	@Test
	void shouldReturnPagedTeamDtos_whenTeamsExist() {
		// given
		Pageable pageable = PageRequest.of(0, 1);
		Team testTeam = new Team(1L, "Arsenal", "Emirates", 60000);

		TeamDto expectedTeamDto = new TeamDto("Arsenal", "Emirates", 60000);

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