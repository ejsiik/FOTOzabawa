package com.example.fotozabawa.viewmodel

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.util.Log
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.lifecycle.AndroidViewModel
import com.example.fotozabawa.model.entity.PhotoEntity
import com.example.fotozabawa.network.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FotoViewModel(application: Application) : AndroidViewModel(application) {
    private val TAG = "cameraX"

    private var outputDirectory: File
    private var cameraExecutor: ExecutorService

    private var apiCall: RetroService

    init{
        this.outputDirectory = getOutputDirectory()
        this.cameraExecutor = Executors.newSingleThreadExecutor()

        this.apiCall = RetroInstance.getRetroInstance().create(RetroService::class.java)
    }
    fun takePhoto(fileName: String, imageCapture: ImageCapture) {

        val photoFile = File( outputDirectory, fileName)

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture?.takePicture(
            outputOptions,
            cameraExecutor,
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val savedUri = output.savedUri ?: Uri.fromFile(photoFile)
                    val fileName2 = savedUri.path?.substring((savedUri.path?.length?.minus(25)!!))
                    Log.d(TAG, "Photo capture succeeded: $savedUri")
                    try{
                        savedUri.path?.let {
                            if (fileName2 != null) {
                                rotateAndSendPhoto(it, fileName2)
                            }
                        }
                    } catch (e : java.lang.Exception){
                        Log.e(TAG, "HTTP 500!")
                    }
                }
            })
    }

    private fun rotateAndSendPhoto(photoPath: String, fileName: String) {
        val bitmap = BitmapFactory.decodeFile(photoPath)
        val matrix = Matrix().apply { setRotate(90f) }
        val rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        val rotatedFile = File(outputDirectory, "rotated_$fileName")
        val fos = FileOutputStream(rotatedFile)
        rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
        fos.close()
        sendPhotoToServer(rotatedFile.path, fileName)
    }

    fun sendPhotoToServer(uri : String, fileName: String) {

        val fileContent = org.apache.commons.io.FileUtils.readFileToByteArray(File(uri))

        val photoString = Base64.getEncoder().encodeToString(fileContent)

        val photoReq = SavePhotoReq(PhotoEntity(photoString, fileName))

        val result = apiCall.postSavePhotoOnServer(photoReq)

        result.enqueue(object : Callback<Unit> {

            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                Log.d(TAG, "Succes saved!")
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                Log.e(TAG, "Save went wrong!")
            }
        })
    }

    fun requestCombinePhotos(baner : Int, filter: Int, currentPhotos: ArrayList<String>) {
        val combineReq = CombinePhotoReq(currentPhotos, baner, filter)

        val result = apiCall.postCombinePhotos(combineReq)

        result.enqueue(object : Callback<CombinePhotoRsp> {
            override fun onResponse(
                call: Call<CombinePhotoRsp>,
                response: Response<CombinePhotoRsp>
            ) {
                Log.d(TAG, "Succes combined!")
            }
            override fun onFailure(call: Call<CombinePhotoRsp>, t: Throwable) {
                Log.e(TAG, "Combine went wrong!")
            }
        })
    }

    fun getOutputDirectory(): File{
        val mediaDir =  getApplication<Application>().getExternalFilesDirs(null).firstOrNull()?.let { mFile->
            File(mFile, "FOTOzabawa").apply {
                mkdirs()
            }
        }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else getApplication<Application>().filesDir
    }

    fun cameraExecutorShutDown(){
        cameraExecutor.shutdown()
    }
}