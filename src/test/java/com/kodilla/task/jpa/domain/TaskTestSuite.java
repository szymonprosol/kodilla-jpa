package com.kodilla.task.jpa.domain;

import com.kodilla.jpa.domain.Invoice;
import com.kodilla.jpa.domain.Item;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
public class TaskTestSuite {

    @PersistenceUnit
    private EntityManagerFactory emf;


    @Test
    void shouldNPlusOneProblemOccure() {
        //Given
        List<Long> savedTasks = insertExampleData();
        EntityManager em = emf.createEntityManager();

        //When
        System.out.println("****************** BEGIN OF FETCHING *******************");
        System.out.println("*** STEP 1 – query for tasks ***");

        TypedQuery<Task> query = em.createQuery(
                "from Task "
                        + " where id in (" + taskIds(savedTasks) + ")",
                Task.class);

        EntityGraph<Task> eg = em.createEntityGraph(Task.class);
        eg.addSubgraph("subtasks").addSubgraph("persons");
        eg.addSubgraph("persons").addAttributeNodes("subtask", "task");
        query.setHint("javax.persistence.fetchgraph", eg);

        List<Task> tasks = query.getResultList();

        for (Task task : tasks) {
            System.out.println("*** STEP 2 – read data from task ***");
            System.out.println(task);

            for (Person person : task.getPersons()) {
                System.out.println("*** STEP 4 – read the person ***");
                System.out.println(person);
            }

            for (Subtask subtask : task.getSubtasks()) {
                System.out.println("*** STEP 4 – read the subtask ***");
                System.out.println(subtask);
            }

        }

        System.out.println("****************** END OF FETCHING *******************");

        //Then
        //Here should be some assertions and the clean up performed

    }

    private String taskIds(List<Long> TaskIds) {
        return TaskIds.stream()
                .map(n -> "" + n)
                .collect(Collectors.joining(","));
    }

    private List<Long> insertExampleData() {
        Task task1 = new Task(null, "Task1", "Status1");
        Task task2 = new Task(null, "Task2", "Status2");

        Subtask subtask11 = new Subtask(null, "Subtask11", "Status11", task1);
        Subtask subtask12 = new Subtask(null, "Subtask12", "Status12", task1);
        Subtask subtask13 = new Subtask(null, "Subtask13", "Status13", task1);
        Subtask subtask21 = new Subtask(null, "Subtask21", "Status21", task2);
        Subtask subtask22 = new Subtask(null, "Subtask22", "Status22", task2);
        Subtask subtask23 = new Subtask(null, "Subtask23", "Status23", task2);

        Person person11 = new Person(null, "Firstname11", "Lastname11", task1, subtask11);
        Person person12 = new Person(null, "Firstname12", "Lastname12", task1, subtask12);
        Person person13 = new Person(null, "Firstname13", "Lastname13", task1, subtask13);

        Person person21 = new Person(null, "Firstname21", "Lastname21", task2, subtask21);
        Person person22 = new Person(null, "Firstname22", "Lastname22", task2, subtask22);
        Person person23 = new Person(null, "Firstname23", "Lastname23", task2, subtask23);

        subtask11.getPersons().addAll(List.of(person11));
        subtask12.getPersons().addAll(List.of(person12));
        subtask13.getPersons().addAll(List.of(person13));

        subtask21.getPersons().addAll(List.of(person21));
        subtask22.getPersons().addAll(List.of(person22));
        subtask23.getPersons().addAll(List.of(person23));

        task1.getPersons().addAll(List.of(person11, person12, person13));
        task2.getPersons().addAll(List.of(person21, person22, person23));

        task1.getSubtasks().addAll(List.of(subtask11, subtask12, subtask13));
        task2.getSubtasks().addAll(List.of(subtask21, subtask22, subtask23));

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(task1);
        em.persist(task2);
        em.persist(subtask11);
        em.persist(subtask12);
        em.persist(subtask13);
        em.persist(subtask21);
        em.persist(subtask22);
        em.persist(subtask23);
        em.persist(person11);
        em.persist(person12);
        em.persist(person13);
        em.persist(person21);
        em.persist(person22);
        em.persist(person23);
        em.flush();
        em.getTransaction().commit();
        em.close();

        return List.of(task1.getId(), task2.getId());
    }
}
