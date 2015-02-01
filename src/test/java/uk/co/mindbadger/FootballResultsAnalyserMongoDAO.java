package uk.co.mindbadger;

import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.mongodb.DB;
import com.mongodb.MongoClient;

import uk.co.mindbadger.footballresultsanalyser.dao.FootballResultsAnalyserDAO;
import uk.co.mindbadger.footballresultsanalyser.domain.Division;
import uk.co.mindbadger.footballresultsanalyser.domain.Fixture;
import uk.co.mindbadger.footballresultsanalyser.domain.Season;
import uk.co.mindbadger.footballresultsanalyser.domain.SeasonDivision;
import uk.co.mindbadger.footballresultsanalyser.domain.SeasonDivisionTeam;
import uk.co.mindbadger.footballresultsanalyser.domain.Team;

public class FootballResultsAnalyserMongoDAO implements
		FootballResultsAnalyserDAO {

	private String mongoHost;
	private MongoClient mongoClient;
	private DB db;
	private String mongoDb;
	
	public FootballResultsAnalyserMongoDAO () throws UnknownHostException {
		mongoClient = new MongoClient (mongoHost);
		setDb(mongoClient.getDB(mongoDb));
	}
	
	@Override
	public Division addDivision(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Fixture addFixture(Season arg0, Calendar arg1, Division arg2,
			Team arg3, Team arg4, Integer arg5, Integer arg6) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Season addSeason(Integer arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Team addTeam(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void closeSession() {
		mongoClient.close();
	}

	@Override
	public Map<Integer, Division> getAllDivisions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Integer, Team> getAllTeams() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<SeasonDivision> getDivisionsForSeason(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Fixture> getFixturesForTeamInDivisionInSeason(int arg0,
			int arg1, int arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Fixture> getFixturesWithNoFixtureDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Season getSeason(Integer arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Season> getSeasons() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<SeasonDivisionTeam> getTeamsForDivisionInSeason(int arg0,
			int arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Fixture> getUnplayedFixturesBeforeToday() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void startSession() {
		try {
			mongoClient = new MongoClient (mongoHost);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
		db = mongoClient.getDB(mongoDb);
	}

	public String getMongoHost() {
		return mongoHost;
	}

	public void setMongoHost(String mongoHost) {
		this.mongoHost = mongoHost;
	}

	public DB getDb() {
		return db;
	}

	public void setDb(DB db) {
		this.db = db;
	}

}
