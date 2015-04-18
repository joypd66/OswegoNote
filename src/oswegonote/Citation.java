package oswegonote;

abstract public class Citation {
    private int ID;
    private String name;
    private int oldID;
   
    private String[] authors = new String[Main.getMaxNumOfAuthors()];
    private Keyword[] keywordArray = new Keyword[Main.getMaxNumOfKeywords()];
    private boolean changed = false;
    /*
        Each citation has a keywordArray that holds up to 5 Keyword objects
    --> Each Keyword Object has a citationIDs array that holds up to 100 citation IDs
        --> Each citationID in the citationIDs array represents a citation that used that keyword.
        --> This poses a problem.  Right now each keyword object is seperetly owned by each citation.
        --> Thus, a new citation sharing an identical keyword will just create a new keyword object
                that contains it's own citationIDs array.
        --> Furthermore, even if one object uses the same keyword 5 times, the addKeyword method
            ensures that the new Keyword object is independent from the existing keyWord objects.
        --> This will be addressed later in the project.
    */
    
    //internal counts
    private int numOfAuthors = 0;
    private int numOfKeywords = 0;
    
    //Citation object constructor
    public Citation(String name){
        this.ID = -1;
        this.name = name;
    }//end Citation constructor.
    

    /*
    updateID will update the ID of each of the Keyword objects that are stored
    in the keywordArray[].  It will end by updating the Citation objects ID.  This 
    ensures that each of the Keyword objects has the same ID as the Citation object 
    that called it.
    */
    public void updateID(int ID){
        /*
        This will update the ID of each of the Keywords in the Citation.
        */
        for(int i = 0; i < keywordArray.length; i++){
            if(keywordArray[i] == null)
                break;
            else    
                keywordArray[i].updateID(ID);
        }//end for loop
        this.ID = ID;
        this.oldID = ID;
    }//end updateID method  
    
    //ChangeID for the citation:
    public void changeID(int newID){
        this.ID = newID;
    }//end changeID
    
    //Change ID for citations and for each of their keywords after a merge.
    public void changeID(int oldID, int newID){
        this.ID = newID;
        
        //this changes the ID associated with each of the keywords
        for(int i = 0; i < getNumOfKeywords(); i++){
            keywordArray[i].changeID(oldID, newID);
        }//end for loop
    }//end changeID
    
    //Delete Citations IDs of deleted Citations
    public void deleteIDs(int citationID){
        for(int i = 0; i < keywordArray.length; i++){
            if(keywordArray[i] == null){
                break;
            }//end if statement
            else{
                keywordArray[i].deleteIDs(citationID);
            }//end else statement
        }//end for loop
    }//end deleteIDs
    
    //corrects and reorders keyword IDs
    public void correctIDs(){
        for(int i = 0; i < keywordArray.length; i++){
            if(keywordArray[i] == null){
                break;
            }//end if statement
            
            else{
                keywordArray[i].correctIDs();
            }//end else
        }//end for loop
    }//end correctIDs method
    //refreshIDs
    public void refreshIDs(int oldID, int newID){
        for(int i = 0; i < keywordArray.length; i++){
            if(keywordArray[i] == null){
                break;
            }//end if statement
            else{
                keywordArray[i].refreshIDs(oldID, newID);
            }//end else statement
        }//end for loop
    }//end refreshIDs method
    /*
    Getters and setters for all of the variables in the class.
    */
    public int getID(){
        return this.ID;
    }//end getID
    
    public int getOldID(){
        return this.oldID;
    }//end getOldID
    
    public int getNumOfKeywords(){
        return numOfKeywords;
    }//end getNumOfKeywords
     
    public void setName(String name){
        this.name = name;
    }//end setName setter method
    
    public String getName(){
        return this.name;
    }//end getName getter method    
    
    /*
    This will add one author to the Citation authors[].  It is acting as a
    setter method.
    */
    public void addAuthor(String author){
        if(numOfAuthors < Main.getMaxNumOfAuthors()){
            authors[numOfAuthors] = author;
            numOfAuthors++;
        }//end if method
        else{
            System.out.println("ERROR: Citation class - addAuthor method.");
        }
    }//end addAuthor setter method
    
    //get author method
    public String getAuthor(int authorNum){
            return authors[authorNum]; 
    }//end getAuthor getter method.
    
    /*
    This will add one Keyword object to the keywordArray[].  It is acting as
    a setter method.
    */    
    public void addKeyword(String keywordIn){
        /*
        This method is creating a new Keyword object for each keyword that is 
        entered in the citation.  The Keyword object is then stored in the 
        keywordArray and the numOfKeywords counter increases.  
        */
        
        /*
        Using keywordArray.length here could be troublesome.  The keywordArray is
        automatically initialized at the maxNumOfKeyword value from main.  If the
        user enters 3 keywords, and the max is 5, 3 Keyword objects will be created
        and stored in the keywordArray but there will be 5 slots in the array.  The
        final two slots will be null.  In this case, if a keywordArray[x].XXXXX command
        is called it will return a null pointer error.
            --> this is handled by ensuring that the calling class only calls for the 
                number of keyWords that exist.
            --> Redundancy could be added by comparing directly to that value with a 
                get method in main.  (Can a get method in main return a local variable
                that only exists in the main method?)
        */
        if(numOfKeywords < keywordArray.length){
            keywordArray[numOfKeywords] = new Keyword(keywordIn, this.ID);           
            numOfKeywords++;
        }//end if statement.
        
        //returns an error in the event that there are too many keywords being added
        else{
            System.out.println("ERROR: Citation class|addKeyword method|Too Many Keywords.");
        }//end else statement
    }//end addKeyword
    
    //getter method for keyword name
    public String getKeyword(int numKeyword){
        return keywordArray[numKeyword].getKeywordName();
    }//end getKeyword getter
    
      //This redirects the pointer of a keyword[i] to a new keywordObject   
    public void setKeywordObject(Keyword masterKeyword, int i){
        keywordArray[i] = masterKeyword;
    }//end setKeyword
    
    //getter method for keyword object in array.
    public Keyword getKeywordObject(int numKeyword){
        return keywordArray[numKeyword];
    }//end getKeywordObject    
        
    //getter class for keyword index number
    public int getKeywordIndexNum(int numOfKeyword, int numOfID){
        return keywordArray[numOfKeyword].getID(numOfID);
    }//end getKeywordIndexNum getter (used for testing).
    
    public Keyword[] getKeywordArray(){
        return keywordArray;
    }//end getKeywordindex
    
  
    public boolean getChanged(){
        return changed;
    }//end getChanged
    
    abstract public String formatIEEE();
    abstract public String formatAPA();
    abstract public String formatACM();
}//end class Citation
