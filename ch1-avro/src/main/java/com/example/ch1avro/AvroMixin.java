package com.example.ch1avro;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({ "specificData" })
public abstract class AvroMixin {}
