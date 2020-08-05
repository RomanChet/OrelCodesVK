package com.example.orelcodesvk

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import io.ktor.client.HttpClient
import io.ktor.client.call.receive
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.HttpStatement
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.jvm.javaio.toInputStream
import kotlinx.android.synthetic.main.activity_second.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

var displayWidth : Int = 0
var groupData : GroupKeeper? = null
class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        val activity = this
        displayWidth = applicationContext.resources.displayMetrics.widthPixels
        GlobalScope.launch {
            getGroup()
            getWall(activity)
        }
        // Ниже составил полный запрос к АПИ ВК, который возвращает json-структуру ( в ней присетствует сервисный ключ доступа приложения)
        // val url = "https://api.vk.com/method/wall.get?owner_id=-146026097&count=100&filter=owner&extended=1&access_token=25504d9425504d9425504d94ee25231c7c2255025504d947a797f5c281dde4adeb75510&v=5.103"
    }
    // Заголовок: иконка группы, имя группы, дата и время
    suspend fun getGroup() {
        val url = "https://api.vk.com/method/groups.getById?group_id=146026097&fields=name,photo_200&access_token=25504d9425504d9425504d94ee25231c7c2255025504d947a797f5c281dde4adeb75510&v=5.103"
        val client = HttpClient()
        var output : String = ""
        client.get<HttpStatement>(url).execute { response: HttpResponse -> // Выполнение запроса от имени клиента
            val channel = response.receive<ByteReadChannel>()
            channel.toInputStream().bufferedReader().use {
                output = it.readText()
            }
        }
        val answer: GroupResponse = Gson().fromJson<GroupResponse>(output, GroupResponse::class.java) // Из json в в эквивалентный объект Java
        Log.i("Avatar", answer.toString())
        val bmp : Bitmap? = withContext(Dispatchers.IO) { Picasso.get().load(answer.response.first().photo_200).get() }
        groupData = GroupKeeper(answer.response.first().name, bmp)
    }
    // Основная часть (тело записи)
    suspend fun getWall(activity: SecondActivity) {
        val url = "https://api.vk.com/method/wall.get?owner_id=-146026097&count=100&filter=owner&extended=1&access_token=25504d9425504d9425504d94ee25231c7c2255025504d947a797f5c281dde4adeb75510&v=5.103"
        val client = HttpClient()
        var output : String = ""
        client.get<HttpStatement>(url).execute { response: HttpResponse -> // Выполнение запроса от имени клиента
            val channel = response.receive<ByteReadChannel>()
            channel.toInputStream().bufferedReader().use {
                output = it.readText()
            }
        }
        val answer: Answer = Gson().fromJson<Answer>(output, Answer::class.java) // Из json в в эквивалентный объект Java
        if(answer.response == null) { // При ошибке
            Log.e("Connection", "Can`t connect, more info in next log")
            Log.e("Connection", output)
        }
        else { // Отправка данных в list_recycler_view
            val adapter = ListAdapter(answer.response.items)
            activity.runOnUiThread() {
                list_recycler_view.apply() {
                    layoutManager = LinearLayoutManager(activity)
                    this.adapter = adapter
                }
                progressBar.visibility = View.INVISIBLE
            }
        }

    }


}