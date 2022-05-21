package com.iyubinest.robolectricsample

import android.app.Application
import org.robolectric.Shadows
import org.robolectric.shadows.ShadowApplication

class ShadowTestApp : Application() {
  override fun onCreate() {
    val shadowApp: ShadowApplication = Shadows.shadowOf(this)
    shadowApp.grantPermissions("android.permission.INTERNET")
  }
}
