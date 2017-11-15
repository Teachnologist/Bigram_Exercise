package com.jasontriche.bigram;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;


@ShellComponent
public class FileInput {
    int d1 = 0;
    int d2 = 0;
    String statement = "";
    String lines = "";
    String word = "";
    ArrayList words = new ArrayList();

    String directory = "";


    @ShellMethod(value="Run my bigram",key="bigram", prefix="*")
    public String bigram() {
     /*   if(promptNumber()) {
            int calc = d1 - d2;
            String sentence2 = statement + calc;
            return sentence2;
        }else{
            return "no statement found";
        }
        */

   /*  if(promptFile()){
         return "true";
     }else{
         return "false";
     }*/
   if(promptFile()){
       return word;
   }else{
        return "no data available";
   }

    }

    @ShellMethod(value="Get word",key="word#", prefix="*")
    public String getWord(int num){
        num = num - 1;

        if(num < 0){
            return "index too small";
        }

        if(promptFile()){
            if(num < words.size()) {
                return (String)words.get(num);
            }else{
                return "selection too large";
            }

        }else{
            return "no data available";
        }
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

    private Boolean promptFile() {
        try {
            System.out.print("Enter the file name with extension : ");

            Scanner input = new Scanner(System.in);

            File file = new File(input.nextLine());

            input = new Scanner(file);

            while (input.hasNextLine()) {
                Scanner s2 = new Scanner(input.nextLine());
                lines += s2;
                while (s2.hasNext()) {
                    String s = s2.next();
                    word += s+"\n";
                    words.add(s);
                }
            }
            input.close();
        return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;

        }
    }
}
