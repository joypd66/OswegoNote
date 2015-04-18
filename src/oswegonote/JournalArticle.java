package oswegonote;

public class JournalArticle extends Citation {
    String titleOfJournal = "";
    int volNumber = -1;
    int issueNumber = -1;
    int startPage = -1;
    int endPage = -1;
    String publicationDate = ""; //ask user for Month, YYYY.  
    
    public JournalArticle(String name){
        super(name);
    }//end Journal Article Constructor
    
    public String getTitleOfJournal(){
        return titleOfJournal;
    }//end getter method.
    
    public void setTitleOfJournal(String titleOfJournal){
        this.titleOfJournal = titleOfJournal;
    }//end setter method.
    
    public int getVolNumber(){
        return volNumber;
    }//end getter method.
    
    public void setVolNumber(int volNumber){
        this.volNumber = volNumber;
    }//end setter method
    
    public int getIssueNumber(){
        return issueNumber;
    }//end getter
    
    public void setIssueNumber(int issueNumber){
        this.issueNumber = issueNumber;
    }//end setter method
    
    public int getStartPage(){
        return startPage;
    }//end getter method
    
    public void setStartPage(int startPage){
        this.startPage = startPage;
    }//end setter method
    
    public int getEndPage(){
        return endPage;
    }//end getter method
    
    public void setEndPage(int endPage){
        this.endPage = endPage;
    }//end setter method
    
    public String getPublicationDate(){
        return publicationDate;
    }//end getter method
    
    public void setPublicationDate(String publicationDate){
        this.publicationDate = publicationDate;
    }//end setter method

    @Override
    public String formatIEEE(){
        String citationOutput = "";
        String authorsOutput = "";
        String name = getName();
        String publicationDate = getPublicationDate();
        String journal = getTitleOfJournal();
        String[] splitDate;
           
        int volNumber = getVolNumber();
        int issueNumber = getIssueNumber();
        int startPage = getStartPage();
        int endPage = getEndPage();

        //The author array is created to be the size of the maxNumber of authors
        String[] authors = new String[Main.getMaxNumOfAuthors()];

        //internal authorCount
        int authorCount = 0;
        
        //for loop to getAuthors
        for(int j = 0; j < Main.getMaxNumOfAuthors(); j++){
            if(getAuthor(j) != null){
                authors[j] = getAuthor(j);
                authorCount++;
            }//end if statement
        }//end for loop

        //Put authors into IEEE FORMAT                  
        for(int j = 0; j < Main.getMaxNumOfAuthors(); j++){
            String fullName = "";
            String[] splitString;
            
            if(authors[j] != null){
                fullName = authors[j];               

                splitString = fullName.split("\\s+");

                for(int i = 0; i < splitString.length; i++){
                    splitString[i] = splitString[i].substring(0,1).toUpperCase() + splitString[i].substring(1).toLowerCase();
                }//end for loop                
                              
                //if the authors last name is not the final name given.
                if(splitString.length == 1){           
                    fullName = splitString[0];
                }//end if statement

                //if two names are provided
                else if(splitString.length == 2){            
                    fullName = splitString[0].substring(0, 1) + ". " + splitString[1];           
                }//end else if statement

                //if three or more names is provided
                else if(splitString.length == 3){                     
                    fullName = splitString[0].substring(0, 1) + ". " + splitString[1].substring(0,1) + ". " + splitString[splitString.length - 1];
                }//end else statement   
            }//end if statement   
            authors[j] = fullName;
        }//end for loop 
                      
        for(int j = 0; j < Main.getMaxNumOfAuthors(); j++){
            if(!authors[j].equals("")){                    
                if(j < authorCount - 2){
                    authorsOutput = authorsOutput + authors[j] + ", ";                    
                }//end if statement              
                
                else if(j == authorCount - 1 && authorCount > 1){
                    authorsOutput = authorsOutput + ", and " + authors[j];
                }
               
                else{
                    authorsOutput = authorsOutput + authors[j];                                   
                }//end else statement                     
            }//end if statement
        }//end for loop
        
        //The following will put the date in IEEE format
        splitDate = publicationDate.split(" ");
        splitDate[0] = splitDate[0].substring(0, 1).toUpperCase() + splitDate[0].substring(1,3).toLowerCase() + ".";
                    
        //This ensures correct output if endPgae and startPage are equal
        if(endPage > startPage){
        citationOutput = "[" + getID() + "] " + authorsOutput + ", \"" + name + ",\" <i>" + titleOfJournal + "</i>, vol. " + 
                volNumber + ", no. " + issueNumber + ", pp. " + startPage + "-" + endPage + ", " + splitDate[0] + " " + splitDate[1] + ".<br>";
        }
        
        else if(endPage == startPage){
        citationOutput = "[" + getID() + "] " + authorsOutput + ", \"" + name + ",\" <i>" + titleOfJournal + "</i>, vol. " + 
                volNumber + ", no. " + issueNumber + ", pp. " + startPage + ", " + splitDate[0] + " " + splitDate[1] + ".<br>";
        }
        
        return citationOutput;       
    }//end formatIEEE

