package br.com.vacinacampina.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Cartao {

    private String nomeVacina;
    private Double idadeParaTomar;
    private LocalDate dataPrimeiraDose;
    private LocalDate dataSegundaDose;
    private LocalDate dataTerceiraDose;
    private Integer doses;

    public Cartao(String nomeVacina, Double idadeParaTomar, LocalDate dataPrimeiraDose, LocalDate dataSegundaDose, LocalDate dataTerceiraDose, Integer doses) {
        this.nomeVacina = nomeVacina;
        this.idadeParaTomar = idadeParaTomar;
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

    public Double getIdadeParaTomar() {
        return idadeParaTomar;
    }

    public void setIdadeParaTomar(Double idadeParaTomar) {
        this.idadeParaTomar = idadeParaTomar;
    }

    public LocalDate getDataPrimeiraDose() {
        return dataPrimeiraDose;
    }

    public void setDataPrimeiraDose(LocalDate dataPrimeiraDose) {
        this.dataPrimeiraDose = dataPrimeiraDose;
    }

    public LocalDate getDataSegundaDose() {
        return dataSegundaDose;
    }

    public void setDataSegundaDose(LocalDate dataSegundaDose) {
        this.dataSegundaDose = dataSegundaDose;
    }

    public LocalDate getDataTerceiraDose() {
        return dataTerceiraDose;
    }

    public void setDataTerceiraDose(LocalDate dataTerceiraDose) {
        this.dataTerceiraDose = dataTerceiraDose;
    }

    public Integer getDoses() {
        return doses;
    }

    public void setDoses(Integer doses) {
        this.doses = doses;
    }

    public List<Cartao> getVacinasSimples(){
        List<Cartao> cartaos = new ArrayList<>();
        cartaos.add(new Cartao("BCG",0.0,null, null, null, 1));
        cartaos.add(new Cartao("Hepatite B",0.0,null, null, null, 3));
        cartaos.add(new Cartao("Penta/DTP",0.0,null, null, null, 3));
        cartaos.add(new Cartao("Pneumocócia(conjugada",0.0,null, null, null, 3));
        cartaos.add(new Cartao("Rotavirus Humano",0.0,null, null, null, 3));
        cartaos.add(new Cartao("Meningocócia Conjugada",0.0,null, null, null, 2));
        cartaos.add(new Cartao("Febre Amarela",0.0,null, null, null, 1));
        cartaos.add(new Cartao("Hepatite A",0.0,null, null, null, 1));
        cartaos.add(new Cartao("Triplice Viral",0.0,null, null, null, 1));
        cartaos.add(new Cartao("Tetra Viral",0.0,null, null, null, 1));
        cartaos.add(new Cartao("Varicela",0.0,null, null, null, 1));
        cartaos.add(new Cartao("HPV",0.0,null, null, null, 1));
        cartaos.add(new Cartao("Pneumocócica 23V",0.0,null, null, null, 1));
        cartaos.add(new Cartao("Dupla Adulto",0.0,null, null, null, 1));
        cartaos.add(new Cartao("DTPA",0.0,null, null, null, 1));
        cartaos.add(new Cartao("Influenza",0.0,null, null, null, 1));

        return cartaos;
    }
}
