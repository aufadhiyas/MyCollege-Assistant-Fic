package me.citrafa.mycollegeassistant.AdapterRecycleView;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;
import me.citrafa.mycollegeassistant.Activity.Dashboard;
import me.citrafa.mycollegeassistant.Activity.Fragment.frmJadwalUjian;
import me.citrafa.mycollegeassistant.Activity.Fragment.menuJadwalUjian;
import me.citrafa.mycollegeassistant.CustomWidget.tvMuseo;
import me.citrafa.mycollegeassistant.ModelClass.JadwalUjianModel;
import me.citrafa.mycollegeassistant.OperationRealm.JadwalUjianOperation;
import me.citrafa.mycollegeassistant.R;

/**
 * Created by SENSODYNE on 21/04/2017.
 */

public class AdapterJadwalUjianRV extends RealmRecyclerViewAdapter<JadwalUjianModel, AdapterJadwalUjianRV.MyViewHolder> {
    private Context mContext;
    Realm realm;
    private RealmResults<JadwalUjianModel> jadwalUjianModels;
    menuJadwalUjian mju;
    JadwalUjianOperation juo;
    Context context;
    Dashboard db;

    public AdapterJadwalUjianRV(menuJadwalUjian mju, @Nullable OrderedRealmCollection<JadwalUjianModel> data, RealmResults<JadwalUjianModel>JUM) {
        super(data, true);
        setHasStableIds(true);
        realm = Realm.getDefaultInstance();
        this.jadwalUjianModels = JUM;
        this.mju = mju;
        juo = new JadwalUjianOperation();
        db = new Dashboard();

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_ju, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final JadwalUjianModel ju = jadwalUjianModels.get(position);
        holder.data = ju;
        if (ju !=null){
            Date waktu = ju.getWaktu();
            SimpleDateFormat Dates = new SimpleDateFormat("EEE, d MMM yyyy HH:mm");
            String Jadi=Dates.format(waktu);
            holder.txt1.setText(Jadi);
            holder.txt2.setText(ju.getJenis());
            holder.txt3.setText(ju.getRuangan());
            holder.txt4.setText(ju.getNama_makul());
            holder.txt5.setText(ju.getDeskripsi());
        }





        holder.imgBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int no = ju.getNo_ju();
                showPopupMenu(v,position,no,ju.getNama_makul());
            }
        });
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        tvMuseo txt1,txt2,txt3,txt4,txt5;
        ImageButton imgBtn1;
        CardView cardView;
        JadwalUjianModel data;

        public MyViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView)itemView.findViewById(R.id.cardviewJU);
            imgBtn1 = (ImageButton)itemView.findViewById(R.id.btnMenuJadwalUjian);
            txt1 = (tvMuseo)itemView.findViewById(R.id.rowJUWaktu);
            txt2 = (tvMuseo)itemView.findViewById(R.id.rowJUJenisUjian);
            txt3 = (tvMuseo)itemView.findViewById(R.id.rowJURuangan);
            txt4 = (tvMuseo)itemView.findViewById(R.id.rowJUMakul);
            txt5 = (tvMuseo)itemView.findViewById(R.id.rowJUDeskripsi);
        }
    }
    private void showPopupMenu(View view,int position,int no,String nama){

        PopupMenu popup = new PopupMenu(view.getContext(),view);
        MenuInflater inflate = popup.getMenuInflater();
        inflate.inflate(R.menu.popupmenu_ju, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener(position,no,nama));
        popup.show();
    }
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener{

        private int position;
        private int no;
        private String nama;
        public MyMenuItemClickListener(int position,int no,String nama){
            this.position = position;
            this.no = no;
            this.nama = nama;
        }
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()){
                case R.id.menuJUubah:
                    Bundle bundle = new Bundle();
                    bundle.putInt("idJU",no);
                    frmJadwalUjian frm = new frmJadwalUjian();
                    frm.setArguments(bundle);
//                    if (mju!=null){
//                        Toast.makeText(mContext, "Tidak Null", Toast.LENGTH_SHORT).show();
                    mju.getFragmentManager().beginTransaction().replace(R.id.fragmentmenuJadwalUjian, frm).addToBackStack(null).commit();
//                    }else {
//                 db.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentmenuJadwalUjian, frm).addToBackStack(null).commit();
//                    }

                    return true;
                case R.id.menuJUHapus:
                    new AlertDialog.Builder(mContext)
                            .setTitle("Hapus jadwal lain?")
                            .setMessage("Apa kamu yakin ingin menghapus jadwal : "+nama)
                            .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    juo.hapusJadwalUjian(no);
                                    notifyDataSetChanged();
                                }
                            })
                            .setNegativeButton("Batal",null)
                            .show();
                    return true;
            }
            return false;
        }
    }
}
