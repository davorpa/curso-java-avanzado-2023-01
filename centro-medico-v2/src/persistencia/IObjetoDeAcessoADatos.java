package persistencia;

import modelo.impl.Enfermera;
import modelo.impl.Medico;
import modelo.impl.Paciente;
import modelo.impl.Paramedico;

import java.util.List;
import java.util.stream.Collectors;

public interface IObjetoDeAcessoADatos extends AutoCloseable { //Data Access Object

    /**
     * Guarda un nuevo médico en el sistema.
     * @param m El {@link Medico} que se quiere guardar
     * @return true si guardó el médico, false si el médico ya estaba registrado, en cuyo caso {@code m} no será guardado.
     */
    boolean guardarMedico(Medico m);

    /**
     * Guarda un nuevo paciente en el sistema.
     * @param p El {@link Paciente} que se quiere guardar
     * @return true si se guardó el paciente, false si el paciente ya estaba registrado, en cuyo caso, {@code p} no será guardado.
     */
    boolean guardarPaciente(Paciente p);

    /**
     * Guarda una nueva enfermera en el sistema.
     * @param e La {@link Enfermera} que se quiere guardar
     * @return true si se guardó la enfermera, false si la enfermera ya estaba registrada, en cuyo caso, {@code e} no será guardada.
     */
    boolean guardarEnfermera(Enfermera e);

    /**
     * Guarda un nuevo paramédico en el sistema.
     * @param p El paramédico que se quiere guardar
     * @return true si guardó el paramédico, false si ya estaba registrado, en cuyo caso {@code p} no será guardado.
     */
    boolean guardarParamedico(Paramedico p);

    /**
     * Obtiene los médicos registrados en el sistema.
     * @return una @{@link List} inmodificable de objetos {@link Medico}.
     */
    List<Medico> consultarMedicos();

    /**
     * Obtiene los pacientes registrados en el sistema.
     * @return una @{@link List} inmodificable de objetos {@link Paciente}.
     */
    List<Paciente> consultarPacientes();

    /**
     * Obtiene las enfermeras registradas en el sistema.
     * @return una @{@link List} inmodificable de objetos {@link Enfermera}.
     */
    List<Enfermera> consultarEnfermeras();

    /**
     * Obtiene los paramédicos registrados en el sistema.
     * @return una @{@link List} inmodificable de objetos {@link Paramedico}.
     */
    List<Paramedico> consultarParamedicos();

    /**
     * Obtiene los pacientes registrados en el sistema dado un grupo sanguíneo determinado.
     * @return una @{@link List} inmodificable de objetos {@link Paciente}.
     */
    default List<Paciente> consultarPacientesGrupoSanguineo(String grupoSanguineo) {
        return consultarPacientes()
                .stream()
                .filter(p -> grupoSanguineo == null
                            ? p.getGrupoSanguineo() == null
                            : grupoSanguineo.equalsIgnoreCase(p.getGrupoSanguineo()))
                .collect(Collectors.toUnmodifiableList());
    }
}
