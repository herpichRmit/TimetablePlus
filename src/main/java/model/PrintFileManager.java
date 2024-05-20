package model;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Used to export file from program, will export it to the downloads folder if possible
 */
public class PrintFileManager  {

  public static void printDownloads(ArrayList<String> input) {
    try {
	  String home = System.getProperty("user.home");
	  String fileName = "enrollment_information";

	  File in = new File(new File(home, "Downloads"), fileName + ".txt");

	  FileWriter file = new FileWriter(in);
	  PrintWriter writer = new PrintWriter(file);

      for (String element : input) {
    	  writer.write(element);
      }

      writer.close();

    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();

    }
  }
  
  // adapted from https://www.w3schools.com/java/java_files_create.asp
  public static void printLocalFolder(ArrayList<String> input) {
	    try {
	      File myObj = new File("enrollment_information.txt");
	      
	      myObj.createNewFile();
	      
	    } catch (IOException e) {
	      System.out.println("An error occurred.");
	      e.printStackTrace();
	    }
	  }
  
}