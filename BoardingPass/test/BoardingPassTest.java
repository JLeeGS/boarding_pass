import com.sun.tools.javac.Main;
import display.MainGui;
import domain.BoardingPass;
import domain.BoardingPassTicket;
import domain.Person;
import org.junit.jupiter.api.*;
import services.LocationAPI;
import services.ScannerFile;
import services.UseFile;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BoardingPassTest {
    @BeforeEach
    void setUp(){
        System.out.println("Starting Test");
    }

    @Test
    void getObjects(){
        Person person=new Person();
        BoardingPass bp=new BoardingPass();
        BoardingPassTicket bpt=new BoardingPassTicket(bp,person);

        assertEquals(bpt.getPerson(),person);
        assertEquals(bpt.getBp(),bp);
    }

    @Test
    void readFile() throws IOException {
        UseFile useFile=new UseFile();
        Person person=new Person();
        BoardingPass bp=new BoardingPass();
        BoardingPassTicket bpt=new BoardingPassTicket(bp,person);
        useFile.getObject();
    }

    @Test
    void writeFileNull() throws IOException {
        UseFile useFile=new UseFile();
        Person person=new Person();
        BoardingPass bp=new BoardingPass();
        BoardingPassTicket bpt=new BoardingPassTicket(bp,person);
        useFile.makeObject(bpt);
    }

    @Test
    void writeTicketToFile() throws IOException {
        UseFile useFile=new UseFile();
        Person person=new Person("Freddy Test","fred@email.com",5242345,"Male",10);
        BoardingPass bp=new BoardingPass(10001,500,"10/11/2020","Texas","Washington DC", "1:00","10:00");
        BoardingPassTicket bpt=new BoardingPassTicket(bp,person);
        useFile.makeTicket(bpt);
    }

    @Test
    void createTicket() throws IOException {
        UseFile useFile=new UseFile();
        Person person=new Person();
        BoardingPass bp=new BoardingPass();
        BoardingPassTicket bpt=new BoardingPassTicket(bp,person);
        useFile.makeTicket(bpt);
    }

    @Test
    void locationAPI() throws IOException, InterruptedException {
        LocationAPI getLocation=new LocationAPI();
        System.out.println(getLocation.postLocation());
    }

    @Test
    void getGeneratedETA() throws ParseException, IOException, InterruptedException {
        LocationAPI getLocation=new LocationAPI();
        Person person=new Person();
        BoardingPass bp=new BoardingPass();
        String origin="New York",destination="";
        bp.setDeparture("1:00");
        bp.setOrigin(origin); bp.setDestination(destination);
        BoardingPassTicket bpt=new BoardingPassTicket(bp,person);
        getLocation.generateETA(bpt);
        int getETA=getLocation.postRequestETA(origin, destination);
        System.out.println(bpt.getBp().getEta());
    }

    @Test
    void getCoords() throws IOException, InterruptedException {
        LocationAPI getLocation=new LocationAPI();
        getLocation.postRequestETA("New York", "San Francisco");
    }

    @Test
    void scanGUI() throws IOException {
        ScannerFile.ScannerGui sfgui=new ScannerFile.ScannerGui();
        BoardingPassTicket bptSf=sfgui.getUserInput(500);
        System.out.println(bptSf.getBp()+"\n"+bptSf.getPerson());
    }

    @Test
    void testGUI() throws IOException {
        ScannerFile.ScannerGui sfgui=new ScannerFile.ScannerGui();
        BoardingPassTicket bpt=sfgui.getUserInput(500);
        MainGui gui=new MainGui();
        System.out.println(bpt.getBp()+"\n"+bpt.getPerson());
    }

    @Test
    void getBptFromFile() throws IOException {
        UseFile useFile=new UseFile();
        ArrayList<BoardingPassTicket> bptArrs=useFile.getBoardingPassTicket();
        bptArrs.forEach(x->{
            System.out.println(x.getBp());
            System.out.println(x.getPerson());
        });
    }

    @Test
    void testBptArray(){
        ArrayList<BoardingPassTicket> bptArrs=new ArrayList<>();
        try {
            File file = new File("src/resources/ticket.txt");
            Scanner scan = new Scanner(file);
            while (scan.hasNextLine()) {
                String line = scan.nextLine().replaceAll(" ", "");
                String[] attr = line.split(":");
                System.out.println(attr[1]);
            }
        }
        catch(Exception e){
        }
    }

    @Test
    void getMaxId() throws IOException {
        UseFile useFile=new UseFile();
        System.out.println(useFile.getNextBoardingPassid(useFile.getBoardingPassTicket()));
    }

    @AfterEach
    void tearDown(){
        System.out.println("Tests Complete");
    }

}
