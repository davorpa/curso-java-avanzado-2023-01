package persistencia.daoimpl;

import modelo.impl.Paciente;
import persistencia.DAO;

import java.util.List;

public class PacienteDaoEnH2 implements DAO<Paciente, String> {
    @Override
    public List<Paciente> consultarTodos() {
        //[1] cargar el controlador
        //[2] crear la conexión a la base de datos
        //[3] crear un objeto statement a partir de la conexión
        //[4] ejecutar la consulta y procesar el ResultSet
        return List.of();
    }

    @Override
    public Paciente consultarPorId(String s) {
        //[1] cargar el controlador
        //[2] crear la conexión a la base de datos
        //[3] crear un objeto statement a partir de la conexión
        //[4] ejecutar la consulta y procesar el ResultSet
        return null;
    }

    @Override
    public int crear(Paciente o) {
        //[1] cargar el controlador
        //[2] crear la conexión a la base de datos
        //[3] crear un objeto statement a partir de la conexión
        //[4] ejecutar un update con la sentencia insert.
        return 0;
    }

    @Override
    public int actualizar(Paciente o) {
        //[1] cargar el controlador
        //[2] crear la conexión a la base de datos
        //[3] crear un objeto statement a partir de la conexión
        //[4]  ejecutar un update con la sentencia update.
        return 0;
    }

    @Override
    public int eliminar(String s) {
        //[1] cargar el controlador
        //[2] crear la conexión a la base de datos
        //[3] crear un objeto statement a partir de la conexión
        //[4]  ejecutar un update con la sentencia delete.
        return 0;
    }
}
