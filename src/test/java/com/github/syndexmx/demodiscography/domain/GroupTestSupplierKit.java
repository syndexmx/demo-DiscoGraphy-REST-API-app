package com.github.syndexmx.demodiscography.domain;



import java.util.List;
import java.util.Random;


public class GroupTestSupplierKit {

    private static Random random = new Random();
    private static Long id = random.nextLong();


    public static Group getTestGroup() {
        return Group.builder()
                .id(id)
                .name("Queen")
                .place("London")
                .synonym("The Queen")
                .year(1970)
                .artistsList(List.of())
                .build();
    }

    public static Group getModifiedTestGroup() {
        return Group.builder()
                .id(id)
                .name("Queen")
                .place("London, UK")
                .synonym("The Queen")
                .year(1970)
                .artistsList(List.of())
                .artistsList(List.of())
                .build();
    }

    private static Long NON_EXISTENT_Long = random.nextLong();

    public static Group getTestNonExistentGroup( ) {
        return Group.builder()
                .id(NON_EXISTENT_Long)
                .artistsList(List.of())
                .build();
    }

}
