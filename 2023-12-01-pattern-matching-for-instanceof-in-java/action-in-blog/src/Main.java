import java.util.HashMap;
import java.util.Map;

class Cat {
    void meow() {
        System.out.println("meow");
    }
}

class Dog {
    void bowwow() {
        System.out.println("bowwow");
    }
}

public class Main {

    static Map contextMap = new HashMap();

    static {
        contextMap.put("animal", new Cat());
    }

    public static void main(String[] args) {
//        Object animal = contextMap.get("animal");
//        if (animal instanceof Cat) {
//            Cat cat = (Cat) animal;
//            cat.meow();
//        } else if (animal instanceof Dog) {
//            Dog dog = (Dog) animal;
//            dog.bowwow();
//        }
        Object animal = contextMap.get("animal");
        if (animal instanceof Cat cat) {
            cat.meow();
        } else if (animal instanceof Dog dog) {
            dog.bowwow();
        }
    }
}