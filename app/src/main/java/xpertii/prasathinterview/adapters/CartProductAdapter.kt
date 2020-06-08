package xpertii.prasathinterview.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import xpertii.prasathinterview.R
import xpertii.prasathinterview.model.Product
import xpertii.prasathinterview.viewholders.CartProductViewHolder


class CartProductAdapter(var mContext: Context, var productlist:List<Product>) : RecyclerView.Adapter<CartProductViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartProductViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.cart_product_list, parent, false)
        return CartProductViewHolder(v)
    }


    override fun onBindViewHolder(holder: CartProductViewHolder, position: Int) {
        var item : Product = productlist[position]
        holder.bindItems(mContext,item,this,position)

        var productPrice = item.price.replace(",", "")
        var finalPrice = productPrice.replace("₹", "")
        var mulitiply =finalPrice.toInt()*item.quantity.toInt()
        holder.showProductTotalPriceQty(mulitiply.toString())
        holder.showCartQty(item.quantity)
        holder.qtyIncrement(mContext,item,this,position)
        holder.qtyDecrement(mContext,item,this,position)

    }


    override fun getItemCount(): Int {
        return productlist.size
    }
    fun updateCartProductList(cartList : List<Product>){
        productlist = cartList
        notifyDataSetChanged()
    }

}