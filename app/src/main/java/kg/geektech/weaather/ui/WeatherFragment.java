package kg.geektech.weaather.ui;

import static kg.geektech.weaather.common.Status.ERROR;
import static kg.geektech.weaather.common.Status.LOADING;
import static kg.geektech.weaather.common.Status.SUCCESS;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import kg.geektech.weaather.ConnectionDetector;
import kg.geektech.weaather.R;
import kg.geektech.weaather.base.BaseFragment;
import kg.geektech.weaather.data.models.MainResponse;
import kg.geektech.weaather.data.models.Sys;
import kg.geektech.weaather.databinding.FragmentWeatherBinding;
import kg.geektech.weaather.di.AppModule;
import kg.geektech.weaather.room.WeatherDao;

@AndroidEntryPoint
public class WeatherFragment extends BaseFragment<FragmentWeatherBinding> {

    private WeatherViewModel viewModel;
    private NavController controller;
    private WeatherFragmentArgs args;
    private WeatherAdapter adapter;
    Boolean isInternetPresent = false;
    ConnectionDetector cd;



    @Inject
    WeatherDao dao;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new WeatherAdapter();
        args = WeatherFragmentArgs.fromBundle(getArguments());
        cd = new ConnectionDetector(requireContext().getApplicationContext());
    }

    @Override
    protected FragmentWeatherBinding bind() {
        return FragmentWeatherBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void setupObservers() {
        //if (ConnectingToInternet(getContext()) == )
        isInternetPresent = cd.ConnectingToInternet();

        if (isInternetPresent) {
            viewModel.liveData.observe(getViewLifecycleOwner(), mainResponseResource -> {
                switch (mainResponseResource.status) {
                    case SUCCESS: {
                        setData(mainResponseResource.data);
                        binding.progress.setVisibility(View.GONE);
                        break;
                    }
                    case ERROR: {
                        binding.progress.setVisibility(View.GONE);
                        break;
                    }
                    case LOADING: {
                        binding.progress.setVisibility(View.VISIBLE);
                        break;
                    }
                }
            });
            viewModel.liveData2.observe(getViewLifecycleOwner(), mainResponse2Resource -> {
                switch (mainResponse2Resource.status) {
                    case SUCCESS: {
                        adapter.setWeatherDays(mainResponse2Resource.data.getList());
                        adapter.setCity(mainResponse2Resource.data.getCity());
                        //adapter.set
                        binding.progress.setVisibility(View.GONE);
                        break;
                    }
                    case ERROR: {
                        binding.progress.setVisibility(View.GONE);
                        break;
                    }
                    case LOADING: {
                        binding.progress.setVisibility(View.VISIBLE);
                        break;
                    }
                }
            });
        }else {
            setData(viewModel.getWeatherFromDb());
        }

    }


    @Override
    protected void setupViews() {
        viewModel = new ViewModelProvider(requireActivity()).get(WeatherViewModel.class);
        binding.recycler5Days.setAdapter(adapter);

    }

    @Override
    protected void setupListeners() {
        controller = Navigation.findNavController(requireActivity(), R.id.nav_host);
        binding.tvLocation.setOnClickListener(view -> {
            controller.navigate(R.id.action_weatherFragment_to_searchFragment);
        });
    }


    private void setData(MainResponse response) {
        String urlImg = "https://openweathermap.org/img/wn/" + response.getWeather().get(0).getIcon() + ".png";
        String maxTemp = Math.round(response.getMain().getTempMax()) + "°C";
        String wind = (int) Math.round(response.getWind().getSpeed()) + " km/h";
        String minTemp = (int) Math.round(response.getMain().getTempMin()) + "°C";
        String humidity = response.getMain().getHumidity() + "%";
        String barometer = response.getMain().getPressure() + "mBar";
        String mainWeather = response.getWeather().get(0).getMain();

        String tempNow = String.valueOf((int) Math.round(response.getMain().getTemp()));

        Glide.with(requireActivity()).load(urlImg).into(binding.ivWeather);
        binding.tvTempVariationUp.setText(maxTemp);
        binding.tvTempVariationDown.setText(minTemp);
        binding.tvWind.setText(wind);
        binding.tvHumidity.setText(humidity);
        binding.tvTemperature.setText(tempNow);
        binding.tvBarometer.setText(barometer);
        binding.tvWeather.setText(mainWeather);

        binding.tvLocation.setText(response.getName());
        binding.tvSunrise.setText(getDate(response.getSys().getSunrise(), "hh:mm a")); // a-взвращает AM
        binding.tvSunset.setText(getDate(response.getSys().getSunset(), "hh:mm a"));
        binding.tvDaytime.setText(getDate(response.getDt(), "hh:mm"));

        Date currentDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("E, dd MMM yyyy", Locale.getDefault());
        String dateText = dateFormat.format(currentDate);
        binding.tvDate.setText(dateText);
    }

    public static String getDate(Integer milliSeconds, String dateFormat) {

        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    @Override
    protected void callRequests() {
        if(args.getCity() != null) {
            viewModel.getWeather(args.getCity());
            viewModel.getWeather5Days(args.getCity());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Calendar uh = Calendar.getInstance();
        int timeOfDay = uh.get(Calendar.HOUR_OF_DAY);

        if (timeOfDay >= 6 && timeOfDay < 20) {
            binding.ivDayNight.setImageResource(R.drawable.ic_graphic);
        } else {
            binding.ivDayNight.setImageResource(R.drawable.ic_graphic__1_);
        }
    }
}