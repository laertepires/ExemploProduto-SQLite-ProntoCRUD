package thiagocury.eti.br.exemplobancosqliteproduto;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class TelaBusca extends AppCompatActivity {

    //Banco
    private ProdutoDAO pDAO; //instância responsável pela persistência dos dados

    private ArrayList<Produto> prodAux; //Lista de contatos cadastrados no BD
    private ProdutoAdapter adapter; //adapter para receber os dados

    //Componentes visuais
    private ListView lvProdutos;

    //Guarda a posição selecionada
    private int posSelec = -1;

    //Menu de Contexto
    private static final int ALTERAR = 0;
    private static final int DELETAR = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_busca);

        lvProdutos = (ListView) findViewById(R.id.lvProdutos);

        pDAO = new ProdutoDAO(this);
        pDAO.abrirBanco();
        prodAux = pDAO.consultar();

        adapter = new ProdutoAdapter(this,prodAux);

        lvProdutos.setAdapter(adapter);
        //Notificando o adapter para atualização dos dados na tela
        adapter.notifyDataSetChanged();

        lvProdutos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                //Guardando a posição escolhida pelo usuário
                posSelec = position;

                return false;
            }
        });

        //Registrando menu de contexto para a ListView
        registerForContextMenu(lvProdutos);
    }//fecha onCreate

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.setHeaderTitle("Menu");
        menu.addSubMenu(DELETAR, DELETAR, 100, "Deletar");
        menu.addSubMenu(ALTERAR, ALTERAR, 200, "Alterar");
    }//fecha onCreate

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case DELETAR:

                AlertDialog.Builder msg = new AlertDialog.Builder(TelaBusca.this);
                msg.setTitle("Alerta");
                msg.setMessage("Você tem certeza que deseja excluir?");
                msg.setIcon(R.mipmap.ic_launcher);
                msg.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Resgatando o produto selecionado pelo usuario
                        Produto p = prodAux.get(posSelec);
                        //Removendo do Banco
                        pDAO.excluir(p);
                        //Removendo do ArrayList
                        prodAux.remove(p);

                        Toast.makeText(
                                getBaseContext(),
                                "Produto Excluído com sucesso!",
                                Toast.LENGTH_LONG).show();
                        adapter.notifyDataSetChanged();
                        //Notificando o adapter
                        adapter.notifyDataSetChanged();
                    }
                });

                msg.setNeutralButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(
                                getBaseContext(),
                                "Produto não foi excluído!",
                                Toast.LENGTH_LONG).show();
                    }
                });
                msg.show();
                break;

            case ALTERAR:
                //Resgatando o produto selecionado pelo usuario
                Produto p = prodAux.get(posSelec);
                //Enviando para tela de cadastro (alterar)
                Intent it = new Intent(TelaBusca.this, TelaCadastro.class);
                it.putExtra("p",p); //Não esquecer de implementar Serializable na Classe Produto
                it.putExtra("acao","alterar");
                startActivity(it);
                finish();
                break;
        }
        return super.onContextItemSelected(item);
    }



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
}//fecha classe
