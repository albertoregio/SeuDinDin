package com.example.regio.seudindin.categories.support;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.example.regio.seudindin.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ColorSpinnerAdapter extends ArrayAdapter<Integer> implements SpinnerAdapter {

    private final LayoutInflater mInflater;
    private final Context mContext;
    private final int mResource;
    private final List<Integer> mObjects;

    public ColorSpinnerAdapter(Context context, int resource, List objects) {
        super(context,resource, 0, objects);
        this.mContext = context;
        this.mObjects = objects;
        this.mResource = resource;
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);

    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    private View createItemView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final View view = mInflater.inflate(mResource, parent, false);
        ImageView colorPicker = (ImageView) view.findViewById(R.id.color_picker_circle_shape);

        int color = ContextCompat.getColor(mContext, mObjects.get(position));

        final GradientDrawable shapeDrawable = (GradientDrawable) colorPicker.getDrawable();
        shapeDrawable.setColor(color);

        return view;

    }
}
