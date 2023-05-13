package my.edu.tarc.bait2073mad.ui.cart

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import my.edu.tarc.bait2073mad.R
import java.io.File
import java.io.FileInputStream


class CartItemAdapter(private val recordClickListener: RecordClickListener) :
    RecyclerView.Adapter<CartItemAdapter.ViewHolder>() {
    private var cartItemList = emptyList<CartItem>()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageViewProductImage: ImageView = view.findViewById(R.id.imageViewCartItemProductImg)
        val textViewProductName: TextView = view.findViewById(R.id.textViewCartItemProductName)
        val textViewProductPrice: TextView = view.findViewById(R.id.textViewCartItemProductPrice)
        val textViewProductQuantity: TextView =
            view.findViewById(R.id.textViewCartItemProductQuantity)
    }

    internal fun setCartItem(cartItem: List<CartItem>) {
        this.cartItemList = cartItem
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //Create a new view, which define the UI of the list item
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cart_record, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Get element from the dataset at this position and replace the contents of the view with that element
//        holder.imageViewProductImage.setImageResource(R.drawable.ic_product_black_24dp)
//        Picasso.get().load(File(, "P001")).into(holder.imageViewProductImage)
//        holder.imageViewProductImage.setImageBitmap(readProductPicture("P001"))
        holder.textViewProductName.text = cartItemList[position].productName
        holder.textViewProductQuantity.text = cartItemList[position].quantity.toString()
        holder.textViewProductPrice.text = cartItemList[position].productPrice.toString()


        holder.itemView.setOnClickListener {
            //Item click event handler
            recordClickListener.onRecordClickListener(position)
//            Toast.makeText(
//                it.context,
//                "Product name:" + cartItemList[position].productName,
//                Toast.LENGTH_SHORT
//            ).show()
        }
    }

    override fun getItemCount(): Int {
        return cartItemList.size
    }

    private fun readProductPicture(filename: String): Bitmap?{
        val sdCard = Environment.getExternalStorageDirectory()
        val directory = File(sdCard.absolutePath + "/Pictures")
        val file = File(directory, filename) //or any other format supported
        val streamIn = FileInputStream(file)
        val bitmap = BitmapFactory.decodeStream(streamIn) //This gets the image
        streamIn.close()
        return bitmap
    }

}// End of Adapter Class

interface RecordClickListener {
    fun onRecordClickListener(index: Int)
}