package com.jonathansligh.fetchcodingexercise

import com.jonathansligh.fetchcodingexercise.models.Item
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ItemListViewModelTests {
    @Test
    fun itemSortingFilterTest() {
        val list: List<Item> = listOf(Item(0,0,""), Item(100, 10, "Item 100"),
            Item(10, 1, "Item 10"), Item(0, 1, "Item 0"),
            Item(10, 10, null))

        val viewModel = ItemListViewModel()
        val newList =  viewModel.filterListData(list)

        assertEquals(newList[0].id, 0)
        assertEquals(newList[1].name, "Item 10")
        assertEquals(newList[2].name, "Item 100")
        assertEquals(newList.size, 3)
    }
}