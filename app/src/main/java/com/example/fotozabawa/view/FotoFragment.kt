package com.example.fotozabawa.view

import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.fotozabawa.*
import com.example.fotozabawa.databinding.ActivityMainBinding
import com.example.fotozabawa.databinding.FragmentFotoBinding
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors




class FotoFragment : Fragment() {

    private val TAG = "cameraX"
    private val FILE_NAME_FORMAT = "yy-MM-dd-HH-mm-ss-SSS"
    private val REQUEST_CODE_PERMISSIONS = 123
    private val REQUIRED_PERMISSIONS = arrayOf(android.Manifest.permission.CAMERA)

    private lateinit var binding: FragmentFotoBinding
    private var imageCapture: ImageCapture? = null
    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_foto, container, false)

        binding = FragmentFotoBinding.inflate(layoutInflater)

        var counter = 0
        val maxPhotos = 3
        val interval: Long = 10000 // interval set by user in milliseconds

        val intervalBP: Long = 1000 //before photo
        val intervalAP: Long = 1000 // after photo
        val intervalASP: Long = 2500 // after series of photos
        val lastInterval:Long = interval-intervalBP-intervalAP// interval between photos

        outputDirectory = getOutputDirectory()
        cameraExecutor = Executors.newSingleThreadExecutor()

        if (allPermissionGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }
        binding.btnTakePhoto.setOnClickListener {
            var handler = Handler()
            if (counter < maxPhotos) {
                try { // play the sound
                    playTune()
                    handler.postDelayed({
                        takePhoto()
                    }, intervalBP)
                    counter++
                    try {
                        handler.postDelayed({
                            playCustomTune()
                        }, intervalAP)
                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                    }
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
                if (counter < maxPhotos) {
                    handler.postDelayed({
                        binding.btnTakePhoto.performClick()
                    }, lastInterval)
                } else {
                    counter = 0
                    handler.postDelayed({
                        playCustomTune()
                    }, intervalASP)
                }
            }
        }
        binding.btnMenu.setOnClickListener {
            openMenu()
        }
        return view
    }
    private fun playTune() { // default notify ringtone
        val notification =
            RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val sound = RingtoneManager.getRingtone(context, notification)
        sound.play()
    }

    private fun playCustomTune() { // custom
        val track: MediaPlayer? = MediaPlayer.create(context, R.raw.notify1)
        track?.start()
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)


        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if(allPermissionGranted()) {
                startCamera()
            } else {
                Toast.makeText(requireContext(),
                    "Permission not granted",
                    Toast.LENGTH_SHORT).show()
                requireActivity().finish()
            }
        }
    }

    private fun getOutputDirectory(): File{
        val mediaDir = requireContext().getExternalFilesDirs(null).firstOrNull()?.let { mFile->
            File(mFile, "FOTOzabawa").apply {
                mkdirs()
            }
        }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else requireContext().filesDir
    }

    private fun openMenu() {
        Toast.makeText(requireContext(),
            "Open menu",
            Toast.LENGTH_SHORT).show()
        startActivity(Intent(requireActivity(), SettingsActivity::class.java))
    }

    private fun takePhoto() {
        val imageCapture = imageCapture ?: return
        val photoFile = File( outputDirectory,
            SimpleDateFormat(
                FILE_NAME_FORMAT,
                Locale.getDefault())
                .format(System
                    .currentTimeMillis()) + ".jpg")

        val outputOption = ImageCapture
            .OutputFileOptions
            .Builder(photoFile)
            .build()

        imageCapture.takePicture(
            outputOption, ContextCompat.getMainExecutor(requireContext()),
            object :ImageCapture.OnImageSavedCallback{
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val savedUri = Uri.fromFile(photoFile)
                    val msg = "Photo Saved"

                    Toast.makeText(requireContext(),
                        "$msg $savedUri",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onError(exception: ImageCaptureException) {
                    Log.e(TAG, "onError: ${exception.message}",exception)
                }
            }
        )
    }

    private fun startCamera() {

        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build().also {
                    mPreview ->
                mPreview.setSurfaceProvider(
                    binding.viewFinder.surfaceProvider
                )
            }

            imageCapture = ImageCapture.Builder().build()

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    requireActivity(), cameraSelector,
                    preview, imageCapture
                )
            } catch(e:Exception) {
                Log.d(TAG, "startCamera fail", e)
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun allPermissionGranted() =
        REQUIRED_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
        }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
        mediaPlayer.release()
    }

}