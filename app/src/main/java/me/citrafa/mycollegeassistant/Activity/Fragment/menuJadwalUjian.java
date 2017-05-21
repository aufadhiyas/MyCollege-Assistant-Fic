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
import me.citrafa.mycollegeassistant.AdapterRecycleView.AdapterJadwalUjianRV;
import me.citrafa.mycollegeassistant.ModelClass.JadwalLainModel;
import me.citrafa.mycollegeassistant.ModelClass.JadwalUjianModel;
import me.citrafa.mycollegeassistant.R;

/**
 * Created by SENSODYNE on 14/05/2017.
 */

public class menuJadwalUjian extends Fragment {
    @BindView(R.id.fabAddJadwalUjian) FloatingActionButton fab;
    @BindView(R.id.recyclerJU) RecyclerView recyclerView;

    FragmentManager fm;
    AdapterJadwalUjianRV adapter;
    Realm realm;
    OrderedRealmCollection<JadwalUjianModel> data;
    RealmResults<JadwalUjianModel> jadwalUjianModels;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu_jadwal_ujian, container, false);
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
                frmJadwalUjian c = new frmJadwalUjian();
                Bundle bundle = new Bundle();
                bundle.putInt("idJU",0);
                c.setArguments(bundle);
                fm.beginTransaction().replace(R.id.fragmentmenuJadwalUjian, c).addToBackStack(null).commit();
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
        data = realm.where(JadwalUjianModel.class).equalTo("status_ju",true).findAll();
        jadwalUjianModels = realm.where(JadwalUjianModel.class).equalTo("status_ju",true).findAll();
        adapter = new AdapterJadwalUjianRV(getActivity(),data,jadwalUjianModels);
        final LinearLayoutManager layout = new LinearLayoutManager(getActivity());
        layout.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layout);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
    }
}
