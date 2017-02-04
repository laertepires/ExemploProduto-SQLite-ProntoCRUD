package thiagocury.eti.br.exemplobancosqliteproduto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by thiagocury on 28/06/15.
 */
public class ProdutoAdapter extends BaseAdapter{

        private Context context;

        private ArrayList<Produto> listaProduto;
        private LayoutInflater inflater;

        /* Buscando referência dos objetos relacionados ao
           layout da linha (row) do ListView */
        public TextView tvIdProduto;
        public TextView tvNome;
        public TextView tvValorCusto;
        public TextView tvQuantidade;

        public ProdutoAdapter(Context context, ArrayList<Produto> listaProduto) {
            //super();
            this.context = context;
            this.listaProduto = listaProduto;

            inflater = LayoutInflater.from(context);
        }//fecha construtor

        //Chamar no braço também
        @Override
        public void notifyDataSetChanged() {
            // TODO Auto-generated method stub
            try {
                super.notifyDataSetChanged();
            } catch (Exception e) {
                // TODO: handle exception
                trace("Erro : " + e.getMessage());
            }
        }//fecha notifyDataSetChanged

        //Métodos responsáveis pelo envio de mensagem
        public void toast (String msg){
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        }

        private void trace (String msg){
            toast(msg);
        }

        public void add(Produto prod){
            listaProduto.add(prod);
        }

        public void remove(Produto prod){
            listaProduto.remove(prod);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            //Fazer no braço
            return listaProduto.size();
        }

        @Override
        public Produto getItem(int position) {
            // TODO Auto-generated method stub
            //Fazer no braço
            return listaProduto.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            //Fazer no braço
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub

            try {
                Produto prod = listaProduto.get(position);

                if(convertView==null) {
                    convertView = inflater.inflate(R.layout.list_produto, null);
                }

                //instâncias dos objetos da linha
                //Guarda a referencia dos objeto
                tvIdProduto = (TextView) convertView.findViewById(R.id.tvIdProduto);
                tvNome = (TextView) convertView.findViewById(R.id.tvNome);
                tvValorCusto = (TextView) convertView.findViewById(R.id.tvValorCusto);
                tvQuantidade = (TextView) convertView.findViewById(R.id.tvQuantidade);

                //Setando o produto na respectiva linha
                tvIdProduto.setText("Código: "+prod.getIdProduto());
                tvNome.setText("Nome: "+prod.getNome());
                tvValorCusto.setText("Valor Custo: R$ "+prod.getValorCusto());
                tvQuantidade.setText("Quantidade: "+prod.getQuantidade());

                return convertView;
            } catch (Exception e) {
                // TODO: handle exception
                trace("Erro : " + e.getMessage());
            }//fecha catch
            return convertView;
        }//fecha getView
}//fecha classe