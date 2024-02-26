package com.example.proyecto.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.proyecto.API.InterfaceAPI
import com.example.proyecto.R
import com.example.proyecto.Requests.LugarRequest
import com.example.proyecto.Requests.ResenyaRequest
import com.example.proyecto.Responses.CityResponse
import com.example.proyecto.Responses.LugarResponse
import com.example.proyecto.Responses.UsuariResponse
import com.example.proyecto.databinding.ActivityCrearResenyaBinding
import com.example.proyecto.databinding.ActivityLugaresBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate

class CrearResenyaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCrearResenyaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCrearResenyaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val lugar = intent.getSerializableExtra("lugar") as LugarResponse
        val usuario = intent.getSerializableExtra("usuario") as UsuariResponse
        val ciudad = intent.getSerializableExtra("ciudad") as CityResponse

        val titulo = binding.titulo
        val texto = binding.texto
        val valoracion = binding.valoracionEditText

        binding.enviarButton.setOnClickListener {
            if (titulo.text.toString().isEmpty() ||
                texto.text.toString().isEmpty() ||
                valoracion.text.toString().isEmpty() ||
                valoracion.text.toString().toInt() !in 0..5

            ) {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT)
                    .show()
            } else {

                val resenya = ResenyaRequest(
                    titulo.text.toString(),
                    texto.text.toString(),
                    valoracion.text.toString().toInt(),
                    LocalDate.now().toString(),
                    ciudad,
                    lugar,
                    usuario.uid,
                    usuario.nombre)

                postResenya(resenya)

                Toast.makeText(this, "Formulario enviado", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, LugaresActivity::class.java)
                intent.putExtra("lugar", lugar)
                intent.putExtra("usuario", usuario)
                intent.putExtra("ciudad", ciudad)
                startActivity(intent)
                finish()

            }
        }
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")//Parte fija de la URL, la que no varia
            .addConverterFactory(GsonConverterFactory.create())//Libreria de GsonConverter, convierte JSON a ComicResponse
            .build()
    }

    private fun postResenya(resenya: ResenyaRequest) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(InterfaceAPI::class.java).postResenya(resenya)
            println(call.body())
        }
    }
}