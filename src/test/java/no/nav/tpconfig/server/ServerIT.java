package no.nav.tpconfig.server;

import io.undertow.util.StatusCodes;
import no.nav.tpconfig.domain.TestTpConfig;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;
import org.junit.*;

import javax.servlet.ServletException;

import java.io.IOException;

import static no.nav.tpconfig.server.EndpointFactory.*;
import static org.junit.Assert.assertEquals;

public class ServerIT {

    private static final int PORT = 8080;
    private static final String LOCALHOST = "localhost";
    private static final String SERVICE_ACCOUNT_ENDPOINT_URL = "http://localhost:8080/serviceaccount/";
    private static final String ORGANISATION_ENDPOINT_URL = "http://localhost:8080/organisation/";
    private static final String TPLEVERANDOER_BY_TPNR_ENDPOINT_URL = "http://localhost:8080/tpleverandoer/";
    private static final String TPLEVERANDOER_BY_TSSNR_ENDPOINT_URL = "http://localhost:8080/tpleverandoer/tssnr/";
    private static final String TSSNR_BY_TPNR_ENDPOINT_URL = "http://localhost:8080/tssnr/";
    private static final String IS_READY_ENDPOINT_URL = "http://localhost:8080/isAlive";
    private static final String IS_ALIVE_ENDPOINT_URL = "http://localhost:8080/isReady";
    private static final String METRICS_ENDPOINT_URL = "http://localhost:8080/metrics";
    private static final String TP_NUMBER_FOR_SPK = "3010";
    private static final String TSS_NUMBER_FOR_SPK = "80000470761";
    private static final String ORGNR_FOR_SPK = "982583462";
    private static final String SERVICEACCOUNT_FOR_SPK = "srvElsam_SPK";
    private static final String TPLEVERANDOER_FOR_SPK = "SPK";
    private static final String NON_EXISTING_TP_NUMBER = "999999";
    private static final String NON_EXISTING_TSS_NUMBER = "00";
    private static final String NO_SERVICEACCOUNT_ORDNING_FOUND_FOR_TP_NR = "No serviceaccount found for TP-nr: ";
    private static final String NO_TP_LEVERANDOER_FOUND_FOR_TP_NR = "No TP-account found for TP-nr: ";
    private static final String NO_TP_LEVERANDOER_FOUND_FOR_TSS_NR = "No TP-account found for TSS-nr: ";
    private static final String NO_TSS_NR_FOUND_FOR_TP_NR = "No TSS-nr found for TP-nr: ";
    private static final String NON_EXISTING_PATH = "http://localhost:8080/pathdoesnotexist/";

    private static Server server;
    private static OkHttpClient client;

    @Before
    public void setUp() throws ServletException {
        TestTpConfig testConfig = new TestTpConfig();
        testConfig.addTestConfig(TP_NUMBER_FOR_SPK, SERVICEACCOUNT_FOR_SPK, TPLEVERANDOER_FOR_SPK, TSS_NUMBER_FOR_SPK, ORGNR_FOR_SPK);
        server = new Server(LOCALHOST, PORT,
                createTpNrToTPLeverandoerEndpoint(testConfig),
                createTssNrToTPLeverandoerEndpoint(testConfig),
                createTpNrToTssNrEndpoint(testConfig),
                createServiceAccountEndpoint(testConfig),
                createOrganisationEndpoint(testConfig));
        server.run();
        client = new OkHttpClient();
    }

    @After
    public void tearDown() {
        server.stop();
    }

    @Test
    public void tpNumberToOrganisation_returns_serviceaccount_and_orgnr_for_SPK_when_correct_tpnr_is_provided() throws Exception {
        Request request = new Request.Builder().url(ORGANISATION_ENDPOINT_URL + TP_NUMBER_FOR_SPK).build();
        Response response = client.newCall(request).execute();

        JSONObject jsonResponse = new JSONObject(response.body().string());

        assertEquals(SERVICEACCOUNT_FOR_SPK, jsonResponse.getString("serviceaccount"));
        assertEquals(ORGNR_FOR_SPK, jsonResponse.getString("orgnr"));
        assertEquals(StatusCodes.OK, response.code());
    }

