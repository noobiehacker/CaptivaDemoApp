package emc.captiva.mobile.sdksampleapp.Service;
import emc.captiva.mobile.sdksampleapp.JsonPojo.ImageUploadObj;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by david on 8/23/16.
 */
public interface CaptivaImageUploadService {

    @POST("cp-rest/session/files")
    Call<ResponseBody> uploadImage(@Body ImageUploadObj file);

}
