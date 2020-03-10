package utilities;

public class ScoreRecord implements Comparable {

    //basically contains a String object to hold a player's name, and an Integer object to hold their score
    private String name;
    private Integer theScore;

    //pls note that the constructors are package-private

    ScoreRecord(String scoreName, String score){
        //this constructor is called when HighScoreHandler is reading from the high score file
        setName(scoreName); //attempts to initialise their name
        try {
            //attempts to get an Integer theScore from the String score
            this.theScore = Integer.parseInt(score);
        } catch (Exception e){
            //sets theScore to 0 if it fails
            this.theScore = 0;
        }
    }

    ScoreRecord(String scoreName, int score) {
        //this constructor is called when HighScoreHandler is constructing a score achieved by the current player
        setName(scoreName); //attempts to initialise their name
        this.theScore = score; //theScore set to value of score
    }

    private void setName(String scoreName){
        if (scoreName == null ||  scoreName.isEmpty()){
            this.name = "unknown"; //name set to unknown if scoreName is empty
        } else{
            this.name = scoreName; //otherwise name set to scoreName
        }
    }

    Integer getScore(){
        return theScore;
    } //returns 'theScore' of this ScoreRecord

    String getName(){ return name; } //returns the 'name' of this ScoreRecord


    @Override
    public String toString(){
        return name + "\n" + theScore + "\n";
        //name on first line of string, theScore on 2nd line, and followed by a newline
    }

    public String toHTMLString(){
        return "<span style = \"font-weight: bold;\">" + name + "</strong><br>" + theScore;
    }


    @Override
    public int compareTo(Object o) {
        if (o instanceof ScoreRecord){
            return this.getScore().compareTo(((ScoreRecord) o).getScore());
            //compares the 'theScore' value of this to the 'theScore' value of the other ScoreRecord
        } else{
            return 0; //can't really compare the other object if it isn't a scoreRecord, y'know?
        }
    }

}