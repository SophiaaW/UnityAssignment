/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package hello;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProjectControllerTests {

    @Autowired
    private MockMvc mockMvc;

//    @Test
//    public void generateLargeNumberOfProjects() throws Exception {
//    	for (int i=0; i<100; i++) {
//    		Calendar cal = Calendar.getInstance();
//    		Date date = cal.getTime();             
//    		SimpleDateFormat format = new SimpleDateFormat("MMddyyyy HH:mm:ss");
//    		Project project = new Project();
//    	    project.setId(i);
//    	    project.setCreationDate(format.format(date));
//    	    cal.add(Calendar.DATE, 1);
//    	    Date date2 = cal.getTime();
//    	    project.setExpiryDate(format.format(date2));
//    	    project.setProjectCost(Math.random());
//    	    project.setEnabled(true);
//    	    project.setProjectName("Project Name: " + i);
//    	    project.setProjectUrl("http://www.unity3d.com/project"+i);
//    	   
//    	    String json = new ObjectMapper().writeValueAsString(project).toString();
//    		this.mockMvc.perform(post("/createProject")
//    				.contentType(MediaType.APPLICATION_JSON)
//    				.content(json)).andExpect(status().isOk()).andReturn();
//    	}
//    }
    
//    @Test
//    public void invalidDataTest() throws Exception {
//    	Calendar cal = Calendar.getInstance();
//		Date date = cal.getTime();             
//		SimpleDateFormat format = new SimpleDateFormat("MMddyyyy HH:mm:ss");
//		Project project = new Project();
//	    project.setId(200);
//	    project.setCreationDate(format.format(date));
//	    project.setExpiryDate("12022016");
//	    project.setProjectCost(Math.random());
//	    project.setEnabled(true);
//	    project.setProjectName("Project Name: 200");
//	    project.setProjectUrl("http://www.unity3d.com/project200");
//	    
//	    String json = new ObjectMapper().writeValueAsString(project).toString();
//		this.mockMvc.perform(post("/createProject")
//				.contentType(MediaType.APPLICATION_JSON)
//				.content(json)).andExpect(status().is4xxClientError()).andReturn();
////				.content(json)).andExpect(status().is5xxServerError()).andReturn();
//    }
//    
//    @Test
//    public void negativeTest() throws Exception {
//    	this.mockMvc.perform(get("/requestProject")
//    			.param("projectId", "invalid"))
//    	.andExpect(status().isOk()).andExpect(model().size(0)).andReturn();
//    }
//    @Test
//    public void oneParam() throws Exception{
//    	this.mockMvc.perform(get("/requestProject").param("projectId", "1"))
//    	.andExpect(status().isOk()).andExpect(model().size(1)).andReturn();
//    }
//    
//    @Test
//    public void twoParam() throws Exception{
//    	this.mockMvc.perform(get("/requestProject").param("projectId", "1").param("keyword", "movie"))
//    	.andExpect(status().isOk()).andExpect(jsonPath("projectId").value("1"))
//    	.andExpect(jsonPath("keyword").value("movie"))
//    	.andReturn();
//    }
    
//    @Test
//    public void threeParam() throws Exception{
//    	this.mockMvc.perform(get("/requestProject").param("country", "USA").param("keyword", "movie"))
//    	.andExpect(status().isOk())
//    	.andExpect(jsonPath("$[0].countries",containsInAnyOrder("USA"))
//    	.andExpect(jsonPath("projectId").value("1"))
//    	.andExpect(jsonPath("keyword").value("movie"))
//    	.andReturn();
//    }
//    
    
    @Test
    public void noParamGreetingShouldReturnDefaultMessage() throws Exception {

        this.mockMvc.perform(get("/greeting")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("Hello, World!"));
    }

    @Test
    public void paramGreetingShouldReturnTailoredMessage() throws Exception {

        this.mockMvc.perform(get("/greeting").param("name", "Spring Community"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("Hello, Spring Community!"));
    }

}
