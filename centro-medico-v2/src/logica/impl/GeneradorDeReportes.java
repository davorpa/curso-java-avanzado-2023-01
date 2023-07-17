package logica.impl;

import logica.IGeneradorDeReportes;
import modelo.impl.Medico;
import modelo.impl.Paciente;
import persistencia.IObjetoDeAcessoADatos;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class GeneradorDeReportes implements IGeneradorDeReportes {

    private final IObjetoDeAcessoADatos baseDeDatos;

    public GeneradorDeReportes(IObjetoDeAcessoADatos baseDeDatos) {
        this.baseDeDatos = baseDeDatos;
    }

    @Override
    public String generarReporteDeMedicos() {
        List<Medico> medicos = baseDeDatos.consultarMedicos();

        return this.aplicarPlantillaDeReporteDeMedicos(medicos);
    }

//    @Override
//    public String generarReporteDeMedicosPorEspecialidad(String especialidad) {
//        //Implementando este método usando programación imperativa
//        List<Medico> medicos = baseDeDatos.consultarMedicos();
//        List<Medico> medicosFiltrados = new ArrayList<>();
//
//        for (Medico m : medicos){
//            if (Objects.equals(m.getEspecialidad(), especialidad)) {
//                medicosFiltrados.add(m);
//            }
//        }
//
//       return this.aplicarPlantillaDeReporteDeMedicos(medicosFiltrados);
//    }

    @Override
    public String generarReporteDeMedicosPorEspecialidad(final String especialidad) {
        // Implementando este método usando programación funcional
        List<Medico> medicos = baseDeDatos.consultarMedicos();
        List<Medico> medicosFiltrados = medicos
                .stream()
                .filter((Medico m) -> Objects.equals(m.getEspecialidad(), especialidad))
                .collect(Collectors.toList());

        return this.aplicarPlantillaDeReporteDeMedicos(medicosFiltrados);
    }

    @Override
    public String generarReporteDePacientes() {
        List<Paciente> pacientes = baseDeDatos.consultarPacientes();
        return aplicarPlantillaDeReporteDePacientes(pacientes);
    }

    @Override
    public String generarReporteDePacientesPorGrupoSanguineo(final String grupoSanguineo) {
        List<Paciente> pacientes = baseDeDatos.consultarPacientesGrupoSanguineo(grupoSanguineo);
        return aplicarPlantillaDeReporteDePacientes(pacientes);
    }

    @Override
    public String generarReporteMediasDeEdadDePacienteAgrupadoPorGrupoSanguineo() {
        final LocalDate now = LocalDate.now();
        Map<String, Double> datos = baseDeDatos.consultarPacientes()
                .stream()
                .collect(Collectors.groupingBy(
                        // Agrupamos por grupo sanguíneo
                        Paciente::getGrupoSanguineo,
                        // En un TreeMap para ordenar las claves por su orden natural
                        () -> new TreeMap<>(String.CASE_INSENSITIVE_ORDER),
                        // ejecutando la función de grupo AVG sobre el atributo edad
                        Collectors.averagingDouble(p -> p.getEdad(now))));
        StringBuilder reporte = new StringBuilder(
                "-- LISTADO DE EDADES PROMEDIO DE PACIENTES PARA CADA GRUPO SANGUÍNEO --\n")
                .append("-----------------------------------------------------------------------\n")
                .append("| Grupo sanguíneo |  Media de edad  |\n");

        for (Map.Entry<String, Double> entry : datos.entrySet()) {
            reporte.append(String.format("| %15s | %15.2f |%n", entry.getKey(), entry.getValue()));
        }

        reporte.append("-----------------------------------------------------------------------\n");

        return reporte.toString();
    }

    private String aplicarPlantillaDeReporteDeMedicos(final Collection<Medico> datos) {
        StringBuilder reporte = new StringBuilder("-- LISTADO DE MÉDICOS --\n")
                .append("------------------\n")
                .append("|   Nombre   | Especialidad |\n");

        for (Medico m : datos) {
            reporte.append("| ")
                    .append(m.getNombre()).append(" | ")
                    .append(m.getEspecialidad()).append(" |\n");
        }

        reporte.append("---------------------------------\n");

        return reporte.toString();
    }

    protected String aplicarPlantillaDeReporteDePacientes(final Collection<Paciente> datos) {
        StringBuilder reporte = new StringBuilder("-- LISTADO DE PACIENTES --\n")
                .append("------------------\n")
                .append("|   Nombre   |  Teléfono  | Grupo Sanguíneo |\n");

        for (Paciente p : datos) {
            reporte.append("| ")
                    .append(p.getNombre()).append(" | ")
                    .append(p.getTelefono()).append(" | ")
                    .append(p.getGrupoSanguineo()).append(" |\n");
        }

        reporte.append("---------------------------------\n");

        return reporte.toString();
    }
}
