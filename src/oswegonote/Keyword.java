package oswegonote;
import java.util.Arrays;

/*
Each keyword is getting its own ID array in this class.  The purpose of the ID array
is to track which citations are using this keyword.  IF a keyword is linked to over 100
citations an error message should be displayed.
*/
public class Keyword {
    private String name;
    private int[] citationIDs = new int[Main.getMaxNumOfCitations()];
    private int numOfCitations = 0;
            
    //internal counter
    private int numCitationIDs = 0;
    
    //Create a Keyword constructor
    public Keyword(String name, int ID){
        /*
        The first time a keyword is used, a new keyword object will be created for it.
        --> The keyword object will create a citationID[100].
        --> citationID[0] assigned citationID of the Citation that used that particular keyword first.
        */
        Arrays.fill(citationIDs, -1);
        this.name = name;
        this.citationIDs[0] = ID; 
        this.numCitationIDs++;
    }//end Keyword constructor
    
    //Returns internal count for numCitationIDs
    public int getNumCitationIDs(){
        return numCitationIDs;
    }//end getCitationIDs
    
    /*
    adds and ID to the citationID[]
    */
    public void addID(int ID){
        if(numCitationIDs < citationIDs.length){
            citationIDs[numCitationIDs] = ID;
            //oldCitationIDs[numCitationIDs] = ID;
            numCitationIDs++;
        }//end if statement
        
        else{
            System.out.println("ERROR: Keyword class - addID method.");
        }//end else statement
    }//end addID method 
   
    /*
    This method will update the ID for each keyword, and then add 1 to the
    numOfCitations that called the keyword.  This will count the number of citations
    that use each keywrod.
    */
    public void updateID(int newID){
        citationIDs[numOfCitations] = newID;
        //oldCitationIDs[numOfCitations] = newID;
        numOfCitations++;
    }//end updateID
    
    //checks to see if citationID array is empty in order to delete keywords.
    public boolean deleteKeyword(){
        if(citationIDs[0] == -1){
            return true;
        }//end if statement
        else{
            return false;
        }//end else statement
    }//end deleteKeywords
    
    //This deletes Citation IDs of all deleted Citations
    public void deleteIDs(int citationID){
        for(int i = 0; i < citationIDs.length; i++){
            if(citationIDs[i] == -1){
                break;
            }//end 
            else if(citationIDs[i] == citationID){
                citationIDs[i] = -2;
            }//end if statement
        }//end for loop        
    }//end deleteIDs
    
    //reorders and corrects ids
    public void correctIDs(){
        for(int i = 0; i < Main.getMaxNumOfCitations(); i++){
            if(citationIDs[i] == -1){
                break;
            }//end if statement
            
            else if(citationIDs[i] == -2){
                for(int j = i; j < Main.getMaxNumOfCitations()-1; j++){
                    if(citationIDs[j+1] == -1){
                        citationIDs[j] = citationIDs[j+1];
                        break;
                    }//end if statement
                    else{
                        citationIDs[j] = citationIDs[j+1];
                    }//end else
                }//end for loop
            }//end if statement
        }//end for loop
    }//end correctIDs method
    
    //This updates IDs after citations are deleted.
    public void refreshIDs(int oldID, int newID){
        for(int i = 0; i < citationIDs.length; i++){
            if(citationIDs[i] == -1){
                break;
            }//end if statement
            
            else if(citationIDs[i] == oldID){
                citationIDs[i] = newID;
            }//end else if statement
        }//end for loop
    }//end refreshIDs method
    
    //This method adjusts any of the keywordIDs that were affected by the insertion of a new Citation.
    public void refreshIDsAfterInsert(int placeholder){
        for(int i = 0; i < citationIDs.length; i++){
            if(citationIDs[i] == -1){
                break;
            }//end if statement
            
            else if(citationIDs[i] > placeholder){
                citationIDs[i] = (citationIDs[i] + 1);
            }//end else if
        }//end for loop
    }//end refreshIDsAfterInsert method
    /*
    This method will search the ID array of keyword for 
    oldID and replace it with newID.  It flags changed citation IDs
    in order to prevent the possibility of changing them once again.
    */    
    public void changeID(int oldID, int newID){
        boolean changed = false;    
        for(int i = 0; i < numCitationIDs; i++){
            if(citationIDs[i] == oldID && changed == false){                
               citationIDs[i] = newID;
               changed = true;
            }//end if statement      
            else if(citationIDs[i] == -1){
                break;
            }//end else if
        }//end for loop
    }//addID
    
    public int getID(int i){
        return citationIDs[i];
    }//end getID 
    
    /*
    This method will display the citation IDS for any given keyword object.
    --> The keyword objects just so happen to share their names with the keywords themselves.
    */
    public int[] getCitationIDs(){
        return this.citationIDs;
    }//end getCitationIDs getter method
    
    /*
    gets the Keyword name
    */
    public String getKeywordName(){
        return this.name;
    }//end getKeywordNames getter method
}//end Keyword class
