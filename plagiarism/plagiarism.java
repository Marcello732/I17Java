import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
//import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Iterator;

public class plagiarism {
    public static void main(String[] args) throws IOException {
        Scanner scn = new Scanner(System.in);
        System.out.println("Podaj nazwe katalogu: ");
        String directory = scn.next();
        String fileExtension = "";
        String name = "";
        List<String> filesToCheck = new ArrayList<>();

        File fileDirectory = new File(directory);
        //System.out.println(fileDirectory);
        File[] contentsOfDirectory = fileDirectory.listFiles();
        //System.out.println(contentsOfDirectory);
        File destPath = new File(directory+"/unziped");
        for(File zip : contentsOfDirectory){
            unzip(zip.getPath(), destPath.getPath());
        }

        System.out.println("Podaj rozszerzenie: ");
        String extension = scn.next();
        //File contentsofUnzipDirectory = new File(destPath);
        for(File object : destPath.listFiles()){
            name = object.getName();
            int index = name.lastIndexOf('.');
            if (index > 0) {
                fileExtension = name.substring(index + 1);
            }
            if (fileExtension.equals(extension)) {
                System.out.println("File: " + object.getName());
                Path FilePath = object.toPath();
                String contentOfFile = Files.readString(FilePath);
                filesToCheck.add(contentOfFile);
            }
            if(object.isDirectory()){
                System.out.format("Directory name: %s%n",object.getName());
            }
        }
        System.out.println(filesToCheck);
        checkPlagiarism(filesToCheck.get(0), filesToCheck.get(1));
        scn.close();
    }

    private static void checkPlagiarism(String firstFile1, String secondFile1){
        String firstFile = firstFile1;
        firstFile = firstFile.replaceAll("[^a-zA-Z0-9\\s]", "");  
        System.out.println(firstFile);
        List<String> firstFileSplit = Arrays.asList(firstFile.split(" "));
        System.out.println(firstFileSplit);

        //LinkedList<String> firstFileSplitCopy = firstFileSplit;

        String secondFile = secondFile1;
        secondFile = secondFile.replaceAll("[^a-zA-Z0-9\\s]", "");  
        System.out.println(secondFile);
        List<String> secondFileSplit = Arrays.asList(firstFile.split(" "));
        System.out.println(secondFileSplit);
        Integer tempIndex = 0;

        ListIterator<String> i = firstFileSplit.listIterator();
        ListIterator<String> i2 = firstFileSplit.listIterator();
        while(i.hasNext() && i2.hasNext()){
            String s = i.next();
            String s2 = i2.next();
            if (secondFileSplit.contains(s) && secondFileSplit.contains(s2)){
                tempIndex += 1;
                i.remove();
                i2.remove();
            }
        }

        // for (int i = 0;i<firstFileSplit.size();i++){
        //     if (secondFileSplit.contains(firstFileSplit.get(i))) {
        //         firstFileSplit.remove(firstFileSplit.get(i));
        //         secondFileSplit.remove(firstFileSplit.get(i));
        //     }
        // }

        // for (String word : firstFileSplit) {
        //     if (secondFileSplit.contains(word) && secondFileSplit.indexOf(word) >= tempIndex) {
        //         tempIndex = secondFileSplit.indexOf(word);
        //         firstFileSplit.remove(word);
        //         secondFileSplit.remove(word);
        //         tempIndex += 1;
        //     }
        // }
        System.out.println(firstFileSplit);
        System.out.println(secondFileSplit);
        //Path firstFilePath = Path.of(firstFilePath1);
        //Path secondFilePath = Path.of(secondFilePath1);
        //String contentOfFirstFile = Files.readString(secondFilePath1);
        //String contentOfSecondFile = Files.readString(secondFilePath, StandardCharsets.UTF_8);
        // File firstFile = firstFile1;
        // File secondFile = secondFile1;
        // BufferedReader firstReader;
        // BufferedReader secondReader;
        // List<String> firstFileLines = new ArrayList<>();
        // List<String> secondFileLines = new ArrayList<>();
		// try {
		// 	firstReader = new BufferedReader(new FileReader(firstFile));
        //     secondReader = new BufferedReader(new FileReader(secondFile));
		// 	String linefirst = firstReader.readLine();
		// 	while (linefirst != null) {
		// 		System.out.println(linefirst);
        //         firstFileLines.add(linefirst);
		// 		// read next line
		// 		linefirst = firstReader.readLine();
		// 	}
		// 	firstReader.close();
        //     String linesecond = secondReader.readLine();
		// 	while (linesecond != null) {
		// 		System.out.println(linesecond);
        //         secondFileLines.add(linesecond);
		// 		// read next line
		// 		linesecond = firstReader.readLine();
		// 	}
		// 	secondReader.close();
		// } catch (IOException e) {
		// 	e.printStackTrace();
		// }

    }

    private static void unzip(String zipFilePath, String destDir) {
        File dir = new File(destDir);
        // create output directory if it doesn't exist
        if(!dir.exists()) dir.mkdirs();
        FileInputStream fis;
        //buffer for read and write data to file
        byte[] buffer = new byte[1024];
        try {
            fis = new FileInputStream(zipFilePath);
            ZipInputStream zis = new ZipInputStream(fis);
            ZipEntry ze = zis.getNextEntry();
            while(ze != null){
                String fileName = ze.getName();
                File newFile = new File(destDir + File.separator + fileName);
                System.out.println("Unzipping to "+newFile.getAbsolutePath());
                //create directories for sub directories in zip
                new File(newFile.getParent()).mkdirs();
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
                }
                fos.close();
                //close this ZipEntry
                zis.closeEntry();
                ze = zis.getNextEntry();
            }
            //close last ZipEntry
            zis.closeEntry();
            zis.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
}