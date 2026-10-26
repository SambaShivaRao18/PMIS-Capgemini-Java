import java.time.LocalDate;
import java.time.Month;
class DateTime
{
    public static void main(String[] args)
    {
        LocalDate now=LocalDate.now();
        System.out.println(now);

        int day=now.getDayOfMonth();
        Month month=now.getMonth();
        int year=now.getYear();

        System.out.println(day+" "+month+" "+year);
    }
}