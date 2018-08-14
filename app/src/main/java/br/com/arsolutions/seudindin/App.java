package br.com.arsolutions.seudindin;

import android.app.Application;
import android.content.Context;

public class App extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static Context getContext() {
        return context;
    }
    public static String getResourceName(int reference) {return context.getResources().getResourceEntryName(reference);}
    public static int getResourceReference(String name) {return context.getResources().getIdentifier(name, "id", context.getPackageName());}
    public static int getResourceDrawable(String name) {return context.getResources().getIdentifier(name, "drawable", context.getPackageName());}
    public static int getResourceColor(String name) {return context.getResources().getIdentifier(name, "color", context.getPackageName());}
}
