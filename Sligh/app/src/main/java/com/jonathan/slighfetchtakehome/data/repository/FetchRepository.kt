package com.jonathan.slighfetchtakehome.data.repository

import com.jonathan.slighfetchtakehome.data.api.FetchApi
import com.jonathan.slighfetchtakehome.data.models.FetchObject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FetchRepository @Inject constructor(private val api: FetchApi) {

    suspend fun getFetchList(): MutableList<FetchObject>? {
        return api.getList().body()?.toMutableList()?.let { filterAndSortItems(it) }
    }

    companion object {
        fun filterAndSortItems(dirtyItems: MutableList<FetchObject>): MutableList<FetchObject> {
            var items = dirtyItems
            items = items.filter { s -> s.name != null && s.name != "null" && s.name != ""} as MutableList<FetchObject>
            items.sortWith(CompareFetchObjects)
            return items
        }
    }
}

class CompareFetchObjects {

    companion object : Comparator<FetchObject> {

        override fun compare(a: FetchObject, b: FetchObject): Int{
            val aListId = a.listId
            val bListId = b.listId
            if (aListId != null && bListId != null) {
                val comparison = aListId.compareTo(bListId)
                return if (comparison == 0) {
                    val aName = a.name
                    val bName = b.name
                    if (aName != null && bName != null) {
                        (aName.compareTo(bName))
                    } else if (aName == null) {
                        -1
                    } else {
                        1
                    }
                } else {
                    comparison
                }
            } else if (aListId == null) {
                return -1
            } else {
                return 1
            }
        }
    }
}