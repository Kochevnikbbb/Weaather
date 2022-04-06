package kg.geektech.weaather.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import kg.geektech.weaather.data.models.MainResponse;

@Dao
public interface WeatherDao {

    @Query("SELECT * FROM mainresponse")
    List<MainResponse> getAllWeather();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(MainResponse weather);

    @Delete
    void delete(MainResponse user);
}
