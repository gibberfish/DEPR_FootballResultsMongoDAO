package uk.co.mindbadger.footballresultsanalyser.dao;

import static uk.co.mindbadger.footballresultsanalyser.dao.MongoEntityNames.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;

import uk.co.mindbadger.footballresultsanalyser.domain.Division;
import uk.co.mindbadger.footballresultsanalyser.domain.DomainObjectFactory;
import uk.co.mindbadger.footballresultsanalyser.domain.Fixture;
import uk.co.mindbadger.footballresultsanalyser.domain.Season;
import uk.co.mindbadger.footballresultsanalyser.domain.SeasonDivision;
import uk.co.mindbadger.footballresultsanalyser.domain.SeasonDivisionTeam;
import uk.co.mindbadger.footballresultsanalyser.domain.Team;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class FootballResultsAnalyserMongoDAO implements	FootballResultsAnalyserDAO<String, String, String> {
	Logger logger = Logger.getLogger(FootballResultsAnalyserMongoDAO.class);

	private DomainObjectFactory<String, String, String> domainObjectFactory;
	private String dbName;
	private DB db;
	private MongoClient mongoClient;
	private MongoMapper mongoMapper;

	@Override
	public Division<String> addDivision(String divisionName) {
		Division<String> division = null;
		
		DBCollection mongoDivisions = db.getCollection(MONGO_DIVISION);
		DBObject query = new BasicDBObject(DIV_NAME, divisionName);
		DBCursor divisionsCursor = mongoDivisions.find(query);
		if(!divisionsCursor.hasNext()) {
			String divId = addMongoRecord(MONGO_DIVISION, kv(DIV_NAME, divisionName));
			division = domainObjectFactory.createDivision(divisionName);
			division.setDivisionId(divId);
		} else {
			DBObject divisionObject = divisionsCursor.next();
			division = mongoMapper.mapMongoToDivision(divisionObject); 
		}
		
		return division;
	}

	@Override
	public Fixture<String> addFixture(Season<String> season, Calendar fixtureDate, Division<String> division, Team<String> homeTeam, Team<String> awayTeam, Integer homeGoals, Integer awayGoals) {
		Fixture<String> fixture = null; 
				
		Date date = null;
		if (fixtureDate != null) {
			date = fixtureDate.getTime();
		}
		
		Fixture<String> existingFixture = getFixture(season, division, homeTeam, awayTeam);
		
		if (existingFixture == null) {
			String fixtureId = addMongoRecord(MONGO_FIXTURE, kv(SSN_NUM, season.getSeasonNumber()),
					kv(HOME_TEAM_ID, homeTeam.getTeamId()),
					kv(AWAY_TEAM_ID, awayTeam.getTeamId()),
					kv(FIXTURE_DATE, date),
					kv(DIV_ID, division.getDivisionId()),
					kv(HOME_GOALS, homeGoals),
					kv(AWAY_GOALS, awayGoals));
			
			fixture = domainObjectFactory.createFixture(season, homeTeam, awayTeam);
			fixture.setDivision(division);
			fixture.setFixtureDate(fixtureDate);
			fixture.setHomeGoals(homeGoals);
			fixture.setAwayGoals(awayGoals);
			fixture.setFixtureId(fixtureId);
		} else {
			boolean homeGoalsHaveChanged = (existingFixture.getHomeGoals() != null && homeGoals != null && existingFixture.getHomeGoals() != homeGoals);
			boolean awayGoalsHaveChanged = (existingFixture.getAwayGoals() != null && awayGoals != null && existingFixture.getAwayGoals() != awayGoals);
			
			if (homeGoalsHaveChanged || awayGoalsHaveChanged) {
				throw new ChangeScoreException("You cannot update the score of a fixture that has already been played using this method");
			}
			
			updateMongoRecord(existingFixture.getFixtureId(), MONGO_FIXTURE, kv(SSN_NUM, season.getSeasonNumber()),
					kv(HOME_TEAM_ID, homeTeam.getTeamId()),
					kv(AWAY_TEAM_ID, awayTeam.getTeamId()),
					kv(FIXTURE_DATE, date),
					kv(DIV_ID, division.getDivisionId()),
					kv(HOME_GOALS, homeGoals),
					kv(AWAY_GOALS, awayGoals));
	
			fixture = existingFixture;
			fixture.setFixtureDate(fixtureDate);
			fixture.setHomeGoals(homeGoals);
			fixture.setAwayGoals(awayGoals);
		}

		return fixture;
	}

	@Override
	public Season<String> addSeason(Integer seasonNumber) {
		Season<String> season = null;
		Season<String> existingSeason = getSeason(seasonNumber);

		if (existingSeason == null) {
			addMongoRecord(MONGO_SEASON, kv(ID, seasonNumber));
			season = domainObjectFactory.createSeason(seasonNumber);
		} else {
			season = existingSeason;
		}
		
		return season;
	}

	@Override
	public Team<String> addTeam(String teamName) {
		Team<String> team = null;
		
		DBCollection mongoTeams = db.getCollection(MONGO_TEAM);
		DBObject query = new BasicDBObject(TEAM_NAME, teamName);
		DBCursor teamsCursor = mongoTeams.find(query);
		if(teamsCursor.hasNext()) {
			DBObject teamObject = teamsCursor.next();
			team = mongoMapper.mapMongoToTeam(teamObject);
		} else {
			String teamId = addMongoRecord(MONGO_TEAM, kv(TEAM_NAME, teamName));
			team = domainObjectFactory.createTeam(teamName);
			team.setTeamId(teamId);
		}
		
		return team;
	}
	
	@Override
	public Map<String, Division<String>> getAllDivisions() {		
		Map<String, Division<String>> divisions = new HashMap<String, Division<String>> ();
		
		DBCollection mongoDivisions = db.getCollection(MONGO_DIVISION);		
		DBCursor divisionsCursor = mongoDivisions.find();
		
		while(divisionsCursor.hasNext()) {
			DBObject divisionObject = divisionsCursor.next();
			Division<String> division = mongoMapper.mapMongoToDivision (divisionObject);
			divisions.put(division.getDivisionId(), division);
		}
		
		return divisions;
	}
	
	@Override
	public Map<String, Team<String>> getAllTeams() {
		Map<String, Team<String>> teams = new HashMap<String, Team<String>> ();
		
		DBCollection mongoTeams = db.getCollection(MONGO_TEAM);		
		DBCursor teamsCursor = mongoTeams.find();
		
		while(teamsCursor.hasNext()) {
			DBObject teamObject = teamsCursor.next();
			Team<String> newTeam = mongoMapper.mapMongoToTeam (teamObject);
			teams.put(newTeam.getTeamId(), newTeam);
		}
		
		return teams;
	}

//	@Override
//	public Set<SeasonDivision<String>> getDivisionsForSeason(int seasonNumber) {
//		throw new RuntimeException("Not implemented yet!");
//	}

	@Override
	public List<Fixture<String>> getFixturesForTeamInDivisionInSeason(Season<String> season, Division<String> division, Team<String> team) {
		List<Fixture<String>> fixtures = new ArrayList<Fixture<String>> ();
		
		DBCollection mongoFixures = db.getCollection(MONGO_FIXTURE);
		
		DBObject homeQuery = new BasicDBObject(SSN_NUM, season.getSeasonNumber())
			.append(DIV_ID, division.getDivisionIdAsString())
			.append(HOME_TEAM_ID, team.getTeamIdAsString());
		
		DBObject awayQuery = new BasicDBObject(SSN_NUM, season.getSeasonNumber())
			.append(DIV_ID, division.getDivisionIdAsString())
			.append(AWAY_TEAM_ID, team.getTeamIdAsString());
		
		BasicDBList or = new BasicDBList();
		or.add(homeQuery);
		or.add(awayQuery);
		
		DBObject query = new BasicDBObject("$or", or);
		
		DBCursor fixturesCursor = mongoFixures.find(query);
		
		while(fixturesCursor.hasNext()) {
			DBObject fixtureObject = fixturesCursor.next();
			Fixture<String> fixture = mongoMapper.mapMongoToFixture (fixtureObject);
			fixtures.add(fixture);
		}
		
		return fixtures;
	}
	
	@Override
	public List<Fixture<String>> getFixturesWithNoFixtureDate() {
		List<Fixture<String>> fixtures = new ArrayList<Fixture<String>> ();
		
		DBCollection mongoFixures = db.getCollection(MONGO_FIXTURE);
		
		DBObject noFixtureDate = new BasicDBObject(FIXTURE_DATE,new BasicDBObject("$exists", false));
		DBObject fixtureDateIsNull = new BasicDBObject(FIXTURE_DATE, null);
		
		BasicDBList or = new BasicDBList();
		or.add(noFixtureDate);
		or.add(fixtureDateIsNull);
		
		DBObject query = new BasicDBObject("$or", or);
		
		DBCursor fixturesCursor = mongoFixures.find(query);
		
		while(fixturesCursor.hasNext()) {
			DBObject fixtureObject = fixturesCursor.next();
			Fixture<String> fixture = mongoMapper.mapMongoToFixture (fixtureObject);
			
			fixtures.add(fixture);
		}
		
		return fixtures;
	}
	
	@Override
	public Season<String> getSeason(Integer seasonNumber) {
		DBCollection mongoSeasons = db.getCollection(MONGO_SEASON);
		DBObject query = new BasicDBObject(ID, seasonNumber);
		DBCursor seasonsCursor = mongoSeasons.find(query);
		if(seasonsCursor.hasNext()) {
			DBObject seasonObject = seasonsCursor.next();
			return mongoMapper.mapMongoToSeason(seasonObject);
		}
		return null;
	}

	@Override
	public Division<String> getDivision(String divId) {
		DBCollection mongoDivisions = db.getCollection(MONGO_DIVISION);
		ObjectId id= new ObjectId(divId);  
		DBObject query = new BasicDBObject(ID, id);
		DBCursor divisionsCursor = mongoDivisions.find(query);
		if(divisionsCursor.hasNext()) {
			DBObject divisionObject = divisionsCursor.next();
			return mongoMapper.mapMongoToDivision(divisionObject);
		}
		return null;
	}
	
	@Override
	public Team<String> getTeam(String teamId) {
		DBCollection mongoTeams = db.getCollection(MONGO_TEAM);
		ObjectId id= new ObjectId(teamId);
		DBObject query = new BasicDBObject(ID, id);
		DBCursor teamsCursor = mongoTeams.find(query);
		if(teamsCursor.hasNext()) {
			DBObject teamObject = teamsCursor.next();
			return mongoMapper.mapMongoToTeam(teamObject);
		}
		return null;
	}

	@Override
	public List<Season<String>> getSeasons() {
		List<Season<String>> seasons = new ArrayList<Season<String>> ();
		
		DBCollection mongoSeasons = db.getCollection(MONGO_SEASON);		
		DBCursor seasonsCursor = mongoSeasons.find();
		seasonsCursor.sort(new BasicDBObject(ID,-1));
		
		while(seasonsCursor.hasNext()) {
			DBObject seasonObject = seasonsCursor.next();
			Season<String> newSeason = mongoMapper.mapMongoToSeason (seasonObject);
			seasons.add(newSeason);
		}
		
		return seasons;	
	}

//	@Override
//	public Set<SeasonDivisionTeam<String>> getTeamsForDivisionInSeason(int arg0,int arg1) {
//		throw new RuntimeException("Not implemented yet!");
//	}

	@Override
	public List<Fixture<String>> getUnplayedFixturesBeforeToday() {
		List<Fixture<String>> fixtures = new ArrayList<Fixture<String>> ();
		
		DBCollection mongoFixures = db.getCollection(MONGO_FIXTURE);
		
		Calendar today = Calendar.getInstance();
		
		DBObject query = new BasicDBObject();
		query.put (FIXTURE_DATE,new BasicDBObject("$exists", true));
		query.put (FIXTURE_DATE, new BasicDBObject ("$lt", today.getTime()));
		query.put (HOME_GOALS, null);
		
		DBCursor fixturesCursor = mongoFixures.find(query);
		
		while(fixturesCursor.hasNext()) {
			DBObject fixtureObject = fixturesCursor.next();
			Fixture<String> fixture = mongoMapper.mapMongoToFixture (fixtureObject);
			fixtures.add(fixture);
		}
		
		return fixtures;
	}

	@Override
	public Fixture<String> getFixture(Season<String> season, Division<String> division, Team<String> homeTeam, Team<String> awayTeam) {
		Fixture<String> fixture = null;
		
		DBCollection mongoFixures = db.getCollection(MONGO_FIXTURE);
		
		DBObject query = new BasicDBObject(SSN_NUM, season.getSeasonNumber())
			.append(DIV_ID, division.getDivisionId())
			.append(HOME_TEAM_ID, homeTeam.getTeamId())
			.append(AWAY_TEAM_ID, awayTeam.getTeamId());
		
		DBCursor fixturesCursor = mongoFixures.find(query);
		
		if(fixturesCursor.hasNext()) {
			DBObject fixtureObject = fixturesCursor.next();
			fixture = mongoMapper.mapMongoToFixture (fixtureObject);
		}
		
		return fixture;
	}

	@Override
	public SeasonDivision<String, String> getSeasonDivision(Season<String> season, Division<String> division) {
		DBCollection mongoSeasonDivisions = db.getCollection(MONGO_SEASON_DIVISION);
		DBObject query = new BasicDBObject(SSN_NUM, season.getSeasonNumber())
			.append(DIV_ID, division.getDivisionId());
		
		DBCursor seasonDivisionsCursor = mongoSeasonDivisions.find(query);
		if(seasonDivisionsCursor.hasNext()) {
			DBObject seasonDivisionObject = seasonDivisionsCursor.next();
			return mongoMapper.mapMongoToSeasonDivision(seasonDivisionObject);
		}
		return null;
	}

	@Override
	public SeasonDivision<String, String> getSeasonDivision(String seasonDivisionId) {
		DBCollection mongoSeasonDivisions = db.getCollection(MONGO_SEASON_DIVISION);
		ObjectId id= new ObjectId(seasonDivisionId);
		DBObject query = new BasicDBObject(SSN_DIV_ID, id);
		
		DBCursor seasonDivisionsCursor = mongoSeasonDivisions.find(query);
		if(seasonDivisionsCursor.hasNext()) {
			DBObject seasonDivisionObject = seasonDivisionsCursor.next();
			return mongoMapper.mapMongoToSeasonDivision(seasonDivisionObject);
		}
		return null;
	}

	@Override
	public SeasonDivision<String, String> addSeasonDivision(Season<String> season, Division<String> division, int position) {
		SeasonDivision<String, String> seasonDivision = null;
		
		DBCollection mongoSeasonDivisions = db.getCollection(MONGO_SEASON_DIVISION);
		
		DBObject query = new BasicDBObject(SSN_NUM, season.getSeasonNumber())
			.append(DIV_ID, division.getDivisionIdAsString());
		
		DBCursor seasonDivisionsCursor = mongoSeasonDivisions.find(query);

		if(!seasonDivisionsCursor.hasNext()) {
			String seasonDivisionId = addMongoRecord(MONGO_SEASON_DIVISION, kv(SSN_NUM, season.getSeasonNumber()),
					kv(DIV_ID, division.getDivisionId()),
					kv(POSITION, position));
			
			seasonDivision = domainObjectFactory.createSeasonDivision(season, division, position);
			
			seasonDivision.setId(seasonDivisionId);
		} else {
			DBObject seasonDivisionObject = seasonDivisionsCursor.next();
			seasonDivision = mongoMapper.mapMongoToSeasonDivision(seasonDivisionObject);
			
			updateMongoRecord(seasonDivision.getId(), MONGO_SEASON_DIVISION, kv(SSN_NUM, season.getSeasonNumber()),
					kv(DIV_ID, division.getDivisionId()),
					kv(POSITION, position));

			seasonDivision.setDivisionPosition(position);
		}
		
		return seasonDivision;
	}

	@Override
	public SeasonDivisionTeam<String, String, String> addSeasonDivisionTeam(SeasonDivision<String, String> seasonDivision, Team<String> team) {
		SeasonDivisionTeam<String, String, String> seasonDivisionTeam = null;
		
		DBCollection mongoSeasonDivisionTeams = db.getCollection(MONGO_SEASON_DIVISION_TEAM);
		
		DBObject query = new BasicDBObject(SSN_DIV_ID, seasonDivision.getId())
			.append(TEAM_ID, team.getTeamId());
		
		DBCursor seasonDivisionTeamsCursor = mongoSeasonDivisionTeams.find(query);

		if(!seasonDivisionTeamsCursor.hasNext()) {
			String seasonDivisionTeamId = addMongoRecord(MONGO_SEASON_DIVISION_TEAM, kv(SSN_DIV_ID, seasonDivision.getId()),
					kv(TEAM_ID, team.getTeamId()));
			
			seasonDivisionTeam = domainObjectFactory.createSeasonDivisionTeam(seasonDivision, team);
			
			seasonDivisionTeam.setId(seasonDivisionTeamId);
		} else {
			DBObject seasonDivisionTeamObject = seasonDivisionTeamsCursor.next();
			seasonDivisionTeam = mongoMapper.mapMongoToSeasonDivisionTeam(seasonDivisionTeamObject);
		}
		
		return seasonDivisionTeam;
	}
	
	@Override
	public Set<SeasonDivision<String, String>> getDivisionsForSeason(Season<String> season) {
		Set<SeasonDivision<String, String>> seasonDivisions = new HashSet<SeasonDivision<String, String>> ();
		
		DBCollection mongoSeasonDivisions = db.getCollection(MONGO_SEASON_DIVISION);
		
		DBObject query = new BasicDBObject(SSN_NUM, season.getSeasonNumber());
				
		DBCursor seasonDivisonsCursor = mongoSeasonDivisions.find(query);
		
		while(seasonDivisonsCursor.hasNext()) {
			DBObject seasonDivisionObject = seasonDivisonsCursor.next();
			SeasonDivision<String, String> seasonDivision = mongoMapper.mapMongoToSeasonDivision (seasonDivisionObject);
			seasonDivisions.add(seasonDivision);
		}
		
		return seasonDivisions;
	}
	
	@Override
	public Set<SeasonDivisionTeam<String, String, String>> getTeamsForDivisionInSeason(SeasonDivision<String, String> seasonDivision) {
		Set<SeasonDivisionTeam<String, String, String>> seasonDivisionTeams = new HashSet<SeasonDivisionTeam<String, String, String>> ();
		
		DBCollection mongoSeasonDivisionTeams = db.getCollection(MONGO_SEASON_DIVISION_TEAM);
		
		DBObject query = new BasicDBObject(SSN_DIV_ID, seasonDivision.getId());
				
		DBCursor seasonDivisonTeamsCursor = mongoSeasonDivisionTeams.find(query);
		
		while(seasonDivisonTeamsCursor.hasNext()) {
			DBObject seasonDivisionTeamObject = seasonDivisonTeamsCursor.next();
			SeasonDivisionTeam<String, String, String> seasonDivisionTeam = mongoMapper.mapMongoToSeasonDivisionTeam (seasonDivisionTeamObject);
			seasonDivisionTeams.add(seasonDivisionTeam);
		}
		
		return seasonDivisionTeams;
	}
	
	@Override
	public List<Fixture<String>> getFixtures() {
		List<Fixture<String>> fixtures = new ArrayList<Fixture<String>> ();
		
		DBCollection mongoFixures = db.getCollection(MONGO_FIXTURE);
		
		DBCursor fixturesCursor = mongoFixures.find();
		
		while(fixturesCursor.hasNext()) {
			DBObject fixtureObject = fixturesCursor.next();
			Fixture<String> fixture = mongoMapper.mapMongoToFixture (fixtureObject);
			
			fixtures.add(fixture);
		}
		
		return fixtures;
	}

	@Override
	public List<Fixture<String>> getFixturesForDivisionInSeason(SeasonDivision<String, String> seasonDivision) {
		List<Fixture<String>> fixtures = new ArrayList<Fixture<String>> ();
		
		DBCollection mongoFixures = db.getCollection(MONGO_FIXTURE);
		
		DBObject query = new BasicDBObject(SSN_NUM, seasonDivision.getSeason().getSeasonNumber())
			.append(DIV_ID, seasonDivision.getDivision().getDivisionIdAsString());
		
		DBCursor fixturesCursor = mongoFixures.find(query);
		fixturesCursor.sort(new BasicDBObject(FIXTURE_DATE,1));
		
		while(fixturesCursor.hasNext()) {
			DBObject fixtureObject = fixturesCursor.next();
			Fixture<String> fixture = mongoMapper.mapMongoToFixture (fixtureObject);
			fixtures.add(fixture);
		}
		
		return fixtures;
	}
	
	@PostConstruct
	@Override
	public void startSession() {
		logger.debug("Opening Mongo DB");
		db = mongoClient.getDB(this.dbName);
	}

	@PreDestroy
	@Override
	public void closeSession() {
		logger.debug("Closing Mongo DB");
		mongoClient.close();
	}
	
	public DomainObjectFactory<String, String, String> getDomainObjectFactory() {
		return domainObjectFactory;
	}

	public void setDomainObjectFactory(DomainObjectFactory<String, String, String> domainObjectFactory) {
		this.domainObjectFactory = domainObjectFactory;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	
	public MongoClient getMongoClient() {
		return mongoClient;
	}

	public void setMongoClient(MongoClient mongoClient) {
		this.mongoClient = mongoClient;
	}

	public MongoMapper getMongoMapper() {
		return mongoMapper;
	}

	public void setMongoMapper(MongoMapper mongoMapper) {
		this.mongoMapper = mongoMapper;
	}

	private String addMongoRecord (String collection, KV ... values) {
		DBCollection mongoCollection = db.getCollection(collection);
		BasicDBObject basicObject = new BasicDBObject ();
		for (int i = 0; i < values.length; i++) {
			basicObject.append(values[i].getKey(), values[i].getValue());
		}
		mongoCollection.insert(basicObject);
		Object objectId = basicObject.get(ID);
		return objectId.toString();
	}

	private void updateMongoRecord (String id, String collection, KV ... values) {
		DBCollection mongoCollection = db.getCollection(collection);
		BasicDBObject basicObject = new BasicDBObject ();
		for (int i = 0; i < values.length; i++) {
			basicObject.append(values[i].getKey(), values[i].getValue());
		}
		
		DBObject idObject = new BasicDBObject(ID, id);
		
		mongoCollection.update(idObject, basicObject);
	}

	private class KV {
		private String key;
		private Object value;
		public KV (String key, Object value) {
			this.key = key;
			this.value = value;
		}
		public String getKey () {
			return key;
		}
		public Object getValue () {
			return value;
		}
	}
	
	private KV kv(String key, Object value) {
		return new KV(key, value);
	}

}
