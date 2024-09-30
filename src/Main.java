public class Main {
    public static void main(String[] args) {
        MyStringBuilder myStringBuilder1 = new MyStringBuilder();
        myStringBuilder1.append("Привет ");
        myStringBuilder1.append("Мир!");
        myStringBuilder1.insert(11, "Инсерт");
        System.out.println(myStringBuilder1);
        myStringBuilder1.undo();
        System.out.println(myStringBuilder1);
        myStringBuilder1.undo();
        System.out.println(myStringBuilder1);
        myStringBuilder1.undo();
        System.out.println(myStringBuilder1);

        MyStringBuilder myStringBuilder2 = new MyStringBuilder("Привет Привет Привет Привет Привет Привет Привет  22322  ");
        myStringBuilder2.append("Привет \" +\n" +
                "                \"Привет Привет Привет world Привет Привет Привет Привет Привет Привет \" +\n" +
                "                \"Привет  22322 Привет Привет Привет Привет world Привет Привет Привет \" +\n" +
                "                \"Привет Привет Привет Привет  22322 Привет Привет Привет Привет world");

        myStringBuilder2.undo();

        System.out.println(myStringBuilder2);
    }
}