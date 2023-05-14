package my.edu.tarc.bait2073mad.ui.voucher

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import my.edu.tarc.bait2073mad.R

class VoucherItemAdapter(private val recordClickListener: RecordClickListener):
    RecyclerView.Adapter<VoucherItemAdapter.ViewHolder>() {
    private var voucherItemList = emptyList<VoucherItem>()

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val voucherName: TextView = view.findViewById(R.id.textViewVoucherName)
        val voucherTerms: TextView = view.findViewById(R.id.textViewVoucherTerm)
        val voucherDate: TextView = view.findViewById(R.id.textViewVoucherExpired)
    }

    internal fun setVoucherItem(voucherItem: List<VoucherItem>){
        this.voucherItemList = voucherItem
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.fragment_voucher_item,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = voucherItemList[position]
        holder.voucherName.text = currentItem.voucherName.toString()
        holder.voucherTerms.text = currentItem.voucherTerms.toString()
        holder.voucherDate.text = currentItem.voucherDate.toString()
//
//        Picasso.get()
//            .load("file:////Users//User//Desktop//fpx-logo.jpg/${currentItem.voucherImage}")
//            .into(holder.voucherImage)
//
//        Picasso.get()
//            .load(currentItem.voucherImage)
//            .placeholder(R.drawable.voucher)
//            .into(holder.voucherImage)

        //Not sure the check box how do in this case
        //set event when click the contact
        holder.itemView.setOnClickListener {
            //Item click event handler
            recordClickListener.onRecordClickListener(position)
        }
    }

    override fun getItemCount(): Int {
        return voucherItemList.size
    }

}

interface RecordClickListener{
    fun onRecordClickListener(index: Int)
}