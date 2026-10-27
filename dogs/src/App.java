public class App {
    
    public static void main(String[] args) {
        Labrador lab = new Labrador("Mickey");
        Poodle poodle = new Poodle("Rexy");
        Cat cat = new Cat();

        // lab.shakeHand();
        // poodle.shakeHand();
        // lab.fetch();
        // poodle.roll();
        lab.train("roll");
        poodle.train("get newspaper");
        // lab.makeSound();
        // poodle.makeSound();
        // cat.makeSound();

        Animal[] animals = {lab, poodle, cat};
        for (int i = 0; i < animals.length; i++) {
          animals[i].makeSound(); 
          if (animals[i] instanceof Trained) {
            ((Trained) animals[i]).perform();
          }
        } 
        
    }
}
