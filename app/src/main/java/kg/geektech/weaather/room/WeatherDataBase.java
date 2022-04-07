package kg.geektech.weaather.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import kg.geektech.weaather.data.models.MainResponse;
import kg.geektech.weaather.data.models.converters.CloudConverter;
import kg.geektech.weaather.data.models.converters.Converter;
import kg.geektech.weaather.data.models.converters.CoordConverter;
import kg.geektech.weaather.data.models.converters.MainConverter;
import kg.geektech.weaather.data.models.converters.SysConverter;
import kg.geektech.weaather.data.models.converters.WeatherConverter;
import kg.geektech.weaather.data.models.converters.WindConverter;

@Database(entities = {MainResponse.class}, version = 1,exportSchema = false)
@TypeConverters({MainConverter.class, CloudConverter.class, CoordConverter.class,
        SysConverter.class, WeatherConverter.class, WindConverter.class})
public abstract class WeatherDataBase extends RoomDatabase {
    public abstract WeatherDao weatherDao();

}
