package persistencia.impl;

import modelo.impl.Enfermera;
import modelo.impl.Medico;
import modelo.impl.Paciente;
import persistencia.IObjetoDeAcessoADatos;

import java.util.*;

public class ObjetoDeAccesoADatos implements IObjetoDeAcessoADatos {

    private List<Medico> medicos = new ArrayList<>();
    private List<Paciente> pacientes = new ArrayList<>();
    private Set<Enfermera> enfermeras = new HashSet<>();


    @Override
    public boolean guardarMedico(Medico m){
        if(!this.medicos.contains(m)){
            this.medicos.add(m);
            return true;
        }

        return false;

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

        return respuesta;
    }

    @Override
    public boolean guardarEnfermera(Enfermera e) {
        //TODO: Implementar usando Set en vez de List
        return false;
    }

    @Override
    public List<Medico> consultarMedicos() {
        return List.copyOf(this.medicos);
    }

    @Override
    public List<Paciente> consultarPacientes() {
        return List.copyOf(this.pacientes);
    }

    @Override
    public List<Enfermera> consultarEnfermeras() {
        return List.copyOf(this.enfermeras);
    }
}
