package persistencia.daoimpl;

import modelo.impl.Medico;
import persistencia.H2ConnectorSupport;
import persistencia.IMedicoDAO;
import persistencia.PersistenciaException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class MedicoDAOEnH2 extends H2ConnectorSupport implements IMedicoDAO {
    @Override
    public List<Medico> consultarTodos() {
        try (PreparedStatement stmt = realizarConexionJDBC().prepareStatement(
                "SELECT id, dni, nombre, telefono, especialidad FROM profesional_de_la_salud WHERE tipo = ?")) {
            stmt.setString(1, "M");

            try (ResultSet rs = stmt.executeQuery()) {
                rs.beforeFirst();

                List<Medico> list = new LinkedList<>();
                while (rs.next()) {
                    list.add(tratarResultSetDeConsulta(rs));
                }
                return list;
            }
        } catch (SQLException ex) {
            throw new PersistenciaException("No se pudo consultar por médicos.", ex);
        }
    }

    @Override
    public Medico consultarPorId(Integer id) {
        try (PreparedStatement stmt = realizarConexionJDBC().prepareStatement(
                "SELECT id, dni, nombre, telefono, especialidad FROM profesional_de_la_salud WHERE id = ? AND tipo = ?")) {
            stmt.setInt(1, id);
            stmt.setString(2, "M");

            try (ResultSet rs = stmt.executeQuery()) {
                rs.beforeFirst();

                if (rs.next()) {
                    return tratarResultSetDeConsulta(rs);
                }
            }
        } catch (SQLException ex) {
            throw new PersistenciaException("No se pudo consultar por médico.", ex);
        }
        return null;
    }

    protected Medico tratarResultSetDeConsulta(ResultSet rs) throws SQLException {
        return new Medico(
                rs.getInt("id"),
                rs.getString("dni"),
                rs.getString("nombre"),
                rs.getString("telefono"),
                rs.getString("especialidad"));
    }

    @Override
    public int crear(Medico o) {
        try (PreparedStatement stmt = realizarConexionJDBC().prepareStatement(
                "INSERT INTO profesional_de_la_salud (id, tipo, dni, nombre, telefono, especialidad) VALUES (?, 'M', ?, ?, ?, ?)")) {
            stmt.setInt(1, o.getId());
            stmt.setString(2, o.getDni());
            stmt.setString(3, o.getNombre());
            stmt.setString(4, o.getTelefono());
            stmt.setString(5, o.getEspecialidad());
            return stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new PersistenciaException("No se pudo insertar médico.", ex);
        }
    }

    @Override
    public int actualizar(Medico o) {
        try (PreparedStatement stmt = realizarConexionJDBC().prepareStatement(
                "UPDATE profesional_de_la_salud SET dni = ?, nombre = ?, telefono = ?, especialidad = ? WHERE id = ? AND tipo = ?")) {
            stmt.setString(1, o.getDni());
            stmt.setString(2, o.getNombre());
            stmt.setString(3, o.getTelefono());
            stmt.setString(4, o.getEspecialidad());
            stmt.setInt(5, o.getId());
            stmt.setString(6, "M");
            return stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new PersistenciaException("No se pudo modificar médico.", ex);
        }
    }

    @Override
    public int eliminar(Integer id) {
        try (PreparedStatement stmt = realizarConexionJDBC().prepareStatement(
                "DELETE FROM profesional_de_la_salud WHERE id = ? AND tipo = ?")) {
            stmt.setInt(1, id);
            stmt.setString(2, "M");
            return stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new PersistenciaException("No se pudo eliminar médico.", ex);
        }
    }
}
