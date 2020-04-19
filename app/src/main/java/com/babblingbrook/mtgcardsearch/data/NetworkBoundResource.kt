package com.babblingbrook.mtgcardsearch.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.babblingbrook.mtgcardsearch.ui.Status
import kotlinx.coroutines.*
import kotlin.coroutines.coroutineContext

abstract class NetworkBoundResource<ResultType, RequestType> {

    private val result = MutableLiveData<Status<ResultType>>()
    private val supervisorJob = SupervisorJob()

    suspend fun build(): NetworkBoundResource<ResultType, RequestType> {
        withContext(Dispatchers.Main) {
            result.value = Status.loading(null)
        }
        CoroutineScope(coroutineContext).launch(supervisorJob) {
            val dbResult = loadFromDb()
            if (shouldFetch(dbResult)) {
                fetchFromNetwork(dbResult)
            } else {
                result.value = Status.success(dbResult)
            }
        }
        return this
    }

    private suspend fun fetchFromNetwork(dbResult: ResultType) {
        setValue(Status.loading(dbResult))
        when (val apiResponse = createCallAsync().await()) {
            is ApiSuccessResponse -> {
                saveCallResults(processResponse(apiResponse))
                setValue(Status.success(loadFromDb()))
            }
            is ApiNetworkError -> {
                setValue(Status.noNetwork(loadFromDb()))
            }
            is ApiGenericError -> {
                setValue(Status.error(apiResponse.errorMessage, loadFromDb()))
            }
        }
    }

    fun asLiveData() = result as LiveData<Status<ResultType>>

    private fun setValue(newValue: Status<ResultType>) {
        if (result.value != newValue) result.postValue(newValue)
    }

    protected open fun processResponse(response: ApiSuccessResponse<RequestType>) = response.body

    protected abstract suspend fun saveCallResults(items: RequestType)

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract suspend fun loadFromDb(): ResultType

    protected abstract suspend fun createCallAsync(): Deferred<ApiResponse<RequestType>>
}