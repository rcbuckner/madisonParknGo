package com.cs407.madisonparkngo;

import java.util.ArrayList;

public abstract class ListHelper {
    public static ArrayList<ParkingLot> mergeLists(ArrayList<ParkingLot> list1, ArrayList<ParkingLot> list2) {
        ArrayList<ParkingLot> out = new ArrayList<>();
        int lotID;

        for(int i = 0; i < list1.size(); i++) {
            lotID = list1.get(i).getLotID();
            for(int j = 0; j < list2.size(); j++) {
                if (lotID == list2.get(j).getLotID()) {
                    out.add(list1.get(i));
                    break;
                }
            }
        }

        return out;
    }
}
