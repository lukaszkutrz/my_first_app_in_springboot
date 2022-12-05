package library.helpers;

import library.enums.BookCategory;
import library.enums.LibraryUserRoleType;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

public class EnumsHelper {

    public static List<String> getAllBookCategories(){
       return new ArrayList<>(EnumSet.allOf(BookCategory.class)).stream().map(x -> x.name().toLowerCase()).collect(Collectors.toList());
    }

    public static List<String> getAlLibraryUserRoleTypes(){
        return new ArrayList<>(EnumSet.allOf(LibraryUserRoleType.class)).stream().map(x-> x.name().toLowerCase()).collect(Collectors.toList());
    }
}
