package no.nav.tpconfig.server;

import io.undertow.util.StatusCodes;
import no.nav.tpconfig.domain.TestTpConfig;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.*;

import javax.servlet.ServletException;

import static org.junit.Assert.assertEquals;

public class ServerIT {

    private static final String LOCALHOST = "localhost";
    private static final int PORT = 8080;
    private static final String SERVICE_ACCOUNT_ENDPOINT_URL = "http://localhost:8080/serviceaccount/";
    private static final String IS_READY_ENDPOINT_URL = "http://localhost:8080/isAlive";
    private static final String IS_ALIVE_ENDPOINT_URL = "http://localhost:8080/isReady";
    private static final String METRICS_ENDPOINT_URL = "http://localhost:8080/metrics";
    private static final String TP_NUMBER_FOR_SPK = "3010";
    private static final String SERVICEACCOUNT_FOR_SPK = "srvElsam_SPK";
    private static final String NON_EXISTING_TP_NUMBER = "999999";
    private static final String NO_TP_ORDNING_FOUND_FOR_TP_NR = "No serviceaccount found for TP-nr: ";
    private static final String NON_EXISTING_PATH = "http://localhost:8080/pathdoesnotexist/";

    private static Server server;
    private static OkHttpClient client;

    @Before
    public void setUp() throws ServletException {
        TestTpConfig testConfig = new TestTpConfig();
        testConfig.addTestConfig(TP_NUMBER_FOR_SPK, SERVICEACCOUNT_FOR_SPK);
        server = new Server(LOCALHOST, PORT, new ServiceAccount(testConfig));
        server.run();
        client = new OkHttpClient();
    }

    @After
    public void tearDown() {
        server.stop();
    }

    @Test
    public void tpNumberToServiceAccount_returns_serviceaccount_for_SPK_when_correct_tpnr_is_provided() throws Exception {
        Request request = new Request.Builder().url(SERVICE_ACCOUNT_ENDPOINT_URL + TP_NUMBER_FOR_SPK).build();
        Response response = client.newCall(request).execute();

        assertEquals(SERVICEACCOUNT_FOR_SPK, response.body().string());
        assertEquals(StatusCodes.OK, response.code());
    }

    @Test
    public void tpNumberToServiceAccount_returns_error_message_when_non_existing_tpnr_is_provided() throws Exception {
        Request request = new Request.Builder().url(SERVICE_ACCOUNT_ENDPOINT_URL + NON_EXISTING_TP_NUMBER).build();
        Response response = client.newCall(request).execute();

        assertEquals(NO_TP_ORDNING_FOUND_FOR_TP_NR + NON_EXISTING_TP_NUMBER, response.body().string());
        assertEquals(StatusCodes.NOT_FOUND, response.code());
    }

    @Test
    public void tpNumberToServiceAccount_returns_NOT_FOUND_when_non_existing_path_is_called() throws Exception {
        Request request = new Request.Builder().url(NON_EXISTING_PATH).build();
        Response response = client.newCall(request).execute();

        assertEquals(StatusCodes.NOT_FOUND, response.code());
    }

    @Test
    public void isAlive_endpoint_returns_OK() throws Exception {
        Request request = new Request.Builder().url(IS_ALIVE_ENDPOINT_URL).build();
        Response response = client.newCall(request).execute();

        assertEquals(StatusCodes.OK, response.code());
    }

    @Test
    public void isReady_endpoint_returns_OK() throws Exception {
        Request request = new Request.Builder().url(IS_READY_ENDPOINT_URL).build();
        Response response = client.newCall(request).execute();

        assertEquals(StatusCodes.OK, response.code());
    }

    @Test
    public void metrics_endpoint_returns_OK() throws Exception {
        Request request = new Request.Builder().url(METRICS_ENDPOINT_URL).build();
        Response response = client.newCall(request).execute();

        assertEquals(StatusCodes.OK, response.code());
    }
}
