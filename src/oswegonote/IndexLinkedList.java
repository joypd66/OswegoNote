package oswegonote;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;

public class IndexLinkedList implements IndexInterface {
    //DEBUGGING
    private boolean debugging = false;
    
    //citationIndex - an array of Citation objects.
    private String formatType = "";
    private LinkedList<Citation> citationIndex;
    private LinkedList<Keyword> keywordIndex  = new LinkedList<>();
    
    //Internal Counts
    private int numCitationsInIndex = 0;//(number of citations actually in the index)
    private int totalNumCitations = Main.getMaxNumOfCitations();//(max that are allowed)
    //Number of unique keywords
    private int totalNumKeywords = 0;
    
    //Index Constructor
    public IndexLinkedList(int totalNumCitations){
        //this.totalNumCitations = totalNumCitations;
        citationIndex = new LinkedList<>();
    }//end Constructor
    
    public LinkedList<Keyword> getKeywordIndex(){
        return keywordIndex;
    }//end getKeywordIndex
    
    public LinkedList<Citation> getCitationIndex(){
        return citationIndex;
    }//end getCitationIndex
    
    @Override
    public void printCitationIndex(){
    //Pre-condition - the index is not empty and has a format type
    //Prints out all the citations in the index formatted according to its format type. 
    //Italicization not required
        
        if(isEmpty() == true){
            System.out.println("The citation index is empty.");
        }//end if statement
        
        else{
            String output = "";
            if(formatType.equalsIgnoreCase("IEEE")){
                for(int i = 0; i < numCitationsInIndex; i++){
                    output = citationIndex.get(i).formatIEEE();
                                                
                    //adjust for printed display
                    output = output.replaceAll("<i>", "");
                    output = output.replaceAll("</i>", "");
                    output = output.replaceAll("<br>", "\n");
                    
                    System.out.print(output);
                }//end for loop
            }//end if statement
            
            else if(formatType.equalsIgnoreCase("ACM")){
                for(int i = 0; i < numCitationsInIndex; i++){
                    output = citationIndex.get(i).formatACM();
                    
                    //adjust for printed display
                    output = output.replaceAll("<i>", "");
                    output = output.replaceAll("</i>", "");
                    output = output.replaceAll("<br>", "\n");
                    
                    System.out.print(output);
                }//end for loop
            }//end else if
            
            else if(formatType.equalsIgnoreCase("APA")){
                for(int i = 0; i < numCitationsInIndex; i++){
                    output = citationIndex.get(i).formatAPA();
                    
                    //adjust for printed display
                    output = output.replaceAll("<i>", "");
                    output = output.replaceAll("</i>", "");
                    output = output.replaceAll("<br>", "\n");
                    
                    System.out.print(output);
                }//end for loop
            }//end else if statement
            
            else{
                System.out.println("Format type is incorrect.");
            }//end else statement
        }//end else statement 
    }//end printCitationIndex
    
    @Override
    public void printKeywords(){
    // Pre-condition - there are keywords in the Index
    // Prints out all the keywords in the Index
        for(int i = 0; i < keywordIndex.size(); i++){
            if(keywordIndex.get(i) != null){
                System.out.println(keywordIndex.get(i).getKeywordName());
            }//end if statement
            
            if(totalNumKeywords == 0){
                System.out.println("The keyword index is empty.");
            }//end else statement
        }//end for loop
    }//end printKeywords
    
    @Override
    public boolean isEmpty(){
    //Returns whether or not the Citation Index is empty
        
        /*
        We could check just the first postion in the array, but
        that is a lazy approach and it could miss a null value (in the event of
        a mistake in adding/ sorting/ deleting and moving citations.
        */
        for(int i = 0; i < citationIndex.size(); i++){
            if(citationIndex.get(i) != null){
                return false;
            }//end if statement
        }//end for loop
        
        return true;
    }//end isEmpty
    
    @Override
    public boolean isFull(){
    //Returns whether or not the Citation Index is full
        
    /*
        We could check just the last postion in the array, but
        that is a lazy approach and it could miss a null value (in the event of
        a mistake in adding/ sorting/ deleting and moving citations.
    */
        for(int i = (citationIndex.size() - 1); i >= 0; i--){
            if(citationIndex.get(i) == null){
                return false;
            }//end if
            return false;
        }//end for loop
        
        return true;
    }//end isFull    
    
    
    /* 
    This method will add a citation to the citationIndex
    and call the updateID(intID) method.  This ensures that duplicate
    keywords are accounted for as we place citations into the citationIndex.
    */
    @Override
    public int addCitation(Citation inCitation){
    // Adds a citation to the Index and returns -1 if unsuccessful and the ID of the added // citation otherwise
    // The Index needs to keep track of which keywords match which citation
        if(numCitationsInIndex < Main.getMaxNumOfCitations()){
            //add the new citation
            citationIndex.add(inCitation);
            //udpate the new Citation ID
            citationIndex.get(numCitationsInIndex).updateID(numCitationsInIndex);
            //increment the numCitationsInIndex counter
            numCitationsInIndex++;
            //update the keyword list.
            this.updateKeywords(inCitation); 
            return 0;
        }//end if statement
        
        else{
            return -1;
        }//end else statement              
    }//end addCitation method
    
    @Override
    public void setFormatType(String formatType){
    // Sets the format type to "IEEE", "APA" or "ACM"
        this.formatType = formatType;
    }//end setFormatType
    
