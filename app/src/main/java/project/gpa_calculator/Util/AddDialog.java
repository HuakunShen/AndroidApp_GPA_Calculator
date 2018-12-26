package project.gpa_calculator.Util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import project.gpa_calculator.R;
import project.gpa_calculator.activities.course.CourseActivity;
import project.gpa_calculator.activities.semester.SemesterActivity;
import project.gpa_calculator.activities.year.YearActivity;

public class AddDialog extends AppCompatDialogFragment {
    private EditText name_ET;
    private EditText description_ET;

    private EditText target_ET;
    private EditText credit_weight_ET;
    private String type;
    private DialogListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof YearActivity) {
            type = "Year";
        } else if (context instanceof SemesterActivity) {
            type = "Semester";
        } else if (context instanceof CourseActivity) {
            type = "Course";
        } else {
            type = "Event";
        }


            setupExtraInput(context);
            try {
                if (context instanceof YearActivity || context instanceof SemesterActivity) {
                    listener = (YearSemesterDialogListener) context;
                } else {
                    listener = (CourseDialogListener ) context;
                }
            } catch (ClassCastException e) {
                throw new ClassCastException(context.toString() + "must implement YearSemesterDialogListener");
            }

    }

    private void setupExtraInput(Context context) {
        target_ET = new EditText(context);
        credit_weight_ET = new EditText(context);
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
//        if (!this.type.equalsIgnoreCase("Course")) {

        View view = inflater.inflate(R.layout.dialog_year_and_semester, null);
        LinearLayout dialog_linearLayout = view.findViewById(R.id.dialog_id);

        name_ET = view.findViewById(R.id.dialog_name_ET);
        name_ET.setHint(this.type + " Name");
        description_ET = view.findViewById(R.id.dialog_description_ET);
        if (this.type.equalsIgnoreCase("Course")) {
            target_ET.setHint("Target Score (Percentage)");
            target_ET.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            dialog_linearLayout.addView(target_ET);
            credit_weight_ET.setHint("Credit Hint");
            credit_weight_ET.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            dialog_linearLayout.addView(credit_weight_ET);
            description_ET.setHint("Course Code");
        }
        builder.setView(view)
                .setTitle("Add " + type)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        if (type.equalsIgnoreCase("Year") || type.equalsIgnoreCase("Semester")) {
                            String semester_name = name_ET.getText().toString();
                            String semester_description = description_ET.getText().toString();
                            if (!semester_name.isEmpty())
                                ((YearSemesterDialogListener) listener).applyDialog(semester_name, semester_description);
//                        } else if (type.equalsIgnoreCase("Course")) {
                            String course_name = name_ET.getText().toString();
                            String course_code = description_ET.getText().toString();
//                        }
                    }
                });
//        } else if (this.type.equalsIgnoreCase("Course")){
//
//        }
        return builder.create();
    }


    public interface DialogListener {

    }

    public interface YearSemesterDialogListener extends DialogListener {
        void applyDialog(String name, String description);
    }

    public interface CourseDialogListener extends DialogListener {
        void applyDialog(String course_name, String course_code, double target, double credit_weight);
    }
}