package org.cmucreatelab.android.flutterprek.database;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.cmucreatelab.android.flutterprek.database.models.Classroom;
import org.cmucreatelab.android.flutterprek.database.models.ClassroomDAO;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import java.util.List;

import static junit.framework.Assert.assertEquals;

/**
 * Created by tasota on 9/6/2018.
 *
 * ClassroomDaoTest
 *
 * Unit tests for the classrooms table.
 */
@RunWith(AndroidJUnit4.class)
public class ClassroomDaoTest {

    private ClassroomDAO classroomDAO;
    private AppDatabase db;

    @Rule public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    @Rule public final ExpectedException expectedException = ExpectedException.none();


    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class)
                // Allowing main thread queries, just for testing.
                .allowMainThreadQueries()
                .build();
        classroomDAO = db.classroomDAO();
    }


    @After
    public void closeDb() {
        db.close();
    }


    @Test
    public void insertAndGetClassroom() throws InterruptedException {
        Classroom classroom = new Classroom(1, "Test Classroom");
        classroomDAO.insert(classroom);
        List<Classroom> allClassrooms = LiveDataTestUtil.getValue(classroomDAO.getAllClassrooms());
        assertEquals(allClassrooms.get(0).getId(), classroom.getId());
    }


    @Test
    public void getAllWords() throws InterruptedException {
        Classroom classroom1 = new Classroom(1, "Test Classroom");
        classroomDAO.insert(classroom1);
        Classroom classroom2 = new Classroom(2, "Another Classroom");
        classroomDAO.insert(classroom2);
        List<Classroom> allClassrooms = LiveDataTestUtil.getValue(classroomDAO.getAllClassrooms());
        assertEquals(allClassrooms.get(0).getId(), classroom1.getId());
        assertEquals(allClassrooms.get(1).getId(), classroom2.getId());
    }


    @Test
    public void primaryKey() throws Exception {
        // An exception that indicates that an integrity constraint was violated.
        expectedException.expect(SQLiteConstraintException.class);

        Classroom classroom1 = new Classroom(1, "Test Classroom");
        classroomDAO.insert(classroom1);
        Classroom classroom2 = new Classroom(1, "Another Classroom");
        classroomDAO.insert(classroom2);
    }

}
