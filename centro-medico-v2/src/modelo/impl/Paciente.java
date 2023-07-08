package modelo.impl;

import modelo.IPaciente;
import modelo.Persona;

import java.util.Objects;

public class Paciente extends Persona implements IPaciente {

    private String grupoSanguineo;

    public Paciente(String dni, String nombre, String telefono, String grupoSanguineo) {
        super(dni, nombre, telefono);
        setGrupoSanguineo(grupoSanguineo);
    }

    public String getGrupoSanguineo() {
        return grupoSanguineo;
    }

    public void setGrupoSanguineo(String grupoSanguineo) {
        this.grupoSanguineo = grupoSanguineo != null && !grupoSanguineo.isBlank() ? grupoSanguineo : "N/A";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Paciente)) return false;
        if (!super.equals(o)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode());
    }
}
