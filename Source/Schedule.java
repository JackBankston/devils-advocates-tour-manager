import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class Schedule {	
	private ArrayList<Person> personList = new ArrayList<Person>();
	private ArrayList<Day> dayList = new ArrayList<Day>();
	private int toursInTheWeek = 0;
	private int currentHighestTourCount; 
	private int firstDay = 0;
	private int numDays = 0;
	private int sundaySaved, mondaySaved, tuesdaySaved, wednesdaySaved, thursdaySaved, fridaySaved, saturdaySaved;
	private int maxGuidesSaved;
	public int makeASchedule(File inputFile, int month, String year, String holidays, int sunday, int monday, int tuesday, int wednesday, int thursday, int friday, int saturday, String minToursPerGuide, String maxToursPerGuide, String minGuidesPerTour, String maxGuidesPerTour) throws IOException{
		//First make the data structures. 
		toursInTheWeek = sunday + monday + tuesday + wednesday + thursday + friday + saturday; //Have to keep track of the number of tours in the week.
		int personStructsResult = makePersonStructures(inputFile);
		//System.out.println("Our person struct result int was " + personStructsResult);
		
		//Check that result...
		//0 means all went well. 
		//-1 means one of the answers wasn't yes/no, Y/N, or 0/1.
		//-2 means at least one line didn't have the right number of availability answers. 
		if(personStructsResult == -1){
			return -1;
		}
		if(personStructsResult == -2){
			return -2;
		}
		
		int minToursPerGuideInt = stringToInt(minToursPerGuide);
		int maxToursPerGuideInt = stringToInt(maxToursPerGuide);
		int minGuidesPerTourInt = stringToInt(minGuidesPerTour);
		int maxGuidesPerTourInt = stringToInt(maxGuidesPerTour);
		if(maxToursPerGuide.isEmpty()) //If they left it empty, it should be a number high enough that it isn't an issue.
			maxToursPerGuideInt = 25; //Random high number.
		if(maxGuidesPerTour.isEmpty())
			maxGuidesPerTourInt = 25; //Random high number.
		
		//Save ints for output:
		sundaySaved = sunday;
		mondaySaved = monday;
		tuesdaySaved = tuesday;
		wednesdaySaved = wednesday;
		thursdaySaved = thursday;
		fridaySaved = friday;
		saturdaySaved = saturday;
		maxGuidesSaved = maxGuidesPerTourInt;
		
		//Now, actual scheduling: 
		makeMonth(month, year, holidays, sunday, monday, tuesday, wednesday, thursday, friday, saturday);
		//Now dayList holds all the days in the month.
	
		int currBoolReset = getCurrentAvailabilityBool(month, year, sunday, monday, tuesday, wednesday, thursday, friday, saturday);
		
		boolean firstScheduleResult = scheduleAlg1(currBoolReset, maxGuidesPerTourInt, maxToursPerGuideInt, minGuidesPerTourInt, minToursPerGuideInt);
		boolean secondScheduleResult = false;
		if(!firstScheduleResult){ //First scheduling algorithm didn't work. 
			System.out.println("First algorithm was unsuccessful.");
			personList = new ArrayList<Person>();
			dayList = new ArrayList<Day>();
			makePersonStructures(inputFile);
			makeMonth(month, year, holidays, sunday, monday, tuesday, wednesday, thursday, friday, saturday);
			
			secondScheduleResult = scheduleAlg2(currBoolReset, maxGuidesPerTourInt, maxToursPerGuideInt, minGuidesPerTourInt, minToursPerGuideInt);
			if(!secondScheduleResult){
				System.out.println("Second algorithm was unsuccessful.");
			}
		}
		//Then writing to the output folder.
		if(firstScheduleResult || secondScheduleResult){
			//printTours(); //For testing purposes.
			return 0; //Success!
		}
		
		return -5; //Neither alg was successful.
	}
	
	public boolean scheduleAlg1(int currBoolReset, int maxGuidesPerTourInt, int maxToursPerGuideInt, int minGuidesPerTourInt, int minToursPerGuideInt){
		//IDEA TWO
		//System.out.println("currBoolReset: " + currBoolReset);
		
		int numberOfWeeks = findNumWeeks();
		for(int x = 0; x < numberOfWeeks; x++){
			for(int y = 0; y < personList.size(); y++){
				//System.out.println("a");
				personList.get(y).weeks.add(false);
			}
		}
		//System.out.println("here it's " + personList.get(0).weeks.size());
		
		int currentAvailabilityBool; //This is the index in the availability boolean array list that is currently relevant. 
		int week = 0;
		boolean innerWhile, outerWhile;
		int currentAvailabilityInt, currentToursGivenInt; 
		currentHighestTourCount = 0;
		if(currBoolReset >= toursInTheWeek){
			currBoolReset = 0;			
		}
		for(int i = 0; i < maxGuidesPerTourInt; i++){
			//System.out.println("guide: " + i);
			//System.out.println("Adding guide " + i);
			currentAvailabilityBool = currBoolReset; //Have to reset it each time we loop through. 
			//System.out.println("OUTERMOST LOOP IS " + i);
			for(int j = 0; j < dayList.size(); j++){
				week = getWeekFromDay(j);
				//System.out.println("Day: " + j + " Week: " + week);
				if(!dayList.get(j).holiday){ //If the current day isn't a holiday.
					//Go through all the tours on the current day.
					//System.out.println(dayList.get(j).numberOfTours + " tours today");
					for(int k = 0; k < dayList.get(j).numberOfTours; k++){
						//System.out.println("Looking at tour " + k);
						outerWhile = true;
						currentToursGivenInt = 0;
						while(outerWhile){
							innerWhile = true;
							//System.out.println("OUTER WHILE - Looking at guides who have given " + currentToursGivenInt + " tours");
							currentAvailabilityInt = 0;
							while(innerWhile){
								//System.out.println("INNER WHILE - Availability of " + currentAvailabilityInt);
								//numOfAvailableSlots
								//Go through guides and find one with an availability matching the currentAvailabilityInt and a true value 
								//at the currentAvailabilityBool. Set lookingForAGuide to true and add that guide to the current tour's list,
								//as long as all the requirements are met. 
								//Remember to check if currentHighestTourCount needs to be incremented.
								for(int m = 0; m < personList.size(); m++){
									//System.out.println("m is " + m + " and currentAvailabilityBool is " + currentAvailabilityBool);
									if(personList.get(m).toursThisMonth == currentToursGivenInt && personList.get(m).numOfAvailableSlots == currentAvailabilityInt && personList.get(m).availabilityBooleans.get(currentAvailabilityBool))
									{
										//Then we've found a guide that's got the minimum availability and is available at this time.
										//Check requirements, then add them if they're met. 
										
										if(personList.get(m).toursThisMonth < maxToursPerGuideInt){
											//Have to check if they've already given a tour that day. 
											if(personList.get(m).checkIfScheduled(j) && personList.get(m).weeks.get(week) == false){ //Make sure they haven't been given a tour on this day before.
												personList.get(m).toursThisMonth++;
												personList.get(m).scheduledDays.add(j); //Add this day to the list. 
												if(personList.get(m).toursThisMonth > currentHighestTourCount){
													currentHighestTourCount = personList.get(m).toursThisMonth;
													//System.out.println("Current highest tour count is " + currentHighestTourCount);
												}
												dayList.get(j).tourList.get(k).addGuide(personList.get(m));
												personList.get(m).tourList.add(dayList.get(j).tourList.get(k)); //Add the tour to that guide's list of tours.
												//System.out.println("We just added " + personList.get(m).name);
												innerWhile = false;
												outerWhile = false;
												personList.get(m).weeks.set(week, true);
												m = personList.size();
											}
										}
									}
								}//For m
							
								currentAvailabilityInt++;
								if(currentAvailabilityInt > toursInTheWeek){
									innerWhile = false;
								}
							} //inner while
							currentToursGivenInt++;
							if(currentToursGivenInt > currentHighestTourCount){
								//We couldn't find a tour guide. Do we break somehow?
								if(i < minGuidesPerTourInt){
									System.out.println("Returned here");
									return false; //Because we never reached the minimum guides per tour number.
								}	
								//Otherwise we can just skip this tour. 
								outerWhile = false;
							}
						} //outer while
						
						//Here's where we update the counter for which availability bool we're looking at.
						currentAvailabilityBool++;
						if(currentAvailabilityBool >= toursInTheWeek){
							currentAvailabilityBool = 0; //Time to reset it.
						}
					}//For k
				}//If not a holiday.
				else{
					for(int k = 0; k < dayList.get(j).numberOfTours; k++){
						currentAvailabilityBool++;
						if(currentAvailabilityBool >= toursInTheWeek){
							currentAvailabilityBool = 0; //Time to reset it.
						}
					}
				}
			}//For j
		}//For i
		//Check if minToursPerGuide has been met.
		if(!checkMinToursPerGuide(minToursPerGuideInt)){
			System.out.println("Min tours per guide not met");
			return false;
		}
		return true; //It worked!
	}
	
	public boolean scheduleAlg2(int currBoolReset, int maxGuidesPerTourInt, int maxToursPerGuideInt, int minGuidesPerTourInt, int minToursPerGuideInt){
		//IDEA TWO
		int numberOfWeeks = findNumWeeks();
		for(int x = 0; x < numberOfWeeks; x++){
			for(int y = 0; y < personList.size(); y++){
				//System.out.println("a");
				//personList.get(y).weeks.set(x, false);
				personList.get(y).weeks.add(false);
			}
		}
		
		int week = 0;
		int currentAvailabilityBool; //This is the index in the availability boolean array list that is currently relevant. 
		boolean innerWhile, outerWhile;
		int currentAvailabilityInt, currentToursGivenInt; 
		currentHighestTourCount = 0;
		if(currBoolReset >= toursInTheWeek){
			currBoolReset = 0;
		}
		for(int i = 0; i < maxGuidesPerTourInt; i++){
			currentAvailabilityBool = currBoolReset; //Have to reset it each time we loop through. 
			//System.out.println("OUTERMOST LOOP IS " + i);
			for(int j = 0; j < dayList.size(); j++){
				week = getWeekFromDay(j);
				if(!dayList.get(j).holiday){ //If the current day isn't a holiday.
					//Go through all the tours on the current day.
					//System.out.println(dayList.get(j).numberOfTours + " tours today");
					for(int k = 0; k < dayList.get(j).numberOfTours; k++){
						//System.out.println("Looking at tour " + k);
						outerWhile = true;
						//currentToursGivenInt = 0;
						currentAvailabilityInt = 0;
						while(outerWhile){
							innerWhile = true;
							//System.out.println("OUTER WHILE - Looking at guides who have given " + currentToursGivenInt + " tours");
							//currentAvailabilityInt = 0;
							currentToursGivenInt = 0;
							while(innerWhile){
								//System.out.println("INNER WHILE - Availability of " + currentAvailabilityInt);
								//numOfAvailableSlots
								//Go through guides and find one with an availability matching the currentAvailabilityInt and a true value 
								//at the currentAvailabilityBool. Set lookingForAGuide to true and add that guide to the current tour's list,
								//as long as all the requirements are met. 
								//Remember to check if currentHighestTourCount needs to be incremented.
								for(int m = 0; m < personList.size(); m++){
									if(personList.get(m).toursThisMonth == currentToursGivenInt && personList.get(m).numOfAvailableSlots == currentAvailabilityInt && personList.get(m).availabilityBooleans.get(currentAvailabilityBool))
									{
										//Then we've found a guide that's got the minimum availability and is available at this time.
										//Check requirements, then add them if they're met. 
										if(personList.get(m).toursThisMonth < maxToursPerGuideInt){
											//Have to check if they've already given a tour that day. 
											if(personList.get(m).checkIfScheduled(j) && personList.get(m).weeks.get(week) == false){ //Make sure they haven't been given a tour on this day before.
												personList.get(m).toursThisMonth++;
												personList.get(m).scheduledDays.add(j); //Add this day to the list. 
												if(personList.get(m).toursThisMonth > currentHighestTourCount){
													currentHighestTourCount = personList.get(m).toursThisMonth;
													//System.out.println("Current highest tour count is " + currentHighestTourCount);
												}
												dayList.get(j).tourList.get(k).addGuide(personList.get(m));
												personList.get(m).tourList.add(dayList.get(j).tourList.get(k));
												innerWhile = false;
												outerWhile = false;
												personList.get(m).weeks.set(week, true);
												m = personList.size();
											}
										}
									}
								}//For m
							    currentToursGivenInt++;
							    if(currentToursGivenInt > currentHighestTourCount){
							    	innerWhile = false;
							    }
							    /*
								currentAvailabilityInt++;
								if(currentAvailabilityInt > toursInTheWeek){
									innerWhile = false;
								}*/
							} //inner while
							currentAvailabilityInt++;
							if(currentAvailabilityInt > toursInTheWeek){
								if(i < minGuidesPerTourInt){
									return false; //Because we never reached the minimum guides per tour number.
								}	
								outerWhile = false; //Otherwise we just skip this tour.
							}
							/*
							currentToursGivenInt++;
							if(currentToursGivenInt > currentHighestTourCount){
								//We couldn't find a tour guide. Do we break somehow?
								if(i <= minGuidesPerTourInt){
									return false; //Because we never reached the minimum guides per tour number.
								}	
								//Otherwise we can just skip this tour. 
								outerWhile = false;
							}*/
						} //outer while
						
						//Here's where we update the counter for which availability bool we're looking at.
						currentAvailabilityBool++;
						if(currentAvailabilityBool >= toursInTheWeek)
							currentAvailabilityBool = 0; //Time to reset it.
					} //For k
				} //If not a holiday.
				else{
					for(int k = 0; k < dayList.get(j).numberOfTours; k++){
						currentAvailabilityBool++;
						if(currentAvailabilityBool >= toursInTheWeek)
							currentAvailabilityBool = 0; //Time to reset it.
					}
				}
			} //For j
		} //For i
		//Check if minToursPerGuide has been met.
		if(!checkMinToursPerGuide(minToursPerGuideInt))
			return false;
		return true; //It worked!
	}
	
	public int getWeekFromDay(int day){
		int daysToTheEndOfTheWeek = 6 - firstDay;
		if(day <= daysToTheEndOfTheWeek){
			return 0;
		}
		int daysSinceEndOfFirstWeek = day - daysToTheEndOfTheWeek - 1;
		return (1 + (daysSinceEndOfFirstWeek/7));	
	}

	
	public int writeToFile(File outputFolder, String fileName, boolean userChoice) throws IOException{
		String folder = outputFolder.getAbsolutePath();		
		String toBeWritten = "";
		
		//Make the array.
		int numWeeks = findNumWeeks();
		int maxTours = getMaxTourDay(sundaySaved, mondaySaved, tuesdaySaved, wednesdaySaved, thursdaySaved, fridaySaved, saturdaySaved);
		int weekTotal = (maxTours * 2) + (maxTours * maxGuidesSaved); //Number of rows in each week. (not counting the date line)
		final int numRows = 1 + numWeeks + (numWeeks * weekTotal);
		String [][] writingArray = new String[numRows][7];
		//System.out.println("numRows is: " + numRows);
		//Initialize every string in the array to "".
		for(int a = 0; a < numRows; a++){
			for(int b = 0; b < 7; b++){
				writingArray[a][b] = "";
			}
		}
		
		//Put in the top line with the day names
		writingArray[0][0] = "Sunday";
		writingArray[0][1] = "Monday";
		writingArray[0][2] = "Tuesday";
		writingArray[0][3] = "Wednesday";
		writingArray[0][4] = "Thursday";
		writingArray[0][5] = "Friday";
		writingArray[0][6] = "Saturday";
		
		//Put in the integer dates.
		int currentDate = 1;
		int currentRow = 1; //That's the index of the first row with dates.
		for(int k = 0; k < numWeeks; k++){
			for(int m = 0; m < 7; m++){
				if(currentDate <= numDays){
					if(m >= firstDay || currentDate != 1){ 
						//Add it to the array.
						writingArray[currentRow][m] = Integer.toString(currentDate);
						currentDate++;
					} //If m >= ...
				} //If currentDate <= numDays
			} //For m
			currentRow += weekTotal + 1; //Why does there have to be a 1? I have no clue. I'm either tired or an idiot. Maybe both, IDK. 
		}
		
		//Add the tour data. 
		currentRow = 1;
		currentDate = 1;
		int numToursOnCurrentDay, labelRow, currTourLabel;
		for(int d = 0; d < numWeeks; d++){
			//System.out.println("Looking at week " + d);
			for(int e = 0; e < 7; e++){
				/*
				System.out.println("     Looking at day " + e);
				System.out.println("     Current date: " + currentDate);
				System.out.println("     Current row: " + currentRow);*/
				if(!writingArray[currentRow][e].equals("")){
					//System.out.println("CURRENT DAY ISN'T EMPTY");
					//At each date, go through all the tours on that date.
					numToursOnCurrentDay = dayList.get(currentDate - 1).numberOfTours;
					if(dayList.get(currentDate - 1).holiday){
						numToursOnCurrentDay = 0;
					}
					labelRow = currentRow + 1;
					for(int f = 0; f < numToursOnCurrentDay; f++){
						//Put the tour name label in the right spot. 
						currTourLabel = f + 1;
						//System.out.println("          Looking at tour " + f);
						//System.out.println("          Label row is " + labelRow);
						if(f != 0){ //Don't skip a line on the first tour.
							labelRow++; //Skip a line.
						}
						writingArray[labelRow][e] = "- Tour " + currTourLabel + " -";
						for(int g = 0; g < maxGuidesSaved; g++){
							//System.out.println("               Looking at guide " + g);
							//Put the people names or integer under the tour name label.
							if(g < dayList.get(currentDate - 1).tourList.get(f).numberOfGuides){
								writingArray[labelRow + g + 1][e] = dayList.get(currentDate - 1).tourList.get(f).guideList.get(g).name;
							}
							else{
								writingArray[labelRow + g + 1][e] = Integer.toString(g + 1);
							}
						}
						//Move the labelRow up...
						labelRow += maxGuidesSaved + 1;
					}
					currentDate++;
				}//If the current date isn't the empty string. 
			}
			currentRow += weekTotal + 1; //I have no idea why this line of code works. 
		}
		
		//Convert to strings in CSV format.
		for(int i = 0; i < numRows; i++){
			toBeWritten += writingArray[i][0] + "," + writingArray[i][1] + "," + writingArray[i][2] + "," + writingArray[i][3] + "," + writingArray[i][4] + "," + writingArray[i][5] + "," + writingArray[i][6] + "\n";
		}
		
		if(userChoice){ //The user wants a list of each guide's tours.
			//First, before we forget, let's add a new line before the stuff is written.
			toBeWritten += "\n";
			
			int maxNumberOfTours = sortPersonList(); //Sorted alphabetically by first name.
			String [][] personArray = new String[personList.size() + 1][maxNumberOfTours + 2];
			
			//Fill with empty strings.
			for(int i = 0; i < personList.size(); i++){
				for(int j = 0; j < (maxNumberOfTours + 2); j++){
					personArray[i][j] = "";
				}
			}
			
			//Fill first line.
			personArray[0][0] = "Name";
			if(maxNumberOfTours > 0){
				int middle = maxNumberOfTours / 2;
				personArray[0][middle] = "Tours";
			}
			personArray[0][maxNumberOfTours + 1] = "Total";
					
			for(int i = 0; i < personList.size(); i++){
				personArray[i + 1][0] = personList.get(i).name;
				for(int j = 0; j < personList.get(i).toursThisMonth; j++){
					personArray[i + 1][j + 1] = personList.get(i).tourList.get(j).month + " " + Integer.toString(personList.get(i).tourList.get(j).day + 1)
							+ ": Tour " + Integer.toString(personList.get(i).tourList.get(j).index + 1);
				}
				personArray[i + 1][maxNumberOfTours + 1] = Integer.toString(personList.get(i).toursThisMonth);
			}
			
			for(int i = 0; i < personList.size(); i++){
				for(int j = 0; j < (maxNumberOfTours + 2); j++){
					toBeWritten += personArray[i][j];
					if(j != (maxNumberOfTours + 2))
						toBeWritten += ",";
				}
				toBeWritten += "\n";
			}
			
		}
		
		//Actually put stuff into the file.
		FileWriter fw = new FileWriter(folder + "/" + fileName + ".csv");
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(toBeWritten);
		bw.close();
		return 0; //Success!
	}
	
	//Alphabetizing the list of guides with insertion sort.
	//Also returns the largest number of tours that any person has.
	//Also sorts each person's tour list.
	public int sortPersonList(){
		Person temp;
		int max = 0;
		
		//Sort the tours for each person.
		for(int i = 0; i < personList.size(); i++){
			personList.get(i).sortTours();
		}
		
		if(personList.size() > 0){
			max = personList.get(0).toursThisMonth;
		}
		
		for(int i = 1; i < personList.size(); i++){
			if(personList.get(i).toursThisMonth > max){
				max = personList.get(i).toursThisMonth;
			}
			
			for(int j = i; j > 0; j--){
				int comparison = personList.get(j).name.toLowerCase().compareTo(personList.get(j-1).name.toLowerCase());
				if(comparison <= 0){
					//Swap.
					temp = personList.get(j);
					personList.set(j, personList.get(j - 1));
					personList.set(j - 1, temp);
				}
			}
		}
		
		return max;
	}
	
	//Returns max number of the 7 parameters.
	public int getMaxTourDay(int a, int b, int c, int d, int e, int f, int g){
		int currentMax = a;
		if(b > currentMax){
			currentMax = b;
		}
		if(c > currentMax){
			currentMax = c;
		}
		if(d > currentMax){
			currentMax = d;
		}
		if(e > currentMax){
			currentMax = e;
		}
		if(f > currentMax){
			currentMax = f;
		}
		if(g > currentMax){
			currentMax = g;
		}
		
		return currentMax;
	}
	
	public int findNumWeeks(){
		int x, y, diff, total;
		if((numDays + firstDay) > 7){
			total = 0;
			x = numDays;
			if(firstDay != 0){
				total = 1;
				x = x - (7 - firstDay);
			}
			y = x % 7; //Find the number of days in the last week.
			diff = x - y;
			total = total + (diff/7);
			if(y != 0)
				total++;
		}
		else
			total = 1;
		
		return total;
	}
	
	public int getCurrentAvailabilityBool(int month, String yearString, int sunday, int monday, int tuesday, int wednesday, int thursday, int friday, int saturday){
		int year = Integer.parseInt(yearString);
		int firstDay = firstDay(month, year);
		
		switch(firstDay){
		case 0: //Sunday
			return 0;
		case 1:
			return sunday;
		case 2:
			return sunday + monday;
		case 3:
			return sunday + monday + tuesday;
		case 4:
			return sunday + monday + tuesday + wednesday;
		case 5:
			return sunday + monday + tuesday + wednesday + thursday;
		case 6:
			return sunday + monday + tuesday + wednesday + thursday + friday;
		}
		
		return 0;
	}
	
	public int makeMonth(int month, String yearString, String holidays, int sunday, int monday, int tuesday, int wednesday, int thursday, int friday, int saturday){
		int year = Integer.parseInt(yearString);
		//Turn the string of holidays into an array list of ints.
		ArrayList<Integer> holidayList = makeHolidayList(holidays);
		//Get the number of days in the month.
		int daysInTheMonth = numberOfDays(month, year);
		numDays = daysInTheMonth;
		//Get the first day of the month (from 0 (Sunday) to 6 (Saturday))
		int currentDayInt = firstDay(month, year);
		firstDay = currentDayInt;
		int numberOfTours;
		
		//Time to make that month structure. 
		for(int i = 0; i < daysInTheMonth; i++){
			switch(currentDayInt){
				case 0:
					numberOfTours = sunday;
					break;
				case 1:
					numberOfTours = monday;
					break;
				case 2:
					numberOfTours = tuesday;
					break;
				case 3:
					numberOfTours = wednesday;
					break;
				case 4:
					numberOfTours = thursday;
					break;
				case 5:
					numberOfTours = friday;
					break;
				case 6:
					numberOfTours = saturday;
					break;
				default:
					numberOfTours = 0;
					break;
			}
			
			Day currentDay = new Day(i, numberOfTours, month);
			if(onTheHolidayList(holidayList, i)){
				currentDay.holiday = true;
			}
			dayList.add(currentDay);
			
			//Increment the day.
			if(currentDayInt == 6){
				currentDayInt = 0; 
			}
			else
				currentDayInt++;
		}
		
		return 0;
	}
	
	public ArrayList<Integer> makeHolidayList(String holidays){
		ArrayList<Integer> holidayList = new ArrayList<Integer>(); 
		
		Scanner holidayScanner = new Scanner(holidays);
		holidayScanner.useDelimiter(",");
		while(holidayScanner.hasNext()){
			String curr = holidayScanner.next();
			holidayList.add(Integer.parseInt(curr) - 1);
		}
		holidayScanner.close();
		return holidayList;
	}

	//True if the day is on the holiday list. False, otherwise. 
	public boolean onTheHolidayList(ArrayList<Integer> list, int day){
		for(int i = 0; i < list.size(); i++){
			if(list.get(i) == day)
				return true;
		}
		
		return false; //By default.
	}
	
	public int numberOfDays(int month, int year){
		switch(month){
		case 0: //January
			return 31;
		case 1: //February
			break;
		case 2: //March
			return 31;
		case 3: //April
			return 30;
		case 4: //May
			return 31;
		case 5: //June
			return 30;
		case 6: //July
			return 31;
		case 7: //August
			return 31;
		case 8: //September
			return 30;
		case 9: //October
			return 31;
		case 10: //November
			return 30;
		case 11: //December
			return 31;
		default: 
			return 0;
		}
		//Now we have to handle the February case. 
		if((year % 4) == 0)
			return 29; //Leap year
		return 28; //Normal year
	}
	
	public int firstDay(int month, int year){
		int firstDay, yearDifference;
		switch(month){
		case 0: //January
			firstDay = 4;
			break;
		case 1: //February
			firstDay = 0;
			break;
		case 2: //March
			firstDay = 0;
			break;
		case 3: //April
			firstDay = 3;
			break;
		case 4: //May
			firstDay = 5;
			break;
		case 5: //June
			firstDay = 1;
			break;
		case 6: //July
			firstDay = 3;
			break;
		case 7: //August
			firstDay = 6;
			break;
		case 8: //September
			firstDay = 2;
			break;
		case 9: //October
			firstDay = 4;
			break;
		case 10: //November
			firstDay = 0;
			break;
		case 11: //December
			firstDay = 2;
			break;
		default: 
			return 0;
		}
		
		yearDifference = year - 2015;
		for(int i = 1; i <= yearDifference; i++){
			//if i = 1, 5, 9, 13, 17 and month is March - December
			//	add 2 to firstDay
			if((((i - 1) % 4) == 0) && (month > 1)){
				firstDay = firstDay + 2;
			}
			else if((((i - 2) % 4) == 0) && (month < 2)){ //Month is January/February and the leap year needs to be taken into account.
				firstDay = firstDay + 2;
			}
			else{
				firstDay++;
			}
			if(firstDay > 6)
				firstDay = firstDay - 7;
		}
		return firstDay;
	}
	
	public void printTours(){
		for(int i = 0; i < dayList.size(); i++){
			if(dayList.get(i).holiday)
				System.out.println("HOLIDAY");
			else{
				System.out.println("Day " + (i + 1));
				for(int j = 0; j < dayList.get(i).numberOfTours; j++){
					System.out.print("Tour " + j + " ");
					for(int k = 0; k < dayList.get(i).tourList.get(j).numberOfGuides; k++){
						System.out.print(dayList.get(i).tourList.get(j).guideList.get(k).name + " ");
					}
					System.out.println("");
				}
			}
		}
		
		//Print how many tours each guide has.
		for(int j = 0; j < personList.size(); j++){
			System.out.println(personList.get(j).name + ": " + personList.get(j).toursThisMonth);
		}
	}
	
	public boolean checkMinToursPerGuide(int min){
		for(int i = 0; i < personList.size(); i++){
			if(personList.get(i).toursThisMonth < min)
				return false;
		}
		return true;
	}
	
	public int makePersonStructures(File inputFile) throws FileNotFoundException{
		//First make an array list of all the individual lines of input.
		ArrayList<String> inputFileLines = new ArrayList<String>();
		Scanner lineScanner = new Scanner(inputFile);
		while(lineScanner.hasNextLine()){
			inputFileLines.add(lineScanner.nextLine());
		}
		lineScanner.close();
		
		//Now split those up by commas. 
		for(int i = 0; i < inputFileLines.size(); i++){
			Scanner currentLineScanner = new Scanner(inputFileLines.get(i));
			currentLineScanner.useDelimiter(",");
			String personName = currentLineScanner.next();
			Person toBeAdded = new Person();
			toBeAdded.name = personName; //Put in the name.
			//Handle everything past the name:
			int numberOfBools = 0; //For making sure the input is formatted correctly.
			int numberOfTrues = 0; //For tracking how many available time slots there are for this person.
			while(currentLineScanner.hasNext()){
				String currBool = currentLineScanner.next();
				int yesNoResult = yesOrNo(currBool);
				if(yesNoResult == 1){
					toBeAdded.availabilityBooleans.add(true);
					numberOfTrues++;
				}
				else if(yesNoResult == 0){
					toBeAdded.availabilityBooleans.add(false);
				}
				else{
					currentLineScanner.close();
					return -1; //Since one of the answers wasn't yes or no or whatever.
				}
				numberOfBools++;		
			}
			if(numberOfBools != toursInTheWeek){
				currentLineScanner.close();
				return -2; //Since this person didn't have the right number of answers for availability. 
			}
			//Update the number of available time slots for this person. 
			toBeAdded.numOfAvailableSlots = numberOfTrues;
			personList.add(toBeAdded);
			currentLineScanner.close();
		}
		return 0;
	}

	
	//1 is a yes, 0 is a no, -1 is a something that shouldn't be there - an error.
	public int yesOrNo(String input){
		input = input.replaceAll("\\s",""); //Remove spaces from a string. Taken from stack overflow. 
		if(input.equals("1"))
			return 1;
		else if(input.equals("0"))
			return 0;
		else if(input.toLowerCase().equals("yes") || input.toLowerCase().equals("y"))
			return 1;
		else if(input.toLowerCase().equals("no") || input.toLowerCase().equals("n"))
			return 0;
		return -1; //If it wasn't one of these, it's an error. 
	}
	
	public int stringToInt(String input){
		if(input.isEmpty())
			return 0;
		else
			return Integer.parseInt(input);
	}
}
