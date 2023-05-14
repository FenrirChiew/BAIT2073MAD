package my.edu.tarc.bait2073mad.ui.voucher

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import my.edu.tarc.bait2073mad.R
import java.io.File
import java.io.FileNotFoundException

class VoucherItemAdapter(private val recordClickListener: RecordClickListener, private val context: Context):
    RecyclerView.Adapter<VoucherItemAdapter.ViewHolder>() {
    private var voucherItemList = emptyList<VoucherItem>()

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val imageViewVoucher: ImageView = view.findViewById(R.id.imageViewVoucher)
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
        //val voucherImage = readVoucherImage(R.drawable.voucher.toString().substring(0,6).plus("jpg"))
        val currentItem = voucherItemList[position]
        holder.imageViewVoucher.setImageResource(R.drawable.voucher)
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

    private fun readVoucherImage(filename: String): Bitmap?{
        val file = File(this.context.filesDir, filename)
        if(file.isFile){
            try{
                return BitmapFactory.decodeFile(file.absolutePath)
            }catch (e: FileNotFoundException){
                e.printStackTrace()
            }
        }
        return null
    }

}

interface RecordClickListener{
    fun onRecordClickListener(index: Int)
}