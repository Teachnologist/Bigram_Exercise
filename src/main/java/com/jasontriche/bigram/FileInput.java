package com.jasontriche.bigram;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;
import javax.activation.*;


import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.ParameterMissingResolutionException;



    @ShellComponent
    public class FileInput {
    String error_message = "";
    Boolean restart = true;

    private void setAll(){
         error_message = "";
         restart = true;
    }

    @ShellMethod(value="Run my bigram as a shell",key="bigram", prefix="*")
    public void bigram() {
        if(restart){
            ArrayList word_array = promptFile();
        if(!word_array.isEmpty()) {
           Map processed = processBigram(word_array);
            System.out.println("\n");
           printbigram(processed);
            System.out.println("\n");
           tryagain();
        }else{
           System.out.println(error_message);
           tryagain();
         }
        }else{
            System.out.println("\n");
        System.out.println("Thank you for participating in my bigram challenge!");
        System.out.println("Please go to jasontriche.com");
        setAll();
        return;
       }
   }

    @ShellMethod(value="Run my bigram from command line",key="bigram -file", prefix="*")
    public void bigramOption(String filename){
        try{
            if(restart){
                ArrayList word_array = parseFile(filename);
                if(!word_array.isEmpty()) {
                    Map processed = processBigram(word_array);
                    System.out.println("\n");
                    printbigram(processed);
                    System.out.println("\n");
                    tryagain();
                }else{
                    System.out.println("\n");
                    System.out.println(error_message);
                    tryagain();
                }
            }else{
                System.out.println("\n");
                System.out.println("Thank you for participating in my bigram challenge!");
                System.out.println("Please go to jasontriche.com");
                setAll();
                return;
            }
            }catch(ParameterMissingResolutionException e){
            bigram();
            }
    }


    private void printbigram(Map <String, Integer> map){
        System.out.println("Beautiful Bigram:\n");
        for (Map.Entry <String, Integer> entry : map.entrySet()) {
            String wordset = (String)entry.getKey();
            int occurrences = entry.getValue();

            if(occurrences != 1) {
                System.out.println(wordset + " occurs " + occurrences + " times");
            }else{
                System.out.println(wordset + " occurs " + occurrences + " time");
            }
        }
        System.out.println("\n");
        System.out.println("Requirement Bigram:\n");
        for (Map.Entry <String, Integer> entry : map.entrySet()) {
            String wordset = (String) entry.getKey();
            int occurrences = entry.getValue();
            System.out.println("\"" + wordset + "\" " + occurrences);
        }

    }

    private Map processBigram(ArrayList list){
        Map <String,Integer> hashMap = new LinkedHashMap<String,Integer>();
        int sizeoflist = list.size();

        for(int i = 0; i <= sizeoflist - 1; i++){
            String bivalue = "";
            int q = i + 1;

            if(q <= sizeoflist - 1){
                bivalue = list.get(i)+" "+list.get(q);
            }else{
                bivalue = (String)list.get(i);
            }

            String bivalue_lowercase = bivalue.toLowerCase();
            if(hashMap.containsKey(bivalue_lowercase)){
                int occurrences = hashMap.get(bivalue_lowercase) + 1;
                hashMap.put(bivalue_lowercase,occurrences);
            }else{
                hashMap.put(bivalue_lowercase,1);
            }
        }
        return hashMap;
    }

    private void tryagain(){
        Console console = System.console();
        String question = console.readLine("Would you like to try again? (yes/no): ");


        switch(question.toLowerCase()){
            case "yes":
            case "y":
                setAll();
                break;
            case "no":
            case "n":
                error_message = "You have opted to exit this program.";
                restart = false;
                break;
            default:
                setAll();
                break;
        }
        bigram();
    }

    private ArrayList promptFile() {
        MimetypesFileTypeMap fileTypeMap = new MimetypesFileTypeMap();
        ArrayList words = new ArrayList();
        System.out.println("Please type the name of the text file to be analyzed");
        System.out.println("If the file is not in this directory, please type the whole path");
        System.out.println("Example: ../Directory/text.txt");
        System.out.println("Warning: Only .txt files will be analyzed.");
        try {

            Console console = System.console();

            String filename = console.readLine("Type file name :> ");
            String filetype = fileTypeMap.getContentType(filename);

            if(filetype.equals("text/plain")) {
                File file = new File(filename);
                String canpath = file.getCanonicalPath();
                System.out.println("Your path to this file was "+canpath);
                System.out.println("This is a "+filetype+" type of file...");
                BufferedReader buffin = new BufferedReader(new FileReader(canpath));
                String line;
                while((line = buffin.readLine()) != null){
                    String[] word = line.split(" ");
                    for(int q = 0; q <= word.length -1; q++) {
                        words.add(word[q].replaceAll("\\W", ""));
                    }
                }

                buffin.close();

                return words;
            }else{
                error_message = "File type not accepted. Only text/plain files!";
                return words;
            }
        } catch (Exception ex) {
            error_message = "File not found";
            return words;
        }
    }

    private ArrayList parseFile(String filename){
        MimetypesFileTypeMap fileTypeMap = new MimetypesFileTypeMap();
        ArrayList words = new ArrayList();

        try {
            String filetype = fileTypeMap.getContentType(filename);
            if(filetype.equals("text/plain")) {
                File file = new File(filename);
                String canpath = file.getCanonicalPath();
                System.out.println("Your path to this file was "+canpath);
                System.out.println("This is a "+filetype+" type of file...");
                BufferedReader buffin = new BufferedReader(new FileReader(canpath));
                String line;
                while((line = buffin.readLine()) != null){
                    String[] word = line.split(" ");
                    for(int q = 0; q <= word.length -1; q++) {
                        words.add(word[q].replaceAll("\\W", ""));
                    }
                }

              buffin.close();
                return words;
            }else{
                error_message = "File type not accepted. Only text/plain files!";
                return words;
            }
        } catch (Exception ex) {
            error_message = "File not found";
            return words;
        }
    }
}
