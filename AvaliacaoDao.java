package br.com.juniorosc.quarta.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import br.com.juniorosc.quarta.entity.Avaliacao;

import java.util.ArrayList;

public class AvaliacaoDao {
    private SQLiteDatabase db;

    public static final String[] COLUNAS = {
            AvaliacaoDao.COLUMN_ID, AvaliacaoDao.COLUMN_HOSPITAL, AvaliacaoDao.COLUMN_ESPECIALIDADE,
            AvaliacaoDao.COLUMN_ESPECIALISTA, AvaliacaoDao.COLUMN_NOTA};

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_HOSPITAL = "hospital";
    public static final String COLUMN_ESPECIALIDADE = "especialidade";
    public static final String COLUMN_ESPECIALISTA = "especialista";
    public static final String COLUMN_NOTA = "nota";

    public AvaliacaoDao(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        this.db = dbHelper.getWritableDatabase();
    }

    public long salvar(Avaliacao avaliacao) {
        if (avaliacao.getId() != 0) {
            return this.atualizar(avaliacao);
        } else {
            return this.inserir(avaliacao);
        }
    }

    public long inserir(Avaliacao avaliacao) {
        long idAvaliacao = -1;
        db.beginTransaction();
        try {
            ContentValues valores = new ContentValues();
            valores.put(AvaliacaoDao.COLUMN_HOSPITAL, avaliacao.getHospital());
            valores.put(AvaliacaoDao.COLUMN_ESPECIALIDADE, avaliacao.getEspecialidade());
            valores.put(AvaliacaoDao.COLUMN_ESPECIALISTA, avaliacao.getEspecialista());
            valores.put(AvaliacaoDao.COLUMN_NOTA, avaliacao.getNota());

            idAvaliacao = db.insert(DBHelper.TABLE_AVALIACAO, null, valores);

            if (idAvaliacao != -1) {
                db.setTransactionSuccessful();
            }
        } finally {
            db.endTransaction();
        }
        return idAvaliacao;
    }

    private long atualizar(Avaliacao avaliacao) {
        long idAvaliacao = -1;
        db.beginTransaction();
        try {
            ContentValues valores = new ContentValues();
            valores.put(AvaliacaoDao.COLUMN_HOSPITAL, avaliacao.getHospital());
            valores.put(AvaliacaoDao.COLUMN_ESPECIALIDADE, avaliacao.getEspecialidade());
            valores.put(AvaliacaoDao.COLUMN_ESPECIALISTA, avaliacao.getEspecialista());
            valores.put(AvaliacaoDao.COLUMN_NOTA, avaliacao.getNota());
            String where = AvaliacaoDao.COLUMN_ID + "=" + avaliacao.getId();
            idAvaliacao = db.update(DBHelper.TABLE_AVALIACAO, valores, where, null);

            if (idAvaliacao != -1) {
                db.setTransactionSuccessful();
            }
        } finally {
            db.endTransaction();
        }
        return idAvaliacao;
    }

    public Avaliacao getAvaliacao(int id) {
        String where = AvaliacaoDao.COLUMN_ID + "=" + id;
        Cursor resultSet = this.db.query(DBHelper.TABLE_AVALIACAO, AvaliacaoDao.COLUNAS, where,
                null, null, null, null);
        Avaliacao retorno = null;

        if (resultSet.getCount() > 0) {
            resultSet.moveToFirst();
            do {
                retorno = this.preencherAvaliacao(resultSet);
            }
            while (resultSet.moveToNext());
        }
        return retorno;

    }

    public ArrayList<Avaliacao> getAvaliacaoList() {
        Cursor resultSet = this.db.query(DBHelper.TABLE_AVALIACAO, AvaliacaoDao.COLUNAS,
                null, null, null, null, null);
        ArrayList<Avaliacao> retorno = new ArrayList<Avaliacao>();

        if (resultSet.getCount() > 0) {
            resultSet.moveToFirst();
            do {
                retorno.add(this.preencherAvaliacao(resultSet));
            }
            while (resultSet.moveToNext());
        }
        return retorno;
    }

    private Avaliacao preencherAvaliacao(Cursor resultSet) {
        int id = resultSet.getInt(resultSet.getColumnIndex(AvaliacaoDao.COLUMN_ID));
        String hospital = resultSet.getString(resultSet.getColumnIndex(AvaliacaoDao.COLUMN_HOSPITAL));
        String especialidade = resultSet.getString(resultSet.getColumnIndex(AvaliacaoDao.COLUMN_ESPECIALIDADE));
        String especialista = resultSet.getString(resultSet.getColumnIndex(AvaliacaoDao.COLUMN_ESPECIALISTA));
        int nota = resultSet.getInt(resultSet.getColumnIndex(AvaliacaoDao.COLUMN_NOTA));
        Avaliacao retorno = new Avaliacao(id, hospital, especialidade, especialista, nota);
        return retorno;
    }

}
