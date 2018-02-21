package br.com.alura.agenda;

import android.location.Address;
import android.location.Geocoder;
import android.view.ContextMenu;
import android.view.View;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import br.com.alura.agenda.dao.AlunoDAO;
import br.com.alura.agenda.modelo.Aluno;

/**
 * Created by f1avi on 20/02/2018.
 */

public class MapaFragment extends SupportMapFragment implements OnMapReadyCallback {

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        LatLng coordenada = getCoordenada("ecovillagio castelo branco");

        if(coordenada != null){
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(coordenada, 17);
            googleMap.moveCamera(update);
        }

        AlunoDAO dao = new AlunoDAO(getContext());
        for (Aluno aluno: dao.buscarAlunos()) {
            LatLng coord = getCoordenada(aluno.getEndereco());

            if(coord != null){
                MarkerOptions marcador = new MarkerOptions();
                marcador.position(coord);
                marcador.title(aluno.getNome());
                marcador.snippet(String.valueOf(aluno.getNota()));
                googleMap.addMarker(marcador);
            }
        }

        dao.close();


    }

    private LatLng getCoordenada(String endereco) {
        try {
            Geocoder geocoder = new Geocoder(getContext());
            List<Address> addresses = geocoder.getFromLocationName(endereco, 1);

            if(addresses.isEmpty()){
                return new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
