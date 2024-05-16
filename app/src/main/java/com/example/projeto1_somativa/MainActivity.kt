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

class MainActivity : AppCompatActivity() {

    private lateinit var username : EditText;
    private lateinit var password : EditText;
    private lateinit var login : Button;
    private lateinit var textNavigation : TextView;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        Singleton.setContext(this);

        username = findViewById(R.id.editTextUsernameLogin);
        password = findViewById(R.id.editTextPasswordLogin);
        login = findViewById(R.id.buttonLogin);
        textNavigation = findViewById(R.id.textViewNavigationLogin);

        textNavigation.setOnClickListener {

           navigateToRegister();

        }

        login.setOnClickListener {

            if(loginUser()){

                navigateToHome();

            } else {

                showMessage();

            }

        }


    }

    private fun showMessage(){

        val builder = AlertDialog.Builder(this);

        builder.setTitle("MENSAGEM");
        builder.setMessage("Usuário ou senha inválidos");
        builder.setPositiveButton("OK") { dialog, _ ->

            username.text.clear();
            password.text.clear();
            dialog.dismiss();

        }

        val dialog = builder.create();

        dialog.show();

    }

    private fun loginUser() : Boolean{

        var usernameValue = username.text.toString();
        var passwordValue = password.text.toString();

        var user = Singleton.request(usernameValue);

        if (user != null && user.password == passwordValue){

            return true;

        }

        return false;

    }

    private fun navigateToRegister(){

        val intent = Intent(this, RegisterActivity::class.java);
        startActivity(intent);

    }


    private fun navigateToHome(){

        val intent = Intent(this, HomeActivity::class.java);
        startActivity(intent);

    }

}