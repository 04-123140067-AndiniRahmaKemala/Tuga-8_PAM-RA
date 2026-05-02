package org.tugas3.project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.stopKoin
import org.koin.dsl.module
import org.tugas3.project.data.AndroidDatabaseDriverFactory
import org.tugas3.project.data.DatabaseDriverFactory
import org.tugas3.project.di.initKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        
        initKoin {
            androidContext(this@MainActivity)
            modules(module {
                single<DatabaseDriverFactory> {
                    AndroidDatabaseDriverFactory(applicationContext) 
                }
                single<Any> { applicationContext }
            })
        }

        setContent {
            App()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopKoin()
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}
