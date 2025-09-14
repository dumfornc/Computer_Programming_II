public class SalaryWorker extends Worker
{
    double annualSalary;

    /**
     * Constructor that creates a salary worker object
     * @param firstName - A string holding a person's first name
     * @param lastName - A string holding a person's last name
     * @param ID - A unique 6 digit string of numbers
     * @param title - A string holding the honorific or formal title of a person
     * @param YoB - An integer in the range 1940 - 2010
     * @param annualSalary - A double representing the annual salary of a salaried worker
     */
    public SalaryWorker(String ID, String firstName, String lastName, String title, int YoB, double annualSalary)
    {
        // Hourly pay is salary divided by weeks in a year divided by hours in a week
        super(ID, firstName, lastName, title, YoB, annualSalary / 52 / 40);
        this.annualSalary = annualSalary;
    }
}
