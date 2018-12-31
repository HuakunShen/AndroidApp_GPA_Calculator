package project.gpa_calculator.activities.year;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.google.firebase.firestore.FirebaseFirestore;

import project.gpa_calculator.Adapter.RecyclerViewAdapter;
import project.gpa_calculator.R;
import project.gpa_calculator.Util.SwipeToDeleteCallback;
import project.gpa_calculator.Util.AddDialog;
import project.gpa_calculator.activities.main.MainActivity;
import project.gpa_calculator.models.User;

public class YearActivity extends AppCompatActivity implements AddDialog.YearSemesterDialogListener {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private YearActivityController controller;
//    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_year);
        setupController();
        setupToolBar();
        setupAddButton();
        setupRecyclerView();
    }

    private void setupController() {
        controller = new YearActivityController();
//        controller.setupCurrentYear((User) getIntent().getSerializableExtra("user_object"));
        controller.setContext(this);
//        controller.loadFromFile(MainActivity.userFile);
        controller.setupYearList();
    }

    private void setupToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setupAddButton() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_year_Btn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }

            private void openDialog() {
                AddDialog dialog = new AddDialog();
                dialog.show(getSupportFragmentManager(), "Add Year Dialog");
            }
        });
    }

    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        controller.setupListItems();

        adapter = new RecyclerViewAdapter(this, controller.getListItems(), controller);
        recyclerView.setAdapter(adapter);
//        adapter.notifyDataSetChanged();


//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback((RecyclerViewAdapter) adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        controller.loadFromFile(MainActivity.userFile);
    }




    @Override
    public void applyDialog(String name, String description) {
        if (controller.addYear(name, description)) {
            adapter.notifyItemInserted(controller.getListItems().size() - 1);
        }
    }

}
