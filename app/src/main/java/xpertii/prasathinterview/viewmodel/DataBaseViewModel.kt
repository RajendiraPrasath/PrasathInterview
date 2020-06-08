package xpertii.prasathinterview.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import xpertii.prasathinterview.model.Product

import xpertii.prasathinterview.sqlite.DatabaseHandler

class DataBaseViewModel(application: Application) : AndroidViewModel(application) {

    lateinit var databaseHandler: DatabaseHandler
    init {

        databaseHandler = DatabaseHandler(application)
    }

    fun insertProduct(product: Product){

        databaseHandler.addCartProduct(product)
    }
    fun updateProduct(product: Product)
    {
        databaseHandler.updateProduct(product)
    }
    fun getCartProduct() : List<Product>
    {
        var productList:List<Product> = ArrayList<Product>()
        productList = databaseHandler.viewProduct()
       return productList
    }
    fun deleteRecord()
    {
        databaseHandler.deleteAllRecord()
    }
}