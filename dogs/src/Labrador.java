public class Labrador extends Dog{

    public Labrador(String name) {
        super(name);
    }

    public void fetch() {
        System.out.println("Fetching ...");
    }
    
    @Override
    public void perform() {
        System.out.print(this.name + " is peforming ");
        super.perform();
    }
}
