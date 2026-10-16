import java.util.function.*;
import java.util.*;
public class LambdaFunction
{
    public static void main(String[] args)
    {
        //Usecase of Supplier
        Supplier<String> otpGen=()->
        {
            Random r=new Random();
            int otp=1000000+r.nextInt(9000000);
            return String.valueOf(otp);
        };

        String otp=otpGen.get();
        System.out.println(otp);
    }
}