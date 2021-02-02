import java.io.*;
import java.io.InputStream;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.*;
import java.awt.event.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.*;
import javax.swing.filechooser.*;
import javax.swing.event.*;
import javax.swing.text.*;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import static javax.swing.GroupLayout.Alignment.*;
import javax.swing.JFormattedTextField;

//import java.text.Format;
//import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;



public class GUI implements ActionListener {

  private int count = 0;
  private String resultsPath;
  private JLabel label;
  private static JLabel l_userFile;
  private JLabel l_urlExclusions;
  private JLabel l_dateRange;
  private JLabel l_dateRangeMessage;
  private JLabel l_resultsReturned;
  private JLabel l_results;
  private JLabel l_userFileMessage;
  private JLabel l_emptyLabel1;
  private JLabel l_emptyLabel2;
  private JLabel l_emptyLabel3;
  private JLabel l_emptyLabel4;

  private JFrame frame;
  private GroupLayout mainPageLayout;
  private JTextField urlExclusions;
  private static JFormattedTextField dateField;
  private JButton b_save;
  private JButton b_saveDate;
  private JButton b_submit;
  private JButton b_saveResults;

  private JFileChooser fileChooser1;
  private JFileChooser fileChooser2;
  private String enteredURLs;
  private JProgressBar progressBar;
  private int scraperToolNumber;
  private int scraperMatchNumber;

  public GUI(){

    frame = new JFrame();

    // Build buttons
    b_save = new JButton("save");
    b_save.addActionListener(this);

    b_saveDate = new JButton("save");
    b_saveDate.addActionListener(this);

    JButton b_open = new JButton("open");
    b_open.addActionListener(this);

    b_submit = new JButton("Submit");
    b_submit.addActionListener(this);

    b_saveResults = new JButton("save");
    b_saveResults.addActionListener(this);

    // Build Labels
    label = new JLabel("Number of clicks: 0");
    l_userFile = new JLabel("no file selected");
    l_urlExclusions = new JLabel("Please enter the URLs for search exclusion:");
    l_dateRange = new JLabel("Date Range Selector Here:");
    l_dateRangeMessage = new JLabel("Date to begin your search:");
    l_resultsReturned = new JLabel("Your Results Here:");
    l_userFileMessage = new JLabel("Upload NTI tool list here:");
    l_emptyLabel1 = new JLabel("");
    l_emptyLabel2 = new JLabel("");
    l_emptyLabel3 = new JLabel("");
    l_emptyLabel4 = new JLabel("");



    // Build Text Field
    urlExclusions = new JTextField();

    // Build Date Picker
    DateFormatter dateFormatter = new DateFormatter(new SimpleDateFormat("dd/MM/yyyy"));
    DefaultFormatterFactory dateFormatterFactory = new DefaultFormatterFactory(dateFormatter, new DateFormatter(), dateFormatter);

    dateField = new JFormattedTextField(dateFormatterFactory);

    // Build a GroupLayout
    mainPageLayout = new GroupLayout(frame.getContentPane());
    frame.getContentPane().setLayout(mainPageLayout);
    mainPageLayout.setAutoCreateGaps(true);
    mainPageLayout.setAutoCreateContainerGaps(true);

    mainPageLayout.setHorizontalGroup(mainPageLayout.createSequentialGroup()
      .addGroup(mainPageLayout.createParallelGroup(LEADING)
        .addComponent(l_userFileMessage)
        .addComponent(l_urlExclusions)
        .addComponent(l_dateRangeMessage)
        .addComponent(b_submit)
        .addComponent(l_resultsReturned))
      .addGroup(mainPageLayout.createParallelGroup()
        .addComponent(l_userFile)
        .addComponent(urlExclusions)
        .addComponent(dateField)
        .addComponent(l_emptyLabel1)
        .addComponent(l_emptyLabel3))
      .addGroup(mainPageLayout.createParallelGroup()
        .addComponent(b_open)
        .addComponent(b_save)
        .addComponent(b_saveDate)
        .addComponent(l_emptyLabel2)
        .addComponent(l_emptyLabel4))
    );

    mainPageLayout.setVerticalGroup(mainPageLayout.createSequentialGroup()
      .addGroup(mainPageLayout.createParallelGroup(BASELINE)
        .addComponent(l_userFileMessage)
        .addComponent(l_userFile)
        .addComponent(b_open))
      .addGroup(mainPageLayout.createParallelGroup(BASELINE)
        .addComponent(l_urlExclusions)
        .addComponent(urlExclusions)
        .addComponent(b_save))
      .addGroup(mainPageLayout.createParallelGroup(BASELINE)
        .addComponent(l_dateRangeMessage)
        .addComponent(dateField)
        .addComponent(b_saveDate))
      .addGroup(mainPageLayout.createParallelGroup(BASELINE)
        .addComponent(b_submit)
        .addComponent(l_emptyLabel1)
        .addComponent(l_emptyLabel2))
      .addGroup(mainPageLayout.createParallelGroup(BASELINE)
        .addComponent(l_resultsReturned)
        .addComponent(l_emptyLabel3)
        .addComponent(l_emptyLabel4))
    );

    dateField.setValue(new Date());
    frame.setSize(800,300);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setTitle("Our GUI");
    frame.setVisible(true);
  }

