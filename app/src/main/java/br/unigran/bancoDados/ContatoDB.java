package br.unigran.bancoDados;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.LinkedList;
import java.util.List;

import br.unigran.agenda.Contato;

public class ContatoDB {
    DBHelper db;
    private SQLiteDatabase conexao;
    public ContatoDB(DBHelper db) {this.db = db;}

    public void inserir(Contato contato){
        conexao = db.getWritableDatabase();//abri o bd

        ContentValues valores = new ContentValues();
        valores.put("nome",contato.getNome());
        valores.put("telefone",contato.getTelefone());

        conexao.insertOrThrow("Agenda",null,valores);
        conexao.close();
    }

    public void list(List data) {
        data.clear();
        conexao = db.getReadableDatabase();

        String names[] = {"id", "nome","telefone"};
        Cursor query = conexao.query(
                "agenda", names, null, null, null, null,
                null
        );

        while(query.moveToNext()) {
            Contato cliente = new Contato();
            cliente.setId(Integer.parseInt(query.getString(0)));
            cliente.setNome(query.getString(1));
            cliente.setTelefone(query.getString(2));

            data.add(cliente);
        }

        conexao.close();

    }

    public void remover(int id){
        conexao=db.getWritableDatabase();
        conexao.delete("Agenda","id=?",new String[]{id+""});
        conexao.close();
    }

}
