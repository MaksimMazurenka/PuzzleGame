package com.example.puzlegame

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView: TextView = findViewById(R.id.textView) as TextView

        val play_button = findViewById(R.id.play_button) as Button
        val privacy_policy  =findViewById(R.id.imageButton) as ImageButton

        prefs = getSharedPreferences("com.example.puzlegame", Context.MODE_PRIVATE)
        textView.setText(prefs.getInt("money",0).toString())

        var dialog: Dialog? = Dialog(this@MainActivity)


        play_button.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
        }

        privacy_policy.setOnClickListener {
            val yourText = "1. Введение\n" +
                    "\n" +
                    "Данная Политика Конфиденциальности описывает, как мы собираем, используем и защищаем информацию, которую вы предоставляете при использовании наших услуг. Мы стремимся обеспечить вашу конфиденциальность и безопасность ваших данных.\n" +
                    "\n" +
                    "2. Собираемая информация\n" +
                    "\n" +
                    "Мы можем собирать следующие типы информации:\n" +
                    "\n" +
                    "    Личная информация: например, имя, адрес электронной почты, номер телефона, и другие данные, которые вы предоставляете добровольно.\n" +
                    "\n" +
                    "    Информация об использовании: данные о том, как вы используете наши услуги, включая информацию о посещенных страницах, действиях на сайте и т.д.\n" +
                    "\n" +
                    "    Информация об устройствах: данные о вашем устройстве, такие как IP-адрес, тип устройства, уникальные идентификаторы и другая техническая информация.\n" +
                    "\n" +
                    "3. Использование информации\n" +
                    "\n" +
                    "Мы используем собранную информацию для следующих целей:\n" +
                    "\n" +
                    "    Предоставление и улучшение услуг.\n" +
                    "\n" +
                    "    Персонализация контента и предоставление рекламы.\n" +
                    "\n" +
                    "    Обеспечение безопасности и защиты прав.\n" +
                    "\n" +
                    "    Анализ статистики использования услуг.\n" +
                    "\n" +
                    "4. Защита информации\n" +
                    "\n" +
                    "Мы предпринимаем меры для защиты вашей информации от несанкционированного доступа, изменения, раскрытия или уничтожения. Мы регулярно обновляем наши методы безопасности в соответствии с технологическими изменениями.\n" +
                    "\n" +
                    "5. Раскрытие информации третьим сторонам\n" +
                    "\n" +
                    "Мы не передаем вашу личную информацию третьим сторонам без вашего согласия, за исключением случаев, предусмотренных законом.\n" +
                    "\n" +
                    "6. Ваши права\n" +
                    "\n" +
                    "У вас есть право запросить доступ, исправление, удаление или ограничение обработки ваших персональных данных. Вы также можете в любое время отозвать свое согласие на обработку данных.\n" +
                    "\n" +
                    "7. Изменения в Политике Конфиденциальности\n" +
                    "\n" +
                    "Мы оставляем за собой право вносить изменения в нашу Политику Конфиденциальности. Любые изменения будут опубликованы на этой странице."

            val alertDialog = AlertDialog.Builder(this).create()
            alertDialog.setTitle("Политика конфиденциальности")
            val scrollView = ScrollView(this)

            val textView = TextView(this)
            textView.text = yourText
            textView.setPadding(20, 20, 20, 20)

            scrollView.addView(textView)
            alertDialog.setView(scrollView)

            alertDialog.setButton(
                AlertDialog.BUTTON_POSITIVE, "OK"
            ) { dialog, which -> dialog.dismiss() }

            alertDialog.show()
        }
    }
}