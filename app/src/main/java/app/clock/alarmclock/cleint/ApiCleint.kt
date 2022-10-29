package app.clock.alarmclock.cleint

class ApiCleint {
    private fun getRetrofit(): Retrofit {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
        return Retrofit.Builder()
            .baseUrl("https://api.astrocoin.uz/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }
    val userService: UserService get() = getRetrofit().create(UserService::class.java)
}