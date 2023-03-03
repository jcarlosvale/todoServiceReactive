package com.study.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class TodoInfo {

    @Id
    private String todoInfoId;
    @NotBlank(message = "description must be present")
    private String description;
    private LocalDate todoDate;

}
