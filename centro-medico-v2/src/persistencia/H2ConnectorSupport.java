package persistencia;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class H2ConnectorSupport implements AutoCloseable {

    protected final Logger logger = Logger.getLogger(getClass());

    private static volatile boolean driverJDBCcargado = false;
    private static volatile boolean esquemaDDBBcargado = false;


    private Connection conexionJDBC = null;


    public H2ConnectorSupport() {
        registrarDriverJDBC();
        realizarConexionJDBC();
    }

    protected void registrarDriverJDBC() {
        // Registrar el driver JDBC una sola vez
        if (driverJDBCcargado) return;
        synchronized (H2ConnectorSupport.class) {
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


    protected synchronized Connection realizarConexionJDBC() {
        // Tener una instancia de conexión por DAO pero que inicialize la BBDD una sola vez.
        try {
            if (esquemaDDBBcargado && conexionJDBC != null) return conexionJDBC;
            synchronized (H2ConnectorSupport.class) {
                if (esquemaDDBBcargado) {
                    if (conexionJDBC != null) return conexionJDBC;
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
            return conexionJDBC;
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
}
