/**
 * IMPORTANT: This test needs Mongo running to pass - it does NOT attempt to mock Mongo 
 */
package uk.co.mindbadger.footballresultsanalyser.dao;

import static org.junit.Assert.*;

import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

import uk.co.mindbadger.footballresultsanalyser.domain.Division;
import uk.co.mindbadger.footballresultsanalyser.domain.DomainObjectFactory;
import uk.co.mindbadger.footballresultsanalyser.domain.DomainObjectFactoryImpl;
import uk.co.mindbadger.footballresultsanalyser.domain.Fixture;
import uk.co.mindbadger.footballresultsanalyser.domain.Season;
import uk.co.mindbadger.footballresultsanalyser.domain.Team;

public class FootballResultsAnalyserMongoDAOTest {
	private static final int SEASON3 = 2003;
	private static final int SEASON2 = 2002;
	private static final int SEASON1 = 2001;
	private static final String DIVISION3 = "League 1";
	private static final String DIVISION2 = "Championship";
	private static final String TEAM3 = "Leeds";
	private static final String TEAM2 = "Liverpool";
	private static final String TEAM1 = "Portsmouth";
	private static final String TEAM4 = "Burnley";
	private static final String DIVISION1 = "Premier League";
	private static final String MONGO_DB_HOST = "localhost";
	private static final String MONGO_TEST_DB_NAME = "footballtest";
	private static final String SEASON_COLLECTION = "season";
	private static final String DIVISION_COLLECTION = "division";
	private static final String TEAM_COLLECTION = "team";
	private static final String FIXTURE_COLLECTION = "fixture";
	
	public FootballResultsAnalyserDAO<String> dao;
	private DomainObjectFactory<String> domainObjectFactory;

	@Before
	public void setup() throws UnknownHostException {
		dao = new FootballResultsAnalyserMongoDAO();
		domainObjectFactory = new DomainObjectFactoryImpl();
		
		((FootballResultsAnalyserMongoDAO)dao).setDbName(MONGO_TEST_DB_NAME);
		((FootballResultsAnalyserMongoDAO)dao).setDomainObjectFactory(domainObjectFactory);
		((FootballResultsAnalyserMongoDAO)dao).setMongoHost(MONGO_DB_HOST);
		
		dao.startSession();
		
		MongoClient client = new MongoClient(MONGO_DB_HOST);			
		DB mongoDb = client.getDB( MONGO_TEST_DB_NAME );

		DBCollection seasons = mongoDb.getCollection(SEASON_COLLECTION);
		seasons.drop();

		DBCollection divisions = mongoDb.getCollection(DIVISION_COLLECTION);
		divisions.drop();

		DBCollection teams = mongoDb.getCollection(TEAM_COLLECTION);
		teams.drop();

		DBCollection fixtures = mongoDb.getCollection(FIXTURE_COLLECTION);
		fixtures.drop();
	}
	
	@After
	public void tearDown() {
		dao.closeSession();
	}
	
	@Test
	public void shouldStartWithNoData() {
		// Given

		// When
		List<Season<String>> seasons = dao.getSeasons();
		Map<String, Division<String>> divisions = dao.getAllDivisions();
		Map<String, Team<String>> teams = dao.getAllTeams();
		
		// Then
		assertEquals (0, seasons.size());
		assertEquals (0, divisions.size());
		assertEquals (0, teams.size());
	}

	@Test
	public void shouldAddSeasons() {
		// Given

		// When
		Season<String> season1 = dao.addSeason(SEASON1);
		Season<String> season2 = dao.addSeason(SEASON2);
		Season<String> season3 = dao.addSeason(SEASON3);
		
		// Then
		List<Season<String>> seasons = dao.getSeasons();
		assertEquals (3, seasons.size());
		
		assertEquals (SEASON1, season1.getSeasonNumber().intValue());
		assertEquals (SEASON2, season2.getSeasonNumber().intValue());
		assertEquals (SEASON3, season3.getSeasonNumber().intValue());

		assertEquals (SEASON1, seasons.get(0).getSeasonNumber().intValue());
		assertEquals (SEASON2, seasons.get(1).getSeasonNumber().intValue());
		assertEquals (SEASON3, seasons.get(2).getSeasonNumber().intValue());
	}

	@Test
	public void shouldGetSeasons() {
		// Given
		Season<String> season1 = dao.addSeason(SEASON1);
		Season<String> season2 = dao.addSeason(SEASON2);
		Season<String> season3 = dao.addSeason(SEASON3);

		// When
		Season<String> season1FromDb = dao.getSeason(SEASON1);
		Season<String> season2FromDb = dao.getSeason(SEASON2);
		Season<String> season3FromDb = dao.getSeason(SEASON3);
		
		// Then
		assertTrue (season1 != season1FromDb);
		assertEquals (season1.getSeasonNumber(), season1FromDb.getSeasonNumber());

		assertTrue (season2 != season2FromDb);
		assertEquals (season2.getSeasonNumber(), season2FromDb.getSeasonNumber());

		assertTrue (season3 != season3FromDb);
		assertEquals (season3.getSeasonNumber(), season3FromDb.getSeasonNumber());
	}

	
	@Test
	public void shouldAddDivision () {
		// Given
		
		// When
		Division<String> division1 = dao.addDivision(DIVISION1);
		Division<String> division2 = dao.addDivision(DIVISION2);
		Division<String> division3 = dao.addDivision(DIVISION3);
		
		String div1Id = division1.getDivisionId();
		String div2Id = division2.getDivisionId();
		String div3Id = division3.getDivisionId();
		
		// Then
		Map<String, Division<String>> divisions = dao.getAllDivisions();
		assertEquals (3, divisions.size());
		
		assertEquals (DIVISION1, divisions.get(div1Id).getDivisionName());
		assertEquals (DIVISION2, divisions.get(div2Id).getDivisionName());
		assertEquals (DIVISION3, divisions.get(div3Id).getDivisionName());
		
		assertEquals (division1.getDivisionId(), division1.getDivisionIdAsString());
		assertEquals (division2.getDivisionId(), division2.getDivisionIdAsString());
		assertEquals (division3.getDivisionId(), division3.getDivisionIdAsString());
	}
	
