package xpertii.prasathinterview

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import xpertii.prasathinterview.adapters.CartProductAdapter
import xpertii.prasathinterview.model.Product
import xpertii.prasathinterview.viewmodel.DataBaseViewModel

class CartActivity : AppCompatActivity() {
    private lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var databaseModel : DataBaseViewModel
    lateinit var cartproductlist:List<Product>
    lateinit var cartText : TextView
    lateinit var cartTotal : TextView
    lateinit var clearCart : TextView
    lateinit var emptyCart : TextView
    lateinit var cartTotalText : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (supportActionBar != null)
            supportActionBar?.hide()
        setContentView(R.layout.cartlayout)
        databaseModel = ViewModelProviders.of(this).get(DataBaseViewModel::class.java)
         initUi()
         clearCart.setOnClickListener(){
             alertDialog()
         }
    }
    fun initUi()
    {
        cartproductlist = databaseModel.getCartProduct()
        var mListRecyclerView = findViewById(R.id.recyclerView) as RecyclerView
        cartText = findViewById(R.id.cartText) as TextView
        cartTotal = findViewById(R.id.carttotal) as TextView
        clearCart = findViewById(R.id.clearcart) as TextView
        emptyCart = findViewById(R.id.emptycart) as TextView
        cartTotalText = findViewById(R.id.carttotaltext) as TextView
        linearLayoutManager = LinearLayoutManager(this)
        mListRecyclerView.layoutManager = linearLayoutManager
        val adapter = CartProductAdapter(this@CartActivity,cartproductlist)
        mListRecyclerView.adapter = adapter
        setTotal(cartproductlist)
        setTotalItems(cartproductlist)
    }

    private fun setTotal(cartList : List<Product>) {
        var price = 0
        for (item in cartList) {

            var productPrice = item.price.replace(",", "")
            var finalPrice = productPrice.replace("₹", "")
            var mulitiply =finalPrice.toInt()*item.quantity.toInt()
            price = price + mulitiply
        }

        cartTotal.setText("₹"+price.toString())

    }

    private fun setTotalItems(cartList : List<Product>) {
        val total = " ( " + cartList.size.toString() + " )"
        cartText.setText("My Cart "+total)
        if(cartList.size<=0)
        {
            emptyCart.visibility=View.VISIBLE
            clearCart.visibility=View.GONE
            cartTotalText.visibility = View.GONE
            cartTotal.visibility = View.GONE
            cartText.visibility = View.GONE
        } else
        {
            emptyCart.visibility=View.GONE
            clearCart.visibility=View.VISIBLE
            cartTotalText.visibility = View.VISIBLE
            cartTotal.visibility = View.VISIBLE
            cartText.visibility = View.VISIBLE
        }
    }
    public fun productUpdate(product : Product)
    {
        Log.i("productQty",product.quantity)
        databaseModel.updateProduct(product)
        cartproductlist = databaseModel.getCartProduct()
        setTotal(cartproductlist)
        setTotalItems(cartproductlist)
    }
    fun getCartProduct():List<Product>{
        return databaseModel.getCartProduct()
    }
    fun alertDialog(){
        val builder = AlertDialog.Builder(this@CartActivity)

        builder.setMessage("Are you sure you want to clear cart?")
        builder.setPositiveButton("Yes") { dialog, which ->
            databaseModel.deleteRecord()
            initUi()
        }
        builder.setNegativeButton("No") { dialog, which ->
            dialog.dismiss()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
    }


