package com.example.proyecto.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyecto.API.InterfaceAPI
import com.example.proyecto.R
import com.example.proyecto.Responses.CityResponse
import com.example.proyecto.Responses.LugarResponse
import com.example.proyecto.Responses.ResenyaResponse
import com.example.proyecto.adapters.CityAdapter
import com.example.proyecto.adapters.LugarAdapter
import com.example.proyecto.adapters.OnClickListenerLugares
import com.example.proyecto.adapters.ResenyaAdapter
import com.example.proyecto.databinding.FragmentResenyasBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ResenyasFragment : Fragment() {

    private var resenyas: List<ResenyaResponse>? = null
    private lateinit var ciudad: CityResponse
    private lateinit var binding : FragmentResenyasBinding
    private lateinit var adapterResenya: ResenyaAdapter
    private lateinit var linearLayout: LinearLayoutManager
    private lateinit var dividerItemDecoration: DividerItemDecoration

    companion object {
        @JvmStatic
        fun newInstance(c: CityResponse) =
            ResenyasFragment().apply {
                arguments = Bundle().apply {
                    putSerializable("ciudad", c)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            ciudad = it.getSerializable("ciudad") as CityResponse
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentResenyasBinding.inflate(inflater,container,false)

        resenyas = emptyList()

        getResenyas("/reseñas/ciudad/${ciudad.id}")


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

            withContext(Dispatchers.Main) {
            binding.titulo.text = "No hay resenyas de la ciudad de ${ciudad.nombre}"

            if (resenyas != null){
                binding.titulo.text = "Resenyas de todos los lugares de ${ciudad.nombre}"
            }

            adapterResenya = ResenyaAdapter(resenyas)
            linearLayout = LinearLayoutManager(context)
            dividerItemDecoration = DividerItemDecoration(context, 1)

            // Actualizar el RecyclerView en el hilo principal

                binding.reciclerResenyas.apply {
                    layoutManager = linearLayout
                    adapter = adapterResenya
                    addItemDecoration(dividerItemDecoration)
                }
            }


        }

    }


}