package dev.rafael.propina.rmi.lib;

import java.io.Serializable;

/**
 * Clase para transportar los datos de la cuenta y el cálculo de propina.
 * 
 * @author Rafael Barragan Acevedo
 */
public class DatosPropina implements Serializable {

    private static final long serialVersionUID = 1L;

    private float valorCuenta;
    private float porcentajePropina;
    private float valorPropina;
    private float totalPagar;
    private String mensaje;

    public DatosPropina() {
    }

    public DatosPropina(float valorCuenta, float porcentajePropina) {
        this.valorCuenta = valorCuenta;
        this.porcentajePropina = porcentajePropina;
    }

    public float getValorCuenta() {
        return valorCuenta;
    }

    public void setValorCuenta(float valorCuenta) {
        this.valorCuenta = valorCuenta;
    }

    public float getPorcentajePropina() {
        return porcentajePropina;
    }

    public void setPorcentajePropina(float porcentajePropina) {
        this.porcentajePropina = porcentajePropina;
    }

    public float getValorPropina() {
        return valorPropina;
    }

    public void setValorPropina(float valorPropina) {
        this.valorPropina = valorPropina;
    }

    public float getTotalPagar() {
        return totalPagar;
    }

    public void setTotalPagar(float totalPagar) {
        this.totalPagar = totalPagar;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
