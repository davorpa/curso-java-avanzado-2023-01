package modelo;

import java.util.Objects;

public abstract class Persona {
    private String dni;
    private String nombre;
    private String telefono;

    public Persona(String dni, String nombre, String telefono) {
        setDni(dni);
        setNombre(nombre);
        setTelefono(telefono);
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Persona)) return false;
        Persona persona = (Persona) o;
        return Objects.equals(getDni(), persona.getDni()) && Objects.equals(getNombre(), persona.getNombre()) && Objects.equals(getTelefono(), persona.getTelefono());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDni(), getNombre(), getTelefono());
    }

    @Override
    public String toString() {
        return "Persona{" +
                "dni='" + dni + '\'' +
                ", nombre='" + nombre + '\'' +
                ", telefono='" + telefono + '\'' +
                '}';
    }
}
