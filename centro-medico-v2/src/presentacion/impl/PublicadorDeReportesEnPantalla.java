package presentacion.impl;

import presentacion.IPublicadorDeReportes;

/**
 * Se encarga de publicar los reportes en pantalla.
 */
public class PublicadorDeReportesEnPantalla implements IPublicadorDeReportes {
    @Override
    public void publicarReporte(final String reporte) {
        System.out.println(reporte);
    }
}
