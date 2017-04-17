package com.cgi.dentistapp.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetailedViewDTO {
    private Long id;

    @Size(min = 1, max = 50)
    private String dentistName;

    @Size(min = 1, max = 50)
    private String physicianName;

    @NotNull
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime visitBeginningDateTime;

    @NotNull
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime visitEndDateTime;
}
