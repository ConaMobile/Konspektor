package com.conamobile.konspektor.ui.history

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.conamobile.konspektor.R
import com.conamobile.konspektor.core.utils.UtilObjects
import com.conamobile.konspektor.core.utils.viewBinding
import com.conamobile.konspektor.databinding.FragmentHistoryBinding
import com.conamobile.konspektor.ui.BaseFragment
import com.conamobile.konspektor.ui.history.adapter.HistoryAdapter
import com.conamobile.konspektor.ui.history.model.HistoryModel
import com.conamobile.konspektor.ui.history.viewmodels.HistoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment : BaseFragment(R.layout.fragment_history) {
    private val binding by viewBinding { FragmentHistoryBinding.bind(it) }
    private var historyList = ArrayList<HistoryModel>()
    private val historyAdapter by lazy { HistoryAdapter(historyList) }
    private val viewModel: HistoryViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapterManager()
        loadRoomList()
        clickItem()
        removeItem()
        backBtnManager()
        deleteBtnManager()
    }

    private fun clickItem() {
        historyAdapter.itemCLick = {
            val navController = findNavController()
            navController.previousBackStackEntry?.savedStateHandle?.set("key0", it.text)
            UtilObjects.lastFragment = true
            navController.popBackStack()
        }
    }

    private fun deleteBtnManager() {
        binding.deleteBtn.click {
            viewModel.deleteAllNotes()
            checkListSize()
        }
    }

    private fun backBtnManager() {
        binding.backBtn.click {
            findNavController().popBackStack()
        }
    }

    private fun removeItem() {
        historyAdapter.deleteCLick = {
            viewModel.deleteNote(it)
            checkListSize()
        }
    }

    private fun adapterManager() {
        binding.recyclerView.apply {
            adapter = historyAdapter

        }
    }

    private fun loadRoomList() {
        viewModel.getAllNote().observe(viewLifecycleOwner) { list ->
            historyAdapter.submitList(list)
            historyList.addAll(list)
            binding.recyclerView.scrollToPosition(list.size - 1)
            checkListSize()
        }
    }

    private fun checkListSize() {
        viewModel.getAllNote().observe(viewLifecycleOwner) {
            binding.apply {
                if (it.isEmpty()) {
                    lottie.isVisible = true
                    notFound.isVisible = true
                    deleteBtn.isVisible = false
                } else {
                    lottie.isVisible = false
                    notFound.isVisible = false
                    deleteBtn.isVisible = true
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        UtilObjects.adapterFirstLogin = true
    }
}