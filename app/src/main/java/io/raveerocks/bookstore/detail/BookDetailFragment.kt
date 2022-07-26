package io.raveerocks.bookstore.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import io.raveerocks.bookstore.ActivityViewModel
import io.raveerocks.bookstore.BookItem
import io.raveerocks.bookstore.R
import io.raveerocks.bookstore.databinding.FragmentBookDetailBinding
import timber.log.Timber

class BookDetailFragment : Fragment() {

    data class Status(val valid:Boolean,val message: String)

    private lateinit var activityViewModel: ActivityViewModel
    private lateinit var binding: FragmentBookDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activityViewModel = ViewModelProvider(requireActivity())[ActivityViewModel::class.java]
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_book_detail, container, false)
        binding.bookItem = BookItem()
        binding.saveButton.setOnClickListener (this::onSaveClicked)
        binding.cancelButton.setOnClickListener(this::onCancelClicked)
        return binding.root
    }

    private fun onSaveClicked(view: View) {
        Timber.i("Saving book item")
        val status = validate()
        if(status.valid){
            saveBookItem()
            redirect(view)
            Timber.i("Saved book item successfully")
        }
        else{
            Timber.i("Save book item failed : ${status.message}")
            Toast.makeText(view.context,status.message,Toast.LENGTH_SHORT).show()
        }
    }

    private fun onCancelClicked(view: View) {
        Timber.i("Cancelling saving book item")
        redirect(view)
        Timber.i("Cancelled saving book item")
    }

    private fun validate():Status{
        var status = Status(true,"")
        val bookItem = binding.bookItem!!
        if(bookItem.title.value!!.isEmpty()){
            status = Status(false,"Title cannot be empty.")
        }else if(bookItem.author.value!!.isEmpty()){
            status = Status(false,"Author cannot be empty.")
        }else if(bookItem.category.value!!.isEmpty()){
            status = Status(false,"Category cannot be empty.")
        }else if(bookItem.isbn.value!!.isEmpty()){
            status = Status(false,"ISBN cannot be empty.")
        }else if(bookItem.publisher.value!!.isEmpty()){
            status = Status(false,"Publisher cannot be empty.")
        }else if(bookItem.language.value!!.isEmpty()){
            status = Status(false,"Language cannot be empty.")
        }else if(bookItem.price.value!!.isEmpty()){
            status = Status(false,"Price cannot be empty.")
        }else if(bookItem.price.value!!.toDoubleOrNull()==null){
            status = Status(false,"Invalid price entered.")
        }
        return status
    }

    private fun saveBookItem() {
        activityViewModel.addBook(binding.bookItem!!)
    }

    private fun redirect(view: View) {
        Timber.i("Redirecting to list")
        view.findNavController().navigate(
            BookDetailFragmentDirections.actionBookDetailFragmentToBookListingFragment()
        )
        Timber.i("Redirected to list")
    }

}