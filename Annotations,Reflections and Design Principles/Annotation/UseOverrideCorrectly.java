public class UseOverrideCorrectly {

    static class Animal {
        public void makeSound() {
            System.out.println("Some generic animal sound");
        }
    }

    static class Dog extends Animal {
        @Override
        public void makeSound() {
            System.out.println("Bark");
        }
    }

    public static void main(String[] args) {
        Dog myDog = new Dog();
        myDog.makeSound();
    }
}