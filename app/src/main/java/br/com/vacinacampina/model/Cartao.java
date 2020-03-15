package br.com.vacinacampina.model;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Cartao implements Serializable {

    private String id;
    private String nomeVacina;
    @Exclude
    private String dataPrimeiraDose;
    private String dataSegundaDose;
    private String dataTerceiraDose;
    private Integer doses;

    public Cartao() {
    }

    public Cartao(String nomeVacina, String dataPrimeiraDose, String dataSegundaDose, String dataTerceiraDose, Integer doses) {
        this.nomeVacina = nomeVacina;
        this.dataPrimeiraDose = dataPrimeiraDose;
        this.dataSegundaDose = dataSegundaDose;
        this.dataTerceiraDose = dataTerceiraDose;
        this.doses = doses;
    }

    public String getNomeVacina() {
        return nomeVacina;
    }

    public void setNomeVacina(String nomeVacina) {
        this.nomeVacina = nomeVacina;
    }

    public String getDataPrimeiraDose() {
        return dataPrimeiraDose;
    }

    public void setDataPrimeiraDose(String dataPrimeiraDose) {
        this.dataPrimeiraDose = dataPrimeiraDose;
    }

    public String getDataSegundaDose() {
        return dataSegundaDose;
    }

    public void setDataSegundaDose(String dataSegundaDose) {
        this.dataSegundaDose = dataSegundaDose;
    }

    public String getDataTerceiraDose() {
        return dataTerceiraDose;
    }

    public void setDataTerceiraDose(String dataTerceiraDose) {
        this.dataTerceiraDose = dataTerceiraDose;
    }

    public Integer getDoses() {
        return doses;
    }

    public void setDoses(Integer doses) {
        this.doses = doses;
    }

    public static List<Cartao> getVacinasSimples(){
        List<Cartao> cartaos = new ArrayList<>();
        cartaos.add(new Cartao("BCG",null, null, null, 1));
        cartaos.add(new Cartao("Hepatite B",null, null, null, 3));
        cartaos.add(new Cartao("Penta/DTP",null, null, null, 3));
        cartaos.add(new Cartao("Pneumocócia(conjugada",null, null, null, 3));
        cartaos.add(new Cartao("Rotavirus Humano",null, null, null, 3));
        cartaos.add(new Cartao("Meningocócia Conjugada",null, null, null, 2));
        cartaos.add(new Cartao("Febre Amarela",null, null, null, 1));
        cartaos.add(new Cartao("Hepatite A",null, null, null, 1));
        cartaos.add(new Cartao("Triplice Viral",null, null, null, 1));
        cartaos.add(new Cartao("Tetra Viral",null, null, null, 1));
        cartaos.add(new Cartao("Varicela",null, null, null, 1));
        cartaos.add(new Cartao("HPV",null, null, null, 1));
        cartaos.add(new Cartao("Pneumocócica 23V",null, null, null, 1));
        cartaos.add(new Cartao("Dupla Adulto",null, null, null, 1));
        cartaos.add(new Cartao("DTPA",null, null, null, 1));
        cartaos.add(new Cartao("Influenza",null, null, null, 1));

        return cartaos;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
