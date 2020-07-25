package com.bcgdv.wealthmgmt.data.parser;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
public class AbstractCsvParser {

    public <T> List<T> readCsvFile(String fileName, String[] headers, Function<CSVRecord, T> mapper) {

        FileReader fileReader = null;
        CSVParser csvFileParser = null;

        CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader(headers);
        List<T> entities = new ArrayList<>();

        try {
            fileReader = new FileReader(getResources(fileName));
            csvFileParser = new CSVParser(fileReader, csvFileFormat);

            //Get a list of CSV file records
            List csvRecords = csvFileParser.getRecords();

            //Read the CSV file. Header is ignored (i == 1)
            for (int i = 1; i < csvRecords.size(); i++) {
                CSVRecord record = (CSVRecord) csvRecords.get(i);

                T result = mapper.apply(record); // transform to desired enitity

                entities.add(result);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return entities;
    }

    private String getResources(String filename) {
        return getClass().getClassLoader().getResource(filename).getFile();
    }
}
