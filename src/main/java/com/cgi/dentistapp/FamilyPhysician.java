package com.cgi.dentistapp;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
public class FamilyPhysician {

    private String firstName;

    private String lastName;

    public String getFullName(){
        return firstName + " " + lastName;
    }

    public static List<String> getFullNames(List<FamilyPhysician> physicians){
        return physicians.stream()
                .map(FamilyPhysician::getFullName)
                .collect(Collectors.toList());
    }
}