    @Override
    public String formatAPA(){
          String citationOutput = "";
        String authorsOutput = "";
        String name = getName();
        String publicationDate = getPublicationDate();
        String journal = getTitleOfJournal();
        String[] splitDate;
           
        int volNumber = getVolNumber();
        int issueNumber = getIssueNumber();
        int startPage = getStartPage();
        int endPage = getEndPage();

        //The author array is created to be the size of the maxNumber of authors
        String[] authors = new String[Main.getMaxNumOfAuthors()];

        //internal authorCount
        int authorCount = 0;
        
        //for loop to getAuthors
        for(int j = 0; j < Main.getMaxNumOfAuthors(); j++){
            if(getAuthor(j) != null){
                authors[j] = getAuthor(j);
                authorCount++;
            }//end if statement
        }//end for loop

        //Put authors into APA FORMAT                  
        for(int j = 0; j < Main.getMaxNumOfAuthors(); j++){
            String fullName = "";
            String[] splitString;
            
            if(authors[j] != null){
                fullName = authors[j];               

                splitString = fullName.split("\\s+");

                for(int i = 0; i < splitString.length; i++){
                    splitString[i] = splitString[i].substring(0,1).toUpperCase() + splitString[i].substring(1).toLowerCase();
                }//end for loop
                
                //if the authors last name is the final name given.
                if(splitString.length == 1 && j == authorCount - 1){           
                    fullName = splitString[0] + ".";
                }//end if statement
                
                //if the authors last name is not the final name given.
                else if(splitString.length == 1){           
                    fullName = splitString[0];
                }//end if statement

                //if two names are provided
                else if(splitString.length == 2){            
                    fullName = splitString[1] + ", " + splitString[0].substring(0, 1) + ".";           
                }//end else if statement

                //if three or more names is provided
                else if(splitString.length == 3){                     
                    fullName = splitString[splitString.length - 1] + ", " + splitString[0].substring(0, 1) + ". " + splitString[1].substring(0,1) + ".";
                }//end else statement   
            }//end if statement   
            authors[j] = fullName;
        }//end for loop 
                      
        for(int j = 0; j < Main.getMaxNumOfAuthors(); j++){
            if(!authors[j].equals("")){                    
                if(j < authorCount - 2){
                    authorsOutput = authorsOutput + authors[j] + ", ";                    
                }//end if statement              
                
                else if(j == authorCount - 1 && authorCount > 1){
                    authorsOutput = authorsOutput + ", & " + authors[j];
                }
               
                else{
                    authorsOutput = authorsOutput + authors[j];                                   
                }//end else statement                     
            }//end if statement
        }//end for loop.
        
        //The following will put the date in APA format
        splitDate = publicationDate.split(" ");
        splitDate[0] = splitDate[0].substring(0, 1).toUpperCase() + splitDate[0].substring(1,3).toLowerCase() + ".";
                    
        //This ensures correct output if endPgae and startPage are equal
        if(endPage > startPage){
        citationOutput = authorsOutput + " (" + splitDate[1] + "). " + name + ". <i>" + titleOfJournal + ", " + 
                volNumber + "</i>(" + issueNumber + "), " + startPage + "-" + endPage + ".<br>";
        }
        
        else if(endPage == startPage){
        citationOutput = authorsOutput + " (" + splitDate[1] + "). " + name + ". <i>" + titleOfJournal + ", " + 
                volNumber + "</i>(" + issueNumber + "), " + startPage + ".<br>";
        }//end else if
        
        return citationOutput; 
    }//end formatAPA

