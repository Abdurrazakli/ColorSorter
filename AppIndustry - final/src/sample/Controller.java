package sample;

import com.fazecast.jSerialComm.SerialPort;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;
import java.util.Scanner;

public class Controller {

    @FXML
    private AnchorPane bigAncorPane;

    @FXML
    private Pane paneRed;

    @FXML
    private TextField Red_textField;

    @FXML
    private Pane paneBlue;

    @FXML
    private TextField Blue_textField;

    @FXML
    private Pane paneGreen;

    @FXML
    private TextField Green_textField;

    @FXML
    private Pane PaneYellow;

    @FXML
    private TextField Yellow_textField;

    @FXML
    private TextField temperatur_textField;

    @FXML
    private Text rutubet_text;

    @FXML
    private TextField rutubet_textField;

    @FXML
    private Button btn_Stop;

    @FXML
    private Button btn_Start;

    @FXML
    private TextField buttons_info_textField;

    @FXML
    private Button btn_restart;
//****************************************Port oxumaq ucun deyisenler********************
    @FXML
    private ChoiceBox<String> listofport;

    @FXML
    private Button refreshButton;

    @FXML
    private Button selectPortButton;

    @FXML
    private Button startButton;

    @FXML
    private Button closePortButton;

    public               SerialPort         serialPort;
    public               SerialPort[]       ports;
    public               String             st;
//**********************************************************************************

    //DIQQET Error functionlari doldurmaq lazim!!!!!!
    private void parseAndCallChangeFunctions(String input) {
        //input is like "Reng#temperatur#rutubet#Status"
        String color;
        //String input = "R#36.5#37.2#0";// just for testing
        double temperatur,rutubet;
        int status,first,second;

        //parsing data
        first = input.indexOf("#");
        color = input.substring(0,first);

        second = input.indexOf("#",first+1);
        temperatur = Double.parseDouble(input.substring(first+1,second));

        first = second+1;
        second = input.indexOf("#",first);
        rutubet = Double.parseDouble(input.substring(first,second));

        status =  Integer.parseInt(input.substring(second+1));


        //changing values
        checkAndChangeColor(color);//changing values of counters of colors
        temperatur_textField.setText(Double.toString(temperatur));
        rutubet_textField.setText(Double.toString(rutubet)+"%");
        System.out.println(temperatur+" "+rutubet + " " + color + " " + status);
        updateDB();
        checkStatus(status);
    }
    //Statusa uygun xeta mesajini cixarir
    public void checkStatus(int status) {
        //0-everything is normal
        //1-gas error
        //2-fire error
        switch (status)
        {
            case 0: break;
            case 1:errorGas();
                break;
            case 2:errorFire();
                break;
            default: break;
        }
    }

    private void errorFire() {
    }

    private void errorGas() {
    }

    //Rengi yoxlayir ve uygun rengin gostericisini bir vahid artirir
    private void checkAndChangeColor(String color){

        int n;
        switch (color)
        {
            case "R": //RED
                n = Integer.parseInt(Red_textField.getText().toString())+1;
                Red_textField.setText(Integer.toString(n));
                break;
            case "G": //GREEN
                n = Integer.parseInt(Green_textField.getText().toString())+1;
                Green_textField.setText(Integer.toString(n));
                break;
            case "B" ://BLUE
                n = Integer.parseInt(Blue_textField.getText().toString())+1;
                Blue_textField.setText(Integer.toString(n));
                break;
            case "Y"://YELLOW
                n = Integer.parseInt(Yellow_textField.getText().toString())+1;
                Yellow_textField.setText(Integer.toString(n));
                break;
            default:
                break;
        }
    }
    public void start(){
    //#4F8A10 for text
    //#DFF2BF for background

    buttons_info_textField.setStyle("-fx-control-inner-background: #DFF2BF;");
    buttons_info_textField.setStyle("-fx-text-fill: #4F8A10;");
    buttons_info_textField.setText("Uğurla başladıldı!");
    btn_Stop.setDisable(false);
    btn_Start.setDisable(true);
    btn_restart.setDisable(true);
    sendOne();

   // st = "R#36.5#37.2#0";
   // parseAndCallChangeFunctions(st);
}

