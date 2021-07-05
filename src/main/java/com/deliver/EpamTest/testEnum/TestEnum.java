package com.deliver.EpamTest.testEnum;

public class TestEnum {
    enum Level{
        LOW,MEDIUM,HIGH
    }

    public static void main(String[] args) {
        Level level = Level.LOW;
        switch (level){
            case LOW:
                System.out.println("low");
                break;
            case MEDIUM:
                System.out.println("medium");
                break;
            case HIGH:
                System.out.println("high");
                break;
        }

        for (Level level1 : Level.values()) {
            System.out.println(level1);
        }

        System.out.println("Level.valueOf(\"MEDIUM\") :"+Level.valueOf("MEDIUM"));
    }
}




