package com.lumu.flashcards

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment

class FlashcardFragment(
    private var question: String,
    private var answer: String) : Fragment() {
    constructor():this("","")

    private lateinit var questionCardView: CardView
    private lateinit var answerCardView: CardView
    private lateinit var questionTextView: TeXView
    private lateinit var answerTextView: TeXView
    private var isShowingQuestion = true
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_flashcard, container, false)
        questionCardView = view.findViewById(R.id.question_cardView)
        answerCardView= view.findViewById((R.id.answer_cardView))
        questionCardView.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.launcher))
        answerCardView.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.launcher))
        // Inflate the layout for this fragment
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        questionTextView = questionCardView.findViewById(R.id.question_text_view)
        answerTextView= answerCardView.findViewById(R.id.answer_text_view)

        if (savedInstanceState != null) {
            question = savedInstanceState.getString(QUESTION, "")
            answer = savedInstanceState.getString(ANSWER, "")
        }

        questionTextView.setLaTeX("\\text{$question}", Color.WHITE)
        answerTextView.setLaTeX("\\text{$answer}", Color.WHITE)

        val scale = requireContext().resources.displayMetrics.density
        val distance = 15000
        questionCardView.cameraDistance = distance * scale
        answerCardView.cameraDistance = distance * scale
        view.setOnClickListener {
            if (isShowingQuestion) {
                flipCard()
            } else {
                flipCardBack()
            }
        }
    }
    private fun flipCard() {
        ViewCompat.animate(questionCardView)
            .rotationY(-90f)
            .setDuration(200)
            .withEndAction {
                isShowingQuestion = false
                questionCardView.visibility = View.GONE
                answerCardView.visibility = View.VISIBLE
                // Set rotationY value here
                answerCardView.rotationY = 90f
                // Start second animation
                ViewCompat.animate(answerCardView)
                    .rotationY(0f)
                    .setDuration(200)
                    .start()
            }
            .start()
    }

    private fun flipCardBack() {
        ViewCompat.animate(answerCardView)
            .rotationY(90f)
            .setDuration(200)
            .withEndAction {
                isShowingQuestion = true
                answerCardView.visibility = View.GONE
                questionCardView.visibility = View.VISIBLE
                // Set rotationY value here
                questionCardView.rotationY = -90f
                // Start second animation
                ViewCompat.animate(questionCardView)
                    .rotationY(0f)
                    .setDuration(200)
                    .start()
            }
            .start()
    }

    companion object {
        private const val QUESTION = "question"
        private const val ANSWER = "answer"
        fun newInstance(question: String, answer: String) = FlashcardFragment(question,answer)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(QUESTION, question)
        outState.putString(ANSWER, answer)
    }
}