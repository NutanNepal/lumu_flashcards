package com.lumu.flashcards

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.view.WindowCompat
import io.nano.tex.LaTeX

class MainActivity : AppCompatActivity(),
    MyCategoriesRecyclerViewAdapter.OnCategoryItemClickListener,
    MyChaptersRecyclerViewAdapter.OnChapterItemClickListener{

    private var currentTitle = "Categories"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LaTeX.instance().init(this)
        setContentView(R.layout.activity_main)

        // Hide the system window decorations
        WindowCompat.setDecorFitsSystemWindows(window, false)
        supportActionBar?.hide()

        // Set the initial title
        this.findViewById<TextView>(R.id.textView).text = currentTitle

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
        // Update the title with the selected category name
        currentTitle = category.toString()
        this@MainActivity.findViewById<TextView>(R.id.textView).text = currentTitle

        // Replace the current fragment with a new ChaptersFragment for the selected category
        val fragment = ChaptersFragment.newInstance(category)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onChapterItemClick(chapter: String) {
        // Start the FlashcardActivity for the selected chapter
        startActivity(FlashcardActivity.newIntent(this@MainActivity, chapter))
    }
}
