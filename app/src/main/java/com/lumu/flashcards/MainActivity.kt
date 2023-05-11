package com.lumu.flashcards

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.WindowCompat

class MainActivity : AppCompatActivity(),
    MyCategoriesRecyclerViewAdapter.OnCategoryItemClickListener,
    MyChaptersRecyclerViewAdapter.OnChapterItemClickListener{
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

    override fun onCategoryItemClick(category: Category) {
        val fragment = ChaptersFragment.newInstance(1, category)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, fragment)
            .addToBackStack(null)
            .commit()
    }
    override fun onChapterItemClick(chapter: String) {
        startActivity(FlashcardActivity.newIntent(this@MainActivity, chapter))
    }
}