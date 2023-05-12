package my.edu.tarc.bait2073mad.ui.product

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import my.edu.tarc.bait2073mad.databinding.FragmentAddProductBinding
import my.edu.tarc.bait2073mad.R

class AddProductFragment : Fragment(), MenuProvider {

    private var _binding: FragmentAddProductBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    // Refers to the ViewModel created by the Main Activity
    private val myProductViewModel: ProductViewModel by activityViewModels()
    private var isEditing: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAddProductBinding.inflate(inflater, container, false)

        //Let ProfileFragment to manage the Menu
        val menuHost: MenuHost = this.requireActivity()
        menuHost.addMenuProvider(
            this, viewLifecycleOwner,
            Lifecycle.State.RESUMED
        )

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Determine the view mode; edit or new
        isEditing = myProductViewModel.selectedIndex != -1
        if (isEditing) {
            with(binding) {
                val product: Product =
                    myProductViewModel.productList.value!!.get(myProductViewModel.selectedIndex)
                editTextProductID.setText(product.productID)
                editTextProductName.setText(product.productName)
                editTextProductPrice.setText(product.productPrice.toString())
                editTextProductStatus.setText(product.productStatus)
                editTextProductDescriptions.setText(product.productDescriptions)
                editTextProductSeller.setText(product.seller)
                editTextProductName.requestFocus()
                editTextProductID.isEnabled = false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        myProductViewModel.selectedIndex = -1
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menu.clear()
        menuInflater.inflate(R.menu.add_product_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == R.id.action_save) {
            if (binding.editTextProductName.text.isEmpty()) {
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
                binding.apply {
                    val productID = editTextProductID.text.toString()
                    val productName = editTextProductName.text.toString()
                    val productPrice = editTextProductPrice.text.toString().toDouble()
                    val productStatue = editTextProductStatus.text.toString()
                    val productFavorite = false
                    val productDescriptions = editTextProductDescriptions.text.toString()
                    val productSeller = editTextProductSeller.text.toString()
                    val newProduct = Product(productID, productName, productPrice, productStatue, productFavorite, productDescriptions, productSeller)
                    myProductViewModel.addProduct(newProduct)
                    if (isEditing) {
                        myProductViewModel.updateProduct(newProduct)
                    } else {
                        myProductViewModel.addProduct(newProduct)
                    }
                }
                Toast.makeText(context, getString(R.string.product_saved), Toast.LENGTH_SHORT)
                    .show()
                findNavController().navigateUp()
            }
        } else if (menuItem.itemId == android.R.id.home) {
            findNavController().navigateUp()
        }
        return true
    }
}