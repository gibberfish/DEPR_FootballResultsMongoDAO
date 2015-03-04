package uk.co.mindbadger.footballresultsanalyser.domain;

public class DomainObjectFactoryImpl implements DomainObjectFactory<String, String, String> {
	
	@Override
	public Season<String> createSeason(Integer seasonNum) {
		Season<String> season = new SeasonImpl();
		season.setSeasonNumber(seasonNum);
		return season;
	}

	@Override
	public Division<String> createDivision(String divisionName) {
		Division<String> division = new DivisionImpl();
		division.setDivisionName(divisionName);
		return division;
	}
	
	@Override
	public Team<String> createTeam(String teamName) {
		Team<String> team = new TeamImpl ();
		team.setTeamName(teamName);
		return team;
	}

	@Override
	public Fixture<String> createFixture(Season<String> season, Team<String> homeTeam, Team<String> awayTeam) {
		Fixture<String> fixture = new FixtureImpl();
		fixture.setSeason(season);
		fixture.setHomeTeam(homeTeam);
		fixture.setAwayTeam(awayTeam);
		return fixture;
	}

	@Override
	public SeasonDivision<String, String> createSeasonDivision(Season<String> season, Division<String> division, int position) {
		SeasonDivision<String, String> seasonDivision = new SeasonDivisionImpl();
		seasonDivision.setSeason(season);
		seasonDivision.setDivision(division);
		seasonDivision.setDivisionPosition(position);
		return seasonDivision;
	}

	@Override
	public SeasonDivisionTeam<String, String, String> createSeasonDivisionTeam(SeasonDivision<String, String> seasonDivision, Team<String> team) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createSeasonDivisionId(Season<String> season, Division<String> division) {
		throw new RuntimeException("Unimplemented method");
//		SeasonDivisionId seasonDivisionId = new SeasonDivisionIdImpl();
//		seasonDivisionId.setSeason(season);
//		seasonDivisionId.setDivision(division);
//		return seasonDivisionId;
	}

	@Override
	public String createSeasonDivisionTeamId(SeasonDivision<String, String> seasonDivision, Team<String> team) {
		throw new RuntimeException("Unimplemented method");
//		SeasonDivisionTeamId seasonDivisionTeamId = new SeasonDivisionTeamIdImpl();
//		seasonDivisionTeamId.setSeasonDivision(seasonDivision);
//		seasonDivisionTeamId.setTeam(team);
//		return seasonDivisionTeamId;
	}
}
