package com.pereira299.app_todo.adapters;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pereira299.app_todo.R;
import com.pereira299.app_todo.dao.CompromissoDao;
import com.pereira299.app_todo.entities.Compromisso;
import com.pereira299.app_todo.listeners.OptionsDialogListener;
import com.pereira299.app_todo.screens.MainActivity;
import com.pereira299.app_todo.screens.VerCompromissoActivity;
import com.pereira299.app_todo.screens.dialog.DialogOptions;

import java.text.SimpleDateFormat;
import java.util.List;

public class CompromissoAdapter extends RecyclerView.Adapter<CompromissoAdapter.CompromissoHolder> {
    private List<Compromisso> compromissos;
    private CompromissoDao dao;

    public CompromissoAdapter(MainActivity mainActivity, List<Compromisso> compromissos, CompromissoDao dao) {
        this.compromissos = compromissos;
        this.dao = dao;
    }

    @NonNull
    @Override
    public CompromissoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.compromisso_item, parent, false);
        return new CompromissoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CompromissoHolder holder, int position) {
        Compromisso compromisso = compromissos.get(position);
        holder.bind(compromisso);
    }

    @Override
    public int getItemCount() {
        return compromissos.size();
    }

    class CompromissoHolder extends RecyclerView.ViewHolder {
        private TextView textViewDescricao;
        private TextView textViewValor;
        private TextView textViewData;

        private Compromisso compromisso;

        public CompromissoHolder(View itemView) {
            super(itemView);
            textViewDescricao = itemView.findViewById(R.id.textViewDescricao);
            textViewValor = itemView.findViewById(R.id.textViewValor);
            textViewData = itemView.findViewById(R.id.textViewData);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), VerCompromissoActivity.class);
                    intent.putExtra("compromisso", compromisso);
                    v.getContext().startActivity(intent);
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    DialogOptions opt = new DialogOptions(v.getContext());
                    opt.setOptionsListener(new OptionsDialogListener() {
                        @Override
                        public void onFeitoClick() {
                            compromisso.setExecutado(true);
                            dao.update(compromisso);
                        }

                        @Override
                        public void onRecebidoClick() {
                            compromisso.setRecebido(true);
                            dao.update(compromisso);
                        }

                        @Override
                        public void onCanceladoClick() {
                            return;
                        }
                    });
                    opt.show();
                    return true;
                }
            });
        }

        public void bind(Compromisso compromisso) {
            textViewDescricao.setText(compromisso.getTitulo());
            textViewValor.setText(String.format("R$ %,.2f",compromisso.getValor()));
            this.compromisso = compromisso;

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String dataFormatada = dateFormat.format(compromisso.getDataHora());
            textViewData.setText(dataFormatada);
        }

        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), VerCompromissoActivity.class);
            intent.putExtra("compromisso", this.compromisso);
            v.getContext().startActivity(intent);
        }

        public boolean onLongClick(View v) {
            DialogOptions opt = new DialogOptions(v.getContext());
            opt.setOptionsListener(new OptionsDialogListener() {
                @Override
                public void onFeitoClick() {
                    compromisso.setExecutado(true);
                    dao.update(compromisso);
                }

                @Override
                public void onRecebidoClick() {
                    compromisso.setRecebido(true);
                    dao.update(compromisso);
                }

                @Override
                public void onCanceladoClick() {
                    return;
                }
            });
            return true;
        }
    }
}