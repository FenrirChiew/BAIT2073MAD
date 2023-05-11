package my.edu.tarc.bait2073mad.ui.cart

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import my.edu.tarc.bait2073mad.R

class CartItemAdapter(): RecyclerView.Adapter<CartItemAdapter.ViewHolder>() {
    private var cartItemList = emptyList<CartItem>()

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val productName: TextView = view.findViewById(R.id.textViewProductName)
        val productImage: ShapeableImageView = view.findViewById(R.id.imageViewProduct)
        val productPrice: TextView = view.findViewById(R.id.textViewProductPrice)
    }

    internal fun setCartItem(cartItem: List<CartItem>){
        this.cartItemList = cartItem
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.fragment_cart_item,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = cartItemList[position]
        holder.productImage.setImageResource(R.drawable.credit_card)
        holder.productName.text = currentItem.productName
        holder.productPrice.text = currentItem.productPrice.toString()

        //event when click the contact
        holder.itemView.setOnClickListener {
            Toast.makeText(it.context,"Clicked",Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return cartItemList.size
    }

    interface RecordClickListener{
        fun onRecordClickListener(index: Int)
    }
}