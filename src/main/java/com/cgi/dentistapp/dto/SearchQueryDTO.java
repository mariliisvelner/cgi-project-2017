package com.cgi.dentistapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * The object which is used to carry the user's query inputs.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchQueryDTO {

    @Size(max = 11)
    private String nid;

    @Size(max = 50)
    private String dentistName;

    @Size(max = 50)
    private String physicianName;

    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime visitBeginningDateTime;

    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime visitEndDateTime;
}
