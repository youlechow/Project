import javax.swing.JFrame;

public class Main {

    public static void main(String[] args) {
        // run the main class and add menuPage
        MenuPage menuPage = new MenuPage();
        JFrame window = new JFrame();
        window.add(menuPage);
        window.pack();

        menuPage.initializeFrame();

    }
}
