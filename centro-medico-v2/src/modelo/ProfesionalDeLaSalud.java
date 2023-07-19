package modelo;

import java.util.Objects;

public abstract class ProfesionalDeLaSalud extends Persona implements IProfesionalDeLaSalud {

    private Integer id;

    protected ProfesionalDeLaSalud(String dni, String nombre, String telefono) {
        super(dni, nombre, telefono);
    }

    protected ProfesionalDeLaSalud(Integer id, String dni, String nombre, String telefono) {
        super(dni, nombre, telefono);
        setId(id);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProfesionalDeLaSalud)) return false;
        if (!super.equals(o)) return false;
        ProfesionalDeLaSalud that = (ProfesionalDeLaSalud) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getId());
    }

    @Override
    protected String defineObjAttrs() {
        return String.format("id=%s, %s",
                getId(), super.defineObjAttrs());
    }

}
