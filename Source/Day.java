import java.util.ArrayList;

public class Day {
	public int numberOfTours;
	public ArrayList<Tour> tourList;
	public boolean holiday;
	
	Day(int dayOfTheMonth, int numTours, int month){
		numberOfTours = numTours;
		holiday = false;
		tourList = new ArrayList<Tour>();
		for(int i = 0; i < numberOfTours; i++){
			Tour newTour = new Tour(dayOfTheMonth, i, month);
			tourList.add(newTour);
		} //For
	} //Day()
} 
