package my.edu.tarc.bait2073mad.ui.product

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import my.edu.tarc.bait2073mad.databinding.FragmentAddProductBinding
import my.edu.tarc.bait2073mad.R
import my.edu.tarc.bait2073mad.ui.home.HomeViewModel
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.OutputStream

class AddProductFragment : Fragment(), MenuProvider {

    private var _binding: FragmentAddProductBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by activityViewModels()
    private val getPhoto = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            binding.imageViewProductImage.setImageURI(uri)
        }
    }
    val db = FirebaseFirestore.getInstance()
    private lateinit var docRef: DocumentReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddProductBinding.inflate(inflater, container, false)

        val menuHost: MenuHost = this.requireActivity()
        menuHost.addMenuProvider(
            this, viewLifecycleOwner,
            Lifecycle.State.RESUMED
        )

        docRef = db.collection("products").document("public")

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val productImage =
            readProductImage(binding.editTextProductID.text.toString().lowercase().plus(".jpg"))
        if (productImage != null) {
            binding.imageViewProductImage.setImageBitmap(productImage)
        } else {
            binding.imageViewProductImage.setImageResource(R.drawable.ic_product_black_24dp)
        }

        binding.imageViewProductImage.setOnClickListener {
            getPhoto.launch("image/*")
        }

        with(binding) {
            val productLength: Int = homeViewModel.productList.value!!.size
            editTextProductID.setText("P".plus("%04d".format(productLength)))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        homeViewModel.selectedIndex = -1
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.add_product_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.action_save -> {
                if (binding.editTextProductID.text.isEmpty()) {
                    Toast.makeText(context, "Missing Product ID", Toast.LENGTH_SHORT)
                        .show()
                } else if (binding.editTextProductName.text.isEmpty()) {
                    Toast.makeText(context, "Missing Product Name", Toast.LENGTH_SHORT)
                        .show()
                } else if (binding.editTextProductPrice.text.isEmpty()) {
                    Toast.makeText(context, "Missing Product Price", Toast.LENGTH_SHORT)
                        .show()
                } else if (binding.editTextProductStatus.text.isEmpty()) {
                    Toast.makeText(context, "Missing Product Status", Toast.LENGTH_SHORT)
                        .show()
                } else if (binding.editTextProductDescriptions.text.isEmpty()) {
                    Toast.makeText(context, "Missing Product Descriptions", Toast.LENGTH_SHORT)
                        .show()
                } else if (binding.editTextProductSeller.text.isEmpty()) {
                    Toast.makeText(context, "Missing Product Seller", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    // Save product image to the local storage
                    saveProductImage(binding.imageViewProductImage)
                    // Save product image to the cloud storage
                    uploadProductImage()

                    // Save product details to the view model
                    saveProductDetails()
                    // Save product details to the cloud storage
                    uploadProductDetails()

                    Toast.makeText(context, getString(R.string.product_saved), Toast.LENGTH_SHORT)
                        .show()
                    findNavController().navigateUp()
                }
            }

            android.R.id.home -> {
                findNavController().navigateUp()
            }
        }
        return true
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

    private fun saveProductImage(imageView: ImageView) {
        val filename = _binding?.editTextProductID?.text.toString().lowercase().plus(".jpg")
        val file = File(this.context?.filesDir, filename)

        val bd = imageView.drawable as BitmapDrawable
        val bitmap = bd.bitmap
        val outputStream: OutputStream

        try {
            outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream)
            outputStream.flush()
            outputStream.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
    }

    private fun uploadProductImage() {
        val filename = _binding?.editTextProductID?.text.toString().lowercase().plus(".jpg")
        val file = Uri.fromFile(File(this.context?.filesDir, filename))
        try {
            val storageRef = Firebase.storage("gs://mad-assignment-1967d.appspot.com/").reference
            if (_binding?.editTextProductID?.text.toString().isEmpty()) {
                Toast.makeText(context, getString(R.string.no_product_image), Toast.LENGTH_SHORT)
                    .show()
            } else {
                val profilePictureRef =
                    storageRef.child("file").child(_binding?.editTextProductID?.text.toString())
                profilePictureRef.putFile(file)
            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
    }

    private fun saveProductDetails() {
        binding.apply {
            val productID = editTextProductID.text.toString()
            val productName = editTextProductName.text.toString()
            val productPrice = editTextProductPrice.text.toString().toDouble()
            val productStatus = editTextProductStatus.text.toString()
            val productDescriptions = editTextProductDescriptions.text.toString()
            val productSeller = editTextProductSeller.text.toString()
            val newProduct = Product(
                productID,
                productName,
                productPrice,
                productStatus,
                productSeller,
                productDescriptions
            )
            homeViewModel.addProduct(newProduct)
        }
    }

    private fun uploadProductDetails() {
        homeViewModel.productList.observe(viewLifecycleOwner) {
            val products = mutableListOf<Map<String, Any>>()
            for (product in it) {
                products.add(
                    mapOf(
                        "ProductID" to product.productID,
                        "ProductName" to product.productName,
                        "ProductPrice" to product.productPrice,
                        "ProductStatus" to product.productStatus,
                        "ProductSeller" to product.seller,
                        "ProductDescriptions" to product.productDescriptions
                    )
                )
            }
            docRef.set(mapOf("products" to products))
        }
    }
}
