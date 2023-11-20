package kz.just_code.deeplinks

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kz.just_code.deeplinks.databinding.StartFragmentBinding

class StartFragment: Fragment() {
    private lateinit var binding: StartFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = StartFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val notificationManager = MyNotificationManager(requireContext())

        binding.showButton.setOnClickListener {
            notificationManager.showMessage(requireContext())
        }
        binding.clearButton.setOnClickListener {
            notificationManager.clear(requireContext())
        }

    }
}