package com.pereira299.app_todo.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.pereira299.app_todo.converters.DateConverter;
import com.pereira299.app_todo.dao.CompromissoDao;
import com.pereira299.app_todo.entities.Compromisso;

@Database(entities = {Compromisso.class}, version = 3)
@TypeConverters({DateConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract CompromissoDao compromissoDao();
}