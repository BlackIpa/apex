package com.football.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.football.dto.TeamDto;
import com.football.service.api.TeamService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class TeamsControllerContextTest {

	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	private MockMvc mockMvc;

	@Mock
	private TeamService teamService;

	@InjectMocks
	private TeamsController teamsController;

	@BeforeEach
	void setup() {
		PageableHandlerMethodArgumentResolver pageableResolver = new PageableHandlerMethodArgumentResolver();
		mockMvc = MockMvcBuilders.standaloneSetup(teamsController)
				.setCustomArgumentResolvers(pageableResolver)
				.build();
	}

	@Test
	void shouldReturnTeamById() throws Exception {
		// given
		Long id = 1L;
		TeamDto dto = new TeamDto("Arsenal", "Emirates", 60000);
		when(teamService.read(id)).thenReturn(dto);

		// when
		mockMvc.perform(get("/teams/{id}", id)
						.contentType(MediaType.APPLICATION_JSON))
				// then
				.andExpect(status().isOk())
				.andExpect(content().json(OBJECT_MAPPER.writeValueAsString(dto)));
		verify(teamService).read(id);
		verifyNoMoreInteractions(teamService);
	}

	@Test
	void shouldReturnPagedTeams() throws Exception {
		// given
		TeamDto dto = new TeamDto("Arsenal", "Emirates", 60000);
		Pageable pageable = PageRequest.of(0, 10);
		Page<TeamDto> teamPage = new PageImpl<>(List.of(dto), pageable, 1);
		when(teamService.readAll(any(Pageable.class))).thenReturn(teamPage);

		// when
		mockMvc.perform(get("/teams")
						.param("page", "0")
						.param("size", "10")
						.contentType(MediaType.APPLICATION_JSON))
				// then
				.andExpect(status().isOk())
				.andExpect(content().json(OBJECT_MAPPER.writeValueAsString(teamPage)));
		verify(teamService).readAll(any(Pageable.class));
		verifyNoMoreInteractions(teamService);
	}
}