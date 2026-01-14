package edu.colostate.cs415.model;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import edu.colostate.cs415.dto.*;

public class WorkerTest {
    @Test
    public void test() {
        assert (true);
    }

    // Constructor Tests
	@Test
    public void testValidWorker() {
        Set<Qualification> qualifications = new HashSet<>();
        qualifications.add(new Qualification("Testing 1"));
        Worker worker = new Worker("Worker", qualifications, 15);
        assertNotNull(worker);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullName() {
		Set<Qualification> qualifications = new HashSet<>();
		qualifications.add(new Qualification("Testing 1"));
        Worker worker = new Worker(null, qualifications, 15);// Should throw an exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyName() {
        Set<Qualification> qualifications = new HashSet<>();
		qualifications.add(new Qualification("Testing 1"));
        Worker worker = new Worker("", qualifications, 15);// Should throw an exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWhitespaceName() {
        Set<Qualification> qualifications = new HashSet<>();
		qualifications.add(new Qualification("Testing 1"));
        Worker worker = new Worker("      ", qualifications, 15);// Should throw an exception
    }

	@Test(expected = IllegalArgumentException.class)
    public void testNullQualifications() {
		Worker worker = new Worker("Worker1", null, 15);// Should throw an exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullQualifications2() {
		Worker worker = new Worker("Worker2", null, 0);// Should throw an exception
    }


	//

    @Test
    public void TestBasicEquals() {
        Worker worker1 = new Worker("Worker 1", new HashSet<>(), 15);
        Worker worker2 = new Worker("Worker 2", new HashSet<>(), 25);
        Worker workerSame = new Worker("Worker 1", new HashSet<>(), 50);

        assertFalse(worker1.equals(worker2));
        assertFalse(worker1.equals(null));
        assertEquals(worker1, workerSame);
    }

    @Test
    public void WorkerGetName() {
        Worker worker1 = new Worker("Worker 1", new HashSet<>(), 5);
        Worker worker2 = new Worker("Worker 2", new HashSet<>(), 50);

        assertEquals("Worker 1", worker1.getName());
        assertEquals("Worker 2", worker2.getName());
    }

    @Test (expected = IllegalArgumentException.class)
    public void WorkerGetNameNull() {
        Worker worker1 = new Worker(null, new HashSet<>(), 5);

        assertNull(worker1.getName());
    }

    @Test (expected = IllegalArgumentException.class)
    public void WorkerGetNameWhiteSpace() {
        Worker worker1 = new Worker("   ", new HashSet<>(), 5);

        String str = "   ";
        assertTrue(str.equals(worker1.getName()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void WorkerGetNameEmpty() {
        Worker worker1 = new Worker("", new HashSet<>(), 5);

        String str = "";
        assertTrue(str.equals(worker1.getName()));
    }

    @Test
    public void WorkerSalary() {
        Worker worker1 = new Worker("Worker 1", new HashSet<>(), 5.0);

        assertEquals(5.0, worker1.getSalary(), 0.0);
        worker1.setSalary(250.0);
        assertFalse(worker1.getSalary() == 5.0);
        assertEquals(250.0, worker1.getSalary(), 0.0);
    }
    @Test
    public void WorkerSalaryZero() {
        Worker worker1 = new Worker("Worker 1", new HashSet<>(), -1.0);
        worker1.setSalary(0);
        assertEquals(0.0, worker1.getSalary(), 0.0);
    }
    @Test
    public void WorkerSalaryNegative() {
        Worker worker1 = new Worker("Worker 1", new HashSet<>(), 0);

        worker1.setSalary(-5.0);
        assertEquals(0.0, worker1.getSalary(), 0.0);
    }

    @Test
    public void testGetWorkload() {
        Set<Qualification> qualifications = new HashSet<>();
        Worker worker = new Worker("Jason Bourne", qualifications, 50_000);

        Project smallProject = new Project("Small_Proj", new HashSet<>(), ProjectSize.SMALL);
        Project mediumProject = new Project("Medium_proj", new HashSet<>(), ProjectSize.MEDIUM);
        Project bigProject = new Project("Big_proj", new HashSet<>(), ProjectSize.BIG);

        // Ensure projects are ACTIVE (workload ignores FINISHED projects)
        smallProject.setStatus(ProjectStatus.ACTIVE);
        mediumProject.setStatus(ProjectStatus.ACTIVE);
        bigProject.setStatus(ProjectStatus.ACTIVE);

        assertEquals(0, worker.getWorkload());
        worker.addProject(smallProject);
        assertEquals(1, worker.getWorkload());

        worker.addProject(mediumProject);
        assertEquals(3, worker.getWorkload());

        worker.addProject(bigProject);
        assertEquals(6, worker.getWorkload());
    }

    @Test
    public void WorkerGetWorkLoadAllFinished() {
        Set<Qualification> quals = new HashSet<>();
        Worker worker = new Worker("Harry Potter", quals, 11.0);

        Project p1 = new Project("Traveling to hogwarts", quals, ProjectSize.BIG);
        Project p2 = new Project("Being a Wizard", quals, ProjectSize.BIG);
        Project p3 = new Project("Defeating Voldy", quals, ProjectSize.BIG);

        Project p4 = new Project("Having an actual parental figure", quals, ProjectSize.SMALL);

        p1.setStatus(ProjectStatus.FINISHED);
        p4.setStatus(ProjectStatus.ACTIVE);
        worker.addProject(p1);
        assertEquals(0, worker.getWorkload());

        worker.addProject(p2);
        assertEquals(3, worker.getWorkload());

        worker.addProject(p3);
        assertEquals(6, worker.getWorkload());

        // worker.addProject(p4);
        assertEquals(6, worker.getWorkload());

        

    }

    @Test
    public void WorkerHashCodeEquals() {
        Worker worker1 = new Worker("Worker 1", new HashSet<>(), 5);
        Worker worker2 = new Worker("Worker 1", new HashSet<>(), 50);

        assertEquals(worker1.hashCode(), worker2.hashCode());
    }

    @Test
    public void WorkerHashCodeNotEqual() {
        Worker worker1 = new Worker("Worker 1", new HashSet<>(), 5);
        Worker worker2 = new Worker("Worker 2", new HashSet<>(), 50);

        assertNotEquals(worker1.hashCode(), worker2.hashCode());
    }

    @Test
    public void WorkerHashCodeConsistency() {
        Worker worker1 = new Worker("Worker 1", new HashSet<>(), 5);

        int hash1 = worker1.hashCode();
        int hash2 = worker1.hashCode();

        assertEquals(hash1, hash2);
    }

	@Test
	public void WorkerQualificationConstructor() {
		Qualification q1 = new Qualification("Excel");
		Set<Qualification> quals = new HashSet<>();
		quals.add(q1);
		Worker worker1 = new Worker("Worker 1", quals, 5);

		assertTrue(worker1.getQualifications().contains(q1));
	}

	@Test
	public void WorkerAddGetQualification() {
		Qualification q1 = new Qualification("G Suite");
		Qualification q2 = new Qualification("Not Talking");
		Set<Qualification> quals = new HashSet<>();
		quals.add(q1);

		Worker worker1 = new Worker("Worker 1", quals, 5);
		worker1.addQualification(q2);

		assertTrue(worker1.getQualifications().contains(q2));
	}

    @Test
    public void WorkerAddQualifactionWithConstructor() {
        Qualification q1 = new Qualification("Excel");
        Qualification q2 = new Qualification("G Suite");
		Qualification q3 = new Qualification("Not Talking");

        
		Set<Qualification> quals = new HashSet<>();
		quals.add(q1);

		Worker worker1 = new Worker("Worker 1", quals, 5);
		worker1.addQualification(q2);
        worker1.addQualification(q3);

		assertTrue(worker1.getQualifications().contains(q3));
    }

    @Test (expected = IllegalArgumentException.class)
    public void WorkerAddQualifactionEmpty() {
        Set<Qualification> quals = new HashSet<>();
        Qualification q1 = new Qualification("");
        Worker worker1 = new Worker("Worker 1", quals, 5);

        worker1.addQualification(q1);

        fail("IllegalArgument Exception was not thrown");
    }

    @Test (expected = IllegalArgumentException.class)
    public void WorkerQualifactionNull() {
        Set<Qualification> quals = null;

        Worker worker1 = new Worker("Worker 1", quals, 5);
    }

    @Test (expected = IllegalArgumentException.class)
    public void WorkerAddQualifactionNull() {
        Set<Qualification> quals = null;

        Qualification q1 = null;
        Worker worker1 = new Worker("Worker 1", quals, 5);
        worker1.addQualification(q1);
    }

    @Test
    public void WorkerToStringBasicCase() {
        Set<Qualification> quals = new HashSet<>();
        quals.add(new Qualification("Code Wizard"));
        quals.add(new Qualification("Certified Caffeine Consumer"));

        Worker worker = new Worker("Sir Nick the Debugger", quals, 10000.99); // Should truncate salary
        assertEquals("Sir Nick the Debugger:0:2:10000", worker.toString());
    }

    @Test
    public void WorkerToStringWithProjectsAndQualifications() {
        Set<Qualification> quals = new HashSet<>();
        quals.add(new Qualification("Python Whisperer"));
        quals.add(new Qualification("Database Tamer"));

        Worker worker = new Worker("Dr. Alex the Query Slayer", quals, 45000.75);
        worker.addProject(new Project("Infinite Loop Fixer", new HashSet<>(), ProjectSize.SMALL));
        worker.addProject(new Project("Bug Exorcism", new HashSet<>(), ProjectSize.BIG));

        assertEquals("Dr. Alex the Query Slayer:2:2:45000", worker.toString());
    }

    @Test
    public void WorkerToStringEdgeCaseZeroSalaryNoProjectsNoQuals() {
        Worker worker = new Worker("Jamie the Intern", new HashSet<>(), 0);
        assertEquals("Jamie the Intern:0:0:0", worker.toString());
    }

    @Test
    public void WorkerAddProject() {
        Worker worker = new Worker("Monica the Gen Z", new HashSet<>(), 4.20);

        assertTrue(worker.getProjects().size()==0);

        Project p1 = new Project("Project 1", new HashSet<>(), ProjectSize.BIG);
        worker.addProject(p1);

        assertTrue(worker.getProjects().size()==1);
        assertTrue(worker.getProjects().contains(p1));
    }

    @Test
    public void WorkerRemoveProject() {
        Worker worker = new Worker("Zak with no c", new HashSet<>(), 4.20);

        assertTrue(worker.getProjects().size()==0);

        Project p1 = new Project("Spelling your name with a c", new HashSet<>(), ProjectSize.SMALL);
        worker.addProject(p1);

        assertTrue(worker.getProjects().size()==1);
        worker.removeProject(p1);
        assertTrue(!worker.getProjects().contains(p1));
        assertTrue(worker.getProjects().size()==0);
    }

    @Test
    public void WorkerAddProjectNull() {
        Worker worker = new Worker("Adammmm", new HashSet<>(), 4.20);
        
        Project p1 = null;
        worker.addProject(p1);
        assertTrue(worker.getProjects().contains(null));
        assertTrue(worker.getProjects().size()==1);
    }

    @Test
    public void WorkerRemoveProjectNull() {
        Worker worker = new Worker("Adammmm", new HashSet<>(), 4.20);
        
        Project p1 = null;
        worker.addProject(p1);
        assertTrue(worker.getProjects().contains(null));
        assertTrue(worker.getProjects().size()==1);
        worker.removeProject(null);
        assertTrue(worker.getProjects().size()==0);
    }

    @Test
    public void WorkerDTOOneQualification() {
        Qualification q1 = new Qualification("Excel");
		Set<Qualification> quals = new HashSet<>();
		quals.add(q1);
        Worker worker = new Worker("Steve the Woz", quals, 50.0);

        WorkerDTO workerDTO = worker.toDTO();
        assertTrue(worker.getName().equals(workerDTO.getName()));
        assertTrue(worker.getSalary()==workerDTO.getSalary());

        assertTrue(q1.toString().equals(workerDTO.getQualifications()[0]));
    }

    @Test
    public void WorkerDTOMultipleQualification() {
        Qualification q1 = new Qualification("Roblox");
        Qualification q2 = new Qualification("Minecraft");
        Qualification q3 = new Qualification("Getting Angy at little kids");
		Set<Qualification> quals = new HashSet<>();
		quals.add(q1);
        quals.add(q2);
        quals.add(q3);
        Worker worker = new Worker("The dude", quals, 25.0);

        WorkerDTO workerDTO = worker.toDTO();
        assertTrue(worker.getName().equals(workerDTO.getName()));
        assertTrue(worker.getSalary()==workerDTO.getSalary());

        Set<String> expectedQuals = new HashSet<>();
        expectedQuals.add("Roblox");
        expectedQuals.add("Minecraft");
        expectedQuals.add("Getting Angy at little kids");

        Set<String> workerQuals = new HashSet<>();
        for(String desp : workerDTO.getQualifications()) {
            workerQuals.add(desp);
        }
        assertEquals(expectedQuals, workerQuals);
    }

    @Test
    public void WorkerDTOMultipleQualificationAndProjects() {
        Qualification q1 = new Qualification("Roblox");
        Qualification q2 = new Qualification("Minecraft");
        Qualification q3 = new Qualification("Getting Angy at little kids");
		Set<Qualification> quals = new HashSet<>();
		quals.add(q1);
        quals.add(q2);
        quals.add(q3);

        Worker worker = new Worker("The dude", quals, 25.0);

        Project p1 = new Project("Making a PDF", quals, ProjectSize.BIG);
        Project p2 = new Project("Watching March Madness", quals, ProjectSize.BIG);

        worker.addProject(p1);
        worker.addProject(p2);


        WorkerDTO workerDTO = worker.toDTO();
        assertTrue(worker.getName().equals(workerDTO.getName()));
        assertTrue(worker.getSalary()==workerDTO.getSalary());

        Set<String> expectedQuals = new HashSet<>();
        expectedQuals.add("Roblox");
        expectedQuals.add("Minecraft");
        expectedQuals.add("Getting Angy at little kids");

        String[] expectedProjectString = worker.getProjects().stream().map(Project::getName).toArray(String[]:: new);

        Set<String> workerQuals = new HashSet<>();
        for(String desp : workerDTO.getQualifications()) {
            workerQuals.add(desp);
        }
        assertEquals(expectedQuals, workerQuals);
        
        int index = 0;
        boolean projectNameMatch = false;
        for (String str : workerDTO.getProjects()) {
            if(!str.equals(expectedProjectString[index])) {
                projectNameMatch = false;
                fail(String.format("Project names don't match. Expected: %s, Recived: %s", expectedProjectString[index], str));
            } else {
                projectNameMatch = true;
            }
            index++;
        }

        assertTrue(projectNameMatch);
        
    }

    @Test 
    public void WorkerNotOverloaded() {
        Worker worker = new Worker("Jeff the Shark", new HashSet<>(), 2);

        Project p1 = new Project("Swimming", new HashSet<>(), ProjectSize.BIG);
        Project p2 = new Project("Acting in Jaws", new HashSet<>(), ProjectSize.BIG); 
        Project p3 = new Project("Vacating in Miami", new HashSet<>(), ProjectSize.BIG); 
        Project p4 = new Project("Jumping out of the water", new HashSet<>(), ProjectSize.SMALL); 

        worker.addProject(p1);
        worker.addProject(p2);
        worker.addProject(p3);
        assertFalse(worker.willOverload(p4));
    }

    @Test
    public void WorkerOverloaded() {
        Worker worker = new Worker("Baker's Man", new HashSet<>(), 15);

        Project p1 = new Project("Baking a cake", new HashSet<>(), ProjectSize.BIG);
        Project p2 = new Project("As fast as he can", new HashSet<>(), ProjectSize.BIG); 
        Project p3 = new Project("Roll it", new HashSet<>(), ProjectSize.SMALL); 
        Project p4 = new Project("Pat it", new HashSet<>(), ProjectSize.SMALL); 
        Project p5 = new Project("and mark it with a B", new HashSet<>(), ProjectSize.MEDIUM); 
        Project p6 = new Project("Put it in the oven for Baby and me", new HashSet<>(), ProjectSize.BIG); 

        worker.addProject(p1);
        worker.addProject(p2);
        worker.addProject(p3);
        worker.addProject(p4);
        worker.addProject(p5);
        System.out.println(worker.getWorkload());
        assertTrue(worker.willOverload(p6));
    }

    @Test
    public void WorkerJustBarelyNoOverload() {
        Worker worker = new Worker("Baker's Man", new HashSet<>(), 15);

        Project p1 = new Project("Baking a cake", new HashSet<>(), ProjectSize.BIG);
        Project p2 = new Project("As fast as he can", new HashSet<>(), ProjectSize.BIG); 
        Project p3 = new Project("Roll it", new HashSet<>(), ProjectSize.BIG); 
        Project p4 = new Project("Pat it", new HashSet<>(), ProjectSize.BIG); 

        worker.addProject(p1);
        worker.addProject(p2);
        worker.addProject(p3);
        System.out.println(worker.getWorkload());
        assertFalse(worker.willOverload(p4));
    }

    @Test 
    public void WorkerIsAvaiable() {
        Worker worker = new Worker("Alexander Hamillton", new HashSet<>(), 2);

        Project p1 = new Project("Why do you always write", new HashSet<>(), ProjectSize.BIG);
        Project p2 = new Project("Like you are running", new HashSet<>(), ProjectSize.BIG); 
        Project p3 = new Project("Out of time", new HashSet<>(), ProjectSize.BIG); 

        worker.addProject(p1);
        worker.addProject(p2);
        worker.addProject(p3);
        assertTrue(worker.isAvailable());
    }

    @Test
    public void WorkerIsNotAvailable() {
        Worker worker = new Worker("Aaron Burr", new HashSet<>(), 15);

        Project p1 = new Project("Hi Are you Aaron Burr, sir", new HashSet<>(), ProjectSize.BIG);
        Project p2 = new Project("That depends", new HashSet<>(), ProjectSize.BIG); 
        Project p3 = new Project("Oh sure Sit", new HashSet<>(), ProjectSize.SMALL); 
        Project p4 = new Project("My name is Alexander Hamilton", new HashSet<>(), ProjectSize.SMALL); 
        Project p5 = new Project("I'm at your service sir", new HashSet<>(), ProjectSize.MEDIUM); 
        Project p6 = new Project("I have been looking for you.", new HashSet<>(), ProjectSize.BIG); 

        worker.addProject(p1);
        worker.addProject(p2);
        worker.addProject(p3);
        worker.addProject(p4);
        worker.addProject(p5);
        worker.addProject(p6);
        
        assertFalse(worker.isAvailable());
    }
}
