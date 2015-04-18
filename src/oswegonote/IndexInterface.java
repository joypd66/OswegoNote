package oswegonote;

public interface IndexInterface {
// This index accepts citations and allows itself to
// be searched by citation name or by keyword
// The Index needs to keep track of which keywords match which citation
// Must have a formatType string
    
    public void printCitationIndex();
    // Pre-condition - the index is not empty and has a format type
    //Prints out all the citations in the index formatted according to its format type. //Italicization not required

    public void printKeywords();
    // Pre-condition - there are keywords in the Index
    // Prints out all the keywords in the Index

    public boolean isEmpty();
    //Returns whether or not the Citation Index is empty

    public boolean isFull();
    //Returns whether or not the Citation Index is full

    public int addCitation(Citation inCitation);
    // Adds a citation to the Index and returns -1 if unsuccessful and the ID of the added // citation otherwise
    // The Index needs to keep track of which keywords match which citation

    public void setFormatType(String s);
    // Sets the format type to "IEEE", "APA" or "ACM"

    public String getFormatType();
    // Returns the format type of the Index ("IEEE", "APA", "ACM")

    public void formatIEEE(String path);
    // Formats all the citations in the index in IEEE format and saves it to the file
    // indicated by path

    public void formatACM(String path);
    // Formats all the citations in the index in ACM format and saves it to the file
    // indicated by path

    public void formatAPA(String path);
    // Formats all the citations in the index in APA format and saves it to the file
    // indicated by path

    public void format(String path);
    // Formats all the citations in the index in the format indicated by formatType
    // and saves it to the file indicated by path

    public void sortCitationIndex();
    // sorts the citations in the index alphabetically by name using the merge sort

    public void sortKeywords();
    // sorts the keywords entered in alphabetical order by name using the insertion sort

    public int insertCitationInPos(Citation myCitation);
    // Pre-condition : The citations are already sorted
    //This inserts a Citation in the correct position alphabetically into the Citation //list
    // This is different from addCitation which just adds a citation to the end of the
    //list. This returns -1 if unsuccessful and the ID of the added  citation otherwise

    public int deleteCitation(String name);
    // Pre-condition: This citation exists in the index
    // This deletes the citation from the index and returns -1 if unsuccessful and 
    // 0 otherwise

    public String[] searchByKeyword( String key);
    // Searches the Keywords for the key provided and returns the CITATIONS that match
    // them formatted according to the format type
    // If there are no matching Citations return "There are no citations that match that 
    //keyword"

    public String[] searchByName( String key);
    // Searches the Citations by name for the key provided and returns the CITATIONS 
    // that match
    // them formatted according to the format type
    // If there are no matching Citations return "There are no citations with that name"

    public void clearIndex();
    // Empties the index of all citations (and keywords)
}//end IndexInterface method