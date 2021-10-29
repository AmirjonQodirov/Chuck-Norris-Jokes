package com.example.chucknorris.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chucknorris.R
import com.example.chucknorris.adapter.JokesAdapter
import com.example.chucknorris.ui.JokesViewModel
import com.example.chucknorris.ui.MainActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_favorite_jokes.*

class SavedJokesFragment : Fragment(R.layout.fragment_favorite_jokes) {

    lateinit var viewModel: JokesViewModel
    lateinit var jokesAdapter: JokesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        setupRecyclerView()

        jokesAdapter.setOnItemClickListener {
            //todo copy to buffer
        }

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val value = jokesAdapter.differ.currentList[position]
                viewModel.deleteJoke(value)
                Snackbar.make(view, "Successfully deleted joke", Snackbar.LENGTH_LONG).apply {
                    setAction("Undo") {
                        viewModel.saveJoke(value)
                    }
                    show()
                }
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(rvSavedJokes)
        }

        viewModel.getSavedJokes().observe(viewLifecycleOwner, Observer { value ->
            jokesAdapter.differ.submitList(value)
        })
    }

    private fun setupRecyclerView() {
        jokesAdapter = JokesAdapter()
        rvSavedJokes.apply {
            adapter = jokesAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}