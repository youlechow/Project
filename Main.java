import javax.swing.*;

public class Main {

    public static void main(String[] args) {
            
            MenuPage menuPage = new MenuPage();
            JFrame window = new JFrame();
            window.add(menuPage);
            window.pack();

            menuPage.initializeFrame();
        
    }
}
