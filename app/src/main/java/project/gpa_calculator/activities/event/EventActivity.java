package project.gpa_calculator.activities.event;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import project.gpa_calculator.Adapter.RecyclerViewAdapter;
import project.gpa_calculator.R;
import project.gpa_calculator.Util.AddDialog;
import project.gpa_calculator.Util.SwipeToDeleteCallback;
import project.gpa_calculator.activities.course.CourseActivityController;
import project.gpa_calculator.activities.main.MainActivity;
import project.gpa_calculator.models.Semester;
import project.gpa_calculator.models.Year;

public class EventActivity extends AppCompatActivity implements AddDialog.EventDialogListener {
    private EventActivityController controller;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        setupToolBar();
        setupController();
        setupAddButton();
        setupRecyclerView();
    }

    private void setupController() {
        controller = new EventActivityController(this);
//        controller.setContext(this);
        controller.loadFromFile(MainActivity.userFile);
        getIntent().getSerializableExtra("year_object");
        getIntent().getSerializableExtra("semester_object");

        controller.setupCurrentCourse((Year) getIntent().getSerializableExtra("year_object"),
                (Semester) getIntent().getSerializableExtra("semester_object"),
                getIntent().getStringExtra("course_code"));
    }

    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        controller.setupListItems();

        adapter = new RecyclerViewAdapter(this, controller.getListItems(), controller);
        recyclerView.setAdapter(adapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback((RecyclerViewAdapter) adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void setupToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setupAddButton() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                openDialog();
            }

            private void openDialog() {
                AddDialog dialog = new AddDialog();
                dialog.show(getSupportFragmentManager(), "Add Event Dialog");
            }
        });
    }

    @Override
    public void applyDialog(String name, double weight) {
        if (controller.addEvent(name, weight)) {
            adapter.notifyItemInserted(controller.getListItems().size() - 1);
        }
    }
}
