package uk.co.mindbadger.footballresultsanalyser.domain;

public class TeamImpl implements Team<String> {
	private static final long serialVersionUID = -5283719994168982295L;
	
	private String teamId;
	private String teamName;
	private String teamShortName;

	@Override
	public String getTeamId() {
		return teamId;
	}

	@Override
	public String getTeamName() {
		return teamName;
	}

	@Override
	public String getTeamShortName() {
		return teamShortName;
	}

	@Override
	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	@Override
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	@Override
	public void setTeamShortName(String teamShortName) {
		this.teamShortName = teamShortName;
	}

	@Override
	public String getTeamIdAsString() {
		return teamId;
	}
}
