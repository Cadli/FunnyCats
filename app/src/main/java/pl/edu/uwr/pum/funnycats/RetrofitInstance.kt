package pl.edu.uwr.pum.funnycats

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val api: CatsApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://cataas.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CatsApi::class.java)
    }
}
