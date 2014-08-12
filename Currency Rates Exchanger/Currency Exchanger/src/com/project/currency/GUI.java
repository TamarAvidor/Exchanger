package com.project.currency;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
import org.apache.log4j.BasicConfigurator;

/**
 * Class GUI will define, create and present the graphic User Interface.
 *
 * @param mainFrame          			will be used to add the other components for the GUI.
 * @param buttonConvert                 a button to take action and convert the written amount.
 * @param buttonClear                   a button to take action and clear the written amount and the result field.
 * @param amountField                   a text field which the user inserts the amount of money to convert.
 * @param resultField                   a text area that will be used to show the amount conversion.
 * @param sourceScroller                a scroll to roll up over the source currencies pane.
 * @param destinationScroller           a scroll to roll up over the destination currencies pane.
 * @param sourceCoinLable               a label to contain the text for the source coin.
 * @param destinationCoinLable          a label to contain the text for the destination coin.
 * @param amountToConvertLabel          a label to contain the text for the amount to convert.
 * @param DefaultListModel              a listModel to add the coins elements.
 * @param sourceCurrenciesList          a list to hold all the source coins to convert from.
 * @param destinationCurrenciesList     a list to hold all the destination coins to convert to.
 */
public class GUI implements ActionListener {
	private IExchanger exchanger = null;
	private IAppManager appManager = null;

	private JFrame mainFrame = null;
	private JButton buttonConvert = null, buttonClear = null;
	private JPanel mainPanel = null, buttonsPanel = null, amountPanel = null, listTablePanel = null;
    private JPanel listsPanel = null, amountFieldPanel = null, panelOfResultField = null;
	private JTextField amountField = null, resultField = null;
  	private JScrollPane sourceScroller = null, destinationScroller = null;
	private JLabel sourceCoinLable = null, destinationCoinLable = null, amountToConvertLabel = null;
	private DefaultListModel model = null;
	private JList sourceCurrenciesList = null, destinationCurrenciesList = null;
	
	/**
	 * Instantiating the declared variables of the class.
	 */
	public GUI()
	{
		mainFrame = new JFrame("Currency Exchange Application");
        resultField = new JTextField();
        buttonConvert = new JButton("Convert");
        buttonClear = new JButton("Clear");
		mainPanel = new JPanel();
        amountPanel = new JPanel();
        listTablePanel = new JPanel();
        listsPanel = new JPanel();
        amountFieldPanel = new JPanel();
        panelOfResultField = new JPanel();
        buttonsPanel = new JPanel();
		amountField = new JTextField();
		sourceCoinLable = new JLabel("Source Coin");
		destinationCoinLable = new JLabel("Destination Coin");
		amountToConvertLabel = new JLabel("Amount to convert");
		model = new DefaultListModel();
        resultField.setEditable(false);                  //Disabling the option to edit the result field.
    }
	
	/**
	 * Responsible to run the main thread runnable object, adding new components to the GUI view and filling the 2 currencies lists
	 */
	public void start()
	{
        BasicConfigurator.configure();                         //Configuring the logger to be in basic mode
        appManager = new AppManager();							//Creating the application manager object 						 
        if(appManager.refreshDataAndCheckForSuccess() == false)
        {
        	JOptionPane.showMessageDialog(null,"First data download failed! Program will now exit.",
                    "Data base missing error",JOptionPane.ERROR_MESSAGE);
			Program.logger.error(this.toString() + ": First operation data download has failed. No previous data to work with, Program has been closed");
        	System.exit(-1);
        }
        exchanger = new RatesExchanger();
        creatingMainFrame();
	}
	
    private void creatingMainFrame()
    {
        creatingTheCurrenciesList();
        creatingTheAmountPanel();
        creatingTheResultPanel();
        creatingTheButtonsPanel();
        mainFrame.setMinimumSize(new Dimension(800, 600));
        mainFrame.addWindowListener(new WindowAdapter()        //Adding window listener to the exit operation event
        {
            public void windowClosing(WindowEvent event) {
                Program.logger.debug(this.toString() + ": Window terminated by user request");
                System.exit(0);
            }
        });
        actionsAndDebug();
        mainFrame.setVisible(true);
  }

    private void creatingTheListsLabels()
    {
        listTablePanel.setLayout(new GridLayout(1, 2));
        listTablePanel.add(sourceCoinLable);
        sourceCoinLable.setFont(new Font(null, 1, 18));
        listTablePanel.add(destinationCoinLable);
        destinationCoinLable.setFont(new Font(null, 1, 18));
        mainPanel.add(listTablePanel);
    }

    /**
     * Iterating the currencies array and adding the coins elements to a list model.
     * Configuring the GUI details including the currencies list display list.
     */
    private void creatingTheCurrenciesList()
    {
        creatingTheListsLabels();
        for(String coinName: appManager.getCurrenciesArray())
        {
            model.addElement(coinName);
        }
        sourceCurrenciesList = new JList(model);
        sourceCurrenciesList.setBackground(Color.yellow);
        Program.logger.debug(this.toString() + ": First list of currencies generated successfully!");
        destinationCurrenciesList = new JList(model);
        destinationCurrenciesList.setBackground(Color.yellow);
        Program.logger.debug(this.toString() + ": Second list of currencies generated successfully!");
        sourceScroller = new JScrollPane(sourceCurrenciesList);
        destinationScroller = new JScrollPane(destinationCurrenciesList);
        listsPanel.setLayout(new GridLayout(1, 2));
        listsPanel.add(sourceScroller);
        listsPanel.add(destinationScroller);
        mainPanel.add(listsPanel);
    }

