package com.androidvoyage.bmc.utils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Gson default date adapter converts to local time which is a problem for us
 * Hence this!
 */
public class UTCDateTypeAdapter implements JsonSerializer<Date>, JsonDeserializer<Date> {
    private final DateFormat dateFormat;

    public UTCDateTypeAdapter(String format) {
        dateFormat = new SimpleDateFormat(format, Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    @Override
    public synchronized JsonElement serialize(Date date, Type type,
                                              JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(dateFormat.format(date));
    }

    @Override
    public synchronized Date deserialize(JsonElement jsonElement, Type type,
                                         JsonDeserializationContext jsonDeserializationContext) {
        try {
            return dateFormat.parse(jsonElement.getAsString());
        } catch (ParseException e) {
            try {
                SimpleDateFormat fallbackFormat = new SimpleDateFormat("yyyy-MM-dd");
                fallbackFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                return fallbackFormat.parse(jsonElement.getAsString());
            } catch (Exception fallbackException) {
            }

            throw new JsonParseException(e);
        }
    }
}

