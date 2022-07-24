package io.raveerocks.bookstore.listing

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import io.raveerocks.bookstore.ActivityViewModel
import io.raveerocks.bookstore.AppState
import io.raveerocks.bookstore.R
import io.raveerocks.bookstore.databinding.BookItemBinding
import io.raveerocks.bookstore.databinding.FragmentBookListingBinding
import timber.log.Timber


class BookListingFragment : Fragment() {

    private lateinit var binding: FragmentBookListingBinding
    private lateinit var activityViewModel: ActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activityViewModel = ViewModelProvider(requireActivity())[ActivityViewModel::class.java]
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_book_listing, container, false)
        activityViewModel.bookItems.observe(viewLifecycleOwner) {
            for (bookItem in activityViewModel.bookItems.value!!) {
                val bookItemBinding = BookItemBinding.inflate(layoutInflater)
                bookItemBinding.bookItem = bookItem
                binding.listContainer.addView(bookItemBinding.root)
            }
        }
        binding.addNewButton.setOnClickListener(this::onAddNewBook)
        requireActivity().addMenuProvider(
            getMenuProvider(),
            viewLifecycleOwner,
            Lifecycle.State.RESUMED
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        redirect()
    }

    private fun redirect() {
        when (activityViewModel.getState()) {
            AppState.LOGIN_NOT_DONE -> onLoginNotDone()
            AppState.LOGIN_DONE -> onWelcomeNotDone()
            AppState.WELCOME_DONE -> onInstructionNotDone()
            AppState.INSTRUCTION_DONE -> {Timber.i("Successfully launched list")}
            else -> {Timber.w("Unknown app state : ${activityViewModel.getState()}")}
        }
    }

    private fun onLoginNotDone() {
        Timber.i("Redirecting to login")
        findNavController().navigate(BookListingFragmentDirections.actionBookListingFragmentToLoginFragment())
        Timber.i("Redirected to login")
    }

    private fun onWelcomeNotDone() {
        Timber.i("Redirecting to welcome")
        findNavController().navigate(BookListingFragmentDirections.actionBookListingFragmentToWelcomeFragment())
        Timber.i("Redirected to welcome")
    }

    private fun onInstructionNotDone() {
        Timber.i("Redirecting to instruction")
        findNavController().navigate(BookListingFragmentDirections.actionBookListingFragmentToInstructionFragment())
        Timber.i("Redirected to instruction")
    }

    private fun onAddNewBook(view: View) {
        Timber.i("Launching detail screen to add new book.")
        view.findNavController()
            .navigate(BookListingFragmentDirections.actionBookListingFragmentToBookDetailFragment())
        Timber.i("Launched detail screen to add new book.")
    }

    private fun getMenuProvider(): MenuProvider {
        val menuProvider = object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.main_menu, menu)
            }
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.log_out_menu -> {
                        Timber.i("Log Out option clicked")
                        activityViewModel.setState(AppState.LOG_OUT_DONE)
                        findNavController().navigate(BookListingFragmentDirections.actionBookListingFragmentToLoginFragment())
                    }
                    else -> return false
                }
                return true
            }
        }
        return menuProvider
    }
}