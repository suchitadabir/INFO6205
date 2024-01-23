/*
 * Copyright (c) 2017. Phasmid Software
 */

package edu.neu.coe.info6205.randomwalk;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.nio.file.Paths;

public class RandomWalk {

    private int x = 0;
    private int y = 0;

    private final Random random = new Random();

    /**
     * Private method to move the current position, that's to say the drunkard moves
     *
     * @param dx the distance he moves in the x direction
     * @param dy the distance he moves in the y direction
     */
    private void move(int dx, int dy) {
        // TO BE IMPLEMENTED  do move
        this.x = this.x + dx;
        this.y = this.y + dy;

        // SKELETON
        // throw new RuntimeException("Not implemented");
        // END SOLUTION
    }

    /**
     * Perform a random walk of m steps
     *
     * @param m the number of steps the drunkard takes
     */
    private void randomWalk(int m) {
        // TO BE IMPLEMENTED

        for (int i = 0; i < m; i++) {
            randomMove();
        }


        //   throw new RuntimeException("implementation missing");
    }

    /**
     * Private method to generate a random move according to the rules of the situation.
     * That's to say, moves can be (+-1, 0) or (0, +-1).
     */
    private void randomMove() {
        boolean ns = random.nextBoolean();
        int step = random.nextBoolean() ? 1 : -1;
        move(ns ? step : 0, ns ? 0 : step);
    }

    /**
     * Method to compute the distance from the origin (the lamp-post where the drunkard starts) to his current position.
     *
     * @return the (Euclidean) distance from the origin to the current position.
     */
    public double distance() {
        // TO BE IMPLEMENTED

        // Calculate Euclidean distance using the Pythagorean theorem
        return Math.sqrt(this.x * this.x + this.y * this.y);
        // END SOLUTION
    }


    /**
     * Perform multiple random walk experiments, returning the mean distance.
     *
     * @param m the number of steps for each experiment
     * @param n the number of experiments to run
     * @return the mean distance
     */
    public static double randomWalkMulti(int m, int n) {
        double totalDistance = 0.0;
        double distanceTravelled = 0.0;
        for (int i = 0; i < n; i++) {
            RandomWalk walk = new RandomWalk();
            walk.randomWalk(m);
            distanceTravelled = walk.distance();
            totalDistance = totalDistance + distanceTravelled;
        }
        System.out.println("for m [ steps ] = " + m + " the Distance is = " + totalDistance / n); // printing each array element
        return totalDistance / n;

    }

    public static void main(String[] args) {
        //if (args.length == 0)
        //    throw new RuntimeException("Syntax: RandomWalk steps [experiments]");
        //int m = Integer.parseInt(args[0]);

        int m = 0;
        int countOfStepValues = 20;
        /*
         *  below code calculates random distinct values of m (steps)
         *  and adds them to arrSteps array
         */
        ArrayList<Integer> listOfRandomNos = new ArrayList<Integer>();

        for (int i = 20; i < 200; i++)
            listOfRandomNos.add(i);

        Collections.shuffle(listOfRandomNos);

        int[] arrSteps = new int[countOfStepValues];

        for (int i = 0; i < countOfStepValues; i++) {
            m = listOfRandomNos.get(i);
            arrSteps[i] = m;
        }

        System.out.println("Array Of No. Steps : m = " + Arrays.toString(arrSteps));

        /*
        # of iterations per m (In question given min value = 10)
         */
        Random rd = new Random();
        int n = rd.nextInt(countOfStepValues) + 10;
        System.out.println("Experiments to perform : n = " + n);

        String content = "";

        for (int i = 0; i < arrSteps.length; i++) {
            double meanDistance = randomWalkMulti(arrSteps[i], n);
            //System.out.println(arrSteps[i] + " steps: " + meanDistance + " over " + n + " experiments");
            content = content.concat(arrSteps[i] + "," + meanDistance + "\n");
        }

        /*
         *storing No. of Steps Vs. Mean Euclidean Distance data in file
         * */
        try {
            /*
             Create new file
             */

            String currentDirPath = System.getProperty("user.dir");
            String OutputCsvFilename = "RandomWalkData.csv";
            String path = Paths.get(currentDirPath, OutputCsvFilename).toString();
            System.out.println("Output CSV File Path :-> " + path);

            //String path = currentDirPath + "/RandomWalkData.csv";
            File file = new File(path);
            String header = "No of steps,Avg Distance\n";

            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();

            FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.append(header);
            bw.append(content);

            bw.close();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

}