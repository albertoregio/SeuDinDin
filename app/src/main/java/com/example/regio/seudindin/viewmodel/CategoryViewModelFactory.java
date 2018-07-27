package com.example.regio.seudindin.viewmodel;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.FragmentActivity;

public class CategoryViewModelFactory {

    private static ICategoryViewModel model;
    public static int ARRAY = 0;
    public static int ROOM = 1;
    private static int type = ROOM;


    public static ICategoryViewModel getInstance(FragmentActivity activity) {
        if (model == null) {
            if (type == ARRAY)
                model = ViewModelProviders.of(activity).get(CategoryViewModel.class);
            else
                model = ViewModelProviders.of(activity).get(CategoryViewModelTest.class);
        }

        return model;
    }

}