    @Override
    public String getFormatType(){
    // Returns the format type of the Index ("IEEE", "APA", "ACM")
        return formatType;
    }//end getFormatType
    
    @Override
    public void formatIEEE(String path){
        String output = "";
        String citationOutput = "";
                
        //adjust Keyword citationIDs
        for(int i = 0; i < numCitationsInIndex; i++){
            int newID = this.getCitationIndex().get(i).getID();
            int oldID = this.getCitationIndex().get(i).getOldID();
            for(int j = 0; j < this.getCitationIndex().get(i).getNumOfKeywords(); j++){
                this.getCitationIndex().get(i).getKeywordObject(j).changeID(oldID, newID); 
            }//end for loop            
        }//end for loop     

        for(int i = 0; i < numCitationsInIndex; i++){
            citationOutput = citationIndex.get(i).formatIEEE();
            output = output + citationOutput;
        }//end for loop
                
        if(path != null || path != ""){
           //DEBUGGING
           if(debugging == true){
               System.out.println("SAVED!");
           }//end if statement
           saveFile(path, output);   
        }//end if statement              
    }//end formatIEEE method
    
    @Override
    public void formatACM(String path){
        String output = "";
        String citationOutput = "";
        boolean tmp;
        
        for(int i = 0; i < numCitationsInIndex; i++){
            citationOutput = citationIndex.get(i).formatACM();
            output = output + citationOutput;
        }//end for loop
        
        if(path != null || path != ""){
            //DEBUGGING
            if(debugging == true){
               System.out.println("SAVED!");
            }//end if statement
            saveFile(path, output);
        }//end if statement
    }//end formatACM method
    
    @Override
    public void formatAPA(String path){
        String output = "";        
        String citationOutput = "";
        for(int i = 0; i < numCitationsInIndex; i++){ 
            citationOutput = citationIndex.get(i).formatAPA();
            output = output + citationOutput;
        }//end for loop
        
        if(path != null || path != ""){
            //DEBUGGING
            if(debugging == true){
               System.out.println("SAVED!");
            }//end if statement
            saveFile(path, output);
        }//end if statement
    }//end formatAPA method
    
    @Override
    public void format(String path){
    // Formats all the citations in the index in the format indicated by formatType
    // and saves it to the file indicated by path.
        
        if(formatType.equalsIgnoreCase("IEEE")){
            formatIEEE(path);
        }//end if statement        
        else if(formatType.equalsIgnoreCase("ACM")){
            formatACM(path);
        }//end else if
        else if(formatType.equalsIgnoreCase("APA")){
            formatAPA(path);
        }//end else if
        else{
            System.out.println("Format type is incorrect.");
        }//end else            
    }//end format method
    
    @Override
    public void sortCitationIndex(){
    // sorts the citations in the index alphabetically by name using the merge sort
        //DEBUGGING
        if(debugging == true){
            System.out.println("\nDEBUGGING: printKeywordIndex before sorting citations:");
            printKeywordIndex();
            System.out.println("\nDEBUGGING: printKeywordIndex after sorting citations: ");
            printKeywordIndex();
        }//end if statement
        
        mergeSort(citationIndex, numCitationsInIndex);        
    }//end sortCitationIndex method
    
    @Override
    public void sortKeywords(){
        // sorts the keywords entered in alphabetical order by name using the insertion sort
        keywordInsertionSort(keywordIndex, totalNumKeywords);
    }//end sortKeywords method
    
    @Override
    public int insertCitationInPos(Citation myCitation){
    // Pre-condition : The citations are already sorted
    // This inserts a Citation in the correct position alphabetically into the Citation list
    // This is different from addCitation which just adds a citation to the end of the
    // list. This returns -1 if unsuccessful and the ID of the added  citation otherwise
        
        //create placeholder
        int placeHolder = -1;
        
        //increment number of citations in index counter
        numCitationsInIndex++;
        
        //find where new citation belongs
        for(int i = 0; i < numCitationsInIndex; i++){
            if(myCitation.getName().compareToIgnoreCase(citationIndex.get(i).getName()) < 0){
                placeHolder = i;
                break;
            }//end if statement
            else{
                placeHolder = numCitationsInIndex;
            }//end else statement
        }//end for loop
        
        //move everything that needs to be moved
        if(placeHolder < numCitationsInIndex){
            for(int i = numCitationsInIndex-1; i > placeHolder; i--){
                //DEBUGGING
                if(debugging == true){
                    System.out.println("Number Of Citations in Index: " + numCitationsInIndex);
                    System.out.println("Citation["+(i)+"]" + " = " + (i-1));
                    System.out.println("i: " + (i));
                }//end if statement
                //add a null slot in order to work with it.
                citationIndex.add(null);
                //move everything up one.
                citationIndex.set(i, citationIndex.get(i-1));
                //this changes the ID of each of the moved citationIndex's
                citationIndex.get(i).changeID(i);
            }//end for loop
            
            //This places the newest citation
            citationIndex.set(placeHolder, myCitation);
                        
            //this updaates the ID of the newest Citation
            citationIndex.get(placeHolder).updateID(placeHolder);
            
            //this updates the keywords list to account for any new keywords added.
            this.updateKeywords(myCitation);
            
            //this sorts the keywords list again
            sortKeywords();
            
            //this updates the Keyword Citation IDs for each of the keywords belonging to any of the moved citations.
            this.refreshIDsAfterInsert(placeHolder);
            
            return placeHolder;
        }//end if statement
        
        else{
            if(citationIndex.size() != numCitationsInIndex){
                citationIndex.set(numCitationsInIndex, myCitation);
                citationIndex.get(numCitationsInIndex).updateID(numCitationsInIndex);
                sortKeywords();
                return numCitationsInIndex;
            }//end if statement
            
            else{
                sortKeywords();
                return -1;
            }//end else statement
        }//end else statement            
    }//end insertCitationInPros method   
    
