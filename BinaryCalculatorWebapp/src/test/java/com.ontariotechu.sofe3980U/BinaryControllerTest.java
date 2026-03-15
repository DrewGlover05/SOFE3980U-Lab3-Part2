package com.ontariotechu.sofe3980U;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@WebMvcTest(BinaryController.class)
public class BinaryControllerTest {

    @Autowired
    private MockMvc mvc;

   
    @Test
    public void getDefault() throws Exception {
        this.mvc.perform(get("/"))//.andDo(print())
            .andExpect(status().isOk())
            .andExpect(view().name("calculator"))
			.andExpect(model().attribute("operand1", ""))
			.andExpect(model().attribute("operand1Focused", false));
    }
	
	    @Test
    public void getParameter() throws Exception {
        this.mvc.perform(get("/").param("operand1","111"))
            .andExpect(status().isOk())
            .andExpect(view().name("calculator"))
			.andExpect(model().attribute("operand1", "111"))
			.andExpect(model().attribute("operand1Focused", true));
    }
	@Test
	    public void postParameter() throws Exception {
        this.mvc.perform(post("/").param("operand1","111").param("operator","+").param("operand2","111"))//.andDo(print())
            .andExpect(status().isOk())
            .andExpect(view().name("result"))
			.andExpect(model().attribute("result", "1110"))
			.andExpect(model().attribute("operand1", "111"));
    }
    
    /* 
        New Test Cases for Addition
    */ 
    @Test
    public void testAddWithZero() throws Exception {
        mvc.perform(post("/")
            .param("operand1", "0")
            .param("operator", "+")
            .param("operand2", "1010"))
        .andExpect(status().isOk())
        .andExpect(view().name("result"))
        .andExpect(model().attribute("result", "1010"));
    }

    @Test
    public void testAddLargeNumbers() throws Exception {
        mvc.perform(post("/")
            .param("operand1", "11111111")
            .param("operator", "+")
            .param("operand2", "1"))
        .andExpect(status().isOk())
        .andExpect(view().name("result"))
        .andExpect(model().attribute("result", "100000000"));
    }

    @Test
    public void testAddDifferentLengths() throws Exception {
        mvc.perform(post("/")
            .param("operand1", "1010")
            .param("operator", "+")
            .param("operand2", "11"))
        .andExpect(status().isOk())
        .andExpect(view().name("result"))
        .andExpect(model().attribute("result", "1101"));
    }

    /* 
        New Test cases for Subtraction, Multiplication, and Division
    */

    // test or
    @Test
    public void testOr() throws Exception {
        mvc.perform(post("/")
            .param("operand1", "1010")
            .param("operator", "|")
            .param("operand2", "11"))
        .andExpect(status().isOk())
        .andExpect(view().name("result"))
        .andExpect(model().attribute("result", "1011"));
    }

    // test multiplication
    @Test
    public void testMultiply() throws Exception {
        mvc.perform(post("/")
            .param("operand1", "1010")
            .param("operator", "*")
            .param("operand2", "11"))
        .andExpect(status().isOk())
        .andExpect(view().name("result"))
        .andExpect(model().attribute("result", "11110"));
    }

    //test and
    @Test
    public void testAnd() throws Exception {
        mvc.perform(post("/")
            .param("operand1", "1010")
            .param("operator", "&")
            .param("operand2", "11"))
        .andExpect(status().isOk())
        .andExpect(view().name("result"))
        .andExpect(model().attribute("result", "10"));
    }
}
