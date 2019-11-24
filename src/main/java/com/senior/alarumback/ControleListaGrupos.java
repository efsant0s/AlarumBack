/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senior.alarumback;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.senior.alarumback.model.Usuario;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Eduardo
 */
public class ControleListaGrupos {

    private static Map<String, Map<String, Usuario>> listaGrupos = new HashMap();

    public static Map<String, Map<String, Usuario>> getListaGrupos() {
        return listaGrupos;
    }

    public static List<String> getListaGerencia() {
        return new ArrayList<>(getListaGrupos().keySet());
    }

    public static List<Usuario> getListaUsuario(String gerencia) {
        return new ArrayList<Usuario>(getListaGrupos().get(gerencia).values());
    }

    public static void lista() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRootRef = database.getReference();
        DatabaseReference gerenRef = myRootRef.child("banco");
        final Map<String, Map<String, Usuario>> listaValores = new HashMap();
        gerenRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    listaValores.clear();
                    Iterator<DataSnapshot> gerenSnaps = dataSnapshot.getChildren().iterator();
                    while (gerenSnaps.hasNext()) {
                        DataSnapshot gerenSnap = gerenSnaps.next();
                        Iterator<DataSnapshot> gruposSnaps = gerenSnap.getChildren().iterator();
                        while (gruposSnaps.hasNext()) {
                            DataSnapshot grupoSnap = gruposSnaps.next();
                            if ("usuarios".equals(grupoSnap.getKey())) {
                                Iterator<DataSnapshot> usuariosSnaps = grupoSnap.getChildren().iterator();
                                Map<String, Usuario> listaUsuario = new HashMap<>();
                                while (usuariosSnaps.hasNext()) {
                                    DataSnapshot userSnaps = usuariosSnaps.next();
                                    Usuario fcmUser = userSnaps.getValue(Usuario.class);
                                    listaUsuario.put(fcmUser.getNm_apelido(), fcmUser);
                                }
                                listaValores.put(gerenSnap.getKey(), listaUsuario);
                            }
                        }

                    }
                    listaGrupos = listaValores;

                } catch (Exception e) {
                    //Log the exception and the key 
                    System.out.println(dataSnapshot.getKey());
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println(("err"));
            }
        });

    }
}