    @Override
    public int deleteCitation(String name){
    // Pre-condition: This citation exists in the indexs
    // This deletes the citation from the index and returns -1 if unsuccessful and 
    // 0 otherwise
        
    /*
        1) find citation in list
        2) get citation ID
        3) set citation ID to null
        4) try to set citation to null and return 0;
        5) catch errors and return -1
        if citation is not in list return -1
    */
        //initialize an array that will hold ID for each match
        int[] matches;
        //search for citation
        matches = citationBinarySearch(citationIndex, name); 
        
        int matchCount = 0;
        for(int i = 0; i < matches.length; i++){
            //DEBUGGING:
            if(debugging == true){
                System.out.println("\nDEBUGGING: IndexArray: deleteCitation Method");
                System.out.println("Citation ID Matches: matches["+i+"] = " + "Citation ID " + matches[i]);
            }//end if statement            
            matchCount++;
        }//end for loop
        
        //DEBUGGING
        if(debugging == true){
            System.out.println("\nDEBUGGING: IndexArray Class: deleteCitation Method");
            System.out.println("Name to be searched and deleted: " + name);
            System.out.println("Matches Array Length (should be 1): " + matches.length);
            System.out.println("Number of Matches (should be 1): " + matchCount);
            System.out.println("Number of Citations currently in the index: " + numCitationsInIndex);
        }//end if statement
        
        if(matchCount > 1)
        {
            System.out.println("Deletion Unsuccessful: ");
            System.out.println("More than one citation begins with the string \"" + name + ".\"");
            System.out.println("Please be more specific.");
            return -1;            
        }//end if statement
        
        else if(matchCount == 1){
            //delete keywordIDs first
            deleteIDs(matches[0]);
            
            //delete matching citation, fix array, and return 0;
            citationIndex.remove(matches[0]);
            this.numCitationsInIndex--;
            
            for(int i = matches[0]; i < numCitationsInIndex; i++){
                refreshIDs(i+1);
            }//end for loop
            
            //DEBUGGING
            if(debugging == true){
                System.out.println("\nKeyword Index after deleteCitation Method: ");
                printKeywordIndex();
            }//end if statement
            
            changeID();
            return 0;
        }//end else if statement
        
        else{
            System.out.println("Deletion Unsuccessful: ");
            System.out.println("There are no citations that begin with the string \"" + name + ".\"");
            
            changeID();
            return -1;
        }//end else statement
    }//end deleteCitation method
    
    @Override
    public String[] searchByKeyword(String key){
    // Searches the Keywords for the key provided and returns the CITATIONS that match
    // them formatted according to the format type
    // If there are no matching Citations return "There are no citations that match that 
    // keyword"
        int[] citationIDs = this.keywordBinarySearch(keywordIndex, key);
        if(citationIDs != null){
            String output = "";        
            String[] matchingCitations = new String[citationIDs.length];

            if(formatType.equalsIgnoreCase("IEEE")){
                for(int i = 0; i < citationIDs.length; i++){
                    output = citationIndex.get(citationIDs[i]).formatIEEE();
                                       
                    //adjust for printed display
                    output = output.replaceAll("<i>", "");
                    output = output.replaceAll("</i>", "");
                    output = output.replaceAll("<br>", "\n");
                    
                    matchingCitations[i] = output;
                }//end for loop
            }//end if statement

            else if(formatType.equalsIgnoreCase("APA")){
                for(int i = 0; i < citationIDs.length; i++){
                    output = citationIndex.get(citationIDs[i]).formatAPA();
                    
                    //adjust for printed display
                    output = output.replaceAll("<i>", "");
                    output = output.replaceAll("</i>", "");
                    output = output.replaceAll("<br>", "\n");
                    
                    matchingCitations[i] = output;
                }//end for loop
            }//end else if

            else if(formatType.equalsIgnoreCase("ACM")){
                for(int i = 0; i < citationIDs.length; i++){
                    output = citationIndex.get(citationIDs[i]).formatACM();
                    
                    //adjust for printed display
                    output = output.replaceAll("<i>", "");
                    output = output.replaceAll("</i>", "");
                    output = output.replaceAll("<br>", "\n");
                    
                    matchingCitations[i] = output;
                }//end for loop
            }//end else if

            else{
                System.out.println("IndexArray | searchByKeyword | formatStyle is incorrect.");
            }//end else
            
            //DEBUGGING
            if(debugging == true){
                System.out.println("\nDEBUGGING: searchByKeyword: printKeywordIndex:");
                this.printKeywordIndex();
                System.out.println();
            }//end if statement
            
            return matchingCitations;
        }//end if statement
        
        else{
            String[] noResults = new String[1];
            noResults[0] = "There are no citations that contain a keyword of string \"" + key + "\".";
            return noResults;
        }//end else statement       
        
    }//end searchByKeyword method
    
