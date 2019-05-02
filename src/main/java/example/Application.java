package example;

import io.micronaut.context.ApplicationContext;
import io.micronaut.context.scope.CustomScope;
import io.micronaut.context.scope.CustomScopeRegistry;

import java.util.Optional;

public class Application {

    private static ApplicationContext context;


    public static void main(String[] args) {
        context = ApplicationContext.run();
        System.out.println("Application.main");
        System.out.println("args = [" + args + "]");

        CurrentRoomScope bean = context.getBean(CurrentRoomScope.class);
        Building building1 = context.getBean(Building.class);
        Building building2 = context.getBean(Building.class);
//        CustomScopeRegistry customScopeRegistry = context.getBean(CustomScopeRegistry.class);
//        Optional<CustomScope> scope = customScopeRegistry.findScope(CurrentRoom.class);


        building1.print();
        System.out.println();
        building2.print();

    }
}
