package blog.in.action.pattern.singleton;

public class Singleton {

    private Singleton() {
        System.out.println("When am I called?");
    }

    /**
     * static member class
     * 내부클래스에서 static변수를 선언해야하는 경우 static 내부 클래스를 선언해야만 한다.
     * static 멤버, 특히 static 메서드에서 사용될 목적으로 선언
     */
    private static class InnerInstanceClazz {
        // 클래스 로딩 시점에서 생성
        private static final Singleton uniqueInstance = new Singleton();
    }

    public static Singleton getInstance() {
        return InnerInstanceClazz.uniqueInstance;
    }

    public static void main(String[] args) {
        System.out.println("before getInstance");
        Singleton.getInstance();
        System.out.println("after getInstance");
    }
}