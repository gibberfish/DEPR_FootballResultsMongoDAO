package uk.co.mindbadger.footballresultsanalyser.dao;

public class ChangeScoreException extends RuntimeException {

	private static final long serialVersionUID = 6858683696179707204L;
	
	public ChangeScoreException (String message) {
		super(message);
	}

}
