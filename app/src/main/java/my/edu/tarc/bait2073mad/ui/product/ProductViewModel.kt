package my.edu.tarc.bait2073mad.ui.product

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ProductViewModel(application: Application) : AndroidViewModel(application) {
    var productList: LiveData<List<Product>>
    private val repository: ProductRepository
    var selectedIndex: Int = -1

    init {
        val productDao = ProductDatabase.getDatabase(application).productDao()
        repository = ProductRepository(productDao)
        productList = repository.allProducts
    }

    fun addProduct(product: Product) = viewModelScope.launch {
        repository.add(product)
    }

    fun updateProduct(product: Product) = viewModelScope.launch {
        repository.update(product)
    }
}
