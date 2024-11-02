package com.jonathan.slighfetchtakehome.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jonathan.slighfetchtakehome.data.database.DatabaseConstants

@Entity (tableName = DatabaseConstants.FETCHOBJECT_TABLE_NAME)
data class FetchObject(
    @PrimaryKey val id: Int?,
    @ColumnInfo (name = "list_id") val listId: Int?,
    val name: String?
)