    @Override
    public String[] searchByName(String key){
    // Searches the Citations by name for the key provided and returns the CITATIONS 
    // that match them formatted according to the format type
    // If there are no matching Citations return "There are no citations with that name"
        int[] citationIDs = citationBinarySearch(citationIndex, key);
        if(citationIDs != null){   
            String output = "";
            String[] matchingCitations = new String[citationIDs.length];
                if(formatType.equalsIgnoreCase("IEEE")){
                    for(int i = 0; i < matchingCitations.length; i++){
                        output = citationIndex.get(citationIDs[i]).formatIEEE();
                        
                        //adjust for printed display
                        output = output.replaceAll("<i>", "");
                        output = output.replaceAll("</i>", "");
                        output = output.replaceAll("<br>", "\n");
                        
                        matchingCitations[i] = output;
                    }//end for loop
                }//end if statement

                else if(formatType.equalsIgnoreCase("APA")){
                    for(int i = 0; i < matchingCitations.length; i++){
                        output = citationIndex.get(citationIDs[i]).formatAPA();
                        
                        //adjust for printed display
                        output = output.replaceAll("<i>", "");
                        output = output.replaceAll("</i>", "");
                        output = output.replaceAll("<br>", "\n");
                        
                        matchingCitations[i] = output;
                    }//end for loop
                }//end else if statement

                else if(formatType.equalsIgnoreCase("ACM")){
                    for(int i = 0; i < matchingCitations.length; i++){
                        output = citationIndex.get(citationIDs[i]).formatACM();
                        
                        //adjust for printed display
                        output = output.replaceAll("<i>", "");
                        output = output.replaceAll("</i>", "");
                        output = output.replaceAll("<br>", "\n");
                        
                        matchingCitations[i] = output;
                    }//end for loop            
                }//end else if
                
                return matchingCitations;
            }//end if statement
        else{
            String[] noResults = new String[1];
            noResults[0] = "There are no citations that begin with the string \"" + key + "\".";
            return noResults;
            }//end else statement
    }//endSearchByName method
    
    @Override
    public void clearIndex(){
    // Empties the index of all citations (and keywords)
        citationIndex = null;
        keywordIndex = null;
        numCitationsInIndex = 0;
        totalNumKeywords = 0;
    }//end clearIndex method        
                    
    /*
    ADDITIONAL METHODS:
    THIS IS WHERE MY METHODS BEGIN AND THE INDEXINTERFACE OVERRIDES TERMINATE.
    */
    
    //This deletes all deleted citationIDs from keywordArray
    private void deleteIDs(int citationID){
        for(int i = 0; i < numCitationsInIndex; i++){
            citationIndex.get(i).deleteIDs(citationID);
        }//end for loopd
        
        //Corrects and reorders citationIDs.
        correctIDs();
        
        //deletes unused keywords from keywordIndex
        deleteKeyword();
    }//end deleteIDs
    
    //reorders and corrects keywordIDs.
    private void correctIDs(){
        for(int i = 0; i < numCitationsInIndex; i++){
            citationIndex.get(i).correctIDs();
        }//end for loop
    }//end correctIDs method
    
    //This method updates all the keyword IDs after a citation has been deleted.
    private void refreshIDs(int oldID){
        for(int i = 0; i < totalNumKeywords; i++){
              keywordIndex.get(i).refreshIDs(oldID, oldID -1);
        }//end for loop        
    }//end refreshIDs
    
    //This method adjusts any of the keywordIDs that were affected by the insertion of a new Citation.
    private void refreshIDsAfterInsert(int placeholder){
        for(int i = 0; i < totalNumKeywords; i++){
            keywordIndex.get(i).refreshIDsAfterInsert(placeholder);
        }//end for loop
    }//end refreshIDs after insert.

    private void deleteKeyword(){
        boolean[] empty = new boolean[totalNumKeywords];
        for(int i = 0; i < totalNumKeywords; i++){
            empty[i] = keywordIndex.get(i).deleteKeyword();
            if(empty[i] == true){
                keywordIndex.remove(i);            
                totalNumKeywords--;
            }//end if statement
        }//end for loop
    }//delete keyword
    
    //This method updates each of the Citation IDs after a citation is deleted.
    private void changeID(){
        for(int i = 0; i < numCitationsInIndex; i++){
            if(citationIndex.get(i).getID() != i){
                citationIndex.get(i).changeID(i);
            }//end if statement
        }//end for loop
    }//end change ID method
    
    //Updates the citations that are attributed to each keyword.
    private void updateKeywords(Citation inCitation){
        /*
        For every keyword in inCitation check to see if the keyword is already in keywordIndex[]
            using the findKeywordInList method.
        */
        for(int i = 0; i < inCitation.getNumOfKeywords(); i++){
            int keywordLocation = findKeywordInList(inCitation.getKeywordObject(i));
            
            if(keywordLocation == -1){
                /*
                If the keyword is not already in keywordIndex[] add the keyword to the 
                next availible location.
                */
                if(totalNumKeywords < Main.getTotalKeywordLimit()){
                    keywordIndex.add(inCitation.getKeywordObject(i));
                    totalNumKeywords++;
                }//end if statement
                
                //If there are no more availible locations print an error message.     
                else{
                    System.out.print("Error: The total keyword limit has been reached.");
                }//end else statement
            }//end if statement
            
            /*
                If the keyword is already in the keywordIndex[] add the citation ID to the
                existing keyword object within the keywordIndex using addID() method of the 
                Keyword class.  Also, redirect the pointer from the each of the subsequent duplicate 
                keywordIndex's to the primary keywordIndex.
            */
            else if (keywordLocation >= 0){
                //if the keyword is in the keywordIndex we add the new ID.
                keywordIndex.get(keywordLocation).addID(inCitation.getID());
                //then we set the keywordObject in the citation to make it point to the correct keyword
                inCitation.setKeywordObject(keywordIndex.get(keywordLocation), i);
            }//end else if statement
            
            else{
                System.out.println("Error: Index | updateKeywords | keywordLocation < -1");
            }
        }//end for loop        
    }//end updateKeywords method
    
