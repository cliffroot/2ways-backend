package two.ways.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import two.ways.dao.ProjectDao;
import two.ways.model.Project;

@RestController
public class ProjectController {

	@Autowired
	ProjectDao projectDao;

	@RequestMapping("/projects")
	public List<Project> greeting() {

		return projectDao.list();

	}

}
