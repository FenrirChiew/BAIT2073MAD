package my.edu.tarc.bait2073mad.ui.cart

import android.app.AlertDialog
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import my.edu.tarc.bait2073mad.R
import my.edu.tarc.bait2073mad.databinding.FragmentCartItemDetailBinding
import java.io.File
import java.io.FileNotFoundException

class CartItemDetailFragment : Fragment() {

    private var _binding: FragmentCartItemDetailBinding? = null
    private val binding get() = _binding!!
    private val cartViewModel: CartViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartItemDetailBinding.inflate(inflater, container, false)

        //Let ProfileFragment to manage the Menu
//        val menuHost: MenuHost = this.requireActivity()
//        menuHost.addMenuProvider(
//            this, viewLifecycleOwner,
//            Lifecycle.State.RESUMED
//        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            //value!! is for the current contactList information
            val cartItem: CartItem =
                cartViewModel.cartItemList.value!![cartViewModel.selectedIndex]
            //to pass the current value to the edit text
            val productImage = readProductImage(cartItem.productID.lowercase().plus(".jpg"))
            if (productImage != null) {
                imageViewCartItemDetailProductImage.setImageBitmap(productImage)
            } else {
                imageViewCartItemDetailProductImage.setImageResource(R.drawable.ic_product_black_24dp)
            }
            textViewCartItemDetailID.text = cartItem.productID
            textViewCartItemDetailName.text = cartItem.productName
            textViewCartItemDetailPrice.text = cartItem.productPrice.toString()
            textViewCartItemDetailQuantity.text = cartItem.quantity.toString()
        }

        binding.buttonCartItemDetailDelete.setOnClickListener {
            val builder = AlertDialog.Builder(requireActivity())
            builder.setMessage(getString(R.string.delete_record))
                .setPositiveButton(getString(R.string.delete)) { _, _ ->
                    cartViewModel.deleteCartItem(
                        cartViewModel.cartItemList.value!![cartViewModel.selectedIndex]
                    )
                    findNavController().navigateUp()
                }.setNegativeButton(getString(R.string.cancel)) { _, _ ->
                    //Do nothing here
                }
            builder.create().show()
        }

        binding.imageButtonCartItemDetailPlus.setOnClickListener {
            val currentQuantity: Int =
                binding.textViewCartItemDetailQuantity.text.toString().toInt()
            if (currentQuantity >= 9) {
                Toast.makeText(context, "Value Reached Maximum!", Toast.LENGTH_SHORT).show()
            } else {
                binding.textViewCartItemDetailQuantity.text = (currentQuantity + 1).toString()
            }
        }

        binding.imageButtonCartItemDetailMinus.setOnClickListener {
            val currentQuantity: Int =
                binding.textViewCartItemDetailQuantity.text.toString().toInt()
            if (currentQuantity <= 1) {
                Toast.makeText(context, "Value Reached Minimum!", Toast.LENGTH_SHORT).show()
            } else {
                binding.textViewCartItemDetailQuantity.text = (currentQuantity - 1).toString()
            }
        }

        binding.buttonCartItemDetailComplete.setOnClickListener {
            val currentProductID: String = binding.textViewCartItemDetailID.text.toString()
            val currentProductName: String = binding.textViewCartItemDetailName.text.toString()
            val currentProductPrice: Double =
                binding.textViewCartItemDetailPrice.text.toString().toDouble()
            val currentQuantity: Int =
                binding.textViewCartItemDetailQuantity.text.toString().toInt()

            val updatedCartItem = CartItem(
                currentProductID,
                currentProductName,
                currentProductPrice,
                currentQuantity
            )
            cartViewModel.updateCartItem(updatedCartItem)
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        cartViewModel.selectedIndex = -1
    }

    private fun readProductImage(filename: String): Bitmap? {
        val file = File(this.context?.filesDir, filename)

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