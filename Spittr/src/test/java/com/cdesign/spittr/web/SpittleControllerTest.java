package com.cdesign.spittr.web;

import com.cdesign.spittr.data.entity.Spittle;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by RealXaker on 28.08.2016.
 */
public class SpittleControllerTest {

    @Test
    public void shouldShowSpittles() throws Exception {
//        int max = 238900;
//        int cnt = 50;
//
//        List<Spittle> expectedSpittles = createSpittleList(cnt);
//        SpittleRepository mockRepository = mock(SpittleRepository.class);
//        when(mockRepository.findSpittles(max, cnt)).thenReturn(expectedSpittles);
//
//        SpittleController controller = new SpittleController(mockRepository);
//        MockMvc mockMvc = standaloneSetup(controller)
//                .setSingleView(new InternalResourceView("WEB-INF/views/spittles.jsp"))
//                .build();
//
//        mockMvc.perform(get("/spittles?max=" + max + "&cnt=" + cnt))
//                .andExpect(view().name("spittles"))
//                .andExpect(model().attributeExists("spittleList"))
//                .andExpect(model().attribute("spittleList",
//                        hasItems(expectedSpittles.toArray())));
    }

    @Test
    public void testSpittle() throws Exception {
//        int id = 12345;
//
//        Spittle spittle = new Spittle("Hello", new Date());
//        SpittleRepository mockRepository = mock(SpittleRepository.class);
//        when(mockRepository.findOne(id)).thenReturn(spittle);
//
//        SpittleController controller = new SpittleController(mockRepository);
//        MockMvc mockMvc = standaloneSetup(controller)
//                .build();
//
//        mockMvc.perform(get("/spittles/" + id))
//                .andExpect(view().name("spittle"))
//                .andExpect(model().attributeExists("spittle"))
//                .andExpect(model().attribute("spittle", spittle));
    }

    private List<Spittle> createSpittleList(int cnt) {
        List<Spittle> spittles = new ArrayList<>();
        for (int i=0; i < cnt; i++) {
            spittles.add(new Spittle("Spittle " + i, new Date()));
        }
        return spittles;
    }
}
