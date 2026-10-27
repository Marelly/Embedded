public class Dog extends Animal implements Trained {
    protected String name;
    private String action = "";

    public Dog(String name) {
        this.name = name;
    }

    public void shakeHand() {
        System.out.println("Hi, my name is " + this.name);
    }

    @Override
    public void makeSound() {
        System.out.println("Hav Hav");
        
    }

    @Override
    public void train(String action) {
        this.action = action;
        
    }

    @Override
    public void perform() {
        System.out.println(action);
        
    }
}
