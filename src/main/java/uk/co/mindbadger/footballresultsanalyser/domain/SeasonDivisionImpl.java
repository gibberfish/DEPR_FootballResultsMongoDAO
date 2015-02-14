package uk.co.mindbadger.footballresultsanalyser.domain;

import java.util.Set;

public class SeasonDivisionImpl implements SeasonDivision<String> {
	private static final long serialVersionUID = -1409106637881070795L;
	
	private Division<String> division;
	private int divisionPosition;
	private Season<String> season;

	@Override
	public Division<String> getDivision() {
		return division;
	}

	@Override
	public int getDivisionPosition() {
		return divisionPosition;
	}

	@Override
	public Season<String> getSeason() {
		return season;
	}

	@Override
	public void setDivision(Division<String> division) {
		this.division = division;
	}

	@Override
	public void setDivisionPosition(int divisionPosition) {
		this.divisionPosition = divisionPosition;
	}

	@Override
	public void setSeason(Season<String> season) {
		this.season = season;
	}

	@Override
	public void setTeamsInSeasonDivision(Set<SeasonDivisionTeam<String>> arg0) {
		throw new RuntimeException ("Unimplemented method");
	}
	
	@Override
	public void setPrimaryKey(SeasonDivisionId<String> arg0) {
		throw new RuntimeException ("Unimplemented method");
	}
	
	@Override
	public SeasonDivisionId<String> getPrimaryKey() {
		throw new RuntimeException ("Unimplemented method");
	}

	@Override
	public Set<SeasonDivisionTeam<String>> getTeamsInSeasonDivision() {
		throw new RuntimeException ("Unimplemented method");
	}
}

