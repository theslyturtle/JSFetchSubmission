package com.jonathan.slighfetchtakehome.data.database

import androidx.annotation.WorkerThread
import com.jonathan.slighfetchtakehome.data.models.FetchObject
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class FetchObjectDbRepo @Inject constructor(private val fetchDao: FetchObjectDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allObjects: Flow<List<FetchObject>> = fetchDao.getAll()

    @WorkerThread
    fun insert(items: List<FetchObject>) {
        fetchDao.insertAll(items)
    }
}