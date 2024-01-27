package pl.edu.uwr.pum.funnycats

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "cats_table")
data class Cat (

    @SerializedName("_id")
    @PrimaryKey(autoGenerate = false) val id: String,

)

