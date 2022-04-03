package com.naci.test.workmanagercodelab.workers

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.naci.test.workmanagercodelab.KEY_IMAGE_URI

class BlurWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {
    override fun doWork(): Result {
        val appContext = applicationContext
        val resourceUri = inputData.getString(KEY_IMAGE_URI)

        makeStatusNotification("It is about to start", appContext)

        sleep()

        try {
            if (resourceUri.isNullOrEmpty()) {
                throw IllegalArgumentException("Invalid input Uri")
            }

            val resolver = appContext.contentResolver
            val picture = BitmapFactory.decodeStream(
                resolver.openInputStream(Uri.parse(resourceUri))
            )

            val blurredPicture = blurBitmap(picture, appContext)
            val pictureUri = writeBitmapToFile(appContext, blurredPicture)
            makeStatusNotification("Blurred picture Uri : $pictureUri", appContext)

            val outputData = workDataOf(KEY_IMAGE_URI to pictureUri.toString())
            return Result.success(outputData)
        } catch (e: Throwable) {
            Log.e(TAG, "Error applying blur")
            return Result.failure()
        }
    }

    companion object {
        private const val TAG = "BlurWorker"
    }
}