package persistencia.daoimpl;

import modelo.impl.Enfermera;
import persistencia.H2ConnectorSupport;
import persistencia.IEnfermeraDAO;
import persistencia.PersistenciaException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class EnfermeraDAOEnH2 extends H2ConnectorSupport implements IEnfermeraDAO {
    @Override
    public List<Enfermera> consultarTodos() {
        try (PreparedStatement stmt = realizarConexionJDBC().prepareStatement(
                "SELECT id, dni, nombre, telefono FROM profesional_de_la_salud WHERE tipo = ?")) {
            stmt.setString(1, "E");

            try (ResultSet rs = stmt.executeQuery()) {
                rs.beforeFirst();

                List<Enfermera> list = new LinkedList<>();
                while (rs.next()) {
                    list.add(tratarResultSetDeConsulta(rs));
                }
                return list;
            }
        } catch (SQLException ex) {
            throw new PersistenciaException("No se pudo consultar por enfermeras.", ex);
        }
    }

    @Override
    public Enfermera consultarPorId(Integer id) {
        try (PreparedStatement stmt = realizarConexionJDBC().prepareStatement(
                "SELECT id, dni, nombre, telefono FROM profesional_de_la_salud WHERE id = ? AND tipo = ?")) {
            stmt.setInt(1, id);
            stmt.setString(2, "E");

            try (ResultSet rs = stmt.executeQuery()) {
                rs.beforeFirst();

                if (rs.next()) {
                    return tratarResultSetDeConsulta(rs);
                }
            }
        } catch (SQLException ex) {
            throw new PersistenciaException("No se pudo consultar por enfermera.", ex);
        }
        return null;
    }

    protected Enfermera tratarResultSetDeConsulta(ResultSet rs) throws SQLException {
        return new Enfermera(
                rs.getInt("id"),
                rs.getString("dni"),
                rs.getString("nombre"),
                rs.getString("telefono"));
    }

    @Override
    public int crear(Enfermera o) {
        try (PreparedStatement stmt = realizarConexionJDBC().prepareStatement(
                "INSERT INTO profesional_de_la_salud (tipo, dni, nombre, telefono) VALUES ('E', ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, o.getDni());
            stmt.setString(2, o.getNombre());
            stmt.setString(3, o.getTelefono());
            int filasAfectadas = stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                rs.beforeFirst();
                rs.next();
                o.setId(rs.getInt(1));
            }

            return filasAfectadas;
        } catch (SQLException ex) {
            throw new PersistenciaException("No se pudo insertar enfermera.", ex);
        }
    }

    @Override
    public int actualizar(Enfermera o) {
        try (PreparedStatement stmt = realizarConexionJDBC().prepareStatement(
                "UPDATE profesional_de_la_salud SET dni = ?, nombre = ?, telefono = ? WHERE id = ? AND tipo = ?")) {
            stmt.setString(1, o.getDni());
            stmt.setString(2, o.getNombre());
            stmt.setString(3, o.getTelefono());
            stmt.setInt(4, o.getId());
            stmt.setString(5, "E");
            return stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new PersistenciaException("No se pudo modificar enfermera.", ex);
        }
    }

    @Override
    public int eliminar(Integer id) {
        try (PreparedStatement stmt = realizarConexionJDBC().prepareStatement(
                "DELETE FROM profesional_de_la_salud WHERE id = ? AND tipo = ?")) {
            stmt.setInt(1, id);
            stmt.setString(2, "E");
            return stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new PersistenciaException("No se pudo eliminar enfermera.", ex);
        }
    }
}
