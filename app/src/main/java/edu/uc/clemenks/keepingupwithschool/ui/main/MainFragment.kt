package edu.uc.clemenks.keepingupwithschool.ui.main

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.drawToBitmap
<<<<<<< HEAD
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserInfo
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.storage.FirebaseStorage
=======
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
>>>>>>> 193344e66447ba4ac6ad4fd81d631ca774a59405
import edu.uc.clemenks.keepingupwithschool.R
import kotlinx.android.synthetic.main.main_fragment.*


class PhotoToBeSaved(val course: String, val imageName: String, var imageRefURI: String, var documentId: String) {}

class MainFragment : Fragment() {

<<<<<<< HEAD
    private val AUTH_REQUEST_CODE = 2002
    private val CAMERA_PERMISSION_REQUEST_CODE = 1999
    private val CAMERA_REQUEST_CODE = 1998
    private lateinit var viewModel: MainViewModel
    private var firestore = FirebaseFirestore.getInstance()
    private var storageReference = FirebaseStorage.getInstance().getReference()
    private var user : FirebaseUser? = null
=======
    private val CAMERA_PERMISSION_REQUEST_CODE: Int = 1999
    private val CAMERA_REQUEST_CODE: Int = 1998
    private val LOCATION_PERMISSION_REQUEST_CODE = 2000

>>>>>>> 193344e66447ba4ac6ad4fd81d631ca774a59405


<<<<<<< HEAD


    init {
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
    }
=======
    private lateinit var viewModel: MainViewModel
    private lateinit var locationViewModel: LocationViewModel
>>>>>>> 193344e66447ba4ac6ad4fd81d631ca774a59405

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
       btnTakePic.setOnClickListener{
           prepTakePhoto()
       }

       btnSave.setOnClickListener{
           var URI = saveImageToInternalStorage(imgPhotoView.drawToBitmap() as Bitmap)
           saveImageToFireStore(URI.toString())
           Toast.makeText(context, "Photo Saved", Toast.LENGTH_LONG).show()
       }
<<<<<<< HEAD
        btnLogon.setOnClickListener {
            logon()
        }
=======
        prepRequestLocationUpdates()

    }

    //See if we have permission to get location
    private fun prepRequestLocationUpdates() {
        if (ContextCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            requestLocationUpdates()
        } else {
            val permissionRequest = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
            requestPermissions(permissionRequest, LOCATION_PERMISSION_REQUEST_CODE)
        }
    }
>>>>>>> 193344e66447ba4ac6ad4fd81d631ca774a59405

    private fun requestLocationUpdates() {
        locationViewModel = ViewModelProviders.of(this).get(LocationViewModel::class.java)
        locationViewModel.getLocationLiveData().observe(viewLifecycleOwner, Observer {
            lblLatitudeValue.text = it.latitude
            lblLongitudeValue.text = it.longitude
        })
    }

<<<<<<< HEAD
    private fun logon() {
        var providers = arrayListOf(AuthUI.IdpConfig.EmailBuilder().build())
        startActivityForResult(
            AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(providers).build(),
            AUTH_REQUEST_CODE
        )
    }

    //See if we have permission or not
=======
    //See if we have permission to take photo
>>>>>>> 193344e66447ba4ac6ad4fd81d631ca774a59405
    private fun prepTakePhoto() {
        if(ContextCompat.checkSelfPermission(context!!, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
            takePhoto()
        }else{
            val permissionRequest = arrayOf(Manifest.permission.CAMERA)
            requestPermissions(permissionRequest, CAMERA_PERMISSION_REQUEST_CODE)
        }

    }

    //Checks permission request for access to camera or Location
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            CAMERA_PERMISSION_REQUEST_CODE -> {
                //Checks if array has the correct code
                if(grantResults.contains(PackageManager.PERMISSION_GRANTED)){
                    //permission granted, lets do stuff
                    takePhoto()
                }else{
                    Toast.makeText(context, "Unable to take photo without permission", Toast.LENGTH_LONG).show()
                }
            }
            LOCATION_PERMISSION_REQUEST_CODE -> {
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    requestLocationUpdates()
                } else {
                    Toast.makeText(context, "Unable to update location without permission", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    //Takes the photo
    private fun takePhoto() {
       Intent(MediaStore.ACTION_IMAGE_CAPTURE).also {
           takePictureIntent -> takePictureIntent.resolveActivity(context!!.packageManager)?.also {
           startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE)
       }
       }
    }

    //Puts the Photo on the Image View
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == RESULT_OK){
            if(requestCode == CAMERA_REQUEST_CODE){

                //now we can get the thumbnail
                val imageBitmap = data!!.extras!!.get("data") as Bitmap
                imgPhotoView.setImageBitmap(imageBitmap)
            }
            else if(requestCode == AUTH_REQUEST_CODE){
                user = FirebaseAuth.getInstance().currentUser
            }
        }
    }
    companion object {
        fun newInstance() = MainFragment()
    }

    // Method to save an image to internal storage
    private fun saveImageToInternalStorage(imageBitmap: Bitmap): Uri {

        var contentResolver = this.context?.contentResolver

        // Save image to gallery
        val savedImageURL = MediaStore.Images.Media.insertImage(
            contentResolver,
            imageBitmap,
            txtImgName.text.toString(),
            "Image"
        )

        // Parse the gallery image url to uri
        return Uri.parse(savedImageURL)
    }

    private fun saveImageToFireStore(photoURI: String) {

        if(user == null){
            Toast.makeText(context, "Please press the power button to login to save.", Toast.LENGTH_LONG).show()
            return
        }

        user ?: return

        var photoInfo = PhotoToBeSaved(actClassName.text.toString(), txtImgName.text.toString(), "", "")

        val document  = firestore.collection("images").document()
        val set = document.set(photoInfo)
            set.addOnSuccessListener {
                Log.d("Firebase", "Saved.")
                photoInfo.documentId = document.id
                uploadPhoto(photoURI, photoInfo)
            }
            set.addOnCanceledListener {
                Log.d("Firebase", "Failed")
            }
    }

    private fun uploadPhoto(
        photoURI: String,
        photoInfo: PhotoToBeSaved

    ) {

        var uri = Uri.parse(photoURI)
        val imageRef = storageReference.child("images/"  + (user?.uid ?: return ) + "/" + uri.lastPathSegment)
        val uploadTask = imageRef.putFile(uri)

        uploadTask.addOnSuccessListener {

            val downloadUrl = imageRef.downloadUrl
            downloadUrl.addOnSuccessListener {

                photoInfo.imageRefURI = it.toString()

                //update firestore
                updatePhotoMetaData(photoInfo)
            }
        }
        uploadTask.addOnFailureListener{

            Log.e(TAG, it.message)
        }

    }

    private fun updatePhotoMetaData(photoInfo: PhotoToBeSaved) {
        firestore.collection("images" )
            .document(photoInfo.documentId)
            .set(photoInfo)
            .addOnSuccessListener {
                Log.d("Firestore", "Complete")
            }
            .addOnFailureListener{
                Log.e("Firestore", "Failure")
            }
    }


}
