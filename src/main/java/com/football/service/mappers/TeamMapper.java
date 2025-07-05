package com.football.service.mappers;

import com.football.dto.TeamDto;
import com.football.repository.dao.Team;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface TeamMapper {

	Team toDao(final TeamDto team);

	TeamDto toDto(final Team team);

}