  public static void main(String[] args){
    new GUI();
  }

  @Override
  public void actionPerformed(ActionEvent e) {

    String eventName = e.getActionCommand();

    if(eventName.equals("submit")) {
      count++;
      label.setText("Number of clicks: " + count);

    } else if (e.getSource() == b_save) {

      enteredURLs = urlExclusions.getText();
      l_urlExclusions.setText("URLs to exclude: " + enteredURLs);

    } else if (eventName.equals("open")) {
      fileChooser1 = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
      fileChooser1.setAcceptAllFileFilterUsed(false);
      fileChooser1.setDialogTitle("Select a .xslx file");

      FileNameExtensionFilter restrict = new FileNameExtensionFilter("Only .xlsx files please", "xlsx");
      fileChooser1.addChoosableFileFilter(restrict);
      int r = fileChooser1.showOpenDialog(null);

      if (r == JFileChooser.APPROVE_OPTION){
        l_userFile.setText(fileChooser1.getSelectedFile().getAbsolutePath());
      } else {
        l_userFile.setText("File needed");
      }

    } else if (e.getSource() == b_saveDate) {
      if (dateField.isEditValid()){
        //getDate();
        //dateField.setValue();
      }

    } else if (e.getSource() == b_submit) {
      submit();

    } else if (e.getSource() == b_saveResults) {
      fileChooser2 = new JFileChooser();
      fileChooser2.setAcceptAllFileFilterUsed(false);
      fileChooser2.setDialogTitle("Select a location to save results.xlsx");
      fileChooser2.setSelectedFile(new File("results/results.xlsx"));

      int r = fileChooser2.showSaveDialog(null);

      if (r == JFileChooser.APPROVE_OPTION){
        try {
          resultsPath = "results/results.xlsx";
          Path src = Paths.get(resultsPath);
          Path dest = Paths.get(fileChooser2.getSelectedFile().getAbsolutePath());
          Files.copy(src, dest);
        } catch (IOException except1) {
           System.out.println("Exception while moving file: " + except1.getMessage());
        }
      }
    }

  }

  public String getSearch() {
    String search = fileChooser1.getSelectedFile().getAbsolutePath();
    return search;
  }

  public String getUrls() {
    String urls;
    if (enteredURLs != null) {
      urls = enteredURLs.replaceAll("\\s","");
    } else {
      urls = "";
    }
    return urls;
  }

  public String getDate(String monthOrYear){
    Date userDate = (Date) dateField.getValue();
    Calendar cal = Calendar.getInstance();
    cal.setTime(userDate);

    if (monthOrYear == "month"){
      int month = cal.get(Calendar.MONTH);
      month++;
      return String.valueOf(month);
    } else if (monthOrYear == "year"){
      int year = cal.get(Calendar.YEAR);
      return String.valueOf(year);
    }

    return String.valueOf(-1);
  }


