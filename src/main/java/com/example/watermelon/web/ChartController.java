package com.example.watermelon.web;

import com.example.watermelon.dto.ChartResponseDto;
import com.example.watermelon.service.ChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
public class ChartController {
    @Autowired
    ChartService chartService;

    @GetMapping(path = "/chart")
    public List<ChartResponseDto> Chart() {
        return chartService.getChartList();
    }
}
