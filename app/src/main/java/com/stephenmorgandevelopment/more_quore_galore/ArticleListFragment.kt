package com.stephenmorgandevelopment.more_quore_galore

import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.lifecycle.viewModelScope
import com.stephenmorgandevelopment.more_quore_galore.adapters.ArticlesAdapter
import com.stephenmorgandevelopment.more_quore_galore.databinding.ArticleListBinding
import com.stephenmorgandevelopment.more_quore_galore.viewmodels.ArticleListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ArticleListFragment : Fragment() {
    private var _binding: ArticleListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ArticleListViewModel by viewModels(
        factoryProducer = { defaultViewModelProviderFactory }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ArticleListBinding.inflate(inflater, container, false)

        val adapter = ArticlesAdapter(inflater)
        binding.articleListview.adapter = adapter
        binding.articleListview.onItemClickListener = itemClickListener()

        viewModel.articles.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                adapter.setArticlesList(it)
            }
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.refreshMenuBtn -> {
                viewModel.viewModelScope.launch {
                    viewModel.refreshList()
                }

                viewModel.articles.observe(viewLifecycleOwner) {
                    val adapter = binding.articleListview.adapter as ArticlesAdapter
                    adapter.setArticlesList(it)
                }

                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun itemClickListener() =
        AdapterView.OnItemClickListener { parent, view, position, id ->
            val fragment = ClientFragment().apply {
                arguments = makeBundleWithIdentifier(id)
            }

            val transaction = parentFragmentManager.beginTransaction()
            transaction.addToBackStack(null)
            transaction.replace(R.id.container, fragment, ClientFragment.TAG)
            transaction.commit()
        }

    private fun makeBundleWithIdentifier(identifier: Long) : Bundle {
        return Bundle().apply {
            putLong(ClientFragment.IDENTIFIER_TAG, identifier)
        }
    }

    companion object {
        val TAG = ArticleListFragment::class.java.simpleName
    }
}