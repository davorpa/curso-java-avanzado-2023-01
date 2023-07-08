package modelo.impl;

import modelo.ProfesionalDeLaSalud;

import java.util.Objects;

public class Paramedico extends ProfesionalDeLaSalud {
    public Paramedico(String dni, String nombre, String telefono) {
        super(dni, nombre, telefono);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Paramedico)) return false;
        if (!super.equals(o)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode());
    }
}
