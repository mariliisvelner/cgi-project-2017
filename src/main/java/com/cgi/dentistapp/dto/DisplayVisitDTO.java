package com.cgi.dentistapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The object which is used to display visits in the visits page.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DisplayVisitDTO {

    private Long id;

    private String nid;

    private String dentistName;

    private String physicianName;

    private String visitBeginningDateTime;

    private String visitEndDateTime;
}
