
public class teste {
	    public static boolean isLeapYear(int year) {
	        // Check if the year is divisible by 4
	        boolean divisibleBy4 = year % 4 == 0;
	        // Check if the year is divisible by 100
	        boolean divisibleBy100 = year % 100 == 0;
	        // Check if the year is divisible by 400
	        boolean divisibleBy400 = year % 400 == 0;

	        // A leap year is either:
	        // - Divisible by 4 and not divisible by 100
	        // - Divisible by 400
	        return (divisibleBy4 && !divisibleBy100) || divisibleBy400;
	    }

	public static void main(String[] args) {
        int year = 2025; // Change the year here to test
        if (isLeapYear(year)) {
            System.out.println(year + " is a leap year.");
        } else {
            System.out.println(year + " is not a leap year.");
        }
    }
}
