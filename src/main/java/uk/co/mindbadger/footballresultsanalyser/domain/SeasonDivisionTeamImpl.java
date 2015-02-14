package uk.co.mindbadger.footballresultsanalyser.domain;

public class SeasonDivisionTeamImpl implements SeasonDivisionTeam<String> {
	private static final long serialVersionUID = 2624036331631829410L;
	
	private Team<String> team;
	private SeasonDivision<String> seasonDivision;

	@Override
	public Team<String> getTeam() {
		return team;
	}

	@Override
	public void setTeam(Team<String> team) {
		this.team = team;
	}
	
	@Override
	public SeasonDivision<String> getSeasonDivision() {
		return seasonDivision;
	}
	
	@Override
	public void setSeasonDivision(SeasonDivision<String> seasonDivision) {
		this.seasonDivision = seasonDivision;
	}

	@Override
	public void setPrimaryKey(SeasonDivisionTeamId<String> arg0) {
		throw new RuntimeException ("Unimplemented method");
	}
	
	@Override
	public SeasonDivisionTeamId<String> getPrimaryKey() {
		throw new RuntimeException ("Unimplemented method");
	}
}

