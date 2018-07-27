package com.example.regio.seudindin.persistence;

import com.example.regio.seudindin.persistence.dao.CategoryDAO;
import com.example.regio.seudindin.model.CategoryModel;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

// Classe responsavel por recuperar uma instancia do database
@Database(entities = {CategoryModel.class}, version = 2, exportSchema = false)
public abstract class MyRoomDatabase extends RoomDatabase {

    // Declaração de variáveis
    public abstract CategoryDAO categoryDAO();
    private static MyRoomDatabase INSTANCE;


    // Recupera uma instancia do database - Singleton
    public static MyRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MyRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MyRoomDatabase.class, "my_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }

            }
        }

        return INSTANCE;
    }

}
