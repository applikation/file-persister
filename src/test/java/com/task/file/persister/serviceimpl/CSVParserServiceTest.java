package com.task.file.persister.serviceimpl;

import com.task.file.persister.entity.Records;
import com.task.file.persister.exceptions.InvalidFileException;
import com.task.file.persister.exceptions.InvalidHeaderException;
import org.junit.Test;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CSVParserServiceTest {

    //TestCases
    private static final int POSITIVE = 1;
    private static final int POSITIVE2 = 2;
    private static final int POSITIVE3 = 3;
    private static final int INVALIDHEADER = 101;
    private static final int INVALIDDATE = 102;
    private static final int EXTRAHEADER = 201;
    private static final int INVALIDENDLINE = 301;

    CSVParserService csvParserService = new CSVParserService();

    @Test
    public void testRecordParsing() {
        assertEquals(getRecords(POSITIVE), csvParserService.parse(getData(POSITIVE)));
        assertEquals(getRecords(POSITIVE2), csvParserService.parse(getData(POSITIVE2)));
        assertEquals(getRecords(POSITIVE3), csvParserService.parse(getData(POSITIVE3)));
    }

    @Test(expected = InvalidHeaderException.class)
    public void testInvalidHeaders() {
        csvParserService.parse(getData(INVALIDHEADER));
    }

    @Test(expected = InvalidHeaderException.class)
    public void testExtraHeaders() {
        csvParserService.parse(getData(EXTRAHEADER));
    }

    @Test(expected = InvalidFileException.class)
    public void testInvalidFileData() {
        csvParserService.parse(getData(INVALIDENDLINE));
    }

     @Test(expected = DateTimeException.class)
    public void testInvalidDateData() {
        csvParserService.parse(getData(INVALIDDATE));
    }

    @Test(expected = InvalidHeaderException.class)
    public void testInvalidFileData2() {
        csvParserService.parse(getData(99));
    }

    private String getData(int key) {
        switch (key) {
            case POSITIVE:
                return "NAME,PRIMARY_KEY,DESCRIPTION,UPDATED_TIMESTAMP\n" +
                        "Somal, 1, Description, 2020-03-30T16:46:16.277\n" +
                        "Tomal, 2, Description2, 2020-01-30T16:46:16.275\n" +
                        " ";
            case POSITIVE2:
                return "PRIMARY_KEY,NAME,DESCRIPTION,UPDATED_TIMESTAMP\n" +
                        "1, Somal, Description, 2020-03-30T16:46:16.277\n" +
                        "2, Tomal, Description2, 2020-01-30T16:46:16.275\n" +
                        " ";
            case POSITIVE3:
                return "PRIMARY_KEY,NAME,DESCRIPTION,UPDATED_TIMESTAMP\n" +
                        "1, Somal , Description , 2020-03-30T16:46:16.277\n" +
                        "2,Tomal,Description2 , 2020-01-30T16:46:16.275\n" +
                        " ";
            case INVALIDHEADER:
                return "NAME,PRIMARY_KEY,DESCRIPTION,UPDATED_TIMESTAMPasf\n" +
                        "1, Somal, Description, 2020-03-30T16:46:16.277\n" +
                        " ";
            case EXTRAHEADER:
                return "NAME,PRIMARY_KEY,DESCRIPTION,UPDATED_TIMESTAMP,TEST\n" +
                        "1, Somal, Description, 2020-03-30T16:46:16.277\n" +
                        " ";
            case INVALIDENDLINE:
                return "NAME,PRIMARY_KEY,DESCRIPTION,UPDATED_TIMESTAMP\n" +
                        "1, Somal, Description, 2020-03-30T16:46:16.277\n" +
                        "2, Tomal, Description2, 2020-01-30T16:46:16.275\n";
           case INVALIDDATE:
                return "NAME,PRIMARY_KEY,DESCRIPTION,UPDATED_TIMESTAMP\n" +
                        "1, Somal, Description, 2020-02-30T16:46:16.277\n" +
                        " ";
            default:
                return "";
        }
    }

    private List<Records> getRecords(int key) {
        switch (key) {
            case POSITIVE:
            case POSITIVE2:
            case POSITIVE3:
                Records record1 = new Records();
                record1.setPrimaryKey("1");
                record1.setName("Somal");
                record1.setDescription("Description");
                record1.setUpdatedTimeStamp(LocalDateTime.parse("2020-03-30T16:46:16.277"));
                Records record2 = new Records();
                record2.setPrimaryKey("2");
                record2.setName("Tomal");
                record2.setDescription("Description2");
                record2.setUpdatedTimeStamp(LocalDateTime.parse("2020-01-30T16:46:16.275"));
                return Arrays.asList(record1, record2);
            default:
                return null;
        }
    }


}