    public void stop(){
    buttons_info_textField.setStyle("-fx-control-background: #FFD2D2;");
    buttons_info_textField.setStyle("-fx-text-fill: #D8000C;");
    buttons_info_textField.setText("Uğurla dayandırıldı!");
    btn_Start.setDisable(false);
    btn_Stop.setDisable(true);
            btn_restart.setDisable(false);
    sendZero();
}

//gonderilen linki backgroundda acir
    public void OpenLinkBackground(String saytLink) throws IOException
        {
		/*
		 *
		 * daxil et bu kitabxanalari
		 * import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
		 *
		 *
		 * */

//gonderilen stringin icinde mutleq http:// olsun yoxsa xeta atir
        URL url = null;


        URLConnection con = null;
        try {
            url = new URL(saytLink);
           // System.out.println("URL setted successfully");
        } catch (MalformedURLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        try {
            con = url.openConnection();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        con.getInputStream();//bunu yazanda linke gedir

		/*
		 * linkden data oxumaq ucun lazimdir bu
		BufferedReader in = new BufferedReader(
                new InputStreamReader(
                con.getInputStream()));

		 * Alinan datani ekrana cap edir;

			String inputLine;

			while ((inputLine = in.readLine()) != null)
			System.out.println(inputLine);
			in.close();
 */
    }

    private void updateDB(){
        String red, green, blue, yellow, temperature, humidity, url;
        red = Red_textField.getText();
        green = Green_textField.getText();
        blue = Blue_textField.getText();
        yellow = Yellow_textField.getText();
        temperature = temperatur_textField.getText();
        humidity = rutubet_textField.getText();
        url = String.format("http://yourwebsite.az/update.php?red=%s&green=%s&blue=%s&yellow=%s&temperature=%s&humidity=%s",red,green,blue,yellow,temperature,humidity);
        try {
            OpenLinkBackground(url);
            System.out.println(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //**********************************Port oxumaq ucun funksiyalar*************************
    @FXML
    public void connect(){
        startButton.setDisable(true);
        try {
            new Thread(() -> {
                try {
                    Scanner data = new Scanner(serialPort.getInputStream());

                    while (data.hasNextLine()) {
                        st = "";
                        try {
                            st = String.valueOf(data.nextLine());
                            System.out.println(st);
                            parseAndCallChangeFunctions(st);//bu yoxlamaq ucundur
                            //updateDB();

                        }
                        catch (Exception e) {
                            System.out.println(e);
                            //ErrorLabel.setText(e + "\n" + ErrorLabel.getText());
                        }
                    }

                }catch (Exception e) {
                    System.out.println(e);
                    //ErrorLabel.setText(e + "\n" + ErrorLabel.getText());
                }
            }).start();
        }catch (Exception e) {
            System.out.println(e);
            //ErrorLabel.setText("Error from connect function!\n" + ErrorLabel.getText());
        }
    }

    @FXML
    public void detectPort() {
        new Thread(() -> {
            try{

                ports = SerialPort.getCommPorts();

                for (SerialPort port : ports) {
                    String portName = port.getSystemPortName();
                    if(!listofport.getItems().contains(portName))
                        listofport.getItems().add(portName);
                }
            }catch (Exception e) {
                System.out.println(e);
                /*ErrorLabel.setText(e + "\n" + ErrorLabel.getText()); */
            }
        }).start();
    }

    @FXML
    public void getChoice() {
        selectPortButton.setDisable(true);
        closePortButton.setDisable(false);
        startButton.setDisable(false);
        btn_Start.setDisable(false);
        btn_Stop.setDisable(true);
        try{

            int chosenPort = listofport.getValue().indexOf(listofport.getValue());
            //System.out.println(chosenPort);
            serialPort = ports[chosenPort];

            if(serialPort.openPort()) {
                //CommandLabel.setText("Successfully opened the port id: \"" + listofport.getValue() + "\"." + "\n" + CommandLabel.getText());
            } else {
                //CommandLabel.setText("Unable to open the port id: \"" + listofport.getValue() + "\"." + "\n" + CommandLabel.getText());
            }

            serialPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);

        }catch (Exception e){
            //CommandLabel.setText("The port id: \"" + listofport.getValue() + "\" is already opened!" + "\n" + CommandLabel.getText());
            //ErrorLabel.setText(e + "\n" + ErrorLabel.getText());
            System.out.println("Serial port is opened: " + serialPort.openPort());
        }
    }

    public void closePort(){

        selectPortButton.setDisable(false);
        closePortButton.setDisable(true);
        startButton.setDisable(true);
        btn_Start.setDisable(true);
        btn_Stop.setDisable(true);
        try {

            if (serialPort.closePort()) {
                //CommandLabel.setText("Successfully closed the port id: \"" + listofport.getValue() + "\".");
            } else {
                //CommandLabel.setText("Unable to close the port id: \"" + listofport.getValue() + "\".");
            }
        }catch (Exception e){
            //CommandLabel.setText("The port id: \"" + listofport.getValue() + "\" is already closed!");
            //ErrorLabel.setText(e + "\n" + ErrorLabel.getText());
            System.out.println("Serial port is opened: " + serialPort.openPort());
        }
        try{listofport.getItems().clear();}catch (Exception e){}
        closePortButton.setDisable(true);


    }

    public void sendZero (){

        OutputStream out = serialPort.getOutputStream();

        try{
            out.write(48);
        }catch (Exception e){
            System.out.println(e);
            //CommandLabel.setText("Couldn't send open command!" + "\n" + CommandLabel.getText());
            //ErrorLabel.setText(e + "\n" + ErrorLabel.getText());
        }
    }

    public void sendOne () {

        OutputStream out = serialPort.getOutputStream();

        try{
            out.write(49);
            //parseAndCallChangeFunctions(st);
        }catch (Exception e){
            System.out.println(e);
            //CommandLabel.setText("Couldn't send open command!" + "\n" + CommandLabel.getText());
            //ErrorLabel.setText(e + "\n" + ErrorLabel.getText());
        }
    }

    public void setBtn_restart(){
        Red_textField.setText("0");
        Green_textField.setText("0");
        Blue_textField.setText("0");
        Yellow_textField.setText("0");
        temperatur_textField.setText("0");
        rutubet_textField.setText("0");
        buttons_info_textField.setText("İlkin vəziyyət");
        String url = "http://3dpanel.az/update.php?red=0&green=0&blue=0&yellow=0&temperature=0&humidity=0";
        try {
            OpenLinkBackground(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
