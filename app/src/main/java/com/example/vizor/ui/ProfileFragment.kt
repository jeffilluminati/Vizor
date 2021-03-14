package com.example.vizor.ui


import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.example.vizor.R
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import android.content.res.ColorStateList
import android.text.SpannableStringBuilder
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.vizor.data.model.MainViewModel
import kotlin.jvm.Throws

class ProfileFragment : Fragment(){
    private lateinit var imageView: ImageView
    lateinit var currentPhotoPath: String
    private lateinit var navController: NavController


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val model: MainViewModel by activityViewModels()

        currentPhotoPath = model.getUri()






    val a = inflater.inflate(R.layout.fragment_profile, container, false)
        return a
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        val model: MainViewModel by activityViewModels()

        imageView = requireView().findViewById(R.id.profileImageView)
        currentPhotoPath = model.getUri()
        if (currentPhotoPath != ""){
            imageView.setImageURI(currentPhotoPath.toUri()
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val model: MainViewModel by activityViewModels()

        requireView().findViewById<TextView>(R.id.userIDTextView).text = MainViewModel.currentUser!!.ID


        imageView = requireView().findViewById(R.id.profileImageView)
        currentPhotoPath = model.getUri()
        if (currentPhotoPath != ""){
            imageView.setImageURI(currentPhotoPath.toUri()
            )
        }
        Log.i("run", "run")
        imageView.setOnClickListener() {
            createImageFile()
            askFilePermissions()
            askCameraPermissions()

            model.setUri(currentPhotoPath)

        }



        var galleryBtn = requireView().findViewById<Button>(R.id.galleryBtn)
        galleryBtn.setOnClickListener(){
            var gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(gallery, 998)

            imageView.setImageURI(currentPhotoPath.toUri())

            model.setUri(currentPhotoPath)
        }
        navController = Navigation.findNavController(view)

        requireView().findViewById<Button>(R.id.infoBtn2).setOnClickListener{
            navController.navigate(R.id.action_profileFragment_to_infoFragment2)
        }

    }
fun askCameraPermissions() {
    if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.CAMERA), 101)
    } else {
        dispatchTakePictureIntent()
    }
}
fun askFilePermissions() {
    if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 105)
    }
    if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 106)
    }

}

override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    if (requestCode == 101){
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
            dispatchTakePictureIntent()
        }
        else{
            Toast.makeText(requireContext(), "Camera is needed to submit image", Toast.LENGTH_LONG).show()
        }
    }
}


override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    super.onActivityResult(requestCode, resultCode, data)
    if (requestCode == REQUEST_IMAGE_CAPTURE){
        if (resultCode == Activity.RESULT_OK){
            var f = File(currentPhotoPath)
            imageView.setImageURI(Uri.fromFile(f))

//                val myBitmap = BitmapFactory.decodeFile(currentPhotoPath)
//                        imageView.setImageBitmap(myBitmap)

            var mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
            var contentUri = Uri.fromFile(f)
            mediaScanIntent.setData(contentUri)
            requireActivity().sendBroadcast(mediaScanIntent)
        }
    }
    if (requestCode == 998){
        if (resultCode == Activity.RESULT_OK){
            var uriContentUri =  data!!.data!!
            val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            val storageDir: File = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val fileName = storageDir.path + "JPEG_" + timeStamp + "." + getFileExt(uriContentUri)
            imageView.setImageURI(uriContentUri)
            currentPhotoPath = this!!.getPath(requireContext(), uriContentUri)
//                val myBitmap = BitmapFactory.decodeFile(currentPhotoPath)
//                        imageView.setImageBitmap(myBitmap)

        }

    }
}
    @Throws(IOException::class)
    private fun createImageFile(): File {
    // Create an image file name
    Log.i("12", "100")
    val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
//        val storageDir: File =  applicationContext!!.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
    val storageDir: File = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
    Log.i("53", storageDir.absolutePath)
    return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
    ).apply {
        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = absolutePath
        Log.i("2", currentPhotoPath)
    }
}

private var REQUEST_IMAGE_CAPTURE = 102

private fun dispatchTakePictureIntent() {
    Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
        // Ensure that there's a camera activity to handle the intent
        takePictureIntent.resolveActivity(requireActivity().packageManager)?.also {
            // Create the File where the photo should go
            Log.i("100", "72")
        }
        val photoFile: File = File(currentPhotoPath)
        Log.i("4",photoFile.absolutePath)
//                // Continue only if the File was successfully created
        photoFile.also {
            val photoURI: Uri = FileProvider.getUriForFile(
                    requireContext(),
                    "com.example.vizor.fileprovider",
                    it
            )
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
        }
        Log.i("23", "100")
        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
    }
}

private fun getFileExt(content : Uri): String{
    var mime = MimeTypeMap.getSingleton()
    return mime.getExtensionFromMimeType(requireActivity().contentResolver.getType(content))!!
}

fun getPath(context: Context, uri: Uri): String {
    var result: String? = null
    val proj = arrayOf(MediaStore.Images.Media.DATA)
    val cursor: Cursor = context.getContentResolver().query(uri, proj, null, null, null)!!
    if (cursor != null) {
        if (cursor.moveToFirst()) {
            val column_index: Int = cursor.getColumnIndexOrThrow(proj[0])
            result = cursor.getString(column_index)
        }
        cursor.close()
    }
    if (result == null) {
        result = "Not found"
    }
    return result
}
}