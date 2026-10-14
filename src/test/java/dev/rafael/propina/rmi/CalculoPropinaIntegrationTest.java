package dev.rafael.propina.rmi;

import dev.rafael.propina.rmi.lib.DatosPropina;
import dev.rafael.propina.rmi.lib.IRemotaCalculoPropina;
import net.sf.lipermi.handler.CallHandler;
import net.sf.lipermi.net.Client;
import net.sf.lipermi.net.Server;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Prueba de integración del Cliente invocando el servicio remoto LipeRMI.
 * 
 * @author Rafael Barragan Acevedo
 */
public class CalculoPropinaIntegrationTest {

    private static Server testServer;
    private static final int TEST_PORT = 9088;

    public static class ServidorPropinaMock implements IRemotaCalculoPropina {
        @Override
        public DatosPropina calcularPropina(DatosPropina datos) {
            float propina = datos.getValorCuenta() * (datos.getPorcentajePropina() / 100.0f);
            float total = datos.getValorCuenta() + propina;
            datos.setValorPropina(propina);
            datos.setTotalPagar(total);
            datos.setMensaje("Cálculo exitoso");
            return datos;
        }
    }

    @BeforeAll
    public static void startServer() throws Exception {
        CallHandler serverHandler = new CallHandler();
        ServidorPropinaMock mockService = new ServidorPropinaMock();
        serverHandler.registerGlobal(IRemotaCalculoPropina.class, mockService);

        testServer = new Server();
        testServer.bind(TEST_PORT, serverHandler);
    }

    @AfterAll
    public static void stopServer() {
        if (testServer != null) {
            testServer.close();
        }
    }

    @Test
    public void testRemoteInvocation() throws Exception {
        CallHandler clientHandler = new CallHandler();
        Client client = new Client("localhost", TEST_PORT, clientHandler);

        IRemotaCalculoPropina service = (IRemotaCalculoPropina) client.getGlobal(IRemotaCalculoPropina.class);
        assertNotNull(service, "El servicio remoto no debe ser null");

        DatosPropina request = new DatosPropina(120000.0f, 15.0f);
        DatosPropina response = service.calcularPropina(request);

        assertNotNull(response);
        assertEquals(18000.0f, response.getValorPropina(), 0.01f);
        assertEquals(138000.0f, response.getTotalPagar(), 0.01f);
        assertEquals("Cálculo exitoso", response.getMensaje());

        client.close();
    }
}
