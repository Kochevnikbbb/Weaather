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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    protected void setupViews() {

    }

    @Override
    protected void setupListeners() {
        binding.btn.setOnClickListener(view -> {
            Prefs prefs = new Prefs(requireContext());
            prefs.saveCity(binding.etText.getText().toString());
            controller.navigateUp();
            controller.navigate(R.id.weatherFragment);
        });


    }

    @Override
    protected void setupObservers() {

    }

    @Override
    protected void callRequests() {

    }
}