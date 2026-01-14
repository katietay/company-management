package edu.colostate.cs415.server;

import static spark.Spark.after;
import static spark.Spark.exception;
import static spark.Spark.get;
import static spark.Spark.options;
import static spark.Spark.path;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.put;
import static spark.Spark.redirect;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Logger;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.google.gson.Gson;

import edu.colostate.cs415.db.DBConnector;
import edu.colostate.cs415.dto.AssignmentDTO;
import edu.colostate.cs415.dto.ProjectDTO;
import edu.colostate.cs415.dto.QualificationDTO;
import edu.colostate.cs415.dto.WorkerDTO;
import edu.colostate.cs415.model.Company;
import edu.colostate.cs415.model.Project;
import edu.colostate.cs415.model.Qualification;
import edu.colostate.cs415.model.Worker;
import edu.colostate.cs415.model.ProjectStatus;
import spark.Request;
import spark.Response;
import spark.Spark;

public class RestController {

	private static Logger log = Logger.getLogger(RestController.class.getName());
	private static String OK = "OK";
	private static String KO = "KO";

	private DBConnector dbConnector;
	private Company company;
	private Gson gson;

	public RestController(int port, DBConnector dbConnector) {
		port(port);
		this.dbConnector = dbConnector;
		gson = new Gson();
	}

	public void start() {
		// Load data from DB
		company = dbConnector.loadCompanyData();

		// Redirect
		redirect.get("/", "/helloworld");

		// Logging
		after("/*", (req, res) -> logRequest(req, res));
		exception(Exception.class, (exc, req, res) -> handleException(exc, res));

		// Hello World
		get("/helloworld", (req, res) -> helloWorld());

		// API
		path("/api", () -> {
			// Enable CORS
			options("/*", (req, res) -> optionsCORS(req, res));
			after("/*", (req, res) -> enableCORS(res));

			// Qualifications
			path("/qualifications", () -> {
				get("", (req, res) -> getQualifications(), gson::toJson);
				get("/:description", (req, res) -> getQualification(req.params("description")),
						gson::toJson);
				post("/:description", (req, res) -> createQualification(req));
			});

			// Projects
			path("/projects", () -> {
				get("", (req, res) -> getProjects(), gson::toJson);
				get("/:name", (req, res) -> getProject(req.params("name")),
						gson::toJson);
				post("/:name", (req, res) -> createProject(req));
			});

			// Workers
			path("/workers", () -> {
				get("", (req, res) -> getWorkerDTOArrary(), gson::toJson);
				get("/:name", (req, res) -> getWorkerDTO(req.params("name")), gson::toJson);
				post("/:name", (req, res) -> createWorker(req));
			});

			// Company Assign & Unassign
			path("/assign", () -> {
				put("", (req, res) -> assignWorker(req));
			});
			path("/unassign", () -> {
				put("", (req, res) -> unassignWorker(req));
			});
			

			// Company Start and End for Company 
			path("/start", () -> {
    			put("", (req, res) -> startCompany(req));
			});

			path("/finish", () -> {
    			put("", (req, res) -> finishCompany(req));
			});


		});

	}

	public void stop() {
		Spark.stop();
	}

	private String helloWorld() {
		return "Hello World!";
	}

	//Qualification Endpoints
	private QualificationDTO[] getQualifications() {
		QualificationDTO[] quals = new QualificationDTO[company.getQualifications().size()];

		int i = 0;
		for (Qualification qual : company.getQualifications()) {
			QualificationDTO dto = qual.toDTO();
			quals[i] = dto;
			i++;
		}
		
		return quals;
	}

	private QualificationDTO getQualification(String description) {
		String nameStr = description;
		nameStr = nameStr.replace(":", "");
		QualificationDTO[] quals = getQualifications();

		for (QualificationDTO qual : quals) {
			if (qual.getDescription().hashCode() == nameStr.hashCode()) {
				return qual;
			}
		}
		throw new IllegalArgumentException("Qualification not found");
	}

	private String createQualification(Request request) {
		QualificationDTO assignmentDTO = gson.fromJson(request.body(), QualificationDTO.class);
		if (request.params("description").equals(assignmentDTO.getDescription())) {
			company.createQualification(assignmentDTO.getDescription());
		} else
			throw new RuntimeException("Qualification descriptions do not match.");
		return OK;
	}

	//Project Endpoints
	private ProjectDTO[] getProjects() {
		ProjectDTO[] projects = new ProjectDTO[company.getProjects().size()];

		int i = 0;
		for (Project project : company.getProjects()) {
			ProjectDTO dto = project.toDTO();
			projects[i] = dto;
			i++;
		}
		
		return projects;
	}

	private ProjectDTO getProject(String name) { //TODO
		String nameStr = name;
		nameStr = nameStr.replace(":", "");
		ProjectDTO[] projects = getProjects();

		for (ProjectDTO project : projects) {
			if (project.getName().hashCode() == nameStr.hashCode()) {
				return project;
			}
		}
		throw new IllegalArgumentException("Project not found");
	}

