package com.jonathan.slighfetchtakehome.ui

import androidx.lifecycle.*
import com.jonathan.slighfetchtakehome.data.database.FetchObjectDbRepo
import com.jonathan.slighfetchtakehome.data.models.FetchObject
import com.jonathan.slighfetchtakehome.data.repository.FetchRepository
import com.jonathan.slighfetchtakehome.data.util.ApiResult
import com.jonathan.slighfetchtakehome.data.util.apiCall
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FetchListViewModel @Inject constructor(
    private val fetchRepo: FetchRepository,
    private val dbRepo: FetchObjectDbRepo
) : ViewModel(),
    DefaultLifecycleObserver {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> = _error

    private val _fetchList = MutableLiveData<List<FetchObject>>()
    val fetchList: LiveData<List<FetchObject>> = _fetchList

    fun getFetchList(refreshing: Boolean = false) {
        _loading.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            loadFetchList(refreshing)
        }
    }

    private suspend fun loadFetchList(refreshing: Boolean = false) {
        val result = apiCall { fetchRepo.getFetchList() }
        //if api result is good, post to UI / insert into DB
        if (result is ApiResult.Success) {
            _fetchList.postValue(result.value)
            dbRepo.insert(result.value)
            _error.postValue(false)
        } else {
            //if api call fails, try to grab from db
            dbRepo.allObjects.collect {
                if (it.isEmpty()) {
                    //if db is empty, post error message
                    _error.postValue(true)
                } else {
                    //if trying to refresh when
                    _fetchList.postValue(it)
                    //if trying to refresh when api fails, put toast only
                    if (refreshing) {
                        _error.postValue(true)
                    }
                }
                _loading.postValue(false)
            }
        }
        _loading.postValue(false)
    }

}