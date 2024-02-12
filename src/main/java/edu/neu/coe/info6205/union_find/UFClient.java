package edu.neu.coe.info6205.union_find;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Paths;
import java.util.Random;

public class UFClient {
    public static int count(int n) {
        UF_HWQUPC ufClient = new UF_HWQUPC(n);
        int pairCount = 0;
        Random random = new Random();

        while (ufClient.components() > 1) {

            int i = random.nextInt(n);
            int j = random.nextInt(n);

            //uf.show();
            //System.out.println("----");

            if (!ufClient.connected(i, j)) {
                ufClient.union(i, j);
                pairCount++;
                //System.out.println("---("+p+","+q+")");
            }

            //uf.show();

        }



        // for(int i = 0; i<n; i++){
        //      System.out.println("("+i+","+uf.getParentToPrint(i)+")");
        //}

        //uf.show();

        return pairCount;
    }

    public static void main(String[] args) {
        String fileContent = null;

        int[] nValues = {10,30,60, 100,150, 200, 400, 800, 1600,1700,  3200, 5200, 6400, 12800, 25600,45600, 51200, 102400, 204800, 604800,160048000 };

        for (int n : nValues) {
            int pairs = count(n);
            System.out.printf("For n objects = %d, the number of pairs are %d.\n", n, pairs);
            if (fileContent == null){
                fileContent = String.valueOf(n) + "," + pairs + "\n";
            }else {
                fileContent = fileContent.concat(String.valueOf(n) + "," + pairs + "\n");
            }

        }


        try {

            String currentDirPath = System.getProperty("user.dir");
            String OutputCsvFilename = "UFClientData.csv";
            String path = Paths.get(currentDirPath, OutputCsvFilename).toString();
            System.out.println("Output CSV File Path :-> " + path);
            File file = new File(path);
            String header = "n,m\n";

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


}
