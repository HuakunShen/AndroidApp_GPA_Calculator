package project.gpa_calculator.activities.year;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import project.gpa_calculator.R;
import project.gpa_calculator.Util.AddDialog;

public class YearActivity extends AppCompatActivity implements AddDialog.YearSemesterDialogListener {

    private YearActivityController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_year);
        setupController();
        controller.setupRecyclerView();
        setupToolBar();
        setupAddButton();
        controller.setupRecyclerViewContent();
    }

    private void setupController() {
        controller = new YearActivityController(this);
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

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void applyDialog(String name, String description) {
        controller.addYear(name, description);
    }

}
