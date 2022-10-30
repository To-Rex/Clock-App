package app.clock.alarmclock.cleint

import retrofit2.http.POST
import app.clock.alarmclock.models.LoginModels
import retrofit2.Call
import retrofit2.http.Body

interface UserService {
    @POST("login")
    fun login(@Body loginModels: LoginModels?): Call<Any?>?
    @POST("register")
    fun register(@Body loginModels: LoginModels?): Call<Any?>?

}