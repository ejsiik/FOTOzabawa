package com.example.fotozabawa.view

import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
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
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.fotozabawa.R
import com.example.fotozabawa.databinding.FragmentFotoBinding
import com.example.fotozabawa.model.SoundManager
import com.example.fotozabawa.model.entity.PhotoEntity
import com.example.fotozabawa.network.*
import com.example.fotozabawa.viewmodel.SettingsViewModel
import kotlinx.android.synthetic.main.fragment_foto.view.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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
    private lateinit var viewModel: SettingsViewModel
    private lateinit var soundManager: SoundManager

    private lateinit var apiCall: RetroService
    private lateinit var currentPhotos: ArrayList<String>

    lateinit var fileName: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_foto, container, false)
        soundManager = SoundManager(requireContext())

        fileName = ""

        var maxPhotos: Int = 2
        var interval: Long = 5000
        var beforeSound: Int = 1
        var afterSound: Int = 2
        var endSound: Int = 3
        var timeBeforePhoto: Long = 1000

        binding = FragmentFotoBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[SettingsViewModel::class.java]

        apiCall = RetroInstance.getRetroInstance().create(RetroService::class.java)



        var counter = 0

        lifecycleScope.launch(Dispatchers.IO){
            val tmpModel = viewModel.getSettings()
            tmpModel?.let {
                maxPhotos = it.count.toInt()
                interval = it.time.toLong() * 1000
                beforeSound = it.soundBefore
                afterSound = it.soundAfter
                endSound = it.soundFinish
                timeBeforePhoto = it.timeBeforePhotoSound.toLong() * 1000
            }
        }

        outputDirectory = getOutputDirectory()
        cameraExecutor = Executors.newSingleThreadExecutor()

        currentPhotos = ArrayList(maxPhotos)

        if (allPermissionGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
            startCamera()
        }

        view.btnTakePhoto.setOnClickListener {
            val photoFile = File( outputDirectory, fileName)
            fileName = SimpleDateFormat(
                FILE_NAME_FORMAT,
                Locale.getDefault())
                .format(System
                    .currentTimeMillis()) + ".jpg"


            val handler = Handler()
            if (counter < maxPhotos) {
                try { // play the sound
                    soundManager.playBeforePictureSound(beforeSound)
                    handler.postDelayed({
                        takePhoto()
                    }, if(interval-timeBeforePhoto>0){
                        interval-timeBeforePhoto
                    } else{
                        900
                    })
                    counter++
                    try {
                        soundManager.playAfterPictureSound(afterSound)

                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                    }
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
                //sendPhotoToServer(photoFile, fileName)
                if (counter < maxPhotos) {
                        handler.postDelayed({
                            view.btnTakePhoto.performClick()
                        }, if(interval-timeBeforePhoto>0){
                            interval-timeBeforePhoto
                        } else{
                            900
                        })
                } else {
                    requestCombinePhotos("baner")
                    counter = 0
                    handler.postDelayed({
                        try {
                            soundManager.playEndSeriesSound(endSound!!)
                        } catch (e: java.lang.Exception) {
                            e.printStackTrace()
                        }
                    }, 1000)
                }
            }
        }
        view.btnMenu.setOnClickListener {
            openMenu()
        }
        return view
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
        findNavController().navigate(R.id.action_fotoFragment_to_settingsFragment)
    }

    private fun takePhoto() {
        val imageCapture = imageCapture ?: return

        val photoFile = File( outputDirectory, fileName)

        /*val outputOption = ImageCapture
            .OutputFileOptions
            .Builder(photoFile)
            .build()*/
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        /*imageCapture.takePicture(
            outputOption, ContextCompat.getMainExecutor(requireContext()),
            object :ImageCapture.OnImageSavedCallback{
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val savedUri = Uri.fromFile(photoFile)
                    val msg = "Photo Saved"

                    /*Toast.makeText(requireContext(),
                        "$msg $savedUri",
                        Toast.LENGTH_SHORT
                    ).show()*/
                }

                override fun onError(exception: ImageCaptureException) {
                    Log.e(TAG, "onError: ${exception.message}",exception)
                }
            }
        )*/
        imageCapture?.takePicture(
            outputOptions,
            cameraExecutor,
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val savedUri = output.savedUri ?: Uri.fromFile(photoFile)
                    Log.d(TAG, "Photo capture succeeded: $savedUri")
                    try{
                        sendPhotoToServer(photoFile, fileName)
                    } catch (e : java.lang.Exception){
                        Log.e(TAG, "HTTP 500!")
                    }
                }
            })
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun sendPhotoToServer(photoFile: File, fileName: String) {
        currentPhotos.add(fileName)

        Log.d("henlo", photoFile.path + "/" + fileName)

        val fileContent = org.apache.commons.io.FileUtils.readFileToByteArray(File(photoFile.path))
        val photoString = Base64.getEncoder().encodeToString(fileContent)

        val photoReq = SavePhotoReq(PhotoEntity(photoString, fileName))

        val result = apiCall.postSavePhotoOnServer(photoReq)

            result.enqueue(object : Callback<Unit> {

                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    Toast.makeText(context, "Data added to API", Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    Toast.makeText(context, "Cant add to API", Toast.LENGTH_SHORT).show()
                }
            })

    }

    private fun requestCombinePhotos(baner : String) {
        val combineReq = CombinePhotoReq(currentPhotos, baner)


        val result = apiCall.postCombinePhotos(combineReq)

            result.enqueue(object : Callback<CombinePhotoRsp> {
                override fun onResponse(
                    call: Call<CombinePhotoRsp>,
                    response: Response<CombinePhotoRsp>
                ) {
                    Toast.makeText(context, "Data added to API", Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<CombinePhotoRsp>, t: Throwable) {
                    Toast.makeText(context, "Cant add to API", Toast.LENGTH_SHORT).show()
                }
            })

    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build().also{
                it.setSurfaceProvider(view?.viewFinder?.surfaceProvider) }

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