package kz.just_code.deeplinks

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import kz.just_code.deeplinks.MyNotificationManager
import kz.just_code.deeplinks.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (!allPermissionGranted()) {
                pushPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }

//        if (intent.hasExtra("ACTION")) {
//            Toast.makeText(this, intent.getStringExtra("ACTION"), Toast.LENGTH_SHORT).show()
//        }

        intent.data?.let {
            it.queryParameterNames.forEach { name ->
                Log.e(">>>>", "$name : ${it.getQueryParameter(name)}")
            }
        }
    }

    private var pushPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            Toast.makeText(
                this,
                if (it) "Permission granted" else " Permission not granted",
                Toast.LENGTH_SHORT
            ).show()
        }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun allPermissionGranted() =
        ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED
}