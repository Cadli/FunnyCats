package pl.edu.uwr.pum.funnycats


import android.app.Application
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import retrofit2.Response
import kotlin.random.Random


class CatsViewModel(application: Application) : ViewModel() {

    private val repository = CatsRepository(application)

    private var _cats: MutableStateFlow<Resource<List<Cat>>> = MutableStateFlow(Resource.Loading())
    val cats: StateFlow<Resource<List<Cat>>> = _cats

    val favCats: StateFlow<List<Cat>> = repository.readData.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        emptyList()
    )

    private var lastScrollPosition: Int = 0


    private val quantity = 50
    private var newQuantity = quantity
    private var skipValue = Random.nextInt(0, 1100 + 1)

    init {
        fetchData(quantity, skipValue)
    }

    private fun fetchData(quantity: Int, skipValue: Int) = viewModelScope.launch {
        val response = repository.fetchData(quantity, skipValue)
        delay(2000L)
        _cats.value = handleCatsResponse(response)
    }


    fun loadMoreCats() = viewModelScope.launch {
        newQuantity += 5
        fetchData(newQuantity, skipValue)
    }

    private fun handleCatsResponse(response: Response<List<Cat>>): Resource<List<Cat>> {
        if (response.isSuccessful)
            response.body()?.let { return Resource.Success(it) }
        return Resource.Error(response.message())
    }

    fun insert(cat: Cat) = viewModelScope.launch {
        repository.insert(cat)
    }

    fun delete(cat: Cat) = viewModelScope.launch {
        repository.delete(cat)
    }

    fun checkIfExistInLocalDb(cat: Cat): Boolean = cat in favCats.value


    fun saveScrollPosition(position: Int) {
        lastScrollPosition = position
    }

    fun loadScrollPosition(): Int {
        return lastScrollPosition
    }
}
