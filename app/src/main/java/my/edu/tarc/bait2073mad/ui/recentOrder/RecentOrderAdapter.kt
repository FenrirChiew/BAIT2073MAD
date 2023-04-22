package my.edu.tarc.bait2073mad.ui.recentOrder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import my.edu.tarc.bait2073mad.R
import my.edu.tarc.bait2073mad.ui.cart.CartItem

class RecentOrderAdapter: RecyclerView.Adapter<RecentOrderAdapter.ViewHolder>() {
    private var recentOrderList = emptyList<RecentOrder>()

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val orderID: TextView = view.findViewById(R.id.textViewOrderID)
        val orderDate: TextView = view.findViewById(R.id.textViewOrderDate)
        val orderAmount: TextView = view.findViewById(R.id.textViewOrderAmount)
    }

    internal fun setRecentOrder(recentOrder: List<RecentOrder>){
        this.recentOrderList = recentOrder
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //not sure fragment_recent_order_item correct or not
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.fragment_recent_order_item,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentRecentOrder = recentOrderList[position]
        holder.orderID.text = currentRecentOrder.orderID.toString()
        holder.orderDate.text = currentRecentOrder.orderDate.toString()
        holder.orderAmount.text = currentRecentOrder.total.toString()

        //set if the row is clicked
        holder.itemView.setOnClickListener {
            Toast.makeText(it.context,"Clciked", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return recentOrderList.size
    }
}