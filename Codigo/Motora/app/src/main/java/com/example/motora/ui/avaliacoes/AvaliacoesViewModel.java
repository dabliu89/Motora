package com.example.motora.ui.avaliacoes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AvaliacoesViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public AvaliacoesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}