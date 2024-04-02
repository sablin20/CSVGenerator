package ru.sablin.app.csvgenerator;

import org.junit.jupiter.api.Test;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CsvGeneratorTest {

    @Test
    public void testGenerateCsvFile() throws IOException {
        var persons = List.of(
                new Person("Alex", 12),
                new Person("Sem", 34));
        var expectedFileName = "Test.csv";
        var expectedHeader = "name|age|";
        var expectedContent = "Alex,12,\nSem,34,\n";
        CsvGenerator.generateCsvFile(persons, expectedFileName);

        List<String> expectedList = new ArrayList<>();
        var bufferedReader = new BufferedReader(new FileReader(expectedFileName));
        String str;
        while ((str = bufferedReader.readLine()) != null) {
            expectedList.add(str);
        }
        bufferedReader.close();

        assertEquals(expectedHeader, expectedList.get(0));
        assertEquals(expectedContent, expectedList.get(1) + "\n" + expectedList.get(2) + "\n");
    }
}