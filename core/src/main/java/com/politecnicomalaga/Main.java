package com.politecnicomalaga;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector3;

public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private BitmapFont fuente;
    private OrthographicCamera camara;
    private Tabla tablero;
    private Boton btnIniciar, btnPausar, btnReiniciar;
    private boolean enEjecucion = false;
    private float tiempoTranscurrido = 0;

    @Override
    public void create() {
        batch = new SpriteBatch();
        fuente = new BitmapFont();
        camara = new OrthographicCamera();
        camara.setToOrtho(false, 800, 600);
        tablero = new Tabla(50, 50, 30);

        btnIniciar = new Boton("Iniciar", 50, 500, 100, 50);
        btnPausar = new Boton("Pausar", 200, 500, 100, 50);
        btnReiniciar = new Boton("Reiniciar", 350, 500, 100, 50);
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camara.combined);
        batch.begin();

        tablero.dibujar(batch, fuente);
        btnIniciar.dibujar(batch, fuente);
        btnPausar.dibujar(batch, fuente);
        btnReiniciar.dibujar(batch, fuente);

        batch.end();

        if (enEjecucion) {
            tiempoTranscurrido += Gdx.graphics.getDeltaTime();
            if (tiempoTranscurrido >= 1) {
                tablero.actualizar();
                tiempoTranscurrido = 0;
            }
        }

        if (Gdx.input.justTouched()) {
            Vector3 posicionToque = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camara.unproject(posicionToque);

            if (btnIniciar.colisiona(posicionToque.x, posicionToque.y)) enEjecucion = true;
            if (btnPausar.colisiona(posicionToque.x, posicionToque.y)) enEjecucion = false;
            if (btnReiniciar.colisiona(posicionToque.x, posicionToque.y)) tablero.reiniciar();
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        fuente.dispose();
    }
}

