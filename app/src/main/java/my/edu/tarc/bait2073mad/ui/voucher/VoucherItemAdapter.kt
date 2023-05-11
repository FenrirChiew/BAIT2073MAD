package my.edu.tarc.bait2073mad.ui.voucher

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import my.edu.tarc.bait2073mad.R

class VoucherItemAdapter: RecyclerView.Adapter<VoucherItemAdapter.ViewHolder>() {
    private var voucherItemList = emptyList<VoucherItem>()

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val voucherName: TextView = view.findViewById(R.id.textViewVoucherName)
        val voucherImage: ShapeableImageView = view.findViewById(R.id.imageViewVoucher)
        val voucherTerms: TextView = view.findViewById(R.id.textViewVoucherTerm)
    }

    internal fun setVoucherItem(voucherItem: List<VoucherItem>){
        this.voucherItemList = voucherItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.fragment_voucher_item,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = voucherItemList[position]
        //holder.voucherImage.setImageResource(currentItem.voucherImage)
        holder.voucherName.text = currentItem.voucherName.toString()
        holder.voucherTerms.text = currentItem.voucherTerms.toString()
        holder.itemView.setOnClickListener {
            Toast.makeText(it.context,"Clicked",Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return voucherItemList.size
    }
}