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
import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;
import me.citrafa.mycollegeassistant.Activity.Fragment.frmCatatan;
import me.citrafa.mycollegeassistant.Activity.Fragment.menuCatatan;
import me.citrafa.mycollegeassistant.CustomWidget.tvMuseo;
import me.citrafa.mycollegeassistant.ModelClass.CatatanModel;
import me.citrafa.mycollegeassistant.OperationRealm.CatatanOperation;
import me.citrafa.mycollegeassistant.R;

/**
 * Created by SENSODYNE on 05/05/2017.
 */

public class AdapterCatatanRV extends RealmRecyclerViewAdapter<CatatanModel, AdapterCatatanRV.myViewHolder> {
    RealmResults<CatatanModel> catatan;
    OrderedRealmCollection<CatatanModel> data;
    Context mContext;
    CatatanOperation CO;
    Realm realm;
    menuCatatan mc;

    public AdapterCatatanRV(menuCatatan mc,@Nullable OrderedRealmCollection<CatatanModel> data, RealmResults<CatatanModel> catatan) {
        super(data, true);
        this.data = data;
        this.catatan = catatan;
        CO = new CatatanOperation();
        this.mc = mc;
        realm = Realm.getDefaultInstance();


    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rowcatatan, parent, false);
        return new myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final myViewHolder holder, final int position) {
        final CatatanModel catatanModel = catatan.get(position);
        holder.txt1.setText(catatanModel.getNama_c());
        SimpleDateFormat waktu = new SimpleDateFormat("dd/MM/yyyy - hh:mm");
        if (catatanModel.getWaktu_c() !=null) {

            holder.txt2.setText(waktu.format(catatanModel.getWaktu_c()));
            holder.txt2.setVisibility(View.VISIBLE);
        }
        holder.txt3.setText(catatanModel.getDeskripsi_c());
        final int id = catatanModel.getNo_c();
        holder.btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(holder.btnMore,position,id,catatanModel.getNama_c());
            }
        });
        if (catatanModel.getAttlink_c()!=null){
            holder.btnFile.setVisibility(View.VISIBLE);
            holder.txt4.setVisibility(View.VISIBLE);
            String path = catatanModel.getAttlink_c();
            String filename=path.substring(path.lastIndexOf("/")+1);
            holder.txt4.setText(filename);
        }
        holder.btnFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File myFile = new File(catatanModel.getAttlink_c());
                try {
                    FileOpen.openFile(mContext, myFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

    }




    public class myViewHolder extends RecyclerView.ViewHolder {
        tvMuseo txt1,txt2,txt3,txt4;
        ImageButton btnMore,btnFile;


        public myViewHolder(View itemView) {
            super(itemView);
            txt1 = (tvMuseo)itemView.findViewById(R.id.rowCatatanNama);
            txt2 = (tvMuseo)itemView.findViewById(R.id.rowCatatanWaktu);
            txt3 = (tvMuseo)itemView.findViewById(R.id.rowCatatanDeskripsi);
            txt4 = (tvMuseo)itemView.findViewById(R.id.rowCatatanNamaFile);
            btnMore = (ImageButton)itemView.findViewById(R.id.rowCatatanBtnMore);
            btnFile = (ImageButton)itemView.findViewById(R.id.rowCatatanBtn);
        }
    }
    private void showPopupMenu(View view, int position, int id, String Nama){
        PopupMenu popup = new PopupMenu(view.getContext(), view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.popupmenu_c, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener(position,id,Nama));
        popup.show();
    }
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener{

        private int position;
        private int id;
        private String nama;
        public MyMenuItemClickListener(int position, int id, String nama){
            this.position = position;
            this.id = id;
            this.nama = nama;
        }
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()){

                case R.id.MenuCUbah:
                    Bundle bundle = new Bundle();
                    bundle.putInt("idC",id);
                    frmCatatan frm = new frmCatatan();
                    frm.setArguments(bundle);
                    mc.getFragmentManager()
                            .beginTransaction()
                            .add(R.id.navMenuCatatan, frm).addToBackStack(null)
                            .commit();
                    break;
                case R.id.MenuCHapus:

                    new AlertDialog.Builder(mContext)
                            .setTitle("Hapus Catatan?")
                            .setMessage("Apa kamu yakin ingin menghapus : "+nama)
                            .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    CO.hapusCatatan(id);
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
