package kg.geektech.weaather;

import android.app.Application;

import dagger.hilt.android.HiltAndroidApp;
import kg.geektech.weaather.data.remote.RetrofitClient;
import kg.geektech.weaather.data.remote.WeatherApi;
import kg.geektech.weaather.data.repositories.MainRepositories;

@HiltAndroidApp
public class App extends Application {

}
