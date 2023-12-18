package com.pereira299.app_todo.screens.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.pereira299.app_todo.R;
import com.pereira299.app_todo.listeners.OptionsDialogListener;

public class DialogOptions extends Dialog {
    private AlertDialog.Builder builder;
    private Context context;
    private OptionsDialogListener listener;

    public DialogOptions(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public void setOptionsListener(OptionsDialogListener events){
        this.listener = events;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options_dialog);

        Button btnFeito = findViewById(R.id.btnFeito);
        Button btnRecebido = findViewById(R.id.btnRecebido);
        Button btnCancelar = findViewById(R.id.btnCancelar);

        btnFeito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Feito
                if(listener != null){
                    listener.onFeitoClick();
                }
                Toast.makeText(context, "Feito", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });

        btnRecebido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Recebido
                if(listener != null){
                    listener.onRecebidoClick();
                }
                Toast.makeText(context, "Recebido", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cancelar
                if(listener != null){
                    listener.onCanceladoClick();
                }
                Toast.makeText(context, "Cancelar", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });
    }
}