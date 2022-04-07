package kg.geektech.weaather.data.repositories;

import android.content.res.Resources;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import javax.inject.Inject;

import kg.geektech.weaather.App;
import kg.geektech.weaather.common.Resource;
import kg.geektech.weaather.data.models.MainResponse;
import kg.geektech.weaather.data.models.days.MainResponse2;
import kg.geektech.weaather.data.remote.WeatherApi;
import kg.geektech.weaather.room.WeatherDao;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainRepositories {
    private WeatherApi api;
    private WeatherDao dao;

    public MainRepositories(WeatherApi api, WeatherDao dao) {
        this.api = api;
        this.dao = dao;
    }

    /*private String city;

    public void setCity(String city) {
        this.city = city;
    }*/

    @Inject
    public MainRepositories(WeatherApi api) {
        this.api = api;
    }

    public MutableLiveData<Resource<MainResponse>> getWeather(String city) {
        MutableLiveData<Resource<MainResponse>> liveData = new MutableLiveData<>();
        liveData.setValue(Resource.loading());
        api.getApi(city, "89ac1f837c318c7a142986110e0b9c02", "metric").enqueue(new Callback<MainResponse>() {
            @Override
            public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    liveData.setValue(Resource.success(response.body()));
                    dao.insertAll(response.body());
                } else {
                    liveData.setValue(Resource.error(response.message(), null));

                }
            }

            @Override
            public void onFailure(Call<MainResponse> call, Throwable t) {
                liveData.setValue(Resource.error(t.getLocalizedMessage(), null));
            }
        });
        return liveData;
    }

    public MutableLiveData<Resource<MainResponse2>> getApi5Days(String cities) {
        MutableLiveData<Resource<MainResponse2>> liveData2 = new MutableLiveData<>();
        liveData2.setValue(Resource.loading());
        api.getApi5Days(cities, "89ac1f837c318c7a142986110e0b9c02", "metric").enqueue(
                new Callback<MainResponse2>() {
                    @Override
                    public void onResponse(Call<MainResponse2> call, Response<MainResponse2> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            liveData2.setValue(Resource.success(response.body()));
                        } else {
                            liveData2.setValue(Resource.error(response.message(), null));
                        }
                    }

                    @Override
                    public void onFailure(Call<MainResponse2> call, Throwable t) {
                        liveData2.setValue(Resource.error(t.getLocalizedMessage(), null));
                    }
                });
        return liveData2;
    }

    public List<MainResponse> getWeatherFromDb() {
        return dao.getAllWeather();
    }

}
