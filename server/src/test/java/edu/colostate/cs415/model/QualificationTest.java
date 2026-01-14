package edu.colostate.cs415.model;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Set;
import java.util.HashSet;

import edu.colostate.cs415.dto.QualificationDTO;

public class QualificationTest {

    @Test
    public void testValidQualification() {
        Qualification q = new Qualification("Java Development");
        assertNotNull(q);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullDescription() {
        new Qualification(null); // Should throw an exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyDescription() {
        new Qualification(""); // Should throw an exception
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWhitespaceDescription() {
        new Qualification("   "); // Should throw an exception
    }

    // Tests for equals method
    @Test
    public void testEqualsSameObject() {
        Qualification q = new Qualification("Java Development");
        assertTrue(q.equals(q)); // Should be true since it's the same object
    }

    @Test
    public void testEqualsDifferentObjectSameDescription() {
        Qualification q1 = new Qualification("Java Development");
        Qualification q2 = new Qualification("Java Development");
        assertTrue(q1.equals(q2)); // Should be true since descriptions match
    }

    @Test
    public void testEqualsDifferentObjectDifferentDescription() {
        Qualification q1 = new Qualification("Java Development");
        Qualification q2 = new Qualification("Python Development");
        assertFalse(q1.equals(q2)); // Should be false since descriptions differ
    }

    @Test
    public void testEqualsNullObject() {
        Qualification q = new Qualification("Java Development");
        assertFalse(q.equals(null)); // Should be false since null is not equal to an object
    }

    @Test
    public void testEqualsDifferentTypeObject() {
        Qualification q = new Qualification("Java Development");
        String differentType = "Java Development";
        assertFalse(q.equals(differentType)); // Should be false since it's a different type
    }

    //tests for hashCode method
    @Test
    public void testHashCodeSameDescription() {
        Qualification q1 = new Qualification("Java Development");
        Qualification q2 = new Qualification("Java Development");
        assertEquals(q1.hashCode(), q2.hashCode()); // Should be equal since descriptions match
    }
    @Test
    public void testHashCodeDifferentDescription() {
        Qualification q1 = new Qualification("Java Development");
        Qualification q2 = new Qualification("Python Development");
        assertNotEquals(q1.hashCode(), q2.hashCode()); // Should be different since descriptions differ
    }
        
    @Test
    public void testHashCodeConsistency() {
        Qualification q = new Qualification("Java Development");
        int hashCode1 = q.hashCode();
        int hashCode2 = q.hashCode();
        assertEquals(hashCode1, hashCode2); // Calling hashCode multiple times should return the same value
    }

    //tests for toString method
    @Test
    public void testToString() {
        Qualification q = new Qualification("Java Development");
        assertEquals("Java Development", q.toString()); // Should return the description
    }

    @Test
    public void testToStringWithDifferentDescription() {
        Qualification q = new Qualification("Python Development");
        assertEquals("Python Development", q.toString()); // Should match the description
    }

    //tests for worker methods
    @Test
    public void testGetWorkersInitiallyEmpty() {
        Qualification q = new Qualification("Python Mastery");
        Set<Worker> workers = q.getWorkers();
        assertTrue(workers.isEmpty());
    }

    @Test
    public void testAddWorker() {
        Qualification q = new Qualification("Debugging Wizardry");
        Worker sillyWorker = new Worker("Sir Fix-a-lot", new HashSet<>(), 50000);
        q.addWorker(sillyWorker);
        assertTrue(q.getWorkers().contains(sillyWorker));
    }

    @Test
    public void testAddMultipleWorkers() {
        Qualification q = new Qualification("Bug Squashing");
        Worker w1 = new Worker("Captain Crunch", new HashSet<>(), 60000);
        Worker w2 = new Worker("Debugzilla", new HashSet<>(), 70000);
        q.addWorker(w1);
        q.addWorker(w2);
        Set<Worker> workers = q.getWorkers();
        assertTrue(workers.contains(w1));
        assertTrue(workers.contains(w2));
        assertEquals(2, workers.size());
    }

    @Test
    public void testAddDuplicateWorker() {
        Qualification q = new Qualification("Refactoring Guru");
        Worker duplicateWorker = new Worker("Copy Cat", new HashSet<>(), 80000);
        q.addWorker(duplicateWorker);
        q.addWorker(duplicateWorker); // Adding same worker again
        Set<Worker> workers = q.getWorkers();
        assertTrue(workers.contains(duplicateWorker));
        assertEquals(1, workers.size()); // Should not have duplicates
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNullWorker() {
        Qualification q = new Qualification("Null Buster");
        q.addWorker(null);
    }

    @Test
    public void testRemoveWorker() {
        Qualification q = new Qualification("Logic Enchanter");
        Worker w = new Worker("The Code Whisperer", new HashSet<>(), 80000);
        q.addWorker(w);
        q.removeWorker(w);
        assertFalse(q.getWorkers().contains(w));
    }

    @Test
    public void testRemoveNonExistentWorker() {
        Qualification q = new Qualification("Syntax Sorcerer");
        Worker w = new Worker("Semicolon Slayer", new HashSet<>(), 75000);
        q.removeWorker(w); // Should not throw any exception
        assertFalse(q.getWorkers().contains(w));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveNullWorker() {
        Qualification q = new Qualification("Logic Enchanter");
        q.removeWorker(null);
    }

    //Qualification DTO method tests
     @Test
    public void testToDTOWithNoWorkers() {
        Qualification q = new Qualification("Master of Naps");
        QualificationDTO dto = q.toDTO();
        
        assertEquals("Master of Naps", dto.getDescription());
        assertEquals(0, dto.getWorkers().length);
    }

    @Test
    public void testToDTOWithOneWorker() {
        Qualification q = new Qualification("Duck Whisperer");
        Worker w = new Worker("Quack McFlapface", new HashSet<>(), 42000);
        q.addWorker(w);
        
        QualificationDTO dto = q.toDTO();
        assertEquals("Duck Whisperer", dto.getDescription());
        assertArrayEquals(new String[] { "Quack McFlapface" }, dto.getWorkers());
    }

    @Test
    public void testToDTOWithMultipleWorkers() {
        Qualification q = new Qualification("Certified Meme Lord");
        Worker w1 = new Worker("Sir Laughs-a-Lot", new HashSet<>(), 50000);
        Worker w2 = new Worker("Duke of Dank", new HashSet<>(), 55000);
        Worker w3 = new Worker("Captain Chuckles", new HashSet<>(), 60000);
        
        q.addWorker(w1);
        q.addWorker(w2);
        q.addWorker(w3);
        
        QualificationDTO dto = q.toDTO();
        Set<String> workerNames = new HashSet<>();
        for (String name : dto.getWorkers()) {
            workerNames.add(name);
        }
        
        Set<String> expectedNames = new HashSet<>();
        expectedNames.add("Sir Laughs-a-Lot");
        expectedNames.add("Duke of Dank");
        expectedNames.add("Captain Chuckles");

        assertEquals(expectedNames, workerNames);
    }
}