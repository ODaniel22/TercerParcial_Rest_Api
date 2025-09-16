package StepDefinitions;

import Constants.BookingEndPoints;
import Entities.Booking;
import Entities.BookingDates;
import Entities.Credenciales;
import Utils.Request;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import java.util.List;

public class BookingSteps {

    Response response;
    Credenciales creds = new Credenciales();

    @When("Generamos un nuevo token")
    public void generateAuthToken() throws InterruptedException {
        Thread.sleep(5000);
        response = Request.createAuthToken(creds);
    }

    @And("Verifico que el estado del codigo es {int}")
    public void verifyStatusCode(int statusCode) {
        response.then().assertThat().statusCode(statusCode);
    }


    @When("Realizo una llamada GET aL ID {string}")
    public void getBookingById(String id) {
        response = Request.getById(BookingEndPoints.GET_BOOKING, id);
    }

    @Then("Verificamos que el campo {string} contenga {string}")
    public void verifyFieldValue(String field, String value) {
        response.then().assertThat().body(field, Matchers.equalTo(value));
    }

    @When("Realizo un POST")
    public void createBooking(DataTable bookingData) throws Exception {
        Thread.sleep(5000);
        List<String> data = bookingData.transpose().asList(String.class);

        Booking booking = new Booking();
        booking.setFirstname(data.get(0));
        booking.setLastname(data.get(1));
        booking.setTotalprice(Integer.parseInt(data.get(2)));
        booking.setDepositpaid(Boolean.parseBoolean(data.get(3)));
        booking.setAdditionalneeds(data.get(6));

        BookingDates dates = new BookingDates();
        dates.setCheckin(data.get(4));
        dates.setCheckout(data.get(5));

        booking.setBookingdates(dates);

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(booking);

        response = Request.post(BookingEndPoints.POST_BOOKING, payload);
    }

    @When("Realizo un PUT con los siguientes datos {string}")
    public void updateBooking(String id, DataTable bookingData) throws Exception {
        List<String> data = bookingData.transpose().asList(String.class);

        BookingDates dates = new BookingDates();
        dates.setCheckin(data.get(4));
        dates.setCheckout(data.get(5));

        Booking booking = new Booking();
        booking.setFirstname(data.get(0));
        booking.setLastname(data.get(1));
        booking.setTotalprice(Integer.parseInt(data.get(2)));
        booking.setDepositpaid(Boolean.parseBoolean(data.get(3)));
        booking.setBookingdates(dates);
        booking.setAdditionalneeds(data.get(6));

        ObjectMapper mapper = new ObjectMapper();
        String payload = mapper.writeValueAsString(booking);

        // PUT con token
        response = Request.putWithAuth(BookingEndPoints.PUT_BOOKING, id, payload, creds.getToken());
    }

    @When("Realizamos un Delete al id {string}")
    public void deleteBooking(String id) {
        response = Request.deleteWithAuth(BookingEndPoints.DELETE_BOOKING, id, creds.getToken());
    }

}
