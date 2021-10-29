package com.example.chucknorris.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chucknorris.R
import com.example.chucknorris.adapter.JokesAdapter
import com.example.chucknorris.ui.JokesViewModel
import com.example.chucknorris.ui.MainActivity
import com.example.chucknorris.util.Resource
import kotlinx.android.synthetic.main.fragment_jokes.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class JokesFragment : Fragment(R.layout.fragment_jokes) {

    lateinit var viewModel: JokesViewModel
    lateinit var jokesAdapter: JokesAdapter

    private val TAG = "JOKES_FRAGMENT"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        setupRecyclerView()
        val text = savedInstanceState?.getString("etCount", "")?:""
        etCount.setText(text)


        var job: Job? = null
        button.setOnClickListener {
            job?.cancel()
            job = MainScope().launch {
                if(etCount.text.toString().isNotEmpty()) {
                    savedInstanceState?.putString("etCount", etCount.text.toString())
                    val cnt = Integer.parseInt(etCount.text.toString())
                    if (cnt > 20 || cnt == 0){
                        makeToastError()
                    }else{
                        viewModel.getJokes(cnt)
                    }
                }else{
                    makeToastError()
                }
            }
        }

        jokesAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("joke", it)
            }
            findNavController().navigate(
                R.id.action_jokesFragment_to_jokeFragment,
                bundle
            )
        }

        viewModel.jokes.observe(viewLifecycleOwner, Observer { response ->
            when(response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { jokesResponse ->
                        jokesAdapter.differ.submitList(jokesResponse.value)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Log.e(TAG, "an error occurred: $message")
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })
    }

    private fun makeToastError() {
        Toast.makeText(context, "Count must be [0..20]", Toast.LENGTH_SHORT).show()
    }

    private fun hideProgressBar() {
        paginationProgressBar.visibility = View.GONE
    }

    private fun showProgressBar() {
        paginationProgressBar.visibility = View.VISIBLE
    }

    private fun setupRecyclerView() {
        jokesAdapter = JokesAdapter()
        rvJokes.apply {
            adapter = jokesAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}