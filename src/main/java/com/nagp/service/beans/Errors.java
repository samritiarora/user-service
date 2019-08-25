package com.nagp.service.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Errors implements Iterable<Error>
{
    @JsonProperty
    private Set<Error> errors = new LinkedHashSet<>();

    public Errors() {
    }


    @JsonIgnore
    public List<Error> getErrors() {
        return new ArrayList<>(errors);
    }

    @JsonIgnore
    public List<Error> getGlobalErrors() {
        return errors.stream().filter(e -> e.getProperty() == null)
                .collect(Collectors.toList());
    }

    @JsonIgnore
    public List<Error> getFieldErrors() {
        return errors.stream().filter(e -> e.getProperty() != null)
                .collect(Collectors.toList());
    }


    @JsonIgnore
    public Error getFieldError(String field) {
        return errors.stream().filter(e -> field != null && Objects.equals(e.getProperty(), field))
                .findFirst().orElse(null);
    }

    @JsonIgnore
    public List<Error> getFieldErrors(String field) {
        return errors.stream().filter(e -> field != null && Objects.equals(e.getProperty(), field))
                .collect(Collectors.toList());
    }

    public Error rejectValue(String errorCode, String developerMessage) {
        Error error = new Error(errorCode, developerMessage);
        errors.add(error);
        return error;
    }

    public Error rejectValue(String errorCode, String field, String developerMessage) {
        Error error = new Error(errorCode, field, developerMessage);
        errors.add(error);
        return error;
    }

    @JsonIgnore
    public int size() {
        return errors.size();
    }

    @JsonIgnore
    public boolean isEmpty() {
        return errors.isEmpty();
    }


    @Override
    public Iterator<Error> iterator() {
        return errors.iterator();
    }

    public String toString() {
        return errors.toString();
    }
}