	@Test
	public void shouldAddTeam () {
		// Given
		
		// When
		Team<String> team1 = dao.addTeam(TEAM1);
		Team<String> team2 = dao.addTeam(TEAM2);
		Team<String> team3 = dao.addTeam(TEAM3);
		
		String team1Id = team1.getTeamId();
		String team2Id = team2.getTeamId();
		String team3Id = team3.getTeamId();
		
		// Then
		Map<String, Team<String>> teams = dao.getAllTeams();
		assertEquals (3, teams.size());
		
		assertEquals (TEAM1, teams.get(team1Id).getTeamName());
		assertEquals (TEAM2, teams.get(team2Id).getTeamName());
		assertEquals (TEAM3, teams.get(team3Id).getTeamName());
		
		assertEquals (team1.getTeamId(), team1.getTeamIdAsString());
		assertEquals (team2.getTeamId(), team2.getTeamIdAsString());
		assertEquals (team3.getTeamId(), team3.getTeamIdAsString());
	}

	@Test
	public void shouldAddFixturesForTeamsInDivisionsInSeasons () {
		// Given
		Season<String> season1 = dao.addSeason(SEASON1);
		Season<String> season2 = dao.addSeason(SEASON2);
		
		Calendar fixtureDate1 = Calendar.getInstance(); fixtureDate1.set(Calendar.DAY_OF_MONTH, 4);
		Calendar fixtureDate2 = Calendar.getInstance(); fixtureDate2.set(Calendar.DAY_OF_MONTH, 7);
		
		Division<String> division1 = dao.addDivision(DIVISION1);
		Division<String> division2 = dao.addDivision(DIVISION2);
		
		Team<String> team1 = dao.addTeam(TEAM1);
		Team<String> team2 = dao.addTeam(TEAM2);
		Team<String> team3 = dao.addTeam(TEAM3);
		Team<String> team4 = dao.addTeam(TEAM4);
		
		// When
		Fixture<String> fixture1 = dao.addFixture(season1, fixtureDate1, division1, team1, team2, 2, 1);
		Fixture<String> fixture2 = dao.addFixture(season1, fixtureDate1, division1, team3, team4, 2, 1);
		Fixture<String> fixture3 = dao.addFixture(season1, fixtureDate2, division1, team4, team1, 2, 1);

		Fixture<String> fixture4 = dao.addFixture(season2, fixtureDate1, division1, team1, team2, 2, 1);
		Fixture<String> fixture5 = dao.addFixture(season2, fixtureDate1, division2, team3, team4, 2, 1);
		Fixture<String> fixture6 = dao.addFixture(season2, fixtureDate2, division1, team2, team1, 2, 1);
		Fixture<String> fixture7 = dao.addFixture(season2, fixtureDate2, division2, team4, team3, 2, 1);
		
		// Then
		List<Fixture<String>> fixturesForSsn1Div1Tm1 = dao.getFixturesForTeamInDivisionInSeason(season1, division1, team1);
		assertEquals (2, fixturesForSsn1Div1Tm1.size());
		assertEquals(fixture1.getFixtureId(), fixturesForSsn1Div1Tm1.get(0).getFixtureId());
		assertEquals(fixture3.getFixtureId(), fixturesForSsn1Div1Tm1.get(1).getFixtureId());	
		
		List<Fixture<String>> fixturesForSsn1Div1Tm2 = dao.getFixturesForTeamInDivisionInSeason(season1, division1, team2);
		assertEquals (1, fixturesForSsn1Div1Tm2.size());
		assertEquals(fixture1.getFixtureId(), fixturesForSsn1Div1Tm2.get(0).getFixtureId());

		List<Fixture<String>> fixturesForSsn1Div1Tm4 = dao.getFixturesForTeamInDivisionInSeason(season1, division1, team4);
		assertEquals (2, fixturesForSsn1Div1Tm4.size());
		assertEquals(fixture2.getFixtureId(), fixturesForSsn1Div1Tm4.get(0).getFixtureId());
		assertEquals(fixture3.getFixtureId(), fixturesForSsn1Div1Tm4.get(1).getFixtureId());

		List<Fixture<String>> fixturesForSsn2Div1Tm1 = dao.getFixturesForTeamInDivisionInSeason(season2, division1, team1);
		assertEquals (2, fixturesForSsn1Div1Tm4.size());
		assertEquals(fixture4.getFixtureId(), fixturesForSsn2Div1Tm1.get(0).getFixtureId());
		assertEquals(fixture6.getFixtureId(), fixturesForSsn2Div1Tm1.get(1).getFixtureId());
		
		List<Fixture<String>> fixturesForSsn2Div2Tm4 = dao.getFixturesForTeamInDivisionInSeason(season2, division2, team4);
		assertEquals (2, fixturesForSsn2Div2Tm4.size());
		assertEquals(fixture5.getFixtureId(), fixturesForSsn2Div2Tm4.get(0).getFixtureId());
		assertEquals(fixture7.getFixtureId(), fixturesForSsn2Div2Tm4.get(1).getFixtureId());

		List<Fixture<String>> fixturesForSsn2Div2Tm1 = dao.getFixturesForTeamInDivisionInSeason(season2, division2, team1);
		assertEquals (0, fixturesForSsn2Div2Tm1.size());

	}
}
