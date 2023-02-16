package com.example.fotozabawa.view

import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.MediaPlayer
import android.net.Uri
import android.os.*
import android.util.Base64.decode
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
import com.example.fotozabawa.model.entity.Model
import com.example.fotozabawa.model.entity.PhotoEntity
import com.example.fotozabawa.network.*
import com.example.fotozabawa.viewmodel.FotoViewModel
import com.example.fotozabawa.viewmodel.SettingsViewModel
import kotlinx.android.synthetic.main.fragment_foto.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.collections.ArrayList

class FotoFragment : Fragment() {

    private val TAG = "cameraX"
    private val FILE_NAME_FORMAT = "yy-MM-dd-HH-mm-ss-SSS"
    private val REQUEST_CODE_PERMISSIONS = 123
    private val REQUIRED_PERMISSIONS = arrayOf(android.Manifest.permission.CAMERA)

    private lateinit var binding: FragmentFotoBinding
    private var imageCapture: ImageCapture? = null

    private lateinit var mediaPlayer: MediaPlayer

    private lateinit var viewModel: SettingsViewModel
    private lateinit var fotoViewModel: FotoViewModel

    private lateinit var soundManager: SoundManager

    private lateinit var currentPhotos: ArrayList<String>

    lateinit var fileName: String

    private var isDone = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_foto, container, false)
        soundManager = SoundManager(requireContext())


        var maxPhotos: Int = 2
        var interval: Long = 5000
        var beforeSound: Int = 1
        var afterSound: Int = 2
        var endSound: Int = 3
        var timeBeforePhoto: Long = 1000
        var lastClickTime: Long = 0
        var baner: Int = 1
        var filter: Int = 1

        binding = FragmentFotoBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[SettingsViewModel::class.java]
        fotoViewModel = ViewModelProvider(this)[FotoViewModel::class.java]


        var counter = 0

        lifecycleScope.launch(Dispatchers.IO){
            val tmpModel = viewModel.getSettings()
            if (tmpModel == null) {
                val model = Model (0, interval.toString(), maxPhotos.toString(), beforeSound, afterSound, endSound,
                        timeBeforePhoto.toString(), baner, filter)
                viewModel.addSettings(model)
            } else {
                tmpModel?.let {
                    maxPhotos = it.count.toInt()
                    interval = it.time.toLong() * 1000
                    beforeSound = it.soundBefore
                    afterSound = it.soundAfter
                    endSound = it.soundFinish
                    timeBeforePhoto = it.timeBeforePhotoSound.toLong() * 1000
                    baner = it.baner
                    filter = it.filter
                }
            }
        }




        currentPhotos = ArrayList(maxPhotos)

        fileName = ""

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
            // mis-clicking prevention, using threshold of 800 ms
            if (SystemClock.elapsedRealtime() - lastClickTime < 800){
                return@setOnClickListener;
            }

            lastClickTime = SystemClock.elapsedRealtime();

            if (counter < maxPhotos) {
                Log.w(TAG, "Nadpisuje name: " + fileName)
                fileName = SimpleDateFormat(
                    FILE_NAME_FORMAT,
                    Locale.getDefault())
                    .format(System
                        .currentTimeMillis()) + ".jpg"
                currentPhotos.add(fileName)


                try {
                    soundManager.playBeforePictureSound(beforeSound)
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
                val scope = CoroutineScope(Dispatchers.Main)
                scope.launch {
                    delay(if(interval-timeBeforePhoto>0){
                        timeBeforePhoto
                    } else{
                        2000
                    })
                    try {
                        imageCapture?.let { it1 -> fotoViewModel.takePhoto(fileName, it1) }
                        counter++
                        soundManager.playAfterPictureSound(afterSound)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    if (counter < maxPhotos) {
                        val scope2 = CoroutineScope(Dispatchers.Main)
                        scope2.launch {
                            delay(if(interval-timeBeforePhoto>0){
                                interval-timeBeforePhoto
                            } else{
                                3000
                            })
                            view.btnTakePhoto.performClick()
                        }
                    } else {
                        counter = 0
                        isDone = true
                        val scope3 = CoroutineScope(Dispatchers.Main)
                        scope3.launch {
                            delay(3500)
                            try {
                                soundManager.playEndSeriesSound(endSound)
                            } catch (e: java.lang.Exception) {
                                e.printStackTrace()
                            }
                            if(isDone){
                                val handler = Handler()
                                handler.postDelayed({
                                    fotoViewModel.requestCombinePhotos(baner, filter, currentPhotos)
                                }, 5000)
                                currentPhotos.clear()
                            }
                        }
                    }
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

    private fun openMenu() {
        findNavController().navigate(R.id.action_fotoFragment_to_settingsFragment)
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
        fotoViewModel.cameraExecutorShutDown()
        /*mediaPlayer.stop();
        mediaPlayer.reset()
        mediaPlayer.release()*/
        soundManager.releaseMediaPlayer()
    }

}

