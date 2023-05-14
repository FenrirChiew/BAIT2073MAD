package my.edu.tarc.bait2073mad.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import my.edu.tarc.bait2073mad.ui.product.Product
import my.edu.tarc.bait2073mad.ui.product.ProductDatabase
import my.edu.tarc.bait2073mad.ui.product.ProductRepository

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    var searchQuery = MutableLiveData<String>("")
    var productList: LiveData<List<Product>>
    var selectedIndex: Int = -1
    private val repository: ProductRepository

    init {
        val productDao = ProductDatabase.getDatabase(application).productDao()
        repository = ProductRepository(productDao)
        productList = repository.allProducts
    }

    fun addProduct(product: Product) = viewModelScope.launch {
        repository.add(product)
    }

    fun deleteProduct(product: Product) = viewModelScope.launch {
        repository.delete(product)
    }

    fun deleteAllProduct() = viewModelScope.launch {
        repository.deleteAll()
    }

    fun updateProduct(product: Product) = viewModelScope.launch {
        repository.update(product)
    }

    fun resetProducts() = viewModelScope.launch {
        productList = repository.allProducts
    }
}