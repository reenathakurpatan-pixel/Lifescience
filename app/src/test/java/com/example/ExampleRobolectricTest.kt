package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.CsirSyllabusCatalog
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read app name and verify all 13 CSIR units load`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("BioPrep CSIR", appName)
    assertEquals(13, CsirSyllabusCatalog.allUnits.size)
    assertTrue(CsirSyllabusCatalog.allTopics.isNotEmpty())
  }
}
