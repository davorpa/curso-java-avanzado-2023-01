package persistencia.daoimpl;

import modelo.impl.Paciente;
import persistencia.H2ConnectorSupport;
import persistencia.IPacienteDAO;
import persistencia.PersistenciaException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class PacienteDaoEnH2 extends H2ConnectorSupport implements IPacienteDAO {
    @Override
    public List<Paciente> consultarTodos() {
        try (PreparedStatement stmt = realizarConexionJDBC().prepareStatement(
                "SELECT dni, nombre, telefono, fecha_nacimiento, grupo_sanguineo FROM pacientes")) {
            try (ResultSet rs = stmt.executeQuery()) {
                rs.beforeFirst();

                List<Paciente> list = new LinkedList<>();
                while (rs.next()) {
                    list.add(tratarResultSetDeConsulta(rs));
                }
                return list;
            }
        } catch (SQLException ex) {
            throw new PersistenciaException("No se pudo consultar por pacientes.", ex);
        }
    }

    @Override
    public List<Paciente> consultarTodosPorNombre(String nombre) {
        try (PreparedStatement stmt = realizarConexionJDBC().prepareStatement(
                "SELECT dni, nombre, telefono, fecha_nacimiento, grupo_sanguineo FROM pacientes WHERE nombre ILIKE ?")) {
            stmt.setString(1, nombre == null ? null : "%" + nombre +"%");

            try (ResultSet rs = stmt.executeQuery()) {
                rs.beforeFirst();

                List<Paciente> list = new LinkedList<>();
                while (rs.next()) {
                    list.add(tratarResultSetDeConsulta(rs));
                }
                return list;
            }
        } catch (SQLException ex) {
            throw new PersistenciaException("No se pudo consultar por pacientes.", ex);
        }
    }

    @Override
    public List<Paciente> consultarTodosPorTelefono(String telefono) {
        try (PreparedStatement stmt = realizarConexionJDBC().prepareStatement(
                "SELECT dni, nombre, telefono, fecha_nacimiento, grupo_sanguineo FROM pacientes WHERE telefono ILIKE ?")) {
            stmt.setString(1, telefono == null ? null : "%" + telefono +"%");

            try (ResultSet rs = stmt.executeQuery()) {
                rs.beforeFirst();

                List<Paciente> list = new LinkedList<>();
                while (rs.next()) {
                    list.add(tratarResultSetDeConsulta(rs));
                }
                return list;
            }
        } catch (SQLException ex) {
            throw new PersistenciaException("No se pudo consultar por pacientes.", ex);
        }
    }

    @Override
    public Paciente consultarPorId(String s) {
        try (PreparedStatement stmt = realizarConexionJDBC().prepareStatement(
                "SELECT dni, nombre, telefono, fecha_nacimiento, grupo_sanguineo FROM pacientes WHERE dni = ?")) {
            stmt.setString(1, s);

            try (ResultSet rs = stmt.executeQuery()) {
                rs.beforeFirst();

                if (rs.next()) {
                    return tratarResultSetDeConsulta(rs);
                }
            }
        } catch (SQLException ex) {
            throw new PersistenciaException("No se pudo consultar por paciente.", ex);
        }
        return null;
    }

    protected Paciente tratarResultSetDeConsulta(ResultSet rs) throws SQLException {
        return new Paciente(
                rs.getString("dni"),
                rs.getString("nombre"),
                rs.getString("telefono"),
                rs.getObject("fecha_nacimiento", LocalDate.class),
                rs.getString("grupo_sanguineo"));
    }

    @Override
    public int crear(Paciente o) {
        try (PreparedStatement stmt = realizarConexionJDBC().prepareStatement(
                "INSERT INTO pacientes (dni, nombre, telefono, fecha_nacimiento, grupo_sanguineo) VALUES (?, ?, ?, ?, ?)")) {
            stmt.setString(1, o.getDni());
            stmt.setString(2, o.getNombre());
            stmt.setString(3, o.getTelefono());
            stmt.setObject(4, o.getFechaNacimiento());
            stmt.setString(5, o.getGrupoSanguineo());
            return stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new PersistenciaException("No se pudo insertar paciente.", ex);
        }
    }

    @Override
    public int actualizar(Paciente o) {
        try (PreparedStatement stmt = realizarConexionJDBC().prepareStatement(
                "UPDATE pacientes SET nombre = ?, telefono = ?, fecha_nacimiento = ?, grupo_sanguineo = ? WHERE dni = ?")) {
            stmt.setString(1, o.getNombre());
            stmt.setString(2, o.getTelefono());
            stmt.setObject(3, o.getFechaNacimiento());
            stmt.setString(4, o.getGrupoSanguineo());
            stmt.setString(5, o.getDni());
            return stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new PersistenciaException("No se pudo modificar paciente.", ex);
        }
    }

    @Override
    public int eliminar(String s) {
        try (PreparedStatement stmt = realizarConexionJDBC().prepareStatement(
                "DELETE FROM pacientes WHERE dni = ?")) {
            stmt.setString(1, s);
            return stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new PersistenciaException("No se pudo eliminar paciente.", ex);
        }
    }
}
