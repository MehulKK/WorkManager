package com.mehulk.workmanager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.location.Location
import android.util.Log
import androidx.annotation.NonNull
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.android.gms.location.*
import java.text.ParseException
import java.util.concurrent.atomic.AtomicInteger


class MyPeriodicTimeWorkRequestWork(private var context: Context, @NonNull workerParams: WorkerParameters) : Worker(context, workerParams) {

    private val TAG = "MyWorker"

    private lateinit var mLocation:Location
    /**
     * Provides access to the Fused Location Provider API.
     */
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    /**
     * Callback for changes in location.
     */
    private lateinit var mLocationCallback: LocationCallback
    internal lateinit var ignored: ParseException

    private val UPDATE_INTERVAL_IN_MILLISECONDS: Long = 10000

    /**
     * The fastest rate for active location updates. Updates will never be more frequent
     * than this value.
     */
    private val FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2

    override fun doWork(): Result {
        displayNotification("My Worker", "Hey I finished my work")
        return Result.success()
    }

    private fun displayNotification(title:String, task:String) {
        Log.d(TAG, "doWork: Done")
        /*mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        mLocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
            }
        }
        val mLocationRequest = LocationRequest()
        mLocationRequest.interval = UPDATE_INTERVAL_IN_MILLISECONDS
        mLocationRequest.fastestInterval = FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        try {
            mFusedLocationClient
                .lastLocation
                .addOnCompleteListener { task ->
                    if (task.isSuccessful && task.result != null) {
                        mLocation = task.result!!
                        Log.d(TAG, "Location : $mLocation")

                        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
                        {
                            val channel = NotificationChannel("simplifiedcoding", "simplifiedcoding", NotificationManager.IMPORTANCE_DEFAULT)
                            notificationManager.createNotificationChannel(channel)
                        }
                        val notification = NotificationCompat.Builder(applicationContext, "simplifiedcoding")
                            .setContentTitle(title)
                            .setContentText("Lat ${mLocation.latitude}   Long :${mLocation.longitude}")
                            .setSmallIcon(R.mipmap.ic_launcher)
                        notificationManager.notify(getID(), notification.build())


                        mFusedLocationClient.removeLocationUpdates(mLocationCallback)
                    } else {
                        Log.w(TAG, "Failed to get location.")
                    }
                }
        }
        catch (e:Exception){
            Log.w(TAG, "Exception : $e")
        }*/

        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
        {
            val channel = NotificationChannel("simplifiedcoding", "simplifiedcoding", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }
        val id = getID()
        val notification = NotificationCompat.Builder(applicationContext, "simplifiedcoding")
            .setContentTitle(title)
            .setContentText("Update $task  id : $id")
            .setSmallIcon(R.mipmap.ic_launcher)
        notificationManager.notify(id, notification.build())

    }
}