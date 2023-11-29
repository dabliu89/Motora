package com.example.motora.ui.avaliacoes;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.motora.R; // Certifique-se de usar o pacote correto aqui
import com.example.motora.model.AvaliacaoResultado;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.List;

public class ListAvaliacoesAdapter extends BaseAdapter implements Filterable {

    private Context context;
    private List<AvaliacaoResultado> avaliacoesList;
    private List<AvaliacaoResultado> filteredList;
    private ValueFilter valueFilter;

    public ListAvaliacoesAdapter(Context context, List<AvaliacaoResultado> avaliacoesList) {
        this.context = context;
        this.avaliacoesList = avaliacoesList;
        this.filteredList = avaliacoesList;
    }

    @Override
    public int getCount() {
        return filteredList.size();
    }

    @Override
    public Object getItem(int position) {
        return filteredList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_avaliacoes_item, null);
        }

        ShapeableImageView listImage = convertView.findViewById(R.id.listAvaliacaoImage); // Use ShapeableImageView

        TextView tituloTextView = convertView.findViewById(R.id.avaliacaoTitulo);
        TextView alunoEDataTextView = convertView.findViewById(R.id.alunoEData);

        AvaliacaoResultado avaliacao = filteredList.get(position);

        tituloTextView.setText(avaliacao.getTitulo());
        alunoEDataTextView.setText(avaliacao.getAluno());

        if (!avaliacao.getMessage().equals("ZONA SAUDÁVEL")) {
            listImage.setBackgroundColor(ContextCompat.getColor(context, R.color.ActionBarTitleColor));
        }

        if (avaliacao.getTipo().equals("ApFRS")) {
            listImage.setImageResource(R.drawable.aptidao_icon);
        } else {
            listImage.setImageResource(R.drawable.antropometria_icon);
        }

        return convertView;
    }

    @Override
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }

    private class ValueFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0) {
                List<AvaliacaoResultado> filterList = new ArrayList<>();
                for (int i = 0; i < avaliacoesList.size(); i++) {
                    if (avaliacoesList.get(i).getTitulo().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        filterList.add(avaliacoesList.get(i));
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = avaliacoesList.size();
                results.values = avaliacoesList;
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredList = (List<AvaliacaoResultado>) results.values;
            notifyDataSetChanged();
        }
    }
}