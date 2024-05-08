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
import com.example.projeto1_somativa.model.UserRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var username : EditText;
    private lateinit var password : EditText;
    private lateinit var register : Button;
    private lateinit var textNavigation : TextView;

    @Inject
    lateinit var userRepository : UserRepository;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        username = findViewById(R.id.editTextUsernameLogin);
        password = findViewById(R.id.editTextPasswordLogin);
        register = findViewById(R.id.buttonLogin);
        textNavigation = findViewById(R.id.textViewNavigationLogin);

        textNavigation.setOnClickListener {

           navigateToRegister();

        }

    }

    private fun showMessage(){

        val builder = AlertDialog.Builder(this);

        builder.setTitle("MENSAGEM");
        builder.setMessage(loginUser());
        builder.setPositiveButton("OK") { dialog, _ ->

            username.text.clear();
            password.text.clear();
            dialog.dismiss();

        }

        val dialog = builder.create();

        dialog.show();

    }

    private fun loginUser() : String{

        var usernameValue = username.text.toString();
        var passwordValue = password.text.toString();

        var user = userRepository.request(usernameValue);

        if (user != null && user.password == passwordValue){

            return "Login válido!";

        }

        return "Usuário ou senha incorretos";

    }

    private fun navigateToRegister(){

        val intent = Intent(this, RegisterActivity::class.java);
        startActivity(intent);

    }

}