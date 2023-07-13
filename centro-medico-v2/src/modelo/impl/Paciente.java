package modelo.impl;

import modelo.IPaciente;
import modelo.Persona;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class Paciente extends Persona implements IPaciente {

    private LocalDate fechaNacimiento;
    private String grupoSanguineo;

    public Paciente(final String dni, final String nombre, final String telefono,
                    final LocalDate fechaNacimiento, final String grupoSanguineo) {
        super(dni, nombre, telefono);
        setFechaNacimiento(fechaNacimiento);
        setGrupoSanguineo(grupoSanguineo);
    }

    @Override
    public long getEdad() {
        return getEdad(LocalDate.now());
    }

    @Override
    public long getEdad(final LocalDate nowReference) {
        return ChronoUnit.YEARS.between(getFechaNacimiento(), nowReference);
    }

    @Override
    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }
    public void setFechaNacimiento(final LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    @Override
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

    @Override
    protected String defineObjType() {
        return "Paciente";
    }

    @Override
    protected String defineObjAttrs() {
        return String.format("%s, fechaNacimiento='%s', edad='%s', grupoSanguineo='%s'",
                super.defineObjAttrs(), getFechaNacimiento(), getEdad(), getGrupoSanguineo());
    }
}
