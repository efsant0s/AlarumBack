/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senior.alarumback.model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Eduardo
 */
public class Mensagem {

    private String ds_mensagem;
    private String ie_tipo;
    private String ds_url_imagem;
    private String ds_titulo;
    private String dt_atualizacao;

    public Mensagem() {
        this.dt_atualizacao = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
    }

    public String getDt_atualizacao() {
        return dt_atualizacao;
    }

    public void setDt_atualizacao(String dt_atualizacao) {
        this.dt_atualizacao = dt_atualizacao;
    }

    public String getDs_titulo() {
        return ds_titulo;
    }

    public void setDs_titulo(String ds_titulo) {
        this.ds_titulo = ds_titulo;
    }

    public String getIe_tipo() {
        return ie_tipo;
    }

    public void setIe_tipo(String ie_tipo) {
        this.ie_tipo = ie_tipo;
    }

    public String getDs_mensagem() {
        return ds_mensagem;
    }

    public void setDs_mensagem(String ds_mensagem) {
        this.ds_mensagem = ds_mensagem;
    }

    public String getDs_url_imagem() {
        return ds_url_imagem;
    }

    public void setDs_url_imagem(String ds_url_imagem) {
        this.ds_url_imagem = ds_url_imagem;
    }

    @Override
    public String toString() {
        return "Mensagem{" + "ds_mensagem=" + ds_mensagem + ", ie_tipo=" + ie_tipo + ", ds_url_imagem=" + ds_url_imagem + ", ds_titulo=" + ds_titulo + ", dt_atualizacao=" + dt_atualizacao + '}';
    }

}
