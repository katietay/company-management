package edu.colostate.cs415.model;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;

public class CompanyTest {
	private Company company;
    private Worker worker1;
    private Worker worker2;

    @Before
    public void setUp() {
        company = new Company("Google");

        // Qualifications for workers
        Set<Qualification> qualifications = new HashSet<>();
        Qualification qual = company.createQualification("Software Engineering");
        qualifications.add(qual);

        // Create two workers
        worker1 = company.createWorker("Baslal", qualifications, 50000);
        worker2 = company.createWorker("Jake", qualifications, 60000);

        // Manually add them to the company's employees set
        //company.getEmployedWorkers().add(worker1);
        //company.getEmployedWorkers().add(worker2);
    }
	
	@Test
	public void test() {
		assert (true);
	}

	//Tests for Constructor:
	@Test
	public void testValidCompany() {
		Company company = new Company("VALID COMPANY NAME");
		assertNotNull(company);
	}

	@Test(expected = IllegalArgumentException.class)
    public void testNullName() {
        new Company(null); // should throw IllegalArguementException
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyName() {
        new Company(""); // should throw IllegalArguementException
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWhitespaceName() {
        new Company("   "); // should throw IllegalArguementException
    }

	//Tests for getName
	@Test
	public void testGetName(){
		Company company = new Company("VALID COMPANY NAME");
		
		assertEquals("VALID COMPANY NAME", company.getName());
	}

	// Tests for getAvailableWorkers()

    
    //Test that both workers are initially available 
    //assuming each has workload < 12 by default.
     
	@Test
    public void testGetAvailableWorkers_InitiallyAllAvailable() {
        System.out.println(company.getAvailableWorkers());
        assertEquals("Initially, both workers should be available",2,company.getAvailableWorkers().size());
        assertTrue(company.getAvailableWorkers().contains(worker1));
        assertTrue(company.getAvailableWorkers().contains(worker2));
    }

   
     // Test that assigning enough BIG projects to worker1 
     //makes them unavailable workload >= 12.
     
    @Test
    public void testGetAvailableWorkers_AfterProjectAssignment() {
        // Each BIG project +3 workload
        // 4 BIG projects =3*4 = 12 => meets thethreshold

        Project bigProject1 = new Project("BigProject1", new HashSet<>(), ProjectSize.BIG);
        Project bigProject2 = new Project("BigProject2", new HashSet<>(), ProjectSize.BIG);
        Project bigProject3 = new Project("BigProject3", new HashSet<>(), ProjectSize.BIG);
        Project bigProject4 = new Project("BigProject4", new HashSet<>(), ProjectSize.BIG);

        bigProject1.setStatus(ProjectStatus.ACTIVE);
        bigProject2.setStatus(ProjectStatus.ACTIVE);
        bigProject3.setStatus(ProjectStatus.ACTIVE);
        bigProject4.setStatus(ProjectStatus.ACTIVE);

        // Assign all 4 BIG projects to worker1
        worker1.addProject(bigProject1);
        worker1.addProject(bigProject2);
        worker1.addProject(bigProject3);
        worker1.addProject(bigProject4);

        // Now worker1's workload = 12 = isAvailable() = false
        assertEquals("Only worker2 should remain available after worker1 reaches workload >= 12", 
                     1, 
                     company.getAvailableWorkers().size());
        assertTrue(company.getAvailableWorkers().contains(worker2));
        assertFalse(company.getAvailableWorkers().contains(worker1));
    }

    
     //Test that an empty company has no available workers.
     
    @Test
    public void testGetAvailableWorkers_NoWorkers() {
        // Create a brand new, empty company
        Company emptyCompany = new Company("Google");
        assertTrue("No workers should be available in an empty company", 
                   emptyCompany.getAvailableWorkers().isEmpty());
    }


     // Tests for getUnavailableWorkers()
    
    
    // Test that initially all workers are available, so no unavailable workers.
     
    @Test
    public void testGetUnavailableWorkers_InitiallyAllAvailable() {
        assertEquals("Initially, no workers should be unavailable", 
                     0, 
                     company.getUnavailableWorkers().size());
    }
    
    
     //Test that overloading a worker makes them unavailable.
     
    @Test
    public void testGetUnavailableWorkers_AfterProjectAssignment() {
        // Each BIG project +3 workload
        // 4 BIG projects = 3 workers * 4 = 12

        Project bigProject1 = new Project("BigProject1", new HashSet<>(), ProjectSize.BIG);
        Project bigProject2 = new Project("BigProject2", new HashSet<>(), ProjectSize.BIG);
        Project bigProject3 = new Project("BigProject3", new HashSet<>(), ProjectSize.BIG);
        Project bigProject4 = new Project("BigProject4", new HashSet<>(), ProjectSize.BIG);

        bigProject1.setStatus(ProjectStatus.ACTIVE);
        bigProject2.setStatus(ProjectStatus.ACTIVE);
        bigProject3.setStatus(ProjectStatus.ACTIVE);
        bigProject4.setStatus(ProjectStatus.ACTIVE);

        // Assign all 4 BIG projects to worker1
        worker1.addProject(bigProject1);
        worker1.addProject(bigProject2);
        worker1.addProject(bigProject3);
        worker1.addProject(bigProject4);

        // Now worker1's workload = 12 = isAvailable() = false
        assertEquals("Worker1 should be unavailable after reaching workload >= 12", 
                     1, 
                     company.getUnavailableWorkers().size());
        assertTrue(company.getUnavailableWorkers().contains(worker1));
        assertFalse(company.getUnavailableWorkers().contains(worker2));
    }
    
    
     //Test that an empty company has no unavailable workers.
     
    @Test
    public void testGetUnavailableWorkers_NoWorkers() {
        
        Company emptyCompany = new Company("Google");
        assertTrue("No workers should be unavailable in an empty company", 
                   emptyCompany.getUnavailableWorkers().isEmpty());
    }
    
    
     //Test that all workers can be unavailable.
     
    @Test
    public void testGetUnavailableWorkers_AllUnavailable() {
        // Make both workers unavailable by assigning enough projects
        Project bigProject1 = new Project("BigProject1", new HashSet<>(), ProjectSize.BIG);
        Project bigProject2 = new Project("BigProject2", new HashSet<>(), ProjectSize.BIG);
        Project bigProject3 = new Project("BigProject3", new HashSet<>(), ProjectSize.BIG);
        Project bigProject4 = new Project("BigProject4", new HashSet<>(), ProjectSize.BIG);

        bigProject1.setStatus(ProjectStatus.ACTIVE);
        bigProject2.setStatus(ProjectStatus.ACTIVE);
        bigProject3.setStatus(ProjectStatus.ACTIVE);
        bigProject4.setStatus(ProjectStatus.ACTIVE);

        // Assign projects to both workers
        worker1.addProject(bigProject1);
        worker1.addProject(bigProject2);
        worker1.addProject(bigProject3);
        worker1.addProject(bigProject4);
        
        worker2.addProject(bigProject1);
        worker2.addProject(bigProject2);
        worker2.addProject(bigProject3);
        worker2.addProject(bigProject4);

        assertEquals("Both workers should be unavailable", 
                     2, 
                     company.getUnavailableWorkers().size());
        assertTrue(company.getUnavailableWorkers().contains(worker1));
        assertTrue(company.getUnavailableWorkers().contains(worker2));
    }
    
    
     // Test with null in employees list.
     
    @Test
    public void testGetUnavailableWorkers_WithNull() {
        company.getEmployedWorkers().add(null);
        
        // With default workloads, workers are available
        assertEquals("No workers should be unavailable by default", 
                     0, 
                     company.getUnavailableWorkers().size());
                     
        // Make worker1 unavailable . 
        Project bigProject1 = new Project("BigProject1", new HashSet<>(), ProjectSize.BIG);
        Project bigProject2 = new Project("BigProject2", new HashSet<>(), ProjectSize.BIG);
        Project bigProject3 = new Project("BigProject3", new HashSet<>(), ProjectSize.BIG);
        Project bigProject4 = new Project("BigProject4", new HashSet<>(), ProjectSize.BIG);

        bigProject1.setStatus(ProjectStatus.ACTIVE);
        bigProject2.setStatus(ProjectStatus.ACTIVE);
        bigProject3.setStatus(ProjectStatus.ACTIVE);
        bigProject4.setStatus(ProjectStatus.ACTIVE);

        worker1.addProject(bigProject1);
        worker1.addProject(bigProject2);
        worker1.addProject(bigProject3);
        worker1.addProject(bigProject4);
        
        assertEquals("Only worker1 should be unavailable", 
                     1, 
                     company.getUnavailableWorkers().size());
        assertTrue(company.getUnavailableWorkers().contains(worker1));
    }

    @Test
    public void CompanyEqualsTrue() {
        Company comp = new Company("Krusty Krab");

        Company comp1 = new Company("Krusty Krab");
        
        assertTrue(comp.equals(comp1));
    }

    @Test
    public void CompanyEqualsFalse() {
        Company comp = new Company("Krusty Krab");

        Company comp1 = new Company("Chum Bucket");
        
        assertFalse(comp.equals(comp1));
    }

    @Test
    public void CompanyEqualsNull() {
        Company comp = new Company("Krusty Krab");
        
        assertFalse(comp.equals(null));
    }

    @Test
    public void CompanyToString() {
        Company comp = new Company("Club Penguin");

        String test1 = "Club Penguin:0:0";
        assertTrue(test1.equals(comp.toString()));

    }
   
    @Test
    public void testGetQualificationsInitiallyEmpty() {
        Company newCompany = new Company("asdf");
        // We haven't added any qualifications to 'company'
        // so by default it should be empty if not set
        assertTrue("Expected no qualifications initially", 
        newCompany.getQualifications().isEmpty());
    }

    @Test
    public void testGetQualifications_WithSome() {
        // Directly adding to the 'qualifications' set in company
        // simulate a real createQualification if it existed
        Qualification q1 = company.createQualification("project management");
        Qualification q2 = company.createQualification("Senior Engineer");

        Set<Qualification> returnedQuals = company.getQualifications();
        assertEquals("Expected 3 qualifications in the company", 3, returnedQuals.size());
        assertTrue("Should contain 'Project Management'", returnedQuals.contains(q1));
        assertTrue("Should contain 'Senior Engineer'", returnedQuals.contains(q2));
    }

    @Test
    public void testGetQualifications_NoOrderingAssumption() {
        // Add multiple qualifications
        Qualification q1 = company.createQualification("project management");
        Qualification q2 = company.createQualification("Senior Engineer");

        // Just ensure we have 3 unique qualifications
        assertEquals("Expected 3 qualifications", 3, company.getQualifications().size());
    }

    public void testCreateQualification_Valid() {
        Qualification q = company.createQualification("Project Management");
        
        // the returned qualification isn't null
        assertNotNull("Expected a valid qualification, but got null", q);
        
        // the company's qualifications set isn't null
        assertNotNull("Company's qualifications set is null", company.getQualifications());
        
        // Verify that the newly created qualification is in the company's set
        assertTrue("Qualification should be added to the company's qualifications set",
                   company.getQualifications().contains(q));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testCreateQualification_Null() {
        // Passing null should throw an exception.
        company.createQualification(null);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testCreateQualification_Empty() {
        // Passing an empty string should throw an exception.
        company.createQualification("");
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testCreateQualification_Whitespace() {
        // Passing a string with only whitespace should throw an exception.
        company.createQualification("   ");
    }



    // Tests for createWorker()
    @Test
    public void testcreateWorker() {
        Company company = new Company("Organization XIII");

        //Should return null if company has no qualifications
        Qualification nobodyQual = new Qualification("Does Not Exist");
        Set<Qualification> nobodySkillset = new HashSet();
        nobodySkillset.add(nobodyQual);
        assertNull(company.createWorker("Nobody", nobodySkillset, 1));

        //First Worker 1 qual
        Qualification qual1 = company.createQualification("Has Fire");
        Set<Qualification> axelSkillset = new HashSet();
        axelSkillset.add(qual1);
        Worker axel = company.createWorker("Ax", axelSkillset, 1);
        assertNotNull(axel);
        assertTrue(company.getEmployedWorkers().contains(axel));

        //Second Worker different qual
        Qualification qual2 = company.createQualification("Has Blue Hair");
        Set<Qualification> saixSkillset = new HashSet();
        saixSkillset.add(qual2);
        Worker saix = company.createWorker("Sa", saixSkillset, 0);
        assertNotNull(saix);
        assertTrue(company.getEmployedWorkers().contains(saix));

        //Third Worker both quals
        Set<Qualification> hadesSkillset = new HashSet();
        hadesSkillset.add(qual1);
        hadesSkillset.add(qual2);
        Worker hades = company.createWorker("Ha", hadesSkillset, 1);
        assertNotNull(hades);
        assertTrue(company.getEmployedWorkers().contains(hades));


        //Check workers are listed in the company's relevant qualifications
        Set<Qualification> companyQualifications = company.getQualifications();
        for (Qualification qualIterator : companyQualifications) {
            if (qualIterator.equals(qual1)) {
                assertTrue(qualIterator.getWorkers().contains(axel));
                assertTrue(qualIterator.getWorkers().contains(hades));
            }
            if (qualIterator.equals(qual2)){
                assertTrue(qualIterator.getWorkers().contains(saix));
                assertTrue(qualIterator.getWorkers().contains(hades));
            }
        }
    }

    @Test
    public void testcreateWorkerNameLengthNot2() {
        Company company = new Company("Organization XIII");

        Qualification qual1 = company.createQualification("Has Fire");
        Set<Qualification> axelSkillset = new HashSet();
        axelSkillset.add(qual1);
        Worker axel = company.createWorker("A", axelSkillset, 1);
        assertNotNull(axel);
        assertTrue(company.getEmployedWorkers().contains(axel));

        //Check worker is listed in the company's relevant qualifications
        Set<Qualification> companyQualifications = company.getQualifications();
        for (Qualification qualIterator : companyQualifications) {
            if (qualIterator.equals(qual1)) {
                assertTrue(qualIterator.getWorkers().contains(axel));
            }
        }
    }

    @Test
    public void testcreateWorkerInvalidName() {
        Company company = new Company("Organization XIII");
        Qualification qual1 = company.createQualification("Has Fire");
        Set<Qualification> axelSkillset = new HashSet();
        axelSkillset.add(qual1);

        assertNull(company.createWorker("    ", axelSkillset, 1));
        assertNull(company.createWorker("", axelSkillset, 1));
        assertNull(company.createWorker(null, axelSkillset, 1));
    }

    @Test
    public void testcreateWorkerInvalidSalary() {
        Company company = new Company("Organization XIII");
        Qualification qual1 = company.createQualification("Has Fire");
        Set<Qualification> axelSkillset = new HashSet();
        axelSkillset.add(qual1);

        assertNull(company.createWorker("Ax", axelSkillset, -1));
    }

    @Test
    public void testcreateWorkerQualificationsInvalid() {
        Company company = new Company("Organization XIII");

        Qualification qual1 = company.createQualification("Has Fire");
        Set<Qualification> axelSkillset = new HashSet();
        axelSkillset.add(qual1);

        Qualification nobodyQual = new Qualification("Does Not Exist");
        axelSkillset.add(nobodyQual);
        assertNull(company.createWorker("Ax", axelSkillset, 1));

        Qualification spikyHairQual = new Qualification("Has Spiky Hair");
        axelSkillset.add(spikyHairQual);
        assertNull(company.createWorker("Ax", axelSkillset, 1));

        Set<Qualification> soraSkillset = new HashSet();
        soraSkillset.add(spikyHairQual);
        assertNull(company.createWorker("So", soraSkillset, 1));

        Set<Qualification> xionSkillset = new HashSet();
        assertNull(company.createWorker("Xi", xionSkillset, 1));
        assertNull(company.createWorker("Xi", null, 1));

    }



    // testing create project:
    @Test
    public void testCreateProject_Valid() {
        
        Qualification q1 = company.createQualification("Project Management");
        Qualification q2 = company.createQualification("Java Developer");

        // Create a set of qualifications to use for the project.
        Set<Qualification> qs = new HashSet<>();
        qs.add(q1);
        qs.add(q2);

        Project p = company.createProject("Travel Management Application", qs, ProjectSize.MEDIUM);

        // Check that the project is created correctly.
        assertNotNull("Project should not be null", p);
        assertEquals("Travel Management Application", p.getName());
        assertEquals(ProjectSize.MEDIUM, p.getSize());
        
        // Verify that the project's qualification requirements include the provided qualifications.
        Set<Qualification> projQuals = p.getRequiredQualifications();
        assertEquals("Project should have 2 qualifications", 2, projQuals.size());
        assertTrue(projQuals.contains(q1));
        assertTrue(projQuals.contains(q2));
        
        assertTrue(company.getProjects().contains(p));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateProject_NullName() {
        Qualification q1 = new Qualification("Project Management");
        company.getQualifications().add(q1);
        Set<Qualification> qs = new HashSet<>();
        qs.add(q1);
        company.createProject(null, qs, ProjectSize.BIG);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateProject_EmptyName() {
        Qualification q1 = new Qualification("Project Management");
        company.getQualifications().add(q1);
        Set<Qualification> qs = new HashSet<>();
        qs.add(q1);
        company.createProject("", qs, ProjectSize.BIG);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateProject_NullQualifications() {
        company.createProject("Travel Management Application", null, ProjectSize.BIG);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateProject_EmptyQualifications() {
        Set<Qualification> emptyQs = new HashSet<>();
        company.createProject("Test Project", emptyQs, ProjectSize.BIG);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateProject_QualificationNotInCompany() {
        Set<Qualification> qs = new HashSet<>();
        qs.add(new Qualification("Non-existent Qualification"));
        company.createProject("Test Project", qs, ProjectSize.BIG);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateProject_NullSize() {
        Qualification q1 = new Qualification("Project Management");
        company.getQualifications().add(q1);
        Set<Qualification> qs = new HashSet<>();
        qs.add(q1);
        company.createProject("Test Project", qs, null);
    }



    // Tests for start()
    @Test
    public void testStart() {
        Company company = new Company("Organization XIII");

        //First Worker 1 qual
        Qualification qual1 = company.createQualification("Has Fire");
        Set<Qualification> axelSkillset = new HashSet();
        axelSkillset.add(qual1);
        Worker axel = company.createWorker("Ax", axelSkillset, 1);

        //Second Worker different qual
        Qualification qual2 = company.createQualification("Has Blue Hair");
        Set<Qualification> saixSkillset = new HashSet();
        saixSkillset.add(qual2);
        Worker saix = company.createWorker("Sa", saixSkillset, 0);

        //Third Worker both quals
        Set<Qualification> hadesSkillset = new HashSet();
        hadesSkillset.add(qual1);
        hadesSkillset.add(qual2);
        Worker hades = company.createWorker("Ha", hadesSkillset, 1);

        //Fourth Worker no relevant quals
        Qualification nobodyQual = company.createQualification("Does Not Exist");
        Set<Qualification> nobodySkillset = new HashSet();
        nobodySkillset.add(nobodyQual);
        Worker nobody = company.createWorker("Nobody", nobodySkillset, 1);

        Project project = company.createProject("Kingdom Hearts", hadesSkillset, ProjectSize.BIG);
        project.addWorker(nobody);
        company.start(project);
        assertFalse(project.getStatus().equals(ProjectStatus.ACTIVE));
        project.addWorker(axel);
        company.start(project);
        assertFalse(project.getStatus().equals(ProjectStatus.ACTIVE));
        project.addWorker(hades);
        company.start(project);
        assertTrue(project.getStatus().equals(ProjectStatus.ACTIVE));
        project.setStatus(ProjectStatus.SUSPENDED);
        company.start(project);
        assertTrue(project.getStatus().equals(ProjectStatus.ACTIVE));
        project.setStatus(ProjectStatus.FINISHED);
        company.start(project);
        assertFalse(project.getStatus().equals(ProjectStatus.ACTIVE));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testStartNullProject() {
        Company company = new Company("Organization XIII");
        company.start(null);
    }



    // Tests for assign()
    @Test
    public void testAssign() {
        Company company = new Company("Organization XIII");

        //First Worker 1 qual
        Qualification qual1 = company.createQualification("Has Fire");
        Set<Qualification> axelSkillset = new HashSet();
        axelSkillset.add(qual1);
        Worker axel = company.createWorker("Ax", axelSkillset, 1);

        //Second Worker different qual
        Qualification qual2 = company.createQualification("Has Blue Hair");
        Set<Qualification> saixSkillset = new HashSet();
        saixSkillset.add(qual2);
        Worker saix = company.createWorker("Sa", saixSkillset, 0);

        //Third Worker both quals
        Set<Qualification> hadesSkillset = new HashSet();
        hadesSkillset.add(qual1);
        hadesSkillset.add(qual2);
        Worker hades = company.createWorker("Ha", hadesSkillset, 1);

        //Fourth Worker no relevant quals
        Qualification nobodyQual = company.createQualification("Does Not Exist");
        Set<Qualification> nobodySkillset = new HashSet();
        nobodySkillset.add(nobodyQual);
        Worker nobody = company.createWorker("Nobody", nobodySkillset, 1);

        Project project = company.createProject("Kingdom Hearts", hadesSkillset, ProjectSize.BIG);
        
        // Base Case
        assertEquals(axel.getWorkload(), 0);
        company.assign(axel, project);
        assertEquals(axel.getWorkload(), 3);
        assertTrue(project.getWorkers().contains(axel));
        project.setStatus(ProjectStatus.SUSPENDED);
        company.assign(saix, project);
        assertEquals(saix.getWorkload(), 3);
        assertTrue(project.getWorkers().contains(saix));

        // Wrong status
        Project project2 = company.createProject("Kingdom Hearts 2", hadesSkillset, ProjectSize.BIG);

        project2.setStatus(ProjectStatus.ACTIVE);
        company.assign(axel, project2);
        assertEquals(axel.getWorkload(), 3);
        assertFalse(project2.getWorkers().contains(axel));

        project2.setStatus(ProjectStatus.FINISHED);
        company.assign(axel, project2);
        assertEquals(axel.getWorkload(), 3);
        assertFalse(project2.getWorkers().contains(axel));

        // Useless qualification
        project2.setStatus(ProjectStatus.PLANNED);
        company.assign(nobody, project2);
        assertEquals(nobody.getWorkload(), 0);
        assertFalse(project2.getWorkers().contains(nobody));

        // Full workload
        company.assign(axel, project2);
        Project project3 = company.createProject("Kingdom Hearts 3", hadesSkillset, ProjectSize.BIG);
        company.assign(axel, project3);
        Project project4 = company.createProject("Kingdom Hearts 4", hadesSkillset, ProjectSize.BIG);
        company.assign(axel, project4);
        assertEquals(axel.getWorkload(), 12);
        Project project5 = company.createProject("Kingdom Hearts 5", hadesSkillset, ProjectSize.BIG);
        company.assign(axel, project5);
        assertEquals(axel.getWorkload(), 12);
        assertFalse(project5.getWorkers().contains(axel));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAssignNullProject() {
        Company company = new Company("Organization XIII");
        Qualification qual = company.createQualification("Has Blue Hair");
        Set<Qualification> saixSkillset = new HashSet();
        saixSkillset.add(qual);
        Worker saix = company.createWorker("Sa", saixSkillset, 0);
        company.assign(saix, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAssignNullWorker() {
        Company company = new Company("Organization XIII");
        Qualification qual = company.createQualification("Has Blue Hair");
        Set<Qualification> saixSkillset = new HashSet();
        saixSkillset.add(qual);
        Project project = company.createProject("Kingdom Hearts", saixSkillset, ProjectSize.BIG);
        company.assign(null, project);
    }

    // test unassigned workers
    @Test
    public void testGetUnassignedWorkers_InitiallyAllUnassigned() {
        Set<Worker> unassigned = company.getUnassignedWorkers();
        assertEquals("Both workers should be unassigned initially", 2, unassigned.size());
        assertTrue(unassigned.contains(worker1));
        assertTrue(unassigned.contains(worker2));
    }

    @Test
    public void testGetUnassignedWorkers_AfterAssignment() {
        Project project = new Project("Mobile App", new HashSet<>(), ProjectSize.MEDIUM);
        project.setStatus(ProjectStatus.ACTIVE);
        worker1.addProject(project);
        
        Set<Worker> unassigned = company.getUnassignedWorkers();
        assertEquals("Only worker2 should be unassigned after worker1 is assigned", 1, unassigned.size());
        assertTrue(unassigned.contains(worker2));
        assertFalse(unassigned.contains(worker1));
    }

    @Test
    public void testGetUnassignedWorkers_NoWorkers() {
        Company emptyCompany = new Company("EmptyCompany");
        assertTrue("No workers should be unassigned in an empty company", 
        emptyCompany.getUnassignedWorkers().isEmpty());
    }

    //test getAssignedWorker
    @Test
    public void testGetAssignedWorkers_InitiallyNoneAssigned() {
        Set<Worker> assigned = company.getAssignedWorkers();
        assertEquals("Initially, no workers should be assigned", 0, assigned.size());
    }
    
    @Test
    public void testGetAssignedWorkers_AfterAssignment() {
        Set<Qualification> qualifications = company.getQualifications();
        Project project = company.createProject("Chat bot", qualifications, ProjectSize.MEDIUM);
        company.assign(worker1, project);
        
        Set<Worker> assigned = company.getAssignedWorkers();
        assertEquals("After assignment, only one worker should be assigned", 1, assigned.size());
        assertTrue("Worker1 should be assigned", assigned.contains(worker1));
        assertFalse("Worker2 should not be assigned", assigned.contains(worker2));
    }
    
    @Test
    public void testGetAssignedWorkers_AllAssigned() {
        Set<Qualification> qualifications = company.getQualifications();
        Project project1 = company.createProject("Project 1", qualifications, ProjectSize.SMALL);
        company.assign(worker1, project1);
        project1.setStatus(ProjectStatus.ACTIVE);
        
        Project project2 = company.createProject("Project 2", qualifications, ProjectSize.SMALL);
        company.assign(worker2, project2);
        project1.setStatus(ProjectStatus.ACTIVE);
        
        Set<Worker> assigned = company.getAssignedWorkers();
        assertEquals("All workers should be assigned", 2, assigned.size());
        assertTrue(assigned.contains(worker1));
        assertTrue(assigned.contains(worker2));
    }
    
    @Test
    public void testGetAssignedWorkers_NoWorkers() {
        Company emptyCompany = new Company("EmptyCompany");
        assertTrue("No workers should be assigned in an empty company", emptyCompany.getAssignedWorkers().isEmpty());
    }


    @Test
    public void testCompanyHashcode() {
        Company comp = new Company("A Cool Company Name");

        assertEquals(0, comp.hashCode());
    }

    //test GetProjects()
    @Test
    public void testGetProjectsInitiallyEmpty() {
        Company emptyCompany = new Company("EmptyCo");
        
        Set<Project> projects = emptyCompany.getProjects();
        assertNotNull("Projects set should not be null", projects);
        assertTrue("Projects set should be empty initially", projects.isEmpty());
    }

    @Test
    public void testGetProjects_AfterOneProject() {
        Qualification q1 = company.createQualification("Security Clearance");
        
        Set<Qualification> reqs = new HashSet<>();
        reqs.add(q1);

        Project p1 = company.createProject("A1 Project", reqs, ProjectSize.SMALL);

        Set<Project> projects = company.getProjects();
        assertNotNull("Projects set should not be null", projects);
        assertEquals("There should be exactly one project", 1, projects.size());
        assertTrue("Projects set should contain the newly created project", projects.contains(p1));
    }

    @Test
    public void testGetProjects_AfterMultipleProjects() {

        Qualification q1 = company.createQualification("Backend");
        Qualification q2 = company.createQualification("Frontend");

        Set<Qualification> reqs1 = new HashSet<>();
        reqs1.add(q1);

        Set<Qualification> reqs2 = new HashSet<>();
        reqs2.add(q2);

        Project p1 = company.createProject("B1 Project", reqs1, ProjectSize.MEDIUM);
        Project p2 = company.createProject("G1 Project", reqs2, ProjectSize.BIG);

        Set<Project> projects = company.getProjects();
        assertEquals("There should be exactly two projects", 2, projects.size());
        assertTrue(projects.contains(p1));
        assertTrue(projects.contains(p2));
    }

    @Test
    public void testGetProjects_NoOrderingAssumption() {

        Qualification q1 = company.createQualification("AI");
        Qualification q2 = company.createQualification("ML");
    
        Set<Qualification> reqs1 = new HashSet<>();
        reqs1.add(q1);
        Project p1 = company.createProject("D1 Project", reqs1, ProjectSize.SMALL);

        Set<Qualification> reqs2 = new HashSet<>();
        reqs2.add(q2);
        Project p2 = company.createProject("E1 Project", reqs2, ProjectSize.BIG);

        Set<Project> projects = company.getProjects();
        assertEquals("Should have exactly two projects", 2, projects.size());
        assertTrue("Projects set should contain 'Delta Project'", projects.contains(p1));
        assertTrue("Projects set should contain 'Epsilon Project'", projects.contains(p2));
    }

    @Test
    public void testGetProjects_DuplicateProject() {

        Qualification q1 = company.createQualification("Design");
        Set<Qualification> reqs = new HashSet<>();
        reqs.add(q1);

        Project p1 = company.createProject("O1 Project", reqs, ProjectSize.MEDIUM);
        Project p2 = company.createProject("O1 Project", reqs, ProjectSize.MEDIUM); // same name

        Set<Project> projects = company.getProjects();
        
        assertNotNull(projects);
        assertTrue("Projects set should contain p1", projects.contains(p1));
        assertTrue("Projects set should contain p2 if it's considered different", 
        projects.contains(p2));
        
        assertTrue("At least 1 project with name 'Omega Project' should exist", 
        projects.size() >= 1);
    }

    @Test
    public void testAssign_ValidAssignment() {
        Company company = new Company("Birdwatching Inc.");

        Qualification flying = company.createQualification("Flying");
        Qualification hunting = company.createQualification("Hunting");

        Set<Qualification> falconSkills = new HashSet<>();
        falconSkills.add(flying);
        falconSkills.add(hunting);

        Worker falcon = company.createWorker("Falcon", falconSkills, 1);
        Project skyPatrol = company.createProject("Sky Patrol", falconSkills, ProjectSize.MEDIUM);

        assertEquals(0, falcon.getWorkload());
        company.assign(falcon, skyPatrol);

        assertTrue(skyPatrol.getWorkers().contains(falcon));
        assertEquals(2, falcon.getWorkload());
    }

    @Test
    public void testAssign_WillOverload() {
        Company company = new Company("Birdwatching Inc.");

        Qualification flying = company.createQualification("Flying");
        Qualification hunting = company.createQualification("Hunting");

        Set<Qualification> falconSkills = new HashSet<>();
        falconSkills.add(flying);
        falconSkills.add(hunting);

        Worker falcon = company.createWorker("Falcon", falconSkills, 1);
        Project skyPatrol = company.createProject("Sky Patrol", falconSkills, ProjectSize.BIG);
        Project skyPatrol2 = company.createProject("Sky Patrol2", falconSkills, ProjectSize.BIG);
        Project skyPatrol3 = company.createProject("Sky Patrol3", falconSkills, ProjectSize.BIG);
        Project skyPatrol4 = company.createProject("Sky Patrol4", falconSkills, ProjectSize.BIG);
        Project skyPatrol5 = company.createProject("Sky Patrol5", falconSkills, ProjectSize.BIG);


        assertEquals(0, falcon.getWorkload());
        company.assign(falcon, skyPatrol);
        company.assign(falcon, skyPatrol2);
        company.assign(falcon, skyPatrol3);
        company.assign(falcon, skyPatrol4);
        assertEquals(12, falcon.getWorkload());
        company.assign(falcon, skyPatrol5);
        assertEquals(12, falcon.getWorkload());
    }

    @Test
    public void testUnassign_SingleWorker() {
        Company company = new Company("Birdwatching Inc.");

        Qualification soaring = company.createQualification("Soaring");
        Set<Qualification> eagleSkills = new HashSet<>();
        eagleSkills.add(soaring);

        Worker eagle = company.createWorker("Eagle", eagleSkills, 1);
        Project windStudy = company.createProject("Wind Study", eagleSkills, ProjectSize.SMALL);

        company.assign(eagle, windStudy);
        assertTrue(windStudy.getWorkers().contains(eagle));

        company.unassign(eagle, windStudy);
        assertFalse(windStudy.getWorkers().contains(eagle));
        assertTrue(company.getAvailableWorkers().contains(eagle));
    }

    @Test
    public void testUnassign_ActiveProjectBecomesSuspended() {
        Company company = new Company("Birdwatching Inc.");

        Qualification flying = company.createQualification("Flying");
        Set<Qualification> owlSkills = new HashSet<>();
        owlSkills.add(flying);

        Worker owl = company.createWorker("Owl", owlSkills, 1);
        Project nightHunt = company.createProject("Night Hunt", owlSkills, ProjectSize.BIG);

        company.assign(owl, nightHunt);
        nightHunt.setStatus(ProjectStatus.ACTIVE);

        company.unassign(owl, nightHunt);
        assertEquals(ProjectStatus.SUSPENDED, nightHunt.getStatus());
    }

    @Test
    public void testUnassign_MultipleProjectsWorkerStillAssigned() {
        Company company = new Company("Birdwatching Inc.");

        Qualification migration = company.createQualification("Migration");

        Set<Qualification> gooseSkills = new HashSet<>();
        gooseSkills.add(migration);

        Worker goose = company.createWorker("Goose", gooseSkills, 1);
        Project migrationStudy = company.createProject("Migration Study", gooseSkills, ProjectSize.SMALL);
        Project riverSurvey = company.createProject("River Survey", gooseSkills, ProjectSize.SMALL);

        company.assign(goose, migrationStudy);
        company.assign(goose, riverSurvey);
        assertTrue(company.getAssignedWorkers().contains(goose));

        company.unassign(goose, migrationStudy);
        assertTrue(company.getAssignedWorkers().contains(goose)); 
    }

    @Test
    public void testUnassign_WorkerNotAssigned_DoesNothing() {
        Company company = new Company("Birdwatching Inc.");

        Qualification diving = company.createQualification("Diving");

        Set<Qualification> duckSkills = new HashSet<>();
        duckSkills.add(diving);

        Worker duck = company.createWorker("Duck", duckSkills, 1);
        Project pondResearch = company.createProject("Pond Research", duckSkills, ProjectSize.MEDIUM);

        assertFalse(pondResearch.getWorkers().contains(duck));

        company.unassign(duck, pondResearch);

        assertFalse(pondResearch.getWorkers().contains(duck));
        assertFalse(company.getAssignedWorkers().contains(duck));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUnassign_NullWorker() {
        Company company = new Company("Birdwatching Inc.");
        Qualification swimming = company.createQualification("Swimming");

        Set<Qualification> duckSkills = new HashSet<>();
        duckSkills.add(swimming);

        Project pondProject = company.createProject("Pond Project", duckSkills, ProjectSize.SMALL);
        company.unassign(null, pondProject);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUnassign_NullProject() {
        Company company = new Company("Birdwatching Inc.");
        Qualification fishing = company.createQualification("Fishing");

        Set<Qualification> heronSkills = new HashSet<>();
        heronSkills.add(fishing);

        Worker heron = company.createWorker("Heron", heronSkills, 1);
        company.unassign(heron, null);
    }

    // test for finish:
    @Test(expected = IllegalArgumentException.class)
    public void testFinish_NullProject() {
        company.finish(null);
    }

    @Test
    public void testFinish_NonActiveProject() {
        
        Qualification q = company.createQualification("Dummy");
        Set<Qualification> qs = new HashSet<>();
        qs.add(q);
        Project p = company.createProject("Planned Project", qs, ProjectSize.SMALL);
        // p's status is PLANNED by default; finish() should do nothing.
        company.finish(p);
        assertEquals("Project status should remain PLANNED", ProjectStatus.PLANNED, p.getStatus());
    }




    @Test
    public void testFinish_ActiveProject() {
        Qualification q = company.createQualification("Ram");
        Set<Qualification> qs = new HashSet<>();
        qs.add(q);
    
        Project p = company.createProject("Active Project", qs, ProjectSize.SMALL);
        p.setStatus(ProjectStatus.ACTIVE);
    
        worker1.addProject(p);
        p.addWorker(worker1);
        
        company.finish(p);
    
        assertEquals("Project should be marked as FINISHED", ProjectStatus.FINISHED, p.getStatus());
        assertTrue("Project should have no workers after finishing", p.getWorkers().isEmpty());
        assertFalse("Worker should no longer be assigned to the project", worker1.getProjects().contains(p));
        assertFalse("Worker should be removed from assigned set", company.getAssignedWorkers().contains(worker1));
        assertTrue("Worker should be in available set", company.getAvailableWorkers().contains(worker1));
    }

    @Test
    public void testFinish_ActiveProjectWorkerWith2Projects() {
        Company company = new Company("Birdwatching Inc.");

        Qualification flying = company.createQualification("Flying");
        Qualification hunting = company.createQualification("Hunting");

        Set<Qualification> falconSkills = new HashSet<>();
        falconSkills.add(flying);
        falconSkills.add(hunting);

        Worker falcon = company.createWorker("Falcon", falconSkills, 1);
        Project skyPatrol = company.createProject("Sky Patrol", falconSkills, ProjectSize.MEDIUM);
        Project skyPatrol2 = company.createProject("Sky Patrol2", falconSkills, ProjectSize.MEDIUM);

        //Add first project
        assertEquals(0, falcon.getWorkload());
        company.assign(falcon, skyPatrol);
        assertTrue(skyPatrol.getWorkers().contains(falcon));
        assertEquals(2, falcon.getWorkload());
        assertTrue(falcon.getProjects().contains(skyPatrol));

        //Add second project
        company.assign(falcon, skyPatrol2);
        assertTrue(skyPatrol2.getWorkers().contains(falcon));
        assertEquals(4, falcon.getWorkload());
        assertTrue(falcon.getProjects().contains(skyPatrol2));

        skyPatrol.setStatus(ProjectStatus.ACTIVE);
        skyPatrol2.setStatus(ProjectStatus.ACTIVE);
        
        company.finish(skyPatrol);

        assertEquals(falcon.getWorkload(), 2);

        assertTrue("Worker should NOT be removed from assigned set", company.getActualAssignedWorkers().contains(falcon));
        assertEquals("Project should be marked as FINISHED", ProjectStatus.FINISHED, skyPatrol.getStatus());
        assertTrue("Project should have no workers after finishing", skyPatrol.getWorkers().isEmpty());
        assertFalse("Worker should no longer be assigned to the project", falcon.getProjects().contains(skyPatrol));
        assertTrue("Worker should be in available set", company.getAvailableWorkers().contains(falcon));
    }
    @Test
    public void testUnassignAll_WithProjects() {
      
        Qualification q = company.createQualification("Dummy");
        Set<Qualification> qs = new HashSet<>();
        qs.add(q);
        Project p = company.createProject("Test Project", qs, ProjectSize.SMALL);
        p.setStatus(ProjectStatus.ACTIVE);

        
        worker1.addProject(p);
        p.addWorker(worker1);

            
        company.unassignAll(worker1);

        
         assertTrue("Worker1 should have no projects after unassignAll", 
         worker1.getProjects().isEmpty());

        
        assertFalse("Worker1 should be removed from assigned set", 
        company.getAssignedWorkers().contains(worker1));

            
        assertTrue("Worker1 should be in available set", 
        company.getAvailableWorkers().contains(worker1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUnassignAll_WithProjects_NullWorker() {
        Qualification q = new Qualification("Dummy");
        Set<Qualification> qs = new HashSet<>();
        qs.add(q);
        company.getQualifications().add(q);
        Project p = company.createProject("Test Project", qs, ProjectSize.SMALL);
        p.setStatus(ProjectStatus.ACTIVE);

        
        worker1.addProject(p);
        p.addWorker(worker1);

            
        company.unassignAll(null);
    }


        
    
    

}
