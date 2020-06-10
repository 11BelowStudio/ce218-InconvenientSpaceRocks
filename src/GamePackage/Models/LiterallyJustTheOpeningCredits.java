package GamePackage.Models;

import java.io.*;
import java.util.ArrayList;

class LiterallyJustTheOpeningCredits {

    static ArrayList<String> OPENING_CREDITS;

    static {
        //if it wasn't obvious, yes, this was repurposed from the HighScoreHandler
        OPENING_CREDITS = new ArrayList<>();
        try{
            //FileReader fr = new FileReader("textAssets/openingTitleCrawlThing.txt");
            //BufferedReader br = new BufferedReader(fr);
            InputStream in = LiterallyJustTheOpeningCredits.class.getResourceAsStream("/textAssets/openingTitleCrawlThing.txt");
            //Thanks, Drew MacInnis! (https://stackoverflow.com/a/20389418)
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String currentString;
            //pretty much setting up the stuff for reading the file

            //until the end of the file is reached, it will add the current string to the file (even the empty ones)
            while ((currentString = br.readLine())!=null) {
                OPENING_CREDITS.add(currentString);
            }
            br.close(); //closes the bufferedReader
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
