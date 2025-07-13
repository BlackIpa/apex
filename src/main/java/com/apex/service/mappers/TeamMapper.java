package com.apex.service.mappers;

import com.apex.dto.TeamDto;
import com.apex.repository.dao.Team;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface TeamMapper {

	Team toDao(final TeamDto team);

	TeamDto toDto(final Team team);

}
