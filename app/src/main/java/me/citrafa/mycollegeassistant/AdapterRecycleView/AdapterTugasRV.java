package me.citrafa.mycollegeassistant.AdapterRecycleView;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;
import me.citrafa.mycollegeassistant.Activity.Fragment.frmTugas;
import me.citrafa.mycollegeassistant.Activity.Fragment.menuTugas;
import me.citrafa.mycollegeassistant.CustomWidget.LibraryDateCustom;
import me.citrafa.mycollegeassistant.CustomWidget.tvMuseo;
import me.citrafa.mycollegeassistant.ModelClass.JadwalKuliahModel;
import me.citrafa.mycollegeassistant.ModelClass.TugasModel;
import me.citrafa.mycollegeassistant.OperationRealm.TugasOperation;
import me.citrafa.mycollegeassistant.R;

/**
 * Created by SENSODYNE on 27/04/2017.
 */

public class AdapterTugasRV extends RealmRecyclerViewAdapter<TugasModel, AdapterTugasRV.MyViewHolder> {

    private static final String TAG = "AdapterTugasRV";
    OrderedRealmCollection<TugasModel> data;
    RealmResults<TugasModel> tugasModels;
    Realm realm;
    RealmList<JadwalKuliahModel> jadwalKuliahModels;
    Context mContext;
    TugasOperation yo;
    menuTugas t;
    LibraryDateCustom ldc;

    public AdapterTugasRV(menuTugas t,@Nullable OrderedRealmCollection<TugasModel> data
            , RealmResults<TugasModel> tugasModels) {

        super(data, true);
        setHasStableIds(true);
        realm = Realm.getDefaultInstance();
        ldc = new LibraryDateCustom();
        this.tugasModels = tugasModels;
        yo = new TugasOperation();
        this.t = t;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rowtugas, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return tugasModels.size();
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final TugasModel tm = tugasModels.get(position);
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm");

        realm = Realm.getDefaultInstance();
        final JadwalKuliahModel jk = realm.where(JadwalKuliahModel.class).equalTo("Tugas.no_t",tm.getNo_t()).findFirst();
//        holder.txt1.setText(jk.getMakul_jk());
        SimpleDateFormat dateTime = new SimpleDateFormat(" dd/MM - HH:mm");

        if (tm.getWaktu_t() !=null){

            holder.txt2.setText(ldc.getHaridanTanggalUntukListSingle(tm.getWaktu_t())+""+dateTime.format(tm.getWaktu_t()));
        }else{
            holder.txt2.setText(jk.getHari_jk()+" - "+timeFormatter.format(jk.getWaktu_jk()));
        }

        if (tm.getAttlink_t()!=null){
            holder.imgFile.setVisibility(View.VISIBLE);
            holder.txt3.setVisibility(View.VISIBLE);
            String path = tm.getAttlink_t();
            String filename=path.substring(path.lastIndexOf("/")+1);
            holder.txt3.setText(filename);
        }
        holder.txt4.setText(tm.getDeskripsi_t());
        holder.imgMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v,position,tm.getNo_t(),jk.getMakul_jk(),jk.getNo_jk());
            }
        });
        holder.imgFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File myFile = new File(tm.getAttlink_t());
                try {
                    FileOpen.openFile(mContext, myFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        tvMuseo txt1,txt2,txt3,txt4;
        ImageButton imgFile,imgMore;

        public MyViewHolder(View itemView) {
            super(itemView);
            txt1 = (tvMuseo)itemView.findViewById(R.id.rowTugasMakul);
            txt2 = (tvMuseo)itemView.findViewById(R.id.rowTugasWaktu);
            txt3 = (tvMuseo)itemView.findViewById(R.id.rowTugasFileName);
            txt4 = (tvMuseo)itemView.findViewById(R.id.rowTugasDeskripsi);
            imgFile = (ImageButton)itemView.findViewById(R.id.rowTugasFilebtn);
            imgMore = (ImageButton)itemView.findViewById(R.id.rowTugasMoreBtn);
        }
    }
    private void showPopupMenu(View view,int position,int no,String Nama,int noJK){

        PopupMenu popup = new PopupMenu(view.getContext(),view);
        MenuInflater inflate = popup.getMenuInflater();
        inflate.inflate(R.menu.popupmenutugas, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener(position,no,Nama,noJK));
        popup.show();
    }
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener{

        private int position;
        private int no,noJK;
        private String nama;
        public MyMenuItemClickListener(int position,int no,String nama, int noJK){
            this.position = position;
            this.no = no;
            this.nama = nama;
            this.noJK = noJK;
        }
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()){
                case R.id.menuTugasUbah:
                    Bundle bundle = new Bundle();
                    bundle.putInt("idJK",noJK);
                    bundle.putInt("idT",no);
                    frmTugas f =new frmTugas();
                    f.setArguments(bundle);
                    t.getFragmentManager().beginTransaction().add(R.id.fragmentmenuTugas,f).addToBackStack(null).commit();
                    break;
                case R.id.menuTugasHapus:
                    new AlertDialog.Builder(mContext)
                            .setTitle("Hapus Tugas ?")
                            .setMessage("Yakin Ingin Hapus Tugas :"+nama)
                            .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    yo.hapusData(no);
                                    notifyDataSetChanged();
                                }
                            })
                            .setNegativeButton("Batal",null)
                            .show();
                    break;
            }
            return false;
        }
    }
}
