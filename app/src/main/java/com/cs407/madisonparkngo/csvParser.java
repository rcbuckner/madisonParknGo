package com.cs407.madisonparkngo;

import android.content.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class csvParser {
    public static void parse(Context context) {
        Scanner scnr;

        try
        {
            File file = new File("parkingLots.csv");
            scnr = new Scanner(file);
        }
        catch (FileNotFoundException e){
            System.out.println("file not found");
            return;
        }

        scnr.nextLine();
        String[] items;
        String line;
        char[] charArray;
        int index;
        int startIndex;
        boolean inQuotes;
        int itemNum;
        ParkingLot lot;


        while (scnr.hasNext()) {
            items = new String[12];
            line = scnr.nextLine();
            charArray = line.toCharArray();
            index = 0;
            startIndex = 0;
            inQuotes = false;
            itemNum = 0;


            while (index < charArray.length) {
                if (charArray[index] == '"') {
                    if (!inQuotes) {
                        startIndex = index+1;
                    }
                    inQuotes = !inQuotes;
                }
                if ((charArray[index] == ',' || index == charArray.length-1) && !inQuotes){
                    if(startIndex == index) {
                        items[itemNum] = "";
                    }
                    else {
                        items[itemNum] = line.substring(startIndex, index);
                        //System.out.println(line.substring(startIndex, index));
                    }
                    itemNum++;
                    startIndex = index + 1;
                }
                index++;
            }

            lot = new ParkingLot(Integer.parseInt(items[0]), items[1], items[2], Integer.parseInt(items[3]),
                    Integer.parseInt(items[4]), Integer.parseInt(items[5]), items[6], items[7], items[8], items[9],
                   items[10], items[11]);

            DBHelper.getDBInstance(context).userDao().insertLot(lot);

            //for (int i = 0; i < 12; i ++) {
            //    System.out.println(items[i]);
            //}

        }

        scnr.close();

    }

}

