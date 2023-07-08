package modelo;

import java.time.LocalDate;

public interface IPaciente {
    /**
     * Obtiene la edad de un paciente con referencia a la fecha de hoy.
     * @return la edad
     */
    long getEdad();

    /**
     * Obtiene la edad de un paciente con referencia a la fecha suministrada.
     * @param nowReference la fecha de referencia con la que calcular la edad.
     * @return la edad
     */
    long getEdad(final LocalDate nowReference);

    /**
     * Obtiene la fecha de nacimiento del paciente.
     * @return la fecha de nacimiento.
     */
    LocalDate getFechaNacimiento();

    /**
     * Obtiene el grupo sanguíneo del paciente.
     * @return el grupo sanguíneo
     */
    String getGrupoSanguineo();
}
