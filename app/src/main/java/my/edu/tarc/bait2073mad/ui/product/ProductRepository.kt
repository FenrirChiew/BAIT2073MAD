package my.edu.tarc.bait2073mad.ui.product

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class ProductRepository(private val productDao: ProductDao) {
    //Room execute all queries on a separate thread
    val allProducts: LiveData<List<Product>> = productDao.getAllProducts()

    @WorkerThread
    suspend fun add(product: Product) {
        productDao.insert(product)
    }

    @WorkerThread
    suspend fun delete(product: Product) {
        productDao.delete(product)
    }

    @WorkerThread
    suspend fun update(product: Product) {
        productDao.update(product)
    }
}
