public class Poodle extends Dog{

    public Poodle(String name) {
        super(name);
        //TODO Auto-generated constructor stub
    }

    public void roll() {
        System.out.println("Rolling ...");
    }
    @Override
    public void shakeHand() {
        System.out.println("Poodles never say their names ...");
    }
    
}
