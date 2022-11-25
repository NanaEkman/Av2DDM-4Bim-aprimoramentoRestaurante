package ddm.nana.av2ddm_4bim_aprimoramentorestaurante;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
//Camila Devita Basaglia - SC3010058 e Nana de Souza Ekman Simões - SC3010414

    private DatabaseReference BD = FirebaseDatabase.getInstance().getReference();

    private EditText txtMesa;
    private EditText txtItem;
    private EditText txtProduto;
    private Button btnInserir;
    private ListView lista;

    private ItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseReference restaurante = BD.child("restaurante");

        txtMesa = findViewById( R.id.txtMesa );
        txtItem = findViewById( R.id.txtItem );
        txtProduto = findViewById( R.id.txtProduto );
        lista = findViewById( R.id.lista);
        btnInserir = findViewById( R.id.btnInserir );
        btnInserir.setOnClickListener( new EscutadorBotao());

        FirebaseListOptions<Item> options = new FirebaseListOptions.Builder<Item>()
                .setLayout(R.layout.item_lista)
                .setQuery(restaurante, Item.class)
                .setLifecycleOwner( this )
                .build();

        adapter = new ItemAdapter(options);

        lista.setAdapter(adapter);

        EscutadorCliqueLista el = new EscutadorCliqueLista();
        lista.setOnItemClickListener( el );
        lista.setOnItemLongClickListener( el );

    }

    private class EscutadorBotao implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            DatabaseReference restaurante = BD.child("restaurante");

            String mesa = txtMesa.getText().toString();
            String item = txtItem.getText().toString();
            String produto = txtProduto.getText().toString();

            String chave = restaurante.push().getKey();
            Item i = new Item( chave, mesa, item, produto );
            restaurante.child(chave).setValue( i );

            txtMesa.setText("");
            txtItem.setText("");
            txtProduto.setText("");
            txtMesa.requestFocus();
        }
    }

    private class ItemAdapter extends FirebaseListAdapter<Item> {

        public ItemAdapter(FirebaseListOptions options) {
            super( options );
        }

        @Override
        protected void populateView(View v, Item c, int position) {

            TextView lblMesa = v.findViewById( R.id.lblMesa );
            TextView lblItem = v.findViewById( R.id.lblItem );
            TextView lblProduto = v.findViewById( R.id.lblProduto );
            TextView lblAtendido = v.findViewById( R.id.lblAtendido );

            lblMesa.setText( c.getMesa() );
            lblItem.setText( c.getItem() );
            lblProduto.setText( c.getProduto() );
            if (c.isAtendido()){
                lblAtendido.setText( "SIM" );
                lblAtendido.setTextColor(Color.GREEN);
            } else {
                lblAtendido.setText( "NÃO" );
                lblAtendido.setTextColor(Color.RED);
            }


        }
    }

    private class EscutadorCliqueLista implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

        private DatabaseReference restaurante = BD.child("restaurante");

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            Item item = adapter.getItem(i);
            item.setAtendido(true);
            restaurante.child(item.getChave()).setValue(item);

        }

        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

            Item item = adapter.getItem(i);
            String chave = item.getChave();
            DatabaseReference c = restaurante.child(chave);
            c.setValue(null);

            return true;
        }
    }
}