package edu.colostate.cs415.server;

import org.apache.hc.client5.http.fluent.Content;
import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.client5.http.fluent.Response;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.client5.http.HttpResponseException;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executor;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Collections;


import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import static org.junit.Assert.fail;
import org.junit.Test;
import static org.mockito.Mockito.*;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import spark.Spark;

import edu.colostate.cs415.db.DBConnector;
import edu.colostate.cs415.dto.AssignmentDTO;
import edu.colostate.cs415.dto.ProjectDTO;
import edu.colostate.cs415.dto.QualificationDTO;
import edu.colostate.cs415.dto.WorkerDTO;
import edu.colostate.cs415.dto.ProjectDTO;
import edu.colostate.cs415.model.Project;
import edu.colostate.cs415.model.ProjectSize;
import edu.colostate.cs415.model.Qualification;
import edu.colostate.cs415.model.Worker;
import edu.colostate.cs415.model.Company;
import edu.colostate.cs415.model.ProjectStatus;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RestControllerTest {

    static RestController controller;

    @Test
    public void test() {
        assert (true);
    }

    @BeforeClass
    public static void StartControler() {
        DBConnector db = new DBConnector();
        RestControllerTest.controller = new RestController(4567, db);
    }

    @After
    public void StopRestControler() {
        spark.Spark.awaitStop();
    }

    @Test
    public void RestControllerHelloWorld() {
        try {
            RestControllerTest.controller.start();
            Spark.awaitInitialization();

            Content response = Request.get("http://localhost:4567/").execute().returnContent();
            

            String responseStr = response.asString();
            assertTrue(responseStr.equals("Hello World!"));
            RestControllerTest.controller.stop();
            Spark.awaitStop();
        } catch (IOException e) {
            fail();
            e.printStackTrace();
        }
    }

    @Test
    public void TestWorkersArrayEndPoint() {
        try {
            RestControllerTest.controller.start();
            Spark.awaitInitialization(); 

            Content response = Request.get("http://localhost:4567/api/workers").execute().returnContent();
            String responseStr = response.asString();
            
            JsonElement jsonElement = JsonParser.parseString(responseStr);
            JsonArray jsonArray = jsonElement.getAsJsonArray();

            ArrayList<String> responseNames = new ArrayList<>();

            for (int i = 0; i < jsonArray.size(); i++) {
                JsonElement element = jsonArray.get(i);
                JsonObject obj = element.getAsJsonObject();
                String name = obj.get("name").getAsString();
                responseNames.add(name);
            }

            String[] expectedNames = {
                "Jamie Burgess", "Erika Johnston",
                "Omar Williamson", "Marcus Schneider",
                "Tim Conner", "Terry Hampton",
                "Nick Hubbard", "Ron Logan",
                "Benjamin Guzman", "Gene Robertson",
                "Nina Banks", "Robert Lambert"
            };

            Boolean containsAllNames = false;
            for(int i = 0; i < expectedNames.length; i++) {
                String expectedName = expectedNames[i];

                if(responseNames.contains(expectedName)) {
                    containsAllNames = true;
                } else {
                    containsAllNames = false;
                }
            }

            assertTrue(containsAllNames);
        } catch (IOException e) {
            fail("IOException occurred: " + e.getMessage());
            e.printStackTrace();
        } finally {
            RestControllerTest.controller.stop();
            Spark.awaitStop();
        }
    }

    @Test
    public void TestWorkersNameEndPoint() {
        try {
            RestControllerTest.controller.start();
            
            Content response = Request.get("http://localhost:4567/api/workers/Ron%20Logan").execute().returnContent();

            String responseStr = response.asString();
            String[] nameResponse = responseStr.split("}");
            String name = nameResponse[0];
            int startIndex = name.indexOf(":\"");
            nameResponse[0] = name.substring(startIndex + 2, name.indexOf(",", startIndex) - 1);

            String expectedName = "Ron Logan";
            
            assertEquals(expectedName, nameResponse[0]);

            RestControllerTest.controller.stop();
            spark.Spark.awaitStop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void TestWorkersNameEndPointNullName() {
        RestControllerTest.controller.start();
        Spark.awaitInitialization();

        try {
            Request.get("http://localhost:4567/api/workers/Jon%20Doe")
                    .execute().returnContent();
            fail("Expected a 500 response for missing worker");
        } catch (HttpResponseException e) {
            assertEquals(500, e.getStatusCode());
        } catch (IOException e) {
            // e.printStackTrace();
            fail("Unexpected IOException");
        } finally {
            RestControllerTest.controller.stop();
            Spark.awaitStop();
        }
    }

    
    @Test
    public void TestNewWorkerEndpoint() {
        RestControllerTest.controller.start();
        Spark.awaitInitialization();

        try {
            String name = "Jacob Smith";
            Set<Qualification> qualifications = new HashSet();
            Qualification qual1 = new Qualification("Java");
            Qualification qual2 = new Qualification("React");
            qualifications.add(qual1);
            qualifications.add(qual2);
            Project project = new Project("Doing Work", qualifications, ProjectSize.BIG);
            double salary = 250.0;
            Worker worker = new Worker(name, qualifications, salary);
            worker.addProject(project);

            Gson gson = new Gson();
            String jsonString = gson.toJson(worker.toDTO());

            String URL = "http://localhost:4567/api/workers/:" + "Jacob%20Smith";

            String response = Request.post(URL)
                    .bodyString(jsonString, ContentType.APPLICATION_JSON).execute().returnContent().asString();
            assertTrue(response.equals("OK"));

            String workerString = Request.get(URL).execute().returnContent().asString();
            WorkerDTO workerResponse = gson.fromJson(workerString, WorkerDTO.class);

            assertEquals(workerResponse.getName(), name);
            

        } catch (HttpResponseException e) {
            e.printStackTrace();
            fail("Worker was not created or found.");
        } catch (IOException e) {
            e.printStackTrace();
            // fail("Worker was not created or found.");
        }
    }


    @Test
    public void TestAssignWorkerEndpoint() {
        RestControllerTest.controller.start();
        Spark.awaitInitialization();
        try {
            AssignmentDTO assDTO = new AssignmentDTO("Erika Johnston", "Android Task Monitoring");

            Gson gson = new Gson();
            String jsonString = gson.toJson(assDTO);

            String URL = "http://localhost:4567/api/assign";
            String response = Request.put(URL)
                    .bodyString(jsonString, ContentType.APPLICATION_JSON).execute().returnContent().asString();
            assertTrue(response.equals("OK"));

            String workerResponse = Request.get("http://localhost:4567/api/workers/Erika%20Johnston").execute().returnContent().asString();
            WorkerDTO workerDTO = gson.fromJson(workerResponse, WorkerDTO.class);
            
            String[] projects = workerDTO.getProjects();
            
            boolean hasProject = false;
            for(int i = 0; i < projects.length; i++) {
                String projectStr = projects[i];
                if(projectStr.equals("Android Task Monitoring")) {
                    hasProject = true;
                    break;
                }
            }
            assertTrue(hasProject);

        } catch (HttpResponseException e) {
            e.printStackTrace();
            fail("Worker was not assigned.");
        } catch (IOException e) {
            e.printStackTrace();
            fail("Worker was not assigned.");
        }
        RestControllerTest.controller.stop();
        Spark.awaitStop();
    }
    
    @Test
    public void TestUnassignWorkerEndpoint() {
        RestControllerTest.controller.start();
        Spark.awaitInitialization();
        try {
            String name = "Jamie Burgess";
            Set<Qualification> qualifications = new HashSet<>();
            qualifications.add(new Qualification("Tensorflow"));
            qualifications.add(new Qualification("JavaScript"));
            qualifications.add(new Qualification("Python"));
            qualifications.add(new Qualification("Java"));
    
            Worker worker = new Worker(name, qualifications, 250.0);
            Project project = new Project("Credit Card Fraud Detection", qualifications, ProjectSize.BIG);
    
            // ✅ Send name strings (not JSON stringified DTOs)
            AssignmentDTO assDTO = new AssignmentDTO(worker.getName(), project.getName());
    
            Gson gson = new Gson();
            String jsonString = gson.toJson(assDTO);
    
            String URL = "http://localhost:4567/api/unassign";
            String response = Request.put(URL)
                    .bodyString(jsonString, ContentType.APPLICATION_JSON)
                    .execute().returnContent().asString();
    
            assertEquals("OK", response);
    
            String workerResponse = Request.get("http://localhost:4567/api/workers/Jamie%20Burgess")
                    .execute().returnContent().asString();
            WorkerDTO workerDTO = gson.fromJson(workerResponse, WorkerDTO.class);
    
            String[] projects = workerDTO.getProjects();
    
            boolean hasProject = false;
            for (String p : projects) {
                if (p.equals("Credit Card Fraud Detection")) {
                    hasProject = true;
                    break;
                }
            }
    
            assertFalse(hasProject);
    
        } catch (HttpResponseException e) {
            fail("Unassign request failed: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            fail("IOException during unassign test.");
        }
    
        RestControllerTest.controller.stop();
        Spark.awaitStop();
    }
    

    @Test
    public void TestStartCompOK() {
    RestControllerTest.controller.start();
    Spark.awaitInitialization();
    try {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setName("Smart Chatbot");

        Gson gson = new Gson();
        String jsonBody = gson.toJson(projectDTO);

        String response = Request.put("http://localhost:4567/api/start")
            .bodyString(jsonBody, ContentType.APPLICATION_JSON)
            .execute()
            .returnContent()
            .asString();

        assertEquals("OK", response);
    } catch (IOException e) {
        e.printStackTrace();
        fail("error");
    } finally {
        RestControllerTest.controller.stop();
        Spark.awaitStop();
    }
}

@Test
public void TestFinishCompOK() {
    RestControllerTest.controller.start();
    Spark.awaitInitialization();
    try {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setName("Smart Chatbot");

        Gson gson = new Gson();
        String jsonBody = gson.toJson(projectDTO);

        String response = Request.put("http://localhost:4567/api/finish")
            .bodyString(jsonBody, ContentType.APPLICATION_JSON)
            .execute()
            .returnContent()
            .asString();

        assertEquals("OK", response);
    } catch (IOException e) {
        e.printStackTrace();
        fail("error");
    } finally {
        RestControllerTest.controller.stop();
        Spark.awaitStop();
    }
}

@Test
public void testGetQualifications() {
    RestControllerTest.controller.start();
    Spark.awaitInitialization();
    try {
        Content response = Request.get("http://localhost:4567/api/qualifications").execute().returnContent();
        String responseStr = response.asString();

        String[] names = responseStr.split("}");
        for (int i = 0; i < names.length - 1; i++) {
            String name = names[i];
            int startIndex = name.indexOf(":\"");
            names[i] = name.substring(startIndex + 2, name.indexOf(",", startIndex) - 1);
        }

        String[] expectedNames = {
            "Angular", "Cyber Security", "Java", "JavaScript",
            "MongoDB", "Python", "React", "Spark",
            "Spring", "Sql", "Tensorflow", "TypeScript"
        };

        List<String> expectedNamesList = new ArrayList<>(Arrays.asList(expectedNames));

        for (int i = 0; i < expectedNames.length; i++) {
            assertTrue("Make sure " + names[i] + " is present", expectedNamesList.contains(names[i]));
        }
    } catch (IOException e) {
        fail("RuntimeException");
        e.printStackTrace();
    } finally {
        RestControllerTest.controller.stop();
        Spark.awaitStop();
    }
}

@Test
public void testGetQualificationDescription() {
    RestControllerTest.controller.start();
    Spark.awaitInitialization();
    try {
        Content response = Request.get("http://localhost:4567/api/qualifications/React").execute().returnContent();
        String responseStr = response.asString();

        String[] nameSplit = responseStr.split(":");
        String name = nameSplit[1].split("\"")[1];

        assertEquals("Make sure " + name + " equals React", "React", name);
    } catch (IOException e) {
        fail("RuntimeException");
        e.printStackTrace();
    } finally {
        RestControllerTest.controller.stop();
        Spark.awaitStop();
    }
}

@Test
public void testGetProjects() {
    RestControllerTest.controller.start();
    Spark.awaitInitialization();
    try {
        Content response = Request.get("http://localhost:4567/api/projects").execute().returnContent();
        String responseStr = response.asString();

        String[] names = responseStr.split("}");
        for (int i = 0; i < names.length - 1; i++) {
            String name = names[i];
            int startIndex = name.indexOf(":\"");
            names[i] = name.substring(startIndex + 2, name.indexOf(",", startIndex) - 1);
        }

        String[] expectedNames = {
            "AI Shopping System", "Android Task Monitoring",
            "Fingerprint-Based ATM System", "Credit Card Fraud Detection",
            "Weather Forecasting System", "Smart Chatbot",
            "Financial Banking System", "Face Detector",
            "Signature Verification System", "Legacy Software Maintanance",
            "Ecommerce Fake Product Reviews Detection System", "Employees DB"
        };

        List<String> expectedNamesList = new ArrayList<>(Arrays.asList(expectedNames));

        for (int i = 0; i < expectedNames.length; i++) {
            assertTrue("Make sure " + names[i] + " is present", expectedNamesList.contains(names[i]));
        }
    } catch (IOException e) {
        fail("RuntimeException");
        e.printStackTrace();
    } finally {
        RestControllerTest.controller.stop();
        Spark.awaitStop();
    }
}

@Test
public void testGetProjectName() {
    RestControllerTest.controller.start();
    Spark.awaitInitialization();
    try {
        Content response = Request.get("http://localhost:4567/api/projects/AI%20Shopping%20System").execute().returnContent();
        String responseStr = response.asString();

        String[] nameSplit = responseStr.split(":");
        String name = nameSplit[1].split("\"")[1];

        assertEquals("Make sure " + name + " equals AI Shopping System", "AI Shopping System", name);
    } catch (IOException e) {
        fail("RuntimeException");
        e.printStackTrace();
    } finally {
        RestControllerTest.controller.stop();
        Spark.awaitStop();
    }
}

@Test
public void TestNewProjectEndpoint() {
    RestControllerTest.controller.start();
    Spark.awaitInitialization();
    try {
        ProjectDTO dto = new ProjectDTO();
        dto.setName("New Web App");
        dto.setSize(ProjectSize.MEDIUM);
        dto.setQualifications(new String[]{"Java", "Spring"});

        Gson gson = new Gson();
        String json = gson.toJson(dto);

        String response = Request.post("http://localhost:4567/api/projects/New%20Web%20App")
            .bodyString(json, ContentType.APPLICATION_JSON)
            .execute()
            .returnContent()
            .asString();

        assertEquals("OK", response);

        Content content = Request.get("http://localhost:4567/api/projects/New%20Web%20App")
            .execute()
            .returnContent();

        assertTrue(content.asString().contains("New Web App"));
    } catch (Exception e) {
        e.printStackTrace();
        fail("POST or GET failed for /api/projects/:name");
    } finally {
        RestControllerTest.controller.stop();
        Spark.awaitStop();
    }
}
@Test
public void TestFinishCompKO() {
    RestControllerTest.controller.start();
    Spark.awaitInitialization();
    try {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setName("Fake Name");

        Gson gson = new Gson();
        String jsonBody = gson.toJson(projectDTO);

        String response = Request.put("http://localhost:4567/api/finish")
            .bodyString(jsonBody, ContentType.APPLICATION_JSON)
            .execute()
            .returnContent()
            .asString();

        assertEquals("KO", response);
    } catch (IOException e) {
        e.printStackTrace();
        fail("error");
    } finally {
        RestControllerTest.controller.stop();
        Spark.awaitStop();
    }
}


    @Test
    public void testPutAssignWithInvalidProject() throws Exception {
        RestControllerTest.controller.start();
        Spark.awaitInitialization();

        try {
            WorkerDTO workerDTO = new WorkerDTO("Erika Johnston", 250.0, 0, new String[0], new String[0]);
            ProjectDTO projectDTO = new ProjectDTO();
            projectDTO.setName("Nonexistent Project");

            AssignmentDTO assignment = new AssignmentDTO(new Gson().toJson(workerDTO), new Gson().toJson(projectDTO));
            String json = new Gson().toJson(assignment);

            String response = Request.put("http://localhost:4567/api/assign")
                .bodyString(json, ContentType.APPLICATION_JSON)
                .execute()
                .returnContent()
                .asString();

            assertEquals("KO", response);
        } finally {
            RestControllerTest.controller.stop();
            Spark.awaitStop();
        }
    }

    @Test
    public void testPutAssignWithInvalidWorker() throws Exception {
        RestControllerTest.controller.start();
        Spark.awaitInitialization();

        try {
            WorkerDTO workerDTO = new WorkerDTO("Fake Worker", 250.0, 0, new String[0], new String[0]);
            ProjectDTO projectDTO = new ProjectDTO();
            projectDTO.setName("Smart Chatbot");

            AssignmentDTO assignment = new AssignmentDTO(new Gson().toJson(workerDTO), new Gson().toJson(projectDTO));
            String json = new Gson().toJson(assignment);

            String response = Request.put("http://localhost:4567/api/assign")
                .bodyString(json, ContentType.APPLICATION_JSON)
                .execute()
                .returnContent()
                .asString();

            assertEquals("KO", response);
        } finally {
            RestControllerTest.controller.stop();
            Spark.awaitStop();
        }
    }

    @Test
    public void testPutUnassignWithInvalidProject() throws Exception {
        RestControllerTest.controller.start();
        Spark.awaitInitialization();

        try {
            WorkerDTO workerDTO = new WorkerDTO("Jamie Burgess", 250.0, 0, new String[0], new String[0]);
            ProjectDTO projectDTO = new ProjectDTO();
            projectDTO.setName("Ghost Project");

            AssignmentDTO assignment = new AssignmentDTO(new Gson().toJson(workerDTO), new Gson().toJson(projectDTO));
            String json = new Gson().toJson(assignment);

            String response = Request.put("http://localhost:4567/api/unassign")
                .bodyString(json, ContentType.APPLICATION_JSON)
                .execute()
                .returnContent()
                .asString();

            assertEquals("KO", response);
        } finally {
            RestControllerTest.controller.stop();
            Spark.awaitStop();
        }
    }

    @Test
    public void testPutUnassignWithInvalidWorker() throws Exception {
        RestControllerTest.controller.start();
        Spark.awaitInitialization();

        try {
            WorkerDTO workerDTO = new WorkerDTO("Ghost Worker", 250.0, 0, new String[0], new String[0]);
            ProjectDTO projectDTO = new ProjectDTO();
            projectDTO.setName("Smart Chatbot");

            AssignmentDTO assignment = new AssignmentDTO(new Gson().toJson(workerDTO), new Gson().toJson(projectDTO));
            String json = new Gson().toJson(assignment);

            String response = Request.put("http://localhost:4567/api/unassign")
                .bodyString(json, ContentType.APPLICATION_JSON)
                .execute()
                .returnContent()
                .asString();

            assertEquals("KO", response);
        } finally {
            RestControllerTest.controller.stop();
            Spark.awaitStop();
        }
    }

    @Test
    public void testPutStartInvalidProject() throws Exception {
        RestControllerTest.controller.start();
        Spark.awaitInitialization();

        try {
            ProjectDTO projectDTO = new ProjectDTO();
            projectDTO.setName("Invisible Project");

            String json = new Gson().toJson(projectDTO);

            String response = Request.put("http://localhost:4567/api/start")
                .bodyString(json, ContentType.APPLICATION_JSON)
                .execute()
                .returnContent()
                .asString();

            assertEquals("KO", response);
        } finally {
            RestControllerTest.controller.stop();
            Spark.awaitStop();
        }
    }





}