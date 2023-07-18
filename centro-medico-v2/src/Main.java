import logica.IGeneradorDeReportes;
import logica.impl.GeneradorDeReportes;
import modelo.impl.Enfermera;
import modelo.impl.Medico;
import modelo.impl.Paciente;
import modelo.impl.Paramedico;
import persistencia.IMedicoDAO;
import persistencia.IObjetoDeAcessoADatos;
import persistencia.IPacienteDAO;
import persistencia.daoimpl.MedicoDAOEnH2;
import persistencia.daoimpl.PacienteDAOEnH2;
import persistencia.impl.ObjetoDeAccesoADatos;
import persistencia.jdbc.ObjetoDeAccesoADatosEnH2;
import presentacion.IPublicadorDeReportes;
import presentacion.impl.PublicadorDeReportesEnPantalla;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) throws Exception {
        probarBaseDeDatosConMedicosRepetidos();
        probarBaseDeDatosConPacientesRepetidos();
        probarBaseDeDatosConEnfermerasRepetidas();
        probarBaseDeDatosConParamedicosRepetidos();
        probarReportesDeMedicos();
        probarReportesDePacientes();
        probarPublicarEnPantallaReporteMediasDeEdadDePacienteAgrupadoPorGrupoSanguineo();
        probarAccesoADatosH2ConPacientes();
        probarAccesoADatosH2ConMedicos();
        probarAccesoADatosH2ConEnfermeras();
        probarAccesoADatosH2ConParamedicos();
        probarDaoH2ConPacientes();
        probarDaoH2ConMedicos();
    }

    public static void probarBaseDeDatosConMedicosRepetidos() {
        //En esta prueba esperamos que no se puedan guardar registros duplicados.
        IObjetoDeAcessoADatos baseDeDatos = new ObjetoDeAccesoADatos();
        Medico m = new Medico(123, "123", "Bla", "321", Medico.ESPECIALIDAD_GENERALISTA);
        Medico m2 = new Medico(123, "123", "Bla", "321", Medico.ESPECIALIDAD_GENERALISTA);

        System.out.println(baseDeDatos.guardarMedico(m));
        System.out.println(baseDeDatos.guardarMedico(m2));
    }

    public static void probarBaseDeDatosConPacientesRepetidos() {
        IObjetoDeAcessoADatos baseDeDatos = new ObjetoDeAccesoADatos();
        Paciente paciente1 = new Paciente("123", "Paciente", "555-555-555",
                LocalDate.of(2002, 12, 9),
                "A+");
        Paciente paciente2 = new Paciente("123", "Paciente", "555-555-555",
                LocalDate.of(2002, 12, 9),
                "A+");

        System.out.println(baseDeDatos.guardarPaciente(paciente1));
        System.out.println(baseDeDatos.guardarPaciente(paciente2));
    }

    public static void probarBaseDeDatosConEnfermerasRepetidas() {
        //En esta prueba esperamos que no se puedan guardar registros duplicados.
        IObjetoDeAcessoADatos baseDeDatos = new ObjetoDeAccesoADatos();
        Enfermera enfermera1 = new Enfermera(456,"456", "Enfermera", "655-555-555");
        Enfermera enfermera2 = new Enfermera(456, "456", "Enfermera", "655-555-555");

        System.out.println(baseDeDatos.guardarEnfermera(enfermera1));
        System.out.println(baseDeDatos.guardarEnfermera(enfermera2));
    }

    public static void probarBaseDeDatosConParamedicosRepetidos() {
        //En esta prueba esperamos que no se puedan guardar registros duplicados.
        IObjetoDeAcessoADatos baseDeDatos = new ObjetoDeAccesoADatos();
        Paramedico paramedico1 = new Paramedico(789, "789", "Paramédico", "555-655-555");
        Paramedico paramedico2 = new Paramedico(789, "789", "Paramédico", "555-655-555");

        System.out.println(baseDeDatos.guardarParamedico(paramedico1));
        System.out.println(baseDeDatos.guardarParamedico(paramedico2));
    }

    public static void probarReportesDeMedicos() {
        IObjetoDeAcessoADatos baseDeDatos = new ObjetoDeAccesoADatos();
        IGeneradorDeReportes generadorDeReportes = new GeneradorDeReportes(baseDeDatos);
        Medico m = new Medico(123, "123", "Médico 1", "321", Medico.ESPECIALIDAD_GENERALISTA);
        Medico m2 = new Medico(124, "124", "Médico 2", "322", Medico.ESPECIALIDAD_GENERALISTA);
        Medico m3 = new Medico(125, "125", "Médico 3", "323", Medico.ESPECIALIDAD_PEDIATRIA);

        baseDeDatos.guardarMedico(m);
        baseDeDatos.guardarMedico(m2);
        baseDeDatos.guardarMedico(m3);

        System.out.println(generadorDeReportes.generarReporteDeMedicos());

        System.out.println(generadorDeReportes.generarReporteDeMedicosPorEspecialidad(Medico.ESPECIALIDAD_GENERALISTA));
    }

    public static void probarReportesDePacientes() {
        IObjetoDeAcessoADatos baseDeDatos = new ObjetoDeAccesoADatos();
        IGeneradorDeReportes generadorDeReportes = new GeneradorDeReportes(baseDeDatos);

        Paciente p1 = new Paciente("123", "Paciente 1", "321",
                LocalDate.of(1983, 8, 18),
                "A+");
        Paciente p2 = new Paciente("124", "Paciente 2", "322",
                LocalDate.of(1992, 4, 11),
                "A+");
        Paciente p3 = new Paciente("125", "Paciente 3", "323",
                LocalDate.of(1948, 3, 2),
                "A-");

        baseDeDatos.guardarPaciente(p1);
        baseDeDatos.guardarPaciente(p2);
        baseDeDatos.guardarPaciente(p3);

        System.out.println(generadorDeReportes.generarReporteDePacientes());

        System.out.println(generadorDeReportes.generarReporteDePacientesPorGrupoSanguineo("A+"));
    }

    public static void probarPublicarEnPantallaReporteMediasDeEdadDePacienteAgrupadoPorGrupoSanguineo() {
        IObjetoDeAcessoADatos baseDeDatos = new ObjetoDeAccesoADatos();
        IGeneradorDeReportes generadorDeReportes = new GeneradorDeReportes(baseDeDatos);
        IPublicadorDeReportes publicadorDeReportes = new PublicadorDeReportesEnPantalla();

        baseDeDatos.guardarPaciente(new Paciente("123", "Paciente 1", "555-789-123",
                LocalDate.of(1983, 8, 18),
                "A+"));
        baseDeDatos.guardarPaciente(new Paciente("456", "Paciente 2", "555-789-789",
                LocalDate.of(1982, 2, 15),
                "A-"));
        baseDeDatos.guardarPaciente(new Paciente("789", "Paciente 3", "555-789-555",
                LocalDate.of(1992, 4, 11),
                "A+"));
        baseDeDatos.guardarPaciente(new Paciente("987", "Paciente 4", "555-789-423",
                LocalDate.of(1972, 11, 11),
                "A+"));
        baseDeDatos.guardarPaciente(new Paciente("654", "Paciente 5", "555-789-012",
                LocalDate.of(2002, 12, 9),
                "A-"));
        baseDeDatos.guardarPaciente(new Paciente("321", "Paciente 6", "555-789-123",
                LocalDate.of(1985, 5, 13),
                "B+"));
        baseDeDatos.guardarPaciente(new Paciente("753", "Paciente 7", "555-789-057",
                LocalDate.of(1948, 3, 2),
                "B+"));
        baseDeDatos.guardarPaciente(new Paciente("951", "Paciente 8", "555-789-570",
                LocalDate.of(1960, 6, 30),
                "AB-"));

        System.out.printf("Hay %d pacientes en el sistema.%n", baseDeDatos.consultarPacientes().stream().count());
        baseDeDatos.consultarPacientes().forEach(System.out::println);

        final String reporte = generadorDeReportes.generarReporteMediasDeEdadDePacienteAgrupadoPorGrupoSanguineo();
        publicadorDeReportes.publicarReporte(reporte);
    }

    public static void probarAccesoADatosH2ConPacientes() throws Exception {
        try (IObjetoDeAcessoADatos baseDeDatos = new ObjetoDeAccesoADatosEnH2()) {
            Paciente p1 = new Paciente("123", "Paciente 1", "321",
                    LocalDate.of(1983, 8, 18),
                    "A+");
            Paciente p2 = new Paciente("124", "Paciente 2", "322",
                    LocalDate.of(1992, 4, 11),
                    "A+");
            Paciente p3 = new Paciente("125", "Paciente 3", "323",
                    LocalDate.of(1948, 3, 2),
                    "A-");

            baseDeDatos.guardarPaciente(p1);
            baseDeDatos.guardarPaciente(p2);
            baseDeDatos.guardarPaciente(p3);

            baseDeDatos.consultarPacientes().forEach(System.out::println);
        }
    }

    public static void probarAccesoADatosH2ConMedicos() throws Exception {
        try (IObjetoDeAcessoADatos baseDeDatos = new ObjetoDeAccesoADatosEnH2()) {
            Medico m = new Medico(123,"123", "Médico 1", "321", Medico.ESPECIALIDAD_GENERALISTA);
            Medico m2 = new Medico(124, "124", "Médico 2", "322", Medico.ESPECIALIDAD_GENERALISTA);
            Medico m3 = new Medico(125, "125", "Médico 3", "323", Medico.ESPECIALIDAD_PEDIATRIA);

            baseDeDatos.guardarMedico(m);
            baseDeDatos.guardarMedico(m2);
            baseDeDatos.guardarMedico(m3);

            baseDeDatos.consultarMedicos().forEach(System.out::println);
        }
    }

    public static void probarAccesoADatosH2ConEnfermeras() throws Exception {
        try (IObjetoDeAcessoADatos baseDeDatos = new ObjetoDeAccesoADatosEnH2()) {
            Enfermera e = new Enfermera(223, "223", "Enfermera 1", "351");
            Enfermera e2 = new Enfermera(224, "224", "Enfermera 2", "352");
            Enfermera e3 = new Enfermera(225, "225", "Enfermera 3", "353");

            baseDeDatos.guardarEnfermera(e);
            baseDeDatos.guardarEnfermera(e2);
            baseDeDatos.guardarEnfermera(e3);

            baseDeDatos.consultarEnfermeras().forEach(System.out::println);
        }
    }

    public static void probarAccesoADatosH2ConParamedicos() throws Exception {
        try (IObjetoDeAcessoADatos baseDeDatos = new ObjetoDeAccesoADatosEnH2()) {
            Paramedico pm = new Paramedico(323, "323", "Paramédico 1", "357");
            Paramedico pm2 = new Paramedico(324, "324", "Paramédico 2", "356");
            Paramedico pm3 = new Paramedico(325, "325", "Paramédico 3", "355");

            baseDeDatos.guardarParamedico(pm);
            baseDeDatos.guardarParamedico(pm2);
            baseDeDatos.guardarParamedico(pm3);

            baseDeDatos.consultarParamedicos().forEach(System.out::println);
        }
    }

    public static void probarDaoH2ConPacientes() throws Exception {
        try (IPacienteDAO dao = new PacienteDAOEnH2()) {

            dao.consultarTodos().forEach(System.out::println);

            System.out.println(dao.consultarPorId("123A"));
            System.out.println(dao.consultarPorId("123"));

            Paciente p1 = new Paciente("123A", "Paciente 4", "6545",
                    LocalDate.of(1987, 5, 29),
                    "0-");
            System.out.println(dao.crear(p1));
            System.out.println(dao.consultarPorId("123A"));

            p1.setGrupoSanguineo("B+");
            System.out.println(dao.actualizar(p1));
            System.out.println(dao.consultarPorId("123A"));

            System.out.println(dao.eliminar("123A"));
            System.out.println(dao.consultarPorId("123A"));

            dao.consultarTodosPorNombre("3").forEach(System.out::println);

            dao.consultarTodosPorTelefono("322").forEach(System.out::println);
        }
    }

    public static void probarDaoH2ConMedicos() throws Exception {
        try (IMedicoDAO dao = new MedicoDAOEnH2()) {

            dao.consultarTodos().forEach(System.out::println);

            System.out.println(dao.consultarPorId(666));
            System.out.println(dao.consultarPorId(123));

            Medico m1 = new Medico(666, "123A", "Médico 4", "965", Medico.ESPECIALIDAD_PEDIATRIA);
            System.out.println(dao.crear(m1));
            System.out.println(dao.consultarPorId(666));

            m1.setNombre("Patricia Mendoza");
            System.out.println(dao.actualizar(m1));
            System.out.println(dao.consultarPorId(666));

            System.out.println(dao.eliminar(666));
            System.out.println(dao.consultarPorId(666));
        }
    }
}
