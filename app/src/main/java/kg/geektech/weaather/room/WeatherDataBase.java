package kg.geektech.weaather.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import kg.geektech.weaather.data.models.MainResponse;
import kg.geektech.weaather.data.models.converters.Converter;

@Database(entities = {MainResponse.class}, version = 1)
@TypeConverters({Converter.class})
public abstract class WeatherDataBase extends RoomDatabase {
    public abstract WeatherDao weatherDao();

}
