package com.lumu.flashcards

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import io.nano.tex.LaTeX

class MainActivity : AppCompatActivity(),
    MyCategoriesRecyclerViewAdapter.OnCategoryItemClickListener,
    MyChaptersRecyclerViewAdapter.OnChapterItemClickListener{

    private var currentTitle = "Categories"
    // Keep a reference to the current fragment
    var currentNavState: String = "home"

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        savedInstanceState.run {
            putString("CURRENT_NAV_STATE", currentNavState)
        }
        super.onSaveInstanceState(savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LaTeX.instance().init(this)
        setContentView(R.layout.activity_main)

        // Hide the system window decorations
        WindowCompat.setDecorFitsSystemWindows(window, false)
        supportActionBar?.hide()

        // Set the initial title
        this.findViewById<TextView>(R.id.textView).text = currentTitle

        if (savedInstanceState != null) {
            currentNavState = savedInstanceState.getString("CURRENT_NAV_STATE", currentNavState) ?: "home"
        }

        if (currentNavState == "home"){
            replaceFragmentContainer(R.id.fragmentContainerView,CategoriesFragment())
        }
        else{
            onCategoryItemClick(Category.valueOf(currentNavState))
        }
    }

    override fun onCategoryItemClick(category: Category) {
        // Update the title with the selected category name
        currentTitle = category.toString()
        this@MainActivity.findViewById<TextView>(R.id.textView).text = currentTitle
        // Replace the current fragment with a new ChaptersFragment for the selected category
        val fragment = ChaptersFragment.newInstance(category)
        replaceFragmentContainer(R.id.fragmentContainerView, fragment)
        currentNavState = category.toString()
    }

    override fun onChapterItemClick(chapter: String) {
        // Start the FlashcardActivity for the selected chapter
        startActivity(FlashcardActivity.newIntent(this@MainActivity, chapter))
    }

    private fun replaceFragmentContainer(oldFragment: Int, newFragment: Fragment){
        // Get the FragmentManager
        val fragmentManager = supportFragmentManager

        // Begin a FragmentTransaction
        val fragmentTransaction = fragmentManager.beginTransaction()

        // Replace the default fragment container with the list fragment
        fragmentTransaction.replace(oldFragment, newFragment)

        // Commit the transaction
        fragmentTransaction.commit()
    }
}
