package animals;

public class Mammal extends Animal {

    int mammalId;

    public Mammal() {
        this.mammalId = 0;
    }

    public int getMammalId() {
        return this.mammalId;
    }

    public static void printClassName(Animal animal) {
        System.out.println("Wuff wuff " + animal.getClass().getSimpleName());
    }
}
