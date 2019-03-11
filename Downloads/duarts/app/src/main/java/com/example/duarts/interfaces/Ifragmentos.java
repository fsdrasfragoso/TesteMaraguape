package com.example.duarts.interfaces;

import com.example.duarts.fragmentos.clientesFragmento;
import com.example.duarts.fragmentos.produtoFragmento;
import com.example.duarts.fragmentos.registroClienteFragmento;
import com.example.duarts.fragmentos.rotasFragmento;
import com.example.duarts.fragmentos.venderFragmento;

public interface Ifragmentos extends
        clientesFragmento.OnFragmentInteractionListener,
        produtoFragmento.OnFragmentInteractionListener,
        registroClienteFragmento.OnFragmentInteractionListener,
        venderFragmento.OnFragmentInteractionListener,
        rotasFragmento.OnFragmentInteractionListener

{
}
