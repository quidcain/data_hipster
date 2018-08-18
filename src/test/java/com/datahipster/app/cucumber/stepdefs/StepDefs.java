package com.datahipster.app.cucumber.stepdefs;

import com.datahipster.app.DatahipsterApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = DatahipsterApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
