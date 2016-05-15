package base.enum_l;

import sun.security.jgss.spi.MechanismFactory;

interface Named {
    public String name();
    public int order();
}

/**
 * 創建一個enum 這個enmu實現name方法
 */
enum Planets implements Named {
    Mercury(1), Venus(1), Earth(1), Mars(1), Jupiter(1), Saturn(1), Uranus(1), Neptune(1);
    ;
    private int age;
    // name() is implemented automagically.
    public int order() { return ordinal(); }
    private  Planets(int age){
        this.age = age ;
    }

    public static void main(String[] args) {
        int order = Mercury.order();
        int age = Planets.Mercury.age;
        System.out.println(order);
    }
}