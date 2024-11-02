package com.jonathansligh.fetchcodingexercise

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jonathansligh.fetchcodingexercise.apis.FetchHiringApiInterface
import com.jonathansligh.fetchcodingexercise.apis.FetchHiringRetrofitInstance
import com.jonathansligh.fetchcodingexercise.models.Item
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ItemListViewModel: ViewModel() {
    private val _itemListData: MutableLiveData<List<Item>> by lazy {
        MutableLiveData<List<Item>>()
    }
    val itemListData: LiveData<List<Item>> = this._itemListData
    private val _networkError: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    val networkError: MutableLiveData<Boolean> = this._networkError

    private val _refreshError: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    val refreshError: MutableLiveData<Boolean> = this._refreshError

    init {
        getListData(false)
    }

    fun getListData(refreshing: Boolean) {
        val apiInterface = FetchHiringRetrofitInstance.getInstance().create(FetchHiringApiInterface::class.java)
        apiInterface.getListData().enqueue(object : Callback<List<Item>> {
            override fun onResponse(call: Call<List<Item>>, response: Response<List<Item>>) {
                if (response.isSuccessful && response.body()!=null){
                    response.body()?.let {
                        _networkError.value = false
                        _itemListData.value = filterListData(it)
                    }
                }
            }

            override fun onFailure(call: Call<List<Item>>, t: Throwable) {
                _networkError.value = true
                _refreshError.value = refreshing
            }
        })
    }

    @VisibleForTesting
    internal fun filterListData(inputList: List<Item>): List<Item> {
        val sortedList = inputList.filter{!it.name.isNullOrBlank()}.sortedWith(
            compareBy<Item> {it.listId}.thenBy {it.name}
        )
        return sortedList
    }
}