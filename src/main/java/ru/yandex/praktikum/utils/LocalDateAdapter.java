package ru.yandex.praktikum.utils;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

public class LocalDateAdapter extends TypeAdapter<LocalDate> {
    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
    @Override
    public void write(JsonWriter jsonWriter, LocalDate date) throws IOException {
        if (date == null) {
            jsonWriter.nullValue();
            return;
        }
        jsonWriter.value(date.format(formatter));
    }

    @Override
    public LocalDate read(JsonReader jsonReader) throws IOException {
        if (jsonReader.peek() == JsonToken.NULL) {
            jsonReader.nextNull();
            return null;
        }
        return LocalDate.parse(jsonReader.nextString(), formatter);
    }
}
