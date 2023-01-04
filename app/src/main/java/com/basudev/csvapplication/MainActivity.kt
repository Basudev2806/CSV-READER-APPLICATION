package crash.gocolor.gowebs.csvapplication

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import crash.gocolor.gowebs.csvapplication.CsvProcessor
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import android.os.Bundle
import crash.gocolor.gowebs.csvapplication.R
import android.view.MenuInflater
import android.content.Intent
import android.app.Activity
import android.content.Context
import crash.gocolor.gowebs.csvapplication.FileUtil
import android.widget.Toast
import crash.gocolor.gowebs.csvapplication.UserListAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import android.os.Build
import android.content.pm.PackageManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import crash.gocolor.gowebs.csvapplication.MainActivity
import androidx.core.app.ActivityCompat
import java.io.File
import java.io.IOException
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    private val csvProcessor = CsvProcessor()
    private var users: List<User> = ArrayList()
    private var recyclerView: RecyclerView? = null
    private var textView: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        isWriteStoragePermissionGranted
        isReadStoragePermissionGranted
        recyclerView = findViewById(R.id.usersRecyclerView)
        textView = findViewById(R.id.no_data)

        //since there is no data to display right now
//        recyclerView!!.setVisibility(View.GONE)
        recyclerView!!.visibility = View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        if (item.itemId == R.id.action_add) {
            val intent = Intent()
                .setType("*/*")
                .setAction(Intent.ACTION_GET_CONTENT)
            startActivityForResult(Intent.createChooser(intent, "Select a file"), 2930)
            return true
        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2930 && resultCode == RESULT_OK) {

            //The uri with the location of the file
            val selectedFile = data!!.data
            if (selectedFile != null) {
                try {
                    val file = FileUtil.from(this@MainActivity, selectedFile)
                    if (getFileExtension(file) == "csv") {
                        users = csvProcessor.getUsers(file)
                        setUpRecyclerView(users, this)
                    } else {
                        Toast.makeText(this, "Please select a csv file", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    //to check if file is csv
    private fun getFileExtension(file: File): String {
        val fileName = file.name
        return if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) fileName.substring(
            fileName.lastIndexOf(".") + 1
        ) else ""
    }

    fun setUpRecyclerView(users: List<User>?, context: Context?) {
        recyclerView!!.visibility = View.VISIBLE
        textView!!.visibility = View.GONE
        val userListAdapter = UserListAdapter(users!!, context!!)
        recyclerView!!.adapter = userListAdapter
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
        recyclerView!!.layoutManager = layoutManager
        recyclerView!!.setHasFixedSize(true)
    }

    //permission is automatically granted on sdk<23 upon installation
    val isReadStoragePermissionGranted: Boolean
        get() = if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED
            ) {
                Log.v(TAG, "Permission is granted")
                true
            } else {
                Log.v(TAG, "Permission is revoked")
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    3
                )
                false
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG, "Permission is granted")
            true
        }

    //permission is automatically granted on sdk<23 upon installation
    val isWriteStoragePermissionGranted: Boolean
        get() = if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED
            ) {
                Log.v(TAG, "Permission is granted")
                true
            } else {
                Log.v(TAG, "Permission is revoked")
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    2
                )
                false
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG, "Permission is granted")
            true
        }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            2 -> {
                Log.d(TAG, "External storage2")
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.v(TAG, "Permission: " + permissions[0] + "was " + grantResults[0])
                    //resume tasks needing this permission
//                    downloadPdfFile();
                } else {
//                    progress.dismiss();
                }
            }
            3 -> {
                Log.d(TAG, "External storage1")
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.v(TAG, "Permission: " + permissions[0] + "was " + grantResults[0])
                    //resume tasks needing this permission
//                    SharePdfFile();
                } else {
//                    progress.dismiss();
                }
            }
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}