package edu.uc.clemenks.keepingupwithschool.ui.main

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Binder
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import edu.uc.clemenks.keepingupwithschool.R
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    private val CAMERA_PERMISSION_REQUEST_CODE = 1999
    private val CAMERA_REQUEST_CODE = 1998

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) : View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
       btnTakePic.setOnClickListener{
           prepTakePhoto()
       }
    }
    //See if we have permission or not
    private fun prepTakePhoto() {
        if(ContextCompat.checkSelfPermission(context!!, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
            takePhoto()
        } else {
            val permissionRequest = arrayOf(Manifest.permission.CAMERA)
            requestPermissions(permissionRequest, CAMERA_PERMISSION_REQUEST_CODE)
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            CAMERA_PERMISSION_REQUEST_CODE -> {
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //permission granted, lets do stuff
                    takePhoto()
                } else {
                    Toast.makeText(context, "Unable to take photo without permission", Toast.LENGTH_LONG).show()
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
        }
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}
