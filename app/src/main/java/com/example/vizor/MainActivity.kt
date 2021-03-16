package com.example.vizor

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import android.widget.ViewAnimator
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import androidx.security.crypto.EncryptedFile
import androidx.security.crypto.MasterKey
import com.example.vizor.data.model.MainViewModel
import com.example.vizor.data.model.User
import com.example.vizor.ui.DiseaseRecyclerViewAdapter
import com.example.vizor.ui.VaccineFragment
import com.google.firebase.auth.FirebaseAuth
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.nio.charset.StandardCharsets

class MainActivity : AppCompatActivity() {
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myViewModel: MainViewModel by viewModels()

        val navControl = Navigation.findNavController(this, R.id.navHostFragment)
        MainViewModel.navController = navControl
        supportActionBar!!.hide()

        if (auth.currentUser != null) {
            try {
                val mainKey = MasterKey.Builder(applicationContext)
                        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                        .build()
                val fileToRead = "loginDetails.txt"
                val encryptedFile = EncryptedFile.Builder(
                        applicationContext,
                        File(getDir("", MODE_PRIVATE), fileToRead),
                        mainKey,
                        EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
                ).build()

                val inputStream = encryptedFile.openFileInput()
                val byteArrayOutputStream = ByteArrayOutputStream()
                var nextByte: Int = inputStream.read()
                while (nextByte != -1) {
                    byteArrayOutputStream.write(nextByte)
                    nextByte = inputStream.read()
                }

                val decryptedString: String = byteArrayOutputStream.toByteArray().decodeToString()

                val ID = decryptedString.substringAfter(": ").substringBefore('\n')
                val password = decryptedString.substringAfterLast(": ")
                User.tryLogin(ID, password, false)
                navControl.navigate(R.id.action_enterFragment_to_threeFragment)

            } catch (ex: IOException) {
                Log.e("MainActivity", "Failed to read from file: $ex")
            }
        } else {
            auth.signInAnonymously()
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Log.d("MainActivity", "signInAnonymously:success")
                            val user = auth.currentUser
                        } else {
                            Log.w("MainActivity", "signInAnonymously:failure", task.exception)
                            Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                        }
                    }
        }
    }

    override fun onStop() {
        if (MainViewModel.currentUser != null) {
            val fileString = "Username: ${MainViewModel.currentUser!!.ID}\nPassword: ${MainViewModel.currentUser!!.password}"

            try {
                // Although you can define your own key generation parameter specification, it's
// recommended that you use the value specified here.
                val mainKey = MasterKey.Builder(applicationContext)
                        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                        .build()

// Create a file with this name, or replace an entire existing file
// that has the same name. Note that you cannot append to an existing file,
// and the file name cannot contain path separators.
                val fileToWrite = File(getDir("", MODE_PRIVATE), "loginDetails.txt")

                if (fileToWrite.exists()) {
                    fileToWrite.delete()
                }

                val encryptedFile = EncryptedFile.Builder(
                        applicationContext,
                        fileToWrite,
                        mainKey,
                        EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
                ).build()

                val fileContent = fileString
                        .toByteArray(StandardCharsets.UTF_8)
                encryptedFile.openFileOutput().apply {
                    write(fileContent)
                    flush()
                    close()
                }
            } catch (ex: IOException) {
                Log.e("MainActivity", "Failed to write to file: $ex")
            }
        } else {
            val fileToWrite = File(getDir("", MODE_PRIVATE), "loginDetails.txt")

            if (fileToWrite.exists()) {
                fileToWrite.delete()
            }
        }
        super.onStop()
    }
}