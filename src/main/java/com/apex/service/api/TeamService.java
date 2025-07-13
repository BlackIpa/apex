package com.apex.service.api;

import com.apex.dto.TeamDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TeamService {

	TeamDto read(final Long id);

	Page<TeamDto> readAll(final Pageable pageable);

}
