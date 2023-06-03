package com.example.fyp_926975;

import java.util.ArrayList;
import java.util.Random;

public class Relations {
    Random rand;

    public ArrayList<Integer> createSet() {
        rand = new Random();
        int res = rand.nextInt(2);

        ArrayList<Integer> set = new ArrayList<>();
        if (res == 1) {
            for (int i = 0; i < 4; i++) {
                set.add(i + 1);
            }
        }
        else {
            for (int i = 0; i < 5; i++) {
                set.add(i + 1);
            }
        }
        return set;
    }
    // create all possible relations between the indexes
    public ArrayList<ArrayList<Integer>> relData2d(ArrayList<Integer> set) {
        ArrayList<ArrayList<Integer>> data2d = new ArrayList<ArrayList<Integer>>();
        int counter = 0;
        for (Integer i: set) {
            for (Integer j: set) {
                data2d.add(new ArrayList<Integer>()); // create new list for each new relation
                data2d.get(counter).add(i);
                data2d.get(counter).add(j);
                counter = counter + 1;
            }
        }

        return data2d;
    }
    // create a relation set by randomly choosing those from parameter
    public ArrayList<ArrayList<Integer>> relListCreate(ArrayList<ArrayList<Integer>> set) {
        ArrayList<ArrayList<Integer>> rel2d = new ArrayList<>();
        rand = new Random();
        int setSize = set.size();
        int size = rand.nextInt(setSize);
        if (setSize == 16) {
            for (int i = 0; i < size; i++) {
                int randSelect = rand.nextInt(setSize);
                ArrayList<Integer> temp = set.get(randSelect);
                if (!rel2d.contains(temp)) {
                    rel2d.add(temp);
                }
            }
        }
        else {
            for (int i = 0; i < size; i++) {
                int randSelect = rand.nextInt(setSize);
                ArrayList<Integer> temp = set.get(randSelect);
                if (!rel2d.contains(temp)) {
                    rel2d.add(temp);
                }
            }
        }

        return rel2d;
    }

    public boolean isSymmetric(ArrayList<ArrayList<Integer>> set) {
        ArrayList<ArrayList<Integer>> temp = new ArrayList<>();
        temp.add(new ArrayList<>());
        temp.add(new ArrayList<>());
        for (ArrayList<Integer> i: set) {
            if (temp.get(0).isEmpty()) {
                temp.get(0).add(0, i.get(0));
                temp.get(0).add(1, i.get(1));
                temp.get(1).add(0, i.get(1));
                temp.get(1).add(1, i.get(0));
            }
            else {
                temp.get(0).set(0, i.get(0));
                temp.get(0).set(1, i.get(1));
                temp.get(1).set(0, i.get(1));
                temp.get(1).set(1, i.get(0));
            }

            if (set.contains(temp.get(0)) && set.contains(temp.get(1))) {
                if (temp.get(0) == temp.get(1)) {
                    return false; // if [x,y], x == y
                }
                else {
                    return true;
                }
            }
        }
        return false;
    }



    public boolean isReflexive(ArrayList<ArrayList<Integer>> data) {
        ArrayList<ArrayList<Integer>> refList = new ArrayList<>();
        refList.add(new ArrayList<>());
        refList.add(new ArrayList<>());
        refList.add(new ArrayList<>());
        refList.add(new ArrayList<>());
        refList.add(new ArrayList<>());

        refList.get(0).add(1);
        refList.get(0).add(1);
        refList.get(1).add(2);
        refList.get(1).add(2);
        refList.get(2).add(3);
        refList.get(2).add(3);
        refList.get(3).add(4);
        refList.get(3).add(4);
        refList.get(4).add(5);
        refList.get(4).add(5);

        for (ArrayList<Integer> i: refList) {
            if (data.contains(i)) {
                return true;
            }
        }
        return false;
    }

    public boolean isTransitive(ArrayList<ArrayList<Integer>> data, ArrayList<ArrayList<Integer>> uInput) {
        for (ArrayList<Integer> i: data) {
            if (data.contains(uInput.get(0)) && data.contains(uInput.get(1))) {
                if (data.contains(uInput.get(2))) {
                    return true;
                }
            }
        }
        return false;
    }
}

