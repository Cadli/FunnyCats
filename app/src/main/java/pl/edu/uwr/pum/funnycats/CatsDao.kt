package pl.edu.uwr.pum.funnycats

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface CatsDao {
    @Query("SELECT * FROM cats_table")
    fun getFavCats(): Flow<List<Cat>>


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(cat: Cat)

    @Query("DELETE FROM cats_table WHERE id = :catId")
    suspend fun delete(catId: String)

}