package kg.geektech.weaather.ui;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import kg.geektech.weaather.data.models.days.City;
import kg.geektech.weaather.data.models.days.List2;
import kg.geektech.weaather.databinding.Item5daysBinding;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {
    private List<List2> weatherDays = new ArrayList<>();
    private City city;

    public void setCity(City city) {
        this.city = city;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Item5daysBinding binding = Item5daysBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new ViewHolder(binding);
    }

    public void setWeatherDays(List<List2> weatherDays) {
        this.weatherDays = weatherDays;
        notifyDataSetChanged();
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(weatherDays.get(position));
    }

    @Override
    public int getItemCount() {
        return weatherDays.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private Item5daysBinding binding;

        public ViewHolder(@NonNull Item5daysBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void onBind(List2 weatherNextDay1) {
            binding.tvMax.setText(weatherNextDay1.getMain().getTempMax().toString());
            binding.tvMin.setText(weatherNextDay1.getMain().getTempMin().toString());
            binding.tvDayDate.setText(getDate(weatherNextDay1.getDt(),"E, dd"));
        }
    }
    public static String getDate(Integer milliSeconds, String dateFormat) {

        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }
}
