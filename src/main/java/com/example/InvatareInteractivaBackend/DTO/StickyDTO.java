package com.example.InvatareInteractivaBackend.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StickyDTO {

    private Long id;
    private String title;
    private String description;
    private String categoryName;

}
