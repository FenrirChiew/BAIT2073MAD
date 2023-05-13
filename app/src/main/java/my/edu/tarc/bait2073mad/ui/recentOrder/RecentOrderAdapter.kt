package my.edu.tarc.bait2073mad.ui.recentOrder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import my.edu.tarc.bait2073mad.R

class RecentOrderAdapter : RecyclerView.Adapter<RecentOrderAdapter.ViewHolder>() {
    private var recentOrderList = emptyList<RecentOrder>()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val count: TextView = view.findViewById(R.id.textViewNo)
        val orderID: TextView = view.findViewById(R.id.textViewOrderID)
        val orderDate: TextView = view.findViewById(R.id.textViewOrderDate)
        val orderAmount: TextView = view.findViewById(R.id.textViewOrderAmount)
    }

    internal fun setRecentOrder(recentOrder: List<RecentOrder>) {
        this.recentOrderList = recentOrder
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //not sure fragment_recent_order_item correct or not
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_recent_order_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.count.text = (position + 1).toString()
        holder.orderID.text = recentOrderList[position].orderID
        holder.orderDate.text = recentOrderList[position].orderDate
        holder.orderAmount.text = String.format("%.2f",recentOrderList[position].total)
    }

    override fun getItemCount(): Int {
        return recentOrderList.size
    }
}