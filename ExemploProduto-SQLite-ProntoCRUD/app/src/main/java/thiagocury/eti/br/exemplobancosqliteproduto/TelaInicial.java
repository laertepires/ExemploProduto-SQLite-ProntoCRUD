package thiagocury.eti.br.exemplobancosqliteproduto;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class TelaInicial extends AppCompatActivity {

    private Button btnAdicionar;
    private Button btnProcurar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial);

        btnAdicionar = (Button) findViewById(R.id.btnAdicionar);
        btnProcurar = (Button) findViewById(R.id.btnProcurar);

        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(TelaInicial.this,TelaCadastro.class);
                startActivity(it);
            }
        });

        btnProcurar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(TelaInicial.this,TelaBusca.class);
                startActivity(it);
            }
        });
    }//fecha onCreate
}//fecha classe
