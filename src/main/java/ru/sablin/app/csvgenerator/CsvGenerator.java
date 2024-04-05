package ru.sablin.app.csvgenerator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CsvGenerator {
    public static void main(String[] args) throws IOException {
        var list = List.of(
                new Person("Alex", 12),
                new Person("Sem", 34));

        generateCsvFile(list, "Test.csv");
    }

    public static <T> void generateCsvFile(List<T> objects, String outputPath) throws IOException {
        // Создаем новый файл для записи CSV
        var file = new File(outputPath);
        var bufferedWriter = new BufferedWriter(new FileWriter(file));

        // Получаем все классы объектов
        var classes = objects.get(0).getClass();
        var fields = classes.getDeclaredFields();
        Set<String> properties = new HashSet<>();
        for (Field field : fields) {
            if (field.isAnnotationPresent(ExportToCsv.class)) {
                properties.add(field.getName());
            }
        }

        // Записываем заголовок CSV файла
        for (String property : properties) {
            bufferedWriter.write(property + "|");
        }
        bufferedWriter.newLine();

        // Записываем данные каждого объекта
        for (Object obj : objects) {
            for (String property : properties) {
                try {
                    var field = obj.getClass().getDeclaredField(property);
                    bufferedWriter.write(field.get(obj) + ",");
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    throw new RuntimeException("Error accessing property '" + property + "'", e);
                }
            }
            bufferedWriter.newLine();
        }
        bufferedWriter.close();
    }
}