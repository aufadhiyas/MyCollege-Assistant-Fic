package me.citrafa.mycollegeassistant.Activity.Fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmResults;
import me.citrafa.mycollegeassistant.AdapterRecycleView.AdapterJadwalLainRV;
import me.citrafa.mycollegeassistant.ModelClass.JadwalLainModel;
import me.citrafa.mycollegeassistant.R;

public class menuJadwalLain extends Fragment {
    @BindView(R.id.fabAddJadwalLain) FloatingActionButton fab;
    @BindView(R.id.recyclerJL) RecyclerView recyclerView;
    AdapterJadwalLainRV adapter;
    Realm realm;
    OrderedRealmCollection<JadwalLainModel> data;
    RealmResults<JadwalLainModel> jadwalLainModels;
    FragmentManager fm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu_jadwal_lain, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fm = getFragmentManager();
        ButterKnife.bind(this,view);
        initRecycler();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frmJadwalLain c = new frmJadwalLain();
                Bundle bundle = new Bundle();
                bundle.putInt("idJL",0);
                c.setArguments(bundle);
                fm.beginTransaction().add(R.id.fragmentmenuJadwalLain, c).addToBackStack(null).commit();
                fab.hide();
            }
        });
        fm.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if(fm.getBackStackEntryCount() == 0){
                    fab.show();
                }
            }
        });
    }


    public void initRecycler(){
        realm = Realm.getDefaultInstance();
        data = realm.where(JadwalLainModel.class).equalTo("status_jl",true).findAll();
        jadwalLainModels = realm.where(JadwalLainModel.class).equalTo("status_jl",true).findAll();
        adapter = new AdapterJadwalLainRV(getActivity(),data,jadwalLainModels);
        final LinearLayoutManager layout = new LinearLayoutManager(getActivity());
        layout.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layout);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
    }
}
