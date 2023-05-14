package my.edu.tarc.bait2073mad.ui.cart


import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import my.edu.tarc.bait2073mad.R
import java.io.File
import java.io.FileNotFoundException


class CartItemAdapter(
    private val context: Context,
    private val recordClickListener: RecordClickListener
) :
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
        val productImage =
            readProductImage(cartItemList[position].productID.lowercase().plus(".jpg"))
        if (productImage != null) {
            holder.imageViewProductImage.setImageBitmap(productImage)
        } else {
            holder.imageViewProductImage.setImageResource(R.drawable.ic_product_black_24dp)
        }

        holder.textViewProductName.text = cartItemList[position].productName
        holder.textViewProductQuantity.text = cartItemList[position].quantity.toString()
        holder.textViewProductPrice.text = String.format("RM %.2f", cartItemList[position].productPrice)


        holder.itemView.setOnClickListener {
            //Item click event handler
            recordClickListener.onRecordClickListener(position)
        }
    }

    override fun getItemCount(): Int {
        return cartItemList.size
    }

    private fun readProductImage(filename: String): Bitmap? {
        val file = File(this.context.filesDir, filename)

        if (file.isFile) {
            try {
                return BitmapFactory.decodeFile(file.absolutePath)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }
        return null
    }

}// End of Adapter Class

interface RecordClickListener {
    fun onRecordClickListener(index: Int)
}