package com.pereira299.app_todo.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;

import com.pereira299.app_todo.converters.DateConverter;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "compromisso_table")
public class Compromisso implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "titulo")
    private String titulo;
    @ColumnInfo(name = "data_hora")
    private Date dataHora;

    @ColumnInfo(name = "descricao")
    private String descricao;

    @ColumnInfo(name = "valor")
    private double valor;

    @ColumnInfo(name = "executado")
    private boolean executado;

    @ColumnInfo(name = "recebido")
    private boolean recebido;

    //Getters
    public int getId() {return id;}
    public String getTitulo() {return titulo;}
    public Date getDataHora() {return dataHora;}
    public String getDescricao() {return descricao;}
    public double getValor() {return valor;}
    public boolean isExecutado() {return executado;}
    public boolean isRecebido() {return recebido;}

    //Setters
    public void setId(int id) {this.id = id;}
    public void setTitulo(String title) {this.titulo = title;}
    public void setDataHora(Date dataHora) {this.dataHora = dataHora;}
    public void setDescricao(String descricao) {this.descricao = descricao;}
    public void setValor(double valor) {this.valor = valor;}
    public void setExecutado(boolean executado) {this.executado = executado;}
    public void setRecebido(boolean recebido) {this.recebido = recebido;}
}