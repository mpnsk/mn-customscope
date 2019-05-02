package example;

import io.micronaut.context.ApplicationContext;

import javax.inject.Inject;

public class Application {

    private static ApplicationContext context;


    public static void main(String[] args) {
        context = ApplicationContext.run();
        System.out.println("Application.main");
        System.out.println("args = [" + args + "]");

        Building building1 = context.getBean(Building.class);
        Building building2 = context.getBean(Building.class);


        building1.printRoom();
        building1.room.printMajorDomus();
        building2.printRoom();
        building2.room.printMajorDomus();


    }
}
