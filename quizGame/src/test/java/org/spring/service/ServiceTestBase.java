package org.spring.service;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class ServiceTestBase {
    @Autowired
    private ResetEjb resetService;

    @BeforeEach
    public void resetDatabase() {
        resetService.resetDatabase();
    }
}
