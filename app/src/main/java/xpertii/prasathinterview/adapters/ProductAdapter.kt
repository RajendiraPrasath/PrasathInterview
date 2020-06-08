package xpertii.prasathinterview.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import xpertii.prasathinterview.R
import xpertii.prasathinterview.model.Product
import xpertii.prasathinterview.viewholders.ProductViewHolder

class ProductAdapter(var mContext: Context,var productlist:List<Product>,var cartproductlist:List<Product>) : RecyclerView.Adapter<ProductViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.recyclear_product_list, parent, false)
        return ProductViewHolder(v)
    }


    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {

        var item : Product = productlist[position]
        holder.bindItems(mContext,item,this,position)
        holder.addProduct(mContext,item,this,position)
        var productItem : Product? = getFromCart(productlist[position].product_id)
        if(productItem!=null && productItem.product_id.equals(item.product_id))
        {
            holder.showQty(true)
            holder.showAddCart(false)
            holder.showCartQty(productItem.quantity)
            holder.qtyIncrement(mContext,productItem,this,position)
            holder.qtyDecrement(mContext,productItem,this,position)
        } else {
            holder.showQty(false)
            holder.showAddCart(true)
                    }

    }
    override fun getItemCount(): Int {
        return productlist.size
    }
    private fun getFromCart(productID: String): Product? {

        return if (cartproductlist.size > 0) {
            for (item in cartproductlist) {
                if (item.product_id.equals(productID)) return item
            }
            null
        } else {
            null
        }
    }

    fun updateCartProductList(cartList : List<Product>){
        cartproductlist = cartList
        notifyDataSetChanged()
    }
}