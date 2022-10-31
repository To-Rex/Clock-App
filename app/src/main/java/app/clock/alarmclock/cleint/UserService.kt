package app.clock.alarmclock.cleint

import app.clock.alarmclock.models.GetTimes
import retrofit2.http.POST
import app.clock.alarmclock.models.LoginModels
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface UserService {
    @POST("login")
    fun login(@Body loginModels: LoginModels?): Call<Any?>?

    @POST("register")
    fun register(@Body loginModels: LoginModels?): Call<Any?>?

    @POST("resendverefy")
    fun resendverefy(@Body loginModels: LoginModels?): Call<Any?>?

    @POST("verefyuser")
    fun verefyuser(@Body loginModels: LoginModels?): Call<Any?>?

    //@POST("cheskverefy")
    //fun cheskverefy(@Body loginModels: LoginModels?): Call<Any?>?

    @GET("gettimes")
    fun gettimes(@Header("Authorization") token: String?): Call<Any?>?

    @POST("addtime")
    fun addtime(@Header("Authorization") token: String?, @Body getTimes: GetTimes?): Call<Any?>?

    @POST("deletetime")
    fun deleteTime(@Query("index") index: Int, @Header("Authorization") token: String?): Call<Any?>?

    @POST("updatetime")
    fun updateTime(@Query("index") index: Int, @Header("Authorization") token: String?, @Body getTimes: GetTimes?): Call<Any?>?

}