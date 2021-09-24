package co.com.ceiba.mobile.pruebadeingreso.ui.posts

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import co.com.ceiba.mobile.pruebadeingreso.data.model.Post
import co.com.ceiba.mobile.pruebadeingreso.databinding.ActivityPostBinding
import co.com.ceiba.mobile.pruebadeingreso.ui.UiModel
import com.example.awesomedialog.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostActivity : AppCompatActivity() {


    private lateinit var binding: ActivityPostBinding
    private val viewModel: PostViewModel by viewModels()
    private val uiModelObserver = Observer<UiModel> { handlUi(it) }
    private val postListObserver = Observer<List<Post>> { handleList(it) }
    private var progress: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        configureObservers()

        val userId = intent.getStringExtra("userId")
        val name = intent.getStringExtra("name")
        val phone = intent.getStringExtra("phone")
        val email = intent.getStringExtra("email")
        binding.name.text = name
        binding.phone.text = phone
        binding.email.text = email

        userId?.toInt()?.let {
            viewModel.getPosts(it)
        }

    }

    private fun configureObservers() {
        viewModel.listPost.observe(this, postListObserver)
        viewModel.uiModel.observe(this, uiModelObserver)
    }

    private fun handleList(listPost: List<Post>) {
        if (listPost.isNotEmpty()) {
            binding.recyclerViewPostsResults.layoutManager =
                LinearLayoutManager(applicationContext)
            binding.recyclerViewPostsResults.adapter = PostAdapter(listPost)
        }
    }

    private fun handlUi(uiModel: UiModel) {
        if (uiModel.showProgress) {
            progress = ProgressDialog.show(this@PostActivity, "", "Cargando...", true);
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
            .background(com.example.awesomedialog.R.color.primary_material_light)
            .onPositive(
                "Aceptar",
            ) {}
    }
}