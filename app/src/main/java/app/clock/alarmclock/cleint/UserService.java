package app.clock.alarmclock.cleint;

import app.clock.alarmclock.models.LoginModels;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {
    @POST("login")
    Call<Object> login(@Body LoginModels loginModels);
}
