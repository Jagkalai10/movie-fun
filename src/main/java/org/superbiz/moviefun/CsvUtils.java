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
        try {

            Class cls = Class.forName("CsvUtils");

            // returns the ClassLoader object associated with this Class
            ClassLoader cLoader = cls.getClassLoader();

            InputStream resourceAsStream = cLoader.getResourceAsStream(path);

            Scanner scanner = new Scanner(resourceAsStream()).useDelimiter("\\A");

            if (scanner.hasNext()) {
                return scanner.next();
            } else {
                return "";
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static <T> List<T> readFromCsv(ObjectReader objectReader, String path) {
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

    public ClassLoader getClassLoader() throws ClassNotFoundException {
        Class cls = Class.forName("CsvUtils");

        // returns the ClassLoader object associated with this Class
        ClassLoader cLoader = cls.getClassLoader();

        // input stream
        InputStream i = cLoader.getResourceAsStream(rsc);
        BufferedReader r = new BufferedReader(new InputStreamReader(i));

        // finds resource with the given name
        URL url = cLoader.getResource("file.txt");

        // finds resource with the given name
        url = cLoader.getResource("newfolder/a.txt");
    }
}
