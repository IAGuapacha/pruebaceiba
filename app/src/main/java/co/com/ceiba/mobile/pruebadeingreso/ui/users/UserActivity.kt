package co.com.ceiba.mobile.pruebadeingreso.ui.users

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

import co.com.ceiba.mobile.pruebadeingreso.data.model.User
import co.com.ceiba.mobile.pruebadeingreso.databinding.ActivityMainBinding
import co.com.ceiba.mobile.pruebadeingreso.ui.UiModel
import co.com.ceiba.mobile.pruebadeingreso.ui.posts.PostActivity
import co.com.ceiba.mobile.pruebadeingreso.ui.users.UserAdapter.*
import com.example.awesomedialog.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserActivity : AppCompatActivity(), OnUserClickListener {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: UserViewModel by viewModels()
    private val uiModelObserver = Observer<UiModel> { handlUi(it) }
    private val userListObserver = Observer<List<User>> { handleList(it) }
    private var progress: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        configureObservers()
        viewModel.getUsers()
        configureTextChange()

    }

    private fun configureTextChange() {
        binding.editTextSearch.doOnTextChanged { text, start, before, count ->
            binding.recyclerViewSearchResults.adapter?.let { adapter ->
                text?.let {
                    (adapter as UserAdapter).filter(it.toString())
                    if (adapter.itemCount == 0) {
                        Toast.makeText(this, "List is empty", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun configureObservers() {
        viewModel.listUsers.observe(this, userListObserver)
        viewModel.uiModel.observe(this, uiModelObserver)

    }

    private fun handlUi(uiModel: UiModel) {
        if (uiModel.showProgress) {
            progress = ProgressDialog.show(this@UserActivity, "", "Cargando...", true);
            progress?.show()
        } else {
            progress.let {
                it?.dismiss()
            }
        }

        if (uiModel.showMessageDialog.isNotBlank()) {
            showDialog(uiModel.showMessageDialog)
        }
    }

    private fun showDialog(showMessageDialog: String) {
        AwesomeDialog.build(this)
            .title(
                "Error",
                titleColor = ContextCompat.getColor(this, android.R.color.black)
            )
            .body(
                showMessageDialog,
                color = ContextCompat.getColor(this, android.R.color.black)
            )
            .background(R.color.primary_material_light)
            .onPositive(
                "Aceptar",
            ) {}
    }

    private fun handleList(userList: List<User>) {
        if (userList.isNotEmpty()) {
            binding.recyclerViewSearchResults.layoutManager =
                LinearLayoutManager(applicationContext)
            binding.recyclerViewSearchResults.adapter = UserAdapter(userList, this)
        }
    }

    override fun onUserClick(user: User) {
        val intent = Intent(this, PostActivity::class.java)
        intent.putExtra("userId", user.id)
        intent.putExtra("name", user.name)
        intent.putExtra("phone", user.phone)
        intent.putExtra("email", user.email)
        startActivity(intent)

    }
}