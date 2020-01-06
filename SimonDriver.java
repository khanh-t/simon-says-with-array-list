/**
 *
 * @author Khanh Tran
 */
import java.util.Iterator;
import javax.swing.JOptionPane;

public class SimonDriver {
    
    public static void main(String[] args) {
        ArrayList<String> game = new ArrayList<>();
        ArrayList<String> player = new ArrayList<>();
        String[] colours = {"Green", "Yellow", "Blue", "Red"};
        boolean loop = true;
        
        while (loop) {
            game.add(game.size(), colours[generateColour()]);
            Iterator iter = game.iterator();
            //Briefly display all the colours currently in game ArrayList
            while (iter.hasNext()) {
                System.out.print("System:   " + iter.next());
                timer();
                System.out.println();
            }
            //User input
            for (int i =0; i <game.size(); i++) {
                player.add(i, (String)JOptionPane.showInputDialog("User Input:"));
            }
            //Compare user input to generated colours
            //If user inputs wrong sequence of colours, display results and end loop
            if (!(game.equals(player))) {
                System.out.println("System:   Game Over! Your score is " + (game.size()-1));
                loop = false;
            }
            //Clear out the user input for next round
            while (!(player.isEmpty())) {
                player.remove(0);
            }
        }
    }
    /**
     * The timer for how long a word displays, then clear the word
     */
    private static void timer(){
        try {
            Thread.sleep(1500);
            System.out.print("\b\b\b\b\b\b");
        }
        catch (Exception e) {
            
        }
    }
    /**
     * Generates random number to be used for selecting a random colour
     * @return random number
     */
    private static int generateColour(){
        int i = (int)(Math.random() *4);
        return i;
    }
}
