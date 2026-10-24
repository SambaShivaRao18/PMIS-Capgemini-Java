
class InsufficientBalanceException extends Exception
{
    public InsufficientBalanceException(String msg)
    {
        super(msg);
    }
}
class ExceptionHandling
{
    public static void withDrawl(int amount)
    {
        try
        {
            if(amount<0)
             throw new IllegalArgumentException("Amount cannot be negative");
            else if(amount>10000)
             throw new InsufficientBalanceException("Insufficient Funds");
            else
                System.out.println("WithDrawl Successful!!");
        }
        catch(IllegalArgumentException e)
        {
            System.out.println(e.getMessage());
        }
        catch(InsufficientBalanceException e)
        {
            System.out.println(e.getMessage());
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
        
    }
    public static int calculator(int a,int b)
    {
        try
        {
            int ans=a/b;
            return ans;
        }
        catch(ArithmeticException e)
        {
            System.out.println(e.getMessage());
        }

        return 0;
    }
    public static void main(String[] args)
    {
        withDrawl(120000);
        System.out.println("End of COde");
    }
}
