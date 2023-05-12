package my.edu.tarc.bait2073mad.ui.checkOut

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import my.edu.tarc.bait2073mad.R

class CheckOutItemAdapter: RecyclerView.Adapter<CheckOutItemAdapter.ViewHolder>() {
    private var checkOutItemList = emptyList<CheckOutItem>()

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val productName: TextView = view.findViewById(R.id.textViewProductName)
        //val productImage: ShapeableImageView = view.findViewById(R.id.imageViewProduct)
        val productPrice: TextView = view.findViewById(R.id.textViewProductPrice)
        val productQuantity: TextView = view.findViewById(R.id.textViewCartItemProductQuantity)
    }

    internal fun setCheckOutItem(checkOutItem: List<CheckOutItem>){
        this.checkOutItemList = checkOutItem
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.fragment_check_out,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = checkOutItemList[position]
//        holder.productImage.setImageResource(currentItem.productImage)
        holder.productName.text = currentItem.productName.toString()
        holder.productPrice.text = currentItem.productPrice.toString()
        holder.productQuantity.text = currentItem.productQuantity.toString()
        holder.itemView.setOnClickListener {
            Toast.makeText(it.context,"Clicked",Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return checkOutItemList.size
    }
}