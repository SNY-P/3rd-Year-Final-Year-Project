package com.example.fyp_926975;

import java.util.ArrayList;
import java.util.Random;

public class cartesian {
    // initiate the two lists that will be used
    private ArrayList<Character> list1;
    private ArrayList<Integer> list2;
    // create a list of cartesian product using the two list from the parameter
    public ArrayList<String> listProduct(ArrayList<Character> setA, ArrayList<Integer> setB) {
        ArrayList<String> product = new ArrayList<String>();

        if (setA.size() == 0) { return product; }
        if (setB.size() == 0) { return product; }

        for (Character i: setA) {
            for (Integer j: setB) {
                String cProd = "(" + i + "," + j + ")";
                product.add(cProd);
            }
        }

        return product;
    }
    // checks how many answers the user gave was correct
    public ArrayList<Integer> answerCheck(ArrayList<String> answer, String userAns) {
        int correctCount = 0;
        int incorrectCount = 0;

        for (int i = 0; i <= answer.size()-1; i++) {
            if (userAns.contains(answer.get(i))) { correctCount += 1; } //true
            else { incorrectCount += 1; } //false
        }

        ArrayList<Integer> count = new ArrayList<>();
        count.add(correctCount);
        count.add(incorrectCount);

        return count;
    }
    // create a the first list (character list) by creating a random sized array
    public ArrayList<Character> list1() {
        Random rand = new Random();
        int low = 3;
        int high = 5;
        int result = rand.nextInt(high - low) + low;

        list1 = new ArrayList<>(result);
        for (int i = 0; i <= result; i++) {
            list1.add((char)(rand.nextInt(26) + 'a')); // add random letter from a-z
        }

        return list1;
    }
    // creates the second list (integer list)
    public ArrayList<Integer> list2() {
        Random rand = new Random();
        int low = 3;
        int high = 5;
        int result = rand.nextInt(high - low) + low;

        list2 = new ArrayList<>(result);

        for (int i = 0; i <= result; i++) {
            list2.add(rand.nextInt(100));
        }

        return list2;
    }
}
