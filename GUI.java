import java.io.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.*;
import java.awt.event.*;

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
  private JLabel label;
  private static JLabel l_userFile;
  private JLabel l_urlExclusions;
  private JLabel l_dateRange;
  private JLabel l_dateRangeMessage;
  private JLabel l_resultsReturned;
  private JLabel l_userFileMessage;
  private JFrame frame;
  private JTextField urlExclusions;
  private static JFormattedTextField dateField;
  private JButton b_save;
  private JButton b_saveDate;
  private JButton b_submit;
  private JFileChooser fileChooser1;
  private String enteredURLs;

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

    // Build Labels
    label = new JLabel("Number of clicks: 0");
    l_userFile = new JLabel("no file selected");
    l_urlExclusions = new JLabel("Please enter the URLs for search exclusion:");
    l_dateRange = new JLabel("Date Range Selector Here:");
    l_dateRangeMessage = new JLabel("Date to begin your search:");
    l_resultsReturned = new JLabel("Your Results Here:");
    l_userFileMessage = new JLabel("Upload NTI tool list here:");

    // Build Text Field
    urlExclusions = new JTextField();

    // Build Date Picker
    DateFormatter dateFormatter = new DateFormatter(new SimpleDateFormat("dd/MM/yyyy"));
    DefaultFormatterFactory dateFormatterFactory = new DefaultFormatterFactory(dateFormatter, new DateFormatter(), dateFormatter);

    dateField = new JFormattedTextField(dateFormatterFactory);

    // Build a GroupLayout
    GroupLayout mainPageLayout = new GroupLayout(frame.getContentPane());
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
        .addComponent(dateField))
      .addGroup(mainPageLayout.createParallelGroup()
        .addComponent(b_open)
        .addComponent(b_save)
        .addComponent(b_saveDate))
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
      .addComponent(b_submit)
      .addComponent(l_resultsReturned)
    );

    dateField.setValue(new Date());
    frame.pack();
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
      fileChooser1.setDialogTitle("Select a .txt file");

      FileNameExtensionFilter restrict = new FileNameExtensionFilter("Only .txt files please", "txt");
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
    }

  }

  public String getSearch() {
    String search = fileChooser1.getSelectedFile().getAbsolutePath();
    return search;
  }

  public String getUrls() {
    String urls = enteredURLs.replaceAll("\\s","");
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



  public void submit(){
    try {
      String search = getSearch();
    } catch (Exception e) {
      System.out.println("No file selected");
    }
    String urls = getUrls();
    String month = getDate("month");
    String year = getDate("year");
/*
    try{
      Process process = Runtime.getRuntime().exec("pipenv shell && python3 script.py");
    } catch (Exception e) {
      e.printStackTrace();
    }
*/

  }

}
