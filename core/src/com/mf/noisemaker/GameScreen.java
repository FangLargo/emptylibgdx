package com.mf.noisemaker;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class GameScreen implements Screen {

    final Noisemaker game;

    OrthographicCamera camera;

    ShapeRenderer shapeRenderer;

    float screenHeight = 100f;
    float pixelsPerUnit = Gdx.graphics.getHeight()/screenHeight;
    float screenWidth = Gdx.graphics.getWidth()/pixelsPerUnit;

    //Vector2 playerPos;
    Array<Vector3> touchPosArray;

    public GameScreen(final Noisemaker gam) {
        // TODO Auto-generated constructor stub
        this.game = gam;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth()/pixelsPerUnit, screenHeight);
        camera.position.set(screenWidth/2, 50, 0);

        shapeRenderer = new ShapeRenderer();

        //playerPos = new Vector2();
        touchPosArray = new Array<Vector3>();
        touchPosArray.add(new Vector3());

        System.out.println("Things Happen Here");
        Gdx.input.setInputProcessor(inputProcessor);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.9f, 0.9f, 0.9f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        shapeRenderer.setProjectionMatrix(camera.combined);

        shapeRenderer.begin(ShapeType.Filled);
        shapeRenderer.setColor(0, 1, 0, 1);
        //shapeRenderer.circle(playerPos.x, playerPos.y, 5f);

        for(int i = 0; i < touchPosArray.size; i++) {
            if(touchPosArray.get(i).x >= 0f) {
                shapeRenderer.circle(touchPosArray.get(i).x, touchPosArray.get(i).y, 5f);
                //System.out.println(i);
            }
        }
        //System.out.println(touchPosArray.size);

        shapeRenderer.end();

        System.out.println(1/delta);
    }

    InputAdapter inputProcessor = new InputAdapter() {

        //Vector3 touchPos = new Vector3();
        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            // TODO Auto-generated method stub
            if (button != Input.Buttons.LEFT || pointer > 0) return false;
            //playerPos.set(touchPos.x, touchPos.y);
            if(touchPosArray.size > pointer) {
                for(int i = touchPosArray.size; i < pointer + 1; i++) {
                    touchPosArray.add(new Vector3());
                }
            }

            camera.unproject(touchPosArray.get(pointer).set(screenX, screenY, 0f));

            //camera.unproject(touchPos.set(screenX, screenY, 0));
            //touchPosArray.get(pointer).set(touchPos.x, touchPos.y);
            //System.out.println(pointer + "+" + touchPosArray.get(pointer).x + "," + touchPosArray.get(pointer).y);
            return true;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            // TODO Auto-generated method stub
            touchPosArray.get(pointer).set(-1, -1, -1);
            return false;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            // TODO Auto-generated method stub
            //camera.unproject(touchPos.set(screenX, screenY, 0));
            //touchPosArray.get(pointer).set(touchPos.x, touchPos.y);
            camera.unproject(touchPosArray.get(pointer).set(screenX, screenY, 0f));
            return false;
        }

    };

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
