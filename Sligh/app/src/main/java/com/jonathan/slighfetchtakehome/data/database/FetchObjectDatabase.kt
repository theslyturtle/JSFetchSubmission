package com.jonathan.slighfetchtakehome.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jonathan.slighfetchtakehome.data.models.FetchObject


@Database(entities = [FetchObject::class], version = 1)
abstract class FetchObjectDatabase : RoomDatabase() {

    abstract fun fetchObjectDao(): FetchObjectDao

}