package com.azamovme.MoviePlayerAkylai.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.animation.*
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.azamovme.MoviePlayerAkylai.R
import com.azamovme.MoviePlayerAkylai.app.App
import com.azamovme.MoviePlayerAkylai.data.response.MovieInfo
import com.azamovme.MoviePlayerAkylai.ui.activity.MainActivity
import com.azamovme.MoviePlayerAkylai.utils.Download.defaultDownload
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.lagradost.nicehttp.addGenericDns
import okhttp3.OkHttpClient
import java.io.*
import java.util.UUID
import kotlin.collections.ArrayList
import kotlin.reflect.KFunction


//@SuppressLint("RestrictedApi")
//fun applyDynamicColors(
//    useMaterialYou: Boolean,
//    context: Context,
//    useOLED: Boolean,
//    bitmap: Bitmap? = null,
//    useCustom: Int? = null
//): Boolean {
//    val builder = DynamicColorsOptions.Builder()
//    var needMaterial = true
//
//    // Set content-based source if a bitmap is provided
//    if (bitmap != null) {
//        builder.setContentBasedSource(bitmap)
//        needMaterial = false
//    } else if (useCustom != null) {
//        builder.setContentBasedSource(bitmap!!)
//        needMaterial = false
//    }
//
//    if (useOLED) {
//    }
//    if (needMaterial && !useMaterialYou) return true
//
//    // Build the options
//    val options = builder.build()
//
//    // Apply the dynamic colors to the activity
//    val activity = context as Activity
//    DynamicColors.applyToActivityIfAvailable(activity, options)
//
//    if (useOLED) {
//        val options2 = DynamicColorsOptions.Builder()
//            .build()
//        DynamicColors.applyToActivityIfAvailable(activity, options2)
//    }
//
//    return false
//}

fun makeAlert(context: Context, appCompatActivity: AppCompatActivity) {
    AlertDialog.Builder(context)
        .setTitle(NativeHelper.getDialogTitle())
        .setMessage(NativeHelper.getDialogMessage())
        .setPositiveButton("Exit") { _, _ -> appCompatActivity.finishAffinity() }
        .setCancelable(false)
        .show()
}

@RequiresApi(Build.VERSION_CODES.O)
fun initActivity(a: Activity) {
    val window = a.window
    AppCompatDelegate.setDefaultNightMode(
        AppCompatDelegate.MODE_NIGHT_YES
    )
    WindowCompat.setDecorFitsSystemWindows(window, false)

}

fun download(activity: Activity, episode: MovieInfo, link: String, animeTitle: String) {
    Toast.makeText(activity, "Downloading...", Toast.LENGTH_SHORT).show()
    defaultDownload(activity, episode, link, animeTitle)
}


fun startMainActivity(activity: Activity, bundle: Bundle? = null) {
    activity.finishAffinity()
    activity.startActivity(
        Intent(
            activity,
            MainActivity::class.java
        ).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            if (bundle != null) putExtras(bundle)
        }
    )
}


@Suppress("UNCHECKED_CAST")
fun <T> loadData(fileName: String, context: Context? = null, toast: Boolean = true): T? {
    val a = context ?: App.instance
    try {
        if (a?.fileList() != null)
            if (fileName in a.fileList()) {
                val fileIS: FileInputStream = a.openFileInput(fileName)
                val objIS = ObjectInputStream(fileIS)
                val data = objIS.readObject() as T
                objIS.close()
                fileIS.close()
                return data
            }
    } catch (e: Exception) {
        if (toast) snackString("Error loading data $fileName")
        e.printStackTrace()
    }
    return null
}

@Suppress("DEPRECATION")
fun Activity.hideSystemBars() {
    window.decorView.systemUiVisibility = (
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            )
}

@Suppress("DEPRECATION")
fun Fragment.hideSystemBars() {
    requireActivity().window.decorView.systemUiVisibility = (
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            )
}

@Suppress("DEPRECATION")
fun Activity.hideStatusBar() {
    window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
}

fun ImageView.loadImage(url: String?, size: Int = 0) {
    if (!url.isNullOrEmpty()) {
        loadImage(FileUrl(url), size)
    }
}

fun ImageView.loadImage(file: FileUrl?, size: Int = 0) {
    if (file?.url?.isNotEmpty() == true) {
        tryWith {
            val glideUrl = GlideUrl(file.url) { file.headers }
            Glide.with(this.context).load(glideUrl)
                .transition(DrawableTransitionOptions.withCrossFade()).override(size).into(this)
        }
    }
}

var loaded: Boolean = false
var loadedFav: Boolean = false
fun openLinkInBrowser(link: String?, activity: Activity) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
    activity.startActivity(intent)
}

object Refresh {
    fun all() {
        for (i in activity) {
            activity[i.key]!!.postValue(true)
        }
    }

    fun all2() {
        for (i in activity2) {
            activity2[i.key]!!.postValue(true)
        }
    }

    val activity = mutableMapOf<Int, MutableLiveData<Boolean>>()
    val activity2 = mutableMapOf<Int, MutableLiveData<Boolean>>()


}


lateinit var isToolbarDisabledGoListener: (Boolean) -> Unit

