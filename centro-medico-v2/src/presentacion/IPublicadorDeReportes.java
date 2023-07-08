package presentacion;

/**
 * Será encargada de publicar el contenido de los reportes oficiales del centro médico.
 */
public interface IPublicadorDeReportes {
    /**
     * Publica el contenido del reporte pasado por parámetro.
     * @param reporte El contenido del reporte a publicar
     */
    void publicarReporte(String reporte);
}
