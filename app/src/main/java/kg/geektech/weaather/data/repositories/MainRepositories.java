package kg.geektech.weaather.data.repositories;

import android.content.res.Resources;

import androidx.lifecycle.MutableLiveData;

import kg.geektech.weaather.App;
import kg.geektech.weaather.common.Resource;
import kg.geektech.weaather.data.models.MainResponse;
import kg.geektech.weaather.data.remote.WeatherApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainRepositories {
    private WeatherApi api;
    private String city;

    public MainRepositories(WeatherApi api) {
        this.api = api;
    }

    public void setCity(String city) {
        this.city = city;
    }
    public MutableLiveData<Resource<MainResponse>> getCharacters(){
        MutableLiveData<Resource<MainResponse>> liveData = new MutableLiveData<>();
        liveData.setValue(Resource.loading());
        api.getApi("Bishkek","89ac1f837c318c7a142986110e0b9c02","metric").enqueue(new Callback<MainResponse>() {
            @Override
            public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                if (response.isSuccessful()&&response.body() != null){
                    liveData.setValue(Resource.success(response.body()));
                }else {
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
}
