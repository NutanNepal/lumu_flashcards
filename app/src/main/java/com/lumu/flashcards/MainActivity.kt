package com.lumu.flashcards

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.WindowCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        supportActionBar?.hide()
        supportActionBar?.title = "Categories"

        // Get the FragmentManager
        val fragmentManager = supportFragmentManager
        // Begin a FragmentTransaction
        val fragmentTransaction = fragmentManager.beginTransaction()
        // Replace the default fragment container with the list fragment
        fragmentTransaction.replace(R.id.fragmentContainerView, CategoriesFragment())
        // Commit the transaction
        fragmentTransaction.commit()

    }
}