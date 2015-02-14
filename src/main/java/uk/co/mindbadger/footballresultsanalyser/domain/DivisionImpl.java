package uk.co.mindbadger.footballresultsanalyser.domain;

import java.util.Set;

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
	public Set<SeasonDivision<String>> getSeasonsForDivision() {
		throw new RuntimeException ("Unimplemented method");
	}

	@Override
	public void setSeasonsForDivision(Set<SeasonDivision<String>> arg0) {
		throw new RuntimeException ("Unimplemented method");
	}

	@Override
	public String getDivisionIdAsString() {
		return divisionId;
	}
}
