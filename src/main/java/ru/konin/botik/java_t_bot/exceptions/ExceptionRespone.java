package ru.konin.botik.java_t_bot.exceptions;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@Accessors
public class ExceptionRespone {

    private Long code;
    private String message;

}
