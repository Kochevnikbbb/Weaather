package kg.geektech.weaather.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
import kg.geektech.weaather.R;
import kg.geektech.weaather.base.BaseFragment;
import kg.geektech.weaather.databinding.FragmentSearchBinding;


public class SearchFragment extends BaseFragment<FragmentSearchBinding> {

    @Override
    protected FragmentSearchBinding bind() {
        return FragmentSearchBinding.inflate(getLayoutInflater());
    }



    @Override
    protected void setupViews() {

    }

    @Override
    protected void setupListeners() {
        binding.btn.setOnClickListener(view -> {
            String city = binding.etText.getText().toString();
            SearchFragmentDirections.ActionSearchFragmentToWeatherFragment action =
                    SearchFragmentDirections.actionSearchFragmentToWeatherFragment();
            action.setCity(city);
            controller.navigate(action);
        });


    }

    @Override
    protected void setupObservers() {

    }

    @Override
    protected void callRequests() {

    }
}