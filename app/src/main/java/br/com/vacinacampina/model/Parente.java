package br.com.vacinacampina.model;

import android.net.Uri;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Parente implements Serializable {


    private String id;
    private String nome;
    private Double idade;
    private String parentesco;
    private String urlFoto;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getIdade() {
        return idade;
    }

    public void setIdade(Double idade) {
        this.idade = idade;
    }

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Parente parente = (Parente) o;
        return Objects.equals(id, parente.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
