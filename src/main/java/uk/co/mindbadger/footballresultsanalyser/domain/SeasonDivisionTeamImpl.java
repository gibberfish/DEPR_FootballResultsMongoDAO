package uk.co.mindbadger.footballresultsanalyser.domain;

public class SeasonDivisionTeamImpl implements SeasonDivisionTeam<String, String, String> {
	private static final long serialVersionUID = 2624036331631829410L;
	
	private Team<String> team;
	private SeasonDivision<String, String> seasonDivision;
	private String id;

	@Override
	public Team<String> getTeam() {
		return team;
	}

	@Override
	public void setTeam(Team<String> team) {
		this.team = team;
	}
	
	@Override
	public SeasonDivision<String, String> getSeasonDivision() {
		return seasonDivision;
	}
	
	@Override
	public void setSeasonDivision(SeasonDivision<String, String> seasonDivision) {
		this.seasonDivision = seasonDivision;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}
	
	@Override
	public String getId() {
		return this.id;
	}
}

