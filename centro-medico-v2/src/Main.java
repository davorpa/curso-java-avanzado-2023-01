import logica.IGeneradorDeReportes;
import logica.impl.GeneradorDeReportes;
import modelo.impl.Enfermera;
import modelo.impl.Medico;
import modelo.impl.Paciente;
import modelo.impl.Paramedico;
import persistencia.IObjetoDeAcessoADatos;
import persistencia.impl.ObjetoDeAccesoADatos;

public class Main {
    public static void main(String[] args) {
        probarBaseDeDatosConMedicosRepetidos();
        probarBaseDeDatosConPacientesRepetidos();
        probarBaseDeDatosConEnfermerasRepetidas();
        probarBaseDeDatosConParamedicosRepetidos();
        probarReportesDeMedicos();
    }

    public static void probarBaseDeDatosConMedicosRepetidos() {
        //En esta prueba esperamos que no se puedan guardar registros duplicados.
        IObjetoDeAcessoADatos baseDeDatos = new ObjetoDeAccesoADatos();
        Medico m = new Medico("123", "Bla", "321", Medico.ESPECIALIDAD_GENERALISTA);
        Medico m2 = new Medico("123", "Bla", "321", Medico.ESPECIALIDAD_GENERALISTA);

        System.out.println(baseDeDatos.guardarMedico(m));
        System.out.println(baseDeDatos.guardarMedico(m2));
    }

    public static void probarBaseDeDatosConPacientesRepetidos() {
        //En esta prueba esperamos que no se puedan guardar registros duplicados.
        IObjetoDeAcessoADatos baseDeDatos = new ObjetoDeAccesoADatos();
        Paciente paciente1 = new Paciente("123", "Paciente", "555-555-555", "A+");
        Paciente paciente2 = new Paciente("123", "Paciente", "555-555-555", "A+");

        System.out.println(baseDeDatos.guardarPaciente(paciente1));
        System.out.println(baseDeDatos.guardarPaciente(paciente2));
    }

    public static void probarBaseDeDatosConEnfermerasRepetidas() {
        //En esta prueba esperamos que no se puedan guardar registros duplicados.
        IObjetoDeAcessoADatos baseDeDatos = new ObjetoDeAccesoADatos();
        Enfermera enfermera1 = new Enfermera("456", "Enfermera", "655-555-555");
        Enfermera enfermera2 = new Enfermera("456", "Enfermera", "655-555-555");

        System.out.println(baseDeDatos.guardarEnfermera(enfermera1));
        System.out.println(baseDeDatos.guardarEnfermera(enfermera2));
    }

    public static void probarBaseDeDatosConParamedicosRepetidos() {
        //En esta prueba esperamos que no se puedan guardar registros duplicados.
        IObjetoDeAcessoADatos baseDeDatos = new ObjetoDeAccesoADatos();
        Paramedico paramedico1 = new Paramedico("789", "Paramédico", "555-655-555");
        Paramedico paramedico2 = new Paramedico("789", "Paramédico", "555-655-555");

        System.out.println(baseDeDatos.guardarParamedico(paramedico1));
        System.out.println(baseDeDatos.guardarParamedico(paramedico2));
    }

    public static void probarReportesDeMedicos() {
        IObjetoDeAcessoADatos baseDeDatos = new ObjetoDeAccesoADatos();
        IGeneradorDeReportes generadorDeReportes = new GeneradorDeReportes(baseDeDatos);
        Medico m = new Medico("123", "Médico 1", "321", Medico.ESPECIALIDAD_GENERALISTA);
        Medico m2 = new Medico("124", "Médico 2", "322", Medico.ESPECIALIDAD_GENERALISTA);
        Medico m3 = new Medico("125", "Médico 3", "323", Medico.ESPECIALIDAD_PEDIATRIA);

        baseDeDatos.guardarMedico(m);
        baseDeDatos.guardarMedico(m2);
        baseDeDatos.guardarMedico(m3);

        System.out.println(generadorDeReportes.generarReporteDeMedicos());

        System.out.println(generadorDeReportes.generarReporteDeMedicosPorEspecialidad(Medico.ESPECIALIDAD_GENERALISTA));
    }
}
