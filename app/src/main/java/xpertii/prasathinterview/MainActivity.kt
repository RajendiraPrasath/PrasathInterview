package xpertii.prasathinterview

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout


import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import xpertii.prasathinterview.adapters.ProductAdapter
import xpertii.prasathinterview.grid.GridItemDecoration
import xpertii.prasathinterview.model.Product
import xpertii.prasathinterview.model.ProductList
import xpertii.prasathinterview.retrofit.Api
import xpertii.prasathinterview.retrofit.ApiService
import xpertii.prasathinterview.viewmodel.DataBaseViewModel

class MainActivity : AppCompatActivity() {

    lateinit var mListRecyclerView : RecyclerView
    lateinit var swipeRefresh : SwipeRefreshLayout
    lateinit var databaseModel : DataBaseViewModel
    lateinit var cartproductlist:List<Product>
    lateinit var adapter : ProductAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        swipeRefresh = findViewById(R.id.swipeToRefresh) as SwipeRefreshLayout
        mListRecyclerView = findViewById(R.id.recyclerView) as RecyclerView
        mListRecyclerView.layoutManager = GridLayoutManager(this@MainActivity, 2)
        mListRecyclerView.addItemDecoration(GridItemDecoration(5, 2))
        swipeRefresh.isRefreshing = true
        swipeRefresh.setOnRefreshListener {
            retrofitCall()

        }
        databaseModel = ViewModelProviders.of(this).get(DataBaseViewModel::class.java)


        retrofitCall()

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        val inflater = menuInflater
        inflater.inflate(R.menu.produtlistmenu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.cart -> {
                val mainIntent =
                    Intent(this@MainActivity, CartActivity::class.java)
                startActivity(mainIntent)
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }
    fun retrofitCall(){
        cartproductlist = databaseModel.getCartProduct()
        val request = ApiService.buildService(Api::class.java)
        val call = request.getProduct()

        call.enqueue(object : Callback<ProductList> {
            override fun onResponse(call: Call<ProductList>, response: Response<ProductList>) {
                if (response.isSuccessful){
                    swipeRefresh.isRefreshing = false


                    adapter = ProductAdapter(this@MainActivity,response.body()!!.products,cartproductlist)
                    mListRecyclerView.adapter = adapter


                }
            }

            override fun onFailure(call: Call<ProductList>, t: Throwable) {

                Log.i("errror msg ","${t.message}")
            }
        })
    }
    public fun productAdd(product : Product)
    {
        databaseModel.insertProduct(product)
    }
    public fun productUpdate(product : Product)
    {
        Log.i("productQty",product.quantity)
        databaseModel.updateProduct(product)
    }
    fun getCartProduct():List<Product>{
        return databaseModel.getCartProduct()
    }
    override fun onResume() {
        super.onResume()
        if(getCartProduct().size<=0)
        {
            retrofitCall()
        }
    else {
            if (this::adapter.isInitialized) {
                adapter.updateCartProductList(getCartProduct())
            }
        }

    }
    }

