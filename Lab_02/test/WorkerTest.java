import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WorkerTest {

    private Worker w1;

    @BeforeEach
    void setUp()
    {
        w1 = new Worker("123456", "Alice", "Smith", "Ms.", 1985, 20.0);
    }

    @Test
    void setHourlyPayRate()
    {
        w1.setHourlyPayRate(25.0);
        assertEquals(25.0, w1.getHourlyPayRate(), "Hourly pay rate should update correctly");
    }

    @Test
    void calculateWeeklyPay_NoOvertime()
    {
        double pay = w1.calculateWeeklyPay(40);
        assertEquals(800.0, pay, "Weekly pay for 40 hours at $20/hr should be $800");
    }

    @Test
    void calculateWeeklyPay_WithOvertime()
    {
        double pay = w1.calculateWeeklyPay(45);
        // 40 * 20 + 5 * (20 * 1.5) = 800 + 150 = 950
        assertEquals(950.0, pay, "Weekly pay with 5 overtime hours should include time and a half");
    }

    @Test
    void testDisplayWeeklyPay_NoOvertime()
    {
        // Capture console output
        java.io.ByteArrayOutputStream outputStream = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outputStream));

        w1.displayWeeklyPay(40);

        String expected = """
            Alice worked 40.00 hours:
            40.00 at regular pay; earning: $800.00
            0.00 at overtime pay; earning: $0.00
            Earning a total of $800.00
            """;

        assertEquals(expected, outputStream.toString());
    }

    @Test
    void testDisplayWeeklyPay_WithOvertime()
    {
        // Capture console output
        java.io.ByteArrayOutputStream outputStream = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outputStream));

        w1.displayWeeklyPay(50);

        String expected = """
            Alice worked 50.00 hours:
            40.00 at regular pay; earning: $800.00
            10.00 at overtime pay; earning: $300.00
            Earning a total of $1100.00
            """;

        assertEquals(expected, outputStream.toString());
    }

    @Test
    void toCSV() {
        String expected = "123456, Alice, Smith, Ms., 1985, 20.00";
        assertEquals(expected, w1.toCSV());
    }

    @Test
    void testToJSON()
    {
        String expected = """
            {
              "id": "123456",
              "firstName": "Alice",
              "lastName": "Smith",
              "title": "Ms.",
              "yob": 1985,
              "hourlyPayRate": 20.00
            }
            """;

        assertEquals(expected, w1.toJSON());
    }

    @Test
    void testToXML()
    {
        String expected = """
            <worker>
              <id>123456</id>
              <firstName>Alice</firstName>
              <lastName>Smith</lastName>
              <title>Ms.</title>
              <yob>1985</yob>
              <hourlyPayRate>20.00</hourlyPayRate>
            </worker>
            """;

        assertEquals(expected, w1.toXML());
    }

    @Test
    void testToString()
    {
        String expected = "Person{" +
            "firstName='Alice'" +
            ", lastName='Smith'" +
            ", ID='123456'" +
            ", title='Ms.'" +
            ", YoB=1985'" +
            ", hourlyPayRate=20.0" +
            '}';

        assertEquals(expected, w1.toString());
    }
}
