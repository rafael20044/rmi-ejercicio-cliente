package dev.rafael.propina.rmi.lib;

/**
 * Interfaz remota para el cálculo de propina.
 * 
 * @author Rafael Barragan Acevedo
 */
public interface IRemotaCalculoPropina {
    public DatosPropina calcularPropina(DatosPropina datos);
}
