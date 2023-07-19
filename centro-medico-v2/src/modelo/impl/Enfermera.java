package modelo.impl;

import modelo.ProfesionalDeLaSalud;

import java.util.Objects;

public class Enfermera extends ProfesionalDeLaSalud {
    public Enfermera(String dni, String nombre, String telefono) {
        super(dni, nombre, telefono);
    }

    public Enfermera(Integer id, String dni, String nombre, String telefono) {
        super(id, dni, nombre, telefono);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Enfermera)) return false;
        if (!super.equals(o)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode());
    }

    @Override
    protected String defineObjType() {
        return "Enfermera";
    }
}
