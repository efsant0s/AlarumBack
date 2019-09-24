/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senior.alarumback.model;

/**
 *
 * @author Eduardo
 */
public class Gerencia {
    private String ds_gerencia;
    private String nm_responsavel;

    public Gerencia(String ds_gerencia, String nm_responsavel) {
        this.ds_gerencia = ds_gerencia;
        this.nm_responsavel = nm_responsavel;
    }

    public String getDs_gerencia() {
        return ds_gerencia;
    }

    public void setDs_gerencia(String ds_gerencia) {
        this.ds_gerencia = ds_gerencia;
    }

    public String getNm_responsavel() {
        return nm_responsavel;
    }

    public void setNm_responsavel(String nm_responsavel) {
        this.nm_responsavel = nm_responsavel;
    }

    
}
