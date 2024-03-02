package edu.neu.coe.info6205.sort.par;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;

/**
 * This code has been fleshed out by Ziyao Qiao. Thanks very much.
 * CONSIDER tidy it up a bit.
 */
public class Main {

    public static void main(String[] args) {
        processArgs(args);
        /*System.out.println("Degree of parallelism: " + ForkJoinPool.getCommonPoolParallelism());
        Random random = new Random();
        int[] array = new int[2000000];
        ArrayList<Long> timeList = new ArrayList<>();
        for (int j = 50; j < 100; j++) {
            ParSort.cutoff = 10000 * (j + 1);
            // for (int i = 0; i < array.length; i++) array[i] = random.nextInt(10000000);
            long time;
            long startTime = System.currentTimeMillis();
            for (int t = 0; t < 10; t++) {
                for (int i = 0; i < array.length; i++) array[i] = random.nextInt(10000000);
                ParSort.sort(array, 0, array.length);
            }
            long endTime = System.currentTimeMillis();
            time = (endTime - startTime);
            timeList.add(time);*/

        int arraySize = 500000;
        int numLoop = 0;
        while (numLoop < 5) {
            numLoop = numLoop + 1;
            arraySize = arraySize * 2;
            int[] array = new int[arraySize];
            System.out.println("Arraysize: " + String.valueOf(arraySize));

            int threadCount = 1;
            int maxThread = Runtime.getRuntime().availableProcessors();
            while (threadCount <= maxThread) {
                ArrayList<Long> timeList = new ArrayList<>();
                threadCount = threadCount << 1;
                System.out.println("Thread Count: " + String.valueOf(threadCount));
                ForkJoinPool myPool = new ForkJoinPool(threadCount);
                System.out.println("Degree of parallelism: " + myPool.getParallelism());
                Random random = new Random();

                for (double j = 0.02; j <= 1; j += 0.02) {
                    ParSort.cutoff = (int) (arraySize * j);
                    // for (int i = 0; i < array.length; i++) array[i] = random.nextInt(10000000);
                    long time;
                    long startTime = System.currentTimeMillis();
                    for (int t = 0; t < 10; t++) {
                        for (int i = 0; i < array.length; i++)
                            array[i] = random.nextInt(10000000);
                        ParSort.sort(array, 0, array.length, myPool);
                    }
                    long endTime = System.currentTimeMillis();
                    time = (endTime - startTime);
                    timeList.add(time);

                    System.out.println(
                            "Arraysize: " + String.valueOf(arraySize) + "\tThread Count: " + String.valueOf(threadCount)
                                    + "\tCutoff：" + (ParSort.cutoff) + "\t10times Time: " + time + "ms");

                }
                try {
                    FileOutputStream fis = new FileOutputStream("./src/ParSort.csv", true);
                    OutputStreamWriter isr = new OutputStreamWriter(fis);
                    BufferedWriter bw = new BufferedWriter(isr);
                    double j = 0.02;
                    for (long i : timeList) {
                        String content = String.valueOf(arraySize) + "," + String.valueOf(threadCount) + ","
                                + String.valueOf(j) + "," + (double) i / 10 + "\n";
                        j += 0.02;
                        bw.write(content);
                        bw.flush();
                    }
                    bw.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void processArgs(String[] args) {
        String[] xs = args;
        while (xs.length > 0)
            if (xs[0].startsWith("-")) xs = processArg(xs);
    }

    private static String[] processArg(String[] xs) {
        String[] result = new String[0];
        System.arraycopy(xs, 2, result, 0, xs.length - 2);
        processCommand(xs[0], xs[1]);
        return result;
    }

    private static void processCommand(String x, String y) {
        if (x.equalsIgnoreCase("N")) setConfig(x, Integer.parseInt(y));
        else
            // TODO sort this out
            if (x.equalsIgnoreCase("P")) //noinspection ResultOfMethodCallIgnored
                ForkJoinPool.getCommonPoolParallelism();
    }

    private static void setConfig(String x, int i) {
        configuration.put(x, i);
    }

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private static final Map<String, Integer> configuration = new HashMap<>();


}