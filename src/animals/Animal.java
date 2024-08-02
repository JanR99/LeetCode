package animals;

public class Animal {

    int id;

    public Animal() {
        this.id = 0;
    }

    public int getId() {
        return id;
    }

    public static void printClassName(Animal animal) {
        System.out.println(animal.getClass().getSimpleName());
    }
}