    //This method searchs through the keywordIndex and looks for the first keyword that matches inKeyword.
    private int findKeywordInList(Keyword inKeyword){
        boolean keywordFound = false;
        int keywordIndexNum = -2;        
        int i;
        for(i = 0; i < totalNumKeywords; i++){
            //This checks to see if any of the keywords match
            if(inKeyword.getKeywordName().equalsIgnoreCase(keywordIndex.get(i).getKeywordName())){
                //DEBUGGING
                if(debugging == true){
                    System.out.println("findKeywordInList method debugging 1: ");
                    System.out.println("Keyword Name: \"" +  inKeyword.getKeywordName() + 
                    "\" | From Citation: " + (inKeyword.getID(0) + 1) + " was first located in " + 
                    (keywordIndex.get(i).getID(0) + 1) + " and already placed in keywordIndex[" + i + "]" );
                }//end if statement
                keywordFound = true;
                keywordIndexNum = i;
                
                break;
            }//end if statement              
        }//end for loop
        
        /*
        If there is a match return the index of that keyword that is in the keywordIndex array.
        to be updated.
        */        
        if(keywordFound == true){
            return keywordIndexNum;
        }//end if statement
               
        //if there is no match in the keywordIndex array return -1;
        else{
            //DEBUGGING
            if(debugging == true){
                System.out.println("findKeywordInList method debugging 2: ");
                System.out.println("Keyword Name: \"" +  inKeyword.getKeywordName() + "\" | From Citation: " + (inKeyword.getID(0) + 1) + " was placed in keywordIndex[" + i + "]");
            }//end if statement
                return -1;
        }//end else statement       
            
    }//end findKeywordInList
    
    private void mergeSort(LinkedList<Citation> citationArray, int arrayLength){
    // Sorts the values array using 
    //the merge sort algorithm.
      if (citationArray.size() > 1)
      { 
        LinkedList<Citation> left = new LinkedList<Citation>();                
        LinkedList<Citation> right = new LinkedList<Citation>();
        
        for(int i = 0; i < (arrayLength/2); i++){
            //This is assigning the object referance from citationArray[i] to left[i]
            left.add(citationArray.get(i));
        }//end for loop
        
        for(int i = 0; i < (arrayLength - (arrayLength/2)); i++){
            //This is assigning the referance from citationArray[i] to left[i]
            right.add(citationArray.get(i + left.size()));
        }//end for loop
        
        mergeSort(left, left.size());
        mergeSort(right, right.size());        
        //This merges the array in order after it is broken into pieces.
        merge(citationArray, left, right, left.get(0).getID(), right.get(0).getID());
      }//end if statement
    }//end mergeSort
    
    private void merge(LinkedList<Citation> citationArray, LinkedList<Citation> left, LinkedList<Citation> right, int leftOldID, int rightOldID){
        //We are sorting the citation array.
        int cursorLeft = 0;
        int cursorRight = 0;
        int cursorEnd = 0;
                
        while(cursorLeft < left.size() & cursorRight < right.size()){            
            if(left.get(cursorLeft).getName().compareToIgnoreCase(right.get(cursorRight).getName()) < 0){
                //DEBUGGING
                if(debugging == true){
                    System.out.print("merge method debugging 1: ");
                    int oldID = left.get(cursorLeft).getID();
                    System.out.println("Citation: " + left.get(cursorLeft).getName() + " oldID: " + leftOldID + " newID: " + cursorEnd);
                }//end if statement
                
                //this places each of the citations into the citation array in order.
                citationArray.set(cursorEnd, left.get(cursorLeft));
               
                /*
                This updates the citationID to match the location in the citationArray
                It also changes the relavent ID for each of the keywords in the Citation keywordArray
                */
                citationArray.get(cursorEnd).changeID(citationArray.get(cursorEnd).getID(), cursorEnd);

                cursorLeft++;
                cursorEnd++;
            }//end if statement

            else{
                //DEBUGGING
                if(debugging == true){
                    System.out.print("merge method debugging 2: ");
                    int oldID = right.get(cursorRight).getID();
                    System.out.println("Citation: " + right.get(cursorRight).getName() + " oldID: " + rightOldID + " newID: " + cursorEnd);
                }//end if statement
           
                //this places each of the citations into the citation array in order.
                citationArray.set(cursorEnd, right.get(cursorRight));
                
                /*
                    This updates the citationID to match the location in the citationArray
                    It also changes the relavent ID for each of the keywords in the Citation keywordArray
                */
                citationArray.get(cursorEnd).changeID(citationArray.get(cursorEnd).getID(), cursorEnd);             
            
                cursorRight++;
                cursorEnd++;                
            }//end else statement  
        }//end if statement
        
        while(cursorLeft < left.size()){
            //DEBUGGING
            if(debugging == true){
                System.out.print("merge method debugging 3: ");
                int oldID = left.get(cursorLeft).getID();
                System.out.println("Citation: " + left.get(cursorLeft).getName() + " oldID: " + leftOldID + " newID: " + cursorEnd);
            }//end if statement

            //this places each of the citations into the citation array in order.
            citationArray.set(cursorEnd, left.get(cursorLeft));
            
            /*
                This updates the citationID to match the location in the citationArray
                It also changes the relavent ID for each of the keywords in the Citation keywordArray
            */
            citationArray.get(cursorEnd).changeID(citationArray.get(cursorEnd).getID(), cursorEnd);
            
            cursorLeft++;
            cursorEnd++;
        }//end while loop
        
        while(cursorRight < right.size()){
            //DEBUGGING
            if(debugging == true){
                System.out.print("merge method debugging 4: ");
                int oldID = right.get(cursorRight).getID();
                System.out.println("Citation: " + right.get(cursorRight).getName() + " oldID: " + rightOldID + " newID: " + cursorEnd);
            }//if statement

       
            //this places each of the citations into the citation array in order.
            citationArray.set(cursorEnd, right.get(cursorRight));
            
            /*
                This updates the citationID to match the location in the citationArray
                It also changes the relavent ID for each of the keywords in the Citation keywordArray
            */
            citationArray.get(cursorEnd).changeID(citationArray.get(cursorEnd).getID(), cursorEnd);
            
            cursorRight++;
            cursorEnd++;
        }//end while loop        
    }//end merge method
    
