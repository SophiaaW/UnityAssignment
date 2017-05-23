package hello;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.apache.log4j.spi.LoggerFactory;
import org.junit.Assert;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class ProjectController {
	
	Logger logger = Logger.getGlobal();
	
	@RequestMapping(value="/requestProject", method=RequestMethod.GET)
	public ResponseEntity<Response> requestProject(@RequestParam(value="projectId", required=false, defaultValue = "-1") Integer projectId, @RequestParam(value="country", required=false, defaultValue = "null") String country, @RequestParam(value="number", required=false, defaultValue="-1") Integer number, @RequestParam(value="keyword", required=false, defaultValue="null") String keyword) {

		logger.log(Level.INFO, "Request Path info: /requestProject");
		logger.log(Level.INFO, "Request Method: GET");
		logger.log(Level.INFO, "Request parameters: ");
		logger.log(Level.INFO, "project id : " + (projectId==-1?"":projectId));
		logger.log(Level.INFO, "country : " + (country=="null"?"":country));
		logger.log(Level.INFO, "number : " + (number==-1?"":number));
		logger.log(Level.INFO, "keyword : " + (keyword=="null"?"":keyword));
		
		List<Project> projects = null;
			try {
				projects = readProjectFromFile("projects.txt");
			} catch (IOException e) {
				ResponseMessage rm = new ResponseMessage();
				rm.setMessage(e.getMessage());
				logger.log(Level.INFO, "Response Code: " + HttpStatus.INTERNAL_SERVER_ERROR);
				logger.log(Level.INFO, "Response Body: " + rm.getMessage());
				return new ResponseEntity<Response>(rm, HttpStatus.INTERNAL_SERVER_ERROR);  
			}
			List<Project> results = projects.stream()
					.filter(project -> parseDate(project.getExpiryDate()).after(Calendar.getInstance()))
					.filter(project -> project.isEnabled())
					.filter(project -> project.getProjectUrl() != null)
					.sorted((p1,p2) -> Double.compare(p2.getProjectCost(), p1.getProjectCost()))
					.filter(project -> project.getId().equals(projectId) || projectId.equals(-1))
					.collect(Collectors.toList());
			
			if(results.size()==1 && results.get(0).getId()==projectId) {
				ResponseObject ro = new ResponseObject();
				ro.setProjectName(results.get(0).getProjectName());
				ro.setProjectCost(results.get(0).getProjectCost());
				ro.setProjectUrl(results.get(0).getProjectUrl());
				logger.log(Level.INFO, "Response Code: " + HttpStatus.OK);
				logger.log(Level.INFO, "Response Body: " + ro.toString());
				return new ResponseEntity<Response>(ro, HttpStatus.OK);
			}
			
			results = results.stream()
					.filter(project -> project.getTargetCountries().contains(country) || country.equals("null"))
					.filter(project -> project.getTargetKeys().stream().filter(targetKey -> targetKey.getNumber() < number).collect(Collectors.toList()).size() == 0 || number.equals(-1))
					.filter(project -> project.getTargetKeys().stream().filter(targetKey -> targetKey.getKeyword().equals(keyword)).collect(Collectors.toList()).size() >0 || keyword.equals("null"))
					.collect(Collectors.toList());
			
			if (results.size() > 0) {
				ResponseObject ro = new ResponseObject();
				ro.setProjectName(results.get(0).getProjectName());
				ro.setProjectCost(results.get(0).getProjectCost());
				ro.setProjectUrl(results.get(0).getProjectUrl());
				logger.log(Level.INFO, "Response Code: " + HttpStatus.OK);
				logger.log(Level.INFO, "Response Body: " + ro.toString());
				return new ResponseEntity<Response>(ro, HttpStatus.OK);
			}
			
			ResponseMessage rm = new ResponseMessage();
			rm.setMessage("no project found");
			logger.log(Level.INFO, "Response Code: " + HttpStatus.NOT_FOUND);
			logger.log(Level.INFO, "Response Body: " + rm.getMessage());
			return new ResponseEntity<Response>(rm, HttpStatus.NOT_FOUND);  
	}
	
	@RequestMapping(value="/createProject", method=RequestMethod.POST)
	public ResponseEntity<Response> createProject(@RequestBody Project project) {
		try {
			logger.log(Level.INFO, "Request Path info: /createProject");
			logger.log(Level.INFO, "Request Method: POST");
			logger.log(Level.INFO, "Request Body: " + new ObjectMapper().writeValueAsString(project).toString());
			
			saveProjectToFile(project);
			ResponseMessage rm = new ResponseMessage();
			rm.setMessage("campaign is successfully created");
			logger.log(Level.INFO, "Response Code: " + HttpStatus.OK);
			logger.log(Level.INFO, "Response Body: " + rm.getMessage());
			return new ResponseEntity(rm, HttpStatus.OK);              
		} catch(DuplicateException e) {
			ResponseMessage rm = new ResponseMessage();
			rm.setMessage(e.getMessage());
			logger.log(Level.INFO, "Response Code: " + HttpStatus.BAD_REQUEST);
			logger.log(Level.INFO, "Response Body: " + rm.getMessage());
			return new ResponseEntity(rm, HttpStatus.BAD_REQUEST);   
		} catch(IOException e) {
			ResponseMessage rm = new ResponseMessage();
			rm.setMessage(e.getMessage());
			logger.log(Level.INFO, "Response Code: " + HttpStatus.INTERNAL_SERVER_ERROR);
			logger.log(Level.INFO, "Response Body: " + rm.getMessage());
			return new ResponseEntity(rm, HttpStatus.INTERNAL_SERVER_ERROR);   
		}
	}
	
	private Calendar parseDate(String datetime) {
		  Calendar cal = Calendar.getInstance();
		  SimpleDateFormat sdf = new SimpleDateFormat("MMddyyyy HH:mm:ss");
		  try {
		   cal.setTime(sdf.parse(datetime));
		  } catch (Exception e) {
		   // TODO Auto-generated catch block
		   cal.set(Calendar.DATE, -1);
		  }
		  return cal;
		 }
	
	private List<Project> readProjectFromFile(String fileName) throws IOException {
		InputStream is = new FileInputStream(fileName); 
		BufferedReader buf = new BufferedReader(new InputStreamReader(is)); 
		String line = buf.readLine(); 
		StringBuilder sb = new StringBuilder(); 
		while(line != null) { 
			sb.append(line); 
			line = buf.readLine(); 
		} 
		String fileAsString = sb.toString();
		TypeReference<List<Project>> type = new TypeReference<List<Project>>() {};
		return new ObjectMapper().readValue(fileAsString, type);
	}
	
	private void saveProjectToFile(Project project) throws IOException, DuplicateException {
			List<Project> projects = readProjectFromFile("projects.txt");
			if (projects.stream().anyMatch(p -> p.getId().equals(project.getId()))) {
				throw new DuplicateException("duplicate project id");
			}
			projects.add(project);
			FileOutputStream stream = new FileOutputStream("projects.txt");

			// get the content in bytes
			byte[] contentInBytes = new ObjectMapper().writeValueAsString(projects).toString().getBytes();

			stream.write(contentInBytes);
			stream.flush();
			stream.close();
			
			if (stream != null) {
				stream.close();
			}
	}
 }