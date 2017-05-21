package me.citrafa.mycollegeassistant.CustomWidget;

/**
 * Created by SENSODYNE on 21/05/2017.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import me.citrafa.mycollegeassistant.R;

public class AdapterSpinner extends BaseAdapter {
    Context context;
    int flags[];
    String[] countryNames;
    LayoutInflater inflter;

    public AdapterSpinner(Context applicationContext, String[] countryNames) {
        this.context = applicationContext;
        this.countryNames = countryNames;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return countryNames.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.spinner_layout, null);

        tvMuseo names = (tvMuseo) view.findViewById(R.id.listSpinner);
        names.setText(countryNames[i]);
        return view;
    }
}