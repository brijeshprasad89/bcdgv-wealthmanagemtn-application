package com.bcgdv.wealthmgmt.common;

import org.apache.commons.csv.CSVRecord;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static Integer csvStringRecordToInteger(CSVRecord csvRecord, String column) {
        String value = csvRecord.get(column);
        if (!value.isBlank()) {
            return Integer.valueOf(value);
        }
        throw new IllegalArgumentException("Error processing the column " + column);
    }

    public static LocalDate csvStringRecordToDate(CSVRecord csvRecord, String column) {
        String value = csvRecord.get(column);

        try {
            return LocalDate.parse(value);

        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Error processing the column " + column);
        }
    }

    public static BigDecimal toBigDecimal(double value) {
        return BigDecimal.valueOf(value);
    }

    public static <T> List<List<T>> partitionList(List<T> list, Integer partitionSize) {
        List<List<T>> partitions = new ArrayList<>();

        for (int i = 0; i < list.size(); i += partitionSize) {
            partitions.add(list.subList(i, Math.min(i + partitionSize, list.size())));
        }

        return partitions;
    }

}
