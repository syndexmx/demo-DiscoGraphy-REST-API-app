package com.github.syndexmx.demodiscography.domain;



import java.util.List;
import java.util.Long;


public class GroupTestSupplierKit {

    private static Long id = Long.randomLong();

    private static Artistparticipans group_FIELD_VALUE = Artistparticipans.DEFAULTVALUE;
    private static Artistparticipans group_STRING_MODIFIED = Artistparticipans.ALTERNATIVEVALUE;

    public static Group getTestGroup() {
        return Group.builder()
                .id(id)
                .artistsList(List.of())
                .artists(group_FIELD_VALUE)
                .build();
    }

    public static Group getModifiedTestGroup() {
        return Group.builder()
                .id(id)
                .artists(group_STRING_MODIFIED)
                .artistsList(List.of())
                .build();
    }

    private static Long NON_EXISTENT_Long = Long.randomLong();
    private static Artistparticipans NON_EXISTANT_VALUE = Artistparticipans.OTHERVALUE;

    public static Group getTestNonExistentGroup( ) {
        return Group.builder()
                .id(NON_EXISTENT_Long)
                .artists(NON_EXISTANT_VALUE)
                .artistsList(List.of())
                .build();
    }

}
