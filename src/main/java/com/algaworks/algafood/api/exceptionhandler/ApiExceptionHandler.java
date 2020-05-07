package com.algaworks.algafood.api.exceptionhandler;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    final MessageSource messageSource;
    final String MSG_ERRO_BEAN_VALIDATION = "Um ou mais campos informados são inválidos. " +
            "Corrija-os e tente novamente.";
    final String MSG_ERRO_GENERICO = "Ocorreu um erro inesperado no sistema. " +
            "Tente novamente e, se o problema persistir, entre em contato com o administrador.";

    public ApiExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        return handleValidationException(ex, headers, status, request, ex.getBindingResult());
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status,
                                                         WebRequest request) {
        return handleValidationException(ex, headers, status, request, ex.getBindingResult());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {

        ApiProblem problem = createApiProblem(status, MSG_ERRO_GENERICO, request, ApiProblemType.MENSAGEM_INCOMPREENSIVEL)
            .build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
                                                                      HttpHeaders headers, HttpStatus status,
                                                                      WebRequest request) {
        return ResponseEntity.status(status).headers(headers).build();
    }

    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<Object> handleAccessDenied(AccessDeniedException ex, WebRequest request) {
        var message = "Você não possui permissão para executar esta operação.";
        var status = HttpStatus.FORBIDDEN;
        ApiProblem problem = createApiProblem(status, message, request, ApiProblemType.ACESSO_NEGADO)
                .build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;

        List<ApiProblem.Detail> details =
                ex.getConstraintViolations().stream()
                        .map(constraintViolation -> {

                            String message = constraintViolation.getMessage();
                            String name = constraintViolation.getPropertyPath().toString();

                            return ApiProblem.Detail.builder()
                                    .name(name)
                                    .userMessage(message)
                                    .build();

                        }).collect(Collectors.toList());

        ApiProblem problem = createApiProblem(status, MSG_ERRO_BEAN_VALIDATION, request, ApiProblemType.DADOS_INVALIDOS)
                .details(details)
                .build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<Object> handleEntidadeNaoEncontrada(EntidadeNaoEncontradaException ex, WebRequest request) {

        HttpStatus status = HttpStatus.NOT_FOUND;
        String detail = ex.getMessage();

        ApiProblem problem = createApiProblem(status, detail, request, ApiProblemType.RECURSO_NAO_ENCONTRADO)
                .build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<Object> handleNegocio(NegocioException ex, WebRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        String detail = ex.getMessage();

        ApiProblem problem = createApiProblem(status, detail, request, ApiProblemType.ERRO_NEGOCIO)
                .build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

//    @ExceptionHandler(ChaveDuplicadaException.class)
//    public ResponseEntity handleChaveDuplicada(ChaveDuplicadaException ex, WebRequest request) {
//
//        HttpStatus status = HttpStatus.BAD_REQUEST;
//        String detail = ex.getMessage();
//
//        ApiProblem problem = createApiProblem(status, detail, request)
//                .build();
//
//        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
//    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUncaughtException(Exception ex, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        log.error(ex.getMessage(), ex);

        ApiProblem problem = createApiProblem(status, MSG_ERRO_GENERICO, request, ApiProblemType.ERRO_DE_SISTEMA)
                .build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    private ResponseEntity<Object> handleValidationException(Exception ex, HttpHeaders headers, HttpStatus status,
                                                             WebRequest request,
                                                             BindingResult bindingResult) {

        List<ApiProblem.Detail> details  =

                bindingResult.getAllErrors().stream()
                        .map(objectError -> {
                            String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());
                            String name = (objectError instanceof FieldError) ?
                                    ((FieldError) objectError).getField() : objectError.getObjectName();

                            if (message.matches("(.*)\\[\\d+\\](.*)"))
                                message = objectError.getDefaultMessage();

                            return ApiProblem.Detail.builder()
                                    .name(name)
                                    .userMessage(message)
                                    .build();

                        }).collect(Collectors.toList());

        ApiProblem problem = createApiProblem(status, MSG_ERRO_BEAN_VALIDATION, request, ApiProblemType.DADOS_INVALIDOS)
                .details(details)
                .build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    private ApiProblem.ApiProblemBuilder createApiProblem(HttpStatus status, String detail, WebRequest request,
                                                          ApiProblemType type) {

        return ApiProblem.builder()
                .timestamp(OffsetDateTime.now())
                .status(status.value())
                .title(type.getTitle())
                .type(type.getUri())
                .uri(((ServletWebRequest)request).getRequest().getRequestURI())
                .userMessage(detail);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {

        if (body == null) {
            body = ApiProblem.builder()
                    .timestamp(OffsetDateTime.now())
                    .title(status.getReasonPhrase())
                    .status(status.value())
                    .userMessage(MSG_ERRO_GENERICO)
                    .build();
        } else if (body instanceof String) {
            body = ApiProblem.builder()
                    .timestamp(OffsetDateTime.now())
                    .title((String) body)
                    .status(status.value())
                    .userMessage(MSG_ERRO_GENERICO)
                    .build();
        }

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }
}