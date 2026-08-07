package Key_Flags;

import java.io.File;

public class test {

    public static int countFiles(File folder) {
        int count = 0;
        File[] files = folder.listFiles();
        if (files == null) {
            return 0;
        }
        for (File file : files) {
            if (file.isFile()) {
                count++;
            } else if (file.isDirectory()) {
                count += countFiles(file); // go inside subfolder
            }
        }

        return count;
    }

    public static void main(String[] args) {

        String parent = "C:\\Syndigo_Phase2\\pim.phase2\\src\\test\\resources\\Pre_ETL_Artifacts\\Key_Flags";

        File parentDir = new File(parent);
        int grandTotal = 0;

        for (File folder : parentDir.listFiles()) {
            if (folder.isDirectory()) {

                int count = countFiles(folder);

                System.out.println(folder.getName() + " -> " + count + " files");

                grandTotal += count;
            }
        }

        System.out.println("\nGrand Total Files = " + grandTotal);
    }
}