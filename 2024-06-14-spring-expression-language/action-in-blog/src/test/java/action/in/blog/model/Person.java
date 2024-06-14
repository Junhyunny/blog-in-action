package action.in.blog.model;

public record Person(String name, int age) {
    public String info() {
        return "[name: " + name + ", age: " + age + "]";
    }
}