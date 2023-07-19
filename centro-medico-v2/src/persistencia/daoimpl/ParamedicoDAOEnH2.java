package persistencia.daoimpl;

import modelo.impl.Paramedico;
import persistencia.H2ConnectorSupport;
import persistencia.IParamedicoDAO;
import persistencia.PersistenciaException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class ParamedicoDAOEnH2 extends H2ConnectorSupport implements IParamedicoDAO {
    @Override
    public List<Paramedico> consultarTodos() {
        try (PreparedStatement stmt = realizarConexionJDBC().prepareStatement(
                "SELECT id, dni, nombre, telefono FROM profesional_de_la_salud WHERE tipo = ?")) {
            stmt.setString(1, "P");

            try (ResultSet rs = stmt.executeQuery()) {
                rs.beforeFirst();

                List<Paramedico> list = new LinkedList<>();
                while (rs.next()) {
                    list.add(tratarResultSetDeConsulta(rs));
                }
                return list;
            }
        } catch (SQLException ex) {
            throw new PersistenciaException("No se pudo consultar por paramédicos.", ex);
        }
    }

    @Override
    public Paramedico consultarPorId(Integer id) {
        try (PreparedStatement stmt = realizarConexionJDBC().prepareStatement(
                "SELECT id, dni, nombre, telefono FROM profesional_de_la_salud WHERE id = ? AND tipo = ?")) {
            stmt.setInt(1, id);
            stmt.setString(2, "P");

            try (ResultSet rs = stmt.executeQuery()) {
                rs.beforeFirst();

                if (rs.next()) {
                    return tratarResultSetDeConsulta(rs);
                }
            }
        } catch (SQLException ex) {
            throw new PersistenciaException("No se pudo consultar por paramédico.", ex);
        }
        return null;
    }

    protected Paramedico tratarResultSetDeConsulta(ResultSet rs) throws SQLException {
        return new Paramedico(
                rs.getInt("id"),
                rs.getString("dni"),
                rs.getString("nombre"),
                rs.getString("telefono"));
    }

    @Override
    public int crear(Paramedico o) {
        try (PreparedStatement stmt = realizarConexionJDBC().prepareStatement(
                "INSERT INTO profesional_de_la_salud (tipo, dni, nombre, telefono) VALUES ('P', ?, ?, ?)",
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
            throw new PersistenciaException("No se pudo insertar paramédico.", ex);
        }
    }

    @Override
    public int actualizar(Paramedico o) {
        try (PreparedStatement stmt = realizarConexionJDBC().prepareStatement(
                "UPDATE profesional_de_la_salud SET dni = ?, nombre = ?, telefono = ? WHERE id = ? AND tipo = ?")) {
            stmt.setString(1, o.getDni());
            stmt.setString(2, o.getNombre());
            stmt.setString(3, o.getTelefono());
            stmt.setInt(4, o.getId());
            stmt.setString(5, "P");
            return stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new PersistenciaException("No se pudo modificar paramédico.", ex);
        }
    }

    @Override
    public int eliminar(Integer id) {
        try (PreparedStatement stmt = realizarConexionJDBC().prepareStatement(
                "DELETE FROM profesional_de_la_salud WHERE id = ? AND tipo = ?")) {
            stmt.setInt(1, id);
            stmt.setString(2, "P");
            return stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new PersistenciaException("No se pudo eliminar paramédico.", ex);
        }
    }
}
