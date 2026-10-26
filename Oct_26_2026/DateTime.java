import java.time.LocalDateTime;
class DateTime
{
    public static void main(String[] args)
    {
        LocalDateTime now=LocalDateTime.now();
        
        System.out.println(now.minusDays(1));

        System.out.println((LocalDateTime)now.minusMonths(186));
    }
}