package xpertii.prasathinterview.viewholders

import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import xpertii.prasathinterview.CartActivity
import xpertii.prasathinterview.R
import xpertii.prasathinterview.adapters.CartProductAdapter
import xpertii.prasathinterview.adapters.ProductAdapter
import xpertii.prasathinterview.model.Product

class CartProductViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {

    lateinit var productImage : ImageView
    lateinit var productName : TextView
    lateinit var productPrice : TextView
    lateinit var productQtyInc : TextView
    lateinit var productQtyDec : TextView
    lateinit var productQty : TextView
    lateinit var productQtyPrice : TextView
    fun bindItems(mContext : Context, product: Product, productAdap : CartProductAdapter, position : Int) {
        productImage = itemView.findViewById(R.id.productimage) as ImageView
        productName  = itemView.findViewById(R.id.productname) as TextView
        productPrice  = itemView.findViewById(R.id.productprice) as TextView
        productQtyDec  = itemView.findViewById(R.id.productqtydecrement) as TextView
        productQty  = itemView.findViewById(R.id.productqty) as TextView
        productQtyInc  = itemView.findViewById(R.id.productqtyincrement) as TextView
        productQtyPrice  = itemView.findViewById(R.id.productqtyprice) as TextView
        productName.text = product.name
        productPrice.text = product.price
        Picasso.with(mContext)
            .load(product.image)
            .into(productImage)

    }
    fun showCartQty(qty: String) {
        productQty.text=qty
    }
    fun showProductTotalPriceQty(qty: String) {
        productQtyPrice.text="₹"+qty
    }
    fun qtyIncrement(mContext : Context,product : Product,productAdap : CartProductAdapter,position : Int)
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
            (mContext as CartActivity).productUpdate(updateProduct)
            callAdapter(mContext,productAdap)
            productAdap.notifyItemChanged(position)
        }
    }
    fun qtyDecrement(mContext : Context,product : Product,productAdap : CartProductAdapter,position : Int)
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
                (mContext as CartActivity).productUpdate(updateProduct)
                callAdapter(mContext,productAdap)
                productAdap.notifyItemChanged(position)
            }
        }
    }
    fun callAdapter(mContext : Context, productAdap : CartProductAdapter)
    {
        productAdap.updateCartProductList((mContext as CartActivity).getCartProduct())

    }
}