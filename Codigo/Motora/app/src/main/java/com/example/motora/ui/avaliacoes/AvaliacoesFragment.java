package com.example.motora.ui.avaliacoes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.motora.databinding.FragmentAvaliacoesBinding;

public class AvaliacoesFragment extends Fragment {

    private FragmentAvaliacoesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AvaliacoesViewModel avaliacoesViewModel =
                new ViewModelProvider(this).get(AvaliacoesViewModel.class);

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        binding = FragmentAvaliacoesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textAvaliacoes;
        avaliacoesViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}