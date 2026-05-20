package com.roncalho.financial_portfolio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private DataSource dataSource;

    @GetMapping("/db")
    public ResponseEntity<String> testDb() {
        try {
            Connection conn = dataSource.getConnection();
            String url = conn.getMetaData().getURL();
            conn.close();
            return ResponseEntity.ok("✅ Banco: " + url);
        } catch (SQLException e) {
            return ResponseEntity.status(500).body("❌ Erro: " + e.getMessage());
        }
    }
}
