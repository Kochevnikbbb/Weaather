package kg.geektech.weaather.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import kg.geektech.weaather.R;
import kg.geektech.weaather.data.models.MainResponse;
import kg.geektech.weaather.data.models.Sys;
import kg.geektech.weaather.databinding.FragmentWeatherBinding;


public class WeatherFragment extends Fragment {
    private WeatherViewModel viewModel;
    private FragmentWeatherBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentWeatherBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(WeatherViewModel.class);
        viewModel.getWeather();

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

    }

    private void setData(MainResponse response) {
        String urlImg = "https://openweathermap.org/img/wn/" + response.getWeather().get(0).getIcon() + ".png";
        String maxTemp = Math.round(response.getMain().getTempMax()) + "°C";
        String wind = String.valueOf((int) Math.round(response.getWind().getSpeed()))+" km/h";
        String minTemp = String.valueOf((int) Math.round(response.getMain().getTempMin())+ "°C");
        String humidity = String.valueOf(response.getMain().getHumidity())+ "%";
        String barometer = String.valueOf(response.getMain().getPressure())+ "mBar";
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
        //binding.tvDate.setText(response.getDt());
        binding.tvSunrise.setText(getDate(response.getSys().getSunrise(), "hh:mm") + " AM");
        binding.tvSunset.setText(getDate(response.getSys().getSunset(), "hh:mm") + " PM");
        binding.tvDaytime.setText(getDate(response.getDt(), "hh:mm"));

        Date currentDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("E, dd b yyyy", Locale.getDefault());
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