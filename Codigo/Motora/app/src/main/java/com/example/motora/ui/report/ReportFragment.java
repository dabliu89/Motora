package com.example.motora.ui.report;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.motora.databinding.FragmentReportBinding;
import com.example.motora.ui.perfil.PerfilViewModel;

public class ReportFragment extends Fragment {

    private FragmentReportBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ReportViewModel reportViewModel =
                new ViewModelProvider(this).get(ReportViewModel.class);

        binding = FragmentReportBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textReport;
        reportViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
