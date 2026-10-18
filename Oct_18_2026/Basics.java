class Basics
{
    public static void main(String[] args)
    {
        StringBuilder sb=new StringBuilder();
        StringBuffer sbr=new StringBuffer();

        sb.append("Samba");
        sbr.append("samba");

        sb.insert(0,'K');
        sbr.insert(0,'K');

        sb.deleteCharAt(sb.length()-1);
        sbr.deleteCharAt(sbr.length()-1);

        System.out.println(sb.toString());
        System.out.println(sbr.toString());
    }
}