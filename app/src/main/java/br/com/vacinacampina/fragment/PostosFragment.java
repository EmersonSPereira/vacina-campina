package br.com.vacinacampina.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import br.com.vacinacampina.R;
import br.com.vacinacampina.util.EnumPostos;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostosFragment extends Fragment {


    public static final LatLng LOC_CAMPINA_GRANDE = new LatLng(-7.227340, -35.910320);
    public static final int ZOOM = 12;

    public PostosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_postos, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.maps_fragment);  //use SuppoprtMapFragment for using in fragment instead of activity  MapFragment = activity   SupportMapFragment = fragment
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                mMap.clear(); //clear old markers

//                CameraPosition googlePlex = CameraPosition.builder()
//                        .target(EnumPostos.CAMPINA_GRANDE.getLocalizacao())
//                        .zoom(12)
//                        .bearing(0)
//                        .tilt(45)
//                        .build();
//                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 10000, null);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LOC_CAMPINA_GRANDE, ZOOM));


                BitmapDescriptor marcador = BitmapDescriptorFactory.fromResource(R.drawable.marcador);

                for (EnumPostos posto: EnumPostos.values()){
                    mMap.addMarker(new MarkerOptions()
                            .position(posto.getLocalizacao())
                            .title(posto.getTitulo())
                            .icon(marcador));
                }
//                mMap.addMarker(new MarkerOptions()
//                        .position(EnumPostos.MONTE_SANTO.getLocalizacao())
//                        .title(EnumPostos.MONTE_SANTO.getTitulo())
//                .icon(marcador));
//
//                mMap.addMarker(new MarkerOptions()
//                        .position(EnumPostos.PALMEIRA.getLocalizacao())
//                        .title(EnumPostos.PALMEIRA.getTitulo())
//                        .snippet("His Talent : Plenty of money")
//                        .icon(marcador));
//
//                mMap.addMarker(new MarkerOptions()
//                        .position(EnumPostos.JEREMIAS.getLocalizacao())
//                        .title(EnumPostos.JEREMIAS.getTitulo())
//                        .icon(marcador));
            }
        });


        return root;
    }


}
