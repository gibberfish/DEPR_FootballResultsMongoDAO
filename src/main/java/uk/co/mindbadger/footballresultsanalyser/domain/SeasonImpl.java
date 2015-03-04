package uk.co.mindbadger.footballresultsanalyser.domain;

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
}
