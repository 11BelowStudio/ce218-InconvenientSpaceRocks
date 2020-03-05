package utilities;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class HighScoreHandler {

    //pls note that everything in this is package-private or just private

    Component parentComponent;

    private ArrayList<ScoreRecord> highScores;
    //arrayList of scoreRecord objects, exists throughout runtime, even if the high score file itself goes missing

    private String fileLocation; //location of the high score file

    private File scoreFile; //reference to the high score file

    private boolean fileExists; //tracks whether or not the high score file exists

    private boolean cheater;
    //tracks whether or not this is being accessed in debug mode
    //so it can call out anyone who is using debug mode to record a score

    public HighScoreHandler(String fl, Component c, boolean hax0r){

        parentComponent = c;

        highScores = new ArrayList<>(); //initialises the highScores arrayList

        fileLocation = fl;

        scoreFile = new File(fileLocation);
        //initialises the fileLocation string and scoreFile (which is a reference to the file at fileLocation)

        cheater = hax0r; //>:(

        String nameLine;
        String scoreLine;
        int i = 0;
        //some temporary variables
        //nameLine will hold the line of the highScoreFile with the current player's name, scoreLine is same but for their score



        //will now attempt to read the high score file and add the details about the score to the file
        try{
            fileExists = true; //file exists until proven otherwise
            FileReader fr = new FileReader(fileLocation);
            BufferedReader br = new BufferedReader(fr);
            //pretty much setting up the stuff for reading the file

            //until the end of the file is reached, it will construct a score record for everything in the file
            while ((nameLine = br.readLine())!=null) {
                scoreLine = br.readLine(); //scoreLine is 2nd line read
                if (scoreLine == null){
                    scoreLine = "0"; //set to '0' if blank
                }
                highScores.add(new ScoreRecord(nameLine,scoreLine));
                //nameLine and scoreLine used to construct a ScoreRecord, which is added to highScores
                i++;
            }
            br.close(); //closes the bufferedReader

            if (i < 5){
                placeholderHighScores(i);
                //in case it terminates 5 scores have been reached
            }

        } catch (FileNotFoundException e){
            //if the file couldn't be found
            fileExists = false;
            System.out.println("did u delete the high score file? smh my head");
            //records and complains about lack of file

            placeholderHighScores(i);
            //resumes filling out the highScores arrayList, with placeholder values
            attemptToMakeFile();
            //will attempt to create the file (and fill it)
        } catch (Exception ex){
            placeholderHighScores(i);
            System.out.println("yeah something weird happened");
            ex.printStackTrace();
            //covers anything else that may be thrown
        }

        Collections.sort(highScores);
        Collections.reverse(highScores);
        //ensures that the highScores arrayList is sorted in descending order


    }

    public void recordHighScore(int newScore){
        /*
        Attempts to find the position of the player's banked score relative to the existing recorded score
        notifies the user of their final score and their position
        asks them to enter their name
        constructs a ScoreRecord object for it
        adds that to the highScores arrayList in the appropriate place
        and then updates the contents of the high score file appropriately
        */

        int pos = 0; //the position of the new score relative to the rest of the scoreboard
        for(ScoreRecord sr:highScores){
            if (newScore > sr.getScore()){
                break;
                //end the loop if the new score is greater than the score in the current ScoreRecord object
            }
            pos++; //increase pos if it's not greater than the current score
        }

        String scoreMessage; //initialises string to hold message for the user
        int yourPos = pos+1;
        if (pos < 5){

            System.out.println("f"); //not bamboozling the player with the following message

            //a very rewarding message if the player has a score in the top 5 scores
            scoreMessage ="<h1>Congratulations!</h1>"
                    +"<p>Your score is number "+ yourPos +" on the leaderboard!</p>"
                    +"<p>However, you still lost. Fs have been entered into chat.</p>"
                    +"<p>Your score was: " + newScore + "</p>"
                    +"<p><br>Please enter your name, so it can be recorded on the leaderboard.</p>";

        } else{
            //less rewarding message if the player did not get a high score.
            scoreMessage = "<h1>congrats u died lol</h1>"
                    +"<p>your score: " + newScore + "</p>"
                    +"<p>your position: " + yourPos + "</p>"
                    +"<p><br>Please enter your name, so it can be recorded on the leaderboard.</p>";
        }

        String scoreName;
        scoreName = JOptionPane.showInputDialog(
                parentComponent,
                "<HTML>" + scoreMessage + "</HTML>",
                "GAME OVER",
                JOptionPane.PLAIN_MESSAGE); //user basically asked for their name

        //scoreName = scoreName.replace("\n","");

        if (cheater){
            scoreName = "CHEATER ALERT: " + scoreName + " IS A CHEATER";
            System.out.println("\nYou cheated not only the game but yourself.\nYou didn't grow.\nYou didn't improve.\nYou took a shortcut and gained nothing.\nYou experienced a hollow victory.\nNothing was risked and nothing was gained.\nIt's sad that you don't know the difference.\n");
            //calls out anyone who is using debug mode to get a high score
        }

        //constructs a new ScoreRecord object for this score
        ScoreRecord thisScore = new ScoreRecord(scoreName, newScore);

        highScores.add(pos,thisScore);
        //adds new score to the appropriate place in the HighScores ArrayList, and removes the last item from it

        if (fileExists){
            try {
                fillFile(); //attempts to fill the file, if it existed when it last checked
            } catch (FileNotFoundException e){
                System.out.println("Error accessing the high score file!");
                fileExists = false;
                //if it doesn't exist now, complains about it and records that it doesn't exist
                attemptToMakeFile(); //will attempt to re-make and fill the file again
            }
            catch (Exception e) {
                System.out.println("Error updating the high score file!");
                //can't update the file if something else prevents it from doing that
            }
        } else{
            attemptToMakeFile();
            //attempts again to create the file (and fill it) if it still doesn't exist yet
        }
        showHighScores(thisScore,yourPos);
    }


    public void showHighScores(){ //package-private btw
        //pretty much just creates a JOptionPane to show a record of the top 5 scores, using the scoresToString method of this
        JOptionPane.showMessageDialog(
                parentComponent,
                "<HTML><h1>Leaderboard</h1>" + scoresToString() + "</HTML>",
                "Leaderboard",
                JOptionPane.PLAIN_MESSAGE);
    }

    private void showHighScores(ScoreRecord newScore, int newPos){
        JOptionPane.showMessageDialog(parentComponent,
                "<HTML><h1>Leaderboard</h1>" + scoresToString(newScore, newPos) + "</HTML>",
                "Leaderboard",
                JOptionPane.PLAIN_MESSAGE);
    }

    private String scoresToString(ScoreRecord newScore, int pos){
        StringBuilder scoreString = new StringBuilder();
        scoreString.append(scoresToString());
        if (pos > 5){
            scoreString.append("<h2>Your Score:</h2>");
            scoreString.append("<ol start=\"");
            scoreString.append(pos);
            scoreString.append("\">");
            scoresToString(scoreString,newScore);//,pos);
            scoreString.append("</ol>");
        }
        return scoreString.toString();
    }

    String scoresToString(){
        int i = 0;
        StringBuilder scoreString = new StringBuilder();
        scoreString.append("<ol>");
        while (i < 5){
            //int pos = i+1;
            scoresToString(scoreString,highScores.get(i));//,pos);
            //scoreString.append("\n");
            //i = pos;
            i++;
        }
        scoreString.append("</ol>");
        return scoreString.toString();
        //basically puts the contents of the top 5 ScoreRecords in a single, multi-line string, which is then returned
        //adds an extra newline between each score for human-readability reasons too
    }

    private void scoresToString(StringBuilder sb, ScoreRecord sr){//, int pos){
        //sb.append("<p><b>");
        //sb.append(pos);
        //sb.append(":</b>");
        sb.append("<li>");
        sb.append(sr.toHTMLString());
        sb.append("</li>");
        //sb.append("</p><br>");
    }



    private void placeholderHighScores(int i){
        //basically fills out the part of the highScores arrayList which hasn't been filled with some high scores
        //ensures that at least 5 records will be present in highScores

        //just adds placeholder values though
        while (i < 5) {
            highScores.add(new ScoreRecord("unknown",0));
            i++;
        }
    }

    private void fillFile() throws IOException {
        //writes the contents of the highScores ArrayList to the high score file
        FileWriter tempWriter = new FileWriter(fileLocation);
        for(ScoreRecord sr: highScores){
            tempWriter.write(sr.toString());
        }
        tempWriter.close();
    }

    private void attemptToMakeFile(){
        try{
            //will attempt to create a new high score file
            if (scoreFile.createNewFile()){
                //if successful, attempts to fill it with the contents of highScores
                fillFile();
                //will only note that it exists after the contents have successfully been added
                fileExists = true;
                System.out.println("high score file has been created!");
            } else{
                System.out.println("couldn't make a new high score file, Fs in chat");
                //error message if the file could not be made
            }
        } catch (Exception e){
            System.out.println("an exception stopped the creation of a new high score file, Fs in chat");
            //error message if there's an exception stopping the creation of a high score file
            e.printStackTrace();
        }
    }

}
