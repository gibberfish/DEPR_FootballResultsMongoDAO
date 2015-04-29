package uk.co.mindbadger.footballresultsanalyser.domain;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import org.junit.*;
import org.mockito.*;

public class SeasonDivisionImplTest {
	@Mock
	private Season<String> mockSeason1992;

	@Mock
	private Season<String> mockSeason2001;

	@Mock
	private Division<String> mockDivisionPrem;

	@Mock
	private Division<String> mockDivisionChamp;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);

		when(mockSeason1992.getSeasonNumber()).thenReturn(1992);
		when(mockSeason2001.getSeasonNumber()).thenReturn(2001);
		
		when(mockDivisionPrem.getDivisionName()).thenReturn("Premier League");
		when(mockDivisionChamp.getDivisionName()).thenReturn("Championship");
	}

	@Test
	public void shouldCompareTwoSeasonDivisionsWithDifferentSeasons () {
		// Given
		SeasonDivisionImpl prem1992 = new SeasonDivisionImpl ();
		prem1992.setSeason(mockSeason1992);
		prem1992.setDivision(mockDivisionPrem);
		prem1992.setDivisionPosition(1);
		
		SeasonDivisionImpl champ2001 = new SeasonDivisionImpl ();
		champ2001.setSeason(mockSeason2001);
		champ2001.setDivision(mockDivisionChamp);
		champ2001.setDivisionPosition(2);
		
		// When
		int compare1 = prem1992.compareTo(champ2001);
		
		// Then
		assertTrue (compare1 < 0);
		
		// When
		int compare2 = champ2001.compareTo(prem1992);
		
		// Then
		assertTrue (compare2 > 0);
	}
	
	@Test
	public void shouldCompareTwoSeasonDivisionsWithSameSeasonButDifferentDivisionPositions () {
		// Given
		SeasonDivisionImpl prem1992 = new SeasonDivisionImpl ();
		prem1992.setSeason(mockSeason1992);
		prem1992.setDivision(mockDivisionPrem);
		prem1992.setDivisionPosition(1);
		
		SeasonDivisionImpl champ1992 = new SeasonDivisionImpl ();
		champ1992.setSeason(mockSeason1992);
		champ1992.setDivision(mockDivisionChamp);
		champ1992.setDivisionPosition(2);
		
		// When
		int compare1 = prem1992.compareTo(champ1992);
		
		// Then
		assertTrue (compare1 < 0);
		
		// When
		int compare2 = champ1992.compareTo(prem1992);
		
		// Then
		assertTrue (compare2 > 0);
	}

	@Test
	public void shouldCompareTwoSeasonDivisionsWithSameSeasonAndSameDivisionPositionsUsingDivisionName () {
		// Given
		SeasonDivisionImpl prem1992 = new SeasonDivisionImpl ();
		prem1992.setSeason(mockSeason1992);
		prem1992.setDivision(mockDivisionPrem);
		prem1992.setDivisionPosition(1);
		
		SeasonDivisionImpl champ1992 = new SeasonDivisionImpl ();
		champ1992.setSeason(mockSeason1992);
		champ1992.setDivision(mockDivisionChamp);
		champ1992.setDivisionPosition(1);
		
		// When
		int compare1 = prem1992.compareTo(champ1992);
		
		// Then
		assertTrue (compare1 > 0);
		
		// When
		int compare2 = champ1992.compareTo(prem1992);
		
		// Then
		assertTrue (compare2 < 0);
	}

}
