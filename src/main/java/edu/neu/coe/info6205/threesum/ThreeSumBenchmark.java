package edu.neu.coe.info6205.threesum;

import edu.neu.coe.info6205.util.Benchmark_Timer;
import edu.neu.coe.info6205.util.TimeLogger;
import edu.neu.coe.info6205.util.Utilities;

import java.io.BufferedWriter;
import java.nio.file.Paths;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import edu.neu.coe.info6205.util.Stopwatch;
import java.io.File;
import java.io.FileWriter;

public class ThreeSumBenchmark {
    public ThreeSumBenchmark(int runs, int n, int m) {
        this.runs = runs;
        this.supplier = new Source(n, m).intsSupplier(10);
        this.n = n;
    }

    public String runBenchmarks() {
        String mycontent = "";
        String content = "";
        System.out.println("ThreeSumBenchmark: N=" + n);
        content = benchmarkThreeSum("ThreeSumQuadratic", (xs) -> new ThreeSumQuadratic(xs).getTriples(), n, timeLoggersQuadratic);
        mycontent = mycontent.concat(content);
        content = benchmarkThreeSum("ThreeSumQuadrithmic", (xs) -> new ThreeSumQuadrithmic(xs).getTriples(), n, timeLoggersQuadrithmic);
        mycontent = mycontent.concat(content);
        content = benchmarkThreeSum("ThreeSumCubic", (xs) -> new ThreeSumCubic(xs).getTriples(), n, timeLoggersCubic);
        mycontent = mycontent.concat(content);
        content = benchmarkThreeSum("ThreeSumQuadraticWithCalipers", (xs) -> new ThreeSumQuadraticWithCalipers(xs).getTriples(), n, timeLoggersQuadraticWithCalipers);
        mycontent = mycontent.concat(content);
        return mycontent;
    }

    public static void main(String[] args) {
        String fileContent = "";
        String content = "";
        content = new ThreeSumBenchmark(100, 250, 250).runBenchmarks();
        fileContent = fileContent.concat(content);
        content = new ThreeSumBenchmark(50, 500, 500).runBenchmarks();
        fileContent = fileContent.concat(content);
        content = new ThreeSumBenchmark(20, 1000, 1000).runBenchmarks();
        fileContent = fileContent.concat(content);
        content = new ThreeSumBenchmark(10, 2000, 2000).runBenchmarks();
        fileContent = fileContent.concat(content);
        content = new ThreeSumBenchmark(5, 4000, 4000).runBenchmarks();
        fileContent = fileContent.concat(content);
        content = new ThreeSumBenchmark(3, 8000, 8000).runBenchmarks();
        fileContent = fileContent.concat(content);
        content = new ThreeSumBenchmark(2, 16000, 16000).runBenchmarks();
        fileContent = fileContent.concat(content);
        //System.out.println(fileContent);
        /*
         *storing No. of Steps Vs. Mean Euclidean Distance data in file
         * */
        try {
            /*
             Create new file
             */

            String currentDirPath = System.getProperty("user.dir");
            String OutputCsvFilename = "ThreeSumBenchmark.csv";
            String path = Paths.get(currentDirPath, OutputCsvFilename).toString();
            System.out.println("Output CSV File Path :-> " + path);
            File file = new File(path);
            String header = "Function,N,Time\n";

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

    private String benchmarkThreeSum(final String description, final Consumer<int[]> function, int n, final TimeLogger[] timeLoggers) {
        String content = "";
        if (description.equals("ThreeSumCubic") && n > 4000) return content;
        // TO BE IMPLEMENTED
        int[] a = supplier.get();
        try (Stopwatch stopwatch = new Stopwatch()) {
            function.accept(a);
            long time = stopwatch.lap();
            content = content.concat(description + "," + n + "," + time + "\n");
            System.out.println("Function = " + description + " n = " + n + " Elapsed time: " + time + " msecs");
        }
        //throw new RuntimeException("implementation missing");
        return content;
    }

    private final static TimeLogger[] timeLoggersCubic = {
            new TimeLogger("Raw time per run (mSec): ", (time, n) -> time),
            new TimeLogger("Normalized time per run (n^3): ", (time, n) -> time / n / n / n * 1e6)
    };
    private final static TimeLogger[] timeLoggersQuadrithmic = {
            new TimeLogger("Raw time per run (mSec): ", (time, n) -> time),
            new TimeLogger("Normalized time per run (n^2 log n): ", (time, n) -> time / n / n / Utilities.lg(n) * 1e6)
    };
    private final static TimeLogger[] timeLoggersQuadratic = {
            new TimeLogger("Raw time per run (mSec): ", (time, n) -> time),
            new TimeLogger("Normalized time per run (n^2): ", (time, n) -> time / n / n * 1e6)
    };
    private final static TimeLogger[] timeLoggersQuadraticWithCalipers = {
            new TimeLogger("Raw time per run (mSec): ", (time, n) -> time),
            new TimeLogger("Normalized time per run (n^2): ", (time, n) -> time / n / n * 1e6)
    };

    private final int runs;
    private final Supplier<int[]> supplier;
    private final int n;
}