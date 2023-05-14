package my.edu.tarc.bait2073mad.ui.account

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.squareup.picasso.Callback
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import my.edu.tarc.bait2073mad.R
import my.edu.tarc.bait2073mad.databinding.FragmentAccountBinding
import my.edu.tarc.bait2073mad.ui.Login
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class AccountFragment : Fragment() {

    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var docRef: DocumentReference
    private lateinit var storageReference: StorageReference
    private lateinit var currentPhotoPath: String

    private val REQUEST_IMAGE_PICK = 1
    private val REQUEST_IMAGE_CAPTURE = 2
    private val PERMISSION_REQUEST_CODE = 3

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val accountViewModel = ViewModelProvider(this)[AccountViewModel::class.java]

        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        storageReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://mad-assignment-1967d.appspot.com")
        val userID = auth.currentUser?.uid
        var username: String
        docRef = db.collection("users").document(userID!!)

        docRef.get()
            .addOnSuccessListener { doc ->
                if (doc != null && doc.exists()) {
                    val profileData = doc.data
                    username = profileData?.get("username") as String
                    binding.usernameTextView.text = username

                    // Check if the profile picture URL exists
                    if (profileData.containsKey("profilePictureUrl")) {
                        val profilePictureUrl = profileData["profilePictureUrl"] as String

                        // Load the profile picture from the URL using Picasso with caching
                        Picasso.get()
                            .load(profilePictureUrl)
                            .networkPolicy(NetworkPolicy.OFFLINE) // Enable caching
                            .placeholder(R.drawable.profile_picture_placeholder)
                            .into(binding.profilePictureImageView, object : Callback {
                                override fun onSuccess() {
                                    // Image loaded successfully from cache
                                }

                                override fun onError(e: Exception?) {
                                    // Failed to load image from cache, try loading from network
                                    Picasso.get()
                                        .load(profilePictureUrl)
                                        .placeholder(R.drawable.profile_picture_placeholder)
                                        .into(binding.profilePictureImageView)
                                }
                            })
                    }
                }
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Data Read Failed", Toast.LENGTH_SHORT).show()
            }

        binding.editProfileIcon.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_account_to_editProfileFragment)
        }

        binding.profilePictureImageView.setOnClickListener {
            requestPermissions()
        }

        binding.buttonLogout.setOnClickListener {
            auth.signOut()

            val intent = Intent(requireContext(), Login::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

        accountViewModel.text.observe(viewLifecycleOwner) {

        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(
                    requireContext(),
            Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA
                    ),
                    PERMISSION_REQUEST_CODE
                )
            } else {
                showImageSelectionDialog()
            }
        } else {
            showImageSelectionDialog()
        }
    }

    private fun showImageSelectionDialog() {
        val options = arrayOf("Choose from Gallery", "Take a Photo")

        AlertDialog.Builder(requireContext())
            .setTitle("Select Option")
            .setItems(options) { dialog, which ->
                when (which) {
                    0 -> openGallery()
                    1 -> openCamera()
                }
            }
            .show()
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_IMAGE_PICK)
    }

    private fun openCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(requireActivity().packageManager) != null) {
            val photoFile: File? = try {
                createImageFile()
            } catch (ex: IOException) {
                // Handle error occurred while creating the File
                null
            }
            photoFile?.let {
                val photoURI: Uri = FileProvider.getUriForFile(
                    requireContext(),
                    "my.edu.tarc.bait2073mad.fileprovider",
                    it
                )
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File? = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_IMAGE_PICK -> {
                    val selectedImageUri: Uri? = data?.data
                    selectedImageUri?.let {
                        uploadImageToFirebase(it)
                    }
                }
                REQUEST_IMAGE_CAPTURE -> {
                    val photoUri: Uri? = Uri.parse(currentPhotoPath)
                    photoUri?.let {
                        uploadImageToFirebase(it)
                    }
                }
            }
        }
    }

    private fun uploadImageToFirebase(imageUri: Uri) {
        val userID = auth.currentUser?.uid
        val profileImageRef = storageReference.child("profile_images/$userID.jpg")

        try {
            val bitmap = if (Build.VERSION.SDK_INT < 28) {
                MediaStore.Images.Media.getBitmap(requireContext().contentResolver, imageUri)
            } else {
                val source = ImageDecoder.createSource(requireContext().contentResolver, imageUri)
                ImageDecoder.decodeBitmap(source)
            }

            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val data = baos.toByteArray()

            val uploadTask: UploadTask = profileImageRef.putBytes(data)
            uploadTask.addOnSuccessListener { taskSnapshot ->
                // Image uploaded successfully
                profileImageRef.downloadUrl.addOnSuccessListener { uri ->
                    // Update the user's profile picture URL in Firestore
                    docRef.update("profilePictureUrl", uri.toString())
                        .addOnSuccessListener {
                            // Profile picture URL updated successfully
                            // Display a success message
                            Toast.makeText(
                                requireContext(),
                                "Profile picture updated successfully",
                                Toast.LENGTH_SHORT
                            ).show()

                            // Set the image to the ImageView
                            binding.profilePictureImageView.setImageURI(imageUri)
                        }
                        .addOnFailureListener { exception ->
                            // Profile picture URL update failed
                            Log.d(TAG, "Failed to update profile picture URL: ${exception.message}")
                            // Display an error message
                            Toast.makeText(
                                requireContext(),
                                "Failed to update profile picture",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                }
            }
                .addOnFailureListener { exception ->
                    // Image upload failed
                    Log.d(TAG, "Failed to upload image to Firebase Storage: ${exception.message}")
                    // Display an error message
                    Toast.makeText(
                        requireContext(),
                        "Failed to upload image",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        } catch (e: IOException) {
            e.printStackTrace()
            // Display an error message
            Toast.makeText(
                requireContext(),
                "Failed to upload image",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    companion object {
        private const val TAG = "AccountFragment"
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showImageSelectionDialog()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Permission denied",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}


