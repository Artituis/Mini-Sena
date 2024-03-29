package br.arturslampert.minisena.exeptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionMessageDto {
    private String message;
    private String field;
}
