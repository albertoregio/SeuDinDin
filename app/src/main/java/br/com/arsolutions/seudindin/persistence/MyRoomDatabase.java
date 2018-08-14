package br.com.arsolutions.seudindin.persistence;

import com.example.regio.seudindin.R;

import br.com.arsolutions.seudindin.persistence.dao.AccountDAO;
import br.com.arsolutions.seudindin.persistence.dao.CategoryDAO;
import br.com.arsolutions.seudindin.persistence.entity.AccountEntity;
import br.com.arsolutions.seudindin.persistence.entity.CategoryEntity;
import br.com.arsolutions.seudindin.utils.ReadScript;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.IOException;

// Classe responsavel por recuperar uma instancia do database
@Database(entities = {CategoryEntity.class, AccountEntity.class}, version = 1, exportSchema = false)
public abstract class MyRoomDatabase extends RoomDatabase {

    private static final String TAG = "MyActivity";

    // Declaração de variáveis
    public abstract CategoryDAO categoryDAO();
    public abstract AccountDAO accountDAO();
    private static MyRoomDatabase INSTANCE;


    // Recupera uma instancia do database - Singleton
    public static MyRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MyRoomDatabase.class) {
                if (INSTANCE == null) {
                    Log.d(TAG, "Iniciando o debug");
                    INSTANCE = buildDatabase(context);
                }

            }
        }

        return INSTANCE;
    }


    private static MyRoomDatabase buildDatabase(final Context context) {
        return Room.databaseBuilder(context,
                MyRoomDatabase.class, "my_database")
                .fallbackToDestructiveMigration()
                .addCallback(new RoomDatabase.Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        Log.d(TAG, "oncreate database");
                        super.onCreate(db);
                        try {
                            ReadScript.insertFromFile(context, R.raw.populate_db, db);
                        } catch (IOException e) {
                            Log.d("DB Population Error", e.toString());
                        }                    }
                })
//                            .allowMainThreadQueries()
                .build();
    }
}
