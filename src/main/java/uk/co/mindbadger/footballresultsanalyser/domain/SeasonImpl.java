package uk.co.mindbadger.footballresultsanalyser.domain;

import java.util.Set;

public class SeasonImpl implements Season<String> {
	private static final long serialVersionUID = 8947201640485942612L;
	
	private Integer seasonNumber;

	@Override
	public Integer getSeasonNumber() {
		return seasonNumber;
	}

	@Override
	public void setSeasonNumber(Integer seasonNumber) {
		this.seasonNumber = seasonNumber;
	}

	@Override
	public Set<SeasonDivision<String>> getDivisionsInSeason() {
		throw new RuntimeException ("Unimplemented method");
	}

	@Override
	public void setDivisionsInSeason(Set<SeasonDivision<String>> arg0) {
		throw new RuntimeException ("Unimplemented method");
	}
}
