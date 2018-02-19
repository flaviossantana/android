package br.com.alura.agenda;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import br.com.alura.agenda.modelo.Prova;

public class ProvasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provas);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction tx = fragmentManager.beginTransaction();
        tx.replace(R.id.frame_principal, new ListaProvaFragment());

        if(isLand()){
            tx.replace(R.id.frame_secundario, new DetalhesProvaFragment());
        }

        tx.commit();

    }

    private boolean isLand() {
        return getResources().getBoolean(R.bool.isModoPaisagem);
    }

    public void apresentaProva(Prova prova) {

        FragmentManager manager = getSupportFragmentManager();
        if(isLand()){
            DetalhesProvaFragment detalhesProvaFrag = (DetalhesProvaFragment) manager.findFragmentById(R.id.frame_secundario);
            detalhesProvaFrag.popularCamposCom(prova);
        }else {
            DetalhesProvaFragment detalhesProvaFragment = new DetalhesProvaFragment();
            Bundle params = new Bundle();
            params.putSerializable("prova", prova);
            detalhesProvaFragment.setArguments(params);

            FragmentTransaction tx = manager.beginTransaction();
            tx.replace(R.id.frame_principal, detalhesProvaFragment);
            tx.commit();
        }


    }
}
