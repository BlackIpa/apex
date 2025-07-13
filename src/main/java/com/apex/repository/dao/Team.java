package com.apex.repository.dao;

import com.apex.enums.Region;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class Team {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	@Enumerated(EnumType.STRING)
	private Region region;

	public Team() {
	}

	public Team(final Long id, final String name, final Region region) {
		this.id = id;
		this.name = name;
		this.region = region;
	}

	public Long getId() { return id; }

	public void setId(Long id) { this.id = id; }

	public String getName() { return name; }

	public void setName(String name) { this.name = name; }

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		Team team = (Team) o;
		return Objects.equals(id, team.id) && Objects.equals(name, team.name) && Objects.equals(region, team.region);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, region);
	}

}
