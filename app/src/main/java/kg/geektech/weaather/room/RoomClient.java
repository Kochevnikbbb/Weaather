package kg.geektech.weaather.room;

import android.content.Context;

import androidx.room.Room;

public class RoomClient {
    public WeatherDataBase weatherDataBase(Context context){
        return Room.databaseBuilder(context,WeatherDataBase.class, "datebase")
                .allowMainThreadQueries().build();
    }
}
