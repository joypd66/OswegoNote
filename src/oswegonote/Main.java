//This is my original
package oswegonote;
import java.util.Arrays;
import java.util.Scanner;
import javax.xml.parsers.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;

public class Main {
    private static final int MAXNUMOFAUTHORS = 3;
    private static final int MAXNUMOFKEYWORDS = 5;
    private static final int MAXNUMOFCITATIONS = 100;
    private static final int TOTALKEYWORDLIMIT = 250;
           
    //Allows other classes to use this variable
    public static int getMaxNumOfAuthors(){
        return MAXNUMOFAUTHORS;
    }//end getMaxNumOfAuthors
    
    //Allows other classes to use this variable
    public static int getMaxNumOfKeywords(){
        return MAXNUMOFKEYWORDS;
    }//end getMaxNumOfKeyword
    
    public static int getMaxNumOfCitations(){
        return MAXNUMOFCITATIONS;
    }//end getMaxNumOfCitations
    
    public static int getTotalKeywordLimit(){
        return TOTALKEYWORDLIMIT;
    }
        
    public static void main(String[] args) throws Exception{
        //DEBUGGING:
        //Hard coded save path in order to save time
        boolean easyPath = true;
        //debugging code
        boolean debugging = false;
        //Allows you to test either "indexArray", "indexArrayList", or "indexLinked List"
        
        
        //for searches
        String[] citationResults;
        String[] keywordResults;
        int[] matchingCitations;
        
        //Scanner object to take input
        Scanner input = new Scanner(System.in);
        String xmlPath = "";
        
        System.out.print("Please enter the path and filename of the XML document. (or enter 'quit' to quit): ");
        
        //a) Parses an XML input file and populates the Citation index
        //b) Saves the citations according to its format type
        //Create a new parser factory
        SAXParserFactory factory = SAXParserFactory.newInstance();
        
        //Create a new parser
        SAXParser saxParser = factory.newSAXParser();
        XMLReader parser = saxParser.getXMLReader();
        
        //Create a new handler
        Handler handler = new Handler();
        
        //Tell the parser to use the handler
        parser.setContentHandler(handler);
        
        while(xmlPath.equals("")){
            
            xmlPath = input.nextLine().trim();
            
            if(xmlPath.equals("quit") || xmlPath.equals("q")){
                System.out.println("You have chosen to quit.");
                System.exit(1);
            }//end if statement 
            
            else if(xmlPath.equals("")){
                System.out.print("Please enter the path and filename of the XML document. (or enter 'quit' to quit): ");
            }//end else if
            
            else{
                //DEBUGGING
                if(easyPath == true){
                    xmlPath = "Citations.xml";
                    System.out.println("XML Path: " + xmlPath);
                }//end if statement
                                                
                //read and parse the document
                parser.parse(xmlPath);
            }//end else statement
        }//end while loop            
        

        //c) Prints out the citations in the index
        System.out.println("\nCitations: ");
        handler.getIndex().printCitationIndex();

        //d) Prints out the keywords 
        System.out.println("\nKeywords: ");
        handler.getIndex().printKeywords();

        //e) Sorts the citations and keywords
        System.out.println("\nSorting Citations...");
        handler.getIndex().sortCitationIndex();
        System.out.println("Citations sorted.");

        System.out.println("\nSorting Keywords...");
        handler.getIndex().sortKeywords();
        System.out.println("Keywords sorted.");

        //f) Prints out the citations
        System.out.println("\nCitations: ");
        handler.getIndex().printCitationIndex();

        //g) Prints out the keywords
        System.out.println("\nKeywords: ");
        handler.getIndex().printKeywords();

        //h) Searches for the citation “A Wavelet” by name and prints out the results to screen
        System.out.println("\nSearching by citation for the string \"A Wavelet\"...");
        citationResults = handler.getIndex().searchByName("A Wavelet");
        System.out.println("Search complete. Here are the Citation(s) that begin with the string \"A Wavelet\":");
        for(int i = 0; i < citationResults.length; i++){
            System.out.print(citationResults[i]);
        }//end for loop

        //i) Searches the index by keyword “FPGA” and prints out the results to screen
        System.out.println("\nSearching by keyword \"FPGA\"...");
        keywordResults = handler.getIndex().searchByKeyword("FPGA");
        System.out.println("Search complete. Here are the Citation(s) that use \"FPGA\" as a keyword:");
        for(int i = 0; i < keywordResults.length; i++){
            System.out.print(keywordResults[i]);
        }//end for loop

        /*
            j) Insert (in the correct position in the sorted array) the citation (a book):
            Name: “Of course you're angry: A guide to dealing with the emotions of substance abuse.”
            Authors: Gina Rosellini  & Mark Worden
            Published in : Center City, MN 
            Published by:   Hazelden.
            Pub date: 2004
            Keywords: Drugs, Sociology, Emotional Health
        */

        //Create and setup new citation.
        System.out.println("\nCreating new Citation...");
        BookCitation newBook = new BookCitation("Of course you\'re angry: A guide to dealing with the emotions of substance abuse.");
        newBook.addAuthor("Gina Rosellini");
        newBook.addAuthor("Mark Worden");
        newBook.setWherePublisher("Center City, MN");
        newBook.setPublisher("Hazelden");
        newBook.setDateOfPublication("2004");
        newBook.addKeyword("Drugs");
        newBook.addKeyword("Sociology");
        newBook.addKeyword("Emotional Health");
        System.out.println("New Citation Created.");

        //insert citation into postion
        System.out.println("\nInserting Citation into postion.");
        handler.getIndex().insertCitationInPos(newBook);
        System.out.println("Insert complete.");        

        //k) Saves the citations according to its format type
        System.out.println("\nSaving to \"" + handler.getFilePath() + "\" in \"" + handler.getIndex().getFormatType() + "\" format.");
        handler.getIndex().format(handler.getFilePath());
        System.out.println("File Saved.");

        //l) Prints out the citations in the index
        System.out.println("\nCitations: ");
        handler.getIndex().printCitationIndex();

        //m) Deletes all citations with the keyword “FPGA” (this requires a call to search and to delete)
        //m) 1) Searches for the citations that use the given keyword again.
        System.out.println("\nSearching by keyword \"FPGA\"...");
        matchingCitations = handler.getIndex().keywordBinarySearch(handler.getIndex().getKeywordIndex(),"FPGA");
        System.out.println("Search complete. Here are the Citation ID(s) that use \"FPGA\" as a keyword:");

        for(int i = 0; i < matchingCitations.length; i++){
            System.out.print(matchingCitations[i] + " ");
        }//end for loop

        //m) 2) Delete each of the citations that use the given keyword
        System.out.println("\nDeleting all citations that use \"FPGA\" as a keyword...");
        int numDeleted = 0;
        for(int i = 0; i < matchingCitations.length; i++){
            //DEBUGGING
            if(debugging == true){
                System.out.println("\nDEBUGGING: Main Class: Delete Citation Portion: ");
                System.out.println("Attempt Number: " + i);
                System.out.println("Matching Citation " + i + " = " + matchingCitations[i]);
                System.out.println("Citation that should be deleted = " + (matchingCitations[i] - numDeleted));
                
                
                //NOTE: USING A SUBSTRING. ADD FUNCTIONALITY THAT ALLOWS FOR LARGE INPUTS.
                System.out.println("Name of Citation to be deleted = " + handler.getIndex().getCitationIndex().get(matchingCitations[i] - numDeleted).getName().substring(0,4));
            }//end if

            handler.getIndex().deleteCitation(handler.getIndex().getCitationIndex().get(matchingCitations[i] - numDeleted).getName().substring(0,4));
            numDeleted++;
        }//end for loop

        //DEBUGGING:
        if(debugging == true){
            System.out.println("\nDEBUUGING: Print Citations: ");
            handler.getIndex().printCitationIndex();
        }//end if statement

        //n) Saves the citations according to its format type
        System.out.println("\nSaving to \"" + handler.getFilePath() + "\" in \"" + handler.getIndex().getFormatType() + "\" format.");
        handler.getIndex().format(handler.getFilePath());
        System.out.println("File Saved.");

        //o) Prints out the citations in the index
        System.out.println("\nCitations: ");
        handler.getIndex().printCitationIndex();

        //p) Prints out the Keywords in the index
        System.out.println("\nKeywords: ");
        handler.getIndex().printKeywords();
    }//end main method
}//end OswegoNote main class
