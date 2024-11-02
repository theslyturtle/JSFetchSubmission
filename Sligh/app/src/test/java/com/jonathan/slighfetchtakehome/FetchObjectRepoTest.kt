package com.jonathan.slighfetchtakehome

import com.jonathan.slighfetchtakehome.data.models.FetchObject
import com.jonathan.slighfetchtakehome.data.repository.FetchRepository
import junit.framework.TestCase.assertEquals
import org.junit.Test

/*
    Testing the filtering and sorting of items in FetchRepository.kt
 */
internal class FetchObjectRepoTest {

    private val testList: MutableList<FetchObject> = mutableListOf(FetchObject(0, 6, "Item 0"),
        FetchObject(2, 1, "Item 2"), FetchObject(1, 1, "Item 1"),
        FetchObject(4, 1, ""), FetchObject(5, 4, null),
        FetchObject(301, 2, "Item 301"),
        FetchObject(299, 2, "Item 299"), FetchObject(6, 3, "null")
    )

    private val sortedList: List<FetchObject> = listOf(FetchObject(1, 1, "Item 1"),
        FetchObject(2, 1, "Item 2"), FetchObject(299, 2, "Item 299"),
        FetchObject(301, 2, "Item 301"), FetchObject(0, 6, "Item 0"))


    @Test
    fun testSorting() {
        val fixedList = FetchRepository.filterAndSortItems(testList)
        assertEquals(fixedList[0], sortedList[0])
        assertEquals(fixedList[1], sortedList[1])
        assertEquals(fixedList[2], sortedList[2])
        assertEquals(fixedList[3], sortedList[3])
        assertEquals(fixedList[4], sortedList[4])
    }
}