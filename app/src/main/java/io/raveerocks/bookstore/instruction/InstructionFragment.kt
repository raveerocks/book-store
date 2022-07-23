package io.raveerocks.bookstore.instruction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import io.raveerocks.bookstore.ActivityViewModel
import io.raveerocks.bookstore.AppState
import io.raveerocks.bookstore.R
import io.raveerocks.bookstore.databinding.FragmentInstructionBinding
import timber.log.Timber

class InstructionFragment : Fragment() {

    private lateinit var binding : FragmentInstructionBinding
    private lateinit var activityViewModel: ActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activityViewModel = ViewModelProvider(requireActivity())[ActivityViewModel::class.java]
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_instruction,container,false)
        binding.markAsReadButton.setOnClickListener(this::onMarkAsRead)
        return binding.root
    }

    private fun onMarkAsRead(view: View) {
        Timber.i("Marking instruction as read")
        activityViewModel.setState(AppState.INSTRUCTION_DONE)
        view.findNavController()
            .navigate(InstructionFragmentDirections.actionInstructionFragmentToBookListingFragment())
        Timber.i("Marked instruction as read")
    }
}