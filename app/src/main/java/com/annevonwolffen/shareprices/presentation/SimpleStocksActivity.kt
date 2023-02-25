package com.annevonwolffen.shareprices.presentation

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import com.annevonwolffen.shareprices.R
import com.annevonwolffen.shareprices.presentation.viewmodel.SimpleStocksViewModel
import com.annevonwolffen.shareprices.utils.ImageManager
import kotlinx.coroutines.launch
import javax.inject.Inject

class SimpleStocksActivity : AppCompatActivity() {

    private lateinit var stocksAdapter: StocksAdapter
    private lateinit var recyclerView: RecyclerView

    @Inject
    lateinit var imageManager: ImageManager

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProvider.Factory

    private val viewModel: SimpleStocksViewModel by lazy {
        ViewModelProvider(this, viewModelProviderFactory)[SimpleStocksViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        initViews()

        lifecycleScope.launch {
            // The block passed to repeatOnLifecycle is executed when the lifecycle
            // is at least STARTED and is cancelled when the lifecycle is STOPPED.
            // It automatically restarts the block when the lifecycle is STARTED again.
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Safely collect from locationFlow when the lifecycle is STARTED
                // and stops collection when the lifecycle is STOPPED
                viewModel.stocksFlow.collect {
                    stocksAdapter.submitList(it.toMutableList())
                }
            }
        }
    }

    private fun initViews() {
        recyclerView = findViewById(R.id.stocks_recycler_view)
        stocksAdapter = StocksAdapter({}, {}, imageManager)
        recyclerView.adapter = stocksAdapter
    }
}