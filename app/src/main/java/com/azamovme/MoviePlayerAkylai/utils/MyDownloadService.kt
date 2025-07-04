package com.azamovme.MoviePlayerAkylai.utils

import android.app.Notification
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.offline.Download
import androidx.media3.exoplayer.offline.DownloadManager
import androidx.media3.exoplayer.offline.DownloadNotificationHelper
import androidx.media3.exoplayer.offline.DownloadService
import androidx.media3.exoplayer.scheduler.PlatformScheduler
import androidx.media3.exoplayer.scheduler.Scheduler
import com.azamovme.MoviePlayerAkylai.R
import com.azamovme.MoviePlayerAkylai.utils.Helper.downloadManager

@UnstableApi
class MyDownloadService : DownloadService(1, 1, "download_service", R.string.app_name, 0) {
    companion object {
        private const val JOB_ID = 1
        private const val FOREGROUND_NOTIFICATION_ID = 1
    }

    override fun getDownloadManager(): DownloadManager =downloadManager(this)


    override fun getScheduler(): Scheduler = PlatformScheduler(this, JOB_ID)
    override fun getForegroundNotification(
        downloads: MutableList<Download>, notMetRequirements: Int
    ): Notification {

        return DownloadNotificationHelper(this, "download_service").buildProgressNotification(
            this,
            R.drawable.logo,
            null,
            null,
            downloads,
            notMetRequirements
        )
    }


}