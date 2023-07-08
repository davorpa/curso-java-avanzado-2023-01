package logica;

/**
 * Será encargada de generar los reportes oficiales del centro m&eacute;dico.
 */
public interface IGeneradorDeReportes {

    /**
     * Genera un reporte con los datos de todos los m&eacute;dicos.
     * @return El reporte a ser mostrado en forma de cadena de caracteres.
     */
    String generarReporteDeMedicos();

    /**
     * Genera un reporte con los datos de los m&eacute;dicos acorde a la <b>especialidad</b> entregada.
     * @param especialidad Describe el código de la especialidad que será usado como criterio de filtrado.
     * @return El reporte a ser mostrado en forma de cadena de caracteres.
     */
    String generarReporteDeMedicosPorEspecialidad(String especialidad);

    /**
     * Genera un reporte con los datos de todos los pacientes.
     * @return El reporte a ser mostrado en forma de cadena de caracteres.
     */
    String generarReporteDePacientes();

    /**
     * Genera un reporte con los datos de los pacientes acorde al <b>grupo sanguíneo</b> indicado.
     * @param grupoSanguineo Describe el grupo sanguíneo que será usado como criterio de filtrado.
     * @return El reporte a ser mostrado en forma de cadena de caracteres.
     */
    String generarReporteDePacientesPorGrupoSanguineo(String grupoSanguineo);

    /**
     * Genera un reporte con las medias de edad de los pacientes agrupadas por grupo sanguíneo.
     * @return El reporte a ser mostrado en forma de cadena de caracteres.
     */
    String generarReporteMediasDeEdadDePacienteAgrupadoPorGrupoSanguineo();
}
