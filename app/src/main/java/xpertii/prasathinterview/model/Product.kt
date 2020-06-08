package xpertii.prasathinterview.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Product (
    @Expose
    @SerializedName("name")
    val name: String,
    @Expose
    @SerializedName("product_id")
    val product_id: String,
    @Expose
    @SerializedName("image")
    val image: String,
    @Expose
    @SerializedName("quantity")
    val quantity: String,
    @Expose
    @SerializedName("price")
    val price: String,
    @Expose
    @SerializedName("special")
    val special: String)
