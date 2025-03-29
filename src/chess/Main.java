package chess;

public class Main {

    public static void main(String[] args){
        Config config = new Config();
        UI ui = new UI(config);
        ui.run();
    }
}
