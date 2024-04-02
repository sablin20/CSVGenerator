package ru.sablin.app.csvgenerator;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Person {
    @ExportToCsv
    String name;
    @ExportToCsv
    int age;
}
