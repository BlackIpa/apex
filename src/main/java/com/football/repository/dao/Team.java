package com.football.repository.dao;

import jakarta.persistence.Entity;
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

	private String stadiumName;

	private Integer stadiumCapacity;

	public Team() {
	}

	public Team(Long id, String name, String stadiumName, Integer stadiumCapacity) {
		this.id = id;
		this.name = name;
		this.stadiumName = stadiumName;
		this.stadiumCapacity = stadiumCapacity;
	}

	public Long getId() { return id; }

	public void setId(Long id) { this.id = id; }

	public String getName() { return name; }

	public void setName(String name) { this.name = name; }

	public String getStadiumName() { return stadiumName; }

	public void setStadiumName(String stadiumName) { this.stadiumName = stadiumName; }

	public Integer getStadiumCapacity() { return stadiumCapacity; }

	public void setStadiumCapacity(Integer stadiumCapacity) { this.stadiumCapacity = stadiumCapacity; }

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		Team team = (Team) o;
		return Objects.equals(id, team.id) && Objects.equals(name, team.name) && Objects.equals(stadiumName, team.stadiumName) && Objects.equals(stadiumCapacity, team.stadiumCapacity);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, stadiumName, stadiumCapacity);
	}
}
