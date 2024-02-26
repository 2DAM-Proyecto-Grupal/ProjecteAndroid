package com.example.proyecto.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.widget.SearchView
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyecto.API.InterfaceAPI
import com.example.proyecto.Responses.CityResponse
import com.example.proyecto.Responses.ResenyaResponse
import com.example.proyecto.Responses.UsuariResponse
import com.example.proyecto.activities.CrearCityActivity
import com.example.proyecto.activities.MapsActivity
import com.example.proyecto.adapters.CityAdapter
import com.example.proyecto.adapters.OnClickListenerCities
import com.example.proyecto.databinding.FragmentMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Suppress("DEPRECATION")
class MainFragment : Fragment(), OnClickListenerCities {
    private lateinit var usuario: UsuariResponse
    private lateinit var listener: CityListener
    private lateinit var binding: FragmentMainBinding
    private lateinit var adapterCiudad : CityAdapter
    private lateinit var linearLayout : LinearLayoutManager
    private lateinit var ciudades: List<CityResponse>
    private var resenyas: List<ResenyaResponse>? = null


    companion object {
        @JvmStatic
        fun newInstance(u: UsuariResponse) =
            MainFragment().apply {
                arguments = Bundle().apply {
                    putSerializable("usuario", u)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            usuario = it.getSerializable("usuario") as UsuariResponse
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater,container,false)

        val array = arrayOf("Nombre", "Internet", "Paz", "Seguridad")

        val spinner = binding.spinnerOptions

        val adpterSpiner = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, array)

        adpterSpiner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner.adapter = adpterSpiner



        ciudades = emptyList()

        var valor = array[0]
        getCiudades("/ciudades", "/reseñas", valor.toString())



        spinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                valor = array.get(position)
                getCiudades("/ciudades", "/reseñas", valor.toString())


            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        })


        setCityListener(object : CityListener {
            override fun onCiudadSeleccionada(c: CityResponse) {
                val intent = Intent(activity, MapsActivity::class.java)
                intent.putExtra("ciudad", c)
                intent.putExtra("usuario", usuario)
                startActivity(intent)
            }
        })

        val search = binding.searchViewCities
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                adapterCiudad.filter(newText)
                return true
            }
        })


        if (usuario.uid != 2){
            binding.fab.isInvisible = true
        }


        binding.fab.setOnClickListener {
            val intent = Intent(activity, CrearCityActivity::class.java)
            intent.putExtra("usuario", usuario)
            startActivity(intent)
        }


        return binding.root
    }



    override fun setCityListener(listener: CityListener) {
        this.listener = listener
    }

    override fun onClick(c: CityResponse) {
        if (listener != null)
            listener.onCiudadSeleccionada(c)
    }


    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")//Parte fija de la URL, la que no varia
            .addConverterFactory(GsonConverterFactory.create())//Libreria de GsonConverter, convierte JSON a ComicResponse
            .build()
    }

    private fun getCiudades(query: String, query2: String, valor: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(InterfaceAPI::class.java).getCiudades("$query")
            ciudades = call.body()!!

            val call2 = getRetrofit().create(InterfaceAPI::class.java).getResenyas("$query2")
            resenyas = call2.body()

            ciudades = ciudades.sortedWith(CompararCity(valor))

            adapterCiudad = CityAdapter(ciudades,resenyas,  this@MainFragment)
            linearLayout = LinearLayoutManager(context)

            // Actualizar el RecyclerView en el hilo principal
            withContext(Dispatchers.Main) {
                binding.recyclerCities.apply {
                    layoutManager = linearLayout
                    adapter = adapterCiudad
                }
            }
        }
    }

    class CompararCity(private val field: String) : Comparator<CityResponse> {
        override fun compare(city1: CityResponse, city2: CityResponse): Int {
            return when (field) {
                "Nombre" -> city1.nombre.compareTo(city2.nombre)
                "Internet" -> city1.internet.compareTo(city2.internet)
                "Paz" -> city1.peace.compareTo(city2.peace)
                "Seguridad" -> city1.safety.compareTo(city2.safety)
                else -> 0
            }
        }
    }
}




