package com.lumu.flashcards

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Xml
import android.widget.TextView
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import org.xmlpull.v1.XmlPullParser

class FlashcardActivity(
    private var chapter: String
) : FragmentActivity() {

    // Secondary constructor that initializes the chapter with an empty string
    constructor() : this("")

    // Views
    private lateinit var viewPager: ViewPager2
    private lateinit var pagerAdapter: FlashcardPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // Set the content view for this activity
        setContentView(R.layout.activity_flashcard)

        // Set the chapter title from the extra provided by the intent
        chapter = intent?.getStringExtra(CHAPTER).toString()
        this.findViewById<TextView>(R.id.flashcard_title).text = chapter

        // Get the ViewPager element from the layout
        viewPager = findViewById(R.id.viewPager)

        // Create a new adapter for the ViewPager
        pagerAdapter = FlashcardPagerAdapter(supportFragmentManager)

        // Set the adapter for the ViewPager
        viewPager.adapter = pagerAdapter

        // Set the page transformer to the zoom out transformer
        viewPager.setPageTransformer(ZoomOutPageTransformer())
    }

    inner class FlashcardPagerAdapter(fragmentManager: FragmentManager) :
        FragmentStateAdapter(fragmentManager, lifecycle) {

        // Flashcard list
        private val flashcards = parseFlashcards(chapter)

        // Return the number of flashcards
        override fun getItemCount(): Int = flashcards.size

        // Create a new instance of the FlashcardFragment for each page
        override fun createFragment(position: Int): Fragment {
            val flashcard = flashcards[position]
            return FlashcardFragment.newInstance(flashcard.question, flashcard.answer)
        }
    }

    // Parse the flashcards from the XML file
    private fun parseFlashcards(chapterList: String): MutableList<Flashcard> {
        val fileName = "$chapterList.xml"
        val inputStream = fileName.let { assets.open(it) }

        // Create a new instance of the XmlPullParser interface
        val parser: XmlPullParser = Xml.newPullParser()

        // Set the input stream for the parser to the opened file
        parser.setInput(inputStream, null)

        // Parse the XML file
        return parseXml(parser)
    }

    // Parse the XML file and return a list of flashcards
    private fun parseXml(parser: XmlPullParser): MutableList<Flashcard> {
        val flashcards = mutableListOf<Flashcard>()
        var eventType = parser.eventType
        var currentFlashcard: Flashcard? = null

        while (eventType != XmlPullParser.END_DOCUMENT) {
            when (eventType) {
                XmlPullParser.START_TAG -> {
                    when (parser.name) {
                        "flashcard" -> {
                            // Create a new Flashcard object when a <flashcard> tag is encountered
                            currentFlashcard = Flashcard("", "")
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
        private const val CHAPTER = "chapter"

        /**
         * Creates a new Intent for the FlashcardActivity
         *
         * @param context The context of the calling activity
         * @param chapterlist The name of the chapter to load
         * @return The Intent for starting the FlashcardActivity
         */
        fun newIntent(context: Context, chapterlist: String): Intent {
            return Intent(context, FlashcardActivity::class.java).apply {
                putExtra(CHAPTER, chapterlist)
            }
        }
    }
}

/**
* Represents a flashcard with a question and answer.
* @property question The question on the flashcard
* @property answer The answer on the flashcard
 */
class Flashcard(
    var question: String,
    var answer: String
)
