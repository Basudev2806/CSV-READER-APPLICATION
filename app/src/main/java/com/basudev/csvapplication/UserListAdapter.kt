package crash.gocolor.gowebs.csvapplication

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import crash.gocolor.gowebs.csvapplication.UserListAdapter.UserListViewHolder
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import crash.gocolor.gowebs.csvapplication.R
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.ArrayList

class UserListAdapter(users: List<User>, context: Context) :
    RecyclerView.Adapter<UserListViewHolder>() {
    private var mUsers: List<User> = ArrayList()
    private val MContext: Context
    var dt1 = SimpleDateFormat("yyyy-MM-dd")

    init {
        mUsers = users
        MContext = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user, parent, false)
        return UserListViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        holder.bindUser(mUsers[position])
    }

    override fun getItemCount(): Int {
        return mUsers.size
    }

    inner class UserListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name: TextView
        private val dob: TextView
        private val height: TextView
        private val role: TextView
        private val status: TextView

        init {
            name = itemView.findViewById(R.id.name)
            dob = itemView.findViewById(R.id.dob)
            height = itemView.findViewById(R.id.height)
            role = itemView.findViewById(R.id.role)
            status = itemView.findViewById(R.id.status)
        }

        fun bindUser(user: User) {
            name.text = user.fullNames
            dob.text = dt1.format(user.dateOfBirth)
            height.text = user.height.toString()
            role.text = listToString(user.roles)
            status.text = user.isActive.toString()
        }

        fun listToString(stringList: List<String?>?): String? {
            var result: String? = ""
            for (s in stringList!!) {
                result += s
            }
            return result
        }
    }
}