package uk.co.mindbadger.footballresultsanalyser.dao;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;

import uk.co.mindbadger.footballresultsanalyser.domain.Division;
import uk.co.mindbadger.footballresultsanalyser.domain.Fixture;
import uk.co.mindbadger.footballresultsanalyser.domain.Season;
import uk.co.mindbadger.footballresultsanalyser.domain.SeasonDivision;
import uk.co.mindbadger.footballresultsanalyser.domain.SeasonDivisionTeam;
import uk.co.mindbadger.footballresultsanalyser.domain.Team;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

public class FootballResultsAnalyserMongoDAO implements	FootballResultsAnalyserDAO<String> {

	Logger logger = Logger.getLogger(FootballResultsAnalyserMongoDAO.class);
	
	private String mongoHost;
	private MongoClient mongoClient;
	private DB db;
	private String mongoDb;
		
	@Override
	public Division<String> addDivision(String divisionName) {
		DBCollection divisions = db.getCollection("division");
		
		BasicDBObject division = new BasicDBObject("div_name", divisionName); 
		divisions.insert(division);
		ObjectId id = (ObjectId)division.get( "_id" );
		
		return null;
	}

	@Override
	public Fixture<String> addFixture(Season<String> arg0, Calendar arg1,
			Division<String> arg2, Team<String> arg3, Team<String> arg4,
			Integer arg5, Integer arg6) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Season<String> addSeason(Integer arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Team<String> addTeam(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void closeSession() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, Division<String>> getAllDivisions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Team<String>> getAllTeams() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<SeasonDivision<String>> getDivisionsForSeason(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Fixture<String>> getFixturesForTeamInDivisionInSeason(int arg0,
			int arg1, int arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Fixture<String>> getFixturesWithNoFixtureDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Season<String> getSeason(Integer arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Season<String>> getSeasons() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<SeasonDivisionTeam<String>> getTeamsForDivisionInSeason(int arg0,
			int arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Fixture<String>> getUnplayedFixturesBeforeToday() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void startSession() {
		// TODO Auto-generated method stub
		
	}


}
