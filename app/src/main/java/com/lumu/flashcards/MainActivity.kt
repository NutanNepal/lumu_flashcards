package com.lumu.flashcards

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import io.nano.tex.LaTeX

class MainActivity : AppCompatActivity(),
    MyCategoriesRecyclerViewAdapter.OnCategoryItemClickListener,
    MyChaptersRecyclerViewAdapter.OnChapterItemClickListener{

    private var currentTitle = "Categories"

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        savedInstanceState.run {
            putString("CURRENT_NAV_STATE", currentTitle)
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

        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true /* enabled by default */) {
            override fun handleOnBackPressed() {
                if (currentTitle == "Categories") {
                    // If already on the CategoriesFragment, call the default back behavior (exit the app)
                    finish()
                } else {
                    // Otherwise, go back to the CategoriesFragment and update the title
                    replaceFragmentContainer(R.id.fragmentContainerView, CategoriesFragment())
                    currentTitle = "Categories"
                    findViewById<TextView>(R.id.textView).text = currentTitle
                }
            }
        }
        // Add the callback to the activity's back stack
        onBackPressedDispatcher.addCallback(this, callback)

        if (savedInstanceState != null) {
            currentTitle = savedInstanceState.getString("CURRENT_NAV_STATE", currentTitle) ?: "Categories"
        }

        if (currentTitle == "Categories"){
            replaceFragmentContainer(R.id.fragmentContainerView,CategoriesFragment())
        }
        else{
            onCategoryItemClick(Category.valueOf(currentTitle))
        }
    }
//    override fun onBackPressed() {
//        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)
//        if (currentFragment is ChaptersFragment) {
//            currentTitle = "Categories"
//            this.findViewById<TextView>(R.id.textView).text = currentTitle
//            replaceFragmentContainer(R.id.fragmentContainerView, CategoriesFragment())
//        } else {
//            super.onBackPressed()
//        }
//    }

    override fun onCategoryItemClick(category: Category) {
        // Update the title with the selected category name
        currentTitle = category.toString()
        this@MainActivity.findViewById<TextView>(R.id.textView).text = currentTitle
        // Replace the current fragment with a new ChaptersFragment for the selected category
        val fragment = ChaptersFragment.newInstance(category)
        replaceFragmentContainer(R.id.fragmentContainerView, fragment)
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

        // Add the current fragment to the back stack
        fragmentTransaction.addToBackStack(null)

        // Commit the transaction
        fragmentTransaction.commit()
    }
}
