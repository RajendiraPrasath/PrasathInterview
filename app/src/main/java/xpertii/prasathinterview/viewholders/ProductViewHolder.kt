package xpertii.prasathinterview.viewholders

import android.content.Context
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import xpertii.prasathinterview.MainActivity
import xpertii.prasathinterview.R
import xpertii.prasathinterview.adapters.ProductAdapter
import xpertii.prasathinterview.model.Product

class ProductViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
    lateinit var qtyLayout : LinearLayout
    //lateinit var productAdd : Button
    lateinit var productAdd : LinearLayout
    lateinit var productQtyDec : TextView
    lateinit var productQty : TextView
    lateinit var productQtyInc : TextView
        fun bindItems(mContext : Context,product : Product,productAdap : ProductAdapter,position : Int) {
        val productImage = itemView.findViewById(R.id.productimage) as ImageView
        val productName  = itemView.findViewById(R.id.productname) as TextView
        val productPrice  = itemView.findViewById(R.id.productprice) as TextView
        //productAdd  = itemView.findViewById(R.id.productadd) as Button
            productAdd  = itemView.findViewById(R.id.productaddlayout) as LinearLayout
        qtyLayout  = itemView.findViewById(R.id.qntylayout) as LinearLayout
        productQtyDec  = itemView.findViewById(R.id.productqtydecrement) as TextView
        productQty  = itemView.findViewById(R.id.productqty) as TextView
        productQtyInc  = itemView.findViewById(R.id.productqtyincrement) as TextView
        productName.text = product.name
        productPrice.text = product.price


        Picasso.with(mContext)
            .load(product.image)
            .into(productImage)




    }
    fun callAdapter(mContext : Context, productAdap : ProductAdapter)
    {
       productAdap.updateCartProductList((mContext as MainActivity).getCartProduct())

    }
    fun qtyIncrement(mContext : Context,product : Product,productAdap : ProductAdapter,position : Int)
    {
        productQtyInc.setOnClickListener() {
            var incr: Int = product.quantity.toInt() + 1
            var updateProduct = Product(
                product.name,
                product.product_id,
                product.image,
                incr.toString(),
                product.price,
                product.special
            )
            (mContext as MainActivity).productUpdate(updateProduct)
            callAdapter(mContext,productAdap)
            productAdap.notifyItemChanged(position)
        }
    }
    fun qtyDecrement(mContext : Context,product : Product,productAdap : ProductAdapter,position : Int)
    {
        productQtyDec.setOnClickListener() {
            if(product.quantity.toInt()>1) {

                var incr: Int = product.quantity.toInt() - 1
                var updateProduct = Product(
                    product.name,
                    product.product_id,
                    product.image,
                    incr.toString(),
                    product.price,
                    product.special
                )
                (mContext as MainActivity).productUpdate(updateProduct)
                callAdapter(mContext,productAdap)
                productAdap.notifyItemChanged(position)
            }
        }
    }
    fun addProduct(mContext : Context,product : Product,productAdap : ProductAdapter,position : Int)
    {
        productAdd.setOnClickListener()
        {
            (mContext as MainActivity).productAdd(product)
            callAdapter(mContext,productAdap)
            productAdap.notifyItemChanged(position)
        }
    }
    fun showQty(show: Boolean) {
       //this.qtyLayout.setVisibility(if (show) View.VISIBLE else View.GONE)
        if(show){
            qtyLayout.visibility = View.VISIBLE
        } else {
            qtyLayout.visibility = View.GONE
        }
    }

    fun showAddCart(show: Boolean) {
        //this.productAdd.setVisibility(if (show) View.VISIBLE else View.GONE)
        if(show){
            productAdd.visibility = View.VISIBLE
        } else {
            productAdd.visibility = View.GONE
        }
    }

    fun showCartQty(qty: String) {
        productQty.text=qty
    }
}