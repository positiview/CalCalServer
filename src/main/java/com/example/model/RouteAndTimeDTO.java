package com.example.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class RouteAndTimeDTO {

    private Long latitude;

    private Long longitude;

    private Long time;

}
