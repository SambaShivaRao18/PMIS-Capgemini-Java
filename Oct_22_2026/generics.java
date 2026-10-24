class Demo<T>
{
    T a;
    Demo(T val)
    {
        a=val;
    }
    void getData()
    {
        System.out.println(a);
    }
}

class generics
{
    public static void main(String[] args)
    {
        Demo<Integer> d=new Demo<>(10);

        d.getData();

    }
}