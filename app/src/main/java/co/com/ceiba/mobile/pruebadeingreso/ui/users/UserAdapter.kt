package co.com.ceiba.mobile.pruebadeingreso.ui.users

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.com.ceiba.mobile.pruebadeingreso.core.BaseViewHolder
import co.com.ceiba.mobile.pruebadeingreso.data.model.User
import co.com.ceiba.mobile.pruebadeingreso.databinding.UserListItemBinding

class UserAdapter() : RecyclerView.Adapter<BaseViewHolder<*>>() {

    private var userList = mutableListOf<User>()
    private lateinit var itemClickListener: OnUserClickListener
    private val userListOriginal = mutableListOf<User>()

    constructor(userList: List<User>, itemClickListener: OnUserClickListener) : this() {
        this.userList = userList as MutableList<User>
        this.itemClickListener = itemClickListener

        userListOriginal.addAll(userList)
    }

    interface OnUserClickListener {
        fun onUserClick(user: User)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding =
            UserListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = UserViewHolder(itemBinding)
        itemBinding.btnViewPost.setOnClickListener {
            val position = holder.adapterPosition
            itemClickListener.onUserClick(userList[position])
        }
        return holder
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is UserViewHolder -> holder.bind(userList[position])
        }
    }

    override fun getItemCount(): Int = userList.size

    fun filter(search: String) {
        if (search.isEmpty()) {
            userList.clear()
            userList.addAll(userListOriginal)
        } else {
            val collect = userListOriginal.filter { i ->
                i.name.lowercase().contains(search)
            }
            userList.clear()
            userList.addAll(collect)
        }
        notifyDataSetChanged()
    }

    private inner class UserViewHolder(
        val binding: UserListItemBinding
    ) : BaseViewHolder<User>(binding.root) {
        override fun bind(item: User) {
            binding.email.text = item.email
            binding.name.text = item.name
            binding.phone.text = item.phone
        }

    }
}