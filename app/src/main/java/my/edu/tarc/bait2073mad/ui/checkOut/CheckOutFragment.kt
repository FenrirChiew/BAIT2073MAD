package my.edu.tarc.bait2073mad.ui.checkOut

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import my.edu.tarc.bait2073mad.R
import my.edu.tarc.bait2073mad.databinding.FragmentCheckOutBinding
import my.edu.tarc.bait2073mad.ui.cart.CartViewModel
import my.edu.tarc.bait2073mad.ui.recentOrder.RecentOrder
import my.edu.tarc.bait2073mad.ui.recentOrder.RecentOrderViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CheckOutFragment : Fragment() {
    //viewModel
    private val cartViewModel: CartViewModel by activityViewModels()
    private val checkOutViewModel: CheckOutViewModel by activityViewModels()
    private val recentOrderViewModel: RecentOrderViewModel by activityViewModels()

    //binding
    private var _binding: FragmentCheckOutBinding? = null
    private val binding get() = _binding!!
    val adapter = CheckOutItemAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCheckOutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var subtotal: Double = 0.0
        var totalPayment: Double = 0.0

        binding.applyVoucherbutton.setOnClickListener {
            findNavController().navigate(R.id.action_checkOutFragment_to_voucherFragment)
        }

        binding.paymentMethodButton.setOnClickListener {
            checkOutViewModel.paymentMethodButtonClicked = true
            val voucher = arguments?.getString("voucherName", "")
            val voucherName = voucher.toString()
            val action = CheckOutFragmentDirections.actionCheckOutFragmentToPaymentMethodFragment(
                voucherName
            )
            findNavController().navigate(action)
        }

        val voucherName = arguments?.getString("voucherName", "")
        binding.textViewVoucherApply.text = voucherName

        //shipping fee fix is RM5.00
        var voucherAmount = 0.0
        var shippingFee = 5.00
        voucherAmount = if (voucherName == "Free Shipping") {
            shippingFee
        } else if (voucherName == "Cashback RM3.00") {
            3.00
        } else if (voucherName == "RM10.00 Off") {
            10.00
        } else {
            0.0
        }

        //To retrieve the card number that input by user
        val cardNumber = arguments?.getLong("cardNumber", 0)
        val maskedCardNumber = "**** **** **** " + cardNumber.toString().takeLast(4)
        binding.textViewChosenPaymentMethod.text = maskedCardNumber


        cartViewModel.cartItemList.observe(
            viewLifecycleOwner,
            Observer {
                adapter.setCheckOutItem(it)
                for (element in it) {
                    subtotal += (element.productPrice * element.quantity)

                }
                binding.textViewVoucherDiscount.text = String.format("RM %.2f", voucherAmount)
                binding.textViewShippingFee.text = String.format("RM %.2f", shippingFee)
                totalPayment = calculation(subtotal, shippingFee, voucherAmount)
                binding.textViewTotalPayment.text = String.format("RM %.2f", totalPayment)
                binding.textViewSubTotalPrice.text = String.format("RM %.2f", subtotal)
                binding.textViewTotalCartItem.text = it.size.toString()
            }
        )
        binding.checkOutRecyclerView.adapter = adapter


        binding.buttonPlaceOrder.setOnClickListener {
            if (!checkOutViewModel.paymentMethodButtonClicked) {
                Snackbar.make(
                    requireView(),
                    "Please select a payment method",
                    Snackbar.LENGTH_SHORT
                ).show()
            } else {
                val builder = AlertDialog.Builder(requireActivity())
                builder.setMessage("Order Placed!")
                    .setPositiveButton("Ok") { _, _ ->
                        val date = Date()
                        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                        val dateString = dateFormat.format(date)

                        val dateTimeFormat = SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault())
                        val formattedDate = dateTimeFormat.format(date)

                        var orderID = "O$formattedDate"
                        recentOrderViewModel.addOrder(
                            RecentOrder(
                                orderID,
                                dateString,
                                totalPayment
                            )
                        )
                        cartViewModel.deleteAll()
                        findNavController().navigate(R.id.action_checkOutFragment_to_navigation_recent_order)
                    }
                builder.create().show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun calculation(subtotal: Double, shippingFee: Double, voucher: Double): Double {
        var totalPayment = 0.0

        totalPayment = (subtotal + shippingFee) - voucher

        return totalPayment
    }


}