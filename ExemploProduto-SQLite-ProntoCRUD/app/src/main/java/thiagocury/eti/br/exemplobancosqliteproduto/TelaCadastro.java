package thiagocury.eti.br.exemplobancosqliteproduto;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class TelaCadastro extends AppCompatActivity {

    private EditText etCodigo;
    private EditText etNome;
    private EditText etValorCusto;
    private EditText etQuantidade;
    private Button btnAdicionar;

    private Button btnAlterar;

    private Produto p;

    //Banco
    private ProdutoDAO pDAO; //instância responsável pela persistência dos dados

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastro);

        etCodigo = (EditText) findViewById(R.id.etCodigo);
        etNome = (EditText) findViewById(R.id.etNome);
        etValorCusto = (EditText) findViewById(R.id.etValorCusto);
        etQuantidade = (EditText) findViewById(R.id.etQuantidade);
        btnAdicionar = (Button) findViewById(R.id.btnAdicionar);
        btnAlterar = (Button) findViewById(R.id.btnAlterar);

        //Instanciando e abrindo o banco
        pDAO = new ProdutoDAO(this);
        pDAO.abrirBanco();

        //Adicionar
        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                p = new Produto();
                p.setIdProduto(Long.parseLong(etCodigo.getText().toString()));
                p.setNome(etNome.getText().toString());
                p.setValorCusto(Double.parseDouble(etValorCusto.getText().toString()));
                p.setQuantidade(Long.parseLong(etQuantidade.getText().toString()));

                Toast.makeText(
                        getBaseContext(),
                        "Produto Cadastrado: "+p.toString(),
                        Toast.LENGTH_LONG).show();

                //Enviando para o método cadastrar
                pDAO.inserir(p);

                limpar();
            }
        });//fecha Adicionar

        //Alterar
        String acao = getIntent().getStringExtra("acao");

        if(acao != null){

            //Habilitando a visualização do botão alterar
            btnAlterar.setVisibility(View.VISIBLE);
            //Desabilitando para a edição o campo do código
            etCodigo.setEnabled(false);

            Produto p = (Produto) getIntent().getSerializableExtra("p");
            etCodigo.setText(String.valueOf(p.getIdProduto()));
            etNome.setText(String.valueOf(p.getNome()));
            etValorCusto.setText(String.valueOf(p.getValorCusto()));
            etQuantidade.setText(String.valueOf(p.getQuantidade()));
        }

        //Botão Alterar
        btnAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                p = new Produto();
                p.setIdProduto(Long.parseLong(etCodigo.getText().toString()));
                p.setNome(etNome.getText().toString());
                p.setValorCusto(Double.parseDouble(etValorCusto.getText().toString()));
                p.setQuantidade(Long.parseLong(etQuantidade.getText().toString()));

                Toast.makeText(
                        getBaseContext(),
                        "Produto Alterado com Sucesso! ",
                        Toast.LENGTH_LONG).show();

                //Enviando para o método alterar
                pDAO.alterar(p);
                limpar();
                //Setando o botão para invisível novamente
                btnAlterar.setVisibility(View.INVISIBLE);
            }
        });//fecha Alterar

    }//fecha onCreate

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        //Toda vez que a Activity receber o foco, ativamos a conexão com o bd
        pDAO.abrirBanco();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        //Toda vez que a Activity perder o foco, encerramos a conexão com o bd
        pDAO.fecharBanco();
    }

    private void limpar(){
        etCodigo.setText(null);
        etNome.setText(null);
        etValorCusto.setText(null);
        etQuantidade.setText(null);
    }//fecha limpar
}//fecha classe