package com.micoinmusic.controllers;

import org.junit.Rule;
import org.junit.Test;
import org.springframework.boot.test.rule.OutputCapture;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class HelloBootControllerTest {

    @Rule
    public OutputCapture outputCapture = new OutputCapture();

    @Test
    public void shouldSayHi() {
        assertThat(new HelloBootController().sayHi(), is("hello"));
        this.outputCapture.expect(containsString("hello"));
    }
}