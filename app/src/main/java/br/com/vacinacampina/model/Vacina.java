package br.com.vacinacampina.model;

import java.io.Serializable;

public class Vacina implements Serializable {

    private String aplicacao;
    private String composicao;
    private String contraindicacao;
    private String cuidados;
    private String doses;
    private String efeitos;
    private String indicacao;
    private String nome;
    private String previne;
    private String resultados;

    public String getAplicacao() {
        return aplicacao;
    }

    public void setAplicacao(String aplicacao) {
        this.aplicacao = aplicacao;
    }

    public String getComposicao() {
        return composicao;
    }

    public void setComposicao(String composicao) {
        this.composicao = composicao;
    }

    public String getContraindicacao() {
        return contraindicacao;
    }

    public void setContraindicacao(String contraindicacao) {
        this.contraindicacao = contraindicacao;
    }

    public String getCuidados() {
        return cuidados;
    }

    public void setCuidados(String cuidados) {
        this.cuidados = cuidados;
    }

    public String getDoses() {
        return doses;
    }

    public void setDoses(String doses) {
        this.doses = doses;
    }

    public String getEfeitos() {
        return efeitos;
    }

    public void setEfeitos(String efeitos) {
        this.efeitos = efeitos;
    }

    public String getIndicacao() {
        return indicacao;
    }

    public void setIndicacao(String indicacao) {
        this.indicacao = indicacao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPrevine() {
        return previne;
    }

    public void setPrevine(String previne) {
        this.previne = previne;
    }

    public String getResultados() {
        return resultados;
    }

    public void setResultados(String resultados) {
        this.resultados = resultados;
    }
}
