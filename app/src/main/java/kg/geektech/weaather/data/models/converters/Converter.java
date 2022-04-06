package kg.geektech.weaather.data.models.converters;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import kg.geektech.weaather.data.models.MainResponse;

public class Converter {


    @TypeConverter
    public String fromMainString(MainResponse response) {
        if (response == null) {
            return null;
        }
        Gson gson = new Gson();
        Type type = new TypeToken<MainResponse>() {
        }.getType();
        return gson.toJson(response, type);
    }

    @TypeConverter
    public MainResponse fromMainString(String respString) {
        if (respString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<MainResponse>() {
        }.getType();
        return gson.fromJson(respString, type);
    }


}
