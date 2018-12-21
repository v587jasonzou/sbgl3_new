package sbgl3.yunda.http;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CommonService {
    //在修机车列表
    @GET("faultTiketMobile!queryZXJCList.action")
    Observable<String> getCarList(@Query("entityJson") String entityJson);
}
