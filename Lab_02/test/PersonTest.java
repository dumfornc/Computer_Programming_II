import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest
{
    Person p1;

    @BeforeEach
    void setUp()
    {
        p1 = new Person("000001", "James", "Kirk", "Capt.", 2233);
    }

    @Test
    void setFirstName()
    {
        p1.setFirstName("S'Chn");
        assertEquals("S'Chn", p1.getFirstName());
    }

    @Test
    void setLastName() {
        p1.setLastName("Spock");
        assertEquals("Spock", p1.getLastName());
    }

    @Test
    void setTitle() {
        p1.setTitle("Lt.");
        assertEquals("Lt.", p1.getTitle());
    }

    @Test
    void setYoB() {
        p1.setYoB(2230);
        assertEquals(2230, p1.getYoB());
    }

    @Test
    void fullName() {
        assertEquals("James Kirk", p1.fullName());
    }

    @Test
    void formalName() {
        assertEquals("Capt. James Kirk", p1.formalName());
    }

    @Test
    void getAge() {
        assertEquals(67, p1.getAge(2300)); // 2300 - 2233 = 67
    }

    @Test
    void testGetAge() {
        // Edge case: current year == yob
        assertEquals(0, p1.getAge(2233));
    }

    @Test
    void toCSV() {
        String expected = "000001, James, Kirk, Capt., 2233";
        assertEquals(expected, p1.toCSV());
    }

    @Test
    void toJSON() {
        String expected = """
                {
                  "id": "000001",
                  "firstName": "James",
                  "lastName": "Kirk",
                  "title": "Capt.",
                  "yob": 2233
                }
                """;
        assertEquals(expected.trim(), p1.toJSON().trim());
    }

    @Test
    void toXML() {
        String expected = """
                <person>
                  <id>000001</id>
                  <firstName>James</firstName>
                  <lastName>Kirk</lastName>
                  <title>Capt.</title>
                  <yob>2233</yob>
                </person>
                """;
        assertEquals(expected.trim(), p1.toXML().trim());
    }

    @Test
    void testToString() {
        assertTrue(p1.toString().contains("James"));
        assertTrue(p1.toString().contains("Kirk"));
    }

    @Test
    void testEquals() {
        Person p2 = new Person("000001", "James", "Kirk", "Capt.", 2233);
        Person p3 = new Person("000002", "S'Chn", "Spock", "Lt.", 2230);

        assertEquals(p1, p2);   // same data
        assertNotEquals(p1, p3); // different person
    }
}