    //Preform a binarySearch on the citationIndex and return an integer array of matching
    private int[] citationBinarySearch(LinkedList<Citation> index, String key){        
            //Keyword[] sortedArray = keywordInsertionSort(keywordIndex, keywordIndex.length);
            //Find the values we need.
            int first = 0;
            
            int last = numCitationsInIndex;
            int[] notFound = null;
            int[] results = new int[numCitationsInIndex];
            
            //initialize all results to -1;
            for(int i = 0; i < results.length; i++){
                results[i] = -1;
            }//end for loop
            
            int numOfResults = 0;
            boolean[] match = new boolean[numCitationsInIndex];
            
            //run to check every citation in index.
            //Search for matches, and ignore ones that have already been found.
            while(first < last){
                int mid = ((first + last)/2);
                    //key is located before the mid point
                    if(key.compareToIgnoreCase(index.get(mid).getName().substring(0, key.length())) < 0){
                        last = mid;
                    }//end if statement
                    
                    //key is located after the midpoint
                    else if(key.compareToIgnoreCase(index.get(mid).getName().substring(0, key.length())) > 0){
                        first = mid + 1;
                    }//end else if
                    
                     //key is located at the midpoints, flag each match found.
                    else{
                        int tempMid = mid;
                        
                        while(tempMid < numCitationsInIndex && key.compareToIgnoreCase(index.get(tempMid).getName().substring(0, key.length())) == 0){
                            if(match[tempMid] == false){
                                match[tempMid] = true;

                                if(match[tempMid] == true){
                                    results[numOfResults] = tempMid;
                                    numOfResults++;
                                    tempMid++;
                                }//end if statement
                            }//end if statement     
                        }//end while loop
                        
                        break;
                    }//end else  
            }//end while loop
                        
            //create an array that holds the citationID of each of the matches
            if(numOfResults > 0){
                int[] matches = new int[numOfResults];
                
                for(int i = 0; i < matches.length; i++){
                    if(results[i] != -1){
                       matches[i] = results[i];                             
                    }//end if statement
                }//end for loop
                
                return matches;
            }//end if statement
            
            //if no matches are found
            else{
               return notFound; 
            }//end else            
    }//end
    
    private static void mergeSortInt(int[] a) {
        if (a.length >= 2) {
            int[] left = new int[a.length / 2];
            int[] right = new int[a.length-(a.length / 2)];

            for (int i = 0; i < left.length; i++){
                left[i] = a[i];
            }//end for loop
            
            for (int i = 0; i < right.length; i++){
                right[i] = a[i + a.length / 2];
            }//end for loop

            mergeSortInt(left);
            mergeSortInt(right);

            mergeInt(a, left, right);
        }//end if statement
    }//end mergeSortInt
    
    //This is used to sort the Keyword citationIDs[] arrays.
    private static void mergeInt(int[] result, int[] left, int[] right) {
        int i1 = 0;
        int i2 = 0;
        
        for (int i = 0; i < result.length; i++) {
            if (i2 >= right.length || (i1 < left.length && left[i1] < right[i2])){
                result[i] = left[i1];
                i1++;
            }//end if statment
            
            else{
                result[i] = right[i2];
                i2++;
            }//end else statement
        }//end for loop
    }//end mergeInt
    
    //Preform a binary search on the keywordIndex
    public int[] keywordBinarySearch(LinkedList<Keyword> index, String key){                   
            //sort the array and store in in a generic object array.
            //Keyword[] sortedArray = keywordInsertionSort(keywordIndex, keywordIndex.length);
            
            //Find the values we need.
            int first = 0;
            int last = totalNumKeywords;
            int[] notFound = null;
                        
            while(first < last){
                int mid = ((first + last)/2); 
                
               
                //key is located before the mid point
                if(key.compareToIgnoreCase(index.get(mid).getKeywordName()) < 0){
                    last = mid;
                }//end if statement
                
                //key is located after the midpoint
                else if(key.compareToIgnoreCase(index.get(mid).getKeywordName()) > 0){
                    first = mid + 1;
                }//end else if
                
                //key is located at the midpoints
                else{
                    int[] results = new int[index.get(mid).getNumCitationIDs()];
                    for(int i = 0; i < index.get(mid).getNumCitationIDs(); i++){
                        results[i] = index.get(mid).getCitationIDs()[i];                        
                    }//end for loop
                    
                    //mergeSort the list of citationIDs before returning to main
                    mergeSortInt(results);
                    
                    //return the results to the user.
                    return results;
                }//end else                       
            }//end while loop
            
            return notFound;
    }//end binarySearch method
    
