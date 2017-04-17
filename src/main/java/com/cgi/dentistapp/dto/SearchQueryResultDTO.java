package com.cgi.dentistapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchQueryResultDTO {

    private Long id;

    private String nid;

    private String dentistName;

    private String physicianName;

    private LocalDateTime visitBeginningDateTime;

    private LocalDateTime visitEndDateTime;
}
