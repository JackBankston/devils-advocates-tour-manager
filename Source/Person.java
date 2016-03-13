import java.util.ArrayList;

public class Person {
	public String name;
	public ArrayList<Boolean> availabilityBooleans;
	public ArrayList<Integer> scheduledDays;
	public int numOfAvailableSlots;
	public int toursThisMonth;
	public ArrayList<Tour> tourList;
	public ArrayList<Boolean> weeks;
	
	//Returns true if the current day is available. False, otherwise.
	public boolean checkIfScheduled(int day){
		for(int i = 0; i < scheduledDays.size(); i++){
			if(scheduledDays.get(i) == day)
				return false;
		}
		return true;
	}
	
	public void sortTours(){
		Tour temp;
		for(int i = 0; i < tourList.size(); i++){
			for(int j = i; j > 0; j--){
				if(tourList.get(j).day < tourList.get(j - 1).day){
					//Swap
					temp = tourList.get(j);
					tourList.set(j, tourList.get(j - 1));
					tourList.set(j - 1, temp);
				}
			}
		}
	}
	
	Person(){//A constructor
		availabilityBooleans = new ArrayList<Boolean>();
		scheduledDays = new ArrayList<Integer>();
		tourList = new ArrayList<Tour>();
		weeks = new ArrayList<Boolean>();
		toursThisMonth = 0;
		numOfAvailableSlots = 0;
	}
}

