package br.com.curso.aws_project01.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {
    private static final Logger LOG = (Logger) LoggerFactory.getLogger(TestController.class);

    @GetMapping("/dog/{name}")
    public ResponseEntity<?> dogTest(@PathVariable String name){
        LOG.info("Test controller - dog: {}" + name);

        return ResponseEntity.ok("Dog: " + name);
    }

    @GetMapping("/pessoa/{name}")
    public ResponseEntity<?> pessoaTest(@PathVariable String name){
        LOG.info("Test controller - pessoa: {}" + name);

        return ResponseEntity.ok("Pessoa: " + name);
    }

    @GetMapping("/objeto/{name}")
    public ResponseEntity<?> objetoTest(@PathVariable String name){
        LOG.info("Test controller - objeto: {}" + name);

        return ResponseEntity.ok("objeto: " + name);
    }
}