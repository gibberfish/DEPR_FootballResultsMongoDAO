package uk.co.mindbadger.footballresultsanalyser.domain;

public class SeasonDivisionImpl implements SeasonDivision<String, String> {
	private static final long serialVersionUID = -1409106637881070795L;
	
	private Division<String> division;
	private int divisionPosition;
	private Season<String> season;
	private String id;

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
	public void setId(String id) {
		this.id = id;
	}
	
	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public int compareTo(SeasonDivision<String, String> compareTo) {
		if (compareTo.getSeason().getSeasonNumber() != this.getSeason().getSeasonNumber()) {
			return this.getSeason().getSeasonNumber() - compareTo.getSeason().getSeasonNumber();
		} else if (compareTo.getDivisionPosition() != this.getDivisionPosition()) {
			return this.getDivisionPosition() - compareTo.getDivisionPosition();
		} else {
			return this.getDivision().getDivisionName().compareTo(compareTo.getDivision().getDivisionName());
		}
	}
}

