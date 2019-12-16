package com.mehulk.workmanager

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.work.*
import kotlinx.android.synthetic.main.activity_main.*
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.WorkManager
import androidx.work.PeriodicWorkRequest
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger


class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var oneTimeRequest: WorkRequest
    private lateinit var mConstraints: Constraints
    private lateinit var mWorkManager: WorkManager
    private lateinit var mWorkId : UUID
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mWorkManager = WorkManager.getInstance(this)

        button.setOnClickListener(this)
        withStorageLow.setOnClickListener(this)
        withBatteryLow.setOnClickListener(this)
        withRequiresCharging.setOnClickListener(this)
        withDeviceIdle.setOnClickListener(this)
        withRequiredNetwork.setOnClickListener(this)
        withPeriodic.setOnClickListener(this)

    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.button -> {
                oneTimeRequest = OneTimeWorkRequest.Builder(MyOneTimeWorkRequestWork::class.java).build()
                mWorkId = oneTimeRequest.id
            }
            R.id.withStorageLow -> {
                mConstraints = Constraints.Builder().setRequiresStorageNotLow(true).build()
                oneTimeRequest = OneTimeWorkRequest.Builder(MyOneTimeWorkRequestWork::class.java)
                    .setConstraints(mConstraints).build()
                mWorkId = oneTimeRequest.id
            }
            R.id.withBatteryLow -> {
                mConstraints = Constraints.Builder().setRequiresBatteryNotLow(true).build()
                oneTimeRequest = OneTimeWorkRequest.Builder(MyOneTimeWorkRequestWork::class.java)
                    .setConstraints(mConstraints).build()
                mWorkId = oneTimeRequest.id
            }
            R.id.withRequiresCharging -> {
                mConstraints = Constraints.Builder().setRequiresCharging(true).build()
                oneTimeRequest = OneTimeWorkRequest.Builder(MyOneTimeWorkRequestWork::class.java)
                    .setConstraints(mConstraints).build()
                mWorkId = oneTimeRequest.id
            }
            R.id.withDeviceIdle -> {
                mConstraints = Constraints.Builder().setRequiresDeviceIdle(true).build()
                oneTimeRequest = OneTimeWorkRequest.Builder(MyOneTimeWorkRequestWork::class.java)
                    .setConstraints(mConstraints).build()
                mWorkId = oneTimeRequest.id
            }
            R.id.withRequiredNetwork -> {
                mConstraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
                oneTimeRequest = OneTimeWorkRequest.Builder(MyOneTimeWorkRequestWork::class.java)
                    .setConstraints(mConstraints).build()
                mWorkId = oneTimeRequest.id
            }
            R.id.withPeriodic->{
                oneTimeRequest = PeriodicWorkRequest.Builder(MyPeriodicTimeWorkRequestWork::class.java, 15, TimeUnit.MINUTES)
                    .addTag(MainActivity::class.java.simpleName)
                    .build()
                mWorkManager.enqueue(oneTimeRequest)
                mWorkId = oneTimeRequest.id
            }
        }

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(mWorkId)
            .observe(this, Observer<WorkInfo> { workInfo ->
                txtStatus.append(workInfo.state.name + "\n")
            })
        mWorkManager.enqueue(oneTimeRequest)
    }

}
