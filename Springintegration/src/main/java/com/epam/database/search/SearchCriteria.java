package com.epam.database.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class SearchCriteria {
    private String fieldName;
    private String value;
}
