package com.spring.csvfactory;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

//CSVReaderWithManualHeader
public class CSVReader {
    private static final String SAMPLE_CSV_FILE_PATH = "src\\main\\resources\\userDataBase.csv";

    public static void main(String[] args) throws IOException {
        try (
            Reader reader = Files.newBufferedReader(Paths.get(SAMPLE_CSV_FILE_PATH));
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .withIgnoreHeaderCase()
                    .withTrim());
        		
        ) {
            for (CSVRecord csvRecord : csvParser) {
                // Accessing values by the names assigned to each column
                String id = csvRecord.get("ID");
                String name = csvRecord.get("Name");
                String designation = csvRecord.get("Designation");
                String company = csvRecord.get("Company");

                System.out.println("Record No - " + csvRecord.getRecordNumber());
                System.out.println("---------------");
                System.out.println("ID : " + id);
                System.out.println("Name : " + name);
                System.out.println("Designation : " + designation);
                System.out.println("Company : " + company);
                System.out.println("---------------\n\n");
            }
        }
    }
}