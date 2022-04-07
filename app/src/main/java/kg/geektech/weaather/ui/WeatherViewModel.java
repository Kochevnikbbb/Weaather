package kg.geektech.weaather.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import kg.geektech.weaather.common.Resource;
import kg.geektech.weaather.data.models.MainResponse;
import kg.geektech.weaather.data.models.days.MainResponse2;
import kg.geektech.weaather.data.repositories.MainRepositories;
import kg.geektech.weaather.room.WeatherDao;

@HiltViewModel
public class WeatherViewModel extends ViewModel {

    public MainRepositories repository;
    public LiveData<Resource<MainResponse>> liveData;
    public LiveData<Resource<MainResponse2>> liveData2;

    @Inject
    public WeatherViewModel(MainRepositories repository) {
        this.repository = repository;
    }



    public void getWeather(String city) {
        liveData = repository.getWeather(city);
    }

    public void getWeather5Days(String city) {
        liveData2 = repository.getApi5Days(city);
    }

    public MainResponse getWeatherFromDb() {
        List<MainResponse> list = repository.getWeatherFromDb();
        return list.get(list.size() - 1);
    }

}
