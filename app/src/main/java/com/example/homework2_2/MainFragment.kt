package com.example.homework2_2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homework2_2.database.User
import com.example.homework2_2.database.UserViewModel
import com.example.homework2_2.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    private val binding by lazy{
        FragmentMainBinding.inflate(layoutInflater)
    }
    private val alarmReceiver by lazy{
        Broadcast()
    }
    private val lbManager by lazy{
        LocalBroadcastManager.getInstance(requireActivity().applicationContext)
    }

    private lateinit var mUserViewModel: UserViewModel
    private val adapterL = EventA()
    private val eventAdapter by lazy{

        findNavController().navigate(
            R.id.action_mainFragment_to_createFragment)
    }

    override fun onStart() {
        super.onStart()
        lbManager.registerReceiver(alarmReceiver, Broadcast.intentFilter)
    }

    override fun onStop() {
        super.onStop()
        lbManager.unregisterReceiver(alarmReceiver)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding.ER.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
            adapter = adapterL
        }
        //eventAdapter.updateEvent(User(eventAdapter.setData(user)))
        mUserViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        mUserViewModel.readAllData.observe(viewLifecycleOwner, Observer { user -> adapterL.setData(user)})



        binding.CreateB.setOnClickListener{
            //eventAdapter.updateEvent(User("New Name $counter", "category $counter","date$counter","description $counter"))

            findNavController().navigate(
                R.id.action_mainFragment_to_createFragment
            )

        }
        return binding.root
    }

    companion object{
        @JvmStatic
        fun newInstance() = MainFragment()
    }
    fun onEventClicked(event: User) {
        Toast.makeText(context, "Clicked on Interface", Toast.LENGTH_LONG).show()
    }


}