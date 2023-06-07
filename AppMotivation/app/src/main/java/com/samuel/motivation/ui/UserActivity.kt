package com.samuel.motivation.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.samuel.motivation.infra.MotivationConstants
import com.samuel.motivation.R
import com.samuel.motivation.infra.SecurityPreferences
import com.samuel.motivation.databinding.ActivityUserBinding

class UserActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonSave.setOnClickListener(this)

        supportActionBar?.hide()

        verifyUserName()
    }

    override fun onClick(view: View) {
        if (view.id === R.id.button_save) {
            handleSave()
        }
    }

    private fun verifyUserName() {
        val name = SecurityPreferences(this).getString(MotivationConstants.KEY.USER_NAME)
        if (name != "") {
            val mainActivity = Intent(this, MainActivity::class.java)
            startActivity(mainActivity)
            finish()
        }
    }

    private fun handleSave() {
        val username = binding.editName.text.toString()

        if (username != null) {
            SecurityPreferences(this).storeString(MotivationConstants.KEY.USER_NAME, username)


            // Referencia uma Activity
            val mainActivity = Intent(this, MainActivity::class.java)

            // Inicia a Activity Referenciada
            startActivity(mainActivity)

            /*
            *   Isso mata a Activity atual da memoria, e já que o usuario entrou em outra, não será
            *   possível voltar para esta novamente pois ele foi finalizada
            */
            finish()
        } else {
            Toast.makeText(this, R.string.validation_mandatory_name, Toast.LENGTH_SHORT).show()
        }
    }
}