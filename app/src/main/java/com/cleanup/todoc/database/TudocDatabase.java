package com.cleanup.todoc.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.cleanup.todoc.MyApp;
import com.cleanup.todoc.database.dao.ProjectDao;
import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import java.util.concurrent.Executors;

@Database(entities = {Project.class, Task.class}, version = 1, exportSchema = false)
public abstract class TudocDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "tudocDatabase.db";
    // -- SINGLETON --
    private static volatile TudocDatabase INSTANCE;

    // -- DAO --
    public abstract ProjectDao projectDao();
    public abstract TaskDao taskDao();

    // -- INSTANCE --
    public static TudocDatabase getInstance(){
        if (INSTANCE == null){
            synchronized (TudocDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(MyApp.app.getApplicationContext(),
                            TudocDatabase.class, DATABASE_NAME)
                            .addCallback(prepopulateDatabase())
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static Callback prepopulateDatabase() {
        return new Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                Executors.newSingleThreadExecutor().execute(() -> INSTANCE.projectDao()
                        .createProject(new Project(1L, "Projet test 1", 0xFFEADAD1)));
                Executors.newSingleThreadExecutor().execute(() -> INSTANCE.projectDao()
                        .createProject(new Project(2L, "Projet test 2", 0xFFB4CDBA)));
                Executors.newSingleThreadExecutor().execute(() -> INSTANCE.projectDao()
                        .createProject(new Project(3L, "Projet test 3", 0xFFA3CED2)));
            }
        };
    }


    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {return null;}

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {return null;}

    @Override
    public void clearAllTables() {}
}
