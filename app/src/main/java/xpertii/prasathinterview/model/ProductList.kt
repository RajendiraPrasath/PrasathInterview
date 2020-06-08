package xpertii.prasathinterview.model
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
data class ProductList (
    @Expose
    @SerializedName("products")
    val products: List<Product>)