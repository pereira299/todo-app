package com.pereira299.app_todo.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.pereira299.app_todo.entities.Compromisso;

import java.util.Date;
import java.util.List;

@Dao
public interface CompromissoDao {
    @Insert
    void insert(Compromisso compromisso);

    @Update
    void update(Compromisso compromisso);

    @Delete
    void delete(Compromisso compromisso);

    @Query("SELECT * FROM compromisso_table")
    LiveData<List<Compromisso>> getAll();

    @Query("SELECT * FROM compromisso_table WHERE data_hora = :dataHora")
    LiveData<List<Compromisso>> getByDate(Date dataHora);

    @Query("SELECT * FROM compromisso_table WHERE executado = :executado")
    LiveData<List<Compromisso>> getByExecution(boolean executado);

    @Query("SELECT * FROM compromisso_table WHERE recebido = :recebido")
    LiveData<List<Compromisso>> getByReceipt(boolean recebido);
}