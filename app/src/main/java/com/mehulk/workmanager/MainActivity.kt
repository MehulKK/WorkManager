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
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger


class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var oneTimeRequest: OneTimeWorkRequest
    private lateinit var mConstraints: Constraints
    private lateinit var mWorkManager: WorkManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mWorkManager = WorkManager.getInstance(this)

        val periodicWork = PeriodicWorkRequest.Builder(MyPeriodicTimeWorkRequestWork::class.java, 15, TimeUnit.MINUTES)
            .addTag(MainActivity::class.java.simpleName)
            .build()
        WorkManager.getInstance(this)
            .enqueue(periodicWork)

        button.setOnClickListener(this)
        withStorageLow.setOnClickListener(this)
        withBatteryLow.setOnClickListener(this)
        withRequiresCharging.setOnClickListener(this)
        withDeviceIdle.setOnClickListener(this)
        withRequiredNetwork.setOnClickListener(this)

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(periodicWork.id)
            .observe(this, Observer<WorkInfo> { workInfo ->
                txtStatus.append(workInfo.state.name + "\n")
            })

    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.button -> {
                oneTimeRequest = OneTimeWorkRequest.Builder(MyOneTimeWorkRequestWork::class.java).build()
            }
            R.id.withStorageLow -> {
                mConstraints = Constraints.Builder().setRequiresStorageNotLow(true).build()
                oneTimeRequest = OneTimeWorkRequest.Builder(MyOneTimeWorkRequestWork::class.java).setConstraints(mConstraints).build()
            }
            R.id.withBatteryLow -> {
                mConstraints = Constraints.Builder().setRequiresBatteryNotLow(true).build()
                oneTimeRequest = OneTimeWorkRequest.Builder(MyOneTimeWorkRequestWork::class.java).setConstraints(mConstraints).build()
            }
            R.id.withRequiresCharging -> {
                mConstraints = Constraints.Builder().setRequiresCharging(true).build()
                oneTimeRequest = OneTimeWorkRequest.Builder(MyOneTimeWorkRequestWork::class.java).setConstraints(mConstraints).build()
            }
            R.id.withDeviceIdle -> {
                mConstraints = Constraints.Builder().setRequiresDeviceIdle(true).build()
                oneTimeRequest = OneTimeWorkRequest.Builder(MyOneTimeWorkRequestWork::class.java).setConstraints(mConstraints).build()
            }
            R.id.withRequiredNetwork -> {
                mConstraints = Constraints.Builder().setRequiredNetworkType(NetworkType.NOT_REQUIRED).build()
                oneTimeRequest = OneTimeWorkRequest.Builder(MyOneTimeWorkRequestWork::class.java).setConstraints(mConstraints).build()
            }
        }

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(oneTimeRequest.id)
            .observe(this, Observer<WorkInfo> { workInfo ->
                txtStatus.append(workInfo.state.name + "\n")
            })
        mWorkManager.enqueue(oneTimeRequest)
    }

}
