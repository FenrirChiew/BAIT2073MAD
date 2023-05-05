package my.edu.tarc.bait2073mad.ui.product

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import my.edu.tarc.bait2073mad.R

class ProductAdapter : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {
    private var productList = emptyList<Product>()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageViewProductImage: ImageView = view.findViewById(R.id.imageViewProductImage)
        val textViewProductName: TextView = view.findViewById(R.id.textViewProductName)
        val textViewProductPrice: TextView = view.findViewById(R.id.textViewProductPrice)
    }

    internal fun setProduct(contact: List<Product>) {
        this.productList = contact
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //Create a new view, which define the UI of the list item
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Get element from the dataset at this position and replace the contents of the view with that element
        holder.imageViewProductImage.setImageBitmap(productList[position].productImage)
        holder.textViewProductName.text = productList[position].productName
        holder.textViewProductPrice.text = productList[position].productPrice.toString()
        holder.itemView.setOnClickListener {
            //Item click event handler
            Toast.makeText(
                it.context,
                "Contact name:" + productList[position].productName,
                Toast.LENGTH_SHORT
            ).show()

            androidx.navigation.Navigation.findNavController(it)
                .navigate(R.id.action_products_fragment_to_product_details_fragment)
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }
}