  public void startPythonScraper() {

      String search = "";
      try {
        search = getSearch();
      } catch (Exception e6) {
        System.out.println("No file selected");
      }

      String urls = getUrls();
      String month = getDate("month");
      String year = getDate("year");

      String arguments = " " + search + " " + urls + " " + month + " " + year;
      System.out.println(arguments);
      String command = "pipenv run python3 -u script.py";


      try {

        //Process process = Runtime.getRuntime().exec(command + arguments);
        Process process = new ProcessBuilder(command + arguments).start();
        //InputStreamReader isr = new InputStreamReader(process.getInputStream());
        //InputStreamReader esr = new InputStreamReader(process.getErrorStream());
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        //BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));

        /*
        Runnable runner = new Runnable() {
          @Override
          public void run(){
            try(InputStreamReader isr = new InputStreamReader(process.getInputStream())){
              int c;
              while((c = isr.read()) >=0) {
                System.out.print((char) c);
                System.out.flush();
              }
            } catch (IOException e){
              System.err.println("StreamHandler: " + e);
            }
          }
        };
        Thread stdOut = new Thread(runner);
        stdOut.start();

        */

        /*
        int c;
        while((c = isr.read()) >=0) {
          System.out.print((char) c);
          System.out.flush();
        }


        int d;
        while((d = esr.read()) >=0) {
          System.out.print((char) d);
          System.out.flush();
        }
        */

        /*
        Runnable runner = new Runnable() {
          @Override
          public void run(){
            try(BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()), 2)){
            String line;
            while ((line = reader.readLine()) != null) {
              System.out.println(line);

              if (line.matches("Tool Count: \\d")){
                System.out.println("Line matches Tool Count");
                updateProgress("Tool Number", Integer.valueOf(line.replaceAll("[^0-9]+","")));

              } else if (line.matches("Match Count: \\d")) {
                System.out.println("Line matches Match Count");
                updateProgress("Match Count", Integer.valueOf(line.replaceAll("[^0-9]+","")));

              } else if (line.matches("New Tool")) {
                System.out.println("Line matches New Tool");
                updateProgress("New Tool", 0);

              } else if (line.matches("New Match")){
                System.out.println("Line matches New Match");
                updateProgress("New Match", 0);

              } else if (line.matches("Done")) {
                System.out.println("Line matches Done");
                updateProgress("Done", 0);
              }

            }

            reader.close();
            } catch (IOException e){
              System.err.println("StreamHandler: " + e);
            }
          }
        };
        Thread stdOut = new Thread(runner);
        stdOut.start();

        Runnable runner2 = new Runnable() {
          @Override
          public void run(){

            try(BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getInputStream()), 2)){
              String line;
              while ((line = errorReader.readLine()) != null) {
                System.out.println(line);
              }
              errorReader.close();
            } catch (IOException e){
              System.err.println("StreamHandler: " + e);
            }

          }
        };
        Thread stdErr = new Thread(runner);
        stdErr.start();

        */
        /*
        String line;
        while ((line = errorReader.readLine()) != null) {
          System.out.println(line);
        }

        */

        String line;
        while ((line = reader.readLine()) != null) {
          System.out.println(line);

          if (line.matches("Tool Count: \\d")){
            System.out.println("Line matches Tool Count");
            updateProgress("Tool Number", Integer.valueOf(line.replaceAll("[^0-9]+","")));

          } else if (line.matches("Match Count: \\d")) {
            System.out.println("Line matches Match Count");
            updateProgress("Match Count", Integer.valueOf(line.replaceAll("[^0-9]+","")));

          } else if (line.matches("New Tool")) {
            System.out.println("Line matches New Tool");
            updateProgress("New Tool", 0);

          } else if (line.matches("New Match")){
            System.out.println("Line matches New Match");
            updateProgress("New Match", 0);

          } else if (line.matches("Done")) {
            System.out.println("Line matches Done");
            updateProgress("Done", 0);
          }

        }

        reader.close();
        //errorReader.close();



      } catch (Exception e) {
        e.printStackTrace();
      }
    };


  public void submit(){
    progressBar = new JProgressBar();
    progressBar.setValue(0);
    progressBar.setStringPainted(true);

    try {
      mainPageLayout.replace(l_emptyLabel3, progressBar);
      frame.revalidate();
    } catch (IllegalArgumentException except2){
      System.out.println("ProgressBar already on-screen");
    }

    Runnable runnable = new Runnable() {
      @Override
      public void run(){
        startPythonScraper();
      }
    };

    Thread thread = new Thread(runnable);
    thread.start();


    // Show filesaver

    try {
      mainPageLayout.replace(l_emptyLabel4, b_saveResults);
      frame.revalidate();
    } catch (IllegalArgumentException except2){
      System.out.println("SaveResults already on-screen");
    }



  }

  public void updateProgress (String whichTask, int value){
    int increaseIncrement = 0;
    int newStatus = 0;

    if (whichTask.matches("Tool Number")){
      System.out.println("In updateProgress for tool number set");
      scraperToolNumber = value;

    } else if (whichTask.matches("Match Count")){
      System.out.println("In updateProgress for match count set");
      scraperMatchNumber = value;

    } else if (whichTask.matches("New Tool")){
      System.out.println("In updateProgress for new tool increment");
      increaseIncrement = 100 / scraperToolNumber;
      newStatus = progressBar.getValue() + increaseIncrement;
      if (newStatus < 100){
        progressBar.setValue(newStatus);
        System.out.println("Setting new progress bar status");
      }

    } else if (whichTask.matches("New Match")){
      System.out.println("In updateProgress for new match increment");
      increaseIncrement = (100 / scraperToolNumber) / scraperMatchNumber;
      newStatus = progressBar.getValue() + increaseIncrement;
      if (newStatus < 100){
        System.out.println("Setting new progress bar status");
        progressBar.setValue(newStatus);
      }

    } else if (whichTask.matches("Done")) {
      progressBar.setValue(100);
      System.out.println("Setting new progress bar status");
    }

    frame.revalidate();
  }

  class StreamGobbler extends Thread {
    InputStream is;
    String type;

    StreamGobbler(InputStream is, String type)
    {
      this.is = is;
      this.type = type;
    }

    public void run() {
      try {
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String line=null;
        while ((line = br.readLine()) != null) System.out.println(type + ">" + line);

      } catch (IOException ioe) {
        ioe.printStackTrace();
      }
    }
  }

}
