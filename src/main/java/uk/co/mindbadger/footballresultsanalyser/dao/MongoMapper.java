package uk.co.mindbadger.footballresultsanalyser.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.mongodb.DBObject;

import uk.co.mindbadger.footballresultsanalyser.domain.Division;
import uk.co.mindbadger.footballresultsanalyser.domain.DomainObjectFactory;
import uk.co.mindbadger.footballresultsanalyser.domain.Fixture;
import uk.co.mindbadger.footballresultsanalyser.domain.Season;
import uk.co.mindbadger.footballresultsanalyser.domain.Team;

import static uk.co.mindbadger.footballresultsanalyser.dao.MongoEntityNames.*;

public class MongoMapper {
	private DomainObjectFactory<String> domainObjectFactory;
	private FootballResultsAnalyserDAO<String> dao;

	public Season<String> mapMongoToSeason (DBObject mongoObject) {
		Integer seasonNumber = (Integer) mongoObject.get(ID);
		Season<String> season = domainObjectFactory.createSeason(seasonNumber);
		return season;
	}

	public Division<String> mapMongoToDivision (DBObject mongoObject) {
		String divName = mongoObject.get(DIV_NAME).toString();
		String divId = mongoObject.get(ID).toString();
		Division<String> division = domainObjectFactory.createDivision(divName);
		division.setDivisionId(divId);
		return division;
	}

	public Team<String> mapMongoToTeam (DBObject mongoObject) {
		String teamName = mongoObject.get(TEAM_NAME).toString();
		String teamId = mongoObject.get(ID).toString();
		Team<String> team = domainObjectFactory.createTeam(teamName);
		team.setTeamId(teamId);
		return team;
	}

	public Fixture<String> mapMongoToFixture (DBObject mongoObject) {
		String seasonNumber = mongoObject.get(SSN_NUM).toString();
		String homeTeamId = mongoObject.get(HOME_TEAM_ID).toString();
		String awayTeamId = mongoObject.get(AWAY_TEAM_ID).toString();
		String id = mongoObject.get(ID).toString();
		
		Calendar fixtureDate = null;
		String fixtureDateAsString = null;
		if (mongoObject.get(FIXTURE_DATE) != null) {
			fixtureDateAsString = mongoObject.get(FIXTURE_DATE).toString();
			fixtureDate = Calendar.getInstance(); //TODO convert the string into a data
			SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
			try {
				fixtureDate.setTime(sdf.parse(fixtureDateAsString));
			} catch (ParseException e) {
				throw new RuntimeException (e);
			}
		}
		
		String divId = mongoObject.get(DIV_ID).toString();
		Integer homeGoals = null;
		if (mongoObject.get(HOME_GOALS) != null) {
			homeGoals = Integer.parseInt(mongoObject.get(HOME_GOALS).toString());
		}
		Integer awayGoals = null;
		if (mongoObject.get(AWAY_GOALS) != null) {
			awayGoals = Integer.parseInt(mongoObject.get(AWAY_GOALS).toString());
		}
		
		Season<String> season = dao.getSeason(Integer.parseInt(seasonNumber));
		Team<String> homeTeam = dao.getTeam(homeTeamId);
		Team<String> awayTeam = dao.getTeam(awayTeamId);
		Division<String> division = dao.getDivision(divId);
		
		Fixture<String> fixture = domainObjectFactory.createFixture(season, homeTeam, awayTeam);
		fixture.setDivision(division);
		fixture.setFixtureDate(fixtureDate);
		fixture.setHomeGoals(homeGoals);
		fixture.setAwayGoals(awayGoals);
		fixture.setFixtureId(id);
		
		return fixture;
	}
	
	public DomainObjectFactory<String> getDomainObjectFactory() {
		return domainObjectFactory;
	}

	public void setDomainObjectFactory(DomainObjectFactory<String> domainObjectFactory) {
		this.domainObjectFactory = domainObjectFactory;
	}

	public FootballResultsAnalyserDAO<String> getDao() {
		return dao;
	}

	public void setDao(FootballResultsAnalyserDAO<String> dao) {
		this.dao = dao;
	}

}
