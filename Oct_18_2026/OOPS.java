class BankAccount
{
    //properties
    int balance;
    int accNo;

    public BankAccount(int balance,int accNo)
    {
        this.balance=balance;
        this.accNo=accNo;
    }

    //behaviours of class
    public void getData()
    {
        System.out.println(this.balance+"  "+this.accNo);
    }
}
public class OOPS
{
    public static void main(String[] args)
    {
        BankAccount acc1=new BankAccount(10000,123);
        acc1.getData();
    }
}
