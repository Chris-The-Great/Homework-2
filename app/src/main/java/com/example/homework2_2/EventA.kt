package com.example.homework2_2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.homework2_2.database.User
import com.example.homework2_2.databinding.EventItemsBinding

class EventA: RecyclerView.Adapter<EventA.MYVIEWHOLDER>(){

    private  var userList = emptyList<User>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType :Int): MYVIEWHOLDER =
        MYVIEWHOLDER(
            EventItemsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false)
        )

    override fun onBindViewHolder(holder: MYVIEWHOLDER, position: Int) {
        holder.bind(userList[position])
        holder.binding.root.setOnClickListener {
            val currentItem = userList[position]
            val action = MainFragmentDirections.actionMainFragmentToDetailsFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }
    }

    public override fun getItemCount(): Int{ return userList.size}

    class MYVIEWHOLDER( val binding: EventItemsBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(event :User)
        {
            binding.eventName.text = event.name.toString()
            binding.eventCat.text = event.catergory.toString()
            binding.eventDes.text = event.description.toString()
            binding.eventDate.text  = event.date.toString()

        }
    }

    fun setData(user: List<User>){
        this.userList = user
        notifyDataSetChanged()
    }

}

