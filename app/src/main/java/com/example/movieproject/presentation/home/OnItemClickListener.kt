package com.example.movieproject.presentation.home

import android.app.Activity
import android.content.Context
import com.example.movieproject.R
import com.google.android.material.bottomnavigation.BottomNavigationView


interface OnItemClickListener {
    fun setOnItemClickListener(listener: (Int) -> Unit)
}