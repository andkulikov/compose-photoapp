package compose.photoapp

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.compose.Composable
import androidx.compose.onDispose
import androidx.compose.remember
import androidx.compose.staticAmbientOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.ui.core.Alignment
import androidx.ui.foundation.Box
import androidx.ui.unit.dp
import androidx.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds

@Composable
fun AdBanner() {
    val adProvider = AdProviderAmbient.current
    val adView = remember(adProvider) { adProvider.getAdView() }
    Box(gravity = Alignment.Center, padding = 16.dp) {
        AndroidView(adView)
        onDispose {
            // workaround for a bug, already fixed in upcoming dev14:
            (adView.parent as ViewGroup).removeView(adView)
        }
    }
}

class AdProvider(private val context: Context, private val lifecycle: Lifecycle) {

    // In the real life we will have a pool of views instead of only one
    private val preloaded: AdView

    init {
        MobileAds.initialize(context)
        // we start preloading ad straight away so it will be ready once we need it
        preloaded = createAdView()
    }

    private fun createAdView() = AdView(context).apply {
        adSize = AdSize.MEDIUM_RECTANGLE
        adUnitId = "ca-app-pub-3940256099942544/6300978111"
        loadAd(AdRequest.Builder().build())
        layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                when (event) {
                    Lifecycle.Event.ON_RESUME -> resume()
                    Lifecycle.Event.ON_PAUSE -> pause()
                    Lifecycle.Event.ON_DESTROY -> destroy()
                }
            }
        })
    }

    fun getAdView(): View = temporaryWrap(
        if (preloaded.parent == null) {
            preloaded
        } else {
            createAdView()
        }
    )
}

val AdProviderAmbient = staticAmbientOf<AdProvider>()

// workaround for a bug, to be removed when the fix is released
private fun temporaryWrap(view: View): View {
    val frameLayout = object : FrameLayout(view.context) {
        override fun onDescendantInvalidated(child: View, target: View) {
            if (isLaidOut) {
                super.onDescendantInvalidated(child, target)
            } else {
                post {
                    if (parent != null) {
                        super.onDescendantInvalidated(child, target)
                    }
                }
            }
        }

        override fun onDetachedFromWindow() {
            super.onDetachedFromWindow()
            removeAllViews()
        }
    }
    frameLayout.layoutParams = view.layoutParams
    frameLayout.addView(view, view.layoutParams)
    return frameLayout
}