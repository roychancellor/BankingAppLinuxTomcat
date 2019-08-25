package edu.gcu.cst341.spring;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@Scope("session")
@SessionAttributes("username")  //gives access to the name attribute in any page we access in this controller
public class LoginController {
	//Allows Spring to take over control of making these objects
	@Autowired
	StudentService StudentService;
	@Autowired
	LoginService LoginService;
	
	@ModelAttribute("username")
	public String username() {
		return null;
	}
	
	//NOTE: return statements are names of .jsp files
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String showLoginScreen() {
		//Open the .jsp file
		return "login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String processLoginScreen(@RequestParam String username, @RequestParam String password, ModelMap map, HttpSession s) {
		//Keeps track of the username to be able to pass to other methods
		//The username can either be "admin" or "faculty"
		map.put("username", username);
		if(LoginService.isValidCredentials(username, password)) {
			//Force the browser to GO TO THE URL /studentlist, not run the .jsp file
			return "redirect:studentlist";
		}
		map.put("errormessage", "Invalid login credentials");
		//Open the .jsp file
		return "login";
	}
	
	@RequestMapping(value = "/studentlist", method = RequestMethod.GET)
	public String showListScreen(@ModelAttribute("username") String username, ModelMap map, HttpSession s) {
		String jspToAccess = null;
		
		//Determine which page to open depending on who is logged in
		if(username.equals("admin")) {
			//Open the .jsp file
			jspToAccess = "studentlistadmin";
		}
		else if(username.equals("faculty")) {
			//Open the .jsp file
			jspToAccess = "studentlistfaculty";
		}
		else {
			//open the login.jsp file
			jspToAccess = "login";
		}
		
		map.addAttribute("stulist", StudentService.getStudentList("admin"));
		return jspToAccess;
	}
	
	/**
	 * Opens the addstudent.jsp file if admin is logged in or else returns to login
	 * @param m the ModelMap
	 * @return a redirect to the studentlistadmin page
	 */
	@RequestMapping(value="/addstudent", method = RequestMethod.GET)
	public String processAddStudent(ModelMap m) {
		if(m.get("username").equals("admin")) {
			//Open the .jsp file
			return "addstudent";
		}
		else {
			//Open the login.jsp file
			return "login";
		}
	}

	/**
	 * Adds a new student to the admin list
	 * @param lastname student last name
	 * @param firstname student first name
	 * @param username the username of the logged-in user
	 * @param map a ModelMap object
	 * @param s an HttpSession object
	 * @return a redirect to the student list page appropriate for the logged-in user
	 */
	@RequestMapping(value="/addstudent", method = RequestMethod.POST)
	public String processReturnToList(
		@RequestParam String lastname,
		@RequestParam String firstname,
		@ModelAttribute("username") String username,
		ModelMap map, HttpSession s) {
		String jspToReturn = "login";
		
		if(username.equals("admin")) {
			StudentService.addStudentToRoster(lastname, firstname, false, username);
			//Redirect to the student list page for admin
			jspToReturn = "redirect:studentlist";
		}
		else if(map.get("username").equals("faculty")) {
			//Redirect to the student list page for faculty
			jspToReturn = "redirect:studentlist";
		}
		
		return jspToReturn;
	}
	
	private static final int ENROLL = 1;
	private static final int DELETE = -1;
	/**
	 * If admin logged in, calls enrollStudent; otherwise, just shows the student list
	 * @param id the index number of the student in the student list
	 * @param map a ModelMap object
	 * @return a redirect to the student list appropriate for the logged-in user
	 */
	@RequestMapping(value="/enroll", method=RequestMethod.GET)
	public String processEnrollStudent(@RequestParam int id, ModelMap map) {
		return processEnrollDelete((String)map.get("username"), id, ENROLL);
	}

	/**
	 * If admin logged in, calls enrollStudent; otherwise, just shows the student list
	 * @param id the index number of the student in the student list
	 * @param map a ModelMap object
	 * @return a redirect to the student list appropriate for the logged-in user
	 */
	@RequestMapping(value="/delete", method=RequestMethod.GET)
	public String processDeleteStudent(@RequestParam int id, ModelMap map) {
		return processEnrollDelete((String)map.get("username"), id, DELETE);
	}
	
	/**
	 * Helper method to process a student enrollmeent or deletion
	 * @param username the username of the logged-in user
	 * @param id the index of the student in the student list
	 * @param action an integer that if > 0 will cause an enrollment or < 0 will cause a deletion
	 * @return a string representing either a redirect or a jsp file for the caller to return
	 */
	private String processEnrollDelete(String username, int id, int action) {
		String jspToReturn = "redirect:studentlist";
		
		//If admin is logged in, perform the action
		if(username.equals("admin")) {
			if(action > 0) {
				StudentService.enrollStudent(id);
			}
			else if(action < 0) {
				StudentService.removeStudentFromRoster(id);
			}
		}
		else if(!username.equals("faculty") && !username.equals("admin")) {
			jspToReturn = "login";
		}
		
		return jspToReturn;
	}
}
