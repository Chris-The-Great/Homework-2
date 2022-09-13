package com.example.homework2_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.homework2_2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private  val binding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val navHost = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHost.navController

        val appConfig = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController,appConfig)


    }
    val object3 = OtherObject().moreFunction()

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.fragmentContainerView).navigateUp()
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}

open class OtherObject{

}
fun OtherObject.moreFunction() : String {
    return this.toString()
}