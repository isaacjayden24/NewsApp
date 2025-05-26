package com.project.newsapp.fragments

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.project.newsapp.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch



class SplashFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val newsImageView = view.findViewById<ImageView>(R.id.newsImageView)

        // Create and start rotation animation
        ObjectAnimator.ofFloat(newsImageView, View.ROTATION, 0f, 360f).apply {
            duration = 1000
            repeatCount = ObjectAnimator.INFINITE
            start()
        }



        // Use lifecycleScope for coroutines
        viewLifecycleOwner.lifecycleScope.launch {
            delay(3000)
            findNavController().navigate(R.id.action_splashFragment_to_baseNewsFragment2)
        }
    }


}