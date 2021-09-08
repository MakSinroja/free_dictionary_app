package developers.guru.core.extensions

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

/**
 * Created by Maulik V. Sinroja on 2021-09-08 19:42.
 */
fun AppCompatActivity.setupTransparentActivity() {
    window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
}

fun AppCompatActivity.setupFullScreenActivity() {
    @Suppress("DEPRECATION")
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        window?.let { w ->
            w.insetsController?.let { controller ->
                controller.hide(WindowInsets.Type.statusBars())
            }
        }
    } else {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }
}

fun AppCompatActivity.setupStatusBarColor(attributeColorId: Int) {
    this.window.statusBarColor = getColorFromAttribute(attributeColorId)
}

fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}

fun AppCompatActivity.addFragment(fragment: Fragment, frameId: Int, bundle: Bundle? = null) {
    supportFragmentManager.inTransaction {
        bundle?.let {
            fragment.arguments = it
        }
        add(frameId, fragment)
    }
}

fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int, bundle: Bundle? = null) {
    supportFragmentManager.inTransaction {
        bundle?.let {
            fragment.arguments = it
        }
        add(frameId, fragment)
    }
}

fun AppCompatActivity.removeFragment(fragment: Fragment) {
    supportFragmentManager.inTransaction {
        remove(fragment)
    }
}