package com.example.ch1avro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ch1avro.domain.UserJson;
import com.example.ch1avro.mapper.UserAvroMapper;
import com.example.ch1avro.service.UserAvroService;
import com.example.ch1avro.utils.CompareUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import user.avro.UserAvro;

@RestController
public class UserController {

    @Autowired
    private UserAvroService userAvroService;

    @GetMapping("/compare")
    public void compareSize() throws Exception {
        System.out.println("--- size compare ---");
        CompareUtils.compareSizes();

        System.out.println("\n--- serialize time compare ---");
        CompareUtils.compareSerializeTime();

        System.out.println("\n--- deserialize time compare ---");
        CompareUtils.compareDeserializeTime();
    }

    @GetMapping("/getJsonUser")
    public ResponseEntity<byte[]> getUserJson() throws Exception {
        UserJson userJson = new UserJson("name", 41, "yellow");
        byte[] jsonBytes = new ObjectMapper().writeValueAsBytes(userJson);
        return ResponseEntity
                .ok()
                .contentLength(jsonBytes.length)
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonBytes);
    }

    @GetMapping("/getAvroUser")
    public byte[] getAvroUser() throws Exception {
        UserAvro user = new UserAvro("name", 41, "yellow");

        return UserAvroMapper.avroSerializeUser(user);
    }

    @GetMapping("/changeAvroUserVersion")
    public void changeAvroUserVersion() throws Exception {

        userAvroService.changeAvroUserVersion();
    }
}
