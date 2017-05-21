package me.citrafa.mycollegeassistant.Activity.Fragment;

import android.content.Intent;
import android.graphics.Paint;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

import me.citrafa.mycollegeassistant.AdapterRecycleView.AdapterJadwalKuliahNew;

import me.citrafa.mycollegeassistant.ModelClass.JadwalKuliahModel;
import me.citrafa.mycollegeassistant.OperationRealm.RealmController;
import me.citrafa.mycollegeassistant.R;

/**
 * Created by SENSODYNE on 13/04/2017.
 */

public class menuJadwalKuliah extends Fragment{

    private static RecyclerView recyclerView;
    private Realm realm;
    private AdapterJadwalKuliahNew adapter;
    private Context mContex;
    private FloatingActionButton fab;
    private Paint p = new Paint();
    private OrderedRealmCollection<JadwalKuliahModel> data;
    String TAG = "";



    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
    protected RecyclerView.LayoutManager mLayoutManager;


    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public menuJadwalKuliah() {
        // Required empty public constructor
    }

    public static menuJadwalKuliah newInstance(String param1, String param2) {
        menuJadwalKuliah fragment = new menuJadwalKuliah();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.realm = RealmController.with(getActivity()).getRealm();

        realm.getDefaultInstance();
        View rootView = inflater.inflate(R.layout.fragment_menu_jadwal_kuliah, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerjk);
        data = realm.where(JadwalKuliahModel.class).equalTo("status_jk",true).findAll();
        RealmResults<JadwalKuliahModel> jkm = realm.where(JadwalKuliahModel.class).equalTo("status_jk",true).findAll().sort("nohari", Sort.ASCENDING);
            adapter = new AdapterJadwalKuliahNew(data,jkm, this);
            final LinearLayoutManager layout = new LinearLayoutManager(getActivity());
            layout.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(layout);
            recyclerView.setAdapter(adapter);

        fab = (FloatingActionButton)rootView.findViewById(R.id.fabAddJadwalKuliah);
        fab.show();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("idJK",0);
                frmJadwalKuliah f = new frmJadwalKuliah();
                f.setArguments(bundle);
                getFragmentManager().beginTransaction().add(R.id.tabJadwalKuliah,f).addToBackStack(null).commit();
                fab.hide();
            }
        });
                recyclerView.setHasFixedSize(true);
        Log.d(TAG, "TAG : OnCreateView Fragment");
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);

        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContex = context;
        Log.d(TAG, "tag : OnAttach Fragment");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
