package kg.geektech.weaather;

import android.app.Application;

import kg.geektech.weaather.data.remote.RetrofitClient;
import kg.geektech.weaather.data.remote.WeatherApi;
import kg.geektech.weaather.data.repositories.MainRepositories;

public class App extends Application {
    private RetrofitClient retrofitClient;
    private WeatherApi api;
    public static MainRepositories repository;
    @Override
    public void onCreate() {
        super.onCreate();
        retrofitClient = new RetrofitClient();
        api = retrofitClient.provideApi();
        repository = new MainRepositories(api);
    }
}
