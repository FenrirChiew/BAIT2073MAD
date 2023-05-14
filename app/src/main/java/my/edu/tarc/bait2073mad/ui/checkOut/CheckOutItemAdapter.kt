package my.edu.tarc.bait2073mad.ui.checkOut

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import my.edu.tarc.bait2073mad.R
import my.edu.tarc.bait2073mad.ui.cart.CartItem

class CheckOutItemAdapter : RecyclerView.Adapter<CheckOutItemAdapter.ViewHolder>() {
    private var checkOutItemList = emptyList<CartItem>()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val productNo: TextView = view.findViewById(R.id.textViewCheckOutItemCount)
        val productName: TextView = view.findViewById(R.id.textViewCheckOutItemName)
        val productPrice: TextView = view.findViewById(R.id.textViewCheckOutItemPrice)
        val productQuantity: TextView = view.findViewById(R.id.textViewCheckOutItemQuantity)
    }

    internal fun setCheckOutItem(cartItem: List<CartItem>) {
        this.checkOutItemList = cartItem
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.fragment_check_out_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = checkOutItemList[position]

        holder.productNo.text = (position + 1).toString()
        holder.productName.text = currentItem.productName
        holder.productPrice.text = currentItem.productPrice.toString()
        holder.productQuantity.text = currentItem.quantity.toString()

        holder.itemView.setOnClickListener {
            Toast.makeText(it.context, "Clicked", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return checkOutItemList.size
    }
}