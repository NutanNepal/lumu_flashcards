package com.lumu.flashcards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment

class FlashcardFragment(
    private val question: String,
    private val answer: String) : Fragment() {
    constructor():this("","")

    private lateinit var questioncardView: CardView
    private lateinit var answercardView: CardView
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
        questioncardView = view.findViewById(R.id.question_cardView)
        answercardView=view.findViewById((R.id.answer_cardView))
        questionTextView = questioncardView.findViewById(R.id.question_text_view)
        answerTextView= answercardView.findViewById(R.id.answer_text_view)
        questionTextView.setLaTeX("\\text{$question}")
        answerTextView.setLaTeX("\\text{$answer}")

        val scale = requireContext().resources.displayMetrics.density
        val distance = 15000
        questioncardView.cameraDistance = distance * scale
        answercardView.cameraDistance = distance * scale
        view.setOnClickListener {
            if (isShowingQuestion) {
                flipCard()
            } else {
                flipCardBack()
            }
        }
    }
    private fun flipCard() {
        ViewCompat.animate(questioncardView)
            .rotationY(-90f)
            .setDuration(200)
            .withEndAction {
                isShowingQuestion = false
                questioncardView.visibility = View.GONE
                answercardView.visibility = View.VISIBLE
                // Set rotationY value here
                answercardView.rotationY = 90f
                // Start second animation
                ViewCompat.animate(answercardView)
                    .rotationY(0f)
                    .setDuration(200)
                    .start()
            }
            .start()
    }

    private fun flipCardBack() {
        ViewCompat.animate(answercardView)
            .rotationY(90f)
            .setDuration(200)
            .withEndAction {
                isShowingQuestion = true
                answercardView.visibility = View.GONE
                questioncardView.visibility = View.VISIBLE
                // Set rotationY value here
                questioncardView.rotationY = -90f
                // Start second animation
                ViewCompat.animate(questioncardView)
                    .rotationY(0f)
                    .setDuration(200)
                    .start()
            }
            .start()
    }

    companion object {
        fun newInstance(question: String, answer: String) = FlashcardFragment(question,answer)
    }
}