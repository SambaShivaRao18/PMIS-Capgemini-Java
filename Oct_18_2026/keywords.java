abstract class Bank
{
    private String name;
    private String accNo;
    private double bal;

    Bank(String n,String an)
    {
        name=n;
        accNo=an;
    }

    public void getBal()
    {
        System.out.println(this.bal);
    }

    public void setBal(double b)
    {
        bal+=b;
    }

    abstract void calIntrest();
}

class savingAcc extends Bank
{
    savingAcc(String name,String accNo)
    {
        super(name,accNo);
    }
    public void calIntrest()
    {
        System.out.println("Intrest Rate for Saving Acc is 4%");
    }
}
class salaryAcc extends Bank
{
    salaryAcc(String name,String accNo)
    {
        super(name,accNo);
    }
    public void calIntrest()
    {
        System.out.println("Intrest Rate for Salary Acc is 6%");
    }
}

class keywords
{
    public static void main(String[] args)
    {
        savingAcc a1=new savingAcc("samba","1234");

        salaryAcc a2=new salaryAcc("shiva","9876");

        a1.calIntrest();
        a2.calIntrest();
    }
}