    //This will do an insertionSort on the keywordIndex to prepare it for binary searching.
    private LinkedList<Keyword> keywordInsertionSort(LinkedList<Keyword> sortedKeywordIndex, int sortedIndexLength){
        int i, j;
        int iMin;
        
        for(j = 0; j < (sortedIndexLength -1); j++){
            iMin = j;
            
            for(i = j + 1; i < sortedIndexLength; i++){
                if(sortedKeywordIndex.get(i).getKeywordName().compareToIgnoreCase(sortedKeywordIndex.get(iMin).getKeywordName()) < 0){
                    iMin = i;
                }//end if statement
            }//end for loop   
            
            if(iMin != j){
                Keyword temp = sortedKeywordIndex.get(j);
                sortedKeywordIndex.set(j, sortedKeywordIndex.get(iMin));
                sortedKeywordIndex.set(iMin, temp);
            }//end if statement
        }//end for loop 
        
        return sortedKeywordIndex;
    }//end insertionSort method
        
    private int getNumCitationsInIndex(){
        return numCitationsInIndex;
    }//end getter
    
    private int getTotalNumKeywords(){
        return totalNumKeywords;
    }//end getTotalNumCitations    
    
    /*
    DEBUGGING
    The printCitationInfo method is used to print each citationID and the name associated with it.
        It calls the printKeywordInfoMethod in order show each of the citations keywords and the 
        citationIDs associated with those keywords.
    These test methods can be used to ensure that the arrays are being populated correctly before AND
    after the merge.
    */
    private void printCitationInfo(){
        for(int i = 0; i < numCitationsInIndex; i++){
            System.out.println("CitationIndex " + (i) + ": "  + "CitationID[" + citationIndex.get(i).getID() + "]: " + citationIndex.get(i).getName());
            printKeywordInfo(i);
        }//end for loop     
    }//end printCitationInfo
    
    /*
    DEBUGGING
    The printKeywordInfo method will print the keyword along with each of the citationIDs associated with it.
    */
    private void printKeywordInfo(int indexNum){
        for(int i = 0; i < citationIndex.get(indexNum).getNumOfKeywords(); i++){            
            System.out.print("Keyword " + (i) + ": " + citationIndex.get(indexNum).getKeyword(i) + " | CitationIDs: ");
            
            for(int j = 0; j < citationIndex.get(indexNum).getKeywordObject(i).getNumCitationIDs(); j++){
                System.out.print(" " + (citationIndex.get(indexNum).getKeywordObject(i).getID(j)));
            }//end for loop      
            System.out.println();
        }//end for loop
        System.out.println();
    }//end printKeywordInfo
    
    /*
    DEBUGGING
    The printKeywordIndex method prints the name of a keyword and the keywordIndex that is has.
    */
    private void printKeywordIndex(){
        for(int i = 0; i < keywordIndex.size(); i++){
            /*
            If keywordIndex[i] has a Keyword object in it print the keyword name 
            and the IDs associated with it.  This proves that, before the merge,
            the citationArray was populated correctly.  The display keywordInfo
            function must not be working correctly.
            */
            if(keywordIndex.get(i) != null){
                System.out.println("Keyword " + i + ": " + keywordIndex.get(i).getKeywordName() + " keywordIndex: " + Arrays.toString(keywordIndex.get(i).getCitationIDs()));             
            }//end if statement            
        }//end for loop
    }//end printCitationIndex
    
    //This formats titles for publications and publishers
    public String titleFormat(String string){
        string.trim();
        //This splits an input based on blank space and then adds the word to a String[]
        String[] words = string.split(" ");
        
        for(int i = 0; i < words.length; i++){
            //This runs if a word that is a special case and should not be capitalized
            if(i != 0 && i != words.length - 1 && 
                    (words[i].equalsIgnoreCase("a") || words[i].equalsIgnoreCase("an") || words[i].equalsIgnoreCase("the") ||
                    words[i].equalsIgnoreCase("for") || words[i].equalsIgnoreCase("and") || words[i].equalsIgnoreCase("nor") ||
                    words[i].equalsIgnoreCase("but") || words[i].equalsIgnoreCase("or") || words[i].equalsIgnoreCase("yet") ||
                    words[i].equalsIgnoreCase("so") || words[i].equalsIgnoreCase("at") || words[i].equalsIgnoreCase("around")||
                    words[i].equalsIgnoreCase("by") || words[i].equalsIgnoreCase("but") || words[i].equalsIgnoreCase("after") ||
                    words[i].equalsIgnoreCase("along") || words[i].equalsIgnoreCase("for") || words[i].equalsIgnoreCase("from") ||
                    words[i].equalsIgnoreCase("of") || words[i].equalsIgnoreCase("on") || words[i].equalsIgnoreCase("to") ||
                    words[i].equalsIgnoreCase("with") || words[i].equalsIgnoreCase("without"))
                    ){
                    words[i] = words[i].substring(0).toLowerCase();
            }//end if statement
            
            //This runs if the word is not in the special cases list above
            else
                words[i] = words[i].substring(0,1).toUpperCase() + words[i].substring(1);
        }//end for loop  
               
        string = convertStringArrayToString(words);
        return string;
    }//end titleFormat
    
