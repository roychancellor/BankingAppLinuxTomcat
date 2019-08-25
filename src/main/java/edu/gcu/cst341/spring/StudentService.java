package edu.gcu.cst341.spring;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class StudentService {
	private static List<Student> studentList = new ArrayList<Student>();
	private static int studentCount = 0;
	
	/**
	 * Gets a list of Student objects for the passed-in username
	 * @param username the username f the logged-in user
	 * @return a list of Student objects for the user
	 */
	public List<Student> getStudentList(String username) {
		List<Student> list = new ArrayList<Student>();
		
		for(Student stu : studentList) {
			if(stu.getUsername().equals(username)) {
				list.add(stu);
			}
		}
		
		return list;
	}
	
	/**
	 * Adds a new student to the student list for the username
	 * @param lastname
	 * @param firstname
	 * @param enrolled
	 * @param username
	 */
	public void addStudentToRoster(String lastname, String firstname, boolean enrolled, String username) {
		studentList.add(new Student(studentCount++, lastname, firstname, enrolled, username));
	}
	
	/**
	 * Removes a student from the roster list
	 * @param id the index in the roster list to remove
	 */
	public void removeStudentFromRoster(int id) {
		Iterator<Student> it = studentList.iterator();
		while(it.hasNext()) {
			Student student = it.next();
			if(student.getId() == id) {
				it.remove();
			}
		}
	}

	/**
	 * Sets the enrolled flag to true for the user at index id
	 * @param id the index in the roster list
	 */
	public void enrollStudent(int id) {
		Iterator<Student> it = studentList.iterator();
		while(it.hasNext()) {
			Student student = it.next();
			if(student.getId() == id) {
				student.setEnrolled(true);
			}
		}
	}
}

