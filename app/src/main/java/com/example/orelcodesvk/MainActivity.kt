package com.example.orelcodesvk
// Подключаем библиотеки
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

// Запуск активити
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Вьюха этого активити(экрана)

    }
    // Функция кнопки старт
    fun pushStart (view: View) {
        val startIntent = Intent(this, SecondActivity::class.java)
        startActivity(startIntent)
    }
}