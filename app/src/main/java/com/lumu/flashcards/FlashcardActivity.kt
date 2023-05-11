package com.lumu.flashcards

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Xml
import android.view.MenuItem
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import org.xmlpull.v1.XmlPullParser

class FlashcardActivity(
    private var chapterlist: String
) : FragmentActivity() {
    constructor(): this(""){}
    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: FlashcardPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        chapterlist= intent.getStringExtra(CHAPTERLIST).toString()
        setContentView(R.layout.activity_flashcard)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        //supportActionBar?.hide()

        // Get the ViewPager element from the layout
        viewPager = findViewById(R.id.viewPager)
        // Create a new adapter for the ViewPager
        val adapter = FlashcardPagerAdapter(supportFragmentManager)
        // Set the adapter for the ViewPager
        viewPager.adapter = adapter
        viewPager.setPageTransformer(ZoomOutPageTransformer())

        // Add a listener for page changes
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

            override fun onPageSelected(position: Int) {
                // Update the title of the activity to the current flashcard
                //supportActionBar?.title = adapter.getPageTitle(position)

                // Check if the user has reached the last flashcard
                //if (position == adapter.itemCount - 1) {
                    // Show a dialog or message to let them know they have reached the end
                    // ...
                //}
            }
            /*
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageScrollStateChanged(state: Int) {
            }*/
        })
    }

    inner class FlashcardPagerAdapter(fragmentManager: FragmentManager) :
        FragmentStateAdapter(fragmentManager, lifecycle) {
        private val flashcards = parse(chapterlist)
        override fun getItemCount(): Int = flashcards.size

        override fun createFragment(position: Int): Fragment {
            // Create a new instance of the FlashcardFragment for each page
            val flashcard = flashcards[position]
            return FlashcardFragment.newInstance(flashcard.question, flashcard.answer)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle back button clicks
        if (item.itemId == android.R.id.home) {
            //onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun parse(chapterlist: String): MutableList<Flashcards>{
        val fileName = "$chapterlist.xml"
        val inputStream = fileName.let { assets.open(it) }
        // Create a new instance of the XmlPullParser interface
        val parser: XmlPullParser = Xml.newPullParser()
        // Set the input stream for the parser to the opened file
        parser.setInput(inputStream, null)
        return parseXml(parser)
    }

    private fun parseXml(parser: XmlPullParser): MutableList<Flashcards> {
        val flashcards = mutableListOf<Flashcards>()
        var eventType = parser.eventType
        var currentFlashcard: Flashcards? = null

        while (eventType != XmlPullParser.END_DOCUMENT) {
            when (eventType) {
                XmlPullParser.START_TAG -> {
                    when (parser.name) {
                        "flashcard" -> {
                            // Create a new Flashcard object when a <flashcard> tag is encountered
                            currentFlashcard = Flashcards("", "")
                        }
                        "question" -> {
                            // Set the question for the current Flashcard object when a <question> tag is encountered
                            currentFlashcard?.question = parser.nextText()
                        }
                        "answer" -> {
                            // Set the answer for the current Flashcard object when an <answer> tag is encountered
                            currentFlashcard?.answer = parser.nextText()
                        }
                    }
                }
                XmlPullParser.END_TAG -> {
                    if (parser.name == "flashcard") {
                        // Add the current Flashcard object to the list when a </flashcard> tag is encountered
                        currentFlashcard?.let { flashcards.add(it) }
                        currentFlashcard = null
                    }
                }
            }
            eventType = parser.next()
        }
        return flashcards
    }

    companion object {
        private const val CHAPTERLIST = "chapter"
        fun newIntent(context: Context, chapterlist: String): Intent{
            return Intent(context, FlashcardActivity::class.java).apply{
                putExtra(CHAPTERLIST, chapterlist)
            }
        }
    }
}

class Flashcards(
    var question: String,
    var answer: String
)