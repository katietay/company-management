package edu.colostate.cs415.model;

import edu.colostate.cs415.model.ProjectSize;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

import static org.junit.Assert.*;


import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Test;

import edu.colostate.cs415.dto.ProjectDTO;


public class ProjectTest {
	@Test
	public void test() {
		assert (true);
	}

	// Tests for Constructor
	@Test
    public void testValidProject() {
		Set<Qualification> qualifications = new HashSet<>();
		Project project0 = new Project("Testing Project", qualifications, ProjectSize.MEDIUM);
        assertNotNull(project0);
		qualifications.add(new Qualification("Testing 1"));
		Project project1 = new Project("Testing Project", qualifications, ProjectSize.MEDIUM);
        assertNotNull(project1);
		qualifications.add(new Qualification("Testing 2"));
		Project project2 = new Project("Testing Project", qualifications, ProjectSize.MEDIUM);
        assertNotNull(project2);
		Project project3 = new Project("T", qualifications, ProjectSize.MEDIUM);
        assertNotNull(project3);
		Project project4 = new Project("Testing Project", qualifications, ProjectSize.BIG);
        assertNotNull(project4);
		Project project5 = new Project("Testing Project", qualifications, ProjectSize.SMALL);
        assertNotNull(project5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullName() {
		Set<Qualification> qualifications = new HashSet<>();
		qualifications.add(new Qualification("Testing 1"));
		Project project = new Project(null, qualifications, ProjectSize.MEDIUM);// Should throw an exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyName() {
		Set<Qualification> qualifications = new HashSet<>();
		qualifications.add(new Qualification("Testing 1"));
		Project project = new Project("", qualifications, ProjectSize.MEDIUM);// Should throw an exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWhitespaceName() {
        Set<Qualification> qualifications = new HashSet<>();
		qualifications.add(new Qualification("Testing 1"));
		Project project = new Project("      ", qualifications, ProjectSize.MEDIUM);// Should throw an exception
    }

	@Test(expected = IllegalArgumentException.class)
    public void testNullQualifications() {
		Project project = new Project("Testing Project", null, ProjectSize.MEDIUM);// Should throw an exception
    }

	@Test(expected = IllegalArgumentException.class)
    public void testNullSize() {
        Set<Qualification> qualifications = new HashSet<>();
		qualifications.add(new Qualification("Testing 1"));
		Project project = new Project("Testing Project", qualifications, null);// Should throw an exception
    }

	

	// Tests for Equals
	@Test
	public void TestEquals() {
		Set<Qualification> qualifications = new HashSet<>();
		qualifications.add(new Qualification("Qualification"));

		Project project1 = new Project("P1", qualifications, ProjectSize.MEDIUM);
		Project project2 = new Project("P1", qualifications, ProjectSize.SMALL);
		Project project3 = new Project("P2", qualifications, ProjectSize.MEDIUM);

		assertTrue(project1.equals(project2));
		assertFalse(project1.equals(project3));
		assertFalse(project1.equals(new Qualification("Qualification")));

		Project project4 = new Project("1", qualifications, ProjectSize.SMALL);
		Project project5 = new Project("1", qualifications, ProjectSize.MEDIUM);

		assertTrue(project4.equals(project5));
		assertTrue(project4.equals(project4));

		assertFalse(project1.equals(null));
	}



	// Tests for hashCode()
	@Test
	public void testHashCode() {
		Set<Qualification> qualifications = new HashSet<>();
		qualifications.add(new Qualification("Qualification"));

		Project project1 = new Project("P1", qualifications, ProjectSize.MEDIUM);
		int project1Hash = project1.hashCode();
		Project project2 = new Project("P1", qualifications, ProjectSize.MEDIUM);
		int project2Hash = project2.hashCode();
		assertEquals(project1Hash, project2Hash);
		assertEquals(project1Hash, project1.hashCode());
		assertEquals(project2Hash, project2.hashCode());
		assertFalse(project2.hashCode() == 0);

		Project project3 = new Project("P1", qualifications, ProjectSize.SMALL);
		int project3Hash = project3.hashCode();
		Project project4 = new Project("P1", qualifications, ProjectSize.SMALL);
		int project4Hash = project4.hashCode();
		assertEquals(project3Hash, project4Hash);
		assertEquals(project3Hash, project3.hashCode());
		assertEquals(project4Hash, project4.hashCode());

		Project project5 = new Project("P1", qualifications, ProjectSize.BIG);
		int project5Hash = project5.hashCode();
		Project project6 = new Project("P1", qualifications, ProjectSize.BIG);
		int project6Hash = project6.hashCode();
		assertEquals(project5Hash, project6Hash);
		assertEquals(project5Hash, project5.hashCode());
		assertEquals(project6Hash, project6.hashCode());

		Project project7 = new Project("P", qualifications, ProjectSize.MEDIUM);
		int project7Hash = project7.hashCode();
		Project project8 = new Project("P", qualifications, ProjectSize.MEDIUM);
		int project8Hash = project8.hashCode();
		assertEquals(project7Hash, project8Hash);
		assertEquals(project7Hash, project7.hashCode());
		assertEquals(project8Hash, project8.hashCode());

		project7.setStatus(ProjectStatus.SUSPENDED);
		project7Hash = project7.hashCode();
		project8.setStatus(ProjectStatus.SUSPENDED);
		project8Hash = project8.hashCode();
		assertEquals(project7Hash, project8Hash);
		assertEquals(project7Hash, project7.hashCode());
		assertEquals(project8Hash, project8.hashCode());

		project7.setStatus(ProjectStatus.ACTIVE);
		project7Hash = project7.hashCode();
		project8.setStatus(ProjectStatus.ACTIVE);
		project8Hash = project8.hashCode();
		assertEquals(project7Hash, project8Hash);
		assertEquals(project7Hash, project7.hashCode());
		assertEquals(project8Hash, project8.hashCode());

		project7.setStatus(ProjectStatus.FINISHED);
		project7Hash = project7.hashCode();
		project8.setStatus(ProjectStatus.FINISHED);
		project8Hash = project8.hashCode();
		assertEquals(project7Hash, project8Hash);
		assertEquals(project7Hash, project7.hashCode());
		assertEquals(project8Hash, project8.hashCode());
	}



	// Tests for toString()
	@Test
	public void testToStringNoWorkers(){
		Set<Qualification> qualifications = new HashSet<>();
		qualifications.add(new Qualification("Testing 1"));
		Project project = new Project("CS5Anniv", qualifications, ProjectSize.BIG);

		String expected = "CS5Anniv:0:PLANNED";
		assertEquals(expected, project.toString());
	}

	@Test
	public void testToString2Workers(){
		Set<Qualification> qualifications = new HashSet<>();
		qualifications.add(new Qualification("Testing 2"));
		Project project = new Project("CS5Anniv", qualifications, ProjectSize.BIG);

		Worker worker1 = new Worker("Nate", qualifications, 500.0);
		Worker worker2 = new Worker("Nick", qualifications, 600.0);

		project.addWorker(worker1);
		project.addWorker(worker2);

		String expected = "CS5Anniv:2:PLANNED";
		assertEquals(expected, project.toString());
	}

	@Test
	public void testToString3Workers(){
		Set<Qualification> qualifications = new HashSet<>();
		qualifications.add(new Qualification("Testing 3"));
		Project project = new Project("CS5Anniv", qualifications, ProjectSize.SMALL);

		Worker worker1 = new Worker("Tim", qualifications, 700.0);
		Worker worker2 = new Worker("Luke", qualifications, 800.0);
		Worker worker3 = new Worker("Karen", qualifications, 900.0);

		project.addWorker(worker1);
		project.addWorker(worker2);
		project.addWorker(worker3);

		String expected = "CS5Anniv:3:PLANNED";
		assertEquals(expected, project.toString());
	}

	@Test
	public void testToStringDifferentStatus(){
		Set<Qualification> qualifications = new HashSet<>();
		qualifications.add(new Qualification("Testing 4"));
		Project project = new Project("CS5Anniv", qualifications, ProjectSize.SMALL);

		project.setStatus(ProjectStatus.ACTIVE);

		String expected = "CS5Anniv:0:ACTIVE";
		assertEquals(expected, project.toString());
	}



	// Tests for getName()
	@Test
	public void testGetName(){
		Set<Qualification> qualifications = new HashSet<>();
		qualifications.add(new Qualification("Testing 1"));

		Project project = new Project("CS5Anniv", qualifications, ProjectSize.MEDIUM);
		assertEquals("CS5Anniv", project.getName());

		Project project2 = new Project("1", qualifications, ProjectSize.MEDIUM);
		assertEquals("1", project2.getName());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetNameEmpty(){
		Set<Qualification> qualifications = new HashSet<>();
		qualifications.add(new Qualification("Testing 1"));
		Project project = new Project("", qualifications, ProjectSize.SMALL);

		project.getName();
	}



	// Tests for getSize()
	@Test
	public void ProjectSizeTest() {
		Set<Qualification> qualifications = new HashSet<>();
		qualifications.add(new Qualification("Testing 1"));

		Project p1 = new Project("Testing Project", qualifications, ProjectSize.SMALL);
		assertTrue(ProjectSize.SMALL.equals(p1.getSize()));

		Project p2 = new Project("Testing Project", qualifications, ProjectSize.MEDIUM);
		assertTrue(ProjectSize.MEDIUM.equals(p2.getSize()));

		Project p3 = new Project("Testing Project", qualifications, ProjectSize.BIG);
		assertTrue(ProjectSize.BIG.equals(p3.getSize()));
	}



	// Tests for getStatus()
	@Test
	public void testGetStatusInitial(){
		Set<Qualification> qualifications = new HashSet<>();
		qualifications.add(new Qualification("Testing Initial"));
		Project project = new Project("CS5Anniv", qualifications, ProjectSize.MEDIUM);

		assertEquals(ProjectStatus.PLANNED, project.getStatus());
	}

	@Test
	public void testGetStatusAfter(){
		Set<Qualification>  qualifications = new HashSet<>();
		qualifications.add(new Qualification("Testing After"));
		Project project = new Project("CS5Anniv", qualifications, ProjectSize.SMALL);

		project.setStatus(ProjectStatus.ACTIVE);
		assertEquals(ProjectStatus.ACTIVE, project.getStatus());
	}



	// Tests for setStatus()
	@Test
    public void testValidSetStatus() {
		Set<Qualification> qualifications = new HashSet<>();
		qualifications.add(new Qualification("Testing 1"));
		Project project = new Project("Testing Project", qualifications, ProjectSize.SMALL);
        assertNotNull(project);

		project.setStatus(ProjectStatus.SUSPENDED);
		assertEquals(ProjectStatus.SUSPENDED, project.getStatus());

		project.setStatus(ProjectStatus.PLANNED);
		assertEquals(ProjectStatus.PLANNED, project.getStatus());

		project.setStatus(ProjectStatus.ACTIVE);
		assertEquals(ProjectStatus.ACTIVE, project.getStatus());

		project.setStatus(ProjectStatus.FINISHED);
		assertEquals(ProjectStatus.FINISHED, project.getStatus());
    }

	@Test(expected = IllegalArgumentException.class)
	public void testNullSetStatus(){
		Set<Qualification> qualifications = new HashSet<>();
		qualifications.add(new Qualification("Testing 1"));
		Project project = new Project("Testing Project", qualifications, ProjectSize.SMALL);
        assertNotNull(project);

		project.setStatus(null);
	}

	

	// Tests for addWorker() and removeWorker()
	@Test
	public void testAddRemoveWorker(){
		Set<Qualification> qualifications = new HashSet<>();
		Project project = new Project("Empty Project", qualifications, ProjectSize.MEDIUM);		

		Qualification qual1 = new Qualification("Has Red Hair");
        qualifications.add(qual1);
        Worker axel1 = new Worker("A", qualifications, 1);
		project.addWorker(axel1);
		Worker axel2 = new Worker("A1", qualifications, 1);
		project.addWorker(axel2);
		Worker axel3 = new Worker("A2", qualifications, 1);
		project.addWorker(axel3);

		// Size shouldn't change
		Worker nobody = new Worker("I don't exist!", qualifications, 1);
		assertEquals(project.getWorkers().size(), 3);
		project.removeWorker(nobody);
		assertEquals(project.getWorkers().size(), 3);

		// Size should change
		project.removeWorker(axel3);
		assertEquals(project.getWorkers().size(), 2);
		project.removeWorker(axel2);
		assertEquals(project.getWorkers().size(), 1);
		project.removeWorker(axel1);
		assertEquals(project.getWorkers().size(), 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddWorkerNull(){
		Set<Qualification> qualifications = new HashSet<>();
		Project project = new Project("Empty Project", qualifications, ProjectSize.MEDIUM);
		// Exception here
        project.addWorker(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRemoveWorkerNull(){
		Set<Qualification> qualifications = new HashSet<>();
		Project project = new Project("Empty Project", qualifications, ProjectSize.MEDIUM);
		// Exception here
        project.removeWorker(null);
	}



	// Tests for getWorkers()
    @Test
    public void testGetWorkersNonEmpty() {
        Set<Qualification> qualifications = new HashSet<>();
        qualifications.add(new Qualification("Singing"));
        Project project = new Project("Wicked: The Untold Story", qualifications, ProjectSize.SMALL);

        Worker worker1 = new Worker("Elphaba Thropp", qualifications, 120000);
        Worker worker2 = new Worker("Glinda Upland", qualifications, 110000);
        
        project.addWorker(worker1);
        project.addWorker(worker2);

        Set<Worker> workers = project.getWorkers();
        assertEquals(2, workers.size());
        assertTrue(workers.contains(worker1));
        assertTrue(workers.contains(worker2));
    }

    @Test
    public void testGetWorkersEmptySet() {
        Set<Qualification> qualifications = new HashSet<>();
        Project project = new Project("Phantom's Return", qualifications, ProjectSize.BIG);

        assertTrue(project.getWorkers().isEmpty());
    }



	// Tests for removeAllWorkers()
	@Test
	public void testRemoveAllWorkersWithTwoWorkers() {
		Set<Qualification> qualifications = new HashSet<>();
		qualifications.add(new Qualification("Purring"));
		Project project = new Project("CatNap Research", qualifications, ProjectSize.SMALL);

		Worker worker1 = new Worker("Whiskers", qualifications, 50000);
		Worker worker2 = new Worker("Paws", qualifications, 52000);

		project.addWorker(worker1);
		project.addWorker(worker2);

		project.removeAllWorkers();

		assertTrue("Workers should be removed", project.getWorkers().isEmpty());
	}

	@Test
	public void testRemoveAllWorkersWithOneWorker() {
		Set<Qualification> qualifications = new HashSet<>();
		qualifications.add(new Qualification("Scratching"));
		Project project = new Project("Feline Defense Training", qualifications, ProjectSize.MEDIUM);

		Worker worker = new Worker("Clawdia", qualifications, 55000);
		project.addWorker(worker);

		project.removeAllWorkers();

		assertTrue("Workers should be removed", project.getWorkers().isEmpty());
	}

	@Test
	public void testRemoveAllWorkersWithEmptySet() {
		Set<Qualification> qualifications = new HashSet<>();
		qualifications.add(new Qualification("Meowing"));
		Project project = new Project("Opera Cats", qualifications, ProjectSize.BIG);

		project.removeAllWorkers();

		assertTrue("Workers should still be empty", project.getWorkers().isEmpty());
	}

	@Test
	public void testRemoveAllWorkersWithNullWorkers() {
		Set<Qualification> qualifications = new HashSet<>();
		qualifications.add(new Qualification("Hunting"));
		Project project = new Project("Mouse Patrol", qualifications, ProjectSize.SMALL);

		// Assuming workers field could be null (even though it isn't in the current implementation)
		// This test ensures safety in such a case.
		project.removeAllWorkers();

		assertTrue("Workers should still be empty", project.getWorkers().isEmpty());
	}



	// Tests for getRequiredQualifications()
	@Test
	public void testGetRequiredQualificationsAdded(){
		Set<Qualification> qualifications = new HashSet<>();
		qualifications.add(new Qualification("Testing Qualifications"));
		qualifications.add(new Qualification("testing Qualifications 2"));
		qualifications.add(new Qualification("Testing Qualifications 3"));
		Project project = new Project("Test project", qualifications, ProjectSize.MEDIUM);

		assertEquals(qualifications, project.getRequiredQualifications());
	}

	@Test
	public void testGetRequiredQualificationsEmpty(){
		Set<Qualification> qualifications = new HashSet<>();
		Project project = new Project("Empty Project", qualifications, ProjectSize.MEDIUM);

		assertTrue(project.getRequiredQualifications().isEmpty());
	}



	// Tests for addQualification()
	@Test
	public void testAddQualification(){
		Set<Qualification> qualifications = new HashSet<>();
		qualifications.add(new Qualification("Test Qualification"));
		Project project = new Project("Test Project", qualifications, ProjectSize.MEDIUM);

		Qualification newQualification = new Qualification("Qualification");
		project.addQualification(newQualification);
		assertTrue(project.getRequiredQualifications().contains(newQualification));

		Qualification newQualification1 = new Qualification("Qualification 1");
		project.addQualification(newQualification1);
		assertTrue(project.getRequiredQualifications().contains(newQualification1));

		Qualification newQualification2 = new Qualification("Qualification 2");
		project.addQualification(newQualification2);
		assertTrue(project.getRequiredQualifications().contains(newQualification2));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddNullQualification(){
		Set<Qualification> qualifications = new HashSet<>();
		qualifications.add(new Qualification("Test Qualification"));
		Project project = new Project("Test Project", qualifications, ProjectSize.SMALL);

		project.addQualification(null);
	}

	@Test
	public void testAddDuplicateQual(){
		Set<Qualification> qualifications = new HashSet<>();
		Qualification testQual = new Qualification("Test");
		qualifications.add(testQual);
		
		Project project = new Project("Test Project", qualifications, ProjectSize.SMALL);

		project.addQualification(testQual);

		assertEquals(1, project.getRequiredQualifications().size());
	}
	

    
	// Tests for getMissingQualifications()
	@Test
	public void testGetMissingQualAllIncluded(){
		Set<Qualification> qualifications = new HashSet<>();
		Qualification testQual = new Qualification("Test");
		Qualification testQual2 = new Qualification("Test2");
		qualifications.add(testQual);
		qualifications.add(testQual2);

		Project project = new Project("Test Project", qualifications, ProjectSize.MEDIUM);

		Set<Qualification> workerQualifications = new HashSet<>();
		workerQualifications.add(testQual);
		workerQualifications.add(testQual2);
		Worker worker = new Worker("SirKittyCat", workerQualifications, 10.0);


		project.addWorker(worker);
		assertTrue(project.getMissingQualifications().isEmpty());
	}

	@Test
	public void testGetMissingQualMissingSome(){
		Set<Qualification> qualifications = new HashSet<>();
		Qualification testQual3 = new Qualification("Test3");
		Qualification testQual4 = new Qualification("Test4");
		qualifications.add(testQual3);
		qualifications.add(testQual4);

		Project project = new Project("Test Project 2", qualifications, ProjectSize.SMALL);

		Set<Qualification> workerQualifications = new HashSet<>();
		workerQualifications.add(testQual3);
		Worker worker = new Worker("MisterKitty", workerQualifications, 9.0);

		project.addWorker(worker);

		Set<Qualification> missingQual = project.getMissingQualifications();
		assertEquals(1, missingQual.size());
		assertTrue(missingQual.contains(testQual4));
	}

	@Test
	public void testGetMissingQualNone(){
		Set<Qualification> qualifications = new HashSet<>();
		Qualification testQual5 = new Qualification("Test5");
		Qualification testQual6 = new Qualification("Test6");
		qualifications.add(testQual5);
		qualifications.add(testQual6);

		Project project = new Project("Test Project 3", qualifications, ProjectSize.MEDIUM);

		Set<Qualification> missingQual = project.getMissingQualifications();

		assertEquals(2, missingQual.size());
		assertTrue(missingQual.contains(testQual5));
		assertTrue(missingQual.contains(testQual6));
	}



	// Tests for isHelpful()
	@Test(expected = IllegalArgumentException.class)
	public void testIsHelpfulNull(){
		Set<Qualification> qualifications = new HashSet<>();
		qualifications.add(new Qualification("Testing 1"));
		Project project = new Project("Test Project", qualifications, ProjectSize.MEDIUM);

		project.isHelpful(null);
	}

	@Test
	public void testIsHelpfulWorkerHelping(){
		Set<Qualification> qualifications = new HashSet<>();
		qualifications.add(new Qualification("Testing 2"));
		qualifications.add(new Qualification("Testing 3"));

		Project project = new Project("Test Project 2", qualifications, ProjectSize.SMALL);

		Set<Qualification> workerQualifications = new HashSet<>();
		workerQualifications.add(new Qualification("Testing 2"));

		Worker worker = new Worker("Onyx", workerQualifications, 10.0);

		assertTrue(project.isHelpful(worker));
	}

	@Test
	public void testIsHelpfulWorkerNotHelping(){
		Set<Qualification> qualifications = new HashSet<>();
		qualifications.add(new Qualification("Testing 3"));
		qualifications.add(new Qualification("Testing 4"));

		Project project = new Project("Test Project 3", qualifications, ProjectSize.BIG);

		Set<Qualification> workerQualifications = new HashSet<>();
		workerQualifications.add(new Qualification("Not Helpful"));

		Worker worker = new Worker("Opal", workerQualifications, 80.0);
		assertFalse(project.isHelpful(worker));
	}

	@Test
	public void testToDTONoWorkers(){
		Set<Qualification> qualifications = new HashSet<>();
		qualifications.add(new Qualification("Testing 1"));
		Project project = new Project("Test 1", qualifications, ProjectSize.SMALL);

		ProjectDTO dto = project.toDTO();

		assertEquals("Test 1", dto.getName());
		assertNotNull(dto.getWorkers());
		assertEquals(0, dto.getWorkers().length);
	}

	@Test
	public void testToDTO1Worker(){
		Set<Qualification> qualifications = new HashSet<>();
		qualifications.add(new Qualification("Testing 2"));
		Project project = new Project("Test 2", qualifications, ProjectSize.MEDIUM);

		Worker worker = new Worker("MisterDog", qualifications, 30.0);
		project.addWorker(worker);

		ProjectDTO dto = project.toDTO();

		assertEquals("Test 2", dto.getName());
		assertArrayEquals(new String[]{"MisterDog"}, dto.getWorkers());
	}

	@Test
	public void testToDTO2Workers(){
		Set<Qualification> qualifications = new HashSet<>();
		Qualification qual = new Qualification("Testing 3");
		qualifications.add(qual);
		Project project = new Project("Test 3", qualifications, ProjectSize.SMALL);

		Worker worker1 = new Worker("SirDog", qualifications, 20.0);
		int worker1Hash = worker1.hashCode();
		Worker worker2 = new Worker("DoggieDog", qualifications, 40.0);
		int worker2Hash = worker2.hashCode();

		project.addWorker(worker1);
		project.addWorker(worker2);

		ProjectDTO dto = project.toDTO();

		assertEquals("Test 3", dto.getName());
		assertArrayEquals(new String[]{"SirDog", "DoggieDog"}, dto.getWorkers());
		assertArrayEquals(new String[]{String.valueOf(qual.toString())}, dto.getQualifications());
	}

	@Test
	public void testToDTO2WorkersMultipleQualifications(){
		Set<Qualification> qualifications = new HashSet<>();
		Qualification qual1 = new Qualification("Testing 1");
		Qualification qual2 = new Qualification("Testing 2");
		Qualification qual3 = new Qualification("Testing 3");
		Qualification qual4 = new Qualification("Testing 4");
		qualifications.add(qual1);
		qualifications.add(qual2);
		qualifications.add(qual3);
		qualifications.add(qual4);
		Project project = new Project("Test 3", qualifications, ProjectSize.SMALL);

		Set<Qualification> qualificationsWorker1 = new HashSet<>();
		qualificationsWorker1.add(qual1);
		Worker worker1 = new Worker("SirDog", qualificationsWorker1, 20.0);
		int worker1Hash = worker1.hashCode();

		Set<Qualification> qualificationsWorker2 = new HashSet<>();
		qualificationsWorker2.add(qual2);
		Worker worker2 = new Worker("DoggieDog", qualificationsWorker2, 40.0);
		int worker2Hash = worker2.hashCode();

		project.addWorker(worker1);
		project.addWorker(worker2);

		ProjectDTO dto = project.toDTO();

		assertEquals("Test 3", dto.getName());
		assertArrayEquals(new String[]{"SirDog", "DoggieDog"}, dto.getWorkers());
		assertEquals(String.valueOf("Testing 3".hashCode()), String.valueOf(dto.getMissingQualifications()[1].hashCode()));
		assertEquals(String.valueOf("Testing 4".hashCode()), String.valueOf(dto.getMissingQualifications()[0].hashCode()));
	}
}