    @Override
    public String formatACM(){
        String citationOutput = "";
        String authorsOutput = "";
        String name = getName();
        String publicationDate = getPublicationDate();
        String journal = getTitleOfJournal();
        String[] splitDate;
           
        int volNumber = getVolNumber();
        int issueNumber = getIssueNumber();
        int startPage = getStartPage();
        int endPage = getEndPage();

        //The author array is created to be the size of the maxNumber of authors
        String[] authors = new String[Main.getMaxNumOfAuthors()];

        //internal authorCount
        int authorCount = 0;
        
        //for loop to getAuthors
        for(int j = 0; j < Main.getMaxNumOfAuthors(); j++){
            if(getAuthor(j) != null){
                authors[j] = getAuthor(j);
                authorCount++;
            }//end if statement
        }//end for loop

        //Put authors into ACM FORMAT                  
        for(int j = 0; j < Main.getMaxNumOfAuthors(); j++){
            String fullName = "";
            String[] splitString;
            
            if(authors[j] != null){
                fullName = authors[j];               

                splitString = fullName.split("\\s+");

                for(int i = 0; i < splitString.length; i++){
                    splitString[i] = splitString[i].substring(0,1).toUpperCase() + splitString[i].substring(1).toLowerCase();
                }//end for loop
                
                //if the authors last name is the final name given.
                if(splitString.length == 1 && j == authorCount - 1){           
                    fullName = splitString[0] + ".";
                }//end if statement
                
                //if the authors last name is not the final name given.
                else if(splitString.length == 1){           
                    fullName = splitString[0];
                }//end if statement

                //if two names are provided
                else if(splitString.length == 2){            
                    fullName = splitString[1] + ", " + splitString[0].substring(0, 1) + ".";           
                }//end else if statement

                //if three or more names is provided
                else if(splitString.length == 3){                     
                    fullName = splitString[splitString.length - 1] + ", " + splitString[0].substring(0, 1) + ". " + splitString[1].substring(0,1) + ".";
                }//end else statement   
            }//end if statement   
            authors[j] = fullName;
        }//end for loop 
                      
        for(int j = 0; j < Main.getMaxNumOfAuthors(); j++){
            if(!authors[j].equals("")){                    
                if(j < authorCount - 2){
                    authorsOutput = authorsOutput + authors[j] + ", ";                    
                }//end if statement              
                
                else if(j == authorCount - 1 && authorCount > 1){
                    authorsOutput = authorsOutput + " and " + authors[j];
                }
               
                else{
                    authorsOutput = authorsOutput + authors[j];                                   
                }//end else statement                     
            }//end if statement
        }//end for loop.
        
        //The following will put the date in ACM format
        splitDate = publicationDate.split(" ");
        splitDate[0] = splitDate[0].substring(0, 1).toUpperCase() + splitDate[0].substring(1,3).toLowerCase() + ".";
                    
        //This ensures correct output if endPgae and startPage are equal
        if(endPage > startPage){
            citationOutput = authorsOutput + " " + name + ". <i>" + titleOfJournal + "</i>, " + 
                volNumber + " (" + issueNumber + "). " + startPage + "-" + endPage + ".<br>";
        }//end if
        
        else if(endPage == startPage){
            citationOutput = authorsOutput + " " + name + ". <i>" + titleOfJournal + "</i>, " + 
                volNumber + " (" + issueNumber + "). " + startPage + ".<br>";
        }//end else if        
        return citationOutput;        
    }//end format ACM
}//end JournalArticle
