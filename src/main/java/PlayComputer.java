import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.move.Move;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class PlayComputer {
    private WebDriver driver;
    private final boolean DEBUG = false;

    public PlayComputer() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver","/home/beter/Selenium_Java/Documents/chromedriver_linux64/chromedriver");
        driver = new ChromeDriver();
        driver.get("https://www.chess.com/play/computer");

        // Play vs Computer
        driver.findElement(By.xpath("//button[contains(.,'Choose')]")).click();
        driver.findElement(By.xpath("//button[contains(.,'Play')]")).click();
        // FEN game


        while (true){
            String FEN = getFenBoard();

            Board board = new Board();
            board.loadFromFen(FEN);
            Chess_Minimax_Alpha_Beta minimax = new Chess_Minimax_Alpha_Beta(board);
            Move move = minimax.getBestMove(1);
            String smove = move.toString();
            String from = convertMoveToRuleChess(smove.substring(0,2));
            String to = convertMoveToRuleChess(smove.substring(2));

            System.out.println("from: " + from + "to: " + to);
//        driver.

            List<WebElement> pieces = driver.findElements(By.className("piece"));
            System.out.println(pieces.size());

            WebElement start=null,end=null;
            for (WebElement i:pieces){
                if (DEBUG) System.out.println(i.getAttribute("class"));
                if (i.getAttribute("class").contains(from)){
                    start = i;
                }
                if (i.getAttribute("class").contains(to)){
                    end = i;
                }
                if (start!=null && end!=null){
                    break;
                }
            }

            start.click();

            if (end==null){
                JavascriptExecutor js = null;
                if (driver instanceof JavascriptExecutor) {
                    js = (JavascriptExecutor)driver;
                }

                String newMove = " element.setAttribute('class', 'piece ww square-"+to+"');";

                js.executeScript("chessboard= document.getElementById('board-vs-personalities');" +
                        " element = document.createElement('div');" +
                        newMove +
                        "element.setAttribute('id', 'tmp');"+
                        " chessboard.appendChild(element);");

                WebElement toTo = driver.findElement(By.id("tmp"));
                toTo.click();
                // Remove
                js.executeScript("x = document.getElementById('tmp');x.remove();");
            }
            else{
                end.click();
            }

            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
        }

//        driver.close();
    }

    private String getFenBoard() {
        // This function get game
        // This is function get game
        // Get FEN board game
        driver.findElement(By.xpath("//div[4]/div[4]/div/button[2]/span")).click();
        String s = "";

        while (s.length()==0){
            try{
                s = driver.findElement(By.xpath("//section/div/div/input")).getAttribute("value");
            }
            catch (Exception e){
//                e.printStackTrace();
            }
        }

        System.out.println(s);
        driver.findElement(By.xpath("//div[2]/div/button/div")).click();
        return s;
    }

    public String convertMoveToRuleChess(String move){
        char col;
        char row = move.charAt(1);

        switch (move.charAt(0)){
            case 'a': col='1';break;
            case 'b': col='2';break;
            case 'c': col='3';break;
            case 'd': col='4';break;
            case 'e': col='5';break;
            case 'f': col='6';break;
            case 'g': col='7';break;
            case 'h': col='8';break;
            default:
                throw new IllegalStateException("Unexpected value: " + move.charAt(0));
        }

        StringBuilder s = new StringBuilder();
        s.append(col);
        s.append(row);
        return s.toString();
    }

    public static void main(String args[]) throws InterruptedException {
        PlayComputer auto = new PlayComputer();
    }
}
