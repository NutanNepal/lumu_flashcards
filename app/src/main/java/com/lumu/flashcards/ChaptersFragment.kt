package com.lumu.flashcards

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class ChaptersFragment(
    private var columnCount: Int = 1,
    private val category: Category
) : Fragment() {

    // onCreate() is called when the fragment is created. It sets up the fragment's initial state.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get the arguments passed to the fragment, if any.
        // In this case, we're getting the number of columns to use in the RecyclerView.
        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    // onCreateView() is called when the fragment should create its UI.
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the fragment_chapters_list layout.
        val view = inflater.inflate(R.layout.fragment_chapters_list, container, false)

        // If the inflated view is a RecyclerView, set up the layout manager and adapter.
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    // If there's only one column, use a vertical LinearLayoutManager.
                    columnCount <= 1 -> LinearLayoutManager(context)
                    // Otherwise, use a GridLayoutManager with the specified number of columns.
                    else -> GridLayoutManager(context, columnCount)
                }
                // Set the adapter for the RecyclerView.
                adapter = MyChaptersRecyclerViewAdapter(
                    category,
                    // The activity hosting this fragment must implement the OnChapterItemClickListener interface.
                    activity as MyChaptersRecyclerViewAdapter.OnChapterItemClickListener
                )
            }
        }

        // Return the inflated view.
        return view
    }

    // This companion object contains constant values and a factory method for creating instances of this fragment.
    companion object {
        // The name of the argument used to pass the number of columns to the fragment.
        const val ARG_COLUMN_COUNT = "column-count"

        // Factory method for creating instances of this fragment.
        @JvmStatic
        fun newInstance(columnCount: Int, category: Category) =
            ChaptersFragment(columnCount, category).apply {
                // Create a new Bundle object to hold the arguments.
                arguments = Bundle().apply {
                    // Put the column count value in the arguments.
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}