/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senior.alarumback.model;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Eduardo
 */
public class Usuario {

    private String nm_apelido;
    private String dt_atualizacao;
    private String nm_usuario;
    private String ds_pc_nome;
    private String ds_pc_ip;
    private String ds_os_nome;
    private String ds_os_versao;
    private String ds_os_arquitetura;

    public Usuario() {
        InetAddress ip;
        try {
            ip = InetAddress.getLocalHost();
        } catch (UnknownHostException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            ip = null;
        }
        this.dt_atualizacao = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
        this.nm_usuario = System.getProperty("user.name");
        this.ds_pc_nome = ip == null ? "--" : ip.getHostAddress();
        this.ds_pc_ip = ip == null ? "--" : ip.getHostAddress();
        this.ds_os_nome = System.getProperty("os.name");
        this.ds_os_versao = System.getProperty("os.version");
        this.ds_os_arquitetura = System.getProperty("os.arch");
    }

    public Usuario(String nm_apelido) {
        InetAddress ip;
        try {
            ip = InetAddress.getLocalHost();
        } catch (UnknownHostException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            ip = null;
        }
        this.dt_atualizacao = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
        this.nm_usuario = System.getProperty("user.name");
        this.ds_pc_nome = ip == null ? "--" : ip.getCanonicalHostName();
        this.ds_pc_ip = ip == null ? "--" : ip.getHostAddress();
        this.ds_os_nome = System.getProperty("os.name");
        this.ds_os_versao = System.getProperty("os.version");
        this.ds_os_arquitetura = System.getProperty("os.arch");
        this.nm_apelido = nm_apelido;
    }

    public String getNm_apelido() {
        return nm_apelido;
    }

    public String getDt_atualizacao() {
        return dt_atualizacao;
    }

    public String getNm_usuario() {
        return nm_usuario;
    }

    public String getDs_pc_nome() {
        return ds_pc_nome;
    }

    public String getDs_pc_ip() {
        return ds_pc_ip;
    }

    public String getDs_os_nome() {
        return ds_os_nome;
    }

    public String getDs_os_versao() {
        return ds_os_versao;
    }

    public String getDs_os_arquitetura() {
        return ds_os_arquitetura;
    }

}
