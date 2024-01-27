package pl.edu.uwr.pum.funnycats

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface CatsApi {

    @GET("cats")
    suspend fun cats(@Query("limit") limit: Int, @Query("skip") skip: Int): Response<List<Cat>>

}
