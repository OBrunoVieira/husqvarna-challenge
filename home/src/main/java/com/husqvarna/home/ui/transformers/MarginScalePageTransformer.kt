package com.husqvarna.home.ui.transformers

import android.view.View
import android.view.ViewParent
import androidx.annotation.Px
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs

class MarginScalePageTransformer(@Px val marginPx: Int) : ViewPager2.PageTransformer {

    companion object {
        const val ILLEGAL_VIEW_PAGER_REFERENCE =
            "Expected the page view to be managed by a ViewPager2 instance."
    }

    override fun transformPage(page: View, position: Float) {
        val viewPager = requireViewPager(page)
        val offset = marginPx * position
        if (viewPager.orientation == ViewPager2.ORIENTATION_HORIZONTAL) {
            page.translationX = offset
        } else {
            page.translationY = offset
        }

        page.scaleY = 1 - (0.25f * abs(position))
    }

    private fun requireViewPager(page: View) = run {
        val recyclerViewParent = page.parent
        val viewPagerParent = recyclerViewParent.parent
        requireViewPager(recyclerViewParent, viewPagerParent)
    }

    internal fun requireViewPager(recyclerView: ViewParent, viewPager: ViewParent): ViewPager2 =
        run {
            if (recyclerView is RecyclerView && viewPager is ViewPager2) {
                return viewPager
            }

            throw IllegalStateException(ILLEGAL_VIEW_PAGER_REFERENCE)
        }
}