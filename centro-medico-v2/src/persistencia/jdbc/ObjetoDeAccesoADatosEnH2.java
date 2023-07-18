package persistencia.jdbc;

import modelo.impl.Enfermera;
import modelo.impl.Medico;
import modelo.impl.Paciente;
import modelo.impl.Paramedico;
import persistencia.H2ConnectorSupport;
import persistencia.IObjetoDeAcessoADatos;
import persistencia.PersistenciaException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class ObjetoDeAccesoADatosEnH2 extends H2ConnectorSupport implements IObjetoDeAcessoADatos {

    @Override
    public boolean guardarMedico(final Medico m) {
        try (PreparedStatement stmt = realizarConexionJDBC().prepareStatement(
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
        try (PreparedStatement stmt = realizarConexionJDBC().prepareStatement(
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
        try (PreparedStatement stmt = realizarConexionJDBC().prepareStatement(
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
        try (PreparedStatement stmt = realizarConexionJDBC().prepareStatement(
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
        try (PreparedStatement stmt = realizarConexionJDBC().prepareStatement(
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
        try (PreparedStatement stmt = realizarConexionJDBC().prepareStatement(
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
        try (PreparedStatement stmt = realizarConexionJDBC().prepareStatement(
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
        try (PreparedStatement stmt = realizarConexionJDBC().prepareStatement(
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
