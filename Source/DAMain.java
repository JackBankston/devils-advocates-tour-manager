//This will hold most of the GUI stuff, or at least that's how I'm planning it.
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class DAMain {
	private JPanel panel;
	private Scanner x;
	private File file, folder;
	private boolean fileSelected = false;
	private boolean folderSelected = false;
	private Color buttonColor = new Color(224,224,224);
	private Color backgroundColor = new Color(253,253,253); //Background color basically everywhere. 
	//private Dimension frameDimensions;
	
	//For backing up purposes:
	private int monthBack = 0;
	private String yearBack, holidaysBack;
	private int sundayBack, mondayBack, tuesdayBack, wednesdayBack, thursdayBack, fridayBack, saturdayBack;
	private String minGuidesPerTourBack, maxGuidesPerTourBack, minToursPerGuideBack, maxToursPerGuideBack;
	private String outputFileNameBack;
	private boolean tourListBack = true;
	
	public DAMain() throws FileNotFoundException{
		intro();
	}
	
	public void about(JFrame oldFrame) throws IOException{
		//All this code is identical to the code from the tutorial(...) method
		JPanel menuPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 12));
		JButton mainMenuButton = new JButton("Main Menu");
		mainMenuButton.setBackground(buttonColor);
		menuPanel.add(mainMenuButton);
		menuPanel.setBackground(backgroundColor);
		
		JEditorPane editorPane = new JEditorPane(); //First block of text.
		editorPane.setSize(oldFrame.getBounds().getSize());
		editorPane.setText(
				  "\tThis program was created for Devils' Advocates at Arizona State University's Tempe campus,"
				+ "\n\tbeginning with the 2015-2016 academic year. It was written by me, Jack Bankston, with the"
				+ "\n\thelp of Amy Lundy (2015-2016 President). It is definitely not a perfect piece of software, but"
				+ "\n\tit should reduce the process of scheduling tours from being \"the worst thing in the world\""
				+ "\n\tto \"pretty terrible\". If there are any problems with the program, you are welcome to contact"
				+ "\n\tme at JackCBankston@gmail.com or Amy at AmyNicoleLundy@gmail.com."
				+ "\n\t - Jack Bankston, 8/26/2015");
		editorPane.setBackground(backgroundColor);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
		mainPanel.add(menuPanel);
		mainPanel.add(editorPane);
		
		//oldFrame.setContentPane(overViewPanel);
		oldFrame.setSize(800, 500);
		oldFrame.setContentPane(mainPanel);
		oldFrame.invalidate();
		oldFrame.validate();
		
		ActionListener menuListener = new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				mainMenu(oldFrame);
			}
		};
		mainMenuButton.addActionListener(menuListener);
	}
	
	public void tutorial(JFrame oldFrame) throws IOException{
		//JPanel overViewPanel = new JPanel(new GridBagLayout());
		JPanel overViewPanel = new JPanel();
		overViewPanel.setLayout(new BoxLayout(overViewPanel, BoxLayout.PAGE_AXIS));
		overViewPanel.setBackground(backgroundColor);
				
		JPanel menuPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 12));
		JButton mainMenuButton = new JButton("Main Menu");
		mainMenuButton.setBackground(buttonColor);
		menuPanel.add(mainMenuButton);
		menuPanel.setBackground(backgroundColor);
		
		JEditorPane editorPane = new JEditorPane(); //First block of text.
		editorPane.setBackground(backgroundColor);
		editorPane.setSize(oldFrame.getBounds().getSize());
		editorPane.setText("Scheduling tours with this program is a two step process."
				+ "\n     1. Create a spreadsheet with all of the guides' availability information."
				+ "\n     2. Specify the requirements for the schedule by answering a small set of questions."
				+ "\n\nStep 1: Spreadsheet Design"
				+ "\n- The only information that goes on the spreadsheet is the names of the guides (first column) and"
				+ "\n whether or not they are available for each of the tour times (colums 2,3,4,...)."
				+ "\n- The day/time of each tour slot should not be included in the spreadsheet. You will specify that"
				+ "\n in the \"answering a small set of questions\" section."
				+ "\n- The spreadsheet has to be in the form of a CSV file. This is very important, but luckily for you,"
				+ "\n it's also super easy. Excel and Google Sheets (or whatever program you're using) allow you to save"
				+ "\n your spreadsheet as a CSV file. It's usually pretty straightforward, but even if it's not, a quick"
				+ "\n Google search will help you figure it out."
				+ "\n- Here's an example of a correctly formatted spreadsheet where there are 20 guides and each week has"
				+ "\n 10 tours: (Note that it has not yet been converted to a CSV file.)"
				+ "\n");
		BufferedImage myPicture = ImageIO.read(getClass().getResource("/images/SampleDataPicture.png"));
		ImageIcon imgIcon = new ImageIcon(myPicture);
		Image image = imgIcon.getImage();
		Image scaledImage = image.getScaledInstance(oldFrame.getBounds().getSize().width/*250Width*/, 400/*Height*/,  java.awt.Image.SCALE_SMOOTH);
		imgIcon = new ImageIcon(scaledImage);
		JLabel picLabel = new JLabel(imgIcon);
		
		JPanel picPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 12));
		picPanel.setBackground(backgroundColor);
		picPanel.add(picLabel);
		
		JEditorPane editorPane2 = new JEditorPane();
		editorPane2.setBackground(backgroundColor);
		editorPane2.setSize(oldFrame.getBounds().getSize());
		editorPane2.setText("\n- After the spreadsheet is saved to a CSV file, opening it in a text editor like Notepad"
				+ "\n or TextEdit will bring up something like this: (You don't actually need to open it in a text editor,"
				+ "\n but I thought you might be curious about what a CSV file actually is.)"
				+ "\n");
		
		BufferedImage myPicture2 = ImageIO.read(getClass().getResource("/images/TutorialCSV.png"));
		ImageIcon imgIcon2 = new ImageIcon(myPicture2);
		Image image2 = imgIcon2.getImage();
		Image scaledImage2 = image2.getScaledInstance((int)(oldFrame.getBounds().getSize().width * .75)/*250Width*/, 350/*Height*/,  java.awt.Image.SCALE_SMOOTH);
		imgIcon2 = new ImageIcon(scaledImage2);
		JLabel picLabel2 = new JLabel(imgIcon2);
		JPanel picPanel2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 12));
		picPanel2.setBackground(backgroundColor);
		picPanel2.add(picLabel2);
		
		JEditorPane editorPane3 = new JEditorPane();
		editorPane3.setBackground(backgroundColor);
		editorPane3.setSize(oldFrame.getBounds().getSize());
		editorPane3.setText("\nStep 2: Specifying Requirements"
				+ "\nThis step involves answering simple questions which will help the scheduling program create"
				+ "\n a perfect tour calendar."
				+ "\n\n1. Question Screen 1 will ask you to specify the month/year and all of the holidays in the month."
				+ "\n Here, \"holidays\" are any days without tours which would, according to your spreadsheet, normally have tours."
				+ "\n\n2. Question Screen 2 asks you to input how many tours there are on each day in a normal week (that is to say,"
				+ "\n one without holidays). The total number of tours in the week must be equal to the number of columns of availability"
				+ "\n answers in your spreadsheet."
				+ "\n\n3. Question Screen 3 asks you four questions. If any of them are not relevant to the requirements for the tour calendar,"
				+ "\n they do not need to be answered. However, don't be surprised if leaving multiple fields blank yields strange results."
				+ "\n The program does need just a bit of guidance after all."
				+ "\n\n4. Question Screen 4 asks you to choose the CSV file you created earlier. This should be straightforward, assuming that"
				+ "\n you didn't save your CSV file inside of a gigantic chain of folders."
				+ "\n\nAfter clicking the Make a Schedule button, you will end up on either an error screen or an output file screen."
				+ "\n     - Error Screen: If the program tells you that neither scheduling algorithm was successful, you should try double checking"
				+ "\n      your input on the questions. If the problem persists, you may have to modify your requirements. Oftentimes, allowing"
				+ "\n      guides to be scheduled for more tours will allow for a valid schedule to be made. For other types of error messages,"
				+ "\n      review your spreadsheet and input to make sure everything makes sense. If that fails, try looking at the actual CSV"
				+ "\n      file. In some cases, empty cells from your spreadsheet might be included in that by mistake. Things like that can cause"
				+ "\n      an error message."
				+ "\n     - Output File Screen: Success! You have created a schedule, so you need to name the file you want it saved to and"
				+ "\n      choose the location to which you want it stored. You also have the option to include a list of all the tours for each guide,"
				+ "\n      if you think that will be useful."
				+ "\n\nAfter all of this (which hopefully wasn't too much work), you will have a CSV file containing a tour calendar. You can open"
				+ "\nthis file in your program of choice (Excel, Google Sheets, etc.)."
				+ "\n"
				+ "\n - Jack Bankston (JackCBankston@gmail.com), 8/26/2015" );
		
		//overViewPanel.add(menuPanel);		
		overViewPanel.add(editorPane);
		overViewPanel.add(picPanel);
		overViewPanel.add(editorPane2);
		overViewPanel.add(picPanel2);
		overViewPanel.add(editorPane3);

		//overViewPanel.add(myPicture);

		JScrollPane scroller = new JScrollPane(overViewPanel); //We want to make the whole thing scroll!
				
		//This chunk of code will make sure the scroll bar defaults to the top.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			   public void run() { 
			       scroller.getVerticalScrollBar().setValue(0);
			   }
		});
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
		mainPanel.add(menuPanel);
		mainPanel.add(scroller);
		
		//oldFrame.setContentPane(overViewPanel);
		oldFrame.setSize(900, 500);
		oldFrame.setContentPane(mainPanel);
		oldFrame.invalidate();
		oldFrame.validate();
		
		ActionListener menuListener = new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				mainMenu(oldFrame);
			}
		};
		mainMenuButton.addActionListener(menuListener);
	}
	
	public void monthAndHolidays(JFrame oldFrame){
		panel = new JPanel();
		JPanel selectPanel = new JPanel(new GridBagLayout());
		JPanel holidayPanel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		JLabel monthLabel, yearLabel, topLabel, bottomLabel, holidayExplanationLabel;
		monthLabel = new JLabel("Month");
		monthLabel.setFont(new Font("Dialog", Font.BOLD, 12));
		yearLabel = new JLabel("Year");
		yearLabel.setFont(new Font("Dialog", Font.BOLD, 12));
		topLabel = new JLabel("When are these tours happening?");
		topLabel.setFont(new Font("Dialog", Font.BOLD, 25));
		bottomLabel = new JLabel("Which days are holidays?");
		bottomLabel.setFont(new Font("Dialog", Font.BOLD, 25));
		holidayExplanationLabel = new JLabel("Enter the dates separated by commas (example: 2,5,16) or leave the field blank if there are no holidays.");
		holidayExplanationLabel.setFont(new Font("Dialog", Font.BOLD, 12));
		
		JButton back, next;
		
		//Customize those buttons!
		back = new JButton("Back");
		back.setFont(new Font("Dialog", Font.BOLD, 12));
		//back.setBackground(new Color(238,233,233));
		//back.setBackground(new Color(224,224,224)); //Light gray
		back.setBackground(buttonColor);
		next = new JButton("Next");
		next.setFont(new Font("Dialog", Font.BOLD, 12));
		//next.setBackground(new Color(238,233,233));
		//next.setBackground(new Color(224,224,224));
		next.setBackground(buttonColor);
		JPanel backButtonPanel =new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 12));
		backButtonPanel.add(back);
		JPanel nextButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 12));
		nextButtonPanel.add(next);
		
		
		String[] months = {"January","February","March","April","May","June","July","August","September","October","November","December"};
		JComboBox<String> monthSelect = new JComboBox<String>(months);
		monthSelect.setSelectedIndex(monthBack);
		//monthSelect.setBackground(new Color(238,233,233));
		monthSelect.setBackground(backgroundColor);
		//monthSelect.setBackground(new Color(224,224,224)); //Light gray
		
		JTextField yearSelect = new JTextField(10);
		yearSelect.setText(yearBack);
		JTextField holidaySelect = new JTextField(10);
		holidaySelect.setText(holidaysBack);
		
		//Month and year section
		c.insets = new Insets(5,5,5,5);
		c.gridx = 0;
		c.gridy = 0;
		selectPanel.add(monthLabel, c);
		c.gridx = 1;
		c.gridy = 0;
		selectPanel.add(yearLabel, c);
		c.gridx = 0;
		c.gridy = 1;
		selectPanel.add(monthSelect, c);
		c.gridx = 1;
		c.gridy = 1;
		selectPanel.add(yearSelect, c);
		
		//Holiday section
		c.insets = new Insets(10,10,10,10);
		c.gridx = 0;
		c.gridy = 0;
		holidayPanel.add(bottomLabel, c);
		c.gridx = 0;
		c.gridy = 1;
		holidayPanel.add(holidayExplanationLabel, c);
		c.gridx = 0;
		c.gridy = 2;
		holidayPanel.add(holidaySelect, c);
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		backButtonPanel.setBackground(backgroundColor);
		panel.add(backButtonPanel);
		//topLabel.setBackground(backgroundColor);
		panel.add(topLabel);
		topLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		selectPanel.setBackground(backgroundColor);
		panel.add(selectPanel);
		holidayPanel.setBackground(backgroundColor);
		panel.add(holidayPanel);
		nextButtonPanel.setBackground(backgroundColor);
		panel.add(nextButtonPanel);
		panel.setBackground(backgroundColor);
		
		/*
		frame.add(panel);
		frame.setVisible(true);
		frame.setSize(700, 475);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);*/
		oldFrame.setContentPane(panel);
		oldFrame.invalidate();
		oldFrame.validate();
		
		ActionListener backListener = new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				monthBack = monthSelect.getSelectedIndex();
				yearBack = yearSelect.getText();
				holidaysBack = holidaySelect.getText();
				mainMenu(oldFrame);
			}
		};
		
		ActionListener nextListener = new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				//Get the values of those inputs!
				monthBack = monthSelect.getSelectedIndex();
				yearBack = yearSelect.getText();
				if(yearBack.isEmpty()){
					//Display error message
					JOptionPane.showConfirmDialog(null, "The \"Year\" field cannot be left blank.", "Invalid Input", JOptionPane.DEFAULT_OPTION);
				}
				else if(!isANumber(yearBack)){
					JOptionPane.showConfirmDialog(null, "The \"Year\" must be a number greater than or equal to 2015.", "Invalid Input", JOptionPane.DEFAULT_OPTION);
				}
				else if(Integer.parseInt(yearBack) < 2015){
					JOptionPane.showConfirmDialog(null, "The \"Year\" cannot be before 2015. You are not in the past.", "Invalid Input", JOptionPane.DEFAULT_OPTION);
				}
				else{
					holidaysBack = holidaySelect.getText();
					if(!checkHolidays(holidaysBack)){ //Input for holidays was invalid
						JOptionPane.showConfirmDialog(null, "The input for the holidays field is formatted incorrectly.", "Invalid Input", JOptionPane.DEFAULT_OPTION);
					}
					else{
						dayByDay(oldFrame);
					}
				}
			}	
		};
		next.addActionListener(nextListener);
		back.addActionListener(backListener);
	}//monthAndHolidays
	
	public void chooseOutputFolder(JFrame oldFrame, Schedule schedule){
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		//panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		panel.setBackground(backgroundColor);
		GridBagConstraints c = new GridBagConstraints();

		JLabel selectOutputFolderLabel = new JLabel("Choose your output folder:"); 
		selectOutputFolderLabel.setFont(new Font("Dialog", Font.BOLD, 16));
		JLabel selectedOutputLabel;
		
		if(!folderSelected){
			//This won't be visible.
			selectedOutputLabel = new JLabel("Placeholder");
			selectedOutputLabel.setFont(new Font("Dialog", Font.BOLD, 14));
			selectedOutputLabel.setForeground(backgroundColor);
		}
		else{
			selectedOutputLabel = new JLabel(folder.getAbsolutePath());
			selectedOutputLabel.setFont(new Font("Dialog", Font.BOLD, 14));
			selectedOutputLabel.setForeground(Color.BLACK);
		}
		
		JButton back, next;
		//Customize those buttons!
		back = new JButton("Back");
		back.setFont(new Font("Dialog", Font.BOLD, 12));
		back.setBackground(buttonColor); //Light gray
		next = new JButton("Create File");
		next.setFont(new Font("Dialog", Font.BOLD, 12));
		next.setBackground(buttonColor);
		JPanel backButtonPanel =new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 12));
		backButtonPanel.add(back);
		JPanel nextButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 12));
		nextButtonPanel.add(next); 
		nextButtonPanel.setBackground(backgroundColor);
		backButtonPanel.setBackground(backgroundColor);
		
		JButton selectOutputFolderButton;
		selectOutputFolderButton = new JButton("Browse");
		selectOutputFolderButton.setFont(new Font("Dialog", Font.BOLD, 12));
		selectOutputFolderButton.setBackground(buttonColor); //Light gray
		
		
		JTextField outputFileField = new JTextField(10);
		outputFileField.setHorizontalAlignment(JTextField.RIGHT);//So text is on the right side.
		outputFileField.setText(outputFileNameBack);
		JLabel outputFileLabel = new JLabel(".csv");
		outputFileLabel.setFont(new Font("Dialog", Font.BOLD, 16));
		JLabel topOutputFileLabel = new JLabel("Name your output file:");
		topOutputFileLabel.setFont(new Font("Dialog", Font.BOLD, 16));
		topOutputFileLabel.setBackground(backgroundColor);
		
		JPanel outputFilePanel = new JPanel(new FlowLayout());
		outputFilePanel.add(outputFileField);
		outputFilePanel.add(outputFileLabel);
		outputFilePanel.setBackground(backgroundColor);
		
		JPanel selectOutputFolderPanel = new JPanel(new GridBagLayout());
		c.insets = new Insets(10,10,10,10);
		c.gridx = 0;
		c.gridy = 0;
		selectOutputFolderPanel.add(selectOutputFolderLabel, c);
		c.gridx = 0;
		c.gridy = 1;
		selectOutputFolderPanel.add(selectOutputFolderButton, c);
		c.gridx = 0;
		c.gridy = 2;
		selectOutputFolderPanel.add(selectedOutputLabel, c);
		selectOutputFolderPanel.setBackground(backgroundColor);
		
		//Two radio buttons and labels.
		JLabel userListLabel = new JLabel("Along with the tour calendar, would you like a list of tours for each guide?");
		userListLabel.setFont(new Font("Dialog", Font.BOLD, 16));
		
		JLabel yesLabel = new JLabel("Yes");
		yesLabel.setFont(new Font("Dialog", Font.BOLD, 14));
		JLabel noLabel = new JLabel("No");
		noLabel.setFont(new Font("Dialog", Font.BOLD, 14));
		
		ButtonGroup userChoice = new ButtonGroup();
		JRadioButton yesButton = new JRadioButton();
		JRadioButton noButton = new JRadioButton();
		if(tourListBack){	
			yesButton.setSelected(true);
			noButton.setSelected(false);
		}
		else{
			yesButton.setSelected(false);
			noButton.setSelected(true);
		}
		yesButton.setBackground(backgroundColor);
		noButton.setBackground(backgroundColor);
		userChoice.add(yesButton);
		userChoice.add(noButton);
		
		JPanel yesPanel = new JPanel(new FlowLayout());
		yesPanel.add(yesButton);
		yesPanel.add(yesLabel);
		JPanel noPanel = new JPanel(new FlowLayout());
		noPanel.add(noButton);
		noPanel.add(noLabel);
		yesPanel.setBackground(backgroundColor);
		noPanel.setBackground(backgroundColor);
		
		JPanel buttonPanel = new JPanel(new GridBagLayout());
		c.gridx = 0;
		c.gridy = 0;
		buttonPanel.add(yesPanel, c);
		c.gridx = 1;
		buttonPanel.add(noPanel, c);
		buttonPanel.setBackground(backgroundColor);
		
		JPanel bigPanel = new JPanel(new GridBagLayout());
		c.gridx = 0;
		c.gridy = 1;
		bigPanel.add(topOutputFileLabel, c);
		c.gridy = 2;
		bigPanel.add(outputFilePanel, c);
		c.gridy = 3;
		bigPanel.add(selectOutputFolderPanel, c);
		c.gridy = 4;
		bigPanel.add(userListLabel, c);
		c.insets = new Insets(0,0,0,0); //To be honest, I've got no clue if this line does anything.
		c.gridy = 5;
		bigPanel.add(buttonPanel, c);
		bigPanel.setBackground(backgroundColor);
		
		panel.add(backButtonPanel);
		panel.add(bigPanel);
		panel.add(nextButtonPanel);
		bigPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		oldFrame.setContentPane(panel);
		oldFrame.invalidate();
		oldFrame.validate();
		
		ActionListener selectFolderListener = new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				final JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); //Only folders
				int returnVal = fc.showOpenDialog(null);
	            if (returnVal == JFileChooser.APPROVE_OPTION) {//User hit open.
	                folder = fc.getSelectedFile();
	                //Let's test whether that worked or not.
	                System.out.println(folder.getAbsolutePath());
	                //Now let's update the panel.
	                selectedOutputLabel.setText(folder.getAbsolutePath());
	                selectedOutputLabel.setForeground(Color.BLACK);
	                folderSelected = true;
	                oldFrame.setContentPane(panel);
	        		oldFrame.invalidate();
	        		oldFrame.validate();
	            } 
			}
		};
		
		ActionListener backListener = new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				outputFileNameBack = outputFileField.getText();
				if(yesButton.isSelected()){
					tourListBack = true;
				}
				else{
					tourListBack = false;
				}
				chooseFiles(oldFrame);
			}
		};		
		
		ActionListener nextListener = new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				String fileName = outputFileField.getText();
				if(yesButton.isSelected()){
					tourListBack = true;
				}
				else{
					tourListBack = false;
				}
				//Check if there's an output folder selected:
				if(!folderSelected){
					JOptionPane.showConfirmDialog(null, "You must select an output folder.", "Invalid Input", JOptionPane.DEFAULT_OPTION);
				}
				else if(fileName.isEmpty()){
					JOptionPane.showConfirmDialog(null, "You must name your output file.", "Invalid Input", JOptionPane.DEFAULT_OPTION);
				}
				else if(fileExists(folder, fileName)){
					//Have to tell the user that they chose a file that already exists. Make sure they're cool with this.
					String message = "There is already a file named " + fileName + ".csv in the folder you chose.\n"
							+ "Would you like to replace the existing file or change your desired name/folder?";

					 //Custom button text
					Object[] options = {"Replace Existing File",
                    	"Change Name/Folder"};
					
					int choice = JOptionPane.showOptionDialog(oldFrame, message, "File Already Exists", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
					if(choice == 0){//The user wants to replace the file.
						try {
							outputFileNameBack = fileName;
							schedule.writeToFile(folder, fileName, tourListBack);
							output(oldFrame, 0);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							output(oldFrame, -8);
							e.printStackTrace();
						}
					}
				}
				else{ //Everything was good with the input.
					try {
						outputFileNameBack = fileName;
						schedule.writeToFile(folder, fileName, tourListBack);
						output(oldFrame, 0);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
		
		selectOutputFolderButton.addActionListener(selectFolderListener);
		back.addActionListener(backListener);
		next.addActionListener(nextListener);
		
	} //chooseOutputFolder()
	
	//Returns true if the file exists in the folder. False otherwise.
	public boolean fileExists(File folder, String name){
		String fullFileName = folder.getAbsolutePath() + "/" + name + ".csv";
		
		File fullFile = new File(fullFileName);
		
		if(fullFile.exists()){
			return true; //Because we found a file with this name in this folder.
		}
		return false;
	}
	
	public void otherRequirements(JFrame oldFrame){		
		panel = new JPanel();
		JPanel maxToursPerGuidePanel = new JPanel(new GridBagLayout());
		JPanel minToursPerGuidePanel = new JPanel(new GridBagLayout());
		JPanel maxGuidesPerTourPanel = new JPanel(new GridBagLayout());
		JPanel minGuidesPerTourPanel = new JPanel(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		
		JLabel maxToursPerGuideLabel, minToursPerGuideLabel, maxGuidesPerTourLabel, minGuidesPerTourLabel, noteLabel;
		maxToursPerGuideLabel = new JLabel("What is the maximum number of tours that a guide can be scheduled to give?");
		maxToursPerGuideLabel.setFont(new Font("Dialog", Font.BOLD, 15));
		minToursPerGuideLabel = new JLabel("How many regular tours per month is a guide required to give?");
		minToursPerGuideLabel.setFont(new Font("Dialog", Font.BOLD, 15));
		maxGuidesPerTourLabel = new JLabel("What is the maximum number of guides that can be scheduled for each tour?");
		maxGuidesPerTourLabel.setFont(new Font("Dialog", Font.BOLD, 15));
		minGuidesPerTourLabel = new JLabel("What is the minimum number of guides needed per tour time?");
		minGuidesPerTourLabel.setFont(new Font("Dialog", Font.BOLD, 15));
		noteLabel = new JLabel("Note: any of these fields can be left blank, if they are not relevant requirements.");
		noteLabel.setFont(new Font("Dialog", Font.BOLD, 12));
		
		JTextField maxToursPerGuideSelect = new JTextField(10);
		JTextField minToursPerGuideSelect = new JTextField(10);
		JTextField maxGuidesPerTourSelect = new JTextField(10);
		JTextField minGuidesPerTourSelect = new JTextField(10);
		
		maxToursPerGuideSelect.setText(maxToursPerGuideBack);
		minToursPerGuideSelect.setText(minToursPerGuideBack);
		maxGuidesPerTourSelect.setText(maxGuidesPerTourBack);
		minGuidesPerTourSelect.setText(minGuidesPerTourBack);
		
		JButton back, next;
		
		//Customize those buttons!
		back = new JButton("Back");
		back.setFont(new Font("Dialog", Font.BOLD, 12));
		back.setBackground(buttonColor); //Light gray
		next = new JButton("Next");
		next.setFont(new Font("Dialog", Font.BOLD, 12));
		next.setBackground(buttonColor);
		JPanel backButtonPanel =new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 12));
		backButtonPanel.add(back);
		JPanel nextButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 12));
		nextButtonPanel.add(next);
		
		//maxToursPerGuidePanel
		c.insets = new Insets(10,10,10,10);
		c.gridx = 0;
		c.gridy = 0;
		maxToursPerGuidePanel.add(maxToursPerGuideLabel, c);
		c.gridx = 0;
		c.gridy = 1;
		maxToursPerGuidePanel.add(maxToursPerGuideSelect, c);
		
		//minToursPerGuidePanel
		c.gridx = 0;
		c.gridy = 0;
		minToursPerGuidePanel.add(minToursPerGuideLabel, c);
		c.gridx = 0;
		c.gridy = 1;
		minToursPerGuidePanel.add(minToursPerGuideSelect, c);
		
		//maxGuidesPerTourPanel
		c.gridx = 0;
		c.gridy = 0;
		maxGuidesPerTourPanel.add(maxGuidesPerTourLabel, c);
		c.gridx = 0;
		c.gridy = 1;
		maxGuidesPerTourPanel.add(maxGuidesPerTourSelect, c);
		c.gridx = 0;
		c.gridy = 2;
		maxGuidesPerTourPanel.add(noteLabel, c);
		
		//minGuidesPerTourPanel
		c.gridx = 0;
		c.gridy = 0;
		minGuidesPerTourPanel.add(minGuidesPerTourLabel, c);
		c.gridx = 0;
		c.gridy = 1;
		minGuidesPerTourPanel.add(minGuidesPerTourSelect, c);
		
		//Add things to the main panel.
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		backButtonPanel.setBackground(backgroundColor);
		panel.add(backButtonPanel);
		minToursPerGuidePanel.setBackground(backgroundColor);
		panel.add(minToursPerGuidePanel);
		maxToursPerGuidePanel.setBackground(backgroundColor);
		panel.add(maxToursPerGuidePanel);
		minGuidesPerTourPanel.setBackground(backgroundColor);
		panel.add(minGuidesPerTourPanel);
		maxGuidesPerTourPanel.setBackground(backgroundColor);
		panel.add(maxGuidesPerTourPanel);
		nextButtonPanel.setBackground(backgroundColor);
		panel.add(nextButtonPanel);
		
		/*
		frame.add(panel);
		frame.setVisible(true);
		frame.setSize(700, 475);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);*/
		oldFrame.setContentPane(panel);
		oldFrame.invalidate();
		oldFrame.validate();
		
		ActionListener backListener = new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				minToursPerGuideBack = minToursPerGuideSelect.getText();
				maxToursPerGuideBack = maxToursPerGuideSelect.getText();
				minGuidesPerTourBack = minGuidesPerTourSelect.getText();
				maxGuidesPerTourBack = maxGuidesPerTourSelect.getText();
				
				dayByDay(oldFrame);
			}
		};
		
		ActionListener nextListener = new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				minToursPerGuideBack = minToursPerGuideSelect.getText();
				maxToursPerGuideBack = maxToursPerGuideSelect.getText();
				minGuidesPerTourBack = minGuidesPerTourSelect.getText();
				maxGuidesPerTourBack = maxGuidesPerTourSelect.getText();
				//Check to make sure that those are all valid inputs.
				if(!isANumber(minToursPerGuideBack)){
					JOptionPane.showConfirmDialog(null, "<html>The minimum number of tours required for each guide must be a postive number (or zero).<br> If there is no such requirement, the field can be left blank.</html>", "Invalid Input", JOptionPane.DEFAULT_OPTION);
				}
				else if(!isANumber(maxToursPerGuideBack)){
					JOptionPane.showConfirmDialog(null, "<html>The maximum number of tours that a guide can be scheduled to give must be a positive number (or zero). <br>If there is no such requirement, the field can be left blank.</html>", "Invalid Input", JOptionPane.DEFAULT_OPTION);
				}
				else if(!isANumber(minGuidesPerTourBack)){
					JOptionPane.showConfirmDialog(null, "<html>The minimum number of guides needed per tour time must be a positive number (or zero). <br>If there is no such requirement, the field can be left blank.</html>", "Invalid Input", JOptionPane.DEFAULT_OPTION);
				}
				else if(!isANumber(maxGuidesPerTourBack)){
					JOptionPane.showConfirmDialog(null, "<html>The maximum number of guides that can be scheduled for each tour must be a positive number (or zero).<br> If there is no such requirement, the field can be left blank.</html>", "Invalid Input", JOptionPane.DEFAULT_OPTION);
				}
				else if(!compareMinAndMax(minToursPerGuideBack, maxToursPerGuideBack)){
					JOptionPane.showConfirmDialog(null, "<html> The maximum number of tours per guide must be greater than or equal <br> to the minimum number of tours per guide.<br> Alternatively, either field can be left blank, if they are not both required.</html>", "Invalid Input", JOptionPane.DEFAULT_OPTION);
				}
				else if(!compareMinAndMax(minGuidesPerTourBack, maxGuidesPerTourBack)){
					JOptionPane.showConfirmDialog(null, "<html> The maximum number of guides per tour must be greater than or equal <br> to the minimum number of guides per tour.<br> Alternatively, either field can be left blank, if they are not both required.</html>", "Invalid Input", JOptionPane.DEFAULT_OPTION);
				}
				else{
					chooseFiles(oldFrame);
				}
			}	
		};
		next.addActionListener(nextListener);
		back.addActionListener(backListener);
	}
	
	public void chooseFiles(JFrame oldFrame){
		panel = new JPanel();
		
		JPanel selectInputFilePanel = new JPanel(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		
		JLabel selectInputFileLabel, selectedInputLabel;
		selectInputFileLabel = new JLabel("Choose your .csv input file:");
		selectInputFileLabel.setFont(new Font("Dialog", Font.BOLD, 25));
		if(!fileSelected){
			selectedInputLabel = new JLabel("Placeholder");
			selectedInputLabel.setFont(new Font("Dialog", Font.BOLD, 14));
			selectedInputLabel.setForeground(backgroundColor);
		}
		else{
			selectedInputLabel = new JLabel(file.getName());
			selectedInputLabel.setFont(new Font("Dialog", Font.BOLD, 14));
			selectedInputLabel.setForeground(Color.BLACK);
		}

		JButton back, next, selectInputFileButton;
		
		//Customize those buttons!
		back = new JButton("Back");
		back.setFont(new Font("Dialog", Font.BOLD, 12));
		back.setBackground(buttonColor); //Light gray
		//next = new JButton("Next");
		next = new JButton("Make a Schedule");
		next.setFont(new Font("Dialog", Font.BOLD, 12));
		next.setBackground(buttonColor);
		JPanel backButtonPanel =new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 12));
		backButtonPanel.add(back);
		JPanel nextButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 12));
		nextButtonPanel.add(next);
		
		//Customize input and output buttons.
		selectInputFileButton = new JButton("Browse");
		selectInputFileButton.setFont(new Font("Dialog", Font.BOLD, 12));
		selectInputFileButton.setBackground(buttonColor); //Light gray

		c.insets = new Insets(10,10,10,10);
		c.gridx = 0;
		c.gridy = 0;
		selectInputFilePanel.add(selectInputFileLabel, c);
		c.gridx = 0;
		c.gridy = 1;
		selectInputFilePanel.add(selectInputFileButton, c);
		c.gridx = 0;
		c.gridy = 2;
		selectInputFilePanel.add(selectedInputLabel, c);
		
		//Add things to the main panel.
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		backButtonPanel.setBackground(backgroundColor);
		panel.add(backButtonPanel);
		selectInputFilePanel.setBackground(backgroundColor);
		panel.add(selectInputFilePanel);
		selectInputFilePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		nextButtonPanel.setBackground(backgroundColor);
		panel.add(nextButtonPanel);
		
		oldFrame.setContentPane(panel);
		oldFrame.invalidate();
		oldFrame.validate();
		
		ActionListener selectFileListener = new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				//Time to browse for a file!
				final JFileChooser fc = new JFileChooser();
				fc.setFileFilter(new FileNameExtensionFilter("CSV Files", "csv"));
				int returnVal = fc.showOpenDialog(null);
				//See what the user chose
	            if (returnVal == JFileChooser.APPROVE_OPTION) {//User hit open.
	                file = fc.getSelectedFile();
	                //if(csvFile()){
	                //Let's test whether that worked or not.
	                System.out.println(file.getName());
	                //Now let's update the panel.
	                selectedInputLabel.setText(file.getName());
	                selectedInputLabel.setForeground(Color.BLACK);
	                fileSelected = true;
	                oldFrame.setContentPane(panel);
	        		oldFrame.invalidate();
	        		oldFrame.validate();
	               // } //If (csvFile())
	            } 
				
			}
		};
		
		ActionListener nextListener = new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				if(!fileSelected){
					JOptionPane.showConfirmDialog(null, "You must select an input file.", "Invalid Input", JOptionPane.DEFAULT_OPTION);
				}
				else{
					//confirmation(oldFrame); //Current this isn't working so...
					//Code for making a real schedule.
					Schedule schedule = new Schedule();
					int result = 1; //Just to initialize it. 
					try {
						result = schedule.makeASchedule(file, monthBack, yearBack, holidaysBack, sundayBack, mondayBack, tuesdayBack, wednesdayBack, thursdayBack, fridayBack, saturdayBack, minToursPerGuideBack, maxToursPerGuideBack, minGuidesPerTourBack, maxGuidesPerTourBack);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						output(oldFrame, -9);
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						output(oldFrame, -8);
						e.printStackTrace();
					}
					System.out.println("Our result integer was " + result);
					if(result != 0){
						output(oldFrame, result);
					}
					else{
						chooseOutputFolder(oldFrame, schedule);
					}
				}
			}	
		};
		
		ActionListener backListener = new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				otherRequirements(oldFrame);
			}
		};
		next.addActionListener(nextListener);
		selectInputFileButton.addActionListener(selectFileListener);
		back.addActionListener(backListener);
		
	}//chooseInputFile
	
	public void output(JFrame oldFrame, int result){
		panel = new JPanel();
		
		JButton mainMenuButton = new JButton("Main Menu");
		mainMenuButton.setFont(new Font("Dialog", Font.BOLD, 12));
		mainMenuButton.setBackground(buttonColor); //Light gray
		
		JLabel topOutputLabel, bottomOutputLabel;
		topOutputLabel = new JLabel();
		bottomOutputLabel = new JLabel();
		switch(result){
		case 0:
			topOutputLabel.setText("Your schedule is finished!");
			bottomOutputLabel.setText("It can be found in \"" + outputFileNameBack + ".csv\" in the folder " + folder.getAbsolutePath() + ".");
			break;
		case -1:
			topOutputLabel.setText("At least one of the answers for a guide's availability was invalid.");
			bottomOutputLabel.setText("It must be \"Yes\"/\"No\", \"Y\"/\"N\", or \"1\"/\"0\"");
			break;
		case -2:
			topOutputLabel.setText("At least one of the guides didn't have the correct number of availability answers.");
			bottomOutputLabel.setText("Each guide must have the same number of answers as there are tours scheduled for each week.");
			break;
		case -5:
			topOutputLabel.setText("A schedule could not be created.");
			bottomOutputLabel.setText("Neither of the two scheduling algorithms found a valid schedule.");
			break;
		case -8:
			topOutputLabel.setText("An error occurred.");
			bottomOutputLabel.setText("Make sure to close any files that are being used by this program."); 
			break;
		case -9:
			topOutputLabel.setText("An error occured.");
			bottomOutputLabel.setText("The file could not be found.");
			break;
		default:
			topOutputLabel.setText("The result was " + result);
			bottomOutputLabel.setText("This output shouldn't actually happen.");
		}
		topOutputLabel.setFont(new Font("Dialog", Font.BOLD, 15));
		bottomOutputLabel.setFont(new Font("Dialog", Font.BOLD, 15));
		
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(10,10,10,10);		

		JPanel outputPanel = new JPanel(new GridBagLayout());
		c.gridx = 0;
		c.gridy = 0;
		outputPanel.add(topOutputLabel, c);
		c.gridx = 0;
		c.gridy = 1;
		outputPanel.add(bottomOutputLabel, c);
		
		panel.setLayout(new GridBagLayout());
		c.gridx = 0;
		c.gridy = 0;
		outputPanel.setBackground(backgroundColor);
		panel.add(outputPanel, c);
		c.gridx = 0;
		c.gridy = 1;
		panel.add(mainMenuButton, c);
		panel.setBackground(backgroundColor);
		
		oldFrame.setContentPane(panel);
		oldFrame.invalidate();
		oldFrame.validate();
		
		ActionListener mainMenuListener = new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				mainMenu(oldFrame);
			}	
		};
		mainMenuButton.addActionListener(mainMenuListener);
	} //Output
	
	public void dayByDay(JFrame oldFrame){
		panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		JLabel topLabel;
		topLabel = new JLabel("How many tours are there each day?");
		topLabel.setFont(new Font("Dialog", Font.BOLD, 25));
		JLabel sundayLabel, mondayLabel, tuesdayLabel, wednesdayLabel, thursdayLabel, fridayLabel, saturdayLabel;
		JPanel sundayPanel, mondayPanel, tuesdayPanel, wednesdayPanel, thursdayPanel, fridayPanel, saturdayPanel;
		JButton back, next;
		
		//Customize those buttons!
		back = new JButton("Back");
		back.setFont(new Font("Dialog", Font.BOLD, 12));
		back.setBackground(buttonColor); //Light gray
		next = new JButton("Next");
		next.setFont(new Font("Dialog", Font.BOLD, 12));
		next.setBackground(buttonColor);
		
		//Radio buttons
		ButtonGroup sundayRadio, mondayRadio, tuesdayRadio, wednesdayRadio, thursdayRadio, fridayRadio, saturdayRadio;
		JRadioButton sun0, sun1, sun2, sun3, sun4, sun5;
		JRadioButton mon0, mon1, mon2, mon3, mon4, mon5;
		JRadioButton tue0, tue1, tue2, tue3, tue4, tue5;
		JRadioButton wed0, wed1, wed2, wed3, wed4, wed5;
		JRadioButton thu0, thu1, thu2, thu3, thu4, thu5;
		JRadioButton fri0, fri1, fri2, fri3, fri4, fri5;
		JRadioButton sat0, sat1, sat2, sat3, sat4, sat5;
		
		sundayRadio = new ButtonGroup();
		sun0 = new JRadioButton("0");
		sun0.setSelected(true);
		sun1 = new JRadioButton("1");
		sun1.setSelected(false);
		sun2 = new JRadioButton("2");
		sun2.setSelected(false);
		sun3 = new JRadioButton("3");
		sun3.setSelected(false);
		sun4 = new JRadioButton("4");
		sun4.setSelected(false);
		sun5 = new JRadioButton("5");
		sun5.setSelected(false);
		sundayRadio.add(sun0);
		sundayRadio.add(sun1);
		sundayRadio.add(sun2);
		sundayRadio.add(sun3);
		sundayRadio.add(sun4);
		sundayRadio.add(sun5);
		
		mondayRadio = new ButtonGroup();
		mon0 = new JRadioButton("0");
		mon0.setSelected(true);
		mon1 = new JRadioButton("1");
		mon1.setSelected(false);
		mon2 = new JRadioButton("2");
		mon2.setSelected(false);
		mon3 = new JRadioButton("3");
		mon3.setSelected(false);
		mon4 = new JRadioButton("4");
		mon4.setSelected(false);
		mon5 = new JRadioButton("5");
		mon5.setSelected(false);
		mondayRadio.add(mon0);
		mondayRadio.add(mon1);
		mondayRadio.add(mon2);
		mondayRadio.add(mon3);
		mondayRadio.add(mon4);
		mondayRadio.add(mon5);
		
		tuesdayRadio = new ButtonGroup();
		tue0 = new JRadioButton("0");
		tue0.setSelected(true);
		tue1 = new JRadioButton("1");
		tue1.setSelected(false);
		tue2 = new JRadioButton("2");
		tue2.setSelected(false);
		tue3 = new JRadioButton("3");
		tue3.setSelected(false);
		tue4 = new JRadioButton("4");
		tue4.setSelected(false);
		tue5 = new JRadioButton("5");
		tue5.setSelected(false);
		tuesdayRadio.add(tue0);
		tuesdayRadio.add(tue1);
		tuesdayRadio.add(tue2);
		tuesdayRadio.add(tue3);
		tuesdayRadio.add(tue4);
		tuesdayRadio.add(tue5);
		
		wednesdayRadio = new ButtonGroup();
		wed0 = new JRadioButton("0");
		wed0.setSelected(true);
		wed1 = new JRadioButton("1");
		wed1.setSelected(false);
		wed2 = new JRadioButton("2");
		wed2.setSelected(false);
		wed3 = new JRadioButton("3");
		wed3.setSelected(false);
		wed4 = new JRadioButton("4");
		wed4.setSelected(false);
		wed5 = new JRadioButton("5");
		wed5.setSelected(false);
		wednesdayRadio.add(wed0);
		wednesdayRadio.add(wed1);
		wednesdayRadio.add(wed2);
		wednesdayRadio.add(wed3);
		wednesdayRadio.add(wed4);
		wednesdayRadio.add(wed5);
		
		thursdayRadio = new ButtonGroup();
		thu0 = new JRadioButton("0");
		thu0.setSelected(true);
		thu1 = new JRadioButton("1");
		thu1.setSelected(false);
		thu2 = new JRadioButton("2");
		thu2.setSelected(false);
		thu3 = new JRadioButton("3");
		thu3.setSelected(false);
		thu4 = new JRadioButton("4");
		thu4.setSelected(false);
		thu5 = new JRadioButton("5");
		thu5.setSelected(false);
		thursdayRadio.add(thu0);
		thursdayRadio.add(thu1);
		thursdayRadio.add(thu2);
		thursdayRadio.add(thu3);
		thursdayRadio.add(thu4);
		thursdayRadio.add(thu5);
		
		fridayRadio = new ButtonGroup();
		fri0 = new JRadioButton("0");
		fri0.setSelected(true);
		fri1 = new JRadioButton("1");
		fri1.setSelected(false);
		fri2 = new JRadioButton("2");
		fri2.setSelected(false);
		fri3 = new JRadioButton("3");
		fri3.setSelected(false);
		fri4 = new JRadioButton("4");
		fri4.setSelected(false);
		fri5 = new JRadioButton("5");
		fri5.setSelected(false);
		fridayRadio.add(fri0);
		fridayRadio.add(fri1);
		fridayRadio.add(fri2);
		fridayRadio.add(fri3);
		fridayRadio.add(fri4);
		fridayRadio.add(fri5);
		
		saturdayRadio = new ButtonGroup();
		sat0 = new JRadioButton("0");
		sat0.setSelected(true);
		sat1 = new JRadioButton("1");
		sat1.setSelected(false);
		sat2 = new JRadioButton("2");
		sat2.setSelected(false);
		sat3 = new JRadioButton("3");
		sat3.setSelected(false);
		sat4 = new JRadioButton("4");
		sat4.setSelected(false);
		sat5 = new JRadioButton("5");
		sat5.setSelected(false);
		saturdayRadio.add(sat0);
		saturdayRadio.add(sat1);
		saturdayRadio.add(sat2);
		saturdayRadio.add(sat3);
		saturdayRadio.add(sat4);
		saturdayRadio.add(sat5);

		sundayLabel = new JLabel("Sunday");
		sundayLabel.setFont(new Font("Dialog", Font.BOLD, 15));
		mondayLabel = new JLabel("Monday");
		mondayLabel.setFont(new Font("Dialog", Font.BOLD, 15));
		tuesdayLabel = new JLabel("Tuesday");
		tuesdayLabel.setFont(new Font("Dialog", Font.BOLD, 15));
		wednesdayLabel = new JLabel("Wednesday");
		wednesdayLabel.setFont(new Font("Dialog", Font.BOLD, 15));
		thursdayLabel = new JLabel("Thursday");
		thursdayLabel.setFont(new Font("Dialog", Font.BOLD, 15));
		fridayLabel = new JLabel("Friday");
		fridayLabel.setFont(new Font("Dialog", Font.BOLD, 15));
		saturdayLabel = new JLabel("Saturday");
		saturdayLabel.setFont(new Font("Dialog", Font.BOLD, 15));
		
		//Sunday panel
		sundayPanel = new JPanel(new GridBagLayout());
		c.insets = new Insets(10, 10, 10, 10);
		//c.insets = new Insets(5,5,5,5);
		c.gridx = 0;
		c.gridy = 0;
		sundayPanel.add(sundayLabel, c);
		c.insets = new Insets(5,5,5,5);
		c.gridx = 0;
		c.gridy = 1;
		sun0.setBackground(backgroundColor);
		sundayPanel.add(sun0, c);
		c.gridx = 0;
		c.gridy = 2;
		sun1.setBackground(backgroundColor);
		sundayPanel.add(sun1, c);
		c.gridx = 0;
		c.gridy = 3;
		sun2.setBackground(backgroundColor);
		sundayPanel.add(sun2, c);
		c.gridx = 0;
		c.gridy = 4;
		sun3.setBackground(backgroundColor);
		sundayPanel.add(sun3, c);
		c.gridx = 0;
		c.gridy = 5;
		sun4.setBackground(backgroundColor);
		sundayPanel.add(sun4, c);
		c.gridx = 0;
		c.gridy = 6;
		sun5.setBackground(backgroundColor);
		sundayPanel.add(sun5, c);
		
		//Monday panel
		mondayPanel = new JPanel(new GridBagLayout());
		c.insets = new Insets(10, 10, 10, 10);
		c.gridx = 0;
		c.gridy = 0;
		mondayPanel.add(mondayLabel, c);
		c.insets = new Insets(5,5,5,5);
		c.gridx = 0;
		c.gridy = 1;
		mon0.setBackground(backgroundColor);
		mondayPanel.add(mon0, c);
		c.gridx = 0;
		c.gridy = 2;
		mon1.setBackground(backgroundColor);
		mondayPanel.add(mon1, c);
		c.gridx = 0;
		c.gridy = 3;
		mon2.setBackground(backgroundColor);
		mondayPanel.add(mon2, c);
		c.gridx = 0;
		c.gridy = 4;
		mon3.setBackground(backgroundColor);
		mondayPanel.add(mon3, c);
		c.gridx = 0;
		c.gridy = 5;
		mon4.setBackground(backgroundColor);
		mondayPanel.add(mon4, c);
		c.gridx = 0;
		c.gridy = 6;
		mon5.setBackground(backgroundColor);
		mondayPanel.add(mon5, c);
		
		//Tuesday panel
		tuesdayPanel = new JPanel(new GridBagLayout());
		c.insets = new Insets(10, 10, 10, 10);
		c.gridx = 0;
		c.gridy = 0;
		tuesdayPanel.add(tuesdayLabel, c);
		c.insets = new Insets(5,5,5,5);
		c.gridx = 0;
		c.gridy = 1;
		tue0.setBackground(backgroundColor);
		tuesdayPanel.add(tue0, c);
		c.gridx = 0;
		c.gridy = 2;
		tue1.setBackground(backgroundColor);
		tuesdayPanel.add(tue1, c);
		c.gridx = 0;
		c.gridy = 3;
		tue2.setBackground(backgroundColor);
		tuesdayPanel.add(tue2, c);
		c.gridx = 0;
		c.gridy = 4;
		tue3.setBackground(backgroundColor);
		tuesdayPanel.add(tue3, c);
		c.gridx = 0;
		c.gridy = 5;
		tue4.setBackground(backgroundColor);
		tuesdayPanel.add(tue4, c);
		c.gridx = 0;
		c.gridy = 6;
		tue5.setBackground(backgroundColor);
		tuesdayPanel.add(tue5, c);
		
		//Wednesday panel
		wednesdayPanel = new JPanel(new GridBagLayout());
		c.insets = new Insets(10, 10, 10, 10);
		c.gridx = 0;
		c.gridy = 0;
		wednesdayPanel.add(wednesdayLabel, c);
		c.insets = new Insets(5,5,5,5);
		c.gridx = 0;
		c.gridy = 1;
		wed0.setBackground(backgroundColor);
		wednesdayPanel.add(wed0, c);
		c.gridx = 0;
		c.gridy = 2;
		wed1.setBackground(backgroundColor);
		wednesdayPanel.add(wed1, c);
		c.gridx = 0;
		c.gridy = 3;
		wed2.setBackground(backgroundColor);
		wednesdayPanel.add(wed2, c);
		c.gridx = 0;
		c.gridy = 4;
		wed3.setBackground(backgroundColor);
		wednesdayPanel.add(wed3, c);
		c.gridx = 0;
		c.gridy = 5;
		wed4.setBackground(backgroundColor);
		wednesdayPanel.add(wed4, c);
		c.gridx = 0;
		c.gridy = 6;
		wed5.setBackground(backgroundColor);
		wednesdayPanel.add(wed5, c);
		
		//Thursday panel
		thursdayPanel = new JPanel(new GridBagLayout());
		c.insets = new Insets(10, 10, 10, 10);
		c.gridx = 0;
		c.gridy = 0;
		thursdayPanel.add(thursdayLabel, c);
		c.insets = new Insets(5,5,5,5);
		c.gridx = 0;
		c.gridy = 1;
		thu0.setBackground(backgroundColor);
		thursdayPanel.add(thu0, c);
		c.gridx = 0;
		c.gridy = 2;
		thu1.setBackground(backgroundColor);
		thursdayPanel.add(thu1, c);
		c.gridx = 0;
		c.gridy = 3;
		thu2.setBackground(backgroundColor);
		thursdayPanel.add(thu2, c);
		c.gridx = 0;
		c.gridy = 4;
		thu3.setBackground(backgroundColor);
		thursdayPanel.add(thu3, c);
		c.gridx = 0;
		c.gridy = 5;
		thu4.setBackground(backgroundColor);
		thursdayPanel.add(thu4, c);
		c.gridx = 0;
		c.gridy = 6;
		thu5.setBackground(backgroundColor);
		thursdayPanel.add(thu5, c);
		
		//Friday panel
		fridayPanel = new JPanel(new GridBagLayout());
		c.insets = new Insets(10, 10, 10, 10);
		c.gridx = 0;
		c.gridy = 0;
		fridayPanel.add(fridayLabel, c);
		c.insets = new Insets(5,5,5,5);
		c.gridx = 0;
		c.gridy = 1;
		fri0.setBackground(backgroundColor);
		fridayPanel.add(fri0, c);
		c.gridx = 0;
		c.gridy = 2;
		fri1.setBackground(backgroundColor);
		fridayPanel.add(fri1, c);
		c.gridx = 0;
		c.gridy = 3;
		fri2.setBackground(backgroundColor);
		fridayPanel.add(fri2, c);
		c.gridx = 0;
		c.gridy = 4;
		fri3.setBackground(backgroundColor);
		fridayPanel.add(fri3, c);
		c.gridx = 0;
		c.gridy = 5;
		fri4.setBackground(backgroundColor);
		fridayPanel.add(fri4, c);
		c.gridx = 0;
		c.gridy = 6;
		fri5.setBackground(backgroundColor);
		fridayPanel.add(fri5, c);
		
		//Saturday panel
		saturdayPanel = new JPanel(new GridBagLayout());
		c.insets = new Insets(10, 10, 10, 10);
		c.gridx = 0;
		c.gridy = 0;
		saturdayPanel.add(saturdayLabel, c);
		c.insets = new Insets(5,5,5,5);
		c.gridx = 0;
		c.gridy = 1;
		sat0.setBackground(backgroundColor);
		saturdayPanel.add(sat0, c);
		c.gridx = 0;
		c.gridy = 2;
		sat1.setBackground(backgroundColor);
		saturdayPanel.add(sat1, c);
		c.gridx = 0;
		c.gridy = 3;
		sat2.setBackground(backgroundColor);
		saturdayPanel.add(sat2, c);
		c.gridx = 0;
		c.gridy = 4;
		sat3.setBackground(backgroundColor);
		saturdayPanel.add(sat3, c);
		c.gridx = 0;
		c.gridy = 5;
		sat4.setBackground(backgroundColor);
		saturdayPanel.add(sat4, c);
		c.gridx = 0;
		c.gridy = 6;
		sat5.setBackground(backgroundColor);
		saturdayPanel.add(sat5, c);
		
		c.gridx = 3;
		c.gridy = 0;
		panel.add(topLabel, c);
		c.gridx = 0;
		c.gridy = 1;
		sundayPanel.setBackground(backgroundColor);
		panel.add(sundayPanel, c);
		c.gridx = 1;
		c.gridy = 1;
		mondayPanel.setBackground(backgroundColor);
		panel.add(mondayPanel, c);
		c.gridx = 2;
		c.gridy = 1;
		tuesdayPanel.setBackground(backgroundColor);
		panel.add(tuesdayPanel, c);
		c.gridx = 3;
		c.gridy = 1;
		wednesdayPanel.setBackground(backgroundColor);
		panel.add(wednesdayPanel, c);
		c.gridx = 4;
		c.gridy = 1;
		thursdayPanel.setBackground(backgroundColor);
		panel.add(thursdayPanel, c);
		c.gridx = 5;
		c.gridy = 1;
		fridayPanel.setBackground(backgroundColor);
		panel.add(fridayPanel, c);
		c.gridx = 6;
		c.gridy = 1;
		saturdayPanel.setBackground(backgroundColor);
		panel.add(saturdayPanel, c);
		
		JPanel mainPanel, topPanel, bottomPanel;
		topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 12));
		
		topPanel.add(back);
		bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 12));
		
		bottomPanel.add(next);
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
		topPanel.setBackground(backgroundColor);
		mainPanel.add(topPanel);
		mainPanel.add(topLabel);
		panel.setBackground(backgroundColor);
		mainPanel.add(panel);
		bottomPanel.setBackground(backgroundColor);
		mainPanel.add(bottomPanel);
		//back.setAlignmentX(Component.CENTER_ALIGNMENT);
		//topLabel.setAlignmentY(Component.);
		topLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		mainPanel.setBackground(backgroundColor);

		selectTheRightOne(sundayBack, sun0, sun1, sun2, sun3, sun4, sun5);
		selectTheRightOne(mondayBack, mon0, mon1, mon2, mon3, mon4, mon5);
		selectTheRightOne(tuesdayBack, tue0, tue1, tue2, tue3, tue4, tue5 );
		selectTheRightOne(wednesdayBack, wed0, wed1, wed2, wed3, wed4, wed5 );
		selectTheRightOne(thursdayBack, thu0, thu1, thu2, thu3, thu4, thu5);
		selectTheRightOne(fridayBack, fri0, fri1, fri2, fri3, fri4, fri5 );
		selectTheRightOne(saturdayBack, sat0, sat1, sat2, sat3, sat4, sat5);
		
		oldFrame.setContentPane(mainPanel);
		oldFrame.invalidate();
		oldFrame.validate();
		
		ActionListener backListener = new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				sundayBack = findSelected(sun0, sun1, sun2, sun3, sun4, sun5);
				mondayBack = findSelected(mon0, mon1, mon2, mon3, mon4, mon5);
				tuesdayBack = findSelected(tue0, tue1, tue2, tue3, tue4, tue5);
				wednesdayBack = findSelected(wed0, wed1, wed2, wed3, wed4, wed5);
				thursdayBack = findSelected(thu0, thu1, thu2, thu3, thu4, thu5);
				fridayBack = findSelected(fri0, fri1, fri2, fri3, fri4, fri5);
				saturdayBack = findSelected(sat0, sat1, sat2, sat3, sat4, sat5);
				monthAndHolidays(oldFrame);
			}
		};
		
		ActionListener nextListener = new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				//Get the values of all those radio buttons.
				sundayBack = findSelected(sun0, sun1, sun2, sun3, sun4, sun5);
				mondayBack = findSelected(mon0, mon1, mon2, mon3, mon4, mon5);
				tuesdayBack = findSelected(tue0, tue1, tue2, tue3, tue4, tue5);
				wednesdayBack = findSelected(wed0, wed1, wed2, wed3, wed4, wed5);
				thursdayBack = findSelected(thu0, thu1, thu2, thu3, thu4, thu5);
				fridayBack = findSelected(fri0, fri1, fri2, fri3, fri4, fri5);
				saturdayBack = findSelected(sat0, sat1, sat2, sat3, sat4, sat5);
				
				if(sundayBack == 0 && mondayBack == 0 && tuesdayBack == 0 && wednesdayBack == 0 && thursdayBack == 0 && fridayBack == 0 && saturdayBack == 0)
				{
					JOptionPane.showConfirmDialog(null, "There has to be at least one tour for at least one day of the week.", "Invalid Input", JOptionPane.DEFAULT_OPTION);
				}
				else{
					otherRequirements(oldFrame);
				}
			}	
		};
		next.addActionListener(nextListener);
		back.addActionListener(backListener);
		
	} //dayByDay
	
	public void intro(){
		JFrame frame;
		frame = new JFrame("Devils' Advocates Tour Manager");
		
		panel = new JPanel(new GridBagLayout());
		panel.setBackground(backgroundColor);
		GridBagConstraints c = new GridBagConstraints();
		
		
		//ImageIcon advoIcon = new ImageIcon("DevilsAdvocatesLogo.jpg","Logo");
		ImageIcon advoIcon = new ImageIcon(getClass().getResource("/images/DevilsAdvocatesLogo.jpg"));
		JLabel imageLabel = new JLabel(advoIcon);
		
		c.insets = new Insets(10,10,10,10);
		c.gridx = 0;
		c.gridy = 0;
		panel.add(imageLabel, c);
		
		
		frame.add(panel);
		frame.setVisible(true);
		frame.setSize(700, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		
		/*frameDimensions = frame.getBounds().getSize();
		class ResizeListener implements ComponentListener {

	        public void componentHidden(ComponentEvent e) {}
	        public void componentMoved(ComponentEvent e) {}
	        public void componentShown(ComponentEvent e) {}

	        public void componentResized(ComponentEvent e) {
	            frameDimensions = e.getComponent().getBounds().getSize();   
	            System.out.println("Resized");
	        }   
	    }
		
		frame.addComponentListener(new ResizeListener());*/
		
		//Delay should be 1500 or so!
		int delay = 2750; //milliseconds...or 1.5 seconds
		ActionListener taskPerformer = new ActionListener() {
		    public void actionPerformed(ActionEvent evt) {
		   		mainMenu(frame);	//This is what it will have to be at the end.   	
		    }
		};
		Timer t = new Timer(delay, taskPerformer);
		t.setRepeats(false); //So it won't keep repeating the action listener
		t.start();
	}
	
	public void mainMenu(JFrame oldFrame){
		panel = new JPanel(new GridBagLayout());
		panel.setBackground(backgroundColor);
		
		GridBagConstraints c = new GridBagConstraints();
		
		JLabel welcomeLabel = new JLabel("Welcome to the Devils' Advocates Tour Manager!");
		welcomeLabel.setFont(new Font("Dialog", Font.BOLD, 25));
		
		JButton startButton, tutorialButton, aboutButton;
		startButton = new JButton("Start");
		startButton.setFont(new Font("Dialog", Font.BOLD, 12));
		startButton.setBackground(buttonColor);
		//startButton.setBackground(new Color(205,205,193));
		//startButton.setBackground(new Color(224,224,224)); //Light gray
		tutorialButton = new JButton("Tutorial");
		tutorialButton.setFont(new Font("Dialog", Font.BOLD, 12));
		//tutorialButton.setBackground(new Color(245,245,245)); //White smoke
		//tutorialButton.setBackground(new Color(224,224,224)); //Light gray
		tutorialButton.setBackground(buttonColor);
		
		
		aboutButton = new JButton("About");
		aboutButton.setFont(new Font("Dialog", Font.BOLD, 12));
		aboutButton.setBackground(buttonColor);
		
		
		/*
		ImageIcon advoIcon = new ImageIcon("DevilsAdvocatesLogo.jpg","Logo");
		JLabel imageLabel = new JLabel(advoIcon);*/
		
		c.insets = new Insets(10,10,10,10);
		c.gridx = 0;
		c.gridy = 0;
		panel.add(welcomeLabel, c);
		c.gridx = 0;
		c.gridy = 1;
		panel.add(startButton, c);
		c.gridx = 0;
		c.gridy = 2;
		panel.add(tutorialButton, c);
		c.gridx = 0;
		c.gridy = 3;
		panel.add(aboutButton, c);
		
		
		oldFrame.setSize(700, 500); //In case we're coming back from the tutorial or about section.
		oldFrame.setContentPane(panel);
		oldFrame.invalidate();
		oldFrame.validate();
		//oldFrame.setVisible(false);
		
		ActionListener startListener = new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				monthAndHolidays(oldFrame);
			}
		};
		startButton.addActionListener(startListener);
		
		ActionListener tutorialListener = new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				/*
				Tutorial tut = new Tutorial(buttonColor, backgroundColor);
				tut.overview(oldFrame);
				*/
				try {
					tutorial(oldFrame);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		};
		tutorialButton.addActionListener(tutorialListener);
		
		ActionListener aboutListener = new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				/*
				Tutorial tut = new Tutorial(buttonColor, backgroundColor);
				tut.overview(oldFrame);
				*/
				try {
					about(oldFrame);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		};
		aboutButton.addActionListener(aboutListener);
	}
	
	//Returns true if it's a valid holiday string, false otherwise.
	public boolean checkHolidays(String holidays){
		if(holidays.isEmpty())
			return true;
		x = new Scanner(holidays);
		x.useDelimiter(","); //We expect it to be comma delimited.
		while(x.hasNext()){
			if(!isANumber(x.next())){
				return false;
			}
		}
		return true;
	}
	
	//Returns true if the string is a number (or empty), false otherwise.
	public static boolean isANumber(String input){
		if(input.isEmpty()){
			return true;
		}
		for(int i = 0; i < input.length(); i++){
			if(input.charAt(i) != '0' && input.charAt(i) != '1' &&
			   input.charAt(i) != '2' && input.charAt(i) != '3' &&
			   input.charAt(i) != '4' && input.charAt(i) != '5' &&
			   input.charAt(i) != '6' && input.charAt(i) != '7' &&
			   input.charAt(i) != '8' && input.charAt(i) != '9'){
				return false;
			}
		}
		return true;
	}
	
	public String tourOrTours(int x){
		if(x == 1){
			return "tour";
		}
		else
			return "tours";
	}
	//Selects the 1st, 2nd, 3rd, 4th, or 5th button, depending on what "index" is. 
	public void selectTheRightOne(int index, JRadioButton a, JRadioButton b, JRadioButton c, JRadioButton d, JRadioButton e, JRadioButton f){
		switch(index){
		case 1:
			b.setSelected(true);
			break;
		case 2:
			c.setSelected(true);
			break;
		case 3:
			d.setSelected(true);
			break;
		case 4:
			e.setSelected(true);
			break;
		case 5:
			f.setSelected(true);
			break;
		}
	}
	
	public int findSelected(JRadioButton a, JRadioButton b, JRadioButton c, JRadioButton d, JRadioButton e, JRadioButton f){
		if(a.isSelected())
			return 0;
		else if(b.isSelected())
			return 1;
		else if(c.isSelected())
			return 2;
		else if(d.isSelected())
			return 3;
		else if(e.isSelected())
			return 4;
		else 
			return 5;
	}
	
	//True if min and max are valid. False otherwise.
	public boolean compareMinAndMax(String min, String max){
		if(min.isEmpty() || max.isEmpty()){
			return true; //Because it's valid by default.
		}
		int minInt = Integer.parseInt(min);
		int maxInt = Integer.parseInt(max);
		if(maxInt >= minInt){
			return true;
		}
		return false;
	}
	
	public String intToMonth(int i){
		switch(i){
		case 0: 
			return "January";
		case 1: 
			return "February";
		case 2:
			return "March";
		case 3:
			return "April";
		case 4:
			return "May";
		case 5:
			return "June";
		case 6:
			return "July";
		case 7:
			return "August";
		case 8:
			return "September";
		case 9:
			return "October";
		case 10:
			return "November";
		default:
			return "December";
		}
	}
	
	public static void main(String[] args){
		try {
			new DAMain();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
