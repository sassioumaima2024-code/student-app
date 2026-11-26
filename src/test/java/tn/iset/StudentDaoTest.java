package tn.iset;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class StudentDaoTest {

    @Test
    void tableVideAuDebut() throws Exception {
        StudentDao dao = new StudentDao("jdbc:h2:mem:test1");
        assertTrue(dao.findAll().isEmpty());
    }

    @Test
    void ajoutEtLecture() throws Exception {
        StudentDao dao = new StudentDao("jdbc:h2:mem:test2");
        dao.add("Ali");
        dao.add("Sara");
        assertEquals(List.of("Ali", "Sara"), dao.findAll());
    }
}
