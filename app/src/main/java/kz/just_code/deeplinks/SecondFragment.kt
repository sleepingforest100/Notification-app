package kz.just_code.deeplinks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import kz.just_code.deeplinks.databinding.SecondFragmentBinding
import kz.just_code.deeplinks.databinding.StartFragmentBinding

class SecondFragment: Fragment() {
    private lateinit var binding: SecondFragmentBinding
    private val args: SecondFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = SecondFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val name = requireActivity().intent.getStringExtra("name")
        binding.showBtn.text = args.name


    }
}