package com.microservicetemplate.rest.controllers.query_params;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TemplateDomainQueryParams {
    public String templateString;
}