fun disableToolbar(listener: (Boolean) -> Unit) {
    isToolbarDisabledGoListener = listener
}

lateinit var changeToolbarColorListener: (Boolean) -> Unit

fun changeToolbarColor(listener: (Boolean) -> Unit) {
    changeToolbarColorListener = listener
}

data class FileUrl(
    val url: String,
    val headers: Map<String, String> = mapOf()
) : Serializable {
    companion object {
        operator fun get(url: String?, headers: Map<String, String> = mapOf()): FileUrl? {
            return FileUrl(url ?: return null, headers)
        }
    }
}

//Credits to leg
data class Lazier<T>(
    val lClass: KFunction<T>,
    val name: String
) {
    val get = lazy { lClass.call() }
}

fun <T> lazyList(vararg objects: Pair<String, KFunction<T>>): List<Lazier<T>> {
    return objects.map {
        Lazier(it.second, it.first)
    }
}

fun <T> T.printIt(pre: String = ""): T {
    println("$pre$this")
    return this
}


fun OkHttpClient.Builder.addGoogleDns() = (
        addGenericDns(
            "https://dns.google/dns-query",
            listOf(
                "8.8.4.4",
                "8.8.8.8"
            )
        ))

fun OkHttpClient.Builder.addCloudFlareDns() = (
        addGenericDns(
            "https://cloudflare-dns.com/dns-query",
            listOf(
                "1.1.1.1",
                "1.0.0.1",
                "2606:4700:4700::1111",
                "2606:4700:4700::1001"
            )
        ))

fun OkHttpClient.Builder.addAdGuardDns() = (
        addGenericDns(
            "https://dns.adguard.com/dns-query",
            listOf(
                // "Non-filtering"
                "94.140.14.140",
                "94.140.14.141",
            )
        ))

fun <T> tryWith(post: Boolean = false, snackbar: Boolean = true, call: () -> T): T? {
    return try {
        call.invoke()
    } catch (e: Throwable) {
        null
    }
}

fun saveData(fileName: String, data: Any?, context: Context? = null) {
    tryWith {
        val a = context ?: App.instance
        val fos: FileOutputStream = a.openFileOutput(fileName, Context.MODE_PRIVATE)
        val os = ObjectOutputStream(fos)
        os.writeObject(data)
        os.close()
        fos.close()
    }
}


fun setAnimation(
    context: Context,
    viewToAnimate: View,
    duration: Long = 150,
    list: FloatArray = floatArrayOf(0.0f, 1.0f, 0.0f, 1.0f),
    pivot: Pair<Float, Float> = 0.5f to 0.5f
) {
    val anim = ScaleAnimation(
        list[0],
        list[1],
        list[2],
        list[3],
        Animation.RELATIVE_TO_SELF,
        pivot.first,
        Animation.RELATIVE_TO_SELF,
        pivot.second
    )
    anim.duration = (duration * 1f).toLong()
    anim.setInterpolator(context, R.anim.over_shoot)
    viewToAnimate.startAnimation(anim)
}


fun setSlideIn() = AnimationSet(false).apply {
    var animation: Animation = AlphaAnimation(0.0f, 1.0f)
    animation.duration = (500 * 1f).toLong()
    animation.interpolator = AccelerateDecelerateInterpolator()
    addAnimation(animation)

    animation = TranslateAnimation(
        Animation.RELATIVE_TO_SELF, 1.0f,
        Animation.RELATIVE_TO_SELF, 0f,
        Animation.RELATIVE_TO_SELF, 0.0f,
        Animation.RELATIVE_TO_SELF, 0f
    )

    animation.duration = (750 * 1f).toLong()
    animation.interpolator = OvershootInterpolator(1.1f)
    addAnimation(animation)
}

fun setSlideUp() = AnimationSet(false).apply {
    var animation: Animation = AlphaAnimation(0.0f, 1.0f)
    animation.duration = (500 * 1f).toLong()
    animation.interpolator = AccelerateDecelerateInterpolator()
    addAnimation(animation)

    animation = TranslateAnimation(
        Animation.RELATIVE_TO_SELF, 0.0f,
        Animation.RELATIVE_TO_SELF, 0f,
        Animation.RELATIVE_TO_SELF, 1.0f,
        Animation.RELATIVE_TO_SELF, 0f
    )

    animation.duration = (750 * 1f).toLong()
    animation.interpolator = OvershootInterpolator(1.1f)
    addAnimation(animation)
}

fun loadTvFilters(): ArrayList<Pair<String, Int>> {
    val filterlist = ArrayList<Pair<String, Int>>()
    filterlist.add(Pair("Barcha", 1))
    filterlist.add(Pair("Uzbek", 25))
    filterlist.add(Pair("Bolalar", 20))
    filterlist.add(Pair("O`yin-Kulgi", 21))
    filterlist.add(Pair("Yangiliklar", 22))
    filterlist.add(Pair("Kino", 23))
    filterlist.add(Pair("Sport", 24))
    filterlist.add(Pair("Hayvonot", 26))
    return filterlist;
}

//This is android ID
fun getUniqueID() = UUID.randomUUID().toString()
