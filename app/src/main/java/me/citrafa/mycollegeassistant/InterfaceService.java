package me.citrafa.mycollegeassistant;

import java.util.List;

import me.citrafa.mycollegeassistant.ModelClass.JadwalKuliahModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by SENSODYNE on 21/05/2017.
 */

public interface InterfaceService {
    @POST("")
    Call<ResponseBody> uploadJadwalKuliah(@Body List<JadwalKuliahModel> list);
}
