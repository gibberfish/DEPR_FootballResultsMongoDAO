package uk.co.mindbadger.footballresultsanalyser.domain;

public class DivisionImpl implements Division<String> {
	private static final long serialVersionUID = -4631642049305209765L;
	
	private String divisionId;
	private String divisionName;

	@Override
	public String getDivisionId() {
		return divisionId;
	}

	@Override
	public String getDivisionName() {
		return divisionName;
	}

	@Override
	public void setDivisionId(String divisionId) {
		this.divisionId = divisionId;
	}

	@Override
	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}

	@Override
	public String getDivisionIdAsString() {
		return divisionId;
	}
}