    //This converts a String[] to a string
    private String convertStringArrayToString(String[] stringArray){
        String string = "";
        StringBuilder builder = new StringBuilder();
        
        for(String str : stringArray){
            builder.append(str + " ");
            string = builder.toString().trim();
        }//end for loop
        
        return string;
    }//end convertStringArrayToString
    
    //This formats places for the citation
    public String placeFormat(String string){
        String[] splitPlace;
        String stringReturn = "";
        
        string = string.replaceAll(",", " ");
        string= string.replaceAll("\\s+", " ");
        splitPlace = string.split(" ");
        
        if(splitPlace.length == 1){
            splitPlace[0] = splitPlace[0].substring(0,1).toUpperCase() + splitPlace[0].substring(1).toLowerCase();
            string = splitPlace[0];
        }//end if statement
        
        else if(splitPlace.length == 2){
            splitPlace[0] = splitPlace[0].substring(0,1).toUpperCase() + splitPlace[0].substring(1).toLowerCase();
            splitPlace[1] = splitPlace[1].substring(0,1).toUpperCase() + splitPlace[1].substring(1).toLowerCase();
            string = splitPlace[0] + ", " + splitPlace[1];
        }//end else if
        
        else if (splitPlace.length > 2){
            String[] splitString = string.split(" ");
            
            splitPlace[0] = splitPlace[0].substring(0,1).toUpperCase() + splitPlace[0].substring(1).toLowerCase();
            splitPlace[1] = splitPlace[1].substring(0,1).toUpperCase() + splitPlace[1].substring(1).toLowerCase();
            string = splitPlace[0] + ", " + splitPlace[1];
            
            for(int i = 2; i < splitString.length; i++){
                splitString[i] = splitString[i].substring(0,1).toUpperCase() + splitString[i].substring(1).toLowerCase();
                stringReturn = stringReturn + " " + splitString[i];
            }//end for loop
            
            string = string + stringReturn;
        }//end else
        
        return string;   
    }//end placeFormat
    
    //This formats general words that are not titles/places/or authors
    public String wordFormat(String string){
        String[] splitString;
        String stringReturn = "";
        splitString = string.split(" ");
        
        for(int i = 0; i < splitString.length; i++){
            splitString[i] = splitString[i].substring(0,1).toUpperCase() + splitString[i].substring(1);            
            stringReturn = stringReturn + " " + splitString[i];
        }//end for loop
        
        return stringReturn.trim();
    }//end wordFormat
    
    //This formats author names for the citation    
    public String authorFormat(String string){
        String fullName = string;
        String[] splitString;
        
        splitString = fullName.split("\\s+");
        
        for(int i = 0; i < splitString.length; i++){
            splitString[i] = splitString[i].substring(0,1).toUpperCase() + splitString[i].substring(1).toLowerCase();
        }//end for loop
            
        
        //if one name is provided
        if(splitString.length == 1){           
            fullName = splitString[0];
        }//end if statement
        
        //if two names are provided
        else if(splitString.length == 2){            
            fullName = splitString[0] + " " + splitString[1];            
        }//end else if statement
        
        //if three or more names is provided
        else{                     
            fullName = splitString[0] + " " + splitString[1] + " " + splitString[splitString.length - 1];
         }//end else statement
        return fullName;
    }//end authorFormat
    
    //This returns the author
    private String getAuthor(int indexNum, int authorNum){
        return citationIndex.get(indexNum).getAuthor(authorNum);
    }//end getAuthor
    
    //This returns the keyword
    private String getKeyword(int indexNum, int keywordNum){
        return citationIndex.get(indexNum).getKeyword(keywordNum);
    }//end getKeyword 
    
    //Ensures the Correct date format from user.
    private String dateFormat(String type, String userInput){
        String[] splitInput;
        int year = -1;
        splitInput = userInput.split("\\s+");
        
        if(type.equalsIgnoreCase("journal/magazine") && splitInput.length == 2 && splitInput[1].length() == 4){
            try{
                year = Integer.parseInt(splitInput[1]);
                splitInput[0] = splitInput[0].replaceAll(",", "");
                splitInput[0] = splitInput[0].substring(0,1).toUpperCase() + splitInput[0].substring(1).toLowerCase();
                return splitInput[0] + ", " + year;
            }catch(NumberFormatException e){
                return "invalid";
            }//end try catch
        }//end journal if statement
        
        else if((type.equalsIgnoreCase("book") || type.equalsIgnoreCase("conference")) && splitInput.length == 1 && splitInput[0].length() == 4){
            try{
                year = Integer.parseInt(splitInput[0]);
                return splitInput[0];
            }catch(NumberFormatException e){
                return "invalid";
            }//end try/catch
        }//end book else if
        
        else{
            return "invalid";
        }//end else
    }//end dateFormat
    
    //This saves the citations in an html file.
    private boolean saveFile(String path, String output){
        try{
            PrintWriter out = new PrintWriter(new FileWriter(path));
            out.println(output);
            out.flush();
            out.close();
            return true;
        }catch(IOException e){
            System.out.print("Please enter a valid name and path for the html file. (or enter 'quit' to quit): ");
            return false;
        }//end try/catch block
    }//end saveFile
}//end IndexLinkedList class