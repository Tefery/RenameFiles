package com.mascas.renameSmashDLCs;

import java.io.File;

public class Main {

    private static final String COSA = "] [";
    private static final String FLECHA = " -> ";

    public static void main(String[] args) {
        File[] files = new File(args[0]).listFiles();
        showFiles(files);
    }

    public static void showFiles(File[] files) {
        int index;
        String name;
        File tempFile;
        for (File file : files) {
            name = file.getName();
            while ((index = name.indexOf(COSA)) != -1) {
                name = name.substring(index + 3);
            }
            tempFile = new File(file.getParent() + File.separator + name);
            System.out.println(file.getName() + FLECHA + tempFile.getPath());
            file.renameTo(tempFile);
        }
    }

}
