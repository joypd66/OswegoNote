package oswegonote;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class Handler extends DefaultHandler {
    //DEBUGGING
    private boolean debugging = false;
    
    //INDEX
    private IndexArrayList index;
    
    //Citation Objects
    private BookCitation newBook;
    private JournalArticle newJournalArticle;
    private ConfProceedings newConfProc;
    private Unpublished newUnpublished;
    
    //Useful Strings
    private String tmpValue;          
    private String formatStyle;
    private String filePath;
    private String citationType = "";
    
    //SAX calls this method when it finds a start tag
    @Override
    public void startElement(String namespaceURI, String localName, String qualifiedName, Attributes attributes) throws SAXException
    {   
        //creates a new index
        if(qualifiedName.equalsIgnoreCase("Index")){
            
            index = new IndexArrayList (Main.getMaxNumOfCitations());
            
            //DEBUGGING
            if(debugging == true){
                System.out.println("startElement: " + qualifiedName);
            }//end if statement            
        }//end if statement
        
        if(qualifiedName.equalsIgnoreCase("Book"))        
        {
            newBook = new BookCitation("name");
            citationType = "book";
            //DEBUGGING
            if(debugging == true){
                System.out.println("startElement: " + qualifiedName);
            }//end if statement                              
        }//end if statement
                  
        if(qualifiedName.equalsIgnoreCase("JournalArticle")){
            newJournalArticle = new JournalArticle("name");
            citationType = "journalArticle";
            //DEBUGGING
            if(debugging == true){
                System.out.println("startElement: " + qualifiedName);  
            }//end if statement            
        }//end if statement
        
        if(qualifiedName.equalsIgnoreCase("ConferenceProceedings")){
            newConfProc = new ConfProceedings("name");
            citationType = "confProc";
            //DEBUGGING
            if(debugging == true){
                System.out.println("startElement: " + qualifiedName);
            }//end if statement            
        }//end if statement
        
        if(qualifiedName.equalsIgnoreCase("Unpublished")){
            newUnpublished = new Unpublished("name");
            citationType = "unpublished";
            //DEBUGGING
            if(debugging == true){
                System.out.println("startElement: " + qualifiedName);
            }//end if statement
            
        }//end if statement  s   
                
        if(qualifiedName.equalsIgnoreCase("Pages")){
            String startPageString = attributes.getValue("StartPage");
            String endPageString = attributes.getValue("EndPage");
            
            if(citationType.equalsIgnoreCase("confProc")){                      
                try{
                    int startPageInt = Integer.parseInt(startPageString);
                    int endPageInt = Integer.parseInt(endPageString);
                    newConfProc.setStartPage(startPageInt);
                    newConfProc.setEndPage(endPageInt);
                    //DEBUGGING
                    if(debugging == true){
                        System.out.println("endPageInt: " + endPageInt);
                        System.out.println("startPageInt: " + startPageInt);
                    }//end if statement                    
                }catch(NumberFormatException e){
                    System.out.println("Error | Handler | startElement | pages | Conference Proceedings");
                }//end try/catch                 
            }//end if statement
            
                        
            if(citationType.equalsIgnoreCase("journalArticle")){
                try{
                    int startPageInt = Integer.parseInt(startPageString);
                    int endPageInt = Integer.parseInt(endPageString);
                    newJournalArticle.setStartPage(startPageInt);
                    newJournalArticle.setEndPage(endPageInt);
                    //DEBUGGING
                    if(debugging == true){
                        System.out.println("endPageInt: " + endPageInt);
                        System.out.println("startPageInt: " + startPageInt);
                    }//end if statement            
                }catch(NumberFormatException e){
                    System.out.println("Error | Handler | startElement | pages | JournalArticle");
                }//end try/catch                 
            }//end if statement
            
            //DEBUGGING
            if(debugging == true){
                System.out.println("startElement: " + qualifiedName);    
            }//end if statement            
        }//end if statement              
    }//end startElement method
    
    @Override
    public void endElement(String namspaceURI, String localName, String qualifiedName) throws SAXException
    {
        if(qualifiedName.equalsIgnoreCase("Index")){
            //DEBUGGING
            if(debugging == true){
                System.out.println("endElement: " + qualifiedName);    
            }//end if statement            
        }//end if statement
        
        if(qualifiedName.equalsIgnoreCase("Book"))        
        {   
            int temp = index.addCitation(newBook);
            if(temp == -1){
                System.out.println("HANDLER | END ELEMENT | BOOK: There was an error adding this citation: " + newBook);
            }//end if statement
            citationType = "";
            //DEBUGGING
            if(debugging == true){
                System.out.println("endElement: " + qualifiedName); 
            }//end if statement            
        }//end if statement
                  
        if(qualifiedName.equalsIgnoreCase("journalArticle"))
        {
            int temp = index.addCitation(newJournalArticle);
            if(temp == -1){
                System.out.println("HANDLER | END ELEMENT | JOURNAL ARTICLE: There was an error adding this citation: " + newJournalArticle);
            }//end if statement
            citationType = "";
            //DEBUGGING
            if(debugging == true){
                System.out.println("endElement: " + qualifiedName); 
            }//end if statement            
        }//end if statement
        
        if(qualifiedName.equalsIgnoreCase("ConferenceProceedings"))
        {
            int temp = index.addCitation(newConfProc);
            if(temp == -1){
                System.out.println("HANDLER | END ELEMENT | CONFERENCE PROCEEDINGS: There was an error adding this citation: " + newConfProc);
            }//end if statement
            citationType = "";
            //DEBUGGING
            if(debugging == true){
                System.out.println("endElement: " + qualifiedName); 
            }//end if statement            
        }//end if statement
        
        if(qualifiedName.equalsIgnoreCase("Unpublished"))
        {
            int temp = index.addCitation(newUnpublished);
            if(temp == -1){
                System.out.println("HANDLER | END ELEMENT | UNPUBLISHED: There was an error adding this citation: " + newUnpublished);
            }//end if statement
            citationType = "";
            //DEBUGGING
            if(debugging == true){
                System.out.println("endElement: " + qualifiedName);
            }//end if statement             
        }//end if statement
        
        //Book
        if(citationType.equalsIgnoreCase("book")){
            if(qualifiedName.equalsIgnoreCase("name"))
            {
                tmpValue = index.titleFormat(tmpValue);
                newBook.setName(tmpValue);
                //DEBUGGING
                if(debugging == true){
                    System.out.println("endElement: " + qualifiedName); 
                }//end if statement                
            }//end if statement

            if(qualifiedName.equalsIgnoreCase("Author")){
                tmpValue = index.authorFormat(tmpValue);
                newBook.addAuthor(tmpValue);
                //DEBUGGING
                if(debugging == true){
                    System.out.println("endElement: " + qualifiedName);
                }//end if statement                 
            }//end if statement

            if(qualifiedName.equalsIgnoreCase("Keyword"))
            {
                tmpValue = index.wordFormat(tmpValue);
                newBook.addKeyword(tmpValue);
                //DEBUGGING
                if(debugging == true){
                    System.out.println("endElement: " + qualifiedName);
                }//end if statement                     
            }//end if statement

            if(qualifiedName.equalsIgnoreCase("publisher"))
            {
                tmpValue = index.titleFormat(tmpValue);
                newBook.setPublisher(tmpValue);
                //DEBUGGING
                if(debugging == true){
                    System.out.println("endElement: " + qualifiedName); 
                }//end if statement
            }//end if statement

            if(qualifiedName.equalsIgnoreCase("PublicationDate"))
            {
                newBook.setDateOfPublication(tmpValue);
                //DEBUGGING
                if(debugging == true){
                    System.out.println("endElement: " + qualifiedName);  
                }//end if statement      
            }//end if statement

            if(qualifiedName.equalsIgnoreCase("PublicationPlace"))
            {   
                tmpValue = index.placeFormat(tmpValue);
                newBook.setWherePublisher(tmpValue);
                //DEBUGGING
                if(debugging == true){
                    System.out.println("endElement: " + qualifiedName);
                }//end if statement        
            }//end if statement
        }//end if statement
    
        //Journal Article
        if(citationType.equalsIgnoreCase("journalArticle")){
            if(qualifiedName.equalsIgnoreCase("name"))
            {
                tmpValue = index.titleFormat(tmpValue);
                newJournalArticle.setName(tmpValue);
                //DEBUGGING
                if(debugging == true){
                    System.out.println("endElement: " + qualifiedName); 
                }//end if statement
            }//end if statement

            if(qualifiedName.equalsIgnoreCase("Author")){
                tmpValue = index.authorFormat(tmpValue);
                newJournalArticle.addAuthor(tmpValue);
                //DEBUGGING
                if(debugging == true){
                    System.out.println("endElement: " + qualifiedName);     
                }//end if statement
            }//end if statement

            if(qualifiedName.equalsIgnoreCase("Keyword"))
            {
                tmpValue = index.wordFormat(tmpValue);
                newJournalArticle.addKeyword(tmpValue);
                //DEBUGGING
                if(debugging == true){
                    System.out.println("endElement: " + qualifiedName);
                }//end if statement        
            }//end if statement
            
            if(qualifiedName.equalsIgnoreCase("TitleOfJournal")){
                tmpValue = index.titleFormat(tmpValue);
                newJournalArticle.setTitleOfJournal(tmpValue);
                //DEBUGGING
                if(debugging == true){
                    System.out.println("endElement: " + qualifiedName);  
                }//end if statement        
            }//end if statement

            if(qualifiedName.equalsIgnoreCase("VolNumber")){
                int volNumber;
                try{
                    volNumber = Integer.parseInt(tmpValue);
                    newJournalArticle.setVolNumber(volNumber);
                }catch(NumberFormatException e){
                    System.out.println("Error | endElement | qualifiedName.equalsIgnoreCase(\"volNumber\")");
                }//end try/catch block
                //DEBUGGING
                if(debugging == true){
                    System.out.println("endElement: " + qualifiedName);
                }//end if statement 
            }//end if statement

            if(qualifiedName.equalsIgnoreCase("IssueNumber")){
                int issueNumber;
                try{
                   issueNumber = Integer.parseInt(tmpValue);
                   newJournalArticle.setIssueNumber(issueNumber);
                }catch(NumberFormatException e){
                    System.out.println("Error | endElement | qualifiedName.equalsIgnoreCase(\"issueNumber\")");
                }//end try/catch block
                //DEBUGGING
                if(debugging == true){
                    System.out.println("endElement: " + qualifiedName);
                }//end if statement 
            }//end if statement
            
            if(qualifiedName.equalsIgnoreCase("PublicationDate"))
            {
                newJournalArticle.setPublicationDate(tmpValue);
                //DEBUGGING
                if(debugging == true){
                    System.out.println("endElement: " + qualifiedName);
                }//end if statement        
            }//end if statement
                
        }//end if statement
        
        //Conference Proceedings
        if(citationType.equalsIgnoreCase("confProc")){
            if(qualifiedName.equalsIgnoreCase("name"))
            {
                tmpValue = index.titleFormat(tmpValue);
                newConfProc.setName(tmpValue);
                //DEBUGGING
                if(debugging == true){
                    System.out.println("endElement: " + qualifiedName); 
                }//end if statement               
            }//end if statement

            if(qualifiedName.equalsIgnoreCase("Author")){
                tmpValue = index.authorFormat(tmpValue);
                newConfProc.addAuthor(tmpValue);
                //DEBUGGING
                if(debugging == true){
                    System.out.println("endElement: " + qualifiedName); 
                }//end if statement
            }//end if statement

            if(qualifiedName.equalsIgnoreCase("Keyword"))
            {
                tmpValue = index.wordFormat(tmpValue);
                newConfProc.addKeyword(tmpValue);
                //DEBUGGING
                if(debugging ==  true){
                    System.out.println("endElement: " + qualifiedName);
                }//end if statement                        
            }//end if statement
            
            if(qualifiedName.equalsIgnoreCase("ConferenceLocation")){
                tmpValue = index.placeFormat(tmpValue);
                newConfProc.setLocationOfConference(tmpValue);
                //DEBUGGING
                if(debugging == true){
                    System.out.println("endElement: " + qualifiedName); 
                }//end if statement                      
            }//end if statement

            if(qualifiedName.equalsIgnoreCase("TitleOfConferenceProceeding")){
                //tmpValue = index.titleFormat(tmpValue);
                newConfProc.setNameOfConf(tmpValue);
                //DEGUGGING
                if(debugging == true){
                    System.out.println("endElement: " + qualifiedName);     
                }//end if statement
            }//end if statement

            if(qualifiedName.equalsIgnoreCase("ConferenceYear")){
                newConfProc.setConfYear(tmpValue);
                //DEBUGGING
                if(debugging == true){
                    System.out.println("endElement: " + qualifiedName);  
                }//end if statement
            }//end if statement

            if(qualifiedName.equalsIgnoreCase("Editor")){
                tmpValue = index.wordFormat(tmpValue);
                newConfProc.setEditor(tmpValue);
                //DEBUGGING
                if(debugging == true){
                    System.out.println("endElement: " + qualifiedName);        
                }//end if statement                
            }//end if statement
            
            if(qualifiedName.equalsIgnoreCase("publisher"))
            {
                tmpValue = index.titleFormat(tmpValue);
                newConfProc.setPublisher(tmpValue);
                //DEBUGGING
                if(debugging == true){
                    System.out.println("endElement: " + qualifiedName); 
                }//end if statment                
            }//end if statement
        }//end if statement
        
        //Unpublished
        if(citationType.equalsIgnoreCase("unpublished")){
            if(qualifiedName.equalsIgnoreCase("name"))
            {
                tmpValue = index.titleFormat(tmpValue);
                newUnpublished.setName(tmpValue);
                //DEBUGGING
                if(debugging == true){
                System.out.println("endElement: " + qualifiedName); 
                }//end if statement
            }//end if statement

            if(qualifiedName.equalsIgnoreCase("Author")){
                tmpValue = index.authorFormat(tmpValue);
                newUnpublished.addAuthor(tmpValue);
                //DEBUGGING
                if(debugging == true){
                    System.out.println("endElement: " + qualifiedName); 
                }//end if statement
            }//end if statement

            if(qualifiedName.equalsIgnoreCase("Keyword"))
            {
                tmpValue = index.wordFormat(tmpValue);
                newUnpublished.addKeyword(tmpValue);
                //DEBUGGING
                if(debugging == true){
                    System.out.println("endElement: " + qualifiedName);
                }//end if statement
            }//end if statement            
        }//end if statement
        
        //formatting and saving
        if(qualifiedName.equalsIgnoreCase("FormattingStyle")){
            formatStyle = tmpValue;
            index.setFormatType(tmpValue);
            //DEBUGGING
            if(debugging == true){
                System.out.println("endElement: " + qualifiedName); 
            }//end if statement
        }//end if statement
        
        if(qualifiedName.equalsIgnoreCase("FilePath")){
            filePath = tmpValue;
            //DEBUGGING
            if(debugging == true){
                System.out.println("endElement: " + qualifiedName); 
            }//end if statement
        }//end if statement
    }//end endElement method
    
    public void characters(char ch[], int start, int length) throws SAXException
    {
        String newString = new String(ch, start, length);
        
        if(newString.trim().replaceAll(" ", "").replaceAll("\n", "").length() > 0)
        {
            tmpValue = newString;
            //DEBUGGING
            if(debugging == true){
                System.out.println("characters: \"" + newString + "\"");
            }//end inf statement
        }//end if statement
    }//end characters method
    
    public IndexArrayList getIndex(){
        return index;
    }//end getIndex
    
    public String getFormatType(){
        return formatStyle;
    }//end getFormatType
    
    public String getFilePath(){
        return filePath;
    }//end getFilePath method
}//end Handler
