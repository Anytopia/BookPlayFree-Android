package com.zachnr.bookplayfree.datastore.ext

import android.content.Context
import java.io.File

fun Context.dataStoreFile(fileName: String): File =
    File(applicationContext.filesDir, "datastore/$fileName")
