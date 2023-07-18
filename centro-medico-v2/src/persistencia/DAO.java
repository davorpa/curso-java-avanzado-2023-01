package persistencia;

import java.util.List;

/**
 * Define el comportamiento de un Data Access Object.
 *
 * @param <E> el tipo de dato que identifica los registros de entidad
 * @param <ID> el tipo de dato para los ID de dichas entidades
 */
public interface DAO<E, ID> extends AutoCloseable {

    /**
     * Obtiene todos los registros.
     *
     * @return una lista no modificable con todos los registros
     */
    List<E> consultarTodos();

    /**
     * Obtiene un determinado registro.
     *
     * @param id el identificador por el que consultar
     * @return el registro que corresponde con dicho id, o {@code null} si no se encuentra.
     */
    E consultarPorId(ID id);

    /**
     * Crea un determinado registro nuevo.
     * @param o el detalle del registro a crear
     * @return el número de filas afectadas por dicha operación
     */
    int crear(E o);

    /**
     * Actualiza un determinado registro ya existente.
     * @param o el detalle del registro a modificar
     * @return el número de filas afectadas por dicha operación
     */
    int actualizar(E o);

    /**
     * Elimina un determinado registro ya existente.
     * @param id el identificador por el que consultar
     * @return el número de filas afectadas por dicha operación
     */
    int eliminar(ID id);
}
