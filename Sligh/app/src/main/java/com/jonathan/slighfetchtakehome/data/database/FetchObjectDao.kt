package com.jonathan.slighfetchtakehome.data.database

import androidx.room.*
import com.jonathan.slighfetchtakehome.data.models.FetchObject
import kotlinx.coroutines.flow.Flow

@Dao
interface FetchObjectDao {

    @Query("SELECT * FROM ${DatabaseConstants.FETCHOBJECT_TABLE_NAME} order by " +
            "list_id, name")
    fun getAll(): Flow<List<FetchObject>>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(items: List<FetchObject>)

    @Delete
    fun delete(item: FetchObject)
}