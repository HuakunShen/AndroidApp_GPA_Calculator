package project.gpa_calculator.activities.main;

import android.content.Context;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import project.gpa_calculator.models.Course;
import project.gpa_calculator.models.Event;
import project.gpa_calculator.models.GPA_Calculator;
import project.gpa_calculator.models.Semester;
import project.gpa_calculator.models.User;
import project.gpa_calculator.models.Year;

import static android.content.Context.MODE_PRIVATE;

public class MainActivityController {

    private User user;

    private Context context;

    public MainActivityController() {
        user = new User("admin", "admin", "admin");
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void initializeUserForTesting() {
        Year year2018ForTesting = new Year("year2018ForTesting");
        Semester fall = new Semester("Fall");
        year2018ForTesting.addSemester(fall);
        Course course1 = new Course("CSC207", "Software Design", 85d, 0.5);
        fall.addCourse(course1);
        Event event = new Event("midterm", 50d);
        Event event1 = new Event("final", 50d);
        course1.addEvent(event);
        course1.addEvent(event1);
        Course course2 = new Course("CSC343", "Intro To Database", 80d, 0.5d);
        fall.addCourse(course2);
        Event event3 = new Event("final", 100d);
        course2.addEvent(event3);
        event3.setEvent_score(70);
        this.user.addYear(year2018ForTesting);

    }


    public void loadFromFile(String fileName) {
        try {
            InputStream inputStream = context.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                this.user = (User) input.readObject();
//                user = (User) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
        }
    }

    /**
     * Save the board manager to fileName.
     *
     * @param fileName the name of the file
     */
    public void saveToFile(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    context.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(this.user);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }



}
