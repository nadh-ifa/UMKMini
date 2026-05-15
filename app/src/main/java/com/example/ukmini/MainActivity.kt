package com.example.ukmini

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.ukmini.navigation.AppNavigation
import com.example.ukmini.ui.theme.UKMiniTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            UKMiniTheme {
                AppNavigation()
            }
        }
    }
}