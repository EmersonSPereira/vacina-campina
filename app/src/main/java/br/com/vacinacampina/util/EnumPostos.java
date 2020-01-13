package br.com.vacinacampina.util;

import com.google.android.gms.maps.model.LatLng;

public enum  EnumPostos {


    MONTE_SANTO("Posto de Saúde Monte Santo", new LatLng(-7.208029, -35.899469)),
    PALMEIRA("Centro de Saúde Palmeira",new LatLng(-7.208647, -35.895985) ),
    JEREMIAS("Posto de Saúde Jeremias",new LatLng(-7.204644, -35.900963) );

    private String titulo;
    private LatLng localizacao;

    EnumPostos(String titulo, LatLng localizacao) {
        this.titulo = titulo;
        this.localizacao = localizacao;
    }

    public String getTitulo() {
        return titulo;
    }

    public LatLng getLocalizacao() {
        return localizacao;
    }
}
