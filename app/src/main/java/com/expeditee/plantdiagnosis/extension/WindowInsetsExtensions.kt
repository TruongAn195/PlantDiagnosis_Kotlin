package com.expeditee.plantdiagnosis.extension

import androidx.core.view.WindowInsetsCompat

fun WindowInsetsCompat.statusBars(): Int {
    return WindowInsetsCompat.Type.statusBars()
}

fun WindowInsetsCompat.navigationBars(): Int {
    return WindowInsetsCompat.Type.navigationBars()
}

fun WindowInsetsCompat.systemBars(): Int {
    return WindowInsetsCompat.Type.systemBars()
}
