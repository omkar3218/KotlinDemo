package com.omkar.jet2demo.ui.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.omkar.jet2demo.R
import com.omkar.jet2demo.data.model.Article
import com.omkar.jet2demo.databinding.ArticalListFragmentBinding
import com.omkar.jet2demo.ui.custom.EndlessRecyclerOnScrollListener
import com.omkar.jet2demo.ui.viewmodel.ArticleListViewModel
import com.omkar.jet2demo.ui.viewmodel.ViewModelFactory
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


class ArticleListFragment : Fragment() {

    private lateinit var binding: ArticalListFragmentBinding

    private var articleList = mutableListOf<Article>()

    private var activateEndScrolling = false


    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var viewModel: ArticleListViewModel

    companion object {
        fun newInstance() = ArticleListFragment()
    }

    private var page: Int = 1
    lateinit var layoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        binding.articleRecyclerView.adapter =
            ArticleListAdapter(articleList)
        binding.articleRecyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
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
        viewModel = viewModelFactory.create(ArticleListViewModel::class.java)
        observeViewModel()
        viewModel.fetchArticleList(page)
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    private fun observeViewModel() {
        viewModel.articleLiveData.observe(viewLifecycleOwner, Observer {
            activateEndScrolling = it.isNotEmpty() && it.size >= 10
            articleList.addAll(it)
            if (articleList.isEmpty()) {
                binding.noDataTextView.visibility = View.VISIBLE
            } else
                binding.noDataTextView.visibility = View.GONE

            binding.articleRecyclerView.adapter?.notifyDataSetChanged()
        })

        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else
                binding.progressBar.visibility = View.GONE

        })
    }


    private fun loadNextPage(currentPage: Int) {
        viewModel.fetchArticleList(currentPage)
    }


}
