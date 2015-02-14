package uk.co.mindbadger.footballresultsanalyser.domain;

import java.util.Calendar;

public class FixtureImpl implements Fixture<String> {
	private static final long serialVersionUID = -6113305997296125615L;
	
	private Integer awayGoals;
	private Team<String> awayTeam;
	private Division<String> division;
	private Calendar fixtureDate;
	private String fixtureId;
	private Integer homeGoals;
	private Team<String> homeTeam;
	private Season<String> season;

	@Override
	public Integer getAwayGoals() {
		return awayGoals;
	}

	@Override
	public Team<String> getAwayTeam() {
		return awayTeam;
	}

	@Override
	public Division<String> getDivision() {
		return division;
	}

	@Override
	public Calendar getFixtureDate() {
		return fixtureDate;
	}

	@Override
	public String getFixtureId() {
		return fixtureId;
	}

	@Override
	public Integer getHomeGoals() {
		return homeGoals;
	}

	@Override
	public Team<String> getHomeTeam() {
		return homeTeam;
	}

	@Override
	public Season<String> getSeason() {
		return season;
	}

	@Override
	public void setAwayGoals(Integer awayGoals) {
		this.awayGoals = awayGoals;
	}

	@Override
	public void setAwayTeam(Team<String> awayTeam) {
		this.awayTeam = awayTeam;
	}

	@Override
	public void setDivision(Division<String> division) {
		this.division = division;
	}

	@Override
	public void setFixtureDate(Calendar fixtureDate) {
		this.fixtureDate = fixtureDate;
	}

	@Override
	public void setFixtureId(String fixtureId) {
		this.fixtureId = fixtureId;
	}

	@Override
	public void setHomeGoals(Integer homeGoals) {
		this.homeGoals = homeGoals;
	}

	@Override
	public void setHomeTeam(Team<String> homeTeam) {
		this.homeTeam = homeTeam;
	}

	@Override
	public void setSeason(Season<String> season) {
		this.season = season;
	}

}
