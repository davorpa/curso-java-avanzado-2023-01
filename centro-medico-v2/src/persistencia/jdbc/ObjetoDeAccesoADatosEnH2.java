package persistencia.jdbc;

import modelo.impl.Enfermera;
import modelo.impl.Medico;
import modelo.impl.Paciente;
import modelo.impl.Paramedico;
import org.apache.log4j.Logger;
import persistencia.IObjetoDeAcessoADatos;
import persistencia.PersistenciaException;

import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class ObjetoDeAccesoADatosEnH2 implements IObjetoDeAcessoADatos {

    protected final Logger logger = Logger.getLogger(getClass());

    private static volatile boolean driverJDBCcargado = false;
    private static volatile boolean esquemaDDBBcargado = false;


    private Connection conexionJDBC = null;


    public ObjetoDeAccesoADatosEnH2() {
        registrarDriverJDBC();
        realizarConexionJDBC();
    }

    protected void registrarDriverJDBC() {
        // Registrar el driver JDBC una sola vez
        if (driverJDBCcargado) return;
        synchronized (ObjetoDeAccesoADatosEnH2.class) {
            if (!driverJDBCcargado) {
                logger.info("Registrando driver JDBC de conexión con la base de datos H2...");
                try {
                    Class.forName("org.h2.Driver");
                } catch (ClassNotFoundException ex) {
                    throw new PersistenciaException(
                            "No se pudo cargar el driver JDBC. Compruebe que h2.jar esté agregado al classpath", ex);
                }
                driverJDBCcargado = true;
                logger.info("Se ha registrado el driver JDBC de conexión con la base de datos H2.");
            }
        }
    }


    protected synchronized void realizarConexionJDBC() {
        // Tener una instancia de conexión por DAO pero que inicialize la BBDD una sola vez.
        try {
            if (esquemaDDBBcargado && conexionJDBC != null) return;
            synchronized (ObjetoDeAccesoADatosEnH2.class) {
                if (esquemaDDBBcargado) {
                    if (conexionJDBC != null) return;
                    logger.info("Conectando con la base de datos H2...");
                    conexionJDBC = DriverManager.getConnection(
                            "jdbc:h2:./data/h2-db;", "sa", "");
                } else {
                    logger.info("Conectando e inicializando la base de datos H2...");
                    conexionJDBC = DriverManager.getConnection(
                            "jdbc:h2:./data/h2-db;INIT=RUNSCRIPT FROM 'sql/h2-db.init.sql'", "sa", "");
                    esquemaDDBBcargado = true;
                }
            }
            logger.info("Se ha establecido nueva conexión con la base de datos H2.");
        } catch (SQLException ex) {
            throw new PersistenciaException("No se pudo conectar con la base de datos.", ex);
        }
    }

    @Override
    public void close() throws Exception {
        if (conexionJDBC != null) {
            logger.info("Cerrando conexión con la base de datos...");
            conexionJDBC.close();
        }
    }


    @Override
    public boolean guardarMedico(final Medico m) {
        realizarConexionJDBC();
        try (PreparedStatement stmt = conexionJDBC.prepareStatement(
                "INSERT INTO medicos (id, dni, nombre, telefono, especialidad) VALUES (?, ?, ?, ?, ?)")) {
            stmt.setInt(1, m.getId());
            stmt.setString(2, m.getDni());
            stmt.setString(3, m.getNombre());
            stmt.setString(4, m.getTelefono());
            stmt.setObject(5, m.getEspecialidad());
            long filasAfectadas = stmt.executeLargeUpdate();

            return filasAfectadas > 0;
        } catch (SQLException ex) {
            throw new PersistenciaException("No se pudo insertar médico.", ex);
        }
    }

    @Override
    public boolean guardarPaciente(final Paciente p) {
        realizarConexionJDBC();
        try (PreparedStatement stmt = conexionJDBC.prepareStatement(
                "INSERT INTO pacientes (dni, nombre, telefono, fecha_nacimiento, grupo_sanguineo) VALUES (?, ?, ?, ?, ?)")) {
            stmt.setString(1, p.getDni());
            stmt.setString(2, p.getNombre());
            stmt.setString(3, p.getTelefono());
            stmt.setObject(4, p.getFechaNacimiento());
            stmt.setString(5, p.getGrupoSanguineo());
            long filasAfectadas = stmt.executeLargeUpdate();

            return filasAfectadas > 0;
        } catch (SQLException ex) {
            throw new PersistenciaException("No se pudo insertar paciente.", ex);
        }
    }

    @Override
    public boolean guardarEnfermera(final Enfermera e) {
        realizarConexionJDBC();
        try (PreparedStatement stmt = conexionJDBC.prepareStatement(
                "INSERT INTO enfermeras (id, dni, nombre, telefono) VALUES (?, ?, ?, ?)")) {
            stmt.setInt(1, e.getId());
            stmt.setString(2, e.getDni());
            stmt.setString(3, e.getNombre());
            stmt.setString(4, e.getTelefono());
            long filasAfectadas = stmt.executeLargeUpdate();

            return filasAfectadas > 0;
        } catch (SQLException ex) {
            throw new PersistenciaException("No se pudo insertar enfermera.", ex);
        }
    }

    @Override
    public boolean guardarParamedico(final Paramedico p) {
        realizarConexionJDBC();
        try (PreparedStatement stmt = conexionJDBC.prepareStatement(
                "INSERT INTO paramedicos (id, dni, nombre, telefono) VALUES (?, ?, ?, ?)")) {
            stmt.setInt(1, p.getId());
            stmt.setString(2, p.getDni());
            stmt.setString(3, p.getNombre());
            stmt.setString(4, p.getTelefono());
            long filasAfectadas = stmt.executeLargeUpdate();

            return filasAfectadas > 0;
        } catch (SQLException ex) {
            throw new PersistenciaException("No se pudo insertar paramedico.", ex);
        }
    }

    @Override
    public List<Medico> consultarMedicos() {
        realizarConexionJDBC();
        try (PreparedStatement stmt = conexionJDBC.prepareStatement(
                "SELECT id, dni, nombre, telefono, especialidad FROM medicos")) {
            try (ResultSet rs = stmt.executeQuery()) {
                rs.beforeFirst();

                List<Medico> list = new LinkedList<>();
                while (rs.next()) {
                    list.add(new Medico(
                            rs.getInt("id"),
                            rs.getString("dni"),
                            rs.getString("nombre"),
                            rs.getString("telefono"),
                            rs.getString("especialidad")));
                }
                return list;
            }
        } catch (SQLException ex) {
            throw new PersistenciaException("No se pudo consultar por médicos.", ex);
        }
    }

    @Override
    public List<Paciente> consultarPacientes() {
        realizarConexionJDBC();
        try (PreparedStatement stmt = conexionJDBC.prepareStatement(
                "SELECT dni, nombre, telefono, fecha_nacimiento, grupo_sanguineo FROM pacientes")) {
            try (ResultSet rs = stmt.executeQuery()) {
                rs.beforeFirst();

                List<Paciente> list = new LinkedList<>();
                while (rs.next()) {
                    list.add(new Paciente(
                            rs.getString("dni"),
                            rs.getString("nombre"),
                            rs.getString("telefono"),
                            rs.getObject("fecha_nacimiento", LocalDate.class),
                            rs.getString("grupo_sanguineo")));
                }
                return list;
            }
        } catch (SQLException ex) {
            throw new PersistenciaException("No se pudo consultar por pacientes.", ex);
        }
    }

    @Override
    public List<Enfermera> consultarEnfermeras() {
        realizarConexionJDBC();
        try (PreparedStatement stmt = conexionJDBC.prepareStatement(
                "SELECT id, dni, nombre, telefono FROM enfermeras")) {
            try (ResultSet rs = stmt.executeQuery()) {
                rs.beforeFirst();

                List<Enfermera> list = new LinkedList<>();
                while (rs.next()) {
                    list.add(new Enfermera(
                            rs.getInt("id"),
                            rs.getString("dni"),
                            rs.getString("nombre"),
                            rs.getString("telefono")));
                }
                return list;
            }
        } catch (SQLException ex) {
            throw new PersistenciaException("No se pudo consultar por enfermeras.", ex);
        }
    }

    @Override
    public List<Paramedico> consultarParamedicos() {
        realizarConexionJDBC();
        try (PreparedStatement stmt = conexionJDBC.prepareStatement(
                "SELECT id, dni, nombre, telefono FROM paramedicos")) {
            try (ResultSet rs = stmt.executeQuery()) {
                rs.beforeFirst();

                List<Paramedico> list = new LinkedList<>();
                while (rs.next()) {
                    list.add(new Paramedico(
                            rs.getInt("id"),
                            rs.getString("dni"),
                            rs.getString("nombre"),
                            rs.getString("telefono")));
                }
                return list;
            }
        } catch (SQLException ex) {
            throw new PersistenciaException("No se pudo consultar por paramédicos.", ex);
        }
    }
}
