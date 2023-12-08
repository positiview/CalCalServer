package com.example.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class RouteAndTimeDTO {

    private Double latitude;

    private Double longitude;

    private Double time;

}
