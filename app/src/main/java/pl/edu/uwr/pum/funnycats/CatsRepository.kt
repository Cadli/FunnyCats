package pl.edu.uwr.pum.funnycats

import android.app.Application
import kotlinx.coroutines.flow.Flow



class CatsRepository(application: Application) {

    private val catsDao = CatsDatabase.getDatabase(application).catsDao()

    suspend fun fetchData(quantity: Int, skipValue: Int) = RetrofitInstance.api.cats(limit = quantity,skip = skipValue)


    val readData: Flow<List<Cat>> = catsDao.getFavCats()

    suspend fun insert(cat: Cat) = catsDao.insert(cat)
    suspend fun delete(cat: Cat) = catsDao.delete(cat.id)
}



