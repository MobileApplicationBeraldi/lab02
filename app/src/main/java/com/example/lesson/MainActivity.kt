package com.example.lesson

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyScreen()
        }
    }
}

@Composable
fun StatelessCounter(
    count: Int,
    onIncrement: () -> Unit,
    modifier: Modifier = Modifier)
{
    Column(modifier = Modifier.fillMaxSize()) {
        Text("$count")
        Button(onClick = { onIncrement() }) {
            Text("click me")
        }
    }
}

@Composable
fun StatelessCounter2(viewModel: MyViewModel) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text("${viewModel.count.collectAsState().value}")
        Button(onClick = { viewModel.increment() }) {
            Text("click me")
        }
    }

}


@Composable
fun MyScreen(){
   // var count by  remember { mutableIntStateOf(0) }
    //StatelessCounter(count = count, onIncrement = { count ++})

    StatelessCounter2(MyViewModel())


    //StatefulCounter()

}

class MyViewModel : ViewModel() {
    private val _state = MutableStateFlow(0)
    val count = _state.asStateFlow()
    private val _dataSource = Data(0)
    @Composable
    fun StatefulCounter(){
        var count by  remember { mutableIntStateOf(0) }
        Column(modifier = Modifier.fillMaxSize()) {
            Text("$count")
            Button(onClick = { count++ }) {
                Text("click me")
            }
        }
    }


    fun increment() {
        _dataSource.increment()
        //_state.value++
        viewModelScope.launch {
            delay(1000)
            _state.value++
        }

    }


}



class Data(val value: Int){
    var _value : Int = 0

    fun increment(){
        _value++
    }

}