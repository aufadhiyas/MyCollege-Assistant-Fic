package me.citrafa.mycollegeassistant.AdapterRecycleView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;

import me.citrafa.mycollegeassistant.Activity.Fragment.frmJadwalKuliah;
import me.citrafa.mycollegeassistant.Activity.Fragment.frmTugas;
import me.citrafa.mycollegeassistant.Activity.Fragment.menuJadwalKuliah;
import me.citrafa.mycollegeassistant.CustomWidget.tvMuseo;
import me.citrafa.mycollegeassistant.ModelClass.JadwalKuliahModel;
import me.citrafa.mycollegeassistant.ModelClass.JadwalLainModel;
import me.citrafa.mycollegeassistant.OperationRealm.JadwalKuliahOperation;
import me.citrafa.mycollegeassistant.R;

/**
 * Created by SENSODYNE on 25/04/2017.
 */

public class AdapterJadwalKuliahNew extends RealmRecyclerViewAdapter<JadwalKuliahModel, AdapterJadwalKuliahNew.MyViewHolder> {
    private Context mContext;
    Realm realm;
    RealmResults<JadwalKuliahModel> jkm;
    private int ids;
    JadwalKuliahOperation JAO;
    menuJadwalKuliah mj;


    public AdapterJadwalKuliahNew(@Nullable OrderedRealmCollection<JadwalKuliahModel> data, RealmResults<JadwalKuliahModel>jkm, menuJadwalKuliah mj) {
        super(data, true);
        this.jkm = jkm;
        setHasStableIds(true);
        realm = Realm.getDefaultInstance();
        JAO = new JadwalKuliahOperation();
        this.mj = mj;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rowjknew, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final JadwalKuliahModel jk = jkm.get(position);

            holder.nullLayout.setVisibility(View.GONE);
            Date date1 = jk.getWaktu_jk();
            Date date2 = jk.getWaktu_jkf();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            String Jam1 = sdf.format(date1);
            String Jam2 = sdf.format(date2);
            if (position != 0) {
                if (jk.getHari_jk().equalsIgnoreCase(jkm.get(position - 1).getHari_jk())) {
                    holder.txtHari.setVisibility(View.GONE);
                } else {
                    holder.txtHari.setVisibility(View.VISIBLE);
                }
            } else {
                holder.txtHari.setVisibility(View.VISIBLE);
            }

            holder.txtHari.setText(jk.getHari_jk());
            holder.txtJam.setText(Jam1);
            holder.txtJam1.setText(Jam2);
            holder.txtMakul.setText(jk.getMakul_jk());
            holder.txtDosen.setText(jk.getDosen_jk());
            holder.txtRuangan.setText(jk.getRuangan_jk());
            final int module = jk.getNo_jk();

            if (jk.Tugas.isEmpty()) {
                holder.statusTugas.setText("Tidak Ada Tugas");
            } else {
                holder.statusTugas.setText("Ada Tugas");
            }

            holder.imgButtonJK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPopupMenu(holder.imgButtonJK, position, module, jk.getMakul_jk());
                }
            });


    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        tvMuseo txtHari,txtJam,txtJam1,txtMakul,txtDosen,txtRuangan,statusTugas;
        ImageView imgTugas;
        ImageButton imgButtonJK;
        RelativeLayout nullLayout;

        public MyViewHolder(View v) {
            super(v);

            txtHari = (tvMuseo) v.findViewById(R.id.rowhariJKn);
            txtJam = (tvMuseo) v.findViewById(R.id.rowJamjkn);
            txtJam1 = (tvMuseo)v.findViewById(R.id.rowJamjk2n);
            txtMakul = (tvMuseo) v.findViewById(R.id.rowMakuln);
            txtDosen = (tvMuseo) v.findViewById(R.id.rowDosenn);
            txtRuangan = (tvMuseo) v.findViewById(R.id.rowRuanganjkn);

            imgTugas = (ImageView)v.findViewById(R.id.iconTugasRowJKn);
            statusTugas = (tvMuseo)v.findViewById(R.id.rowstatusTugasJKn);
            imgButtonJK = (ImageButton)v.findViewById(R.id.menuOptionJKn);
            nullLayout = (RelativeLayout)v.findViewById(R.id.rowJKNull);

        }
    }
    private void showPopupMenu(View view, int position, int id, String Nama){
        PopupMenu popup = new PopupMenu(view.getContext(), view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.popupmenu_jk, popup.getMenu());
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
                case R.id.MenuJKTugas:
                    Bundle bundle = new Bundle();
                    bundle.putInt("idJK",id);
                    bundle.putInt("idT",0);
                    frmTugas f = new frmTugas();
                    f.setArguments(bundle);
                    mj.getFragmentManager().beginTransaction().add(R.id.tabJadwalKuliah,f).addToBackStack(null).commit();
                    break;
                case R.id.MenuJKUbah:
                    Intent edit = new Intent(mContext,frmJadwalKuliah.class);
                    edit.putExtra("id",id);
                    mContext.startActivity(edit);
                    break;
                case R.id.MenuJKHapus:

                    new AlertDialog.Builder(mContext)
                            .setTitle("Hapus jadwal kuliah?")
                            .setMessage("Apa kamu yakin ingin menghapus jadwal kuliah : "+nama)
                            .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    JAO.deleteItemAsync(realm,id);
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
