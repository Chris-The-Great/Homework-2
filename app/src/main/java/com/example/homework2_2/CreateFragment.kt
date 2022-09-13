package com.example.homework2_2

import android.app.AlarmManager
import android.app.AlarmManager.RTC_WAKEUP
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.homework2_2.database.User
import com.example.homework2_2.database.UserViewModel
import com.example.homework2_2.databinding.FragmentCreateBinding
import java.sql.Time
import java.util.*
import kotlin.properties.Delegates

class CreateFragment : Fragment() {
    private lateinit var mUserViewModel: UserViewModel

    private lateinit var mainFragment : MainFragment

    private var yearc by Delegates.notNull<Int>()

    private var monthc by Delegates.notNull<Int>()

    private var dayofm by Delegates.notNull<Int>()

    private val alarmManager by lazy{
        requireContext().getSystemService(ALARM_SERVICE) as AlarmManager
    }

    private val binding by lazy{
        FragmentCreateBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mUserViewModel = ViewModelProvider(this)[UserViewModel ::class.java]
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
            binding.date.text = StringBuilder().append(day).append(month +1).append(year).toString()

            Log.d("Date", dayofm.toString())
        }
        binding.SaveBtn.setOnClickListener{
            val calendar = Calendar.getInstance()
            val calendarc = Calendar.getInstance()
            calendar.set(yearc,monthc-1 ,dayofm-1,8,30,0)
            calendarc.set(2022,8,9,11,42,0)
            settingAlarm(calendar.timeInMillis.toInt() - System.currentTimeMillis().toInt())
            Log.d("Time",( System.currentTimeMillis().toInt() - calendar.timeInMillis.toInt()).toString())
            insertDataToDatabase()
            findNavController().navigate(
                R.id.action_createFragment_to_mainFragment
            )
        }
        return binding.root
    }

    private fun insertDataToDatabase() {
        val name = binding.Name.text.toString()
        val catergory = binding.Catergory.text.toString()
        val des = binding.des.text.toString()
        val date = binding.date.text.toString()

        val user = User(0,name,catergory,des,date,yearc,monthc,dayofm)
        mUserViewModel.addUser(user)
        Log.d("Data", user.toString())
        Toast.makeText(requireContext(), "Data has been recorde", Toast.LENGTH_LONG).show()
    }

    public fun settingAlarm(timeMilliseconds : Int)
    {
        val setTime = System.currentTimeMillis() + timeMilliseconds
        alarmManager.set(
            RTC_WAKEUP,
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