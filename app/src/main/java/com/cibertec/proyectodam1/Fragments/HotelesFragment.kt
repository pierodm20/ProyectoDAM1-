package com.cibertec.proyectodam1.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cibertec.proyectodam1.Adapters.HotelAdapter
import com.cibertec.proyectodam1.Entitys.Hotel
import com.cibertec.proyectodam1.R

class HotelesFragment : Fragment() {

    private lateinit var rvHotelesFrag: RecyclerView
    private lateinit var hotelAdapter: HotelAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_hoteles, container, false)
        rvHotelesFrag = view.findViewById<RecyclerView>(R.id.rvHotelesFrag)
        var hoteles = listOf(
            Hotel(1, "JW Marriott Hotel Lima", "Lima", 5, 250.00, "https://images.unsplash.com/photo-1566073771259-6a8506099945?w=500&auto=format&fit=crop&q=60"),
            Hotel(2, "Casa Andina Select Miraflores", "Lima", 4, 130.00, "https://images.unsplash.com/photo-1520250497591-112f2f40a3f4?w=500&auto=format&fit=crop&q=60"),
            Hotel(3, "Belmond Miraflores Park", "Lima", 5, 320.00, "https://images.unsplash.com/photo-1542314831-068cd1dbfeeb?w=500&auto=format&fit=crop&q=60"),
            Hotel(4, "Palacio del Inka", "Cusco", 5, 280.00, "https://images.unsplash.com/photo-1571896349842-33c89424de2d?w=500&auto=format&fit=crop&q=60"),
            Hotel(5, "Tambo del Inka Resort & Spa", "Urubamba", 5, 310.00, "https://images.unsplash.com/photo-1584132967334-10e028bd69f7?w=500&auto=format&fit=crop&q=60"),
            Hotel(6, "Arawi Miraflores Prime", "Lima", 4, 95.00, "https://images.unsplash.com/photo-1590490360182-c33d57733427?w=500&auto=format&fit=crop&q=60"),
            Hotel(7, "Hotel Costa del Sol Wyndham", "Arequipa", 4, 110.00, "https://images.unsplash.com/photo-1551882547-ff40c63fe5fa?w=500&auto=format&fit=crop&q=60"),
            Hotel(8, "Aranwa Cusco Boutique Hotel", "Cusco", 5, 210.00, "https://images.unsplash.com/photo-1611892440504-42a792e24d32?w=500&auto=format&fit=crop&q=60"),
            Hotel(9, "Ibis Larco Miraflores", "Lima", 3, 65.00, "https://images.unsplash.com/photo-1618773928121-c32242e63f39?w=500&auto=format&fit=crop&q=60"),
            Hotel(10, "DoubleTree by Hilton", "Ica", 4, 145.00, "https://images.unsplash.com/photo-1445019980597-93fa8acb246c?w=500&auto=format&fit=crop&q=60")
        )
        hotelAdapter = HotelAdapter(hoteles, requireActivity())
        rvHotelesFrag.layoutManager = LinearLayoutManager(requireContext())
        rvHotelesFrag.adapter = hotelAdapter

        return view
    }
}