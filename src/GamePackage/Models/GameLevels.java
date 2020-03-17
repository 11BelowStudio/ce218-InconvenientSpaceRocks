package GamePackage.Models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

//package-private
class GameLevels {

    /*
    okay so basically this will handle the configurations of levels,
    and will return the parameters to be used to construct a level for the game
     */
    private static ArrayList<ArrayList<ArrayList<Integer>>> levels;
    //3d arraylist containing level info

    //{small asteroids,medium,big}
    private static int[][][] arrLevels = {
            {{2, 2, 0}, {1, 3, 0}, {0, 0, 1}},
            {{0, 2, 1}, {0, 1, 2}, {0, 0, 3}, {0, 5,0}},
            {{0, 0, 4}, {0, 0, 5}},
            {{0, 0, 6}, {0, 5, 4},{0,15,1}},
            {{0, 10, 4}, {0, 0, 10}},
            {{30, 0, 1}, {0, 6, 10}, {8, 1, 10}, {5, 3, 10}, {2, 5, 10}},
            {{7, 2, 10}, {4, 4, 10}, {1, 6, 13}, {9, 1, 13}, {6, 3, 10}},
            {{0, 8, 10}, {8, 2, 10}, {5, 4, 10}, {2, 6, 10}, {7, 3, 10},{40,20,10}}
    };

    static {

        /*
        basically all the levels as a 3d array, so it can be put in an arraylist.
        making the 3d arraylist directly looked more painful than justifiable,
        so it constructs it from this 3d array

        stuff in the array is stored in the format:
            int[level][configurations for the level][small asteroids,medium asteroids,big asteroids]

        also 3d arrayList time
         */

        levels = new ArrayList<>();

        //making the 3d 'levels' arraylist directly looked much more painful than justifiable,
        //so here's some for loops instead to make it
        int i = 0;
        int j;
        for (int[][] eachLevel : arrLevels) {
            levels.add(new ArrayList<>()); //adds arrayList to levels
            j = 0;
            for (int[] configurations : eachLevel) {
                levels.get(i).add(new ArrayList<>()); //adds arrayList to current arrayList of levels
                for (int value : configurations) {
                    levels.get(i).get(j).add(value);
                    //adds current integer to current arrayList of currentArraylist of levels
                }
                j++;
            }
            i++;
        }
    }


    static ArrayList<Integer> getLevelConfig(int level){
        //returns one random configuration for the current level of the player, passed as a parameter to makeBoard in FlipBoard

        int tempLevel = level - 1; //reduces level by 1 for purposes of easily getting it from the arraylist
        if (tempLevel > 7){ //there's only configurations for 8 levels so yeah after that it works differently
            int extraLevel = tempLevel-7; //works out how many levels the player has gone past the final level
            if (extraLevel > 10){
                extraLevel =10; //just making sure it doesn't get too out of hand
            }
            ArrayList<Integer> theLevel = getRandomLevelStatic(7); //obtains a random final level
            for (int i = 0; i < 3; i++) {
                //increases the quantity of each asteroid by a random number
                //  between 1 and the number of levels that the player has progressed past the final defined level
                int current = theLevel.get(i);
                current += (int)(Math.random() * extraLevel);
                theLevel.set(i,current);
            }
            return theLevel;
        } else {
            return getRandomLevelStatic(tempLevel);
            //returns the level as-is if the player hasn't got past level
        }
    }

    private static ArrayList<Integer> getRandomLevelStatic(int levelToGet){
        ArrayList<ArrayList<Integer>> temp = levels.get(levelToGet);//extracts the configurations for the current level only
        Collections.shuffle(temp); //randomizes the configurations for the current level
        return temp.get(0); //gets the first configuration after the order was randomized
    }



    /*
    this is a stress test. basically, using 'ohHecc' instead of a call to 'getLevelConfig'
    ensures that the maximum amount of all asteroids are spawned in.
    it is significantly above the actual cap, but thats to ensure that it'll spawn in enough if I increase the cap later
    fun times.
    */
    private static Integer[] ohNoes = new Integer[]{500,500,500};
    static ArrayList<Integer> ohHecc = new ArrayList<>(Arrays.asList(ohNoes));





}
