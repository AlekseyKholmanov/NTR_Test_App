package com.example.ntr_test_app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.ntr_test_app.R
import com.example.ntr_test_app.databinding.FragmentDetailsBinding
import com.example.ntr_test_app.extension.dp
import com.example.ntr_test_app.ui.recycler.StateAdapter
import com.example.ntr_test_app.ui.recycler.decorations.FeedHorizontalDividerItemDecoration
import com.example.ntr_test_app.ui.recycler.decorations.GroupVerticalItemDecoration
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class FragmentDetails : Fragment() {


    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    val stateDetailsViewModel: StateDetailsViewModel by sharedViewModel()

    private val detailsAdapter by lazy {
        StateAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUI()
        observeUI()
    }

    private fun setUpUI() {
        with(binding) {
            stateDetails.apply {
                adapter = detailsAdapter
                addItemDecoration(
                    GroupVerticalItemDecoration(
                        R.layout.item_loader,
                        160.dp.toInt(),
                        40.dp.toInt()
                    )
                )
                addItemDecoration(
                    GroupVerticalItemDecoration(
                        R.layout.item_county,
                        20.dp.toInt(),
                        10.dp.toInt()
                    )
                )
                addItemDecoration(FeedHorizontalDividerItemDecoration(10.dp.toInt()))
                addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))

            }
        }
    }

    private fun observeUI() {
        stateDetailsViewModel.state.onEach {
            detailsAdapter.setItems(it.getFilteredItem())
        }.launchIn(lifecycleScope)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}