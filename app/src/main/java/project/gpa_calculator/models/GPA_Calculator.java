package project.gpa_calculator.models;

import java.util.HashMap;
import java.util.Map;

public class GPA_Calculator {
    private static Map<String, Double> letter_gpa_map = new HashMap<String, Double>() {
        {
            put("A+", 4.0d);
            put("A", 4.0d);
            put("A-", 3.7d);
            put("B+", 3.3d);
            put("B", 3.0d);
            put("B-", 2.7d);
            put("C+", 2.3d);
            put("C", 2.0d);
            put("C-", 1.7d);
            put("D+", 1.3d);
            put("D", 1.0d);
            put("D-", 1.0d);
            put("F", 0.0d);
        }
    };

    /**
     * -1 should be returned if any event (event, course, semester year) in the gpa_object is not
     * done
     * @param gpa_object
     * @return gpa of the given object
     */
    public static double calculate_gpa(Object gpa_object) {
        if (gpa_object instanceof Course) {
            return calculate_course_gpa((Course) gpa_object);
        } else if (gpa_object instanceof Semester) {
            return calculate_semester_gpa((Semester) gpa_object);
        } else if (gpa_object instanceof Year) {
            return calculate_year_gpa((Year) gpa_object);
        } else {
            return -1d;
        }
    }

    private static double calculate_year_gpa(Year year) {
        return 0;
    }

    private static double calculate_semester_gpa(Semester semester) {
        return 0;
    }

    private static double calculate_course_gpa(Course course) {
        double gpa_sum = 0;
        double weight_sum = 0;
        for (Event event: course) {
            if (event.isDone()) {
                double percentage = event.getEvent_score();
                double gpa = calculate_gpa(calculate_letter_grade(percentage));
                double weight = event.getEvent_weight();
                gpa_sum += gpa * weight;
                weight_sum += weight;
            } else {
                return -1d;
            }
        }
        return 0;
    }

    public static String calculate_letter_grade(double percentage) {
        if (90 <= percentage && percentage <= 100) {
            return "A+";
        } else if (85 <= percentage && percentage <= 89) {
            return "A";
        } else if (80 <= percentage && percentage <= 84) {
            return "A-";
        } else if (77 <= percentage && percentage <= 79) {
            return "B+";
        } else if (73 <= percentage && percentage <= 76) {
            return "B";
        } else if (70 <= percentage && percentage <= 72) {
            return "B-";
        } else if (67 <= percentage && percentage <= 69) {
            return "C+";
        } else if (63 <= percentage && percentage <= 66) {
            return "C";
        } else if (60 <= percentage && percentage <= 62) {
            return "C-";
        } else if (57 <= percentage && percentage <= 59) {
            return "D+";
        } else if (53 <= percentage && percentage <= 56) {
            return "D";
        } else if (50 <= percentage && percentage <= 52) {
            return "D-";
        } else {
            return "F";
        }
    }

    public static double calculate_gpa(String letter_grade) {
        Double gpa = letter_gpa_map.get(letter_grade);
        return gpa != null ? gpa : -1d;
    }
}