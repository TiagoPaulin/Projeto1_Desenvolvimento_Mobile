package com.example.projeto1_somativa

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.projeto1_somativa.model.Singleton
import com.example.projeto1_somativa.model.User


class RegisterActivity : AppCompatActivity() {

    private lateinit var username : EditText;
    private lateinit var password : EditText;
    private lateinit var register : Button;
    private lateinit var textNavigation : TextView;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        username = findViewById(R.id.editTextUsernameRegister);
        password = findViewById(R.id.editTextPasswordRegister);
        register = findViewById(R.id.buttonRegister);
        textNavigation = findViewById(R.id.textViewNavigationRegister);

        textNavigation.setOnClickListener {

            navigateToLogin();

        }

        register.setOnClickListener {

            registerUser();

        }

    }

    private fun registerUser(){

        var usernameValue = username.text.toString();
        var passwordValue = password.text.toString();

        var message = validation(usernameValue, passwordValue);

        if (message == "Cadastro realizado com sucesso!") {

            showMessage(message)

            Singleton.add(User(0, usernameValue, passwordValue))

        } else {

            showMessage(message)

        }

    }

    private fun validation(usernameValue : String, passwordValue : String) : String {

        if (usernameValue.isNullOrEmpty()){

            return "Preencha o campo de usuário";

        } else if (passwordValue.isNullOrEmpty()){

            return "Preencha o campo da senha";

        } else if ((usernameValue.length > 15) || (usernameValue.length < 3)) {

            return "O nome de usuário deve ter no mínimo 3 caracteres e no máximo 15";

        } else if ((passwordValue.length < 8) || passwordValue.length > 20) {

            return "A senha deve conter no mínimo 8 caracteres e no máximo 20"

        }

        return "Cadastro realizado com sucesso!";

    }

    private fun showMessage(message : String){

        val builder = AlertDialog.Builder(this);

        builder.setTitle("MENSAGEM");
        builder.setMessage(message);
        builder.setPositiveButton("OK") { dialog, _ ->

            if (message == "Cadastro realizado com sucesso!") {

                dialog.dismiss();
                navigateToLogin();

            } else {

                username.text.clear();
                password.text.clear();
                dialog.dismiss();

            }
        }

        val dialog = builder.create();

        dialog.show();

    }

    private fun navigateToLogin(){

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

    }

}