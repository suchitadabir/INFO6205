package edu.neu.coe.info6205.sort.elementary;

import edu.neu.coe.info6205.sort.Helper;
import edu.neu.coe.info6205.sort.HelperFactory;
import edu.neu.coe.info6205.sort.SortWithHelper;
import edu.neu.coe.info6205.util.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Array;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Random;
import java.util.function.Supplier;

public class Assignment3Benchmark {

    public Assignment3Benchmark(int runs, int n, int m) {
        this.runs = runs;
        this.safetyFactor = 10;
        this.supplier = this.genSupplier(safetyFactor);
        this.OrderedSupplier = this.getOrderedSupplier(safetyFactor);
        this.PartialOrderedSupplier = this.getPartialOrderedSupplier(safetyFactor);
        this.RevOrderSupplier = this.getRevOrderSupplier(safetyFactor);

        this.n = n;
        this.m = m;
    }

    public String runBenchmarks() {
        String mycontent = "";
        String content = "";
        String temp = null;
        System.out.println("Assignment3Benchmark: N=" + n*this.safetyFactor);

        final Config config = Config.setupConfig("true", "0", "1", "", "");
        Helper<Integer> helper = HelperFactory.create("InsertionSort", n, config);
        helper.init(n);
        SortWithHelper<Integer> insertionSorter = new InsertionSort<Integer>(helper);

        double d = (new Benchmark_Timer<>("random",null, arr-> insertionSorter.sort((Integer[]) arr,0,((Integer[]) arr).length),null)).runFromSupplier(supplier,runs);
        //System.out.println("Assignment3Benchmark: time :"+d);
        temp = "";
        temp = "RANDOM,"+runs+","+ (n*safetyFactor) +"," +String.valueOf(d)+"\n";
        mycontent = mycontent.concat(temp); //array size is n*fact

        double d1 = (new Benchmark_Timer<>("ordered",null, arr-> insertionSorter.sort((Integer[]) arr,0,((Integer[]) arr).length),null)).runFromSupplier(OrderedSupplier,runs);
        //System.out.println("Assignment3Benchmark: time :"+d1);
        temp = "";
        temp = "ORDERED,"+runs+","+ (n*safetyFactor) +"," +String.valueOf(d1)+"\n";
        mycontent = mycontent.concat(temp);

        double d2 = (new Benchmark_Timer<>("partially-ordered",null, arr-> insertionSorter.sort((Integer[]) arr,0,((Integer[]) arr).length),null)).runFromSupplier(PartialOrderedSupplier,runs);
        //System.out.println("Assignment3Benchmark: time :"+d2);
        temp = "";
        temp = "PARTIALLY-ORDERED,"+runs+","+ (n*safetyFactor) +"," +String.valueOf(d2)+"\n";
        mycontent = mycontent.concat(temp);

        double d3 = (new Benchmark_Timer<>("reverse-ordered",null, arr-> insertionSorter.sort((Integer[]) arr,0,((Integer[]) arr).length),null)).runFromSupplier(RevOrderSupplier,runs);
        //System.out.println("Assignment3Benchmark: time :"+d3);
        temp = "";
        temp = "REVERSE-ORDERED,"+runs+","+ (n*safetyFactor) +"," +String.valueOf(d3)+"\n";
        mycontent = mycontent.concat(temp);

        return mycontent;
    }

    public static void main(String[] args) {
        String fileContent = "";
        String content = "";
        content = new Assignment3Benchmark(100, 250, 250).runBenchmarks();
        fileContent = fileContent.concat(content);
        content = new Assignment3Benchmark(50, 500, 500).runBenchmarks();
        fileContent = fileContent.concat(content);
        content = new Assignment3Benchmark(20, 1000, 1000).runBenchmarks();
        fileContent = fileContent.concat(content);
        content = new Assignment3Benchmark(10, 2000, 2000).runBenchmarks();
        fileContent = fileContent.concat(content);
        content = new Assignment3Benchmark(5, 4000, 4000).runBenchmarks();
        fileContent = fileContent.concat(content);
        content = new Assignment3Benchmark(3, 8000, 8000).runBenchmarks();
        fileContent = fileContent.concat(content);
        content = new Assignment3Benchmark(2, 16000, 16000).runBenchmarks();
        fileContent = fileContent.concat(content);

        try {

            String currentDirPath = System.getProperty("user.dir");
            String OutputCsvFilename = "Assignment3Benchmark.csv";
            String path = Paths.get(currentDirPath, OutputCsvFilename).toString();
            System.out.println("Output CSV File Path :-> " + path);
            File file = new File(path);
            String header = "Array-Ordering,Runs,N,Time\n";
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();

            FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.append(header);
            bw.append(fileContent);

            bw.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    private final int runs;
    private final Supplier<Object> supplier;
    private final Supplier<Object> OrderedSupplier;
    private final Supplier<Object> PartialOrderedSupplier;
    private final Supplier<Object> RevOrderSupplier;
    private final int n;
    private final int m;
    private final int safetyFactor;


    public Supplier<Object> genSupplier(int safetyFactor) {
        return () -> {
            Integer[] ints = (Integer[]) Array.newInstance(Integer.class, safetyFactor * n);
            for (int i = 0; i < ints.length; i++) ints[i] = (Integer)((new Random()).nextInt(safetyFactor * this.m) - safetyFactor * this.m / 2);
            // Integer[] distinct = (Integer[]) Arrays.stream(ints).distinct().toArray();
            // Integer[] result = (Integer[]) Array.newInstance(int.class, n);
            //  for (int i = 0; i < n; i++)
            //     result[i] = distinct[i * (distinct.length / n)];
            return (Object) ints;
        };
    }
    public Supplier<Object> getOrderedSupplier(int safetyFactor) {
        return () -> {
            Integer[] ints = (Integer[]) Array.newInstance(Integer.class, safetyFactor * n);
            for (int i = 0; i < ints.length; i++) ints[i] = (Integer)((new Random()).nextInt(safetyFactor * this.m) - safetyFactor * this.m / 2);
            Arrays.sort(ints);
            return (Object) ints;
        };
    }
    public Supplier<Object> getPartialOrderedSupplier(int safetyFactor) {
        return () -> {
            Integer[] ints = (Integer[]) Array.newInstance(Integer.class, safetyFactor * n);
            for (int i = 0; i < ints.length; i++) ints[i] = (Integer)((new Random()).nextInt(safetyFactor * this.m) - safetyFactor * this.m / 2);
            Arrays.sort(ints, 0,(ints.length/2));
            return (Object) ints;
        };
    }
    public Supplier<Object> getRevOrderSupplier(int safetyFactor) {
        return () -> {
            Integer[] ints = (Integer[]) Array.newInstance(Integer.class, safetyFactor * n);
            for (int i = 0; i < ints.length; i++) ints[i] = (Integer)((new Random()).nextInt(safetyFactor * this.m) - safetyFactor * this.m / 2);
            Arrays.sort(ints, (a, b) -> b.compareTo(a));
            return (Object) ints;
        };
    }
}
