package com.project.newsapp.fragments

import androidx.fragment.app.Fragment


/**
 * A simple [Fragment] subclass.
 * Use the [ArticleFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ArticleFragment : Fragment() {


   /* val args: ArticleFragmentArgs by navArgs()




    private val newsViewModel: NewsViewModel by viewModels {
        NewsViewModelFactory((requireActivity().application as NewsApp).newsRepository)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_article, container, false)

    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val webView=view.findViewById<WebView>(R.id.webView)

        val article=args.article
        webView.apply {
            webViewClient= WebViewClient()
            loadUrl(article.url)

        }



    }*/
}