	private String createProject(Request request) {
    try {
        ProjectDTO projectDTO = gson.fromJson(request.body(), ProjectDTO.class);
           if (projectDTO.getName() == null || projectDTO.getName().trim().isEmpty()
                   || projectDTO.getQualifications() == null || projectDTO.getSize() == null) {
               throw new IllegalArgumentException("Missing required fields in ProjectDTO");
           }
           Set<Qualification> projectQuals = new HashSet<>();
           for (String qualStr : projectDTO.getQualifications()) {
               boolean found = false;
               for (Qualification existing : company.getQualifications()) {
                   if (existing.toString().equals(qualStr)) {
                       projectQuals.add(existing);
                       found = true;
                       break;
                   }
               }
               if (!found) {
                   throw new IllegalArgumentException("Qualification not found: " + qualStr);
               }
           }
           company.createProject(projectDTO.getName(), projectQuals, projectDTO.getSize());
       } catch (Exception e) {
           return KO;
       }
       return OK;

	}
	
	
	// Worker Endpoints

	private WorkerDTO[] getWorkerDTOArrary() {
		WorkerDTO[] workersArr = new WorkerDTO[this.company.getEmployedWorkers().size()];

		int index = 0;
		for (Worker w : this.company.getEmployedWorkers()) {
			WorkerDTO workerDTO = w.toDTO();
			workersArr[index] = workerDTO;
			index++;
		}
		return workersArr;
	}

	private WorkerDTO getWorkerDTO(String name) {
		String nameStr = name;
		nameStr = nameStr.replace(":", "");
		WorkerDTO[] workers = getWorkerDTOArrary();

		for (int i = 0; i < workers.length; i++) {
			WorkerDTO worker = workers[i];
			String workerName = worker.getName();
			if (workerName.equals(nameStr)) {
				return worker;
			}
		}
		throw new IllegalArgumentException("Worker not found");
	}

	private String createWorker(Request request) {
		try {
			WorkerDTO newWorkerDTO = gson.fromJson(request.body().toString(), WorkerDTO.class);
			Set<Qualification> newQuals = new HashSet<>();
			String[] DTOQuals = newWorkerDTO.getQualifications();
			for (int i = 0; i < DTOQuals.length; i++) {
				Qualification qual = new Qualification(DTOQuals[i]);
				newQuals.add(qual);
			}
			this.company.createWorker(newWorkerDTO.getName(), newQuals, newWorkerDTO.getSalary());
		} catch (Exception e) {
			// e.printStackTrace();
			return KO;
		}
		return OK;
	}

	private String assignWorker(Request request) {
		try {
			AssignmentDTO assignment = gson.fromJson(request.body(), AssignmentDTO.class);
			String workerName = assignment.getWorker();
			String projectName = assignment.getProject();
	
			Worker worker = null;
			for (Worker w : company.getEmployedWorkers()) {
				if (w.getName().equals(workerName)) {
					worker = w;
					break;
				}
			}
	
			Project project = null;
			for (Project p : company.getProjects()) {
				if (p.getName().equals(projectName)) {
					project = p;
					break;
				}
			}
	
			if (worker == null || project == null) return KO;
	
			company.assign(worker, project);
			return OK;
		} catch (Exception e) {
			return KO;
		}
	}
	
	
	private String unassignWorker(Request request) {
		try {
			AssignmentDTO assignment = gson.fromJson(request.body(), AssignmentDTO.class);
	
			String workerName = assignment.getWorker();
			String projectName = assignment.getProject();
	
			Worker worker = null;
			for (Worker w : company.getEmployedWorkers()) {
				if (w.getName().equals(workerName)) {
					worker = w;
					break;
				}
			}
	
			Project project = null;
			for (Project p : company.getProjects()) {
				if (p.getName().equals(projectName)) {
					project = p;
					break;
				}
			}
	
			if (worker == null || project == null) {
				return KO;
			}
	
			company.unassign(worker, project);
			return OK;
	
		} catch (Exception e) {
			return KO;
		}
	}
	

	private String startCompany(Request request) {
		try {
			ProjectDTO projectDTO = gson.fromJson(request.body(), ProjectDTO.class);
	
			Project foundProject = null;
			for (Project p : this.company.getProjects()) {
				if (p.getName().equals(projectDTO.getName())) {
					foundProject = p;
					break;
				}
			}
	
			if (foundProject == null) {
				return KO;
			}
	
			company.start(foundProject); 
			return OK;
		} catch (Exception ex) {
			return KO;
		}
	}
	

	private String finishCompany(Request request) {
		try {
        ProjectDTO projectDTO = gson.fromJson(request.body().toString(), ProjectDTO.class);

        Project foundProject = null;
        for (Project p : this.company.getProjects()) {
            if (projectDTO.getName().equals(p.getName())) {
                foundProject = p;
                break;
            }
        }

        if (foundProject == null) {
            throw new IllegalArgumentException("Project not found in company list");
        }

        this.company.finish(foundProject);
        return OK;
    } catch (Exception ex) {
        return KO;
    }
}

	// Logs every request received
	private void logRequest(Request request, Response response) {
		log.info(request.requestMethod() + " " + request.pathInfo() + "\nREQUEST:\n" + request.body() + "\nRESPONSE:\n"
				+ response.body());
	}

	// Exception handling
	private void handleException(Exception exception, Response response) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		exception.printStackTrace();
		exception.printStackTrace(pw);
		log.severe(sw.toString());
		response.body(KO);
		response.status(500);
	}

	// Enable CORS
	private void enableCORS(Response response) {
		response.header("Access-Control-Allow-Origin", "*");
	}

	// Enable CORS
	private String optionsCORS(Request request, Response response) {
		String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
		if (accessControlRequestHeaders != null)
			response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);

		String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
		if (accessControlRequestMethod != null)
			response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
		return OK;
	}

}