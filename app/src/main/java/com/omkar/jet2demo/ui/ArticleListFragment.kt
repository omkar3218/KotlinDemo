package com.omkar.jet2demo.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.omkar.jet2demo.R
import com.omkar.jet2demo.data.model.Article
import com.omkar.jet2demo.databinding.ArticalListFragmentBinding
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class ArticleListFragment : Fragment() {

    private lateinit var binding: ArticalListFragmentBinding

    private var articleList = mutableListOf<Article>()

    private var activateEndScrolling = false


    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var viewModel: ArticleListViewModel

    companion object {
        fun newInstance() = ArticleListFragment()
    }

    private var page: Int = 1
    lateinit var layoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate<ArticalListFragmentBinding>(
                inflater,
                R.layout.artical_list_fragment,
                container,
                false
            )
        layoutManager = LinearLayoutManager(context)

        binding.articleRecyclerView.layoutManager = layoutManager
        binding.articleRecyclerView.adapter = ArticleListAdapter(articleList)

        val endlessRecyclerOnScrollListener: EndlessRecyclerOnScrollListener =
            object : EndlessRecyclerOnScrollListener(layoutManager) {
                override fun onLoadMore(current_page: Int) {
                    if (activateEndScrolling) {
                        loadNextPage(current_page)
                    }
                }
            }

        binding.articleRecyclerView.addOnScrollListener(endlessRecyclerOnScrollListener)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.fetchArticleList(page)
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    private fun observeViewModel() {
        viewModel.articleLiveData.observe(this, Observer {
            activateEndScrolling = it.isNotEmpty() && it.size >= 10
            articleList.addAll(it)
            binding.articleRecyclerView.adapter?.notifyDataSetChanged()
        })
    }


    private fun loadNextPage(currentPage: Int) {
        viewModel.fetchArticleList(currentPage)
    }


}
