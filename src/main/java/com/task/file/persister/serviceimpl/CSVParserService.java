package com.task.file.persister.serviceimpl;

import com.task.file.persister.config.ColumnNames;
import com.task.file.persister.entity.Records;
import com.task.file.persister.exceptions.InvalidFileException;
import com.task.file.persister.exceptions.InvalidHeaderException;
import com.task.file.persister.service.IParserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementation to parse CSV format file data.
 */
@Service
public class CSVParserService implements IParserService {

    private static final String NEWLINE = "\\n";
    private static final String DELIMITER = ",";
    private static final int HEADERLINENUM = 0;
    private static final int EXPECTEDNUMOFCOLUMNS = 4;


    @Override
    public List<Records> parse(String fileData) {
        String[] allLines = splitIntoLines(fileData);
        validateFile(allLines);
        String[] lines = stripLeadingAndTrailingLines(allLines);
        List<List<String>> records = filterInvalidRecords(lines);
        return transformToRecords(records, getHeaders(allLines[HEADERLINENUM]));
    }

    /**
     * Gets the headers/column name from the first line of the file data and trims the whitespaces
     *
     * @param allLine
     * @return
     */
    private List<String> getHeaders(String allLine) {
        return Arrays.stream(allLine.split(DELIMITER)).map(String::trim).collect(Collectors.toList());
    }

    private List<List<String>> filterInvalidRecords(String[] lines) {
        return Arrays.stream(lines)
                .map(line -> line.split(DELIMITER))
                .filter(split -> split.length == EXPECTEDNUMOFCOLUMNS)
                .map(Arrays::asList)
                .collect(Collectors.toList());
    }

    private String getFirstLine(String[] allLines) {
        return allLines[HEADERLINENUM];
    }

    private String getLastLine(String[] allLines) {
        return allLines[allLines.length - 1];
    }

    /**
     * Gets the subset of lines array, excluding first line/headers and the last line which is empty
     *
     * @param lines
     * @return subset of lines array
     */
    private String[] stripLeadingAndTrailingLines(String[] lines) {
        return Arrays.copyOfRange(lines, 1, lines.length - 1);
    }

    private String[] splitIntoLines(String fileData) {
        return fileData.split(NEWLINE);
    }

    /**
     * Converts the List of values from each line/record of file to corresponding
     * record entry to be persisted based on the column indexes determined on runtime
     * it can be out of sequence but the name should match exactly
     *
     * @param rows
     * @param firstLine
     * @return
     */
    private List<Records> transformToRecords(List<List<String>> rows, List<String> firstLine) {
        final List<Records> records = new ArrayList<>(rows.size());
        final int PRIMARYKEYINDEX = getPrimaryKeyIndex(firstLine);
        final int NAMEINDEX = getNameIndex(firstLine);
        final int DESCRIPTIONINDEX = getDescriptionIndex(firstLine);
        final int TIMESTAMPINDEX = getTimeStampIndex(firstLine);
        rows.forEach(row -> {
            Records entry = new Records();
            entry.setPrimaryKey(row.get(PRIMARYKEYINDEX).trim());
            entry.setName(row.get(NAMEINDEX).trim());
            entry.setDescription(row.get(DESCRIPTIONINDEX).trim());
            entry.setUpdatedTimeStamp(LocalDateTime.parse(row.get(TIMESTAMPINDEX).trim()));
            records.add(entry);
        });
        return records;
    }

    private int getTimeStampIndex(List<String> firstLine) {
        if (firstLine.contains(ColumnNames.UPDATED_TIMESTAMP)) {
            return firstLine.indexOf(ColumnNames.UPDATED_TIMESTAMP);
        }
        throw new InvalidHeaderException("Updated timestamp column not present in headers.");
    }

    private int getDescriptionIndex(List<String> firstLine) {
        if (firstLine.contains(ColumnNames.DESCRIPTION)) {
            return firstLine.indexOf(ColumnNames.DESCRIPTION);
        }
        throw new InvalidHeaderException("Description column not present in headers.");
    }

    private int getNameIndex(List<String> firstLine) {
        if (firstLine.contains(ColumnNames.NAME)) {
            return firstLine.indexOf(ColumnNames.NAME);
        }
        throw new InvalidHeaderException("Name column not present in headers.");
    }

    private int getPrimaryKeyIndex(List<String> firstLine) {
        if (firstLine.contains(ColumnNames.PRIMARY_KEY)) {
            return firstLine.indexOf(ColumnNames.PRIMARY_KEY);
        }
        throw new InvalidHeaderException("Primary key column not present in headers.");
    }

    /**
     * Checks if the first and last line comply with the format requirements
     * First line should be having expected number of column no more no less
     * Last line should be empty with at least a whitespace character
     *
     * @param allLines
     */
    private void validateFile(String[] allLines) {
        String firstLine = getFirstLine(allLines);
        String lastLine = getLastLine(allLines);
        if (!(firstLine.split(DELIMITER).length == EXPECTEDNUMOFCOLUMNS)) {
            throw new InvalidHeaderException("Headers length mismatch.");
        }
        if (!lastLine.trim().isEmpty()) {
            throw new InvalidFileException("Last line is not empty/blank.");
        }
    }
}
