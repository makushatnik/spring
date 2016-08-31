package com.cdesign.spittr.web;

import com.cdesign.spittr.web.controllers.HomeController;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * Created by RealXaker on 28.08.2016.
 */
public class HomeControllerTest {
    @Test
    public void testHomePage() throws Exception {
        HomeController controller = new HomeController();
        MockMvc mockMvc = standaloneSetup(controller).build();

        //assertEquals("home", controller.home());
        mockMvc.perform(get("/")).andExpect(view().name("home"));
    }
}
