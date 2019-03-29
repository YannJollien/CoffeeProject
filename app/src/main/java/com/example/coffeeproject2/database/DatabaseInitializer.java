package com.example.coffeeproject2.database;

import android.os.AsyncTask;
import android.util.Log;

import com.example.coffeeproject2.database.AppDatabase;
import com.example.coffeeproject2.database.entity.User;


/**
 * Generates dummy data
 */
public class DatabaseInitializer {

    public static final String TAG = "DatabaseInitializer";

    public static void populateDatabase(final AppDatabase db) {
        Log.i(TAG, "Inserting demo data.");
        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();
    }

    private static void addClient(final AppDatabase db, final String email, final String firstName,
                                  final String lastName, final String password) {
        User user = new User();
        db.userDao().insertUser(user);
    }


    private static void populateWithTestData(AppDatabase db) {

        addClient(db,
                "m.p@fifa.com", "Michel", "Platini", "michel1"
        );
        addClient(db,
                "s.b@fifa.com", "Sepp", "Blatter", "sepp1"
        );
        addClient(db,
                "e.s@fifa.com", "Ebbe", "Schwartz", "ebbe1"
        );
        addClient(db,
                "a.c@fifa.com", "Aleksander", "Ceferin", "aleksander1"
        );

        try {
            // Let's ensure that the clients are already stored in the database before we continue.
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final AppDatabase database;

        PopulateDbAsync(AppDatabase db) {
            database = db;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            populateWithTestData(database);
            return null;
        }

    }
}
