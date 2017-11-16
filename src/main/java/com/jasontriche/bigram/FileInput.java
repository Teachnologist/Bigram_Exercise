package com.jasontriche.bigram;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import javax.activation.*;


import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;



@ShellComponent
public class FileInput {
    String error_message = "";



    int d1 = 0;
    int d2 = 0;
    String statement = "";
    String lines = "";
    String word = "";


    String directory = "";


    @ShellMethod(value="Run my bigram",key="bigram", prefix="*")
    public void bigram() {
   ArrayList word_array = promptFile();
   if(!word_array.isEmpty()){
       System.out.println("not empty?");
       printbigram(word_array);
   }else{
       System.out.println("empty why?");
       if(!tryagain()) {
           System.out.println("quit");
       }
   }
        System.out.println("placeholder");
        bigram();
    }

    

    @ShellMethod(value="Get word",key="word#", prefix="*")
    public String getWord(int num){
        num = num - 1;

        if(num < 0){
            return "index too small";
        }

        ArrayList word_array = promptFile();
        if(!word_array.isEmpty()){
            return "all good";

        }else{
            return "no data available";
        }
    }

    private void printbigram(ArrayList <String> list){


        for (String value : list) {
            System.out.println(value);
        }

    }

    private Boolean tryagain(){
        System.out.println(error_message);
        Console console = System.console();
        String question = console.readLine("Would you like to try again? (yes/no): ");


        switch(question.toLowerCase()){
            case "yes":
            case "y":
                bigram();
                break;
            case "no":
            case "n":
                return false;
            default:
                tryagain();
                break;
        }
        System.out.println("in try again");
       return false;
    }



    private Boolean promptNumber() {
        try {
            InputStreamReader text = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(text);
            System.out.print("Enter the message: ");
            statement = br.readLine();
            System.out.print("Enter the first digit: ");
            d1 = Integer.parseInt(br.readLine());
            System.out.print("Enter the second digit: ");
            d2 = Integer.parseInt(br.readLine());
            return true;
        } catch (IOException ioe) {
            return false;
        }
    }

    private ArrayList promptFile() {
        MimetypesFileTypeMap fileTypeMap = new MimetypesFileTypeMap();
        ArrayList words = new ArrayList();
        System.out.println("Please type the name of the text file to be analyzed");
        System.out.println("If the file is not in this directory, please type the whole path");
        System.out.println("Example: User/Directory/text.txt");
        System.out.println("Warning: Only .txt files will be analyzed.");
        try {

            Scanner input = new Scanner(System.in);
            Console console = System.console();

            String fileName = console.readLine("Type file name :> ");
            String filetype = fileTypeMap.getContentType(fileName);
            System.out.println(filetype);

            if(filetype.equals("text/plain")) {
                File file = new File(fileName);


                input = new Scanner(file);

                while (input.hasNextLine()) {
                    Scanner s2 = new Scanner(input.nextLine());
                    while (s2.hasNext()) {
                        String s = s2.next();
                        System.out.println(s);
                        words.add(s);
                    }
                }
                input.close();

                return words;
            }else{
                error_message = "File type not accepted. Only text/plain files!";
                return words;
            }
        } catch (Exception ex) {
            System.out.print("File not found");
            error_message = "File not found";
            return words;
        }
    }
}
