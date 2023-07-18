package persistencia;

import modelo.impl.Paciente;

import java.util.List;

public interface PacienteDAO extends DAO<Paciente, String> {
    List<Paciente> consultarTodosPorNombre(String nombre);

    List<Paciente> consultarTodosPorTelefono(String telefono);
}
