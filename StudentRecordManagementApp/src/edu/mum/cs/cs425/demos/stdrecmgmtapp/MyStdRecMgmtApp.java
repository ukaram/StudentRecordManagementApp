package edu.mum.cs.cs425.demos.stdrecmgmtapp;
import edu.mum.cs.cs425.demos.stdrecmgmtapp.model.Student;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class MyStdRecMgmtApp {
    public static void main(String[] args) {
        List<Student> students = new ArrayList<>();
        students.add(new Student(110001,"Dave", "11/18/1951"));
        students.add(new Student(110002,"Anna", "12/07/1990"));
        students.add(new Student(110003, "Erica", "01/31/1974"));
        students.add(new Student(110004, "Carlos", "08/22/2009"));
        students.add(new Student(110005, "Bob", "03/05/1990"));

        printListOfStudents(students);
        getListOfPlatinumAlumniStudents(students);

        printHelloWorld(35); // multiple of both
        printHelloWorld(25); // multiple of 5
        printHelloWorld(28); // multiple of 7

        System.out.println(findSecondBiggest(new int[]{1, 2, 3, 4, 5}));
        System.out.println(findSecondBiggest(new int[]{19, 9, 11, 0, 12}));
    }

    public static void printListOfStudents(List<Student> students){
        students.sort(new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        System.out.println("\n------List of Students in Ascending Order of their name------");
        for(Student s: students){
            System.out.println(s.toString());
        }
        System.out.println("------------\n");
    }

    public static void getListOfPlatinumAlumniStudents(List<Student> students){

        students = students.stream().filter(s -> {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                Date date = sdf.parse(s.getDateOfAdmission());
                LocalDate sDoA = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                long years = ChronoUnit.YEARS.between(sDoA, LocalDate.now());
                return years>=30;
            } catch (ParseException e) {
                System.err.println(s.toString()+" has invalid date!");
                return false;
                //throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
        students.sort(new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                    Date date1 = sdf.parse(o1.getDateOfAdmission());
                    Date date2 = sdf.parse(o2.getDateOfAdmission());
                    return date2.compareTo(date1);
                } catch (ParseException e) {
                    return 0;
                    //throw new RuntimeException(e);
                }
            }
        });
        System.out.println("\n------List of Platinum Alumni Students Descending order of their DoA------");
        for(Student s: students){
            System.out.println(s.toString());
        }
        System.out.println("------------\n");
    }

    public static void printHelloWorld(int i){
        String output = "";
        if(i % 5 == 0){
            output += "Hello";
        }
        if(i % 7 == 0){
            output += "World";
        }
        System.out.println(output);
    }

    public static int findSecondBiggest(int[] arr){
        int max=Integer.MIN_VALUE, secondMax = Integer.MIN_VALUE;
        for (int i : arr) {
            if (i > max) {
                secondMax = max;
                max = i;
            }else if(i > secondMax){
                secondMax = i;
            }
        }
        return secondMax;
    }
}