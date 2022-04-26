package com.example.ntr_test_app.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.ntr_test_app.R
import com.example.ntr_test_app.databinding.FramentMainBinding
import com.example.ntr_test_app.extension.dp
import com.example.ntr_test_app.models.ui.StateItem
import com.example.ntr_test_app.ui.recycler.StateAdapter
import com.example.ntr_test_app.ui.recycler.decorations.FeedHorizontalDividerItemDecoration
import com.example.ntr_test_app.ui.recycler.decorations.GroupVerticalItemDecoration
import com.example.ntr_test_app.ui.recycler.holders.OpenDetailsCallback
import com.example.ntr_test_app.ui.recycler.holders.StateItemCallback
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import reactivecircus.flowbinding.android.widget.afterTextChanges
import reactivecircus.flowbinding.android.widget.textChanges

class FragmentMain : Fragment() {


    private var _binding: FramentMainBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FramentMainBinding.inflate(inflater, container, false)

        Log.d("M_APP", "on createView bundle: ${savedInstanceState == null}")
        return binding.root
    }

    val stateViewModel: StateViewModel by viewModel()
    val stateDetailsViewModel: StateDetailsViewModel by sharedViewModel()

    private val stateItemCallback = object : StateItemCallback {
        override fun onDoubleClicked(stateItem: StateItem) {
            stateViewModel.userIntent.trySend(StateViewModel.Wish.OnDoubleClicked(stateItem.state))
        }

        override fun onSingleClicked(stateItem: StateItem) {
            stateDetailsViewModel.userIntent.trySend(
                StateDetailsViewModel.Wish.OnSingleClicked(
                    stateItem
                )
            )
        }
    }

    private val openDetailsCallback = object : OpenDetailsCallback {
        override fun onClick() {
            val destination = FragmentMainDirections.openDetails()
            findNavController().navigate(destination)
        }

    }

    private val stateAdapter by lazy {
        StateAdapter(stateItemCallback = stateItemCallback)
    }

    private val filteredStateAdapter by lazy {
        StateAdapter(stateItemCallback = stateItemCallback)
    }

    private val detailsAdapter by lazy {
        StateAdapter(
            openDetailsCallback = openDetailsCallback
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(savedInstanceState == null){
            stateViewModel.userIntent.trySend(StateViewModel.Wish.FetchNumbers)
        }
        Log.d("M_APP", "onCreate bundle: ${savedInstanceState == null}")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpUI()
        observeUI()
        Log.d("M_APP", "on viewCreated bundle: ${savedInstanceState == null}")
    }

    private fun observeUI() {
        stateViewModel.state.onEach {
            stateAdapter.setItems(it.items)
            filteredStateAdapter.setItems(it.getFilteredItems())
        }.launchIn(lifecycleScope)

        stateDetailsViewModel.state.onEach {
            detailsAdapter.setItems(it.stateItems)
        }.launchIn(lifecycleScope)
    }

    private fun setUpUI() {
        with(binding) {
            stateList.apply {
                adapter = stateAdapter
                addItemDecoration(FeedHorizontalDividerItemDecoration(50))
                addItemDecoration(
                    GroupVerticalItemDecoration(
                        R.layout.item_state,
                        0,
                        5.dp.toInt()
                    )
                )
                addItemDecoration(
                    DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
                )
            }

            stateFiltered.apply {
                adapter = filteredStateAdapter
                addItemDecoration(FeedHorizontalDividerItemDecoration(50))
                addItemDecoration(
                    GroupVerticalItemDecoration(
                        R.layout.item_state,
                        0,
                        5.dp.toInt()
                    )
                )
                addItemDecoration(
                    DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
                )
            }

            stateDetails.apply {
                adapter = detailsAdapter

                addItemDecoration(
                    GroupVerticalItemDecoration(
                        R.layout.item_county,
                        20.dp.toInt(),
                        10.dp.toInt()
                    )
                )
            }

            filterEditText.textChanges()
                .skipInitialValue()
                .flatMapLatest {
                    delay(500)
                    return@flatMapLatest flowOf(it.toString())
                }.onEach {
                    stateViewModel.userIntent.trySend(
                        StateViewModel.Wish.Filter(
                            it.lowercase()
                        ))
                }.launchIn(lifecycleScope)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}