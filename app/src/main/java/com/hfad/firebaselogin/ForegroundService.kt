package com.hfad.firebaselogin

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.IBinder
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.hfad.firebaselogin.activities.GlobalClass
import com.hfad.firebaselogin.activities.HomeActivity
import com.hfad.firebaselogin.activities.MainActivity
import com.hfad.firebaselogin.activities.StepCounterActivity
import kotlinx.android.synthetic.main.activity_step_counter.*

class ForegroundService : Service(), SensorEventListener {

    // Declare Variables
    private val CHANNEL_ID = "ForegroundService Kotlin"

    // Test Variables
    var running = false
    var sensorManager: SensorManager? = null
    var stepCount = -1
    var savedSteps = 0
    var stepGoal = 50

    companion object {

        fun startService(context: Context, message: String) {
            val startIntent = Intent(context, ForegroundService::class.java)
            startIntent.putExtra("inputExtra", message)
            ContextCompat.startForegroundService(context, startIntent)
        }

        fun stopService(context: Context) {
            val stopIntent = Intent(context, ForegroundService::class.java)
            context.stopService(stopIntent)
        }
    }

    // Do heavy work on a background thread
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        // Sensor Setup
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        running = true
        var stepSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        if (stepSensor == null){
            Toast.makeText(this,"No Step Counter Detected", Toast.LENGTH_SHORT).show()
        }
        else{
            sensorManager?.registerListener(this,stepSensor, SensorManager.SENSOR_STATUS_ACCURACY_HIGH)
        }

        val input = intent?.getStringExtra("inputExtra")
        createNotificationChannel()
        val notificationIntent = Intent(this, StepCounterActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0, notificationIntent, 0
        )

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Current Walk:     " + GlobalClass.Companion.globalCurrentSteps + " Steps.")
            //.setContentText("" + GlobalClass.Companion.globalCurrentSteps)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentIntent(pendingIntent)
            .build()
        startForeground(1, notification)
        //stopSelf();

        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(CHANNEL_ID, "Foreground Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT)
            val manager = getSystemService(NotificationManager::class.java)
            manager!!.createNotificationChannel(serviceChannel)
        }
    }

    // OnAccuracyChanged -- No functionality
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    // OnSensorChanged detects a new step each time
    override fun onSensorChanged(event: SensorEvent?) {

        if (running){
            if (event != null) {

                // Create notifications
                val notificationIntent = Intent(this, HomeActivity::class.java)

                val pendingIntent = PendingIntent.getActivity(
                    this,
                    0, notificationIntent, 0
                )

                // Set up notification
                val notification = NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("Current Walk:     " + GlobalClass.Companion.globalCurrentSteps + " Steps.")
                    //.setContentText("" + GlobalClass.Companion.globalCurrentSteps)
                    .setSmallIcon(R.drawable.ic_notification)
                    .setContentIntent(pendingIntent)
                    .build()
                startForeground(1, notification)
                //stopSelf();

                // increment the Foreground
                GlobalClass.Companion.globalCurrentSteps++

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        running = false;
    }
}