package com.example.proyecto.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyecto.API.InterfaceAPI
import com.example.proyecto.R
import com.example.proyecto.Responses.CityResponse
import com.example.proyecto.Responses.LugarResponse
import com.example.proyecto.Responses.ResenyaResponse
import com.example.proyecto.Responses.UsuariResponse
import com.example.proyecto.activities.CrearResenyaActivity
import com.example.proyecto.adapters.ResenyaAdapter
import com.example.proyecto.databinding.FragmentResenyasBinding
import com.example.proyecto.databinding.FragmentResenyasLugaresBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ResenyasLugaresFragment : Fragment() {

    private lateinit var binding: FragmentResenyasLugaresBinding
    private var resenyas: List<ResenyaResponse>? = null
    private lateinit var lugar: LugarResponse
    private lateinit var usuario: UsuariResponse
    private lateinit var ciudad: CityResponse
    private lateinit var adapterResenya: ResenyaAdapter
    private lateinit var linearLayout: LinearLayoutManager

    companion object {
        @JvmStatic
        fun newInstance(l: LugarResponse, u: UsuariResponse, c: CityResponse) =
            ResenyasLugaresFragment().apply {
                arguments = Bundle().apply {
                    putSerializable("lugar", l)
                    putSerializable("usuario", u)
                    putSerializable("ciudad", c)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            lugar = it.getSerializable("lugar") as LugarResponse
            usuario = it.getSerializable("usuario") as UsuariResponse
            ciudad= it.getSerializable("ciudad") as CityResponse

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentResenyasLugaresBinding.inflate(inflater,container,false)

        binding.titulo.text = "Resenyas de ${lugar.nombre}"

        resenyas = emptyList()

        getResenyas("/reseñas/lugar/${lugar.id}")

        binding.fab.setOnClickListener {
            val intent = Intent(activity, CrearResenyaActivity::class.java)
            intent.putExtra("lugar", lugar)
            intent.putExtra("usuario", usuario)
            intent.putExtra("ciudad", ciudad)
            startActivity(intent)
        }

        return binding.root
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")//Parte fija de la URL, la que no varia
            .addConverterFactory(GsonConverterFactory.create())//Libreria de GsonConverter, convierte JSON a ComicResponse
            .build()
    }

    private fun getResenyas(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(InterfaceAPI::class.java).getResenyas(query)
            resenyas = call.body()

            adapterResenya = ResenyaAdapter(resenyas)
            linearLayout = LinearLayoutManager(context)

            // Actualizar el RecyclerView en el hilo principal
            withContext(Dispatchers.Main) {
                binding.reciclerResenyasLugares.apply {
                    layoutManager = linearLayout
                    adapter = adapterResenya
                }
            }

        }

    }


}