    @Test
    public void tpNumberToServiceAccount_returns_serviceaccount_for_SPK_when_correct_tpnr_is_provided() throws Exception {
        Request request = new Request.Builder().url(SERVICE_ACCOUNT_ENDPOINT_URL + TP_NUMBER_FOR_SPK).build();
        Response response = client.newCall(request).execute();

        assertEquals(SERVICEACCOUNT_FOR_SPK, response.body().string());
        assertEquals(StatusCodes.OK, response.code());
    }

    @Test
    public void tpNumberToOrganisation_returns_error_message_when_non_existing_tpnr_is_provided() throws Exception {
        Request request = new Request.Builder().url(SERVICE_ACCOUNT_ENDPOINT_URL + NON_EXISTING_TP_NUMBER).build();
        Response response = client.newCall(request).execute();

        assertEquals(StatusCodes.NOT_FOUND, response.code());
        assertEquals(NO_SERVICEACCOUNT_ORDNING_FOUND_FOR_TP_NR + NON_EXISTING_TP_NUMBER, response.body().string());
    }

    @Test
    public void tpNumberToServiceAccount_returns_error_message_when_non_existing_tpnr_is_provided() throws Exception {
        Request request = new Request.Builder().url(SERVICE_ACCOUNT_ENDPOINT_URL + NON_EXISTING_TP_NUMBER).build();
        Response response = client.newCall(request).execute();

        assertEquals(StatusCodes.NOT_FOUND, response.code());
        assertEquals(NO_SERVICEACCOUNT_ORDNING_FOUND_FOR_TP_NR + NON_EXISTING_TP_NUMBER, response.body().string());
    }

    @Test
    public void tpNumberToTpLeverandoerByTpNumber() throws IOException {
        Request request = new Request.Builder().url(TPLEVERANDOER_BY_TPNR_ENDPOINT_URL + TP_NUMBER_FOR_SPK).build();
        Response response = client.newCall(request).execute();

        assertEquals(TPLEVERANDOER_FOR_SPK, response.body().string());
        assertEquals(StatusCodes.OK, response.code());
    }

    @Test
    public void tpNumberToTpLeverandoerByTpNumber_returns_error_message_when_non_existing_tpnr_is_provided() throws IOException {
        Request request = new Request.Builder().url(TPLEVERANDOER_BY_TPNR_ENDPOINT_URL + NON_EXISTING_TP_NUMBER).build();
        Response response = client.newCall(request).execute();

        assertEquals(NO_TP_LEVERANDOER_FOUND_FOR_TP_NR + NON_EXISTING_TP_NUMBER, response.body().string());
        assertEquals(StatusCodes.NOT_FOUND, response.code());
    }

    @Test
    public void tssNumberToTpLeverandoerByTssNumber() throws IOException {
        Request request = new Request.Builder().url(TPLEVERANDOER_BY_TSSNR_ENDPOINT_URL + TSS_NUMBER_FOR_SPK).build();
        Response response = client.newCall(request).execute();

        assertEquals(TPLEVERANDOER_FOR_SPK, response.body().string());
        assertEquals(StatusCodes.OK, response.code());
    }

    @Test
    public void tssNumberToTpLeverandoerByTssNumber_returns_error_message_when_non_existing_tssnr_is_provided() throws IOException {
        Request request = new Request.Builder().url(TPLEVERANDOER_BY_TSSNR_ENDPOINT_URL + NON_EXISTING_TSS_NUMBER).build();
        Response response = client.newCall(request).execute();

        assertEquals(NO_TP_LEVERANDOER_FOUND_FOR_TSS_NR + NON_EXISTING_TSS_NUMBER, response.body().string());
        assertEquals(StatusCodes.NOT_FOUND, response.code());
    }

    @Test
    public void TpNumberToTssNumberByTpNumber() throws IOException {
        Request request = new Request.Builder().url(TSSNR_BY_TPNR_ENDPOINT_URL + TP_NUMBER_FOR_SPK).build();
        Response response = client.newCall(request).execute();

        assertEquals(TSS_NUMBER_FOR_SPK, response.body().string());
        assertEquals(StatusCodes.OK, response.code());
    }

    @Test
    public void TpNumberToTssNumberByTpNumber_returns_error_message_when_non_existing_tpnr_is_provided() throws IOException {
        Request request = new Request.Builder().url(TSSNR_BY_TPNR_ENDPOINT_URL + NON_EXISTING_TP_NUMBER).build();
        Response response = client.newCall(request).execute();

        assertEquals(NO_TSS_NR_FOUND_FOR_TP_NR + NON_EXISTING_TP_NUMBER, response.body().string());
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
