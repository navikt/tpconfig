package no.nav.tpconfig.server;

import io.undertow.util.StatusCodes;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ConfigServerTest {

    private static ConfigServer server;
    private static OkHttpClient client;

    @BeforeClass
    public static void setUp() {
        server = new ConfigServer("localhost", 8080);
        server.runServer();
        client = new OkHttpClient();
    }

    @AfterClass
    public static void tearDown() {
        server.stopServer();
    }

    @Test
    public void tpNumberServiceAccount_ReturnsSPK_When_CorrectTpNrIsProvided() throws Exception {
        String tpNumberForSpk = "3010";
        String testUrl = "http://localhost:8080/serviceaccount/" + tpNumberForSpk;

        Request request = new Request.Builder().url(testUrl).build();
        Response response = client.newCall(request).execute();

        assertEquals("srvElsam_SPK", response.body().string());
        assertEquals(StatusCodes.OK, response.code());
    }

    @Test
    public void tpNumberServiceAccount_Returns404_When_NonExistingTpNrIsProvided() throws Exception {
        String nonExistingTpNumber = "999999";
        String testUrl = "http://localhost:8080/serviceaccount/" + nonExistingTpNumber;

        Request request = new Request.Builder().url(testUrl).build();
        Response response = client.newCall(request).execute();

        String expectedErrorMessage = "No TP-ordning found for TP-nr: " + nonExistingTpNumber;
        assertEquals(expectedErrorMessage, response.body().string());
        assertEquals(StatusCodes.NOT_FOUND, response.code());
    }

    @Test
    public void tpNumberServiceAccount_Returns400_When_MangledUrlIsUsed() throws Exception {
        String tpNumber= "3010";
        String testUrl = "http://localhost:8080/serviceaccoun/" + tpNumber;

        Request request = new Request.Builder().url(testUrl).build();
        Response response = client.newCall(request).execute();

        assertEquals(StatusCodes.NOT_FOUND, response.code());
    }

}
