package com.example.motora.ui.avaliacoes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.motora.R;
import com.example.motora.dao.DAOUsuario;
import com.example.motora.model.Aluno;
import com.example.motora.model.AvaliacaoResultado;

import java.util.ArrayList;

public class ListAvaliacoesAdapter extends ArrayAdapter<AvaliacaoResultado> {


    public ListAvaliacoesAdapter(@NonNull Context context, ArrayList<AvaliacaoResultado> dataArrayList) {
        super(context, R.layout.list_avaliacoes_item, dataArrayList);
    }

    public static String alunoName;

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        AvaliacaoResultado avaliacaoResultado = getItem(position);
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.list_avaliacoes_item, parent, false);
        }
            ImageView listImage = view.findViewById(R.id.listAvaliacaoImage);
            TextView listTitle = view.findViewById(R.id.avaliacaoTitulo);
            TextView listAlunoData = view.findViewById(R.id.alunoEData);

            listImage.setImageResource(avaliacaoResultado.getImage());
            listAlunoData.setText(avaliacaoResultado.getAluno());
            listTitle.setText(avaliacaoResultado.getTitulo());

        return view;
    }


}
