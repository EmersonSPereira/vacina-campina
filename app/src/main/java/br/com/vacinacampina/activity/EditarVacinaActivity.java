package br.com.vacinacampina.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

import br.com.vacinacampina.R;
import br.com.vacinacampina.fragment.DatePickerFragment;
import br.com.vacinacampina.model.Cartao;
import br.com.vacinacampina.service.CartaoService;

public class EditarVacinaActivity extends AppCompatActivity {

    public static final String VACINA = "vacina";
    public static final String ID_PARENTE = "idParente";

    private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    private EditText editTextData1, editTextData2, editTextData3;
    private ProgressBar progressBar;
    private Button buttonSalvar;
    private Cartao vacina;
    private int dialogExibido;
    private Calendar calendarSetado;
    private String parenteId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_vacina);


        editTextData1 =  findViewById(R.id.editText_data_dose1);
        editTextData2 =  findViewById(R.id.editText_data_dose2);
        editTextData3 =  findViewById(R.id.editText_data_dose3);

        buttonSalvar = findViewById(R.id.button_editar_vacina_salvar);

        progressBar = findViewById(R.id.progressBar_salvar_editar_vacina);

        buttonSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvar();
            }
        });

        editTextData1.setKeyListener(null);
        editTextData2.setKeyListener(null);
        editTextData3.setKeyListener(null);

        Bundle bundle = getIntent().getExtras();
        if( bundle != null) {
            vacina = (Cartao) bundle.getSerializable(VACINA);
            parenteId = bundle.getString(ID_PARENTE);

            if(vacina.getDoses().equals(1)){

                editTextData1.setText(vacina.getDataPrimeiraDose() != null && !vacina.getDataPrimeiraDose().isEmpty() ? vacina.getDataPrimeiraDose(): null);

                editTextData2.setEnabled(false);
                editTextData3.setEnabled(false);
            } else if( vacina.getDoses().equals(2)){

                editTextData1.setText(vacina.getDataPrimeiraDose() != null && !vacina.getDataPrimeiraDose().isEmpty() ? vacina.getDataPrimeiraDose(): null);
                editTextData1.setText(vacina.getDataSegundaDose() != null && !vacina.getDataSegundaDose().isEmpty() ? vacina.getDataSegundaDose(): null);

                editTextData3.setEnabled(false);
            } else {
                editTextData1.setText(vacina.getDataPrimeiraDose() != null && !vacina.getDataPrimeiraDose().isEmpty() ? vacina.getDataPrimeiraDose(): null);
                editTextData1.setText(vacina.getDataSegundaDose() != null && !vacina.getDataSegundaDose().isEmpty() ? vacina.getDataSegundaDose(): null);
                editTextData1.setText(vacina.getDataTerceiraDose() != null && !vacina.getDataTerceiraDose().isEmpty() ? vacina.getDataTerceiraDose(): null);
            }
        }

        editTextData1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogExibido = 1;
                calendarSetado =  Calendar.getInstance();
                if(vacina.getDataPrimeiraDose() != null && !vacina.getDataPrimeiraDose().isEmpty()){
                    try {
                        calendarSetado.setTime(Objects.requireNonNull(format.parse(vacina.getDataPrimeiraDose())));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                showDatePickerDialog(v);
            }
        });
        editTextData2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogExibido = 2;
                calendarSetado =  Calendar.getInstance();
                if(vacina.getDataSegundaDose() != null && !vacina.getDataSegundaDose().isEmpty()){
                    try {
                        calendarSetado.setTime(Objects.requireNonNull(format.parse(vacina.getDataSegundaDose())));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                showDatePickerDialog(v);
            }
        });
        editTextData3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogExibido = 3;
                calendarSetado =  Calendar.getInstance();
                if(vacina.getDataTerceiraDose() != null && !vacina.getDataTerceiraDose().isEmpty()){
                    try {
                        calendarSetado.setTime(Objects.requireNonNull(format.parse(vacina.getDataTerceiraDose())));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                showDatePickerDialog(v);
            }
        });




    }

    private void salvar()  {
        buttonSalvar.setEnabled(false);

            vacina.setDataPrimeiraDose(editTextData1.getText() != null && !editTextData1.getText().toString().isEmpty()
                    ? editTextData1.getText().toString(): null);
            vacina.setDataSegundaDose(editTextData2.getText() != null && !editTextData2.getText().toString().isEmpty()
                    ? editTextData1.getText().toString(): null);
            vacina.setDataTerceiraDose(editTextData3.getText() != null && !editTextData3.getText().toString().isEmpty()
                    ? editTextData1.getText().toString(): null);



        CartaoService.salvarAtualizarVacinaCartao(vacina,parenteId,progressBar,this);

    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment;
        switch (dialogExibido) {
            case 1:
                newFragment = new DatePickerFragment(editTextData1,calendarSetado);
                newFragment.show(getSupportFragmentManager(), "datePicker");
                break;
            case 2:
                newFragment = new DatePickerFragment(editTextData2,calendarSetado);
                newFragment.show(getSupportFragmentManager(), "datePicker");
                break;
            case 3:
                newFragment = new DatePickerFragment(editTextData3,calendarSetado);
                newFragment.show(getSupportFragmentManager(), "datePicker");
                break;
        }

    }


}
