package edu.neu.coe.info6205.sort.select;

import edu.neu.coe.info6205.util.Benchmark_Timer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Array;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Random;
import java.util.function.Supplier;

public class SelectBenchmark {

    public SelectBenchmark(int runs, int n, int m) {
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
        System.out.println("SelectBenchmark: N=" + n * this.safetyFactor);

        Object object = supplier.get();
        Integer[] integers = (Integer[]) object;
        int length = integers.length;
        QuickSelect<Integer> quickSelect = new QuickSelect<>(integers, length / 2);

        double d = (new Benchmark_Timer<>("random", null, arr -> quickSelect.select((Integer[]) arr, ((Integer[]) arr).length / 2), null)).runFromSupplier(supplier, runs);
        //System.out.println("SelectBenchmark: time :"+d);
        temp = "";
        temp = "QuickSelect,RANDOM," + runs + "," + (n * safetyFactor) + "," + String.valueOf(d) + "\n";
        mycontent = mycontent.concat(temp); //array size is n*fact

        double d1 = (new Benchmark_Timer<>("ordered", null, arr -> quickSelect.select((Integer[]) arr, ((Integer[]) arr).length / 2), null)).runFromSupplier(OrderedSupplier, runs);
        //System.out.println("SelectBenchmark: time :"+d1);
        temp = "";
        temp = "QuickSelect,ORDERED," + runs + "," + (n * safetyFactor) + "," + String.valueOf(d1) + "\n";
        mycontent = mycontent.concat(temp);

        double d2 = (new Benchmark_Timer<>("partially-ordered", null, arr -> quickSelect.select((Integer[]) arr, ((Integer[]) arr).length / 2), null)).runFromSupplier(PartialOrderedSupplier, runs);
        //System.out.println("SelectBenchmark: time :"+d2);
        temp = "";
        temp = "QuickSelect,PARTIALLY-ORDERED," + runs + "," + (n * safetyFactor) + "," + String.valueOf(d2) + "\n";
        mycontent = mycontent.concat(temp);

        double d3 = (new Benchmark_Timer<>("reverse-ordered", null, arr -> quickSelect.select((Integer[]) arr, ((Integer[]) arr).length / 2), null)).runFromSupplier(RevOrderSupplier, runs);
        //System.out.println("SelectBenchmark: time :"+d3);
        temp = "";
        temp = "QuickSelect,REVERSE-ORDERED," + runs + "," + (n * safetyFactor) + "," + String.valueOf(d3) + "\n";
        mycontent = mycontent.concat(temp);

        SlowSelect<Integer> slowSelect = new SlowSelect<>(integers, length / 2);

        double d4 = (new Benchmark_Timer<>("random", null, arr -> slowSelect.select((Integer[]) arr, ((Integer[]) arr).length / 2), null)).runFromSupplier(supplier, runs);
        //System.out.println("SelectBenchmark: time :"+d);
        temp = "";
        temp = "SlowSelect,RANDOM," + runs + "," + (n * safetyFactor) + "," + String.valueOf(d4) + "\n";
        mycontent = mycontent.concat(temp); //array size is n*fact

        double d5 = (new Benchmark_Timer<>("ordered", null, arr -> slowSelect.select((Integer[]) arr, ((Integer[]) arr).length / 2), null)).runFromSupplier(OrderedSupplier, runs);
        //System.out.println("SelectBenchmark: time :"+d1);
        temp = "";
        temp = "SlowSelect,ORDERED," + runs + "," + (n * safetyFactor) + "," + String.valueOf(d5) + "\n";
        mycontent = mycontent.concat(temp);

        double d6 = (new Benchmark_Timer<>("partially-ordered", null, arr -> slowSelect.select((Integer[]) arr, ((Integer[]) arr).length / 2), null)).runFromSupplier(PartialOrderedSupplier, runs);
        //System.out.println("SelectBenchmark: time :"+d2);
        temp = "";
        temp = "SlowSelect,PARTIALLY-ORDERED," + runs + "," + (n * safetyFactor) + "," + String.valueOf(d6) + "\n";
        mycontent = mycontent.concat(temp);

        double d7 = (new Benchmark_Timer<>("reverse-ordered", null, arr -> slowSelect.select((Integer[]) arr, ((Integer[]) arr).length / 2), null)).runFromSupplier(RevOrderSupplier, runs);
        //System.out.println("SelectBenchmark: time :"+d3);
        temp = "";
        temp = "SlowSelect,REVERSE-ORDERED," + runs + "," + (n * safetyFactor) + "," + String.valueOf(d7) + "\n";
        mycontent = mycontent.concat(temp);

        return mycontent;
    }

    public static void main(String[] args) {
        String fileContent = "";
        String content = "";
        content = new SelectBenchmark(100, 256, 256).runBenchmarks();
        fileContent = fileContent.concat(content);
        content = new SelectBenchmark(50, 512, 512).runBenchmarks();
        fileContent = fileContent.concat(content);
        content = new SelectBenchmark(20, 1024, 1024).runBenchmarks();
        fileContent = fileContent.concat(content);
        content = new SelectBenchmark(10, 2048, 2048).runBenchmarks();
        fileContent = fileContent.concat(content);
        content = new SelectBenchmark(5, 4096, 4096).runBenchmarks();
        fileContent = fileContent.concat(content);
        content = new SelectBenchmark(3, 8192, 8192).runBenchmarks();
        fileContent = fileContent.concat(content);
        content = new SelectBenchmark(2, 16384, 16384).runBenchmarks();
        fileContent = fileContent.concat(content);

        try {

            String currentDirPath = System.getProperty("user.dir");
            String OutputCsvFilename = "SelectBenchmark.csv";
            String path = Paths.get(currentDirPath, OutputCsvFilename).toString();
            System.out.println("Output CSV File Path :-> " + path);
            File file = new File(path);
            String header = "Method,Array-Ordering,Runs,N,Time\n";
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
            for (int i = 0; i < ints.length; i++)
                ints[i] = (Integer) ((new Random()).nextInt(safetyFactor * this.m) - safetyFactor * this.m / 2);
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
            for (int i = 0; i < ints.length; i++)
                ints[i] = (Integer) ((new Random()).nextInt(safetyFactor * this.m) - safetyFactor * this.m / 2);
            Arrays.sort(ints);
            return (Object) ints;
        };
    }

    public Supplier<Object> getPartialOrderedSupplier(int safetyFactor) {
        return () -> {
            Integer[] ints = (Integer[]) Array.newInstance(Integer.class, safetyFactor * n);
            for (int i = 0; i < ints.length; i++)
                ints[i] = (Integer) ((new Random()).nextInt(safetyFactor * this.m) - safetyFactor * this.m / 2);
            Arrays.sort(ints, 0, (ints.length / 2));
            return (Object) ints;
        };
    }

    public Supplier<Object> getRevOrderSupplier(int safetyFactor) {
        return () -> {
            Integer[] ints = (Integer[]) Array.newInstance(Integer.class, safetyFactor * n);
            for (int i = 0; i < ints.length; i++)
                ints[i] = (Integer) ((new Random()).nextInt(safetyFactor * this.m) - safetyFactor * this.m / 2);
            Arrays.sort(ints, (a, b) -> b.compareTo(a));
            return (Object) ints;
        };
    }
}
