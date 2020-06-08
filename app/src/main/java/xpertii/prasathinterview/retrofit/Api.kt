package xpertii.prasathinterview.retrofit


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import xpertii.prasathinterview.model.ProductList

interface Api {
    @POST("v2/5def7b172f000063008e0aa2")
    fun getProduct(): Call<ProductList>
}