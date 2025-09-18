package com.example.ch1avro.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ch1avro.utils.CompareUtils;

@RestController
public class UserController {

    @GetMapping("/compareSize")
    public void compareSize() throws Exception {
        System.out.println("--- size compare ---");
        CompareUtils.compareSizes();

        System.out.println("\n--- serialize time compare ---");
        CompareUtils.compareSerializeTime();

        System.out.println("\n--- deserialize time compare ---");
        CompareUtils.compareDeserializeTime();
    }
}
