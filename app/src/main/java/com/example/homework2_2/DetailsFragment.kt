package com.example.homework2_2

import android.app.AlarmManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.homework2_2.database.User
import com.example.homework2_2.database.UserViewModel
import com.example.homework2_2.databinding.FragmentDetailsBinding
import java.util.*
import kotlin.properties.Delegates

class DetailsFragment : Fragment() {

    private val args by navArgs<DetailsFragmentArgs>()

    private lateinit var mUserViewModel : UserViewModel

    private val binding by lazy {
        FragmentDetailsBinding.inflate(layoutInflater)
    }

    private val alarmManager by lazy{
        requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }

    private var yearc by Delegates.notNull<Int>()

    private var monthc by Delegates.notNull<Int>()

    private var dayofm by Delegates.notNull<Int>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = binding.root

        mUserViewModel = ViewModelProvider(this)[UserViewModel ::class.java]

        binding.Name.setText(args.currentUser.name)
        binding.Catergory.setText(args.currentUser.catergory)
        binding.des.setText(args.currentUser.description)
        binding.date.text = args.currentUser.date

        binding.Cal.setOnDateChangeListener{view, year, month ,day ->

            if(day == 1)
            {
                if(month == 1)
                {
                    yearc = year - 1
                }
                else if ( month == 2 || month == 4 || month == 6 || month == 8 || month == 9 || month == 11)
                {
                    dayofm = 31

                }
                else if( month == 3)
                {
                    dayofm = 28
                }
                else
                {
                    dayofm  = 30
                }
                //monthc = month -1
            }
            else
            {
                yearc = year
                monthc = month + 1
                dayofm = day
            }
            val calendar = Calendar.getInstance()
            val calendarc = Calendar.getInstance()
            calendar.set(yearc,monthc-1 ,dayofm-1,8,30,0)
            calendarc.set(2022,8,9,11,42,0)
            settingAlarm(calendar.timeInMillis.toInt() - System.currentTimeMillis().toInt())

            binding.date.text = StringBuilder().append(day).append(month +1).append(year).toString()

            Log.d("Date", dayofm.toString())
        }



        binding.UpdateBtn.setOnClickListener{
            updateUser()
            findNavController().navigate(R.id.action_detailsFragment_to_mainFragment)
        }

        return view
    }

    fun updateUser(){
        val name = binding.Name.text.toString()
        val cat = binding.Catergory.text.toString()
        val des = binding.des.text.toString()
        val date = binding.date.text.toString()


        val updateUser = User(args.currentUser.key,name,cat,des,date,yearc,monthc,dayofm)

        mUserViewModel.updateUser(updateUser)
    }

    public fun settingAlarm(timeMilliseconds : Int)
    {
        val setTime = System.currentTimeMillis() + timeMilliseconds
        alarmManager.set(
            AlarmManager.RTC_WAKEUP,
            setTime,
            Broadcast.getAlarmPendingIntent(
                requireActivity().applicationContext,
                Broadcast.getIntent(
                    requireActivity().applicationContext,
                    "this is a new event",
                    "this is the info"
                )
            )
        )

    }

}