import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import static java.nio.file.StandardOpenOption.CREATE;


public class Main {

    static ArrayList<String> list = new ArrayList<>();
    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        Scanner in = new Scanner(System.in);
        final String menu = " A - Add  D - Delete  V - View  O - Open  S - Save  C - Clear  Q - Quit";
        boolean done = false;
        String cmd = "";
        displayList();
        do
        {

            // display the list
            // display the menu options
            // get a menu choice
            cmd = SafeInput.getRegExString(console, menu, "[AaDdVvQqOoSsCc]");
            cmd = cmd.toUpperCase();
            int deletedItem = 0;
            String addedItem = "";

            // execute the choice
            switch(cmd)
            {
                case "A":
                    // prompt the user for a list item
                    addedItem = SafeInput.getNonZeroLenString(console, "What item would you like to add?");
                    list.add(addedItem);
                    break;

                case "D":
                    // prompt the user for the number of the item they want to delete
                    System.out.println("What item would you like to get rid of?");
                    deletedItem = in.nextInt();
                    deletedItem = deletedItem - 1;
                    list.remove(deletedItem);
                    // translate the number to an index by subtracting 1
                    // remove the item from the list
                    break;

                case "V":
                    displayList();
                    break;

                case "Q":
                    System.out.println("Do you want to save before quiting?");
                    System.exit(0);
                    break;
                case "S":
                    ArrayList <String>recs = new ArrayList<>();
                    recs.add(String.valueOf(list));
                    File workingDirectory = new File(System.getProperty("user.dir"));
                    Path file = Paths.get(workingDirectory.getPath() + "\\src\\data.txt");

                    try
                    {
                        OutputStream out = new BufferedOutputStream(Files.newOutputStream(file, CREATE));
                        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));

                        for (String rec : recs)
                        {
                            writer.write(rec, 0, rec.length());
                            writer.newLine();
                        }
                        writer.close();
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                    break;
                case "O":
                    JFileChooser chooser = new JFileChooser();
                    File selectedFile;
                    String rec;

                    try
                    {
                        File workingDirectorys = new File(System.getProperty("user.dir"));
                        chooser.setCurrentDirectory(workingDirectorys);
                        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
                        {
                            selectedFile = chooser.getSelectedFile();

                            Path files = selectedFile.toPath();

                            BufferedInputStream InputStream = new BufferedInputStream(Files.newInputStream(files, CREATE));

                            BufferedReader reader = new BufferedReader(new InputStreamReader(InputStream));

                            while (reader.ready())
                            {
                                // read and count the characters, words, and lines
                                rec = reader.readLine();

                                System.out.println(rec);
                            }
                            reader.close();
                        }

                    }
                    catch (FileNotFoundException e)
                    {
                        System.out.println("File not found!!");
                        e.printStackTrace();
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                    break;
                case "C":
                    list.clear();
                    break;
            }



        }while(!done);

    }

    private static void displayList()
    {
        System.out.println("-----------------------------------");
        if(list.size() != 0)
        {
            for (int i = 0; i < list.size(); i++) {
                System.out.printf("%3d%35s", i + 1, list.get(i));
                System.out.println();
            }
        }
        else
        {
            System.out.println("---        List is empty        ---");
        }
        System.out.println("-----------------------------------");

    }
}