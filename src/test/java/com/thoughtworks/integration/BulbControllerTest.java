package com.thoughtworks.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BulbControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void shouldBeAbleToCreateNewBulb() throws Exception {

        String json = "{\n" +
                "  \"title\" : \"my bulb\",\n" +
                "  \"summary\" : \"this is my summary\"\n" +
                "}";
        mvc.perform(MockMvcRequestBuilders.post("/bulb").content(json)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.title", is("my bulb")))
                .andExpect(jsonPath("$.summary", is("this is my summary")));
        ;
    }

    @Test
    public void shouldNotBeAbleToCreateNewBulbWithMissingInformation() throws Exception {
        String jsonWithoutSummary = "{\n" +
                "  \"title\" : \"my bulb\"\n" +
                "}";
        mvc.perform(MockMvcRequestBuilders.post("/bulb").content(jsonWithoutSummary)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().is4xxClientError());


        String jsonWithoutTitle = "{\n" +
                "  \"summary\" : \"my bulb\"\n" +
                "}";
        mvc.perform(MockMvcRequestBuilders.post("/bulb").content(jsonWithoutTitle)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().is4xxClientError());
    }

}
