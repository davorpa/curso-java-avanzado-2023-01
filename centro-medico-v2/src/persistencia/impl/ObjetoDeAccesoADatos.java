package persistencia.impl;

import modelo.impl.Medico;
import modelo.impl.Paciente;
import persistencia.IObjetoDeAcessoADatos;

import java.util.ArrayList;
import java.util.List;

public class ObjetoDeAccesoADatos implements IObjetoDeAcessoADatos {

    private List<Medico> medicos = new ArrayList<>();
    private List<Paciente> pacientes = new ArrayList<>();


    @Override
    public boolean guardarMedico(Medico m){
        boolean respuesta = false;

        if(!this.medicos.contains(m)){
            this.medicos.add(m);
            respuesta = true;
        }

        System.err.println(respuesta);

        return respuesta;

//        Implementación básica usando ciclos.
//        for (Medico medicoActual : this.medicos){
//            if(medicoActual.equals(m)){
//                return false;
//            }
//        }
//
//        this.medicos.add(m);
//
//        return true;
    }

    @Override
    public boolean guardarPaciente(Paciente p) {
        boolean respuesta = false;

        if(!this.pacientes.contains(p)){
            this.pacientes.add(p);
            respuesta = true;
        }

        System.err.println(respuesta);

        return respuesta;
    }
}
