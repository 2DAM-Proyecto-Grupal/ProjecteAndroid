package com.example.proyecto.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyecto.API.InterfaceAPI
import com.example.proyecto.R
import com.example.proyecto.Responses.CityResponse
import com.example.proyecto.Responses.LugarResponse
import com.example.proyecto.Responses.UsuariResponse
import com.example.proyecto.activities.CrearLugarActivity
import com.example.proyecto.activities.LugaresActivity
import com.example.proyecto.activities.MapsActivity
import com.example.proyecto.adapters.CityAdapter
import com.example.proyecto.adapters.LugarAdapter
import com.example.proyecto.adapters.OnClickListenerLugares
import com.example.proyecto.databinding.FragmentLugaresBinding
import com.example.proyecto.databinding.FragmentMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class LugaresFragment : Fragment(), OnClickListenerLugares {

    private var lugares: List<LugarResponse>? = null
    private lateinit var ciudad: CityResponse
    private lateinit var usuario: UsuariResponse
    private lateinit var binding : FragmentLugaresBinding
    private lateinit var adapterLugar: LugarAdapter
    private lateinit var listener : LugaresListener
    private lateinit var gridLayout: GridLayoutManager

    companion object {
        @JvmStatic
        fun newInstance(c: CityResponse, u: UsuariResponse) =
            LugaresFragment().apply {
                arguments = Bundle().apply {
                    putSerializable("ciudad", c)
                    putSerializable("usuario", u)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            ciudad = it.getSerializable("ciudad") as CityResponse
            usuario = it.getSerializable("usuario") as UsuariResponse
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLugaresBinding.inflate(inflater,container,false)

        binding.titulo.text = "Lugares populares de ${ciudad.nombre}"

        lugares = emptyList()
        println(ciudad.id)

        getLugares("/lugares/ciudad/${ciudad.id}")

        setLugaresListener(object : LugaresListener {
            override fun onLugarSeleccionado(l: LugarResponse) {
                val intent = Intent(activity, LugaresActivity::class.java)
                intent.putExtra("lugar", l)
                intent.putExtra("ciudad", ciudad)
                intent.putExtra("usuario", usuario)
                startActivity(intent)
            }
        })

        if (!usuario.premium)
            binding.fab.isInvisible = true


        binding.fab.setOnClickListener {
            val intent = Intent(activity, CrearLugarActivity::class.java)
            intent.putExtra("ciudad", ciudad)
            intent.putExtra("usuario", usuario)
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

    private fun getLugares(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(InterfaceAPI::class.java).getLugares(query)
            lugares = call.body()

            adapterLugar = LugarAdapter(lugares, this@LugaresFragment)
            gridLayout = GridLayoutManager(context,2)

            // Actualizar el RecyclerView en el hilo principal
            withContext(Dispatchers.Main) {
                binding.reciclerLugares.apply {
                    layoutManager = gridLayout
                    adapter = adapterLugar
                }
            }

        }

    }
    override fun setLugaresListener(listener: LugaresListener) {
        this.listener = listener

    }
    override fun onClick(lugar: LugarResponse) {
        if (listener != null)
            listener.onLugarSeleccionado(lugar)
    }


}