package com.husqvarna.home.ui.transformers

import android.widget.ListView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.husqvarna.home.ui.transformers.MarginScalePageTransformer.Companion.ILLEGAL_VIEW_PAGER_REFERENCE
import io.mockk.mockk
import org.junit.Assert
import org.junit.Test

class MarginScalePageTransformerTest {

    @Test
    fun `should require view pager successfully`() {
        val transformer = MarginScalePageTransformer(9)

        val viewPager = mockk<ViewPager2>()
        val result = transformer.requireViewPager(mockk<RecyclerView>(), viewPager)

        assert(result == viewPager)
    }

    @Test
    fun `should throw an exception when instances is not recycler view and view pager`() {
        val transformer = MarginScalePageTransformer(9)

        val exception = Assert.assertThrows(IllegalStateException::class.java) {
            transformer.requireViewPager(mockk<ListView>(), mockk<ViewPager2>())
        }

        assert(exception.message == ILLEGAL_VIEW_PAGER_REFERENCE)
    }
}