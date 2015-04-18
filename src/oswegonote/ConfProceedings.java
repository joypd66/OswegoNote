package oswegonote;

public class ConfProceedings extends Citation{
    String nameOfConf = "";
    String publisher = "";
    String locationOfConference = "";
    String editor = "";
    int startPage = -1;
    int endPage = -1;
    int confYear = -1;
    
    public ConfProceedings(String name) {
        super(name);
    }//end ConfProceedings Constructor
    
    public String getNameOfConf(){
        return nameOfConf;
    }//end getter method
    
    public void setNameOfConf(String nameOfConf){
        this.nameOfConf = nameOfConf;
    }//end setter method
    
    public void setPublisher(String publisher){
        this.publisher = publisher;
    }//end publisher
    
    public String getPublisher(){
        return publisher;
    }
    
    public String getLocationOfConference(){
        return locationOfConference;
    }//end getter method
    
    public void setLocationOfConference(String locationOfConference){
        this.locationOfConference = locationOfConference;
    }//end setter
    
    public String getEditor(){
        return editor;
    }//end getter
    
    public void setEditor(String editor){
        this.editor = editor;
    }//end setter
    
    public int getStartPage(){
        return startPage;
    }//end getter
    
    public void setStartPage(int startPage){
        this.startPage = startPage;
    }//end setter
    
    public int getEndPage(){
        return endPage;
    }//end getter
    
    public void setEndPage(int endPage){
        this.endPage = endPage;
    }//end setter
    
    public int getConfYear(){
        return confYear;
    }//end getter
    
    public void setConfYear(String confYear){
        try{
            this.confYear = Integer.parseInt(confYear);
        }catch(NumberFormatException e){
            System.out.println("confYear incorrect format");
        }//end try/catch
    }//end setter       
        
    @Override
    public String formatIEEE(){
        String citationOutput = "";
        String authorsOutput = "";
        
        String name = getName();        
        String nameOfConf = getNameOfConf();
        String locationOfConference = getLocationOfConference();
        String editor = getEditor();        
        int confYear = getConfYear();        
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

        citationOutput = "[" + getID() + "] " + authorsOutput + ", \"" + name + ",\" " + "in " + "<i>" + nameOfConf + "</i>, " +  locationOfConference + ", " + confYear + ".<br>";
        return citationOutput;        
    }//end formatIEEE
    
    @Override
    public String formatAPA(){
        String citationOutput = "";
        String authorsOutput = "";
        
        String name = getName();        
        String nameOfConf = getNameOfConf();
        String publisher = getPublisher();
        String locationOfConference = getLocationOfConference();
        String editor = getEditor();    
        String[] splitEditor;
        int startPage = getStartPage();
        int endPage = getEndPage();
        int confYear = getConfYear();
        
        splitEditor = editor.split(" ");
        
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
        
        if(startPage != endPage){
            citationOutput = authorsOutput + " " + "(" + confYear + "). " + name + ". in " + 
                    splitEditor[0].substring(0,1).toUpperCase() + ". " + splitEditor[1].substring(0,1).toUpperCase() + 
                    splitEditor[1].substring(1).toLowerCase() + " (Ed.)" + "<i>" + nameOfConf + "</i> (pp. " +
                    startPage + "-" + endPage + "). " + locationOfConference + ": " + publisher + ".<br>";                    
        }//end if
        
        else if(startPage == endPage){
            citationOutput = authorsOutput + " " + "(" + confYear + "). " + name + ". in " + 
                    splitEditor[0].substring(0,1).toUpperCase() + ". " + splitEditor[1].substring(0,1).toUpperCase() + 
                    splitEditor[1].substring(1).toLowerCase() + " (Ed.)" + "<i>" + nameOfConf + "</i> (pp. " + 
                    startPage + "). " +locationOfConference + ": " + publisher + ".<br>";
                    
        }//end else if
        
        return citationOutput;           
    }//end formatAPA
    
    @Override
    public String formatACM(){
        String citationOutput = "";
        String authorsOutput = "";
        
        String name = getName();        
        String nameOfConf = getNameOfConf();
        String publisher = getPublisher();
        String locationOfConference = getLocationOfConference();
        String editor = getEditor();    
        int startPage = getStartPage();
        int endPage = getEndPage();
        int confYear = getConfYear();

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
        
        if(startPage != endPage){
            citationOutput = authorsOutput + " " + name + ". in " + "<i>" + nameOfConf + "</i>, (" +  locationOfConference + ", " + confYear + "), " + publisher + ", " + startPage + "-" + endPage + ".<br>";
        }//end if
        
        else if(startPage == endPage){
            citationOutput = authorsOutput + " " + name + ". in " + "<i>" + nameOfConf + "</i>, (" +  locationOfConference + ", " + confYear + "), " + publisher + ", " + startPage + ".<br>";
        }//end else if
        
        return citationOutput;         
    }//end formatACM
}//end ConfProceedings Class
