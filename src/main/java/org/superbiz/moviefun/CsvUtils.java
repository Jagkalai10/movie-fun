package org.superbiz.moviefun;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CsvUtils {


    public static String readFile(String path) {

            //Class cls = Class.forName("CsvUtils");

            System.out.println("Path = " + path);

//            // returns the ClassLoader object associated with this Class
//            ClassLoader cLoader = CsvUtils.class.getClass().getClassLoader();
//
//            InputStream resourceAsStream = cLoader.getResourceAsStream(path);

            Scanner scanner = new Scanner(CsvUtils.class.getClassLoader().getResourceAsStream(path)).useDelimiter("\\A");

            if (scanner.hasNext()) {
                return scanner.next();
            } else {
                return "";
            }

    }

    public  static <T> List<T> readFromCsv(ObjectReader objectReader, String path) {
        try {
            List<T> results = new ArrayList<>();

            MappingIterator<T> iterator = objectReader.readValues(readFile(path));

            while (iterator.hasNext()) {
                results.add(iterator.nextValue());
            }

            return results;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
