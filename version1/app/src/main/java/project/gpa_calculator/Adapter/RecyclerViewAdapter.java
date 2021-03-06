package project.gpa_calculator.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import project.gpa_calculator.R;
import project.gpa_calculator.Util.ActivityController;
import project.gpa_calculator.activities.GPA_setter.GPA_Setter_Controller;
import project.gpa_calculator.activities.course.CourseActivity;
import project.gpa_calculator.activities.course.CourseActivityController;
import project.gpa_calculator.activities.event.EventActivity;
import project.gpa_calculator.activities.semester.SemesterActivity;
import project.gpa_calculator.activities.semester.SemesterActivityController;
import project.gpa_calculator.activities.year.YearActivity;
import project.gpa_calculator.activities.year.YearActivityController;
import project.gpa_calculator.models.Course;
import project.gpa_calculator.models.GPAListItem;
import project.gpa_calculator.models.ListItem;
import project.gpa_calculator.models.Semester;
import project.gpa_calculator.models.Year;
import project.gpa_calculator.models.YearListItem;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private Context context;
    private List<ListItem> list_items;
    private ActivityController controller;
//    private AbstractSequentialList mListItems;


    public RecyclerViewAdapter(Context context, List<ListItem> list_items, ActivityController controller) {
        this.context = context;
        this.list_items = list_items;
        this.controller = controller;
    }


    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (controller instanceof GPA_Setter_Controller) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.gpa_row, parent, false);
            return new GPAViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_row, parent, false);
            return new YearViewHolder(view);
        }


    }

    /**
     * connect item to view
     *
     * @param viewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder viewHolder, int position) {
        ListItem item = list_items.get(position);
        if (controller instanceof GPA_Setter_Controller) {
            GPAListItem cur_item = (GPAListItem) item;
            GPAViewHolder cur_view = (GPAViewHolder) viewHolder;
            cur_view.low.setHint(Integer.toString(cur_item.getLow()));
            cur_view.high.setHint(Integer.toString(cur_item.getHigh()));
            cur_view.gpa_point.setHint(Double.toString(cur_item.getGPA()));
            cur_view.gpa_grade.setHint(cur_item.getGPA_mark());
        } else {
            YearViewHolder cur_view = (YearViewHolder) viewHolder;
            cur_view.name.setText(item.getName());
            cur_view.description.setText(item.getDescription());
            cur_view.gpa.setText(item.getGpa());
        }

    }

    @Override
    public int getItemCount() {
        return list_items.size();
    }

    public void deleteItem(final int position) {
        new AlertDialog.Builder(context)
                .setTitle("Deletion Warning!")
                .setMessage("Do You Want To Delete?\nIt Is Unrecoverable!")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "Deletion Cancelled", Toast.LENGTH_SHORT).show();
                    }
                })
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        list_items.remove(position);
                        controller.deleteItem(position);
                        notifyItemRemoved(position);
                    }
                }).show();
    }


    public Context getContext() {
        return context;
    }

    public abstract class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class YearViewHolder extends ViewHolder implements View.OnClickListener {
        private TextView name;
        private TextView description;
        private TextView gpa;

        private YearViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            name = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description1);
            gpa = itemView.findViewById(R.id.description2);

        }

        /**
         * click to go to next page
         *
         * @param v
         */
        @Override
        public void onClick(View v) {
            // Get Position of row clicked
            int position = getAdapterPosition();
            ListItem item = list_items.get(position);
            if (context instanceof YearActivity) {
                Intent intent = new Intent(context, SemesterActivity.class);
                Year year = (Year) ((YearListItem) item).getObj();
//                intent.putExtra("year_docID", year.getDocID());
                intent.putExtra("year_doc_path", ((YearActivityController) controller).getYearPath() + year.getDocID());
                context.startActivity(intent);
            } else if (context instanceof SemesterActivity) {
                Intent intent = new Intent(context, CourseActivity.class);
                Semester semester = (Semester) ((YearListItem) item).getObj();
                intent.putExtra("semester_doc_path", ((SemesterActivityController) controller).getSemesterPath() + semester.getDocID());
//                intent.putExtra("year_object", ((SemesterActivityController) controller).getCurrent_year());
                context.startActivity(intent);
            } else if (context instanceof CourseActivity) {
                Intent intent = new Intent(context, EventActivity.class);
                Course course = (Course) ((YearListItem) item).getObj();
                intent.putExtra("course_doc_path", ((CourseActivityController) controller).getCoursePath() + course.getDocID());
                context.startActivity(intent);
            }
            Toast.makeText(context, item.getName(), Toast.LENGTH_LONG).show();
        }
    }

    public class GPAViewHolder extends ViewHolder implements View.OnClickListener {
        private TextView low, high, gpa_grade, gpa_point;

        private GPAViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            low = itemView.findViewById(R.id.low);
            high = itemView.findViewById(R.id.high);
            gpa_grade = itemView.findViewById(R.id.GPA_grade);
            gpa_point = itemView.findViewById(R.id.GPA_point);

            gpa_point.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            low.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            high.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        }

        @Override
        public void onClick(View v) {

        }

        public Integer getLow() {
            return low.getText().toString().equals("") ? Integer.valueOf(low.getHint().toString()) : Integer.valueOf(low.getText().toString());
        }

        public Integer getHigh() {
            return high.getText().toString().equals("") ? Integer.valueOf(high.getHint().toString()) : Integer.valueOf(high.getText().toString());
        }

        public String getGpa_grade() {
            return gpa_grade.getText().toString().equals("") ? gpa_grade.getHint().toString() : gpa_grade.getText().toString();
        }

        public Double getGpa_point() {
            return gpa_point.getText().toString().equals("") ? Double.valueOf(gpa_point.getHint().toString()) : Double.valueOf(gpa_point.getText().toString());
        }
    }


}

