package my.edu.tarc.bait2073mad.ui.product

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import my.edu.tarc.bait2073mad.R
import java.io.File
import java.io.FileNotFoundException

class ProductAdapter(
    private val context: Context,
    private val recordClickListener: RecordClickListener
) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {
    private var productList = emptyList<Product>()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageViewProductImage: ImageView = view.findViewById(R.id.imageViewProductImage)
        val textViewProductName: TextView = view.findViewById(R.id.textViewProductName)
        val textViewProductPrice: TextView = view.findViewById(R.id.textViewProductPrice)
    }

    internal fun setProduct(productList: List<Product>) {
        this.productList = productList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //Create a new view, which define the UI of the list item
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Get element from the dataset at this position and replace the contents of the view with that element
        val productImage =
            readProductImage(productList[position].productID.lowercase().plus(".jpg"))
        if (productImage != null) {
            holder.imageViewProductImage.setImageBitmap(productImage)
        } else {
            holder.imageViewProductImage.setImageResource(R.drawable.ic_product_black_24dp)
        }
        holder.textViewProductName.text = productList[position].productName
        holder.textViewProductPrice.text = context.getString(R.string.currency_myr).plus(" ")
            .plus("%.2f".format(productList[position].productPrice))
        holder.itemView.setOnClickListener {
            //Item click event handler
            Toast.makeText(
                it.context,
                "Product name: " + productList[position].productName,
                Toast.LENGTH_SHORT
            ).show()
            recordClickListener.onRecordClickListener(
                productList[position].productID.substring(
                    productList[position].productID.length - 4
                ).toInt()
            )
        }
    }

    override fun getItemCount(): Int {
        return productList.size
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
}

interface RecordClickListener {
    fun onRecordClickListener(index: Int)
}