    /**
     * Configuring the amount area for display.
     */
    private void creatingTheAmountPanel()
    {
        amountPanel.setLayout(new GridLayout(1, 3));
        amountToConvertLabel.setFont(new Font(null, 1, 16));
        amountPanel.add(amountToConvertLabel);
        amountFieldPanel.setLayout(new GridLayout(3, 1));
        amountFieldPanel.add(new JPanel());
        amountFieldPanel.add(amountField);
        amountField.setFont(new Font(null, 1, 18));
        amountField.setBackground(Color.LIGHT_GRAY);
        amountFieldPanel.add(new JPanel());
        amountPanel.add(amountFieldPanel);
        amountPanel.add(new JLabel());
        mainPanel.add(amountPanel);
    }

    /**
     * Configuring the result panel area for display.
     */
    private void creatingTheResultPanel()
    {
        mainPanel.setLayout(new GridLayout(4,1));
        panelOfResultField.setLayout(new BorderLayout());
        panelOfResultField.add(BorderLayout.NORTH, new JPanel());
        panelOfResultField.add(BorderLayout.CENTER, resultField);
        resultField.setBackground(Color.cyan);
        panelOfResultField.add(BorderLayout.SOUTH, new JPanel());
        mainPanel.add(panelOfResultField);
        mainFrame.add(BorderLayout.CENTER, mainPanel);
    }
    
	/**
	 * Configuring the buttons panel for display.
	 */
    private void creatingTheButtonsPanel()
    {
        buttonsPanel.add(buttonConvert);
        buttonsPanel.add(buttonClear);
        buttonsPanel.setSize(600,100);
        mainFrame.add(BorderLayout.SOUTH, buttonsPanel);
    }
    
    /**
     * Adding action listeners to the buttons and logs the logger messages. 
     */
    private void actionsAndDebug()
    {
        buttonConvert.addActionListener((ActionListener) this);
        Program.logger.debug(this.toString() + ": ActionListner added to btConvert object");
        buttonClear.addActionListener((ActionListener) this);
        Program.logger.debug(this.toString() + ": ActionListner added to btClear object");
        Program.logger.addAppender(Program.appender);
        Program.logger.debug(this.toString() + ": Currencies Exchange Rate table generated");
    }

    /**
     * Handles events from the GUI.
     * 
     * @param source                   hold the target component of which event occurred. (the source of the action event).                       			
     */
    public void actionPerformed(ActionEvent event)
    /*In this method we call the relevant methods according to the user requests.
      done it by using 3 action listeners 1. for buttonConvert 2. for buttonClear 3. for buttonRefresh */
    {
        Object source = event.getSource();
        
        if (source == buttonConvert)
        {
        	Program.logger.info(this.toString() + ": Convert button pushed");
            if ((sourceCurrenciesList.isSelectionEmpty() == false)
              &&(destinationCurrenciesList.isSelectionEmpty() == false)
              &&(amountField.getText().isEmpty() == false))
            {
               try
               {
            	   if(appManager.isDataUpToDate(appManager.getFileDate(), 4) == true)
            	   {
            		   calcResultAndAnnounce();
            	   }
            	   else
            	   {
            		   JOptionPane.showMessageDialog(null,"File is not up to date", "Data is not correct", JOptionPane.ERROR_MESSAGE);
						Program.logger.debug(this.toString() + ": A convertion without an updated data was atempted ");
						
						if(appManager.refreshDataAndCheckForSuccess() == true)
						{
							calcResultAndAnnounce();
						}
						else
						{
							JOptionPane.showMessageDialog(null,"File still could not be updated", "Data is not correct", JOptionPane.ERROR_MESSAGE);
						}
            	   }

               }
               catch (NumberFormatException ex)
               {
                   JOptionPane.showMessageDialog(null,"You should enter number in Amount field !",
                                                      "Miss match type error",JOptionPane.ERROR_MESSAGE);
                   Program.logger.debug(this.toString() + ": Miss match type error : You should " +
                                                          "enter number in Amount field ");
               }
            }
           else
           {
                 JOptionPane.showMessageDialog(null,"You should select one currency from each list " +
                                                    "and Amount for exchanging","Information Missing"
                                                    ,JOptionPane.ERROR_MESSAGE);
                 Program.logger.debug(this.toString() + ": Information Missing warning: You should " +
                                                        "select one currency from each list and Amount " +
                                                        "for exchanging");
           }

        }
        
        if(source == buttonClear)
        {
        	Program.logger.info(this.toString() + ": Clear button pushed");
            amountField.setText(null);
            resultField.setText(null);
        }
    }
    
    /**
     * Sending the arguments for the calculations that the Exchanger will do.
     * 
     * @param rateResult           			   the result of the conversion. 
     */
    private void calcResultAndAnnounce()
    {
		   double rateResult = exchanger.moneyExchange(appManager.stringToCoin((String) sourceCurrenciesList.getSelectedValue()).getCurrencyRate(),
	                Double.parseDouble(amountField.getText()),
	                appManager.stringToCoin((String) destinationCurrenciesList.getSelectedValue()).getCurrencyRate());
	        resultField.setText("\t   RESULT:\t" + String.format("%.3f", rateResult));
	        resultField.setFont(new Font(null, 1, 18));
	        Program.logger.info(this.toString() + ": Exchange finished successfully! "
	                                            + Double.parseDouble(amountField.getText()) + " "
	                                            + (String) sourceCurrenciesList.getSelectedValue() + " equals "
	                                            + Double.toString(rateResult) + " "
	                                            + (String) destinationCurrenciesList.getSelectedValue());    	
    }
}
