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
import me.citrafa.mycollegeassistant.AdapterRecycleView.AdapterCatatanRV;
import me.citrafa.mycollegeassistant.ModelClass.CatatanModel;
import me.citrafa.mycollegeassistant.R;

/**
 * Created by SENSODYNE on 14/05/2017.
 */

public class menuCatatan extends Fragment {
    @BindView(R.id.fabAddCatatan) FloatingActionButton fab;
    @BindView(R.id.recyclerC) RecyclerView recyclerView;

    AdapterCatatanRV adapter;
    Realm realm;
    OrderedRealmCollection<CatatanModel> data;
    RealmResults<CatatanModel> catatanModels;
    FragmentManager fm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu_catatan, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        initRecycler();
        fm = getFragmentManager();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frmCatatan c = new frmCatatan();
                Bundle bundle = new Bundle();
                bundle.putInt("idC",0);
                c.setArguments(bundle);
                fm.beginTransaction().add(R.id.fragmentmenuCatatan,c).addToBackStack(null).commit();
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

    @Override
    public void onResume() {
        super.onResume();
    }

    public void initRecycler(){
        realm = Realm.getDefaultInstance();
        data = realm.where(CatatanModel.class).equalTo("status",true).findAll();
        catatanModels = realm.where(CatatanModel.class).equalTo("status",true).findAll();
        adapter = new AdapterCatatanRV(this,data,catatanModels);
        final LinearLayoutManager layout = new LinearLayoutManager(getActivity());
        layout.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layout);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
    }

}
