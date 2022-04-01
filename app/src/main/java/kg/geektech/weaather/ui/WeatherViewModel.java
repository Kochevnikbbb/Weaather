package kg.geektech.weaather.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import kg.geektech.weaather.common.Resource;
import kg.geektech.weaather.data.models.MainResponse;
import kg.geektech.weaather.data.repositories.MainRepositories;

@HiltViewModel
public class WeatherViewModel extends ViewModel {

    public MainRepositories repository;

    @Inject
    public WeatherViewModel(MainRepositories repository) {
        this.repository = repository;
    }

    public LiveData<Resource<MainResponse>> liveData;

    public void getWeather(String city) {
        liveData = repository.getWeather(city);
    }


}
