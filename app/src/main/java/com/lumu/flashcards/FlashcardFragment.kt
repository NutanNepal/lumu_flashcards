package com.lumu.flashcards

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat

class FlashcardFragment(
    private val question: String,
    private val answer: String) : Fragment() {
    constructor():this("","")

    private lateinit var cardView: CardView
    private lateinit var questionTextView: TeXView
    private lateinit var answerTextView: TeXView
    private var isShowingQuestion = true
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_flashcard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cardView= view.findViewById(R.id.flashcard_view)
        questionTextView = cardView.findViewById(R.id.question_text_view)
        answerTextView= cardView.findViewById(R.id.answer_text_view)
        questionTextView.setLaTeX("\\text{$question}")
        answerTextView.setLaTeX("\\text{$answer}")
        cardView.setOnClickListener {
            if (isShowingQuestion) {
                flipCard()
            } else {
                flipCardBack()
            }
        }
    }

    private fun flipCard() {
        ViewCompat.animate(cardView)
            .rotationY(-90f)
            .setDuration(250)
            .withEndAction {
                isShowingQuestion = false
                questionTextView.visibility = View.GONE
                answerTextView.visibility = View.VISIBLE
                cardView.rotationY = 90f
                ViewCompat.animate(cardView)
                    .rotationY(0f)
                    .setDuration(250)
                    .start()
            }
            .start()
    }

    private fun flipCardBack() {
        ViewCompat.animate(cardView)
            .rotationY(90f)
            .setDuration(250)
            .withEndAction {
                isShowingQuestion = true
                answerTextView.visibility = View.GONE
                questionTextView.visibility = View.VISIBLE
                cardView.rotationY = -90f
                ViewCompat.animate(cardView)
                    .rotationY(0f)
                    .setDuration(250)
                    .start()
            }
            .start()
    }

    companion object {
        fun newInstance(question: String, answer: String) = FlashcardFragment(question,answer)
    }
}