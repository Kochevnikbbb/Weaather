package kg.geektech.weaather.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import kg.geektech.weaather.App;
import kg.geektech.weaather.common.Resource;
import kg.geektech.weaather.data.models.MainResponse;
import kg.geektech.weaather.data.repositories.MainRepositories;

public class WeatherViewModel extends ViewModel {
    public LiveData<Resource<MainResponse>> liveData;

    private String city;

    public void setCity(String city) {
        this.city = city;
    }

    public void getWeather() {
        liveData = App.repository.getCharacters();
    }
}
